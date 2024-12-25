package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.util.Date;

/**
 * <b>功能：</b>kds个性化配置<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:04&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsSetting {
    /**
     * 参数类型
     */
    private Long type;
    /**
     * 参数值
     */
    private String value;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人id
     */
    private Long creatorId;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 修改人id
     */
    private Long modifierId;
    /**
     * 配置key
     */
    private Long key;
    /**
     * 店铺号
     */
    private Integer shopId;

    private Long posId;
    private String kdsParamEnum;

}