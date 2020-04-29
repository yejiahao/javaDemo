package org.algorithm.tree;

import java.util.*;
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
        List<List<Integer>> list = new ArrayList<>();
        averageOfLevels(root, list, 0);
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

    /**
     * https://leetcode-cn.com/problems/univalued-binary-tree/
     * <p>
     * 如果二叉树每个节点都具有相同的值，那么该二叉树就是单值二叉树。
     */
    public boolean isUnivalTree(TreeNode root) {
        return Objects.isNull(root) // 只用于根节点
                || (Objects.isNull(root.left) || root.val == root.left.val && isUnivalTree(root.left)) // 左节点 result
                && (Objects.isNull(root.right) || root.val == root.right.val && isUnivalTree(root.right)); // 右节点 result
    }

    /**
     * https://leetcode-cn.com/problems/binary-tree-tilt/
     * <p>
     * 给定一个二叉树，计算整个树的坡度。
     * 一个树的节点的坡度定义即为，该节点左子树的结点之和和右子树结点之和的差的绝对值。空结点的的坡度是0。
     * 整个树的坡度就是其所有节点的坡度之和。
     */
    public int findTilt(TreeNode root) {
        if (Objects.isNull(root)) {
            return 0;
        }
        List<Integer> list = new ArrayList<>();
        findTilt(root, list);
        return list.stream().mapToInt(i -> i).sum();
    }

    private int findTilt(TreeNode root, List<Integer> list) {
        int lVal = Objects.isNull(root.left) ? 0 : root.left.val + findTilt(root.left, list);
        int rVal = Objects.isNull(root.right) ? 0 : root.right.val + findTilt(root.right, list);
        list.add(Math.abs(lVal - rVal));
        return lVal + rVal;
    }

    /**
     * https://leetcode-cn.com/problems/diameter-of-binary-tree/
     * <p>
     * 给定一棵二叉树，你需要计算它的直径长度。
     * 一棵二叉树的直径长度是任意两个结点路径长度中的最大值。这条路径可能穿过根结点。
     * 注意：两结点之间的路径长度是以它们之间边的数目表示。
     */
    private int diameterOfBinaryTreeMax;

    public int diameterOfBinaryTree(TreeNode root) {
        calDiameterOfBinaryTree(root);
        return diameterOfBinaryTreeMax;
    }

    private int calDiameterOfBinaryTree(TreeNode root) {
        if (Objects.isNull(root)) {
            return -1;
        }
        int leftLen = calDiameterOfBinaryTree(root.left);
        int rightLen = calDiameterOfBinaryTree(root.right);
        // 直径 = 左层数 + 右层数 + 2
        diameterOfBinaryTreeMax = Math.max(diameterOfBinaryTreeMax, Math.addExact(leftLen, rightLen) + 2);
        return Math.max(leftLen, rightLen) + 1; // 层数
    }

    /**
     * https://leetcode-cn.com/problems/balanced-binary-tree/
     * <p>
     * 给定一个二叉树，判断它是否是高度平衡的二叉树。
     * 一棵高度平衡二叉树定义为：一个二叉树每个节点的左右两个子树的高度差的绝对值不超过1。
     */
    public boolean isBalanced(TreeNode root) {
        Map<String, Integer> result = calBalance(root);
        return result.get("b") == 1;
    }

    private Map<String, Integer> calBalance(TreeNode node) {
        Map<String, Integer> result = new HashMap<>(); // b -> 节点是否平衡, d -> 节点深度
        if (Objects.isNull(node)) {
            result.put("b", 1);
            result.put("d", 0);
        } else {
            Map<String, Integer> leftResult = calBalance(node.left);
            Map<String, Integer> rightResult = calBalance(node.right);
            int leftDepth = leftResult.get("d");
            int rightDepth = rightResult.get("d");
            result.put("b", leftResult.get("b") & rightResult.get("b") & (Math.abs(leftDepth - rightDepth) >> 1 ^ 1));
            result.put("d", Math.max(leftDepth, rightDepth) + 1);
        }
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/leaf-similar-trees/
     * <p>
     * 请考虑一颗二叉树上所有的叶子，这些叶子的值按从左到右的顺序排列形成一个 叶值序列
     * <p>
     * 如果有两颗二叉树的叶值序列是相同，那么我们就认为它们是 叶相似 的。
     * <p>
     * 如果给定的两个头结点分别为 root1 和 root2 的树是叶相似的，则返回 true；否则返回 false 。
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        calLeafSimilar(root1, list1);
        calLeafSimilar(root2, list2);
        return list1.equals(list2);
    }

    private void calLeafSimilar(TreeNode root, List<Integer> list) {
        TreeNode left = root.left;
        TreeNode right = root.right;
        if (Objects.nonNull(left)) {
            calLeafSimilar(left, list);
        }
        if (Objects.nonNull(right)) {
            calLeafSimilar(right, list);
        }
        if (Objects.isNull(left) && Objects.isNull(right)) {
            list.add(root.val);
        }
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
