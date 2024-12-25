package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：催菜标识枚举</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 15:00&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public enum UrgentStateEnum {

    UNURGENT(0, "未催菜"),
    URGENT(1, "已催菜");

    private Integer value;
    private String message;

    private UrgentStateEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static UrgentStateEnum getEnumByValue(Integer value) {
        for (UrgentStateEnum item : UrgentStateEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static UrgentStateEnum getEnumByMessage(String message) {
        for (UrgentStateEnum item : UrgentStateEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
