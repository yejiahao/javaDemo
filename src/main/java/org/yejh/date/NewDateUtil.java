package org.yejh.date;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

/**
 * JDK 1.8 新增的时间处理包 {@link java.time} 使用示例
 *
 * @author <a href="mailto:yejh.1248@qq.com">Ye Jiahao</a>
 * @create 2019-12-29
 * @since 1.0.0
 */
public class NewDateUtil {

    private NewDateUtil() {
        throw new AssertionError();
    }

    /**
     * 获取当前日期
     */
    public static void getCurrentDate() {
        LocalDate localDate = LocalDate.now();
        System.out.println("current Local date: " + localDate);

        LocalTime localTime = LocalTime.now();
        System.out.println("current Local time: " + localTime);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("current Local datetime: " + localDateTime);

        // 这个是作为对比
        Date date = new Date();
        System.out.println("current date: " + date);
    }

    /**
     * 获取时间戳
     */
    public static void getTimestamp() {
        Instant timestamp = Instant.now();
        System.out.println("timestamp: " + timestamp);
    }

    public static void clock() {
        // 根据系统时间返回当前时间并设置为UTC
        System.out.println("Clock.systemUTC() = " + Clock.systemUTC());
        // 根据系统时钟区域返回时间
        System.out.println("Clock.systemDefaultZone() = " + Clock.systemDefaultZone());
    }

    /**
     * 获取年、月、日信息
     */
    public static void getDetailDate() {
        LocalDate today = LocalDate.now();
        int year = today.getYear();
        int month = today.getMonthValue();
        int day = today.getDayOfMonth();

        System.out.printf("Year: %d, Month: %d, day: %d%n", year, month, day);
    }

    /**
     * 处理特定日期
     */
    public static void handleSpecialDate() {
        LocalDate date = LocalDate.of(2019, 9, 30);
        System.out.println("The specific date is: " + date);
    }

    /**
     * 判断两个日期是否相等
     */
    public static void compareDate() {
        LocalDate today = LocalDate.now();
        LocalDate targetDay = LocalDate.of(2019, 12, 29);

        if (Objects.equals(targetDay, today)) {
            System.out.printf("TODAY %s and TARGET_DAY %s are same date%n", today, targetDay);
        }
    }

    /**
     * 使用 {@link MonthDay} 处理周期性的日期
     */
    public static void cycleDate() {
        LocalDate today = LocalDate.now();
        LocalDate dateOfBirth = LocalDate.of(1992, 12, 29);

        MonthDay birthday = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(today);

        if (Objects.equals(currentMonthDay, birthday)) {
            System.out.println("Many Many happy returns of the day !!");
        } else {
            System.out.println("Sorry, today is not your birthday");
        }
    }

    /**
     * 增加小时
     */
    public static void plusHours() {
        LocalTime localTime = LocalTime.now();
        LocalTime newTime = localTime.plusHours(3);
        System.out.println("newTime: " + newTime);
    }

    /**
     * 计算前后的日期
     */
    public static void plusAndMinusDate() {
        LocalDate today = LocalDate.now();
        System.out.println("Today is: " + today);

        LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
        System.out.println("Date after 1 week: " + nextWeek);

        LocalDate prevYear = today.minus(1, ChronoUnit.YEARS);
        System.out.println("Date before 1 year: " + prevYear);
        LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
        System.out.println("Date after 1 year: " + nextYear);
    }

    /**
     * 判断日期是早于还是晚于另一个日期
     */
    public static void beforeOrAfter() {
        LocalDate today = LocalDate.now();

        LocalDate tomorrow = LocalDate.of(2020, 2, 12);
        if (tomorrow.isAfter(today)) {
            System.out.println("Tomorrow comes after today");
        }

        LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);

        if (yesterday.isBefore(today)) {
            System.out.println("Yesterday is day before today");
        }
    }

    /**
     * 时区处理
     */
    public static void getZoneTime() {
        // 设置时区
        ZoneId americaZone = ZoneId.of("America/New_York");

        LocalDateTime localDateAndTime = LocalDateTime.now();

        ZonedDateTime dateTimeInNewYork = ZonedDateTime.of(localDateAndTime, americaZone);
        System.out.println("现在的日期时间在特定的时区: " + dateTimeInNewYork);
    }

    /**
     * 使用 {@link YearMonth} 处理特定的日期
     */
    public static void checkCardExpiry() {
        YearMonth currentYearMonth = YearMonth.now();
        System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());

        YearMonth creditCardExpiry = YearMonth.of(2028, Month.FEBRUARY);
        System.out.printf("Your credit card expires on %s%n", creditCardExpiry);
    }

    /**
     * 检查闰年
     */
    public static void isLeapYear() {
        LocalDate today = LocalDate.now();
        System.out.printf("%d %s leap year", today.getYear(), today.isLeapYear() ? "is" : "is not");
    }

    /**
     * 使用 {@link Period} 计算两个日期之间的月数
     */
    public static void calcDateDays() {
        LocalDate specDay = LocalDate.of(2018, Month.MAY, 14);
        LocalDate today = LocalDate.now();

        Period period = Period.between(specDay, today);
        System.out.println("Period: " + period);
        System.out.println("Months left between specDay and today: " + period.getMonths());
    }

    /**
     * 包含时差信息的日期和时间
     */
    public static void zoneOffset() {
        LocalDateTime datetime = LocalDateTime.of(2019, Month.DECEMBER, 14, 12, 34);
        ZoneOffset offset = ZoneOffset.of("+05:30");
        OffsetDateTime date = OffsetDateTime.of(datetime, offset);
        System.out.println("DateTime with timezone offset: " + date);
    }

    /**
     * {@link Date} 和 {@link LocalDateTime} 相互切换
     */
    public static void switchDateAndLocalDateTime() {
        Date date1 = new Date();
        ZonedDateTime zonedDateTime = date1.toInstant().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime1 = zonedDateTime.toLocalDateTime();
        System.out.printf("Date: [%s] -> LocalDateTime: [%s]%n", date1, localDateTime1);

        LocalDateTime localDateTime2 = LocalDateTime.now();
        Instant instant = localDateTime2.atZone(ZoneId.systemDefault()).toInstant();
        Date date2 = Date.from(instant);
        System.out.printf("LocalDateTime: [%s] -> Date: [%s]%n", localDateTime2, date2);
    }

    /**
     * 格式化日期
     */
    public static void formatDate() {
        DateTimeFormatter formatter1 = DateTimeFormatter.BASIC_ISO_DATE;
        String dateStr = "20191229";
        LocalDate parse = LocalDate.parse(dateStr, formatter1);
        System.out.printf("String: [%s] -> LocalDate: [%s]%n", dateStr, parse);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS");
        LocalDateTime localDateTime = LocalDateTime.now();
        String format = localDateTime.format(formatter2);
        System.out.printf("LocalDateTime: [%s] -> String: [%s]%n", localDateTime, format);
    }

}
