package org.algorithm;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;

/**
 * consistent hashing 一致性哈希算法
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-07-17
 * @since x.y.z
 */
public class ConsistencyHash<K, V> {

    private static final int DEFAULT_SIZE = 10;

    private Node<K, V>[] entry;

    private int size;

    public ConsistencyHash() {
        this(DEFAULT_SIZE);
    }

    public ConsistencyHash(int size) {
        entry = new Node[size];
    }

    /**
     * 添加元素
     */
    public void put(K k, V v) {
        int length = entry.length;
        if (size >= length) {
            // 1.5倍扩容
            int newLength = length + (length >> 1);
            // when length = 0 | 1, dilatation not take effect
            if (newLength == length) {
                newLength = DEFAULT_SIZE;
            }
            entry = Arrays.copyOf(entry, newLength);
        }
        entry[size++] = new Node<>(k, v);
    }

    /**
     * {@link #entry}数组元素按{@link Node#hashCode}值升序排列
     */
    private void sort() {
        Arrays.sort(entry, 0, size, (o1, o2) -> {
            if (o1.hashCode() < o2.hashCode()) {
                return -1;
            } else if (o1.hashCode() > o2.hashCode()) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    /**
     * 定位{@param source}所属节点
     * 当找不到hashCode较大的Node时，返回第一个Node，模拟Hash环结构
     */
    public V locate(Object source) {
        this.sort();
        for (int i = 0; i < size; i++) {
            if (entry[i].hashCode() >= source.hashCode()) {
                return entry[i].value;
            }
        }
        return entry[0].value;
    }

    private class Node<K, V> {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public int hashCode() {
            return key.hashCode();
        }
    }

    /**
     * @return 有效元素个数
     */
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "ConsistencyHash[", "]");
        Arrays.stream(entry)
                .filter(node -> Objects.nonNull(node))
                .forEach(node -> joiner.add(String.format("%n{%s, %s}", node.key, node.value)));
        return joiner.toString();
    }

    public static void main(String[] args) {
        int bound = 35;
        ConsistencyHash<Integer, String> hashRing = new ConsistencyHash<>(bound);
        for (int i = 0; i < bound; i++) {
            hashRing.put(new Random().nextInt(bound), "127.0.0." + i);
        }

        System.out.println(hashRing);
        String source = "!";
        String result = hashRing.locate(source);
        System.out.println("result: " + result);
    }
}
