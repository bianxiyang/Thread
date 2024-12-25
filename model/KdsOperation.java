package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>kds操作记录<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/12/13 14:34&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsOperation {

    /**
     * 主键
     */
    private Long id;
    /**
     * 第三方订单主键
     */
    private Long bsId;

    /**
     * 店铺号
     */
    private Integer shopId;
    /**
     * 操作时间
     */
    private Date createTime;

    /**
     * 当前pos号
     */
    private Long posId;
    private String traceId;
    private String operMes;
    private String reqJson;
}
