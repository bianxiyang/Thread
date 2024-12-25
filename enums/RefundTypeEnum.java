package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：退单类型枚举</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:57&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */


public enum RefundTypeEnum {

    ALL_REFUND(0, "整单退"),
    PART_OF_THE_REFUND(1, "部分退");

    private Integer value;
    private String message;

    private RefundTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static RefundTypeEnum getEnumByValue(Integer value) {
        for (RefundTypeEnum item : RefundTypeEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static RefundTypeEnum getEnumByMessage(String message) {
        for (RefundTypeEnum item : RefundTypeEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
