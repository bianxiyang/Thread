package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class KdsReportParams implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer shopId;
    private Long posId;
    private Long bsId;
    private Integer createShopId;
    private Long creatorId;
    private Date createTime;
    private Long modifierId;
    private Date modifyTime;
    //划菜时传入，发餐不用传
    private List<Long> itemIds;

    public KdsReportParams() {

    }

    /**
     * 新建
     */
    public KdsReportParams(Integer shopId, Integer createShopId, Long posId, Long bsId, Long creatorId, Date createTime) {
        this.shopId = shopId;
        this.posId = posId;
        this.bsId = bsId;
        this.createShopId = createShopId;
        this.creatorId = creatorId;
        this.createTime = createTime;
        this.modifierId = creatorId;
        this.modifyTime = createTime;
    }

    /**
     * 划菜
     */
    public KdsReportParams(Integer shopId, Integer createShopId, Long posId, Long bsId, Date modifyTime, List<Long> itemIds,Long creatorId) {
        this.shopId = shopId;
        this.posId = posId;
        this.bsId = bsId;
        this.createShopId = createShopId;
        this.modifyTime = modifyTime;
        this.itemIds = itemIds;
        this.modifierId = creatorId;
        this.creatorId = creatorId;
    }

    /**
     * 发餐
     */
    public KdsReportParams(Integer shopId, Integer createShopId, Long posId, Long bsId, Date modifyTime,Long creatorId) {
        this.shopId = shopId;
        this.posId = posId;
        this.bsId = bsId;
        this.createShopId = createShopId;
        this.modifyTime = modifyTime;
        this.modifierId = creatorId;
        this.creatorId = creatorId;
    }

}
