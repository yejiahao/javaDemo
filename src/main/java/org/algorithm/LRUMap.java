package org.algorithm;

import java.util.Objects;
import java.util.StringJoiner;

/**
 * Least Recently Used(LRU) 最近最少使用算法
 * <p>
 * {@code get} or {@code put} method will link last {@code Node}
 * Once <i>count</i> &gt; <i>threshold</i>, <i>head</i> removed.
 */
public class LRUMap<K, V> {
    private Node<K, V> head;

    private Node<K, V> tail;

    private int count;

    private int threshold;

    public LRUMap() {
        this(10);
    }

    public LRUMap(int threshold) {
        this.threshold = threshold;
    }

    public V get(K key) {
        Node<K, V> node = head;
        while (node != null) {
            if (node.key == key || Objects.equals(node.key, key)) {
                setTail(node);
                return node.value;
            }
            node = node.next;
        }
        return null;
    }

    public void put(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);
        if (count == 0) {
            head = tail = newNode;
        } else {
            Node<K, V> oldTail = tail;
            oldTail.next = newNode;
            newNode.prev = oldTail;
            tail = newNode;
        }
        if (++count > threshold) {
            removeHead();
        }
    }

    private void setTail(Node<K, V> node) {
        if (node != tail) {
            if (node == head) {
                head = node.next;
                head.prev = null;
            } else {
                Node<K, V> prevNode = node.prev;
                Node<K, V> nextNode = node.next;
                prevNode.next = nextNode;
                nextNode.prev = prevNode;
            }
            // link last
            Node<K, V> oldTail = tail;
            oldTail.next = node;
            node.prev = oldTail;
            node.next = null;
            tail = node;
        }
    }

    private void removeHead() {
        Node<K, V> oldHead = head;
        head = oldHead.next;
        oldHead = null;// help GC
        count--;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" -> ", "", "\n-------------------------------");
        Node<K, V> node = head;
        while (node != null) {
            joiner.add(String.format("(%s, %s)", node.key, node.value));
            node = node.next;
        }
        return joiner.toString();
    }

    public int size() {
        return count;
    }

    static class Node<K, V> {
        K key;
        V value;
        Node<K, V> prev;
        Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        LRUMap<String, Object> lruMap = new LRUMap<>(3);
        for (int i = 1; i <= 5; i++) {
            lruMap.put(i + "", i * 100);
            System.out.println("size: " + lruMap.size());
            System.out.println(lruMap);
        }

        for (int i = 5; i > 0; i--) {
            System.out.println(i + " get value: " + lruMap.get(i + ""));
            System.out.println(lruMap);
        }

        lruMap.put("x", 0);
        System.out.println(lruMap);
    }
}
