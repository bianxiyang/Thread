package cn.com.tcsl.fast.kds.server.controller;

import cn.com.tcsl.fast.common.constant.CacheKeyConstant;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.datasync.dto.kds.KdsOrderSettleDto;
import cn.com.tcsl.fast.framework.controller.GenericController;
import cn.com.tcsl.fast.framework.exception.ApplicationError;
import cn.com.tcsl.fast.kds.cnd.*;
import cn.com.tcsl.fast.kds.server.enums.ServiceModeEnum;
import cn.com.tcsl.fast.kds.server.event.EventPublisher;
import cn.com.tcsl.fast.kds.server.event.kds.PushModifyTimeEvent;
import cn.com.tcsl.fast.kds.server.model.KdsOrder;
import cn.com.tcsl.fast.kds.server.model.KdsOrderItem;
import cn.com.tcsl.fast.kds.server.model.datasync.KdsSyncCnd;
import cn.com.tcsl.fast.kds.server.service.KdsService;
import cn.com.tcsl.fast.kds.server.service.PushService;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderBo;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderItemBo;
import cn.com.tcsl.fast.kds.server.service.bo.PushNewOrderCnd;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <b>功能：kds对接windows门店端</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/kdsWindows")
public class KdsForWinController extends GenericController {

    @Resource
    private KdsService kdsService;
    @Resource
    private PushService pushService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private EventPublisher eventPublisher;

