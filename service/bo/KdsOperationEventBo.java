package cn.com.tcsl.fast.kds.server.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>功能：</b>kds操作入参<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/12/13 14:33&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsOperationEventBo {
    @ApiModelProperty("营业流水id")
    private Long bsId;
    @ApiModelProperty("店铺号")
    private Integer shopId;
    @ApiModelProperty("posId")
    private Long posId;
    @ApiModelProperty("更新品项集合(使用点餐订餐唯一标识，用于区分同品项合并划菜问题)")
    private List<Long> bsItemIds = new ArrayList();
    @ApiModelProperty("更新类型")
    private Integer updateKdsType;
}
