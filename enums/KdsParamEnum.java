package cn.com.tcsl.fast.kds.server.enums;

import java.util.Objects;


/**
 * <b>功能：kds个性化配置枚举</b><br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2022/4/20 14:54&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */

public enum KdsParamEnum {


    //  kds本地配置key值-就餐人数
    AC_NUMBER_SETTING(1001001L, "acNumberSetting"),
    //  kds本地配置key值-菜品道数
    ITEM_NUMBER_SETTING(1002001L, "itemNumberSetting"),
    //  kds本地配置key值-等待时长
    WAITING_TIME_SETTING(1003001L, "waitingTimeSetting"),
    //  kds本地配置key值-VIP标识
    VIP_FLAG_SETTING(1004001L, "vipFlagSetting"),
    //  kds本地配置key值-是否显示菜品单位（1开启2关闭）
    ITEM_SIZE_SETTING(1005001L, "itemSizeSetting"),
    //  kds本地配置key值-是否显示已取餐按钮（1开启2关闭）
    PICK_UP_FINISHED_SETTING(1006001L, "pickUpFinishedSetting"),
    //  kds本地配置key值-是否显示叫号按钮（1开启2关闭）
    CALL_NUMBER_BUTTON_SETTING(1007001L, "callNumberButtonSetting"),
    //  kds本地配置key值-是否显示已点汇总（1开启2关闭）
    QUERY_TOTAL_SETTING(1008001L, "queryTotalSetting"),
    //  kds本地配置key值-是否开启每次划菜并叫号（1开启2关闭）
    UPDATE_CALL_SETTING(1009001L, "updateCallSetting"),
    //  kds本地配置key值-是否开启超时提醒（1开启2关闭）
    TIME_OUT_REMINDER(1010001L, "timeOutReminder"),
    //  kds本地配置key值-超时时间配置
    OUT_TIME_SETTING(1011001L, "outTimeSetting"),
    //  kds本地配置key值-数据时间间隔配置
    TIME_BETWEEN_SETTING(1012001L, "timeBetweenSetting"),
    //  kds本地配置key值-kds字体大小（1小2中3大4超大）
    FONT_SIZE_SETTING(1013001L, "fontSizeSetting"),
    //  kds本地配置key值-显示主套餐
    PKG_SETTING(1014001L, "pkgSetting"),
    //  kds本地配置key值-kds显示条数
    PAGE_SIZE_SETTING(1015001L, "pageSizeSetting"),
    //  kds本地配置key值-kds叫号模板配置
    CALL_TEMPLATE_SETTING(1016001L, "callTemplateSetting"),
    //  kds本地配置key值-推送打印配置
    PUSH_PRINT_SETTING(1017001L, "pushPrintSetting"),
    //  kds本地配置key值-是否显示送餐/自提时间
    SEND_PICKUP_TIME_SETTING(1018001L, "sendPickupTimeSetting"),
    //  kds本地配置key值-桌牌号设置：显示自增单号（1开启2关闭）
    CALL_BRAND_NUMBER(1019001L, "callBrandNumber"),
    //  kds本地配置key值-kds做法字体个性化配置
    METHOD_FONT_SETTING(1020001L, "methodFontSetting"),
    //  kds本地配置key值-餐7的沽清URL
    CY7_GUQING_URL(1021001L, "cy7GuqingUrl"),
    //  kds本地配置key值-行列设置（1*4:1   2*4:2）
    RANKS_SETTING(1022001L, "ranksSetting"),
    //  kds本地配置key值-来新单提醒（1开启2关闭）
    NEW_ORDER_REMINDER(1023001L, "newOrderReminder"),
    //  kds本地配置key值-出餐并叫号（1开启2关闭）
    SEND_ORDER_CALL_SETTING(1024001L, "sendOrderCallSetting"),
    //  kds本地配置key值-按菜模式kds字体大小（1小2中3大4超大）
    ITEM_MODE_FONT_SIZE(1025001L, "itemModeFontSize"),
    //  kds本地配置key值-按菜模式kds做法字体大小（1小2中3大4超大）
    ITEM_MODE_METHOD_FONT_SIZE(1026001L, "itemModeMethodFontSize"),
    //  kds本地配置key值-桌牌号设置：显示手输牌号 （1开启2关闭）
    CALL_TABLE_NUMBER(1027001L, "callTableNumber"),
    //  kds本地配置key值-是否开启取餐屏（1开启2关闭）
    TAKE_OUT_SCREEN(1028001L, "takeOutScreen"),
    //  kds本地配置key值-是否发餐叫号(1开始2关闭)
    SEND_ORDER_CALL(1029001L, "sendOrderCall"),
    //  kds本地配置key值-自定义行数
    CUSTOMIZE_LINE(1030001L, "customizeLine"),
    //  kds本地配置key值-自定义列数
    CUSTOMIZE_ROW(1031001L, "customizeRow"),
    //  kds本地配置key值-颜色模式
    COLOR_MODE(1032001L, "colorMode"),
    //  kds本地配置key值-展示销售类型过滤条件(1:开启;2:关闭)
    SALE_TYPE_FIFTER(1033001L, "saleTypeFifter"),
    //  kds本地配置key值-是否展示套餐名称(1:开启;2:关闭)
    PKG_NAME_SHOW(1034001L, "pkgNameShow"),
    //  显示规格(1:开启;2:关闭)
    PKG_SIZE_SHOW(1035001L, "pkgSizeShow"),
    //  叫号的设置(1:开启;2:关闭)
    PKG_CALL_NUMBER(1036001L, "pkgCallNumber"),
    //  显示分页(1:开启;2:关闭)
    PKG_PAGE(1037001L, "pkgPage"),



