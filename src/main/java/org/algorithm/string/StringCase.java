package org.algorithm.string;

import java.util.*;
import java.util.stream.Collectors;

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
     * 如果不存在最后一个单词，请返回 0。
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

    /**
     * https://leetcode-cn.com/problems/find-common-characters/
     * <p>
     * 给定仅有小写字母组成的字符串数组 A，返回列表中的每个字符串中都显示的全部字符（包括重复字符）组成的列表。
     * 例如，如果一个字符在每个字符串中出现 3 次，但不是 4 次，则需要在最终答案中包含该字符 3 次。
     * <p>
     * 输入：["bella","label","roller"]
     * 输出：["e","l","l"]
     */
    public List<String> commonChars(String[] A) {
        // 字符串中字符数组升序排列
        String[] A1 = new String[A.length];
        for (int i = 0, length = A.length; i < length; i++) {
            char[] chars = A[i].toCharArray();
            Arrays.sort(chars);
            A1[i] = new String(chars);
        }

        String[] letters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
                "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        List<String> result = new ArrayList<>();
        flag:
        for (String letter : letters) {
            String temp = letter;
            while (true) {
                for (String str : A1) {
                    if (!str.contains(temp)) {
                        continue flag;
                    }
                }
                result.add(letter);
                temp += letter;
            }
        }
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/reverse-string/
     * <p>
     * 编写一个函数，其作用是将输入的字符串反转过来。输入字符串以字符数组 char[] 的形式给出。
     * 不要给另外的数组分配额外的空间，你必须原地修改输入数组、使用 O(1) 的额外空间解决这一问题。
     * 你可以假设数组中的所有字符都是 ASCII 码表中的可打印字符。
     * <p>
     * 输入：["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     */
    public void reverseString(char[] s) {
        LinkedList<Character> list = new LinkedList<>();
        for (char c : s) {
            list.add(c);
        }
        for (int i = 0, len = s.length; i < len; i++) {
            s[i] = list.removeLast();
        }
    }

    /**
     * https://leetcode-cn.com/problems/reverse-string-ii/
     * <p>
     * 给定一个字符串和一个整数 k，你需要对从字符串开头算起的每个 2k 个字符的前k个字符进行反转。
     * 如果剩余少于 k 个字符，则将剩余的所有全部反转。
     * 如果有小于 2k 但大于或等于 k 个字符，则反转前 k 个字符，并将剩余的字符保持原样。
     * <p>
     * 输入：s = "abcdefg", k = 2
     * 输出："bacdfeg"
     * 要求：1. 该字符串只包含小写的英文字母。2. 给定字符串的长度和 k 在[1, 10000]范围内。
     */
    public String reverseStr(String s, int k) {
        List<Character> list = new LinkedList<>();
        int index = 0; // 插入位置
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (i % (k << 1) < k) {
                list.add(index, chars[i]);
            } else {
                list.add(chars[i]);
                index = list.size();
            }
        }
        return list.stream().map(Objects::toString).collect(Collectors.joining());
    }

    /**
     * https://leetcode-cn.com/problems/reverse-words-in-a-string-iii/
     * <p>
     * 给定一个字符串，你需要反转字符串中每个单词的字符顺序，同时仍保留空格和单词的初始顺序。
     * <p>
     * 输入："Let's take LeetCode contest"
     * 输出："s'teL ekat edoCteeL tsetnoc"
     * 注意：在字符串中，每个单词由单个空格分隔，并且字符串中不会有任何额外的空格。
     */
    public String reverseWords(String s) {
        List<Character> list = new LinkedList<>();
        int index = 0; // 插入位置
        for (char c : s.toCharArray()) {
            if (c == ' ') {
                list.add(c);
                index = list.size();
            } else {
                list.add(index, c);
            }
        }
        return list.stream().map(Objects::toString).collect(Collectors.joining());
    }

    /**
     * https://leetcode-cn.com/problems/last-stone-weight/
     * <p>
     * 有一堆石头，每块石头的重量都是正整数。
     * <p>
     * 每一回合，从中选出两块 最重的 石头，然后将它们一起粉碎。假设石头的重量分别为 x 和 y，且 x <= y。那么粉碎的可能结果如下：
     * <p>
     * 如果 x == y，那么两块石头都会被完全粉碎；
     * 如果 x != y，那么重量为 x 的石头将会完全粉碎，而重量为 y 的石头新重量为 y-x。
     * 最后，最多只会剩下一块石头。返回此石头的重量。如果没有石头剩下，就返回 0。
     * <p>
     * 输入：[2,7,4,1,8,1]
     * 输出：1
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());
        for (int i : stones) {
            pq.offer(i);
        }
        while (pq.size() >= 2) {
            int first = pq.poll();
            @SuppressWarnings("ConstantConditions")
            int second = pq.poll();
            if (first > second) {
                pq.offer(first - second);
            }
        }
        return pq.size() == 1 ? pq.peek() : 0;
    }

    /**
     * https://leetcode-cn.com/problems/split-a-string-in-balanced-strings/
     * <p>
     * 在一个「平衡字符串」中，'L' 和 'R' 字符的数量是相同的。
     * <p>
     * 给出一个平衡字符串 s，请你将它分割成尽可能多的平衡字符串。
     * <p>
     * 返回可以通过分割得到的平衡字符串的最大数量。
     * <p>
     * 输入：s = "RLRRLLRLRL" | "RLLLLRRRLR" | "LLLLRRRR"
     * 输出：4 | 3 | 1
     * 解释：["RL", "RRLL", "RL", "RL"] | ["RL", "LLLRRR", "LR"] | ["LLLLRRRR"]
     */
    public int balancedStringSplit(String s) {
        int ret = 0, tmp = 0;
        for (char c : s.toCharArray()) {
            if (c == 'L') {
                tmp++;
            } else {
                tmp--;
            }
            if (tmp == 0) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * https://leetcode-cn.com/problems/shortest-distance-to-a-character/
     * <p>
     * 给定一个字符串 S 和一个字符 C。返回一个代表字符串 S 中每个字符到字符串 S 中的字符 C 的最短距离的数组。
     * <p>
     * 输入：S = "loveleetcode", C = 'e'
     * 输出：[3, 2, 1, 0, 1, 0, 0, 1, 2, 2, 1, 0]
     */
    public int[] shortestToChar(String S, char C) {
        char[] chars = S.toCharArray();
        int charsLen;
        int[] ret = new int[charsLen = chars.length];
        // 构造出指定升序下标数组 [3, 5, 6, 11]
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < charsLen; i++) {
            if (chars[i] == C) {
                list.add(i);
            }
        }

        for (int i = 0; i < charsLen; i++) {
            for (int j = 0, size = list.size(); j < size; j++) {
                int low = list.get(j);
                if (j == 0 && i <= low) {
                    ret[i] = low - i;
                    break;
                }
                if (j == size - 1 && i >= low) {
                    ret[i] = i - low;
                    break;
                }
                int high = list.get(j + 1);
                if (i >= low && i < high) {
                    ret[i] = Math.min(i - low, high - i);
                    break;
                }
            }
        }
        return ret;
    }

    /**
     * https://leetcode-cn.com/problems/count-number-of-nice-subarrays/
     * <p>
     * 给你一个整数数组 nums 和一个整数 k。
     * <p>
     * 如果某个 连续 子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。
     * <p>
     * 请返回这个数组中「优美子数组」的数目。
     * <p>
     * 输入：nums = [1,1,2,1,1], k = 3 | nums = [2,4,6], k = 1 | nums = [2,2,2,1,2,2,1,2,2,2], k = 2
     * 输出：2 | 0 | 16
     */
    @SuppressWarnings("SpellCheckingInspection")
    public int numberOfSubarrays(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        list.add(-1); // 构造左读取下标
        int len;
        for (int i = 0; i < (len = nums.length); i++) {
            if (nums[i] % 2 == 1) list.add(i); // 添加奇数元素下标
        }
        list.add(len); // 构造右读取下标

        int result = 0;
        int a = 0;
        while (a + k + 1 < list.size()) {
            int lChoice = list.get(a + 1) - list.get(a);
            int rChoice = list.get(a + k + 1) - list.get(a + k);
            result += lChoice * rChoice;
            a++;
        }
        return result;
    }

    /**
     * https://leetcode-cn.com/problems/generate-a-string-with-characters-that-have-odd-counts/
     * <p>
     * 给你一个整数 n，请你返回一个含 n 个字符的字符串，其中每种字符在该字符串中都恰好出现 奇数次。
     * <p>
     * 返回的字符串必须只含小写英文字母。如果存在多个满足题目要求的字符串，则返回其中任意一个即可。
     * <p>
     * 输入：n = 4 | n = 2 | n = 7
     * 输出："pppz" | "xy" | "holasss"
     */
    public String generateTheString(int n) {
        if (n <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0, size = n - 1; i < size; i++) {
            sb.append('a');
        }
        if (n % 2 == 1) {
            sb.append('a');
        } else {
            sb.append('b');
        }
        return sb.toString();
    }

    /**
     * https://leetcode-cn.com/problems/increasing-decreasing-string/
     * <p>
     * 给你一个字符串 s ，请你根据下面的算法重新构造字符串：
     * <p>
     * 1. 从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。
     * 2. 从 s 剩余字符中选出 最小 的字符，且该字符比上一个添加的字符大，将它 接在 结果字符串后面。
     * 3. 重复步骤 2，直到你没法从 s 中选择字符。
     * 4. 从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。
     * 5. 从 s 剩余字符中选出 最大 的字符，且该字符比上一个添加的字符小，将它 接在 结果字符串后面。
     * 6. 重复步骤 5，直到你没法从 s 中选择字符。
     * 7. 重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。
     * 在任何一步中，如果最小或者最大字符不止一个，你可以选择其中任意一个，并将其添加到结果字符串。
     * <p>
     * 请你返回将 s 中字符重新排序后的 结果字符串。
     * <p>
     * 输入：s = "aaaabbbbcccc" | "rat" | "leetcode" | "ggggggg" | "spo"
     * 输出："abccbaabccba" | "art" | "cdelotee" | "ggggggg" | "ops"
     * 解释：第一轮的步骤 1，2，3 后，结果字符串为 result = "abc"
     * 第一轮的步骤 4，5，6 后，结果字符串为 result = "abccba"
     * 第一轮结束，现在 s = "aabbcc" ，我们再次回到步骤 1
     * 第二轮的步骤 1，2，3 后，结果字符串为 result = "abccbaabc"
     * 第二轮的步骤 4，5，6 后，结果字符串为 result = "abccbaabccba"
     */
    public String sortString(String s) {
        StringBuilder sb = new StringBuilder();
        int[] arr = new int[26];
        for (char c : s.toCharArray()) {
            arr[c - 97]++;
        }
        boolean naturalOrder = true; // 正序遍历
        while (sb.length() != s.length()) {
            if (naturalOrder) {
                for (int i = 0; i < 26; i++) {
                    if (arr[i] > 0) {
                        sb.append((char) (i + 97));
                        arr[i]--;
                    }
                }
            } else {
                for (int i = 25; i > -1; i--) {
                    if (arr[i] > 0) {
                        sb.append((char) (i + 97));
                        arr[i]--;
                    }
                }
            }
            naturalOrder = !naturalOrder; // 改变遍历顺序
        }
        return sb.toString();
    }

    /**
     * https://leetcode-cn.com/problems/reverse-vowels-of-a-string/
     * <p>
     * 345. 反转字符串中的元音字母
     * <p>
     * 编写一个函数，以字符串作为输入，反转该字符串中的元音字母。
     * <p>
     * 输入: "leetcode"
     * 输出: "leotcede"
     */
    public String reverseVowels(String s) {
        List<Character> vowels = Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U');
        List<Object[]> list = new ArrayList<>();
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (vowels.contains(chars[i])) {
                Object[] objs = new Object[2];
                objs[0] = i;
                objs[1] = chars[i];
                list.add(objs);
            }
        }
        for (int i = 0, size = list.size(); i < size; i++) {
            Object[] objs = list.get(i);
            Object[] newObjs = list.get(size - 1 - i);
            chars[(int) objs[0]] = (char) newObjs[1];
        }
        return new String(chars);
    }

    /**
     * https://leetcode-cn.com/problems/remove-element/
     * <p>
     * 27. 移除元素
     * <p>
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * <p>
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     */
    public int removeElement(int[] nums, int val) {
        int j = 0;
        for (int i = 0, len = nums.length; i < len; i++) {
            if (nums[i] != val) {
                nums[j++] = nums[i];
            }
        }
        return j;
    }
}
