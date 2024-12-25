package cn.com.tcsl.fast.kds.server.service.bo;



import cn.com.tcsl.fast.kds.server.model.KdsOrderItem;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class PushNewOrderCnd implements Serializable {
    private Integer shopId;
    private Integer centerId;
    private Long posId = 0L;
    private Long bsId;
    private Date orderDate;
    private String orderCode;
    private Integer orderType;
    private List<KdsOrderItem> orderItems;
    private Integer versionFlag;
    private Integer serviceMode;
    private Date deliveryTime;
}
