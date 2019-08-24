package org.algorithm.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-08-25
 * @since x.y.z
 */
public class BinaryTreeCase {

    /**
     * https://leetcode-cn.com/problems/symmetric-tree/
     * <p>
     * 给定一个二叉树，检查它是否是镜像对称的。
     */
    public boolean isSymmetric(TreeNode root) {
        return isSymmetric(root, root);
    }

    private boolean isSymmetric(TreeNode leftNode, TreeNode rightNode) {
        if (Objects.isNull(leftNode) && Objects.isNull(rightNode)) {
            return true;
        }
        if (Objects.nonNull(leftNode) && Objects.isNull(rightNode) || Objects.isNull(leftNode) || leftNode.val != rightNode.val) {
            return false;
        }
        return isSymmetric(leftNode.left, rightNode.right) && isSymmetric(leftNode.right, rightNode.left);
    }

    /**
     * https://leetcode-cn.com/problems/invert-binary-tree/
     * <p>
     * 翻转一棵二叉树。
     */
    public TreeNode invertTree(TreeNode root) {
        if (Objects.nonNull(root)) {
            TreeNode temp = root.left;
            root.left = root.right;
            root.right = temp;
            invertTree(root.left);
            invertTree(root.right);
        }
        return root;
    }

    /**
     * https://leetcode-cn.com/problems/average-of-levels-in-binary-tree/
     * <p>
     * 给定一个非空二叉树，返回一个由每层节点平均值组成的数组。
     */
    public List<Double> averageOfLevels(TreeNode root) {
        int level = 0;
        List<List<Integer>> list = new ArrayList<>();
        list.add(level++, Collections.singletonList(root.val));
        averageOfLevels(root.left, list, level);
        averageOfLevels(root.right, list, level);
        return list.stream()
                .map(v -> v.stream()
                        .mapToDouble(Integer::doubleValue)
                        .average()
                        .getAsDouble())
                .collect(Collectors.toList());
    }

    private void averageOfLevels(TreeNode root, List<List<Integer>> list, int level) {
        if (Objects.nonNull(root)) {
            int nextLevel = level + 1;
            if (list.size() < nextLevel) {
                list.add(level, new ArrayList<Integer>(nextLevel) {
                    {
                        add(root.val);
                    }
                });
            } else {
                list.get(level).add(root.val);
            }
            averageOfLevels(root.left, list, nextLevel);
            averageOfLevels(root.right, list, nextLevel);
        }
    }

    /**
     * https://leetcode-cn.com/problems/maximum-depth-of-binary-tree/
     * <p>
     * 给定一个二叉树，找出其最大深度。
     */
    public int maxDepth(TreeNode root) {
        int i = 0;
        if (Objects.nonNull(root)) {
            return maxDepth(root, i + 1);
        }
        return i;
    }

    private int maxDepth(TreeNode root, int i) {
        int leftDepth = i;
        int rightDepth = i;
        if (Objects.nonNull(root.left)) {
            leftDepth = maxDepth(root.left, i + 1);
        }
        if (Objects.nonNull(root.right)) {
            rightDepth = maxDepth(root.right, i + 1);
        }
        return Math.max(leftDepth, rightDepth);
    }
}

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}
