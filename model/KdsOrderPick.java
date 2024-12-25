package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>kds取餐状态model<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:04&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsOrderPick {
    private Long id;

    private Long kdsOrderId;

    private Integer shopId;

    private Integer createShopId;

    private Long bsId;

    private Date createTime;

    private Date modifyTime;

    private Integer pickUpStatus;


}