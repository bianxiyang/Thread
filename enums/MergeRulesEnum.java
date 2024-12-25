package cn.com.tcsl.fast.kds.server.enums;



import cn.com.tcsl.fast.kds.vo.KdsQueryItemMode;

import java.util.*;
import java.util.function.Function;

/**
 * <b>功能：</b>按菜模式，拆分的规则<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/10/19 17:30&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public enum MergeRulesEnum {

    M(1, Collections.singletonList("1"), "做法", 1, null),
    R(2, Collections.singletonList("2"), "备注", 1, null),
    P(3, Collections.singletonList("3"), "套餐", 1, null),
    T(4, Collections.singletonList("4"), "时间", 1, null),
    M_R(5, Arrays.asList("1", "2"), "做法+备注", 2, null),
    M_P(6, Arrays.asList("1", "3"), "做法+套餐", 2, null),
    M_T(7, Arrays.asList("1", "4"), "做法+时间", 2, null),
    R_P(8, Arrays.asList("2", "3"), "备注+套餐", 2, null),
    P_T(9, Arrays.asList("3", "4"), "套餐+时间", 2, null),
    R_T(10, Arrays.asList("2", "4"), "备注+时间", 2, null),
    M_R_P(11, Arrays.asList("1", "2", "3"), "做法+备注+套餐", 3, null),
    M_R_T(12, Arrays.asList("1", "2", "4"), "做法+备注+时间", 3, null),
    M_P_T(13, Arrays.asList("1", "3", "4"), "做法+套餐+时间", 3, null),
    R_P_T(14, Arrays.asList("2", "3", "4"), "备注+套餐+时间", 3, null),
    M_R_P_T(15, Arrays.asList("1", "2", "3", "4"), "做法+备注+套餐+时间", 4, null),
    UNKNOWN(16, Arrays.asList(), "未找到", 0, null);


    private int code;
    private List<String> list;
    private String message;
    private int length;

    private Function<KdsQueryItemMode, List<Object>> function;


    private MergeRulesEnum(int code, List<String> list, String message, int length, Function<KdsQueryItemMode, List<Object>> function) {
        this.code = code;
        this.list = list;
        this.message = message;
        this.length = length;
        this.function = function;
    }

    public List<String> getList() {
        return this.list;
    }

    public String getMessage() {
        return this.message;
    }

    public int getCode() {
        return this.code;
    }

    public Function<KdsQueryItemMode, List<Object>> getFunction() {
        return this.function;
    }


    public static List<MergeRulesEnum> getEnumByLength(int length) {
        List<MergeRulesEnum> enums = new ArrayList<>();
        for (MergeRulesEnum item : MergeRulesEnum.values()) {
            if (Objects.equals(item.getList().size(), length)) {
                enums.add(item);
            }
        }
        return enums;
    }


    public static MergeRulesEnum getEnumByValue(List<String> values) {
        //  1.寻找对应枚举
        int size = values.size();
        MergeRulesEnum mergeRulesEnum = MergeRulesEnum.UNKNOWN;

        //  2.给枚举的Function赋值，返回Function
        if (size == 4) {
            mergeRulesEnum = MergeRulesEnum.M_R_P_T;
            mergeRulesEnum.function = kdsQueryItemMode ->
                    Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getPkgFlag());
        } else {
            List<MergeRulesEnum> enumList = MergeRulesEnum.getEnumByLength(size);
            for (MergeRulesEnum item : enumList) {
                if (new HashSet<>(item.getList()).containsAll(values)) {
                    mergeRulesEnum = item;
                    Function<KdsQueryItemMode, List<Object>> functionItem = mergeRulesEnum.getFunction();
                    if (null == functionItem) {
                        int code = mergeRulesEnum.getCode();
                        switch (code) {
                            case 1:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr());
                                break;
                            case 2:
                                functionItem = kdsQueryItemMode ->

                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getRemark());
                                break;
                            case 3:
                                functionItem = kdsQueryItemMode ->

                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getPkgFlag());
                                break;


                            case 4:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getCreateTime());
                                break;


                            case 5:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getRemark());
                                break;


                            case 6:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getPkgFlag());
                                break;
                            case 7:

                                functionItem = kdsQueryItemMode ->

                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getCreateTime());
                                break;
                            case 8:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getPkgFlag());
                                break;
                            case 9:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getPkgFlag(), kdsQueryItemMode.getCreateTime());
                                break;
                            case 10:

                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getCreateTime());
                                break;
                            case 11:
                                functionItem = kdsQueryItemMode ->

                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getPkgFlag());
                                break;


                            case 12:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getCreateTime());
                                break;
                            case 13:
                                functionItem = kdsQueryItemMode ->
                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getPkgFlag(), kdsQueryItemMode.getCreateTime());
                                break;
                            case 14:

                                functionItem = kdsQueryItemMode ->

                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getPkgFlag(), kdsQueryItemMode.getCreateTime());
                                break;
                            case 15:

                                functionItem = kdsQueryItemMode ->

                                        Arrays.asList(kdsQueryItemMode.getSkuId(), kdsQueryItemMode.getMethodIdStr(), kdsQueryItemMode.getRemark(), kdsQueryItemMode.getPkgFlag(), kdsQueryItemMode.getCreateTime());
                                break;


                            case 16:
                                functionItem = kdsQueryItemMode ->
                                        Collections.singletonList(kdsQueryItemMode.getSkuId());
                                break;

                            default:
                                functionItem = kdsQueryItemMode ->
                                        Collections.singletonList(kdsQueryItemMode.getSkuId());
                                break;
                        }


                    }

                    mergeRulesEnum.function = functionItem;


                }
            }
        }
        return mergeRulesEnum;
    }


}
