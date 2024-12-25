package cn.com.tcsl.fast.kds.server.model.datasync;

import cn.com.tcsl.fast.datasync.dto.kds.KcItemDto;
import cn.com.tcsl.fast.datasync.dto.kds.KcKscBakDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class KdsSyncCnd {

    private Integer shopId;

    private Integer createShopId;

    private List<KcKscBakDto> kcKscBakDtos;

    private List<KcItemDto> kcItemDtos;

    private Integer flag = 1;

    private Long bsId;

    @ApiModelProperty("kds配置的超时时间 分钟数")
    private short standardTime;
    @ApiModelProperty("正向逆向标识(1正向2逆向)")
    private int sequence;
    @ApiModelProperty("当前厨房传菜状态")
    private Integer sourceKitchenFlg;
    @ApiModelProperty("目标厨房传菜状态")
    private Integer targetKitchenFlg;

    @ApiModelProperty("订单服务叫号时间")
    private String callUpTime;
}