    @ApiOperation(value = "接收门店端结算数据", notes = "接收门店端结算数据", httpMethod = "POST")
    @PostMapping(path = "/receiveWinSettle")
    public ResponseEntity<ResponseResult> receiveWinSettle(@Valid @RequestBody ReceiveWinSettleCnd receiveWinSettleCnd) {
        log.info("接收门店端结算数据入参:" + jsonConverter.toJson(receiveWinSettleCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String redisKey = CacheKeyConstant.CACHE_TEMP + "kdsReceiveWinSettle:" + receiveWinSettleCnd.getBsId();
        RBucket<Object> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists()) {
            throw new ApplicationError("S1.SYS.10002", "结算单重复！");
        }
        Integer shopId = receiveWinSettleCnd.getShopId();
        List<WindowsItem> settleItems = receiveWinSettleCnd.getSettleItems();
        if (CollectionUtils.isEmpty(settleItems) || Objects.isNull(shopId)) {
            log.info("接收门店端结算数据接口开始-校验失败" + jsonConverter.toJson(receiveWinSettleCnd));
            throw new ApplicationError("S1.ERR.10020", "接收门店端结算数据接口开始-校验失败！");
        }
        List<KdsOrderItem> orderItems = kdsService.saveWindowsOrder(receiveWinSettleCnd);
        bucket.set(receiveWinSettleCnd.getBsId(), 3, TimeUnit.DAYS);
        PushNewOrderCnd pushNewOrderCnd = new PushNewOrderCnd();
        pushNewOrderCnd.setShopId(shopId);
        pushNewOrderCnd.setOrderItems(orderItems);
        pushNewOrderCnd.setVersionFlag(1);
        pushNewOrderCnd.setServiceMode(ServiceModeEnum.SETTLE.getValue());
        pushNewOrderCnd.setCenterId(getCenterId());
        if (Objects.nonNull(receiveWinSettleCnd.getDeliveryTime())) {
            Date deliveryTime = kdsService.handleDeliveryTime(receiveWinSettleCnd.getDeliveryTime(), receiveWinSettleCnd.getCenterId(), shopId, receiveWinSettleCnd.getOrderType());
            pushNewOrderCnd.setDeliveryTime(deliveryTime);
        }
        pushNewOrderCnd.setOrderType(receiveWinSettleCnd.getOrderType());
        pushService.pushNewOrder(pushNewOrderCnd);
        stopWatch.stop();
        log.info("接收门店端结算数据接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    @ApiOperation(value = "接收门店端退单数据", notes = "接收门店端退单数据", httpMethod = "POST")
    @PostMapping(path = "/receiveWinRefund")
    public ResponseEntity<ResponseResult> receiveWinRefund(@Valid @RequestBody ReceiveWinRefundCnd receiveWinRefundCnd) {
        log.info("接收门店端退单数据入参:" + jsonConverter.toJson(receiveWinRefundCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String redisKey = CacheKeyConstant.CACHE_TEMP + "kdsReceiveWinRefund:" + receiveWinRefundCnd.getBsId() + "_" + receiveWinRefundCnd.getSsId();
        RBucket<Object> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists()) {
            throw new ApplicationError("S1.SYS.10002", "退单重复！");
        }
        Long bsId = receiveWinRefundCnd.getBsId();
        Integer shopId = receiveWinRefundCnd.getShopId();
        List<WindowsRefundItem> refundItems = receiveWinRefundCnd.getRefundItemList();
        if (CollectionUtils.isEmpty(refundItems) || Objects.isNull(bsId)) {
            log.info("接收门店端退单数据接口开始-校验失败" + jsonConverter.toJson(receiveWinRefundCnd));
            throw new ApplicationError("S1.ERR.10020", "接收门店端退单数据接口开始-校验失败！");
        }
        List<KdsOrderItem> orderItems = kdsService.handleWindowsRefund(receiveWinRefundCnd);
        bucket.set(receiveWinRefundCnd.getBsId(), 3, TimeUnit.DAYS);
        PushNewOrderCnd pushNewOrderCnd = new PushNewOrderCnd();
        pushNewOrderCnd.setShopId(shopId);
        pushNewOrderCnd.setOrderItems(orderItems);
        pushNewOrderCnd.setVersionFlag(1);
        pushNewOrderCnd.setServiceMode(ServiceModeEnum.ALL_REFUND.getValue());
        pushNewOrderCnd.setCenterId(getCenterId());
        pushService.pushNewOrder(pushNewOrderCnd);
        stopWatch.stop();
        log.info("接收门店端退单数据接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }

    @ApiOperation(value = "接收订单送餐/自提时间数据", notes = "接收订单送餐/自提时间数据", httpMethod = "POST")
    @PostMapping(path = "/updateDeliveryTime")
    public ResponseEntity<ResponseResult> updateDeliveryTime(@RequestBody KdsUpdateCnd kdsUpdateCnd) {
        log.info("接收订单送餐/自提时间数据入参:" + jsonConverter.toJson(kdsUpdateCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //查询单据信息
        KdsOrderBo orderBo = new KdsOrderBo();
        orderBo.setShopId(kdsUpdateCnd.getShopId());
        orderBo.setBsId(kdsUpdateCnd.getBsId());
        List<KdsOrder> orderList = kdsService.queryKdsOrder(orderBo);
        log.info("kds更新查询单据结果：{}", jsonConverter.toJson(orderList));
        KdsOrder kdsOrder;
        if (CollectionUtils.isNotEmpty(orderList)) {
            kdsOrder = orderList.get(0);
            kdsOrder.setDeliveryTime(kdsUpdateCnd.getDeliveryTime());
            kdsOrder.setSettleTime(kdsUpdateCnd.getSettleTime());
            kdsService.updateKdsOrder(kdsOrder);
            //  更新本地后，刷新页面
            KdsOrderItemBo kdsOrderItemBo = new KdsOrderItemBo();
            kdsOrderItemBo.setShopId(kdsOrder.getShopId());
            kdsOrderItemBo.setKdsOrderId(kdsOrder.getId());
            log.info("kds更新查询2：{}", jsonConverter.toJson(kdsOrderItemBo));
            List<KdsOrderItem> kdsOrderItems = kdsService.queryKdsOrderItems(kdsOrderItemBo);
            PushNewOrderCnd pushNewOrderCnd = new PushNewOrderCnd();
            pushNewOrderCnd.setShopId(kdsUpdateCnd.getShopId());
            pushNewOrderCnd.setOrderItems(kdsOrderItems);
            pushNewOrderCnd.setPosId(999L);
            pushNewOrderCnd.setVersionFlag(1);
            pushNewOrderCnd.setServiceMode(ServiceModeEnum.DEFAULT.getValue());
            pushNewOrderCnd.setBsId(kdsOrder.getBsId());
            pushNewOrderCnd.setCenterId(getCenterId());
            pushService.pushNewOrder(pushNewOrderCnd);
        }

        stopWatch.stop();
        log.info("接收订单送餐/自提时间数据接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    @ApiOperation(value = "接收门店端出品数据", notes = "接收门店端出品数据", httpMethod = "POST")
    @PostMapping(path = "/receiveWinKdsData")
    public ResponseEntity<ResponseResult> receiveWinKdsData(@Valid @RequestBody KdsSyncCnd kdsSyncCnd) {
        log.info("接收门店端出品数据入参:" + jsonConverter.toJson(kdsSyncCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String,Object> pushKscMap = new HashMap<>();
        BeanUtil.copyProperties(kdsSyncCnd, pushKscMap);
        eventPublisher.publish(new PushModifyTimeEvent(this, kdsSyncCnd.getShopId(), pushKscMap));
        stopWatch.stop();
        log.info("接收门店端出品数据接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


}
