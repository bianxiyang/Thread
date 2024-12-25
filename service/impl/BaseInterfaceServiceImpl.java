package cn.com.tcsl.fast.kds.server.service.impl;

import cn.com.tcsl.fast.base.client.BaseForKdsClient;
import cn.com.tcsl.fast.base.dto.arch.KdsItemDto;
import cn.com.tcsl.fast.base.dto.arch.OrderItemDto;
import cn.com.tcsl.fast.base.dto.biz.McIdDto;
import cn.com.tcsl.fast.base.dto.device.CallingScreenParamDto;
import cn.com.tcsl.fast.base.dto.device.CallingScreenSettingDto;
import cn.com.tcsl.fast.base.dto.pos.PosDto;
import cn.com.tcsl.fast.base.dto.pos.PosHasSaleTypeDto;
import cn.com.tcsl.fast.base.dto.pos.PosSettingDto;
import cn.com.tcsl.fast.base.dto.shop.ShopDto;
import cn.com.tcsl.fast.base.vo.arch.OrderItemVo;
import cn.com.tcsl.fast.base.vo.biz.KitchenItemPlanOverAllVo;
import cn.com.tcsl.fast.base.vo.biz.KitchenPlanByPosVo;
import cn.com.tcsl.fast.base.vo.biz.KitchenPosInfoByItemsVo;
import cn.com.tcsl.fast.base.vo.pos.PosHasSaleTypeVo;
import cn.com.tcsl.fast.base.vo.shop.ShopInfoVo;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.common.util.JSONUtil;
import cn.com.tcsl.fast.common.util.ResponseEntityUtil;
import cn.com.tcsl.fast.framework.auth.ApplicationConstant;
import cn.com.tcsl.fast.framework.service.GenericBaseService;
import cn.com.tcsl.fast.kds.server.service.BaseInterfaceService;
import cn.com.tcsl.fast.kds.server.util.RestClient;
import cn.com.tcsl.fast.kds.vo.BasePosSettingVo;
import cn.com.tcsl.fast.kds.vo.KitchenSettingVo;
import cn.com.tcsl.fast.redisson.util.RedissonUtils;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@Slf4j
public class BaseInterfaceServiceImpl extends GenericBaseService implements BaseInterfaceService {

    @Resource
    private BaseForKdsClient baseForKdsClient;
    @Resource
    private RedissonClient redissonClient;


    @Resource(name = "archRedissonClient")
    private RedissonClient baseArchRedissonClient;

