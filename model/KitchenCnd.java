
package cn.com.tcsl.fast.kds.server.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <b>功能：</b>查询kds方案入参<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/5/11 9:15&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
@ApiModel(description = "查询kds方案入参")
public class KitchenCnd implements Serializable {
    @ApiModelProperty("店铺号")
    private Integer shopId;
    @ApiModelProperty("集团号")
    private Integer centerId;
    @ApiModelProperty("posIds")
    private List<Long> posIds;
    @ApiModelProperty("要过滤的品项")
    private List<KdsOrderItem> sourceList;

}