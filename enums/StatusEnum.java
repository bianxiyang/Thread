package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：kds单据或品项状态(0.取消制作，1.待制作，2.开始配菜，3.配菜完成，4.制作中，5.制作完成，6.待取餐，7.取餐完成。)目前只用到1和6和0</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:58&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */


public enum StatusEnum {


    CANCEL_MAKING(0, "取消制作"),
    WAIT_MAKING(1, "待制作"),
    BEGIN_PREPARE_ITEM(2, "开始配菜"),
    PREPARE_ITEM_FINISH(3, "配菜完成"),
    MAKING(4, "制作中"),
    MAKING_FINISH(5, "制作完成"),
    WAIT_GET_ITEM(6, "待取餐"),
    GET_ITEM_FINISH(7, "取餐完成"),
    NEW_MAKE_FINISH(99, "新版本制作完成"),
    TRANSMITING(100, "传菜中"),
    TRANSMIT_FINISH(110, "传菜完成");


    private Integer value;
    private String message;

    private StatusEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static StatusEnum getEnumByValue(Integer value) {
        for (StatusEnum item : StatusEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return null;
    }

    public static StatusEnum getEnumByMessage(String message) {
        for (StatusEnum item : StatusEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return null;
    }

}
