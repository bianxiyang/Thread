package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.util.List;
import java.util.Map;
/**
 * <b>功能：</b>方案过滤对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:03&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class FifterKdsOrderItem {

    private boolean enableLane;

    private Long posId;
    //  启用泳道返回Map<laneId,List<KdsOrderItem>>
    private Map<Long, List<KdsOrderItem>> laneKdsOrderItemMap;
    //  普通kds方案返回Map<posId,List<KdsOrderItem>>
    private Map<Long, List<KdsOrderItem>> kdsOrderItemMap;
    //  启用对接外卖小程序(启用后会将该kds方案中的品项上传外卖小程序用于计算繁忙程度以及等待时长)
    private Boolean  enableTakeawayApplet = false;

}
