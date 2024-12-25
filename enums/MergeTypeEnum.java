package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：</b>按菜模式，拆分的类型<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/10/19 17:30&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum MergeTypeEnum {
    METHOD("1", "按做法拆分"),
    REMARK("2", "按整单备注拆分"),
    PKG_FLAG("3", "按套餐明细和单品拆分"),
    TIME("4", "按创建时间拆分");

    private String value;
    private String message;

    private MergeTypeEnum(String value, String message) {
        this.value = value;
        this.message = message;
    }

    public String getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static MergeTypeEnum getEnumByValue(String value) {
        for (MergeTypeEnum item : MergeTypeEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static MergeTypeEnum getEnumByMessage(String message) {
        for (MergeTypeEnum item : MergeTypeEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
