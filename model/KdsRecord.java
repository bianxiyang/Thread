package cn.com.tcsl.fast.kds.server.model;

import lombok.Data;

import java.util.Date;

@Data
public class KdsRecord {
    private Long id;

    private Long kdsItemId;

    private Long bsId;

    private Integer shopId;

    private Long itemId;

    private Date createTime;

    private Date refundTime;

    private Date finishTime;

    private Date transmitTime;

    private Integer createShopId;


}