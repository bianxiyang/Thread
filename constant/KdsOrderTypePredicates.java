package cn.com.tcsl.fast.kds.server.constant;

public class KdsOrderTypePredicates {
    //  吾享归属点餐类型
    public final static java.util.function.Predicate<Long> orderOriginIds = (orderOriginId -> orderOriginId == 5 || orderOriginId == 8 );

    public final static java.util.function.Predicate<Integer> saleTypeIds = (saleTypeId -> saleTypeId == 1 || saleTypeId == 2 );

}
