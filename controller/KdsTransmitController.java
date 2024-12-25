package cn.com.tcsl.fast.kds.server.controller;


import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.framework.controller.GenericController;
import cn.com.tcsl.fast.framework.exception.ApplicationError;
import cn.com.tcsl.fast.kds.cnd.*;
import cn.com.tcsl.fast.kds.server.enums.UpdateKdsTypeEnum;
import cn.com.tcsl.fast.kds.server.service.KdsTransmitService;
import cn.com.tcsl.fast.kds.vo.KdsQueryVo;
import cn.hutool.core.bean.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * <b>功能：</b>kds传菜controller<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/1/2 15:27&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */


@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/kdsTransmit")
public class KdsTransmitController extends GenericController {


    @Resource
    private KdsTransmitService kdsTransmitService;

    /**
     * <b>功能：</b>待传配列表查询<br>
     * <b>Copyright TCSL</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/1/2 15:30&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     *
     * </ul>
     */
    @PostMapping(path = "/transmitQuery")
    public ResponseEntity<ResponseResult<KdsQueryVo>> transmitQuery(@Valid @RequestBody KdsQueryCnd kdsQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryCnd);
        log.info("kds待传配查询，入参为：{}", jsonConverter.toJson(kdsQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        kdsTransmitService.kdsTransmitQuery(kdsQueryCnd, kdsQueryVo, getCenterId(), getShopId());
        stopWatch.stop();
        log.info("kds待传配查询结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }

    /**
     * <b>功能：</b>已传配查询接口<br>
     * <b>Copyright TCSL</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/1/2 15:33&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     *
     * </ul>
     */

    @PostMapping(path = "/finishTransmitQuery")
    public ResponseEntity<ResponseResult<KdsQueryVo>> finishTransmitQuery(@Valid @RequestBody KdsQueryCnd kdsQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsQueryCnd);
        log.info("已传配查询，入参为：{}", jsonConverter.toJson(kdsQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        //  返回值
        KdsQueryVo kdsQueryVo = new KdsQueryVo();
        kdsTransmitService.finishTransmitQuery(kdsQueryCnd, kdsQueryVo, getCenterId());
        stopWatch.stop();
        log.info("已传配查询结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsQueryVo));
    }


    /**
     * <b>功能：</b>整单传配接口<br>
     * <b>Copyright TCSL</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/4/11 15:54&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     *
     * </ul>
     */
    @PostMapping(path = "/transmitOrder")
    public ResponseEntity<ResponseResult> transmitOrder(@RequestBody KdsSendAndCallCnd kdsSendAndCallCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSendAndCallCnd);
        log.info("kds整单传配功能入参:" + jsonConverter.toJson(kdsSendAndCallCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Long bsId = kdsSendAndCallCnd.getBsId();
        Long modifierId = kdsSendAndCallCnd.getModifierId();
        if (Objects.isNull(bsId)) {
            throw new ApplicationError("S1.ERR.10020", "kds整单传配功能入参校验失败！");
        }
        kdsTransmitService.transmitOrder(kdsSendAndCallCnd, getShopId(), getCenterId(), bsId, modifierId);
        stopWatch.stop();
        log.info("kds整单传配接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    /**
     * <b>功能：</b>单品传菜和撤销<br>
     * <b>Copyright TCSL</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/4/12 14:26&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     *
     * </ul>
     */
    @PostMapping(path = "/transmit")
    public ResponseEntity<ResponseResult> transmit(@Valid @RequestBody KdsUpdateCnd kdsUpdateCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsUpdateCnd);
        log.info("单品传菜和撤销入参:" + jsonConverter.toJson(kdsUpdateCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsUpdateCnd.getShopId();
        Integer updateKdsType = kdsUpdateCnd.getUpdateKdsType();
        Long bsId = kdsUpdateCnd.getBsId();
        List<Long> bsItemIds = kdsUpdateCnd.getBsItemIds();
        Long modifierId = kdsUpdateCnd.getModifierId();
        if (Objects.isNull(shopId) || Objects.isNull(updateKdsType) || Objects.isNull(bsId)) {
            throw new ApplicationError("S1.ERR.10020","单品传菜和撤销入参校验失败！");
        }
        if ((UpdateKdsTypeEnum.SINGELE_FINISHED.getValue().equals(updateKdsType)
                || UpdateKdsTypeEnum.SINGLE_BACKOUT.getValue().equals(updateKdsType)
                || UpdateKdsTypeEnum.SINGLE_DELETE.getValue().equals(updateKdsType)
                || UpdateKdsTypeEnum.ORDER_TRANSMIT_BACKOUT.getValue().equals(updateKdsType)
        ) && bsItemIds.size() == 0) {
            throw new ApplicationError("S1.ERR.10020","单品传菜和撤销未传品项！");
        }
        kdsTransmitService.transmit(kdsUpdateCnd, shopId, updateKdsType, bsId, bsItemIds, modifierId, getCenterId());
        stopWatch.stop();
        log.info("单品传菜和撤销接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    /**
     * <b>功能：</b>批量传配<br>
     * <b>Copyright TCSL</b>
     * <ul>
     * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
     * <hr>
     * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/1/22 10:38&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
     *
     * </ul>
     */
    @PostMapping(path = "/batchTransmitItems")
    public ResponseEntity<ResponseResult> batchTransmitItems(@Valid @RequestBody KdsBatchUpdateCnd kdsBatchUpdateCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsBatchUpdateCnd);
        log.info("kds批量传配入参:" + jsonConverter.toJson(kdsBatchUpdateCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsBatchUpdateCnd.getShopId();
        Integer updateKdsType = kdsBatchUpdateCnd.getUpdateKdsType();
        List<KdsBatchUpdateItemsCnd> operItems = kdsBatchUpdateCnd.getBatchUpdateItems();
        if (Objects.isNull(shopId) || Objects.isNull(updateKdsType)) {
            throw new ApplicationError("S1.ERR.10020","kds批量传配入参校验失败！");
        }
        if (operItems.size() == 0) {
            throw new ApplicationError("S1.ERR.10020","批量传配操作未传品项！");
        }
        kdsTransmitService.batchTransmitItems(kdsBatchUpdateCnd, getCenterId());
        stopWatch.stop();
        log.info("kds批量传配接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }

    private void handleRequest(Integer currentShopId, Long posId, Object o) {
        String jsonString = jsonConverter.toJson(o);
        log.info("handleRequest,shopId：{}，posId：{}，Object：{}", currentShopId, posId, jsonConverter.toJson(o));
        Map paramsMap = jsonConverter.fromJson(jsonString, Map.class);
        if (Objects.isNull(paramsMap.get("shopId"))) {
            paramsMap.put("shopId", currentShopId);
        }
        if (Objects.isNull(paramsMap.get("posId"))) {
            paramsMap.put("posId", posId);
        }
        BeanUtil.copyProperties(paramsMap, o);
    }

}
