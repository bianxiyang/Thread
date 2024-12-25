package cn.com.tcsl.fast.kds.server.controller;

import cn.com.tcsl.fast.base.vo.biz.KitchenItemPlanVo;
import cn.com.tcsl.fast.base.vo.pos.PosHasSaleTypeVo;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.framework.controller.GenericController;
import cn.com.tcsl.fast.framework.exception.ApplicationError;
import cn.com.tcsl.fast.framework.interceptor.annotation.PreventDuplication;
import cn.com.tcsl.fast.kds.cnd.KdsBaseCnd;
import cn.com.tcsl.fast.kds.cnd.*;
import cn.com.tcsl.fast.kds.server.constant.KdsConstant;
import cn.com.tcsl.fast.kds.server.enums.SceneTypeEnum;
import cn.com.tcsl.fast.kds.server.enums.StatusEnum;
import cn.com.tcsl.fast.kds.server.enums.UpdateKdsTypeEnum;
import cn.com.tcsl.fast.kds.server.event.EventPublisher;
import cn.com.tcsl.fast.kds.server.event.kds.KdsOperationEvent;
import cn.com.tcsl.fast.kds.server.model.FifterKdsOrderItem;
import cn.com.tcsl.fast.kds.server.model.KdsOrder;
import cn.com.tcsl.fast.kds.server.model.KdsOrderItem;
import cn.com.tcsl.fast.kds.server.service.*;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderBo;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderItemBo;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderListBo;
import cn.com.tcsl.fast.kds.vo.*;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yomahub.tlog.context.TLogContext;
import io.swagger.annotations.ApiOperation;
import jodd.bean.BeanCopy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.redisson.api.RLock;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>功能：kds核心功能controller</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:50&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/kdsOrder")
public class KdsController extends GenericController {

    @Resource
    private KdsService kdsService;
    @Resource
    private PushService pushService;
    @Resource
    private KdsOrderService kdsOrderService;
    @Resource
    private RedissonClient redissonClient;
    @Resource
    private KdsSettingService kdsSettingService;
    @Resource
    private BaseInterfaceService baseInterfaceService;
    @Resource
    private EventPublisher eventPublisher;

