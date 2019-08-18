package org.algorithm;

import java.util.Objects;
import java.util.Stack;

/**
 * https://leetcode-cn.com/problems/remove-all-adjacent-duplicates-in-string/
 * <p>
 * 给出由小写字母组成的字符串 S，重复项删除操作会选择两个相邻且相同的字母，并删除它们。
 * <p>
 * e.g.
 * input: "abbaca"
 * output: "ca"
 * explain: 在 "abbaca" 中，我们可以删除 "bb" 由于两字母相邻且相同，这是此时唯一可以执行删除操作的重复项。
 * 之后我们得到字符串 "aaca"，其中又只有 "aa" 可以执行重复项删除操作，所以最后的字符串为 "ca"。
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-08-18
 * @since x.y.z
 */
public class RemoveDuplicateLetter {

    private static String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<>();
        for (char c : S.toCharArray()) {
            if (!stack.empty() && Objects.equals(stack.peek(), c)) {
                stack.removeElementAt(stack.size() - 1);
            } else {
                stack.add(c);
            }
        }

        StringBuilder target = new StringBuilder();
        stack.forEach(c -> target.append(c));
        return target.toString();
    }

}