    //  取餐屏所选择的kds方案配置
    TAKE_OUT_PLAN_ID(1038001L, "takeOutPlanId"),
    //  kds本地配置key值-竖屏展示开关（1开启2关闭）
    VERTICAL_SHOW(1039001L, "verticalShow"),
    //  kds本地配置key值-同菜合并开关（1开启2关闭）
    SAME_ITEM_MERGE(1040001L, "sameItemMerge"),
    //  kds本地配置key值-合并时间配置（1开启2关闭）
    MERGE_BETWEEN(1041001L, "mergeBetween"),
    //  kds本地配置key值-按做法拆分（1开启2关闭）
    METHOD_SPLIT(1042001L, "methodSplit"),
    //  kds本地配置key值-按备注拆分（1开启2关闭）
    REMARK_SPLIT(1043001L, "remarkSplit"),
    //  kds本地配置key值-按套餐明细和单品拆分（1开启2关闭）
    PKG_FLAG_SPLIT(1044001L, "pkgFlagSplit"),

    //  kds本地配置key值-分批制作开关（1开启2关闭）
    DIVIDE_MAKE(1045001L, "divideMake"),
    //  kds本地配置key值-操作即下屏（1开启2关闭）
    OPER_DOWN(1046001L, "operDown"),

    //  kds本地配置key值-高峰选优(0:默认 1:堂食2:外带3:外卖4:自提)
    ORDER_PRIORITY(1047001L, "orderPriority"),


    //  kds本地配置key值-启用按销售类型区分显示(0:默认 1:合并外卖、自提2:合并外卖、外带、自提)
    ORDER_TYPE_GROUPING(1048001L, "orderTypeGrouping"),

    FIXED_SIZE(1049001L,"fixedSize"),
    //  kds本地配置key值-展示的类型(0:按单模式 1:按菜模式)
    BOARD_MODE(1050001L,"boardMode"),
    //  kds本地配置key值-撤销按钮（1开启2关闭）
    UNDO_SWITCH(1051001L,"undoSwitch"),


    //  kds本地配置key值-是否开启工序（1开启2关闭）
    STEP_SWITCH(1052001L,"stepSwitch"),

    //  kds本地配置key值-做法/整单备注字号
    METHOD_FONT_SIZE(1053001L,"methodFontSize"),
    //  kds本地配置key值-桌牌号字号
    TABLENUMBER_FONT_SIZE(1054001L,"tableNumberFontSize"),
    //  kds本地配置key值-完成叫号字号
    SEND_ORDER_FONT_SIZE(1055001L,"sendOrderFontSize"),
    //  kds本地配置key值-是否开启完成按钮（1开启2关闭）
    SENDORDER_SWITCH(1056001L,"sendOrderSwitch"),



    UN_KNOWN(9999L, "未知配置项");
    private Long value;
    private String message;

    private KdsParamEnum(Long value, String message) {
        this.value = value;
        this.message = message;
    }

    public Long getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static KdsParamEnum getEnumByValue(Long value) {
        for (KdsParamEnum item : KdsParamEnum.values()) {
            if (Objects.equals(item.getValue(), value)) {
                return item;
            }
        }
        return KdsParamEnum.UN_KNOWN;
    }

    public static KdsParamEnum getEnumByMessage(String message) {
        for (KdsParamEnum item : KdsParamEnum.values()) {
            if (item.getMessage().equals(message)) {
                return item;
            }
        }
        return KdsParamEnum.UN_KNOWN;
    }

}
