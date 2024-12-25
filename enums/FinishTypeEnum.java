package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：kds订单或品项完成类型枚举</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:55&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public enum FinishTypeEnum {
    UNFINISHED(0, "未完成"),
    ITEM_FINISHED(1, "品项完成"),
    ORDER_FINISHED(2, "整单完成");


    private Integer value;
    private String message;

    private FinishTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static FinishTypeEnum getEnumByValue(Integer value) {
        for (FinishTypeEnum item : FinishTypeEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static FinishTypeEnum getEnumByMessage(String message) {
        for (FinishTypeEnum item : FinishTypeEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
