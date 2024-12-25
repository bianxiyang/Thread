package cn.com.tcsl.fast.kds.server.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * <b>功能：</b>集合工具类，封装部分stream流的常用操作<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;2023/3/13 20:08&nbsp;&nbsp;快餐产品事业部&nbsp;&nbsp;&nbsp;&nbsp;边喜洋&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 *
 * </ul>
 */
public class CollUtils extends CollectionUtils {


    public static boolean isEmpty(Object[] arrays) {
        return arrays == null || arrays.length == 0;
    }


    /**
     * 转换指定类型List集合
     *
     * @param from 源数据
     * @param func 映射规则
     * @param <T>  源数据类型
     * @param <U>  返回值类型
     * @return 指定U类型List
     */
    public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    /**
     * 转换指定类型Set集合
     *
     * @param from 源数据
     * @param func 映射规则
     * @param <T>  源数据类型
     * @param <U>  返回值类型
     * @return 指定U类型List
     */
    public static <T, U> Set<U> convertSet(List<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toSet());
    }

    /**
     * 给定List按照 keyFunc 规则转换成Map集合
     *
     * @param from    源数据
     * @param keyFunc 映射规则
     * @param <T>     Map Value 类型
     * @param <K>     Map Key 类型
     * @return Map
     */
    public static <T, K> Map<K, T> convertMap(List<T> from, Function<T, K> keyFunc) {
        if (isEmpty(from)) {
            return new HashMap<>();
        }
        return from.stream().collect(Collectors.toMap(keyFunc, item -> item, (k1, k2) -> k1));
    }

    /**
     * 给定List按照 指定Key Value 转换成Map
     *
     * @param from      源数据
     * @param keyFunc   key映射规则
     * @param valueFunc value映射规则
     * @param <T>       源数据类型
     * @param <K>       Map Key 类型
     * @param <V>       Map Value 类型
     * @return Map
     */
    public static <T, K, V> Map<K, V> convertMap(List<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        return from.stream().collect(Collectors.toMap(keyFunc, valueFunc, (v1, v2) -> v1));
    }

    /**
     * 给定List按照 指定Key Value 转换成Map
     *
     * @param from 源数据
     * @param <T>  源数据类型
     * @return Map
     */
    public static <T> Map<Boolean, List<T>> partitioningBy(List<T> from, Predicate<T> predicate) {
        return from.stream().collect(Collectors.partitioningBy(predicate));
    }

    /**
     * 给定List按照 指定Key Value 转换成 Map<K, List<V>>
     *
     * @param from      源数据
     * @param keyFunc   key映射规则
     * @param valueFunc value映射规则
     * @param <T>       源数据类型
     * @param <K>       Map Key 类型
     * @param <V>       Map Value 类型
     * @return Map
     */
    public static <T, K, V> Map<K, List<V>> convertMultiMap(List<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        return from.stream().collect(Collectors.groupingBy(keyFunc,
                Collectors.mapping(valueFunc, Collectors.toList())));
    }

    /**
     * 给定List按照 指定Key Value 转换成 Map<K, Set<V>>
     *
     * @param from      源数据
     * @param keyFunc   key映射规则
     * @param valueFunc value映射规则
     * @param <T>       源数据类型
     * @param <K>       Map Key 类型
     * @param <V>       Map Value 类型
     * @return Map
     */
    public static <T, K, V> Map<K, Set<V>> convertMultiMapValueIsSet(List<T> from, Function<T, K> keyFunc, Function<T, V> valueFunc) {
        return from.stream().collect(Collectors.groupingBy(keyFunc, Collectors.mapping(valueFunc, Collectors.toSet())));
    }

    /**
     * 扁平化处理数据
     *
     * @param from 源数据
     * @param <T>  返回值类型
     * @return List
     */
    public static <T> List<T> convertList(Collection<List<T>> from) {
        return from.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }


}