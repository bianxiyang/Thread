package cn.com.tcsl.fast.kds.server.service.impl;

import cn.com.tcsl.fast.common.converter.json.JsonConverter;
import cn.com.tcsl.fast.common.model.ResponseResult;
import cn.com.tcsl.fast.common.util.Maps;
import cn.com.tcsl.fast.framework.exception.ApplicationError;
import cn.com.tcsl.fast.kds.cnd.KdsMissedOrderCnd;
import cn.com.tcsl.fast.kds.server.constant.KdsConstant;
import cn.com.tcsl.fast.kds.server.enums.PickUpStatusEnum;
import cn.com.tcsl.fast.kds.server.enums.SceneTypeEnum;
import cn.com.tcsl.fast.kds.server.enums.StatusEnum;
import cn.com.tcsl.fast.kds.server.enums.UpdateKdsTypeEnum;
import cn.com.tcsl.fast.kds.server.model.KdsOrder;
import cn.com.tcsl.fast.kds.server.model.KdsOrderItem;
import cn.com.tcsl.fast.kds.server.model.KdsOrderPick;
import cn.com.tcsl.fast.kds.server.model.KdsSetting;
import cn.com.tcsl.fast.kds.server.service.KdsCOMPService;
import cn.com.tcsl.fast.kds.server.service.KdsService;
import cn.com.tcsl.fast.kds.server.service.KdsSettingService;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderBo;
import cn.com.tcsl.fast.kds.server.service.bo.KdsOrderItemBo;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <b>功能：kds补偿（KdsCompensateController）</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/03/20 09:48&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;杨彦岭&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Slf4j
@Service
public class KdsCOMPServiceImpl implements KdsCOMPService {


    @Resource
    private KdsService kdsService;
    @Resource
    private KdsSettingService kdsSettingService;

    @Resource
    protected JsonConverter jsonConverter;

    /**
     * 发餐补偿机制
     *
     * @param query
     * @return
     */
    @Override
    public ResponseResult pushMissedOrder(KdsMissedOrderCnd query) {
        log.info("kds发餐补偿机制入参:" + jsonConverter.toJson(query));
        Integer shopId = query.getShopId();
        Long bsId = query.getBsId();
        Long posId = query.getPosId();
        Long modifierId = query.getModifierId();

        //  查询单据信息
        KdsOrderBo orderBo = new KdsOrderBo();
        orderBo.setShopId(shopId);
        orderBo.setBsId(bsId);
        List<KdsOrder> orderList = kdsService.queryKdsOrder(orderBo);
        if (CollectionUtils.isEmpty(orderList)) {
            throw new ApplicationError("S1.ERR.10020", "订单数据不存在");
        }
        log.info("kds发餐补偿机制功能查询单据结果：{}", jsonConverter.toJson(orderList));
        KdsOrder kdsOrder = orderList.get(0);
        if (!StatusEnum.NEW_MAKE_FINISH.getValue().equals(kdsOrder.getStatus())) {
            int sendOrderSetting = 0;
            List<KdsSetting> sendOrderSettingList = kdsSettingService.queryByParam(Maps.create("posId", posId, "key", KdsConstant.SEND_ORDER_CALL, "shopId", shopId));
            if (CollectionUtils.isNotEmpty(sendOrderSettingList)) {
                sendOrderSetting = Integer.parseInt(sendOrderSettingList.get(0).getValue());
            }
            //修改订单状态
            kdsOrder.setStatus(StatusEnum.NEW_MAKE_FINISH.getValue());
            kdsOrder.setModifyTime(new Date());
            kdsOrder.setMakeEndTime(new Date());
            int updateKdsOrder = kdsService.updateKdsOrder(kdsOrder);
            if (updateKdsOrder != 1) {
                throw new ApplicationError("S1.ERR.10020", "修改订单状态失败");
            }
            if (sendOrderSetting == 2) {
                KdsOrderPick kdsOrderPick = new KdsOrderPick();
                kdsOrderPick.setPickUpStatus(PickUpStatusEnum.PICK_UP_FINISH.getValue());
                kdsOrderPick.setModifyTime(new Date());
                kdsOrderPick.setBsId(bsId);
                int updateKdsOrderPick = kdsService.updateKdsOrderPick(kdsOrderPick);
                if (updateKdsOrderPick != 1) {
                    throw new ApplicationError("S1.ERR.10020", "修改取餐状态失败");
                }
            }
            KdsOrderItemBo kdsOrderItemBo = new KdsOrderItemBo();
            kdsOrderItemBo.setShopId(kdsOrder.getShopId());
            kdsOrderItemBo.setKdsOrderId(kdsOrder.getId());
            log.info("kds更新查询3：{}", jsonConverter.toJson(kdsOrderItemBo));
            List<KdsOrderItem> kdsOrderItems = kdsService.queryKdsOrderItems(kdsOrderItemBo);
            //  修改品项状态
            kdsService.handleOrderItems(shopId, UpdateKdsTypeEnum.ORDER_FINISHED.getValue(), modifierId, kdsOrderItems, kdsOrder, 1, sendOrderSetting,posId, SceneTypeEnum.SEND_ORDER.getValue(),new HashMap<>());
        }
        log.info("kds发餐补偿机制功接口结束");
        return ResponseResult.success();
    }
}
