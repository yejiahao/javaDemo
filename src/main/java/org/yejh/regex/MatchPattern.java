package org.yejh.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchPattern {
    public static String str = "I'm a    person lives in GuangZhou.";

    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(str);
        int count = 0;
        while (matcher.find()) {
            count++;
        }
        System.out.println("wordCount: " + count);

        str = "xxx<ResultCode>0</ResultCode>yyy";
        Pattern pattern2 = Pattern.compile(".*(<)(ResultCode>)0\\1\\/\\2.*");
        Matcher matcher2 = pattern2.matcher(str);
        if (matcher2.find()) {
            System.out.println("string match!");
        } else {
            System.out.println("string not match!");
        }
    }
}
