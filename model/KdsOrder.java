package cn.com.tcsl.fast.kds.server.model;

import cn.com.tcsl.fast.kds.server.util.PrItemFormatUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.Objects;

/**
 * <b>功能：</b>kds单据model<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:04&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
@Slf4j
public class KdsOrder {
    /**
     * 主键
     */
    private Long id;
    /**
     * 第三方订单主键
     */
    private Long bsId;
    /**
     * 退单主键
     */
    private Long ssId;
    /**
     * 店铺号
     */
    private Integer shopId;

    private Integer createShopId;

    /**
     * 单据类型(OrderTypeEnum)
     */
    private Integer orderType;
    /**
     * 单据code号
     */
    private String yyCode;
    /**
     * 结算code号
     */
    private String jsCode;

    /**
     * 桌号
     */
    private String tableNumber;

    /**
     * 牌号
     */
    private String brandNumber;

    /**
     * 线上线下统一大排行
     */
    private String rankingNumber;


    /**
     * 渠道类型id
     */
    private Long  orderOriginId;

    /**
     * 订单来源名称
     */
    private String orderOriginName;
    /**
     * 是否是会员
     */
    private boolean memberTag;

    /**
     * 客流量
     */
    private Integer acNumber;

    /**
     * 开始制作时间(默认下单时间)
     */
    private Date makeStartTime;
    /**
     * 下单时间
     */
    private Date settleTime;
    /**
     * 送餐/自提时间
     */
    private Date deliveryTime;
    /**
     * 创建者id
     */
    private Long creatorId;
    /**
     * 创建人姓名
     */
    private String creatorName;
    /**
     * 下单pos号
     */
    private Long posId;
    /**
     * 订单来源(OrderSourceEnum)
     */
    private Integer orderSource;
    /**
     * 整单备注
     */
    private String remark;
    /**
     * 逻辑删除标识(DelFlgEnum)
     */
    private Integer delflg;
    /**
     * 制作完成时间
     */
    private Date makeEndTime;
    /**
     * 修改者id
     */
    private Long modifierId;
    /**
     * 修改时间
     */
    private Date modifyTime;
    /**
     * 整单状态(StatusEnum)
     */
    private Integer status;


    private Integer pickUpStatus;


    /**
     * 呼叫编号
     */
    private String callCode = "";
    /**
     * 外卖单号
     */
    private String deNo;
    /**
     * 客位id
     */
    private Long pointId;
    /**
     * 客位标识 0-默认，1-固定
     */
    private Integer pointTag = 0;

    public String getRemark() {
        setRemark();
        return remark;
    }

    public void setRemark() {
        if (Objects.nonNull(this.remark)) {
            try {
                this.remark = PrItemFormatUtils.emojiAndSpecialCharFilter(this.remark).replaceAll("\\s*", "");
            } catch (NumberFormatException e) {
                log.error("备注过滤emoji异常:", e);
                this.remark = null;
            }
        }
    }


}