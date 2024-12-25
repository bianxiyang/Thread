package cn.com.tcsl.fast.kds.server.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <b>功能：</b>kds配置model<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:04&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("kds_param")
public class KdsParam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 配置类型
     */
    @TableId(value = "type", type = IdType.INPUT)
    private Integer type;

    /**
     * 类型下的key值
     */
    private Long key;

    /**
     * key值对应的配置value
     */
    private String value;

    /**
     * 配置图标
     */
    private String icon;

    /**
     * 描述
     */
    private String describe;

    /**
     * 排序规则
     */
    private Long sortOrder;


}
