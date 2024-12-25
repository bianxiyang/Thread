package cn.com.tcsl.fast.kds.server.service;

import cn.com.tcsl.fast.base.dto.arch.OrderItemDto;
import cn.com.tcsl.fast.base.dto.device.CallingScreenSettingDto;
import cn.com.tcsl.fast.base.vo.arch.OrderItemVo;

import cn.com.tcsl.fast.base.vo.biz.KitchenItemPlanOverAllVo;

import cn.com.tcsl.fast.base.vo.biz.KitchenPlanByPosVo;
import cn.com.tcsl.fast.base.vo.biz.KitchenPosInfoByItemsVo;
import cn.com.tcsl.fast.base.vo.pos.PosHasSaleTypeVo;
import cn.com.tcsl.fast.base.vo.shop.ShopInfoVo;
import cn.com.tcsl.fast.kds.vo.BasePosSettingVo;
import cn.com.tcsl.fast.kds.vo.KitchenSettingVo;


import java.util.List;
import java.util.Map;

/**
 * <b>功能：base服务提供的接口</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/3/8 14:10&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public interface BaseInterfaceService {

    //  根据门店号获取总店
    ShopInfoVo getCenterShopInfoVo(Integer shopId);

    KitchenSettingVo getKitchenSetting(Integer centerId, Integer shopId);

    BasePosSettingVo queryPosSetting(Long posId, Integer shopId);

    CallingScreenSettingDto querySetting(Integer shopId);

    List<KitchenItemPlanOverAllVo> selectKitchenPlanListOverAllByShopId();

    List<PosHasSaleTypeVo> queryPosSaleType(Long posId, Integer shopId);


    List<OrderItemVo> queryKdsItemInfoList(OrderItemDto orderItemDto);

    List<KitchenPlanByPosVo> selectKitchenPlansByItemIds(Integer centerId, Integer shopId, List<Long> itemIds);

}
