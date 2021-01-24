package org.algorithm.tree;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 二叉树遍历算法
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2021-01-18
 * @since 1.1.0
 */
public class TreeOrder {

    private static final TreeNode<Integer> node1, node2, node3, node4, node5, node6, node7;

    static {
        node1 = new TreeNode<>(1);
        node2 = new TreeNode<>(2);
        node3 = new TreeNode<>(3);
        node4 = new TreeNode<>(4);
        node5 = new TreeNode<>(5);
        node6 = new TreeNode<>(6);
        node7 = new TreeNode<>(7);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
    }

    public static void main(String[] args) {
        preOrder(node1);
        System.out.println("\n---------------------------------");
        midOrder(node1);
        System.out.println("\n---------------------------------");
        postOrder(node1);
        System.out.println("\n---------------------------------");
        levelOrder(node1);
        System.out.println("\n---------------------------------");
    }

    /**
     * 先序遍历
     */
    public static void preOrder(TreeNode<?> node) {
        if (Objects.isNull(node)) return;
        System.out.print(node.val + "\t");
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历
     */
    public static void midOrder(TreeNode<?> node) {
        if (Objects.isNull(node)) return;
        midOrder(node.left);
        System.out.print(node.val + "\t");
        midOrder(node.right);
    }

    /**
     * 后序遍历
     */
    public static void postOrder(TreeNode<?> node) {
        if (Objects.isNull(node)) return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val + "\t");
    }

    /**
     * 层次遍历
     */
    public static void levelOrder(TreeNode<?> node) {
        Queue<TreeNode<?>> queue = new LinkedList<>();
        if (Objects.nonNull(node)) {
            queue.offer(node);
        }

        while (!queue.isEmpty()) {
            TreeNode<?> poll = queue.poll();
            System.out.print(poll.val + "\t");
            if (Objects.nonNull(poll.left)) queue.offer(poll.left);
            if (Objects.nonNull(poll.right)) queue.offer(poll.right);
        }
    }

    static class TreeNode<T> {
        T val;
        TreeNode<T> left, right;

        public TreeNode(T val) {
            this.val = val;
        }
    }
}
