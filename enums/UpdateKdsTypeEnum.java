package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：kds操作类型</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:59&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public enum UpdateKdsTypeEnum {

    SINGELE_FINISHED(0, "单品项完成"),
    ORDER_FINISHED(1, "整单完成"),
    SINGLE_BACKOUT(2, "单品项撤销"),
    ORDER_BACKOUT(3, "整单撤销"),
    ORDER_DELETE(4, "整单逻辑删除"),
    UPDATE_DATA(5,"更新订单数据"),
    SINGLE_DELETE(6,"单品项删除"),
    ORDER_TRANSMIT(7,"整单传配"),
    ORDER_TRANSMIT_BACKOUT(8,"整单传配撤销"),
    SINGLE_TRANSMIT(9,"单品传配"),
    SINGLE_TRANSMIT_BACKOUT(10,"单品传配撤销"),
    FORCE_OPERATION(11,"强制操作"),
    FORCE_OPERATION_BACKOUT(12,"强制操作撤销"),

    PART_OF_THE_REFUND(998,"部分退"),
    UNKNOWN(999,"未知操作");


    private Integer value;
    private String message;

    private UpdateKdsTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static UpdateKdsTypeEnum getEnumByValue(Integer value) {
        for (UpdateKdsTypeEnum item : UpdateKdsTypeEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return UpdateKdsTypeEnum.UNKNOWN;
    }

    public static UpdateKdsTypeEnum getEnumByMessage(String message) {
        for (UpdateKdsTypeEnum item : UpdateKdsTypeEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return UpdateKdsTypeEnum.UNKNOWN;
    }

}
