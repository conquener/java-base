package com.hp.learn.base.map;

import java.io.Serializable;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 实现hashMap
 *
 * @author ritchey
 * @version v1.0
 * Description:
 * @date 2019/7/22 9:45
 */
public class NewHashMap<K,V> extends NewAbstractMap<K,V> implements NewMap<K,V> ,Cloneable,Serializable {


    private static final long serialVersionUID = -1014306064005643349L;

    /**
     * 初始化容量  必须是2的幂
     * 如果是自定义大小，这个值会自动增到 2的幂
     * 为什么?   hashcode 转化成表索引是这样计算的  index = hashcode & （arraylength - 1）
     * 如果 array是2的幂，  那么 按位与的对象就是这样的一个值  1 ， 11 ， 111 ，1111 ，  11111  这样 不同hashcode 产生的索引是不会重复的
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 最大容量 必须为 1<<30
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;
    /**
     * 没有指定构造时 使用的负载因子
     *  假设容量为 4 ， 当key的数量 为3时 也就是 4*0.75 =3 ，那么容量 * 2
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    /**
     * 树形化的阈值  添加了8个对象之后 bucket（存储桶） 结构转成Red-Black tree（红黑数）
     */
    static final int TREEIFY_THRESHOLD = 8;


    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return null;
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {

    }

    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {

    }

    @Override
    public V putIfAbSent(K key, V value) {
        return null;
    }

    @Override
    public boolean remove(K key, V value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public boolean replace(K key, V newValue) {
        return false;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return null;
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return null;
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return null;
    }
}
