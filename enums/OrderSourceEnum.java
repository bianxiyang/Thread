package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：kds订单来源枚举</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:55&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public enum OrderSourceEnum {

    ANDROID_MACHINE(0, "安卓一体机单据"),
    THIRD_ORDER(1, "第三方订单"),
    SHOP_ORDER(2, "门店端订单");

    private Integer value;
    private String message;

    private OrderSourceEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static OrderSourceEnum getEnumByValue(Integer value) {
        for (OrderSourceEnum item : OrderSourceEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static OrderSourceEnum getEnumByMessage(String message) {
        for (OrderSourceEnum item : OrderSourceEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
