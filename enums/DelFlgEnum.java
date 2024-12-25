package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：删除标识枚举</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:54&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum DelFlgEnum {

    NOT_DELETE(0, "未删除"),
    DELETED(1, "已删除"),
    BACK_UP_DELETED(2, "备份过期删除");


    private Integer value;
    private String message;

    private DelFlgEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static DelFlgEnum getEnumByValue(Integer value) {
        for (DelFlgEnum item : DelFlgEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static DelFlgEnum getEnumByMessage(String message) {
        for (DelFlgEnum item : DelFlgEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
