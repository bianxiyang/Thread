package cn.com.tcsl.fast.kds.server.controller;

import cn.com.tcsl.fast.base.vo.biz.KitchenItemPlanVo;
import cn.com.tcsl.fast.base.vo.biz.KitchenItemVo;
import cn.com.tcsl.fast.common.constant.CacheKeyConstant;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.framework.controller.GenericController;
import cn.com.tcsl.fast.framework.exception.ApplicationError;
import cn.com.tcsl.fast.kds.cnd.KdsSettingCnd;
import cn.com.tcsl.fast.kds.cnd.KdsSettingQueryCnd;
import cn.com.tcsl.fast.kds.cnd.SaveSetting;
import cn.com.tcsl.fast.kds.server.constant.KdsConstant;
import cn.com.tcsl.fast.kds.server.enums.KdsParamEnum;
import cn.com.tcsl.fast.kds.server.model.KdsParam;
import cn.com.tcsl.fast.kds.server.model.KdsSetting;
import cn.com.tcsl.fast.kds.server.service.KdsParamService;
import cn.com.tcsl.fast.kds.server.service.KdsSettingService;
import cn.com.tcsl.fast.kds.vo.KdsLaneVo;
import cn.com.tcsl.fast.kds.vo.KdsParamVo;
import cn.com.tcsl.fast.kds.vo.KdsPosSettingPlanVo;
import cn.com.tcsl.fast.redisson.util.RedissonUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.swagger.annotations.ApiOperation;
import jodd.bean.BeanCopy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * <b>功能：kds个性化设置</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/3/23 13:10&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
@RestController
@RequestMapping(value = "${spring.mvc.prefix}/kdsSetting")
public class KdsSettingController extends GenericController {

    @Resource
    private KdsSettingService kdsSettingService;
    @Resource
    private KdsParamService kdsParamService;
    @Resource
    private RedissonClient redissonClient;


    @ApiOperation(value = "获取所有kds配置内容", notes = "获取所有kds配置内容", httpMethod = "POST")
    @PostMapping(path = "/kdsParamList")
    public ResponseEntity<ResponseResult> kdsParamList() {
        StopWatch stopWatch = new StopWatch();
        log.info("获取所有kds配置内容接口开始");
        stopWatch.start();
        List<KdsParam> paramList = kdsParamService.getParamList();
        List<KdsParamVo> kdsParamVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(paramList)) {
            paramList.forEach(kdsParam -> {
                KdsParamVo kdsParamVo = new KdsParamVo();
                BeanUtil.copyProperties(kdsParam, kdsParamVo);
                kdsParamVo.setKdsParamEnum(KdsParamEnum.getEnumByValue(kdsParamVo.getKey()).getMessage());
                kdsParamVos.add(kdsParamVo);
            });
        }
        stopWatch.stop();
        log.info("获取所有kds配置内容接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsParamVos));
    }


