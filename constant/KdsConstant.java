package cn.com.tcsl.fast.kds.server.constant;

/**
 * Kds服务常量
 *
 * @author bian_xy
 * @time 2022/02/21
 */
public class KdsConstant {

    //  TEST
    public static final int TEST_CONTANT = 100;

    // 增值
    // 快餐版KDS
    public static final Long APPRECIATION_KDS = 4001003L;
    // 快餐版叫号屏
    public static final Long APPRECIATION_CALLSCREEN = 4001004L;


    //  kds本地配置key值-就餐人数
    public static final Long AC_NUMBER_SETTING = 1001001L;
    //  kds本地配置key值-菜品道数
    public static final Long ITEM_NUMBER_SETTING = 1002001L;
    //  kds本地配置key值-等待时长
    public static final Long WAITING_TIME_SETTING = 1003001L;
    //  kds本地配置key值-VIP标识
    public static final Long VIP_FLAG_SETTING = 1004001L;
    //  kds本地配置key值-是否显示菜品单位（1开启2关闭）
    public static final Long ITEM_SIZE_SETTING = 1005001L;
    //  kds本地配置key值-是否显示已取餐按钮（1开启2关闭）
    public static final Long PICK_UP_FINISHED_SETTING = 1006001L;
    //  kds本地配置key值-是否显示叫号按钮（1开启2关闭）
    public static final Long CALL_NUMBER_BUTTON_SETTING = 1007001L;
    //  kds本地配置key值-是否显示已点汇总（1开启2关闭）
    public static final Long QUERY_TOTAL_SETTING = 1008001L;
    //  kds本地配置key值-是否开启每次划菜并叫号（1开启2关闭）
    public static final Long UPDATE_CALL_SETTING = 1009001L;
    //  kds本地配置key值-是否开启超时提醒（1开启2关闭）
    public static final Long TIME_OUT_REMINDER = 1010001L;


    //  kds本地配置key值-超时时间配置
    public static final Long OUT_TIME_SETTING = 1011001L;
    //  kds本地配置key值-数据时间间隔配置
    public static final Long TIME_BETWEEN_SETTING = 1012001L;
    //  kds本地配置key值-kds字体大小（1小2中3大4超大）
    public static final Long FONT_SIZE_SETTING = 1013001L;
    //  kds本地配置key值-显示主套餐
    public static final Long PKG_SETTING = 1014001L;
    //  kds本地配置key值-kds显示条数
    public static final Long PAGE_SIZE_SETTING = 1015001L;
    //  kds本地配置key值-kds叫号模板配置
    public static final Long CALL_TEMPLATE_SETTING = 1016001L;
    //  kds本地配置key值-推送打印配置
    public static final Long PUSH_PRINT_SETTING = 1017001L;
    //  kds本地配置key值-是否显示送餐/自提时间
    public static final Long SEND_PICKUP_TIME_SETTING = 1018001L;
    //  kds本地配置key值-桌牌号设置：显示自增单号（1开启2关闭）
    public static final Long CALL_BRAND_NUMBER = 1019001L;
    //  kds本地配置key值-kds做法字体个性化配置
    public static final Long METHOD_FONT_SETTING = 1020001L;


    //  kds本地配置key值-餐7的沽清URL
    public static final Long CY7_GUQING_URL = 1021001L;
    //  kds本地配置key值-行列设置（1*4:1   2*4:2）
    public static final Long RANKS_SETTING = 1022001L;
    //  kds本地配置key值-来新单提醒（1开启2关闭）
    public static final Long NEW_ORDER_REMINDER = 1023001L;
    //  kds本地配置key值-出餐并叫号（1开启2关闭）
    public static final Long SEND_ORDER_CALL_SETTING = 1024001L;
    //  kds本地配置key值-按菜模式kds字体大小（1小2中3大4超大）
    public static final Long ITEM_MODE_FONT_SIZE = 1025001L;
    //  kds本地配置key值-按菜模式kds做法字体大小（1小2中3大4超大）
    public static final Long ITEM_MODE_METHOD_FONT_SIZE = 1026001L;
    //  kds本地配置key值-桌牌号设置：显示手输牌号 （1开启2关闭）
    public static final Long CALL_TABLE_NUMBER = 1027001L;
    //  kds本地配置key值-是否开启取餐屏（1开启2关闭）
    public static final Long TAKE_OUT_SCREEN = 1028001L;
    //  kds本地配置key值-发餐操作(默认值1,1发餐叫号2发餐取餐)
    public static final Long SEND_ORDER_CALL = 1029001L;
    //  kds本地配置key值-自定义行数
    public static final Long CUSTOMIZE_LINE = 1030001L;

    //  kds本地配置key值-自定义列数
    public static final Long CUSTOMIZE_ROW = 1031001L;
    //  取餐屏所选择的kds方案配置
    public static final Long TAKE_OUT_PLAN_ID = 1038001L;
    //  kds本地配置key值-竖屏展示开关（1开启2关闭）
    public static final Long VERTICAL_SHOW = 1039001L;
    //  kds本地配置key值-同菜合并开关（1开启2关闭）
    public static final Long SAME_ITEM_MERGE = 1040001L;
    //  kds本地配置key值-合并时间配置（1开启2关闭）
    public static final Long MERGE_BETWEEN = 1041001L;
    //  kds本地配置key值-按做法拆分（1开启2关闭）
    public static final Long METHOD_SPLIT = 1042001L;
    //  kds本地配置key值-按备注拆分（1开启2关闭）
    public static final Long REMARK_SPLIT = 1043001L;
    //  kds本地配置key值-按套餐明细和单品拆分（1开启2关闭）
    public static final Long PKG_FLAG_SPLIT = 1044001L;
    //  kds本地配置key值-分批制作开关（1开启2关闭）
    public static final Long DIVIDE_MAKE = 1045001L;
    //  kds本地配置key值-操作即下屏（1开启2关闭）
    public static final Long OPER_DOWN = 1046001L;
    //  kds本地配置key值-高峰选优(0:默认 1:堂食2:外带3:外卖4:自提)
    public static final Long ORDER_PRIORITY = 1047001L;
    //  kds本地配置key值-启用按销售类型区分显示(0:默认 1:合并外卖、自提2:合并外卖、外带、自提)
    public static final Long ORDER_TYPE_GROUPING = 1048001L;
    //  kds本地配置key值-固定格子尺寸(0:默认 1:开启2:关闭)
    public static final Long FIXED_SIZE = 1049001L;

    //  kds本地配置key值-展示的类型(0:按单模式 1:按菜模式)
    public static final Long BOARD_MODE = 1050001L;

    //  kds本地配置key值-撤销按钮（1开启2关闭）
    public static final Long UNDO_SWITCH = 1051001L;

    //  kds本地配置key值-是否开启工序（1开启2关闭）
    public static final Long STEP_SWITCH = 1052001L;


    //  kds本地配置key值-做法/整单备注字号
    public static final Long METHOD_FONT_SIZE = 1053001L;
    //  kds本地配置key值-桌牌号字号
    public static final Long TABLENUMBER_FONT_SIZE = 1054001L;
    //  kds本地配置key值-完成叫号字号
    public static final Long SEND_ORDER_FONT_SIZE = 1055001L;
    //  kds本地配置key值-是否开启完成按钮（1开启2关闭）
    public static final Long SENDORDER_SWITCH = 1056001L;


}
