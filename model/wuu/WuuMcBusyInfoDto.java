package cn.com.tcsl.fast.kds.server.model.wuu;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <b>功能：</b>推送吾享繁忙程度入参<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/14 14:19&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class WuuMcBusyInfoDto implements Serializable {
    private Integer gcid;
    private Integer mcid;
    private Integer orderCount;
    private Integer itemCount;
    private Integer waitTime;
    private Integer percentage = 0;
    private List<WuuMcBusyOrderInfo> orderList;
}
