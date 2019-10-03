package org.algorithm.string;

import java.util.*;

/**
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-09-26
 * @since x.y.z
 */
public class StringCase {

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
     */
    public static String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<>();
        for (char c : S.toCharArray()) {
            if (!stack.empty() && Objects.equals(stack.peek(), c)) {
                stack.removeElementAt(stack.size() - 1);
            } else {
                stack.add(c);
            }
        }

        StringBuilder target = new StringBuilder();
        stack.forEach(target::append);
        return target.toString();
    }

    /**
     * https://leetcode-cn.com/problems/length-of-last-word/
     * <p>
     * 给定一个仅包含大小写字母和空格 ' ' 的字符串，返回其最后一个单词的长度。
     * 如果不存在最后一个单词，请返回 0 。
     */
    public int lengthOfLastWord(String s) {
        int cnt = 0;
        boolean flag = false;
        char[] chars = s.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            if (chars[i] != ' ' && !flag) {
                flag = true;
            }
            if (flag) {
                if (chars[i] != ' ') {
                    cnt++;
                } else {
                    break;
                }
            }
        }
        return cnt;
    }

    /**
     * https://leetcode-cn.com/problems/number-of-segments-in-a-string/
     * <p>
     * 统计字符串中的单词个数，这里的单词指的是连续的不是空格的字符。
     * <p>
     * 输入："Hello, my name is John"
     * 输出：5
     */
    public int countSegments(String s) {
        int cnt = 0;
        boolean startFlag = true;
        for (char c : s.toCharArray()) {
            if (c == ' ' && !startFlag) {
                startFlag = true;
                continue;
            }
            if (c != ' ' && startFlag) {
                startFlag = false;
                cnt++;
            }
        }
        return cnt;
    }

    /**
     * https://leetcode-cn.com/problems/1-bit-and-2-bit-characters/
     * <p>
     * 有两种特殊字符。第一种字符可以用一比特0来表示。第二种字符可以用两比特(10 或 11)来表示。
     * 现给一个由若干比特组成的字符串。问最后一个字符是否必定为一个一比特字符。给定的字符串总是由0结束。
     * <p>
     * 输入：bits = [1, 0, 0]
     * 输出：true
     * 解释：唯一的编码方式是一个两比特字符和一个一比特字符。所以最后一个字符是一比特字符。
     */
    public boolean isOneBitCharacter(int[] bits) {
        for (int i = 0, len = bits.length; i < len; i++) {
            if (i == len - 1)
                return true;
            if (bits[i] == 1)
                i++;
        }
        return false;
        /*
        int cnt = 0;
        for (int i = 0, len = bits.length - 1; i < len; i++) {
            if (bits[i] == 0)
                cnt = 0;
            else
                cnt++;
        }
        return cnt % 2 == 0;
         */
    }

    /**
     * https://leetcode-cn.com/problems/first-unique-character-in-a-string/
     * <p>
     * 给定一个字符串，找到它的第一个不重复的字符，并返回它的索引。如果不存在，则返回 -1。
     * <p>
     * 输入："loveleetcode"
     * 输出：2
     */
    public int firstUniqChar(String s) {
        char[] chars = s.toCharArray();
        loop:
        for (int i = 0, len = chars.length; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (chars[i] == chars[j] && i != j) {
                    continue loop;
                }
            }
            return i;
        }
        return -1;
        /*
        Map<Character, Boolean> map = new LinkedHashMap<>(s.length());
        for (char c : s.toCharArray()) {
            if (Objects.isNull(map.get(c))) {
                map.put(c, true);
            } else if (map.get(c)) {
                map.put(c, false);
            }
        }
        for (Map.Entry<Character, Boolean> entry : map.entrySet()) {
            if (entry.getValue()) {
                return s.indexOf(entry.getKey());
            }
        }
        return -1;
         */
    }
}
