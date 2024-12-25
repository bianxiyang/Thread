package cn.com.tcsl.fast.kds.server.controller;

import cn.com.tcsl.fast.common.constant.CacheKeyConstant;
import cn.com.tcsl.fast.common.converter.json.JsonConverter;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.common.util.ResponseEntityUtil;
import cn.com.tcsl.fast.framework.controller.GenericController;
import cn.com.tcsl.fast.framework.exception.ApplicationError;
import cn.com.tcsl.fast.framework.vo.OrderSettleDto;
import cn.com.tcsl.fast.kds.server.constant.KdsConstant;
import cn.com.tcsl.fast.kds.server.enums.ServiceModeEnum;
import cn.com.tcsl.fast.kds.server.enums.StatusEnum;
import cn.com.tcsl.fast.kds.server.model.KdsOrder;
import cn.com.tcsl.fast.kds.server.model.KdsOrderItem;
import cn.com.tcsl.fast.kds.server.service.KdsService;
import cn.com.tcsl.fast.kds.server.service.KdsSettingService;
import cn.com.tcsl.fast.kds.server.service.PushService;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderBo;
import cn.com.tcsl.fast.kds.server.service.bo.PushNewOrderCnd;
import cn.com.tcsl.fast.order.client.OrderForKdsClient;
import cn.com.tcsl.fast.order.dto.kds.OrderQueryDto;
import cn.com.tcsl.fast.order.vo.kds.OrderDetailsVo;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <b>功能：kds接收消费机消息controller</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:53&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/mqconsumer")
public class KdsSettleController extends GenericController {
    @Resource
    private JsonConverter jsonConverter;
    @Resource
    private KdsService kdsService;
    @Resource
    private OrderForKdsClient orderForKdsClient;
    @Resource
    private PushService pushService;
    @Resource
    private RedissonClient redissonClient;

    @Resource
    private KdsSettingService kdsSettingService;


