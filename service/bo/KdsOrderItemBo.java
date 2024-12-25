package cn.com.tcsl.fast.kds.server.service.bo;

import lombok.Data;

import java.util.List;

/**
 * <b>功能：</b>Kds查询单据品项业务对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:07&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsOrderItemBo {

    //  kds订单主键
    private Long kdsOrderId;

    //  kds多条订单主键
    private List<Long> kdsOrderIds;

    //  店铺号
    private Integer shopId;

    private List<Long> itemIds;

    private List<Long> bsItemIds;

    private List<Long> pkgItemIds;

    private List<Integer> statusList;
    //  套餐标识字段
    private List<Integer> pkgFlags;

    private List<String> skuIds;

    private String key;

    private Long sideDishBsItemId;

}
