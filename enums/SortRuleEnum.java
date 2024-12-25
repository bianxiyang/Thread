package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;

/**
 * <b>功能：</b>排序规则<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/11/17 10:13&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum SortRuleEnum {

    SETTLE_TIME_ASC(0, "结算时间正序"),
    MODIFY_TIME_DESC(1, "修改时间倒序"),
    NOTHING(2, "无排序规则");

    private Integer value;
    private String message;

    private SortRuleEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static SortRuleEnum getEnumByValue(Integer value) {
        for (SortRuleEnum item : SortRuleEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return NOTHING;
    }

    public static SortRuleEnum getEnumByMessage(String message) {
        for (SortRuleEnum item : SortRuleEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return NOTHING;
    }

}
