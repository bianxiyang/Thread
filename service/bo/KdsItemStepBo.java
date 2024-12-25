package cn.com.tcsl.fast.kds.server.service.bo;

import lombok.Data;

import java.util.List;

/**
 * <b>功能：</b>查询Kds制作工序业务对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/8/19 14:39&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsItemStepBo {

    private List<Long> kdsItemIds;

    private Integer shopId;

    private Long bsId;

    private Long posId;
}