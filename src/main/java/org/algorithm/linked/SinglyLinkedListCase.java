package org.algorithm.linked;

import java.util.*;

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
     * 234. 回文链表
     * <p>
     * 请判断一个链表是否为回文链表。
     * 输入: 1->2->2->1
     * 输出: true
     */
    public boolean isPalindrome(ListNode head) {
        /*
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
        */
        // 要实现 O(n) 的时间复杂度和 O(1) 的空间复杂度，需要翻转后半部分
        if (Objects.isNull(head) || Objects.isNull(head.next)) {
            return true;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (Objects.nonNull(fast) && Objects.nonNull(fast.next)) {
            fast = fast.next.next;
            slow = slow.next;
        }
        // head -> slow -> fast
        ListNode prev = null;
        while (Objects.nonNull(slow)) {
            ListNode nextVal = slow.next;
            slow.next = prev;
            prev = slow;
            slow = nextVal;
        }
        // head -> slow, prev -> slow
        while (Objects.nonNull(head) && Objects.nonNull(prev)) {
            if (head.val != prev.val) return false;
            head = head.next;
            prev = prev.next;
        }
        return true;
    }

    /**
     * https://leetcode-cn.com/problems/linked-list-cycle/
     * <p>
     * 给定一个链表，判断链表中是否有环。
     * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
     * 输入: head = [3,2,0,-4], pos = 1
     * 输出: true
     * 解释: 链表中有一个环，其尾部连接到第二个节点。
     */
    public boolean hasCycle(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (Objects.nonNull(head)) {
            if (!set.add(head)) {
                return true;
            }
            head = head.next;
        }
        return false;
    }

    /**
     * https://leetcode-cn.com/problems/delete-node-in-a-linked-list/
     * <p>
     * 请编写一个函数，使其可以删除某个链表中给定的（非末尾）节点，你将只被给定要求被删除的节点。
     * 链表中所有节点的值都是唯一的。
     * 输入: head = [4,5,1,9], node = 5
     * 输出: [4,1,9]
     * 解释: 给定你链表中值为 5 的第二个节点，那么在调用了你的函数之后，该链表应变为 4 -> 1 -> 9.
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * https://leetcode-cn.com/problems/intersection-of-two-linked-lists/
     * <p>
     * 编写一个程序，找到两个单链表相交的起始节点。
     * 在返回结果后，两个链表仍须保持原有的结构。
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (Objects.isNull(headA) || Objects.isNull(headB)) {
            return null;
        }
        Stack<ListNode> stackA = new Stack<>();
        do {
            stackA.push(headA);
        } while (Objects.nonNull(headA = headA.next));
        Stack<ListNode> stackB = new Stack<>();
        do {
            stackB.push(headB);
        } while (Objects.nonNull(headB = headB.next));
        ListNode result = null;
        ListNode temp;
        while (!stackA.isEmpty() && !stackB.isEmpty()) {
            if ((temp = stackA.pop()) != stackB.pop()) {
                break;
            }
            result = temp;
        }
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/convert-binary-number-in-a-linked-list-to-integer/
     * <p>
     * 1290. 二进制链表转整数
     * <p>
     * 给你一个单链表的引用结点 head。链表中每个结点的值不是 0 就是 1。已知此链表是一个整数数字的二进制表示形式。
     * <p>
     * 请你返回该链表所表示数字的 <em>十进制值</em> 。
     * 输入: head = [1,0,1]
     * 输出: 5
     */
    public int getDecimalValue(ListNode head) {
        int ret = head.val;
        while (Objects.nonNull(head.next)) {
            head = head.next;
            ret = (ret << 1) + head.val;
        }
        return ret;
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