    @ApiOperation(value = "kds待制作查询", notes = "kds待制作查询", httpMethod = "POST")
    @PostMapping(path = "/query")
    public ResponseEntity<ResponseResult<KdsQueryVo>> query(@Valid @RequestBody KdsQueryCnd kdsQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryCnd,SceneTypeEnum.QUERY_SCENE);
        log.info("kds待制作查询，入参为：{}", jsonConverter.toJson(kdsQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //  返回值
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        kdsOrderService.queryKdsOrder(kdsQueryCnd, kdsQueryVo, getCenterId(), getShopId());
        stopWatch.stop();
        log.info("kds待制作查询结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }



    @ApiOperation(value = "kds泳道模式", notes = "kds泳道查询", httpMethod = "POST")
    @PostMapping(path = "/laneModeQuery")
    public ResponseEntity<ResponseResult<KdsQueryVo>> laneModeQuery(@Valid @RequestBody KdsQueryCnd kdsQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryCnd,SceneTypeEnum.LANE_QUERY);
        log.info("kds泳道模式查询，入参为：{}", jsonConverter.toJson(kdsQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        kdsOrderService.laneModeQuery(kdsQueryCnd, kdsQueryVo, getCenterId(), getShopId());
        stopWatch.stop();
        log.info("kds泳道模式查询结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }





    @ApiOperation(value = "kds已传菜品查询(新版本已传无汇总,仅按单按菜按泳道展示)", notes = "kds已传菜品查询", httpMethod = "POST")
    @PostMapping(path = "/finishQuery")
    public ResponseEntity<ResponseResult<KdsQueryVo>> finishQuery(@Valid @RequestBody KdsQueryCnd kdsQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryCnd,SceneTypeEnum.FINISH_QUERY_SCENE);
        log.info("kds已传菜品查询，入参为：{}", jsonConverter.toJson(kdsQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //  返回值
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        if (kdsOrderService.queryFinish(kdsQueryCnd, kdsQueryVo))
            return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
        stopWatch.stop();
        log.info("kds已传菜品查询结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }

    @PreventDuplication(expireSeconds = 2)
    @ApiOperation(value = "kds本地数据更新", notes = "整单操作以及单品项操作以及已取餐", httpMethod = "POST")
    @PostMapping(path = "/update")
    public ResponseEntity<ResponseResult> update(@Valid @RequestBody KdsUpdateCnd kdsUpdateCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsUpdateCnd,SceneTypeEnum.UPDATE);
        log.info("kds本地数据更新入参:" + jsonConverter.toJson(kdsUpdateCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsUpdateCnd.getShopId();
        Integer updateKdsType = kdsUpdateCnd.getUpdateKdsType();
        Long bsId = kdsUpdateCnd.getBsId();
        List<Long> bsItemIds = kdsUpdateCnd.getBsItemIds();
        Long modifierId = kdsUpdateCnd.getModifierId();
        if (Objects.isNull(shopId) || Objects.isNull(updateKdsType) || Objects.isNull(bsId)) {
            log.info("kds本地数据更新入参校验失败！:" + jsonConverter.toJson(kdsUpdateCnd));
            throw new ApplicationError("S1.ERR.10020", "kds本地数据更新入参校验失败！");
        }
        if ((UpdateKdsTypeEnum.SINGELE_FINISHED.getValue().equals(updateKdsType)
                || UpdateKdsTypeEnum.SINGLE_BACKOUT.getValue().equals(updateKdsType)
                || UpdateKdsTypeEnum.SINGLE_DELETE.getValue().equals(updateKdsType)
                || UpdateKdsTypeEnum.ORDER_BACKOUT.getValue().equals(updateKdsType)
        ) && bsItemIds.size() == 0) {
            log.info("单品项操作未传品项！:" + jsonConverter.toJson(kdsUpdateCnd));
            throw new ApplicationError("S1.ERR.10020", "单品项操作未传品项！");
        }
        //  查询单据信息
        kdsOrderService.update(kdsUpdateCnd, shopId, updateKdsType, bsId, bsItemIds, modifierId, getCenterId());
        stopWatch.stop();
        log.info("kds本地数据更新接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }

    @ApiOperation(value = "初始化接口", notes = "初始化接口", httpMethod = "POST")
    @PostMapping(path = "/initKds")
    public ResponseEntity<ResponseResult<KdsInitVo>> initKds() {
        KdsInitVo kdsInitVo = new KdsInitVo();
        kdsInitVo.setPosId(getPosId());
        UserInfo userInfo = new UserInfo();
        userInfo.setBelongShopId(getShopId());
        userInfo.setCenterId(getCenterId());
        userInfo.setUserId(getEmpId());
        userInfo.setUserName(getEmpName());
        userInfo.setShopName(getShopName());
        kdsInitVo.setUserInfo(userInfo);
        kdsInitVo.setShopName(getShopName());
        List<PosHasSaleTypeVo> posHasSaleTypeVos = baseInterfaceService.queryPosSaleType(getPosId(), getShopId());
        if (CollectionUtils.isNotEmpty(posHasSaleTypeVos)) {
            kdsInitVo.setPosHasSaleTypeVos(posHasSaleTypeVos);
        }
        KitchenSettingVo kitchenSettingVo = baseInterfaceService.getKitchenSetting(getCenterId(), getShopId());
        kdsInitVo.setKitchenSettingVo(kitchenSettingVo);
        kdsInitVo.setBmPosSetPOList(Collections.singletonList(baseInterfaceService.queryPosSetting(getPosId(), getShopId())));
        return ResponseEntity.ok(ResponseResult.success(kdsInitVo));
    }


    @PreventDuplication(expireSeconds = 2)
    @ApiOperation(value = "kds本地数据更新", notes = "整单操作以及单品项操作以及已取餐", httpMethod = "POST")
    @PostMapping(path = "/batchUpdateItems")
    public ResponseEntity<ResponseResult> batchUpdateItems(@Valid @RequestBody KdsBatchUpdateCnd kdsBatchUpdateCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsBatchUpdateCnd,SceneTypeEnum.BATCH_UPDATE);
        log.info("kds批量划菜入参:" + jsonConverter.toJson(kdsBatchUpdateCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsBatchUpdateCnd.getShopId();
        Integer updateKdsType = kdsBatchUpdateCnd.getUpdateKdsType();
        List<KdsBatchUpdateItemsCnd> operItems = kdsBatchUpdateCnd.getBatchUpdateItems();
        if (Objects.isNull(shopId) || Objects.isNull(updateKdsType)) {
            throw new ApplicationError("S1.ERR.10020", "kds批量划菜入参校验失败！");
        }
        if (operItems.size() == 0) {
            throw new ApplicationError("S1.ERR.10020", "批量划菜操作未传品项！");
        }
        kdsOrderService.updateBatch(kdsBatchUpdateCnd, getCenterId());
        stopWatch.stop();
        log.info("kds批量划菜接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }

    @ApiOperation(value = "kds更新牌号", notes = "kds更新牌号", httpMethod = "POST")
    @PostMapping(path = "/updateOrderData")
    public ResponseEntity<ResponseResult> updateOrderData(@Valid @RequestBody KdsUpdateCnd kdsUpdateCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsUpdateCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("kds更新牌号:" + jsonConverter.toJson(kdsUpdateCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        kdsOrderService.updateOrderData(kdsUpdateCnd);
        stopWatch.stop();
        log.info("kds本地数据更新接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }

    @ApiOperation(value = "本地数据汇总接口", notes = "本地数据汇总接口", httpMethod = "POST")
    @PostMapping(path = "/queryTotal")
    public ResponseEntity<ResponseResult> queryTotal(@Valid @RequestBody KdsQueryCnd kdsQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryCnd,SceneTypeEnum.QUERY_TOTAL);
        log.info("kds汇总接口，入参为：{}", jsonConverter.toJson(kdsQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsTotalVo kdsTotalVo = new KdsTotalVo();
        if (kdsOrderService.queryTotal(kdsQueryCnd, kdsTotalVo, getCenterId()))
            return ResponseEntity.ok(ResponseResult.success(kdsTotalVo));
        stopWatch.stop();
        log.info("kds汇总接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsTotalVo));
    }

    @ApiOperation(value = "叫号功能", notes = "叫号", httpMethod = "POST")
    @PostMapping(path = "/callNumber")
    public ResponseEntity<ResponseResult> callNumber(@RequestBody KdsSendAndCallCnd kdsSendAndCallCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSendAndCallCnd,SceneTypeEnum.CALL_NUMBER);
        //  叫号更新本地数据
        log.info("kds叫号入参:" + jsonConverter.toJson(kdsSendAndCallCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsCallNumberVo kdsCallNumberVo = new KdsCallNumberVo();
        kdsSendAndCallCnd.setPosId(getPosId());
        kdsOrderService.callNumber(kdsSendAndCallCnd, kdsCallNumberVo, getCenterId());
        stopWatch.stop();
        log.info("kds叫号接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsCallNumberVo));
    }


    @ApiOperation(value = "扫码叫号功能", notes = "叫号", httpMethod = "POST")
    @PostMapping(path = "/scanCallNumber")
    public ResponseEntity<ResponseResult> scanCallNumber(@RequestBody KdsSendAndCallCnd kdsSendAndCallCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSendAndCallCnd,SceneTypeEnum.CALL_NUMBER);
        //  叫号更新本地数据
        log.info("扫码叫号功能入参:" + jsonConverter.toJson(kdsSendAndCallCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsOrderBo orderBo = new KdsOrderBo();
        orderBo.setShopId(getShopId());
        orderBo.setBsId(kdsSendAndCallCnd.getBsId());
        List<KdsOrder> orderList = kdsService.queryKdsOrder(orderBo);
        log.info("扫码叫号查询单据结果：{}", jsonConverter.toJson(orderList));
        KdsOrder kdsOrder = new KdsOrder();
        if (CollectionUtils.isNotEmpty(orderList)) {
            kdsOrder = orderList.get(0);
            KdsOrderItemBo kdsOrderItemBo = new KdsOrderItemBo();
            kdsOrderItemBo.setShopId(kdsOrder.getShopId());
            kdsOrderItemBo.setKdsOrderId(kdsOrder.getId());
            log.info("kds更新查询1：{}", jsonConverter.toJson(kdsOrderItemBo));
            List<cn.com.tcsl.fast.kds.server.model.KdsOrderItem> kdsOrderItems = kdsService.queryKdsOrderItems(kdsOrderItemBo);
            FifterKdsOrderItem fifterKdsOrderItem = kdsService.fifterKdsOrderItem(Collections.singletonList(kdsSendAndCallCnd.getPosId()), getShopId(), getCenterId(), kdsOrderItems);
            Map<Long, List<cn.com.tcsl.fast.kds.server.model.KdsOrderItem>> targetMap;
            targetMap = fifterKdsOrderItem.getKdsOrderItemMap();
            List<KdsOrderItem> targetList = targetMap.get(getPosId());
            log.info("扫码叫号过滤后结果：{}", jsonConverter.toJson(targetList));
            if (CollectionUtils.isEmpty(targetList)) {
                throw new ApplicationError("S1.ERR.10020", "订单品项不在当前KDS方案内");
            }
        }
        KdsCallNumberVo kdsCallNumberVo = new KdsCallNumberVo();
        kdsSendAndCallCnd.setPosId(getPosId());
        kdsOrderService.callNumber(kdsSendAndCallCnd, kdsCallNumberVo, getCenterId());
        stopWatch.stop();
        log.info("扫码叫号功能接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsCallNumberVo));
    }

    @ApiOperation(value = "初始化取餐屏推送", notes = "初始化取餐屏推送", httpMethod = "POST")
    @PostMapping(path = "/initPush")
    public ResponseEntity<ResponseResult> initPush(@RequestBody KdsSendAndCallCnd kdsSendAndCallCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSendAndCallCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("初始化取餐屏推送:" + jsonConverter.toJson(kdsSendAndCallCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsSendAndCallCnd.getShopId();
        if (Objects.isNull(shopId)) {
            log.info("初始化取餐屏推送入参校验失败！:" + jsonConverter.toJson(kdsSendAndCallCnd));
            throw new ApplicationError("S1.ERR.10020", "初始化取餐屏推送入参校验失败！");
        }
        Boolean flag = kdsSettingService.appreciationCheck(shopId, KdsConstant.APPRECIATION_KDS);
        if (Boolean.FALSE.equals(flag)) {
            pushService.initPush(shopId);
        } else {
            pushService.pushFinishOrder(shopId, null, null, kdsSendAndCallCnd.getVersionFlag(), getCenterId(), 0L);
        }
        stopWatch.stop();
        log.info("初始化取餐屏推送接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }

    @ApiOperation(value = "获取门店所有方案列表", notes = "获取门店所有方案列表", httpMethod = "POST")
    @PostMapping(path = "/kdsPlanList")
    public ResponseEntity<ResponseResult> kdsPlanList(@RequestBody KdsBaseCnd kdsBaseCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsBaseCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("获取门店所有方案列表:" + jsonConverter.toJson(kdsBaseCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsBaseCnd.getShopId();
        Integer centerId = kdsBaseCnd.getCenterId();
        if (Objects.isNull(shopId) || Objects.isNull(centerId)) {
            log.info("获取门店所有方案列表入参校验失败！:" + jsonConverter.toJson(kdsBaseCnd));
            throw new ApplicationError("S1.ERR.10020", "获取门店所有方案列表入参校验失败！");
        }
        List<KitchenItemPlanVo> kitchenItemPlanVoList = kdsService.kdsPlanList(shopId, centerId);
        stopWatch.stop();
        log.info("获取门店所有方案列表接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kitchenItemPlanVoList));
    }

    @PreventDuplication(expireSeconds = 2)
    @ApiOperation(value = "发餐功能", notes = "发餐功能 = 更新状态+叫号", httpMethod = "POST")
    @PostMapping(path = "/sendOrder")
    public ResponseEntity<ResponseResult> sendOrder(@RequestBody KdsSendAndCallCnd kdsSendAndCallCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSendAndCallCnd,SceneTypeEnum.SEND_ORDER);
        //  叫号更新本地数据
        log.info("kds发餐功能入参:" + jsonConverter.toJson(kdsSendAndCallCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsSendAndCallCnd.getShopId();
        Long bsId = kdsSendAndCallCnd.getBsId();
        Long modifierId = kdsSendAndCallCnd.getModifierId();
        if (Objects.isNull(shopId) || Objects.isNull(bsId)) {
            log.info("kds发餐功能入参校验失败！:" + jsonConverter.toJson(kdsSendAndCallCnd));
            throw new ApplicationError("S1.ERR.10020", "kds发餐功能入参校验失败！");
        }
        String lockStr = "店铺:" + shopId + "开始处理发餐";
        //  开始处理发餐
        RLock lock = null;
        try {
            lock = redissonClient.getLock(lockStr);
            lock.lock(3, TimeUnit.SECONDS);
        kdsOrderService.saveSendOrder(kdsSendAndCallCnd, shopId, bsId, modifierId, getCenterId());
        } finally {
            if (lock != null) {
                try {
                    lock.unlock();
                } catch (Exception ignored) {
                }
            }
        }
        stopWatch.stop();
        log.info("kds发餐接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    @ApiOperation(value = "扫码发餐", notes = "扫码发餐", httpMethod = "POST")
    @PostMapping(path = "/scanSendOrder")
    public ResponseEntity<ResponseResult> scanSendOrder(@RequestBody KdsScanSendOrderCnd kdsScanSendOrderCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsScanSendOrderCnd,SceneTypeEnum.SEND_ORDER);
        //  叫号更新本地数据
        log.info("扫码发餐入参:" + jsonConverter.toJson(kdsScanSendOrderCnd));
        KdsScanSendOrderVo kdsScanSendOrderVo = new KdsScanSendOrderVo();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsScanSendOrderCnd.getShopId();
        String code = kdsScanSendOrderCnd.getCode();
        Integer codeType = kdsScanSendOrderCnd.getCodeType();
        if (Objects.isNull(shopId) || Objects.isNull(code)) {
            log.info("扫码发餐入参校验失败！:" + jsonConverter.toJson(kdsScanSendOrderCnd));
            kdsScanSendOrderVo.setCode("S1.ERR.10020");
            kdsScanSendOrderVo.setMsg("扫码发餐入参校验失败！");
            return ResponseEntity.ok(ResponseResult.success(kdsScanSendOrderVo));
        }
        KdsSendAndCallCnd kdsSendAndCallCnd = new KdsSendAndCallCnd();
        kdsSendAndCallCnd.setVersionFlag(1);
        kdsSendAndCallCnd.setShopId(shopId);
        kdsSendAndCallCnd.setPosId(999L);
        if (codeType == 0) {
            String pattern = "^QC.*_$";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(code);
            if (!m.matches()) {
                kdsScanSendOrderVo.setCode("S1.ERR.20005");
                kdsScanSendOrderVo.setMsg("扫码发餐线下码不合法！");
                return ResponseEntity.ok(ResponseResult.success(kdsScanSendOrderVo));
            }
            String bsId = code.substring(code.indexOf("_") + 1, code.length() - 1);
            kdsSendAndCallCnd.setBsId(Long.parseLong(bsId));
        }
        if (codeType == 1) {
            String onlineCodepattern = "^YY.*\\d$";
            Pattern p1 = Pattern.compile(onlineCodepattern);
            Matcher m1 = p1.matcher(code);


            String onlineZTCodepattern = "\\d{1,63}";
            Pattern p2 = Pattern.compile(onlineZTCodepattern);
            Matcher m2 = p2.matcher(code);
            if (!m1.matches()&&!m2.matches()) {
                kdsScanSendOrderVo.setCode("S1.ERR.20006");
                kdsScanSendOrderVo.setMsg("扫码发餐吾享线上码不合法！");
                return ResponseEntity.ok(ResponseResult.success(kdsScanSendOrderVo));
            }
            if(m1.matches()){
                kdsSendAndCallCnd.setYyCode(code);
            }else if(m2.matches()){
                kdsSendAndCallCnd.setDeNo(code);
            }


        }
        kdsScanSendOrderVo = kdsOrderService.saveSendOrder(kdsSendAndCallCnd, shopId, kdsSendAndCallCnd.getBsId(), getPosId(), getCenterId());
        stopWatch.stop();
        log.info("扫码发餐接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsScanSendOrderVo));
    }


    @ApiOperation(value = "已传菜品列表接口(旧版本包含统计下单数量、退单数量、完成数量)", notes = "已传菜品列表接口", httpMethod = "POST")
    @PostMapping(path = "/kdsOrderList")
    public ResponseEntity<ResponseResult<KdsOrderListVo>> kdsOrderList(@RequestBody KdsOrderListCnd kdsOrderListCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsOrderListCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("已传菜品列表入参:" + jsonConverter.toJson(kdsOrderListCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Page<Object> objects = PageHelper.startPage(kdsOrderListCnd.getPage(), kdsOrderListCnd.getLimit());
        KdsOrderListBo orderListBo = new KdsOrderListBo();
        BeanCopy.beans(kdsOrderListCnd, orderListBo).copy();
        List<KdsOrderList> orderListVos = kdsService.queryKdsOrderList(orderListBo);
        KdsOrderListVo kdsOrderListVo = new KdsOrderListVo();
        kdsOrderListVo.setKdsOrderList(orderListVos);
        kdsOrderListVo.setPage(kdsOrderListCnd.getPage());
        kdsOrderListVo.setLimit(kdsOrderListCnd.getLimit());
        kdsOrderListVo.setCount((int) objects.getTotal());
        stopWatch.stop();
        log.info("已传菜品列表返回:" + jsonConverter.toJson(orderListVos));
        log.info("已传菜品列表接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsOrderListVo));
    }


    @ApiOperation(value = "已传菜品详情", notes = "已传菜品详情", httpMethod = "POST")
    @PostMapping(path = "/kdsOrderDetail")
    public ResponseEntity<ResponseResult<KdsOrderDetailVo>> kdsOrderDetail(@RequestBody KdsOrderDetailCnd kdsOrderDetailCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsOrderDetailCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("kds已传菜品详情，入参为：{}", jsonConverter.toJson(kdsOrderDetailCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        if (Objects.isNull(kdsOrderDetailCnd.getKdsOrderId())) {
            log.info("kds已传菜品详情入参校验失败！:" + jsonConverter.toJson(kdsOrderDetailCnd));
            throw new ApplicationError("S1.ERR.10020", "kds已传菜品详情入参校验失败！");
        }
        //  返回值
        KdsOrderDetailVo kdsOrderDetailVo = new KdsOrderDetailVo();
        List<KdsQueryOrder> kdsQueryOrderList = new ArrayList<>();
        if (kdsOrderService.queryKdsOrderDetail(kdsOrderDetailCnd, kdsOrderDetailVo, kdsQueryOrderList))
            return ResponseEntity.ok(ResponseResult.success(kdsOrderDetailVo));
        kdsOrderDetailVo.setKdsQueryOrderList(kdsQueryOrderList);
        stopWatch.stop();
        log.info("kds已传菜品详情结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsOrderDetailVo));

    }


    @ApiOperation(value = "kds合并语音接口", notes = "kds合并语音接口", httpMethod = "POST")
    @PostMapping(path = "/mergeAudio")
    public ResponseEntity<ResponseResult> mergeAudio(@RequestBody KdsMergeAudioCnd kdsMergeAudioCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsMergeAudioCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("kds合并语音接口，入参为：{}", jsonConverter.toJson(kdsMergeAudioCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsMergeAudioCnd.getShopId();
        String tableNumber = kdsMergeAudioCnd.getTableNumber();
        if (Objects.isNull(shopId) || Objects.isNull(tableNumber)) {
            log.info("kds合并语音接口入参校验失败！:" + jsonConverter.toJson(kdsMergeAudioCnd));
            throw new ApplicationError("S1.ERR.10020", "kds合并语音接口入参校验失败！");
        }
        String url = kdsOrderService.mergeAudio(shopId, tableNumber);
        stopWatch.stop();
        log.info("kds合并语音接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(url));
    }


    @ApiOperation(value = "kds查询单据状态接口", notes = "kds查询单据状态接口", httpMethod = "POST")
    @PostMapping(path = "/queryKdsOrder")
    public ResponseEntity<ResponseResult<KdsQueryVo>> queryKdsOrder(@RequestBody KdsQueryOrderCnd kdsQueryOrderCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryOrderCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("kds查询单据状态接口，入参为：{}", jsonConverter.toJson(kdsQueryOrderCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsQueryOrderCnd.getShopId();
        List<Long> bsIds = kdsQueryOrderCnd.getBsIds();
        if (Objects.isNull(shopId) || CollectionUtils.isEmpty(bsIds)) {
            log.info("kds查询单据状态接口入参校验失败！:" + jsonConverter.toJson(kdsQueryOrderCnd));
            throw new ApplicationError("S1.ERR.10020", "kds查询单据状态接口入参校验失败！");
        }
        KdsOrderBo orderBo = new KdsOrderBo();
        orderBo.setShopId(shopId);
        orderBo.setBsIds(bsIds);
        List<KdsOrder> orderList = kdsService.queryKdsOrder(orderBo);
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        if (CollectionUtils.isNotEmpty(orderList)) {
            List<KdsQueryOrder> kdsQueryOrders = new ArrayList<>();
            for (KdsOrder kdsOrder : orderList) {
                KdsQueryOrder kdsQueryOrder = new KdsQueryOrder();
                kdsQueryOrder.setStatusMessage(StatusEnum.getEnumByValue(kdsOrder.getStatus()).getMessage());
                kdsQueryOrder.setBsId(kdsOrder.getBsId());
                kdsQueryOrder.setStatus(kdsOrder.getStatus());
                kdsQueryOrders.add(kdsQueryOrder);
            }
            kdsQueryVo.setKdsQueryOrderList(kdsQueryOrders);
        }
        stopWatch.stop();
        log.info("kds查询单据状态接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }


    private void handleRequest(Integer currentShopId, Long posId, Object o, SceneTypeEnum sceneTypeEnum) {
        String jsonString = jsonConverter.toJson(o);
        log.info("handleRequest,shopId：{}，posId：{}，Object：{}", currentShopId, posId, jsonConverter.toJson(o));
        Map paramsMap = jsonConverter.fromJson(jsonString, Map.class);
        if (Objects.isNull(paramsMap.get("shopId"))) {
            paramsMap.put("shopId", currentShopId);
        }
        if (Objects.isNull(paramsMap.get("posId"))) {
            paramsMap.put("posId", posId);
        }
        if(!SceneTypeEnum.UNKNOWN_SCENE.equals(sceneTypeEnum)){
            BeanUtil.copyProperties(paramsMap, o);
            eventPublisher.publish(new KdsOperationEvent(this, currentShopId, o,sceneTypeEnum, TLogContext.getTraceId()));
        }
    }


    @ApiOperation(value = "kds叫号列表接口", notes = "kds叫号列表接口", httpMethod = "POST")
    @PostMapping(path = "/callOrderList")
    public ResponseEntity<ResponseResult<KdsQueryVo>> callOrderList(@RequestBody KdsQueryOrderCnd kdsQueryOrderCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryOrderCnd,SceneTypeEnum.UNKNOWN_SCENE);
        log.info("kds查询单据状态接口，入参为：{}", jsonConverter.toJson(kdsQueryOrderCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        kdsOrderService.callOrderList(kdsQueryOrderCnd, kdsQueryVo, getCenterId(), getShopId());
        stopWatch.stop();
        log.info("kds查询单据状态接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }


}
