package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：</b>并菜时间间隔<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/10/18 15:48&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum MergeBetweenEnum {
    UNLIMITED(999, "无限制"),
    ZERO_MINUTE(0, "零分钟"),
    ONE_MINUTE(1, "一分钟"),
    TWO_MINUTES(2, "两分钟"),
    THREE_MINUTES(3, "三分钟"),
    FIVE_MINUTES(5, "五分钟"),
    TEN_MINUTES(10, "十分钟"),
    FIFFTEEN_MINUTE(15, "十五分钟"),
    THIRTY_MINUTE(30, "三十分钟");

    private Integer value;
    private String message;

    private MergeBetweenEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static MergeBetweenEnum getEnumByValue(Integer value) {
        for (MergeBetweenEnum item : MergeBetweenEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static MergeBetweenEnum getEnumByMessage(String message) {
        for (MergeBetweenEnum item : MergeBetweenEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