    @Override
    public ShopInfoVo getCenterShopInfoVo(Integer shopId) {
        String redisKey = ApplicationConstant.redis_cy7server + shopId;
        RBucket<Map<String, Object>> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists() && Objects.nonNull(bucket.get().get("getshopinfoformcid"))) {
            //  缓存存在
            ShopInfoVo shopInfoVo = (ShopInfoVo) bucket.get().get("getshopinfoformcid");
            log.info("获取门店信息-redis缓存：{}", JSONUtil.toStr(shopInfoVo));
            return shopInfoVo;
        }
        McIdDto mcIdDto = new McIdDto();
        mcIdDto.setShopId(shopId);
        ResponseEntity<ResponseResult<ShopInfoVo>> responseEntity = baseForKdsClient.getShopInfoForMcId(mcIdDto);
        return ResponseEntityUtil.getResponseData(responseEntity);
    }

    @Override
    public KitchenSettingVo getKitchenSetting(Integer centerId, Integer shopId) {
        KitchenSettingVo kitchenSettingVo = new KitchenSettingVo();
        String key = "getmodesetting" + 100801;
        String redisKey = ApplicationConstant.redis_cy7center + shopId;
        RBucket<Map<String, Object>> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists() && Objects.nonNull(bucket.get().get(key))) {
            //  缓存存在
            Map<String, Object> map = (Map<String, Object>) bucket.get().get(key);
            BeanUtil.copyProperties(map, kitchenSettingVo);
            log.info("获取getKitchenSetting-redis缓存：{}", JSONUtil.toStr(map));
            return kitchenSettingVo;
        }
        ShopDto hopDto = new ShopDto();
        hopDto.setShopId(shopId);
        hopDto.setCreateShopId(centerId);
        ResponseEntity<String> responseEntity = baseForKdsClient.getKitchenSetting(hopDto, shopId);
        kitchenSettingVo = RestClient.getResponseEntity(responseEntity, KitchenSettingVo.class);
        if (Objects.isNull(kitchenSettingVo)) {
            return new KitchenSettingVo();
        }
        return kitchenSettingVo;

    }

    @Override
    public BasePosSettingVo queryPosSetting(Long posId, Integer shopId) {
        String key = "querypossetting" + posId;
        String redisKey = ApplicationConstant.redis_cy7center + shopId;
        RBucket<Map<String, Object>> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists() && Objects.nonNull(bucket.get().get(key))) {
            //  缓存存在
            BasePosSettingVo basePosSettingVo = new BasePosSettingVo();
            PosSettingDto posSettingDto = (PosSettingDto) bucket.get().get(key);
            BeanUtil.copyProperties(posSettingDto, basePosSettingVo);
            log.info("获取pos配置-redis缓存：{}", JSONUtil.toStr(basePosSettingVo));
            return basePosSettingVo;
        }
        PosDto posDto = new PosDto();
        posDto.setPosId(posId);
        posDto.setBelongShopId(shopId);
        ResponseEntity<String> res = baseForKdsClient.queryPosSetting(posDto, shopId);
        return RestClient.getResponseEntity(res, BasePosSettingVo.class);
    }

    @Override
    public CallingScreenSettingDto querySetting(Integer shopId) {
        String key = "querysetting";
        String redisKey = ApplicationConstant.redis_cy7center + shopId;
        RBucket<Map<String, Object>> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists() && Objects.nonNull(bucket.get().get(key))) {
            //  缓存存在
            CallingScreenSettingDto callingScreenSettingDto = (CallingScreenSettingDto) bucket.get().get(key);
            log.info("获取querySetting-redis缓存：{}", JSONUtil.toStr(callingScreenSettingDto));
            return callingScreenSettingDto;
        }
        CallingScreenParamDto dto = new CallingScreenParamDto();
        dto.setShopId(shopId);
        ResponseEntity<ResponseResult<CallingScreenSettingDto>> resultResponseEntity = baseForKdsClient.querySetting(dto, shopId);
        return ResponseEntityUtil.getResponseData(resultResponseEntity);
    }

    @Override
    public List<KitchenItemPlanOverAllVo> selectKitchenPlanListOverAllByShopId() {
        String redisKey = ApplicationConstant.redis_base_kds_plan_item_info + getShopId();
        RBucket<List<KitchenItemPlanOverAllVo>> bucket = baseArchRedissonClient.getBucket(redisKey);
        List<KitchenItemPlanOverAllVo> plans;
        if (bucket.isExists()) {
            plans = bucket.get();
        } else {
            ShopDto shopDto = new ShopDto();
            shopDto.setShopId(getShopId());
            shopDto.setCreateShopId(getCenterId());
            shopDto.setIsVassalModel(getIsVassalModel());
            ResponseEntity<ResponseResult<List<KitchenItemPlanOverAllVo>>> response = baseForKdsClient.selectKitchenPlanListOverAllByShopId(shopDto, getShopId());
            plans = ResponseEntityUtil.getResponseData(response);
        }
        return plans;
    }

    @Override
    public List<PosHasSaleTypeVo> queryPosSaleType(Long posId, Integer shopId) {
        List<PosHasSaleTypeVo> posHasSaleTypeVos;
        String key = "possaletype" + posId;
        String redisKey = ApplicationConstant.redis_cy7center + shopId;
        RBucket<Map<String, Object>> bucket = redissonClient.getBucket(redisKey);
        if (bucket.isExists() && CollectionUtils.isNotEmpty((List<PosHasSaleTypeVo>) bucket.get().get(key))) {
            //  缓存存在
            posHasSaleTypeVos = (List<PosHasSaleTypeVo>) bucket.get().get(key);
            log.info("查询销售类型-redis缓存：{}", jsonConverter.toJson(posHasSaleTypeVos));
        } else {
            PosHasSaleTypeDto posHasSaleTypeDto = new PosHasSaleTypeDto();
            posHasSaleTypeDto.setShopId(shopId);
            posHasSaleTypeDto.setPosId(posId);
            ResponseEntity<ResponseResult<List<PosHasSaleTypeVo>>> posHasSaleRes = baseForKdsClient.queryPosSaleType(posHasSaleTypeDto, shopId);
            posHasSaleTypeVos = ResponseEntityUtil.getResponseData(posHasSaleRes);
        }
        log.info("查询pos设置的订单类型：{}", jsonConverter.toJson(posHasSaleTypeVos));
        return posHasSaleTypeVos;
    }

    @Override
    public List<OrderItemVo> queryKdsItemInfoList(OrderItemDto orderItemDto) {
        //优先查缓存
        String redisKey = ApplicationConstant.redis_base_item_info + orderItemDto.getShopId();
        //查缓存获取门店菜品信息
        List<OrderItemVo> voList = RedissonUtils.getBucket(redissonClient, redisKey);
        //为空调接口获取菜品档案
        if(org.springframework.util.CollectionUtils.isEmpty(voList)){
            ResponseEntity<ResponseResult<List<OrderItemVo>>> responseResultResponseEntity = baseForKdsClient.queryKdsItemInfoList(orderItemDto, orderItemDto.getShopId());
            voList = ResponseEntityUtil.getResponseData(responseResultResponseEntity);
        }
        return voList;
    }

    @Override
    public List<KitchenPlanByPosVo> selectKitchenPlansByItemIds(Integer centerId, Integer shopId, List<Long> itemIds) {
        List<KitchenPlanByPosVo> result = new ArrayList<>();
        KdsItemDto kdsItemDto = new KdsItemDto();
        kdsItemDto.setCreateShopId(centerId);
        kdsItemDto.setShopId(shopId);
        kdsItemDto.setItemIds(itemIds);
        ResponseEntity<ResponseResult<List<KitchenPlanByPosVo>>> responseResultResponseEntity = baseForKdsClient.selectkitchenplansbyitemids(kdsItemDto,shopId);
        result = ResponseEntityUtil.getResponseData(responseResultResponseEntity);
        return result;
    }

}
