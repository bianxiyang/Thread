package cn.com.tcsl.fast.kds.server.model;

import cn.com.tcsl.fast.base.vo.biz.KitchenItemPlanVo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class KitchenResVo {

   private KitchenItemPlanVo kitchenItemPlanVo;

   private Map<Long, List<KdsOrderItem>> returnMap;

}
