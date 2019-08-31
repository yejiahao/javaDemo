package org.algorithm.linked;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-08-26
 * @since x.y.z
 */
public class SinglyLinkedListCase {

    /**
     * https://leetcode-cn.com/problems/reverse-linked-list/
     * <p>
     * 反转一个单链表。
     * 输入: 1->2->3->4->5->NULL
     * 输出: 5->4->3->2->1->NULL
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        while (Objects.nonNull(head)) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }

    /**
     * https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list/
     * <p>
     * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
     * 输入: 1->1->2->3->3
     * 输出: 1->2->3
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode temp = head; // 头结点的临时指针（不可能删除头结点）
        while (Objects.nonNull(head) && Objects.nonNull(head.next)) {
            if (head.val == head.next.val) {
                head.next = head.next.next;
            } else {
                head = head.next;
            }
        }
        return temp;
    }

    /**
     * https://leetcode-cn.com/problems/remove-linked-list-elements/
     * <p>
     * 删除链表中等于给定值 val 的所有节点。
     * 输入: 1->2->6->3->4->5->6, val = 6
     * 输出: 1->2->3->4->5
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode prev = new ListNode(-1);
        prev.next = head;
        ListNode temp = prev;
        while (Objects.nonNull(head)) {
            if (head.val == val) {
                prev.next = head = head.next;
            } else {
                prev = head;
                head = head.next;
            }
        }
        return temp.next;
    }

    /**
     * https://leetcode-cn.com/problems/middle-of-the-linked-list/
     * <p>
     * 给定一个带有头结点 head 的非空单链表，返回链表的中间结点。
     * 如果有两个中间结点，则返回第二个中间结点。
     * 输入: 1->2->3->4->5->6
     * 输出: 此列表中的结点 4 (序列化形式：[4,5,6])
     */
    public ListNode middleNode(ListNode head) {
        ListNode temp = head;
        int cnt = 0;
        do {
            ++cnt;
        } while (Objects.nonNull(head = head.next));
        int midCnt = (cnt >> 1) + 1;
        head = temp; // 头部指针复原
        cnt = 0;
        do {
            if (++cnt == midCnt) {
                return head;
            }
        } while (Objects.nonNull(head = head.next));
        throw new AssertionError();
    }

    /**
     * https://leetcode-cn.com/problems/palindrome-linked-list/
     * <p>
     * 请判断一个链表是否为回文链表。
     * 输入: 1->2->2->1
     * 输出: true
     */
    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode prev = null;
        while (Objects.nonNull(head)) {
            list.add(head.val);
            ListNode temp = head.next;
            head.next = prev;
            prev = head;
            head = temp;
        }
        while (Objects.nonNull(prev)) {
            if (list.remove(0) != prev.val) {
                return false;
            }
            prev = prev.next;
        }
        return true;
    }
}

/**
 * Definition for singly-linked list.
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}
