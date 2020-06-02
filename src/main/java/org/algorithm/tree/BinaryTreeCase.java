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

    /**
     * https://leetcode-cn.com/problems/convert-bst-to-greater-tree/
     * <p>
     * 538. 把二叉搜索树转换为累加树
     * <p>
     * 给定一个二叉搜索树（Binary Search Tree），把它转换成为累加树（Greater Tree)，使得每个节点的值是原来的节点值加上所有大于它的节点值之和。
     */
    int num = 0;

    @SuppressWarnings("UnusedReturnValue")
    public TreeNode convertBST(TreeNode root) {
        if (Objects.nonNull(root)) {
            // 遍历右子树
            convertBST(root.right);
            // 遍历顶点
            root.val += num;
            num = root.val;
            // 遍历左子树
            convertBST(root.left);
            return root;
        }
        return null;
    }

    /**
     * https://leetcode-cn.com/problems/subtree-of-another-tree/
     * <p>
     * 572. 另一个树的子树
     * <p>
     * 给定两个非空二叉树 s 和 t，检验 s 中是否包含和 t 具有相同结构和节点值的子树。s 的一个子树包括 s 的一个节点和这个节点的所有子孙。
     * s 也可以看做它自身的一棵子树。
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (Objects.isNull(s)) {
            return false;
        }
        if (s.val == t.val && calIsSubTree(s, t)) return true;
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    private boolean calIsSubTree(TreeNode s, TreeNode t) {
        boolean sNull = Objects.isNull(s);
        boolean tNull = Objects.isNull(t);
        if (sNull && tNull) return true;
        if (sNull || tNull) return false;
        if (s.val != t.val) return false;

        return calIsSubTree(s.left, t.left) && calIsSubTree(s.right, t.right);
    }

    /**
     * https://leetcode-cn.com/problems/merge-two-binary-trees/
     * <p>
     * 617. 合并二叉树
     * <p>
     * 给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
     * <p>
     * 你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        boolean t1Null = Objects.isNull(t1);
        boolean t2Null = Objects.isNull(t2);
        if (t1Null && t2Null) return null;
        if (t1Null) return t2;
        if (t2Null) return t1;
        TreeNode ret = new TreeNode(t1.val + t2.val);
        ret.left = mergeTrees(t1.left, t2.left);
        ret.right = mergeTrees(t1.right, t2.right);
        return ret;
    }

    /**
     * https://leetcode-cn.com/problems/increasing-order-search-tree/
     * <p>
     * 897. 递增顺序查找树
     * <p>
     * 给你一个树，请你 <em>按中序遍历</em> 重新排列树，使树中最左边的结点现在是树的根，并且每个结点没有左子结点，只有一个右子结点。
     */
    public TreeNode increasingBST(TreeNode root) {
        List<Integer> list = new ArrayList<>(); // 从小到大
        calIncreasingBST(root, list);
        TreeNode head = new TreeNode(-1); // 构造虚拟节点
        TreeNode tmp = head;
        for (Integer integer : list) {
            tmp.right = new TreeNode(integer);
            tmp = tmp.right;
        }
        return head.right;
    }

    private void calIncreasingBST(TreeNode node, List<Integer> list) {
        if (Objects.nonNull(node)) {
            calIncreasingBST(node.left, list);
            list.add(node.val);
            calIncreasingBST(node.right, list);
        }
    }

    /**
     * https://leetcode-cn.com/problems/binary-tree-paths/
     * <p>
     * 257. 二叉树的所有路径
     * <p>
     * 给定一个二叉树，返回所有从根节点到叶子节点的路径。
     * <p>
     * 输入:
     * 1
     * /   \
     * 2     3
     * \
     * 5
     * 输出: ["1->2->5", "1->3"]
     */
    @SuppressWarnings("unchecked")
    public List<String> binaryTreePaths(TreeNode root) {
        if (Objects.isNull(root)) return Collections.EMPTY_LIST;
        calBinaryTreePaths(root, "");
        return binaryTreePathsList;
    }

    private final List<String> binaryTreePathsList = new ArrayList<>();

    private void calBinaryTreePaths(TreeNode node, String val) {
        String newVal = val + (Objects.equals(val, "") ? "" : "->") + node.val;
        if (Objects.nonNull(node.left)) {
            calBinaryTreePaths(node.left, newVal);
        }
        if (Objects.nonNull(node.right)) {
            calBinaryTreePaths(node.right, newVal);
        }
        if (Objects.isNull(node.left) && Objects.isNull(node.right)) {
            binaryTreePathsList.add(newVal);
        }
    }

    /**
     * https://leetcode-cn.com/problems/binary-tree-level-order-traversal-ii/
     * <p>
     * 107. 二叉树的层次遍历 II
     * <p>
     * 给定一个二叉树，返回其节点值自底向上的层次遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历）
     * <p>
     * 输入: [3,9,20,null,null,15,7]
     * 输出: [[15,7], [9,20], [3]]
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<List<Integer>> ret = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>(); // 队列存放层次遍历元素

        if (Objects.nonNull(root)) queue.offer(root);

        while (true) {
            int size = queue.size();
            if (size == 0) return ret;

            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                list.add(node.val);
                TreeNode left, right;
                if (Objects.nonNull(left = node.left)) {
                    queue.offer(left);
                }
                if (Objects.nonNull(right = node.right)) {
                    queue.offer(right);
                }
            }
            ret.addFirst(list);
        }
    }

    /**
     * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
     * <p>
     * 108. 将有序数组转换为二叉搜索树
     * <p>
     * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
     * <p>
     * 本题中，一个高度平衡二叉树是指一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1。
     * <p>
     * 输入: [-10,-3,0,5,9]
     * 输出: [0,-3,9,-10,null,5]
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return calSortedArrayToBST(nums, 0, nums.length - 1);
    }

    private TreeNode calSortedArrayToBST(int[] nums, int low, int high) {
        if (low > high) return null;
        int mid = low + high >>> 1;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = calSortedArrayToBST(nums, low, mid - 1);
        root.right = calSortedArrayToBST(nums, mid + 1, high);
        return root;
    }

    /**
     * https://leetcode-cn.com/problems/sum-of-left-leaves/
     * <p>
     * 404. 左叶子之和
     * <p>
     * 计算给定二叉树的所有左叶子之和。
     * <p>
     * 示例：
     * 3
     * / \
     * 9  20
     * /  \
     * 15   7
     * <p>
     * 在这个二叉树中，有两个左叶子，分别是 9 和 15，所以返回 24
     */
    public int sumOfLeftLeaves(TreeNode root) {
        if (Objects.isNull(root)) return 0;
        calSumOfLeftLeaves(root, "r"); // 父节点不算左叶子，👍
        return sumOfLeftLeavesVal;
    }

    private int sumOfLeftLeavesVal = 0;

    public void calSumOfLeftLeaves(TreeNode root, String lr) {
        boolean lNull = Objects.isNull(root.left);
        boolean rNull = Objects.isNull(root.right);
        if (!lNull) calSumOfLeftLeaves(root.left, "l");
        if (!rNull) calSumOfLeftLeaves(root.right, "r");
        if (lNull && rNull && Objects.equals(lr, "l")) sumOfLeftLeavesVal += root.val;
    }

    /**
     * https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
     * <p>
     * 235. 二叉搜索树的最近公共祖先
     * <p>
     * 最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”
     * <p>
     * 输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * 输出: 6
     * 解释: 节点 2 和节点 8 的最近公共祖先是 6。
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int pDiff = root.val - p.val;
        int qDiff = root.val - q.val;
        if (pDiff * qDiff <= 0) return root;
        if (pDiff < 0 && qDiff < 0) return lowestCommonAncestor(root.right, p, q);
        else return lowestCommonAncestor(root.left, p, q);
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
