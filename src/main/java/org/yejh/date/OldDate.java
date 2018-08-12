package org.yejh.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class OldDate {
    static final DateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static final DateTimeFormatter JDK8_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    static final ThreadLocal<SimpleDateFormat> LOCAL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    protected static String date2String(Date date) {
        return DF.format(date);
    }

    protected static Date string2Date(String str) throws ParseException {
        return DF.parse(str);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(date2String(new Date()));
        System.out.println("--------------------------------");
        System.out.println(string2Date("2017-12-30 10:23:45"));
        System.out.println("--------------------------------");

        Date d1 = string2Date("2015-06-29 10:23:45");
        System.out.println(d1);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d1);
        calendar.add(Calendar.MONDAY, 7);
        System.out.println(calendar.getTime());
    }
}
