package cn.com.tcsl.fast.kds.server.model;

import cn.com.tcsl.fast.kds.server.util.PinyinUtil;
import cn.com.tcsl.fast.kds.server.util.PrItemFormatUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

/**
 * <b>功能：</b>kds单据品项model<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:02&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
@Data
@Slf4j
public class KdsOrderItem {
    /**
     * 主键
     */
    private Long id;
    /**
     * kds订单表主键(kds_order)
     */
    private Long kdsOrderId;
    /**
     * 店铺号
     */
    private Integer shopId;
    private Integer createShopId;
    /**
     * 品项id
     */
    private Long itemId;
    /**
     * 品项名称
     */
    private String itemName;
    /**
     * 品项简称
     */
    private String itemAbbreviation;
    /**
     * 有效数量
     */
    private BigDecimal quantity;

    private BigDecimal originalQuantity;

    /**
     * 部分退数量
     */
    private BigDecimal refundQuantity = BigDecimal.ZERO;

    /**
     * 已传数量
     */
    private BigDecimal finishQuantity = BigDecimal.ZERO;

    /**
     * 传配数量
     */
    private BigDecimal transmitQuantity = BigDecimal.ZERO;

    /**
     * 类别id
     */
    private Long classId;
    /**
     * 类别名称
     */
    private String className;
    /**
     * 单位id
     */
    private Long unitId;
    /**
     * 单位名称
     */
    private String unitName;
    /**
     * 品项备注
     */
    private String remark;
    /**
     * 加急催菜标识(UrgentStateEnum)
     */
    private Integer urgentState;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date modifyTime;
    private Date refundTime;
    private Date finishTime;
    private Date transmitTime;
    //  实际操作时间
    private Long realOperationTime;
    /**
     * 品项状态(StatusEnum)
     */
    private Integer status;
    /**
     * 规格的状态
     */
    private Integer specStatus;

    /**
     * 修改人id
     */
    private Long modifierId;
    /**
     * 修改人名称
     */
    private String modifyName;
    /**
     * 品项逻辑删除标识(DelFlgEnum)
     */
    private Integer delflg;
    /**
     * 用于套餐关联使用，biz_sc_item表主键
     */
    private Long bsItemId;

    /**
     * 配菜父id
     */
    private Long sideDishBsItemId;


    /**
     * 套餐主键
     */
    private Long pkgId;
    /**
     * 套餐标识（0：非套餐品项   1：套餐（即套餐主项） 2：套餐内明细）
     */
    private Integer pkgFlag;


    private String pkgName;

    /**
     * 点餐顺序
     */
    private Long sortOrder;
    /**
     * 临时品项标识
     */
    private boolean tempTag;
    /**
     * 单据类型(OrderTypeEnum)
     */
    private Integer orderType;

    /**
     * 打包标记 0-不打包 1-打包
     */
    private Integer packTag = 0;


    /**
     * kds品项做法表主键
     */
    private Long methodId;

    /**
     * 做法类别(MethodTypeEnum)
     */
    private Integer methodType;
    /**
     * 做法名称
     */
    private String methodName;

    /**
     * 規格
     */
    private String specifications;
    /**
     * 规格主键
     */
    private Long specId = 0L;

    private Long sizeId = -1L;

    private String skuId;

    private BigDecimal packCount = BigDecimal.ZERO;

    private Long laneId;
    /**
     * 标准制作时长
     */
    private Integer standardTime;

    /**
     * 预警时间
     */
    private Integer warnTime;
    /**
     * 是否是称重品项
     */
    private boolean weighed;

    private boolean methodFlag = true;

    private boolean needMake = true;


    public String getSkuId() {
        setSkuId();
        return skuId;
    }

    public void setSkuId() {
        if (Objects.nonNull(this.sizeId)) {
            this.skuId = this.itemId.toString() + "_" + this.sizeId.toString();
        } else {
            this.skuId = this.itemId.toString() + "_" + "-1";
        }
    }

    public String getItemAbbreviation() {
        setItemAbbreviation();
        return itemAbbreviation;
    }

    public void setItemAbbreviation() {
        if (Objects.nonNull(this.itemName)) {
            this.itemAbbreviation = PinyinUtil.getPinyinj(this.itemName);
        }
    }

}