package cn.com.tcsl.fast.kds.server.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <b>功能：</b>Kds查询订单列表对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:07&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsOrderListBo {

    @ApiModelProperty("店铺号")
    private Integer shopId;
    @ApiModelProperty("品项名称")
    private String itemName;
    @ApiModelProperty("结算流水号")
    private String jsCode;
    @ApiModelProperty("关键字查询")
    private String key;

}
