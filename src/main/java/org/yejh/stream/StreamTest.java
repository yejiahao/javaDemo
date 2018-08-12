package org.yejh.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class StreamTest {
    private static List<List<String>> dataList() {
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("apple", "click"));
        lists.add(Arrays.asList("boss", "dig", "qq", "vivo"));
        lists.add(Arrays.asList("c#", "biezhi"));
        return lists;
    }

    private static void oldMethod(List<List<String>> lists) {
        int count = 0;
        for (int i = 0; i < lists.size(); i++) {
            List<String> subList = lists.get(i);
            for (int j = 0; j < subList.size(); j++) {
                String str = subList.get(j);
                if (str.length() > 2) {
                    count++;
                }
            }
        }
        System.out.println("oldMethod: " + count);
    }

    private static void newMethod(List<List<String>> lists) {
        long result = lists.stream().flatMap(Collection::stream).filter(str -> str.length() > 2).count();
        System.out.println("newMethod: " + result);
    }

    public static void main(String[] args) {
        oldMethod(dataList());
        newMethod(dataList());
    }
}
