package com.hp.learn.base.map;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * map 顶层接口
 *
 * @author ritchey
 */
public interface NewMap<K, V> {

    /**
     * 大小
     *
     * @return
     */
    int size();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 是否包含键
     *
     * @param key
     * @return
     */
    boolean containsKey(Object key);

    /**
     * 是否包含值
     *
     * @param value
     * @return
     */
    boolean containsValue(Object value);

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    V get(Object key);

    /**
     * set 键值
     *
     * @param key
     * @param value
     * @return
     */
    V put(K key, V value);

    /**
     * 移除键值
     *
     * @param key
     * @return
     */
    V remove(K key);

    /**
     * set map
     *
     * @param m
     */
    void putAll(NewMap<? extends K, ? extends V> m);


    /**
     * 数据清洗
     */
    void clear();

    /**
     * key collection
     *
     * @return
     */
    Set<K> keySet();

    /**
     * value collection
     *
     * @return
     */
    Collection<V> values();

    /**
     * views of the map
     *
     * @return
     */
    Set<NewMap.Entry<K, V>> entrySet();


    interface Entry<K, V> {

        /**
         * 获取键
         *
         * @return
         */
        K getKey();

        /**
         * 获取值
         *
         * @return
         */
        V getValue();

        /**
         * 替换entry 中键对应的值 并且写入map中
         *
         * @return
         */
        V setValue(V value);

        /**
         * 满足两个条件
         * 1. 两者 键相等，或者均为空 (e1.getKey()==null ?e2.getKey()==null : e1.getKey().equals(e2.getKey()))
         * 2. 两者 值相等 或者值均为空 (e1.getValue()==null ?e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         *
         * @param o
         * @return
         */
        boolean equals(Object o);

        /**
         * (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^ (e.getValue()==null ? 0 : e.getValue().hashCode())
         *
         * @return the hash code value for this map entry
         * @see Object#hashCode()
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        int hashCode();

        /**
         * by 键 比较
         *
         * @param <K>
         * @param <V>
         * @return
         */
        public static <K extends Comparable<? super K>, V> Comparator<NewMap.Entry<K, V>> comparingByKey() {

            return (Comparator<NewMap.Entry<K, V>> & Serializable) (o1, o2) -> o1.getKey().compareTo(o2.getKey());

        }

        /**
         * by value
         *
         * @param <K>
         * @param <V>
         * @return
         */
        public static <K, V extends Comparable<? super V>> Comparator<NewMap.Entry<K, V>> comparingByValue() {

            return (Comparator<NewMap.Entry<K, V>> & Serializable) (c1, c2) -> c1.getValue().compareTo(c2.getValue());
        }

        public static <K, V> Comparator<NewMap.Entry<K, V>> comparingByKey(Comparator<? super K> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<NewMap.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getKey(), c2.getKey());
        }


        public static <K, V> Comparator<NewMap.Entry<K, V>> comparingByValue(Comparator<? super V> cmp) {
            Objects.requireNonNull(cmp);
            return (Comparator<NewMap.Entry<K, V>> & Serializable)
                    (c1, c2) -> cmp.compare(c1.getValue(), c2.getValue());
        }
    }


    boolean equals(Object o);


    int hashCode();

    default V getOrDefault(Object key, V defaultValue) {
        V v;
        return (((v = get(key)) != null) || containsKey(key))
                ? v
                : defaultValue;
    }

    default void forEach(BiConsumer<? super K, ? super V> action) {
        Objects.requireNonNull(action);
        for (NewMap.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }

    }

    default void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        Objects.requireNonNull(function);
        for (NewMap.Entry<K, V> entry : entrySet()) {
            K k;
            V v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }

            v = function.apply(k, v);

            try {
                entry.setValue(v);
            } catch (IllegalStateException ise) {
                throw new ConcurrentModificationException(ise);
            }
        }
    }

    /**
     * 如果指定的key 对应值为null 就放回参数中 给的值， 并且put到map中， 如果不为null 就返回mapzhong 的值
     *
     * @param key
     * @param value
     * @return
     */
    default V putIfAbSent(K key, V value) {
        V v = get(key);
        if (v == null) {
            v = put(key, value);
        }

        return v;
    }

    /**
     * 当key 存在 并且 value 相等 的时候 才移除键
     *
     * @param key
     * @param value
     * @return
     */
    default boolean remove(K key, V value) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, value)
                || (curValue == null && !containsKey(key))) {
            return false;
        }
        remove(key);
        return true;
    }

    /**
     * 替换 当 map中 key 存在 并且提供的oldValue 等于 map中值时 进行值替换
     *
     * @param key
     * @param oldValue
     * @param newValue
     * @return
     */
    default boolean replace(K key, V oldValue, V newValue) {
        Object curValue = get(key);
        if (!Objects.equals(curValue, oldValue)
                || (curValue == null && !containsKey(key))) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    /**
     * 替换值，仅当键存在时
     *
     * @param key
     * @param newValue
     * @return
     */
    default boolean replace(K key, V newValue) {
        Object curValue = get(key);
        // 一开始想不通 为什么 明明 containsKey(key) 这个条件判断的结果集 是 真包含 curValue == null 的 ,为什么要多此一举 判断curValue == null呢?
        // 这个想了一下, 为什么，因为执行速度， 对于 curValue 不等于null 的数据 ，curValue == null 条件 能够迅速判断出来结果 ，就不需要 整个map扫描
        //然后反思了自己以前的代码，是否有做这个方面的考虑呢？
        if (curValue != null || containsKey(key)) {
            return false;
        }
        put(key, newValue);
        return true;
    }

    /**
     * 如果 key 对应的值为null 那么设置为 指定方法计算的值 （map中键不存在,也会put值）
     * 如果不存在 则set0
     * @param key
     * @param mappingFunction
     * @return
     */
    default V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        Objects.requireNonNull(mappingFunction);
        V v;
        if ((v = get(key)) == null) {
            V newValue;
            if ((newValue = mappingFunction.apply(key)) != null) {
                put(key, newValue);
                return newValue;
            }
        }
        return v;
    }

    /**
     * 仅当值存在时 进行计算
     *
     * @param key
     * @param remappingFunction
     * @return
     */
    default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);
        if (oldValue != null) {
            V newValue = remappingFunction.apply(key, oldValue);
            if (newValue != null) {
                put(key, newValue);
                return newValue;
            } else {
                remove(key);
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 计算
     *  如果key 对应的值存在 通过 定义的计算方法 计算出新的值， 如果计算的值为null，并且键存在 那么移除键
     * @param key
     * @param remappingFunction
     * @return
     */
    default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        V oldValue = get(key);
        V newValue = remappingFunction.apply(key, oldValue);
        if (newValue == null) {
            if (oldValue != null || containsKey(key)) {
                remove(key);
                return null;
            } else {
                return null;
            }
        } else {
            put(key, newValue);
            return newValue;
        }
    }

    /**
     * merge 如果以前键 对应值存在 那么通过指定的方法 计算出新的值，值不存在 那么直接set为新的值（新的值为null时 ，移除key）
     * @param key
     * @param value
     * @param remappingFunction
     * @return
     */
    default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value);
        V oldValue = get(key);
        V newValue = (oldValue == null) ? value :
                remappingFunction.apply(oldValue, value);
        if (newValue == null) {
            remove(key);
        } else {
            put(key, newValue);
        }
        return newValue;
    }


}
