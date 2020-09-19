package com.demo.api.sdk.util;

import com.google.common.collect.Lists;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * 集合拓展类
 *
 * @author jamin
 * @date 2020/9/19
 */
public class CollectionUtils extends com.demo.sdk.util.CollectionUtils {

    /**
     * 多个非空list取交集
     *
     * @param paramLists
     * @return <T> List<T>
     **/
    @SafeVarargs
    public static <T> List<T> listIntersection(List<T>... paramLists) {
        if (paramLists == null || paramLists.length == 0) {
            return Lists.newArrayList();
        }
        for (List<T> list : paramLists) {
            if (CollectionUtils.isEmpty(list)) {
                return Lists.newArrayList();
            }
        }
        List<List<T>> lists = Lists.newArrayList(paramLists);
        Optional<List<T>> result = lists.parallelStream()
                .reduce((a, b) -> {
                    a.retainAll(b);
                    return a;
                });
        return result.orElse(Lists.newArrayList());
    }

    /**
     * 按指定大小，分隔集合，将集合按规定个数分为n个部分
     *
     * @param list
     * @param len
     * @return <T> List<List<T>>
     **/
    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (CollectionUtils.isEmpty(list) || len < 1) {
            return Lists.newArrayList();
        }
        List<List<T>> result = Lists.newArrayList();
        int size = list.size();
        int count = (size + len - 1) / len;

        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, (Math.min((i + 1) * len, size)));
            result.add(subList);
        }
        return result;
    }

    /**
     * 判断是否是单个元素集合
     *
     * @param collection 集合
     * @return boolean
     */
    public static boolean isSingleton(Collection<?> collection) {
        return isNotEmpty(collection) && collection.size() == 1;
    }

    /**
     * collectToList
     *
     * @param collection 集合
     * @param mapper     表达式
     * @return <T, R> List<R>
     */
    public static <T, R> List<R> collectToList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        List<R> collect = collection.stream().map(mapper).filter(Objects::nonNull).collect(toList());
        return collect;
    }

    /**
     * collectToSet
     *
     * @param collection 集合
     * @param mapper     表达式
     * @return <T, R> Set<R>
     */
    public static <T, R> Set<R> collectToSet(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        if (isEmpty(collection)) {
            return emptySet();
        }
        return collection.stream().map(mapper).filter(Objects::nonNull).collect(toSet());
    }

    /**
     * collectToUniqueList
     *
     * @param collection
     * @param mapper
     * @return <T, R> List<R>
     */
    public static <T, R> List<R> collectToUniqueList(Collection<T> collection, Function<? super T, ? extends R> mapper) {
        if (isEmpty(collection)) {
            return emptyList();
        }
        return collection.stream().map(mapper).filter(Objects::nonNull).distinct().collect(toList());
    }

    /**
     * collectToMap
     *
     * @param collection  集合
     * @param keyMapper   key
     * @param valueMapper value
     * @return <T, K, U> Map<K, U>
     */
    public static <T, K, U> Map<K, U> collectToMap(Collection<T> collection, Function<? super T, ? extends K> keyMapper,
                                                   Function<? super T, ? extends U> valueMapper) {
        if (isEmpty(collection)) {
            return Collections.emptyMap();
        }
        return collection.stream().filter(Objects::nonNull).collect(Collectors.toMap(keyMapper, valueMapper, (a, b) -> a));
    }
}
