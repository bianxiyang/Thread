package cn.com.tcsl.fast.kds.server.service.bo;

import lombok.Data;

import java.util.List;

/**
 * <b>功能：</b>Kds查询品项规格业务对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:06&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsItemSpecBo {

    //  kds订单主键
    private Long kdsOrderId;
    //  kds单据品项主键
    private Long kdsItemId;

    private List<Long> kdsItemIds;

    //  店铺号
    private Integer shopId;
    //  品项id
    private Long itemId;
    //  品项名称
    private String itemName;
    //  类别id
    private Long classId;
    //  单位id
    private Long unitId;

    private Long sizeId;


}
