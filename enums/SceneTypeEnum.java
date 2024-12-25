package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：</b>场景类型<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2024/8/20 13:13&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum SceneTypeEnum {


    QUERY_SCENE(1, "待制作列表"),
    FINISH_QUERY_SCENE(2, "已制作列表"),
    TRANSMIT_QUERY_SCENE(3, "待传配列表"),
    FINISH_TRANSMIT_QUERY_SCENE(4, "已传配列表"),
    UPDATE(5, "单品制作"),
    SEND_ORDER(6, "整单完成"),
    BATCH_UPDATE(7, "批量制作"),
    CALL_NUMBER(8, "叫号操作"),
    TRANSMIT_ORDER(9, "整单传配"),
    TRANSMIT(10, "单品传配"),
    BATCH_TRANSMIT(11, "批量传配"),
    LANE_QUERY(12, "泳道查询"),
    QUERY_TOTAL(13,"已点汇总"),
    UNKNOWN_SCENE(999, "未知场景");


    private Integer value;
    private String message;

    private SceneTypeEnum(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static SceneTypeEnum getEnumByValue(Integer value) {
        for (SceneTypeEnum item : SceneTypeEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return SceneTypeEnum.UNKNOWN_SCENE;
    }

    public static SceneTypeEnum getEnumByMessage(String message) {
        for (SceneTypeEnum item : SceneTypeEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return SceneTypeEnum.UNKNOWN_SCENE;
    }

}