    @ApiOperation(value = "保存kds个性化设置", notes = "保存kds个性化设置", httpMethod = "POST")
    @PostMapping(path = "/saveSetting")
    @Transactional
    public ResponseEntity<ResponseResult> saveSetting(@Valid @RequestBody KdsSettingCnd kdsSettingCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSettingCnd);
        log.info("保存kds个性化设置入参:" + jsonConverter.toJson(kdsSettingCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Integer shopId = kdsSettingCnd.getShopId();
        Long posId = kdsSettingCnd.getPosId();

        List<SaveSetting> settingList = kdsSettingCnd.getSettingList();
        if (CollectionUtils.isEmpty(settingList)) {
            log.info("保存kds个性化设置未传设置项！:" + jsonConverter.toJson(settingList));
            throw new ApplicationError("S1.ERR.10020", "保存kds个性化设置未传设置项！");
        }
        if (CollectionUtils.isNotEmpty(settingList)) {
            List<Object> keys = settingList.stream().map(SaveSetting::getKey).collect(Collectors.toList());
            if (keys.contains(null)) {
                throw new ApplicationError("S1.ERR.10020", "保存kds个性化设置未传设置项！");
            }
        } else {
            throw new ApplicationError("S1.ERR.10020", "保存kds个性化设置未传设置项！");
        }
        for (SaveSetting saveSetting : settingList) {
            KdsSetting kdsSetting = new KdsSetting();
            kdsSetting.setShopId(shopId);
            kdsSetting.setValue(saveSetting.getValue());
            kdsSetting.setPosId(posId);
            if (Objects.equals(KdsConstant.CALL_BRAND_NUMBER, saveSetting.getKey()) || Objects.equals(KdsConstant.CALL_TABLE_NUMBER, saveSetting.getKey())
                    || Objects.equals(KdsConstant.OUT_TIME_SETTING, saveSetting.getKey())
            ) {
                kdsSetting.setPosId(0L);
            }
            kdsSetting.setKey(saveSetting.getKey());
            KdsSetting settingResult = kdsSettingService.selectByPrimaryKey(kdsSetting);
            if (Objects.nonNull(settingResult)) {
                settingResult.setShopId(shopId);
                settingResult.setKey(saveSetting.getKey());
                settingResult.setValue(saveSetting.getValue());
                if (Objects.equals(KdsConstant.CALL_BRAND_NUMBER, saveSetting.getKey()) || Objects.equals(KdsConstant.CALL_TABLE_NUMBER, saveSetting.getKey())
                        || Objects.equals(KdsConstant.OUT_TIME_SETTING, saveSetting.getKey())
                ) {
                    settingResult.setPosId(0L);
                } else {
                    settingResult.setPosId(posId);
                }
                settingResult.setModifyTime(new Date());
                settingResult.setModifierId(kdsSettingCnd.getCreatorId());
                int num = kdsSettingService.deleteByPrimaryKey(settingResult);
                if (num > 0) {
                    kdsSetting.setCreateTime(new Date());
                    kdsSetting.setCreatorId(kdsSettingCnd.getCreatorId());
                    kdsSetting.setModifyTime(new Date());
                    kdsSetting.setModifierId(kdsSettingCnd.getCreatorId());
                    kdsSettingService.insertSelective(kdsSetting);
                }
            } else {
                kdsSetting.setCreateTime(new Date());
                kdsSetting.setCreatorId(kdsSettingCnd.getCreatorId());
                kdsSetting.setModifyTime(new Date());
                kdsSetting.setModifierId(kdsSettingCnd.getCreatorId());
                kdsSettingService.insertSelective(kdsSetting);
            }
        }
        //  保存配置后，删除redis缓存
        String redisKey = CacheKeyConstant.CACHE_TEMP + "kdsSetting:" + shopId + "_" + posId;
        RedissonUtils.delByKeys(redissonClient, redisKey);
        stopWatch.stop();
        log.info("保存kds个性化设置接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success());
    }


    @ApiOperation(value = "查询指定pos的个性化设置", notes = "查询指定pos的个性化设置", httpMethod = "POST")
    @PostMapping(path = "/posSettingQuery")
    public ResponseEntity<ResponseResult> posSettingQuery(@Valid @RequestBody KdsSettingQueryCnd kdsSettingQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSettingQueryCnd);
        log.info("查询指定pos的个性化设置入参:" + jsonConverter.toJson(kdsSettingQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Map<String, Object> param = new HashMap<>();
        BeanCopy.beans(kdsSettingQueryCnd, param).copy();
        List<KdsSetting> returnSetting = new ArrayList<>();
        List<KdsSetting> settingList = kdsSettingService.queryByParam(param);
        if (CollectionUtils.isNotEmpty(settingList)) {
            Map<Long, List<KdsSetting>> groupSettingMap = settingList.stream().collect(groupingBy(KdsSetting::getKey));
            for (Map.Entry<Long, List<KdsSetting>> entry : groupSettingMap.entrySet()) {
                Long key = entry.getKey();
                List<KdsSetting> settings = entry.getValue();
                if (Objects.equals(KdsConstant.CALL_BRAND_NUMBER, key) || Objects.equals(KdsConstant.CALL_TABLE_NUMBER, key)
                        || Objects.equals(KdsConstant.OUT_TIME_SETTING, key)) {
                    settings.forEach(kdsSetting -> {
                        if (settings.size() == 1) {
                            kdsSetting.setKdsParamEnum(KdsParamEnum.getEnumByValue(kdsSetting.getKey()).getMessage());
                            returnSetting.add(kdsSetting);
                        } else {
                            if (kdsSetting.getPosId().equals(0L)) {
                                kdsSetting.setKdsParamEnum(KdsParamEnum.getEnumByValue(kdsSetting.getKey()).getMessage());
                                returnSetting.add(kdsSetting);
                            }
                        }
                    });
                } else {
                    settings.forEach(kdsSetting -> {
                        kdsSetting.setKdsParamEnum(KdsParamEnum.getEnumByValue(kdsSetting.getKey()).getMessage());
                        returnSetting.add(kdsSetting);
                    });
                }
            }
        }
        stopWatch.stop();
        log.info("查询指定pos的个性化设置接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(returnSetting));
    }


    @ApiOperation(value = "查询指定pos的后台方案", notes = "查询指定pos的后台方案", httpMethod = "POST")
    @PostMapping(path = "/posSettingPlan")
    public ResponseEntity<ResponseResult> posSettingPlan(@Valid @RequestBody KdsSettingQueryCnd kdsSettingQueryCnd) {
        this.handleRequest(getShopId(), getPosId(), kdsSettingQueryCnd);
        log.info("查询指定pos的后台方案入参:" + jsonConverter.toJson(kdsSettingQueryCnd));
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        KdsPosSettingPlanVo kdsPosSettingPlanVo = new KdsPosSettingPlanVo();
        Map<String, Object> param = new HashMap<>();
        kdsSettingQueryCnd.setCreateShopId(getCenterId());
        param.put("shopId", getShopId());
        param.put("createShopId", getCenterId());
        List<Long> posIds = new ArrayList<>();
        posIds.add(kdsSettingQueryCnd.getPosId());
        param.put("posIds", posIds);
        log.info("查询指定pos的后台方案param:" + jsonConverter.toJson(param));
        KitchenItemPlanVo kitchenItemPlanVo = kdsSettingService.posSettingPlan(param);
        log.info("厨房配置:" + jsonConverter.toJson(kitchenItemPlanVo));
        if (Objects.nonNull(kitchenItemPlanVo)) {
            Boolean enableNewLane = kitchenItemPlanVo.getEnableNewLane();
            if (Objects.nonNull(enableNewLane)) {
                kdsPosSettingPlanVo.setEnableNewLane(kitchenItemPlanVo.getEnableNewLane());
            }
            Integer settingMode = kitchenItemPlanVo.getSettingMode();
            List<KitchenItemVo> itemList = kitchenItemPlanVo.getItemList();
            Map<Long, List<KitchenItemVo>> laneMap = new HashMap<>();
            if (settingMode == 0 && CollectionUtils.isNotEmpty(itemList)) {
                itemList = itemList.stream().filter(kitchenItemVo -> Objects.nonNull(kitchenItemVo.getLaneId())).collect(Collectors.toList());
                laneMap = itemList.stream().collect(Collectors.groupingBy(KitchenItemVo::getLaneId));
            }
            List<KitchenItemVo> classList = kitchenItemPlanVo.getClassList();
            if (settingMode == 1 && CollectionUtils.isNotEmpty(classList)) {
                classList = classList.stream().filter(kitchenItemVo -> Objects.nonNull(kitchenItemVo.getLaneId())).collect(Collectors.toList());
                laneMap = classList.stream().collect(Collectors.groupingBy(KitchenItemVo::getLaneId));
            }
            kdsPosSettingPlanVo.setLaneNum(laneMap.size());
            List<KdsLaneVo> kdsLaneVoList = new ArrayList<>();
            for (Map.Entry<Long, List<KitchenItemVo>> entry : laneMap.entrySet()) {
                KdsLaneVo kdsLaneVo = new KdsLaneVo();
                kdsLaneVo.setLaneId(entry.getKey());
                kdsLaneVo.setLaneName(entry.getValue().get(0).getLaneName());
                kdsLaneVoList.add(kdsLaneVo);
            }
            kdsLaneVoList.sort((o1, o2) -> (int) (o1.getLaneId() - o2.getLaneId()));
            kdsPosSettingPlanVo.setKdsLaneVoList(kdsLaneVoList);
        }
        stopWatch.stop();
        log.info("查询指定pos的后台方案接口结束{}ms", stopWatch.getTime(TimeUnit.MILLISECONDS));
        return ResponseEntity.ok(ResponseResult.success(kdsPosSettingPlanVo));
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
