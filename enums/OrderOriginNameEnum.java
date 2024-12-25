package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：</b>订单来源枚举<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/2/9 13:29&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum OrderOriginNameEnum {


    DINE_IN_ONLY(1, "堂食", ""),
    SCAN_ORDER(2, "微信点餐", "微信"),
    WX_ZT(3, "微信自提", "自提"),
    WX_WM(4, "微信外卖", "微信"),
    MEITUAN(5, "美团外卖", "美团"),
    E_LE_ME(6, "饿了么", "饿了么"),
    TIK_TOK(7, "抖音", "抖音"),
    ALIPAY(8, "支付宝点餐", "支付宝"),
    E_BAIDU(9, "饿百", "饿百"),
    SCAN_ORDER_WIN(10, "微信", "微信"),
    UNKNOWN_ENUM(999, "未知信息", "")


    ;
    private Integer value;
    private String message;
    private String callString;

    private OrderOriginNameEnum(Integer value, String message, String callString) {
        this.value = value;
        this.message = message;
        this.callString = callString;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCallString() {
        return this.callString;
    }

    public static OrderOriginNameEnum getEnumByValue(Integer value) {
        for (OrderOriginNameEnum item : OrderOriginNameEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return OrderOriginNameEnum.UNKNOWN_ENUM;
    }

    public static OrderOriginNameEnum getEnumByMessage(String message) {
        for (OrderOriginNameEnum item : OrderOriginNameEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return OrderOriginNameEnum.UNKNOWN_ENUM;
    }

    public static OrderOriginNameEnum getEnumByCallString(String callString) {
        for (OrderOriginNameEnum item : OrderOriginNameEnum.values()) {
            if (item.getCallString().equals(callString)) {
                return item;
            }
        }
        return OrderOriginNameEnum.UNKNOWN_ENUM;
    }


}
