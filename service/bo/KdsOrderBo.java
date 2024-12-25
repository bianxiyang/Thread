package cn.com.tcsl.fast.kds.server.service.bo;

import lombok.Data;

import java.util.List;

/**
 * <b>功能：</b>Kds查询订单业务对象<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:07&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
public class KdsOrderBo {

    //  kds订单主键
    private Long kdsOrderId;

    //  店铺号
    private Integer shopId;

    private String startTime;

    private String endTime;

    private String settleStartTime;

    private String settleEndTime;

    private String tableNumber;

    private String yyCode;

    private Long bsId;

    private List<Long> bsIds;

    private Integer status;

    private List<Integer> statusList;

    private List<Integer> pickUpStatusList;

    private List<Integer> orderTypeList;

    private Integer orderType;

    private Integer versionFlag= 0;

    private Integer sortFlag = 0;

    private Long  orderOriginId;

    private String deNo;

}