    @ApiOperation(value = "接收消费机结算动作", notes = "接收消费机结算动作", httpMethod = "POST")
    @PostMapping(path = "/settle")
    public ResponseEntity<ResponseResult> settle(@Valid @RequestBody OrderSettleDto orderSettleDto) {
        log.info("接收消费机结算入参:" + jsonConverter.toJson(orderSettleDto));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Long bsId = orderSettleDto.getBsId();
        Integer shopId = orderSettleDto.getCreateShopId();
        String redisKey = CacheKeyConstant.CACHE_TEMP + "kdsSettle:" + bsId;
        RBucket<Object> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists()) {
            throw new ApplicationError("S1.SYS.10002", "结算单重复！");
        }
        if (Objects.isNull(shopId) || Objects.isNull(bsId)) {
            log.info("接收消费机结算动作入参校验失败！:" + jsonConverter.toJson(orderSettleDto));
            throw new ApplicationError("S1.ERR.10020", "接收消费机结算动作入参校验失败！");
        }
        //  查询店铺是否使用取餐屏或kds
        Boolean flag = kdsSettingService.appreciationCheck(shopId, KdsConstant.APPRECIATION_KDS);
        if (Boolean.TRUE.equals(flag)) {
            OrderQueryDto queryDto = new OrderQueryDto();
            queryDto.setBsId(bsId);
            queryDto.setCreateShopId(orderSettleDto.getCreateShopId());
            ResponseEntity<ResponseResult<OrderDetailsVo>> response = orderForKdsClient.getdetail(shopId, queryDto);
            OrderDetailsVo orderDetailsVo = ResponseEntityUtil.getResponseData(response);
            List<KdsOrderItem> kdsOrderItems = kdsService.saveOrder(orderDetailsVo);
            bucket.set(bsId, 3, TimeUnit.DAYS);
            //  有异常情况先处理结算，后处理退单
            String refundBeforeSettle = CacheKeyConstant.CACHE_TEMP + "refundBeforeSettle:" + bsId;
            RBucket<OrderSettleDto> refundBeforeBucket = redissonClient.getBucket(refundBeforeSettle);
            PushNewOrderCnd pushNewOrderCnd = new PushNewOrderCnd();
            pushNewOrderCnd.setShopId(shopId);
            pushNewOrderCnd.setOrderItems(kdsOrderItems);
            pushNewOrderCnd.setVersionFlag(1);
            pushNewOrderCnd.setCenterId(getCenterId());
            pushNewOrderCnd.setOrderType(orderDetailsVo.getSaleTypeId());
            if (refundBeforeBucket.isExists()) {
                KdsOrderBo orderBo = new KdsOrderBo();
                orderBo.setShopId(shopId);
                orderBo.setBsId(bsId);
                List<KdsOrder> orderList = kdsService.queryKdsOrder(orderBo);
                kdsService.saveRefund(orderSettleDto, orderList);
                pushNewOrderCnd.setServiceMode(ServiceModeEnum.ALL_REFUND.getValue());
            } else {
                //  接收结算数据后，推送新订单socket,orderDate现在是null,需要OrderDetailsVo，增加制作时间*********。
                pushNewOrderCnd.setServiceMode(ServiceModeEnum.SETTLE.getValue());
                if (Objects.nonNull(orderDetailsVo.getDeliveryTime())) {
                    Date deliveryTime = kdsService.handleDeliveryTime(orderDetailsVo.getDeliveryTime(), getCenterId(), shopId, orderDetailsVo.getSaleTypeId());
                    pushNewOrderCnd.setDeliveryTime(deliveryTime);
                }
            }
            pushService.pushNewOrder(pushNewOrderCnd);
        } else {
            log.info("店铺:{} 当前配置:{}  未开通取餐屏或kds增值", shopId, flag);
        }
        stopWatch.stop();
        log.info("接收消费机结算接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    @ApiOperation(value = "接收消费机退单动作", notes = "接收消费机退单动作", httpMethod = "POST")
    @PostMapping(path = "/refund")
    public ResponseEntity<ResponseResult> refund(@Valid @RequestBody OrderSettleDto orderSettleDto) {
        log.info("接收消费机退单入参:" + jsonConverter.toJson(orderSettleDto));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Long bsId = orderSettleDto.getBsId();
        Long ssId = orderSettleDto.getSsId();
        Integer shopId = orderSettleDto.getCreateShopId();
        Long orderOriginId = orderSettleDto.getOrderOriginId();

        if (Objects.isNull(orderOriginId) || (orderOriginId != 103L)) {
            String redisKey = CacheKeyConstant.CACHE_TEMP + "kdsRefund:" + bsId + "_" + ssId;
            RBucket<Object> bucket = redissonClient.getBucket(redisKey);
            if (bucket.isExists()) {
                throw new ApplicationError("S1.SYS.10002", "退单重复！");
            }
            if (Objects.isNull(shopId) || Objects.isNull(bsId)) {
                log.info("接收消费机退单动作入参校验失败！:" + jsonConverter.toJson(orderSettleDto));
                throw new ApplicationError("S1.ERR.10020", "接收消费机退单动作入参校验失败！");
            }
            KdsOrderBo orderBo = new KdsOrderBo();
            orderBo.setShopId(shopId);
            orderBo.setBsId(bsId);
            List<KdsOrder> orderList = kdsService.queryKdsOrder(orderBo);
            if (CollectionUtils.isEmpty(orderList)) {
                log.info("退单单据不存在或已发餐！:" + jsonConverter.toJson(orderList));
                //  处理先接到退单，后接到结算的问题
                String refundBeforeSettle = CacheKeyConstant.CACHE_TEMP + "refundBeforeSettle:" + bsId;
                RBucket<OrderSettleDto> refundBeforeBucket = redissonClient.getBucket(refundBeforeSettle);
                if (!refundBeforeBucket.isExists()) {
                    refundBeforeBucket.set(orderSettleDto, 1, TimeUnit.DAYS);
                }
                return ResponseEntity.ok(ResponseResult.success());
            } else if (CollectionUtils.isNotEmpty(orderList)) {
                KdsOrder kdsOrder = orderList.get(0);
                if (!kdsOrder.getStatus().equals(StatusEnum.NEW_MAKE_FINISH.getValue())) {
                    List<KdsOrderItem> kdsOrderItems = kdsService.saveRefund(orderSettleDto, orderList);
                    bucket.set(bsId, 3, TimeUnit.DAYS);
                    PushNewOrderCnd pushNewOrderCnd = new PushNewOrderCnd();
                    pushNewOrderCnd.setShopId(shopId);
                    pushNewOrderCnd.setOrderItems(kdsOrderItems);
                    pushNewOrderCnd.setVersionFlag(1);
                    pushNewOrderCnd.setCenterId(getCenterId());
                    pushNewOrderCnd.setServiceMode(ServiceModeEnum.ALL_REFUND.getValue());
                    pushNewOrderCnd.setOrderType(kdsOrderItems.get(0).getOrderType());
                    pushService.pushNewOrder(pushNewOrderCnd);
                }
            }
        }
        stopWatch.stop();
        log.info("接收消费机结算接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


}
