package cn.com.tcsl.fast.kds.server.service.bo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>功能：</b>查询状态的入参<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/1/30 10:36&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsStatusBo {
    //  操作
    private Integer updateKdsType;
    //  原单数量
    private BigDecimal quantity;
    //  退单数量
    private BigDecimal refundQuantity;
    //  已制作完成数量
    private BigDecimal finishQuantity;
    //  已传菜完成数量
    private BigDecimal transmitQuantity;
    //  原始状态
    private Integer originStauts;
    //  是否需要制作
    private boolean needMake;
}
