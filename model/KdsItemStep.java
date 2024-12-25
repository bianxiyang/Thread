package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>功能：kds品项制作工序</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/8/15/016 8:33&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsItemStep {

    private Long id;

    private Long kdsItemId;

    private Long bsId;

    private Integer createShopId;

    private Integer shopId;

    private Long itemId;

    private Date createTime;

    private BigDecimal stepQuantity;

    private Integer status;

    private Long posId;

    private Date modifyTime;

}
