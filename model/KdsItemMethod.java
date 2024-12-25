package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：</b>品项做法model<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:04&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsItemMethod {
    /**
     * kds品项做法表主键
     */
    private Long id;
    /**
     * kds订单品项表主键(kds_order_item)
     */
    private Long kdsItemId;
    /**
     * 店铺号
     */
    private Integer shopId;
    private Integer createShopId;
    /**
     * 品项id
     */
    private Long itemId;
    /**
     * 做法类别(MethodTypeEnum)
     */
    private Integer methodType;

    private Long methodId = 0L;

    /**
     * 做法名称
     */
    private String methodName;
    /**
     * 做法数量
     */
    private BigDecimal methodQty;


    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
}