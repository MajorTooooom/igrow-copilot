package com.zhien.common.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 时间工具类
 *
 * @author zhien
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YY = "yy";

    public static String MM = "MM";

    public static String YYMMDD = "yyMMdd";

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYY_MM_DD_CN = "yyyy年MM月dd日";

    public static String YYYYMMDD = "yyyyMMdd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String getMillisTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS_SSS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String dateTimestamp(final Date date) {
        if (date == null) {
            return null;
        }
        return parseDateToStr(YYYY_MM_DD_HH_MM_SS, date);
    }

    public static final String dateTimeYYYYMMDD(final Date date) {
        return parseDateToStr(YYYYMMDD, date);
    }

    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String dateTime) {
        try {
            return new SimpleDateFormat(format).parse(dateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 时间转换成时间戳,参数和返回值都是字符串
     *
     * @param s
     * @return res
     * @throws ParseException
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        // 设置时间模版
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 获取服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算两个时间相差天数
     */
    public static long getDateDay(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;

        return day;
    }


    /**
     * 计算两个时间差(无负数)
     */
    public static int getDateGapDay(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        if (diff < 0) {
            diff = -diff;
        }
        // 计算差多少天
        long day = diff / nd;
        return (int) day;
    }

    /**
     * 计算两个时间差(无负数)
     */
    public static String getDateGap(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        if (diff < 0) {
            diff = -diff;
        }
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 计算结束时间比，timeUnit为空返回0
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long getDateGap(Date startDate, Date endDate, TimeUnit timeUnit) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        if (diff < 0) {
            diff = -diff;
        }
        if (TimeUnit.DAYS.equals(timeUnit)) {
            return diff / nd;
        } else if (TimeUnit.HOURS.equals(timeUnit)) {
            return diff % nd / nh;
        } else if (TimeUnit.MINUTES.equals(timeUnit)) {
            return diff % nd % nh / nm;
        } else {
            return 0L;
        }
    }

    /**
     * 比较两个日期大小
     *
     * @param endDate
     * @param nowDate
     * @return 结束日期大于开始日期，返回true；反之则返回false
     */
    public static boolean compareDate(Date endDate, Date nowDate) {
        if ((endDate.getTime() - nowDate.getTime()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean compareDate(String endDate, String nowDate) {
        if ((DateUtils.parseDate(endDate).getTime() - DateUtils.parseDate(nowDate).getTime()) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 计算两个时间差精确值毫秒
     */
    public static String getDatePoorHM(Date endDate, Date nowDate) {
        long between = 0;
        between = (endDate.getTime() - nowDate.getTime());// 得到两者的毫秒数
        long day = between / (24 * 60 * 60 * 1000);
        long hour = (between / (60 * 60 * 1000) - day * 24);
        long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒";
    }

    public static String subDays(String format, Date date, int days) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.DAY_OF_MONTH, days);
        Date dt1 = rightNow.getTime();
        String reStr = sdf.format(dt1);
        return reStr;
    }

    // 日期属于当年第几周
    public static int weekOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }


    // 前几小时的时间
    public static Date beforeHourDate(Date date, int hours) {
        long time = date.getTime() - hours * 60 * 60 * 1000;
        return new Date(time);
    }

    // 前几天的时间
    public static Date beforeDayDate(Date date, int days) {
        long time = date.getTime() - days * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    // 未来几天的时间
    public static Date afterDayDate(Date date, int days) {
        long time = date.getTime() + days * 24 * 60 * 60 * 1000;
        return new Date(time);
    }

    // 未来几天的时间(可小数)
    public static Date afterDayDate(Date date, double days) {
        Double tt = days * 24 * 60 * 60 * 1000;
        long time = date.getTime() + tt.longValue();
        return new Date(time);
    }


    // 未来几分钟的时间
    public static Date afterMinuteDate(Date date, int minutes) {
        long time = date.getTime() + minutes * 60 * 1000;
        return new Date(time);
    }

    // 未来几秒钟的时间
    public static String afterSecondDate(Date date, int seconds) {
        long time = date.getTime() + seconds * 1000;
        return DateUtils.dateTimestamp(new Date(time));
    }

    // 日期取整 @since 2022-03-14
    public static Date truncTime(Date date) {
        final long millis = 24 * 60 * 60 * 1000;
        return new Date((date.getTime() + TimeZone.getDefault().getRawOffset()) / millis * millis - TimeZone.getDefault().getRawOffset());
    }

    /**
     * 按照格式获取日期
     *
     * @param pattern
     * @return
     */
    public static String getDateWithFormat(Date date, String pattern) {
        return parseDateToStr(pattern, date);
    }

    // 获取一月前时间
    public static Date getDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date time = calendar.getTime();
        return time;
    }

    // 获取6个月前时间
    public static Date gethalfAYearDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -6);
        Date time = calendar.getTime();
        return time;
    }

    // 获取3个月前时间
    public static Date getThreeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -3);
        Date time = calendar.getTime();
        return time;
    }

    /**
     * 计算两个时间差返回总小时数
     */
    public static BigDecimal getDatePoorHours(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        long fen = 60;
        double totalHour = min * 1.0 / fen;
        totalHour = totalHour + day * 24 + hour;
        BigDecimal totalHours = new BigDecimal(totalHour);
        totalHours = totalHours.setScale(2, RoundingMode.HALF_UP);
        return totalHours;
    }

    /**
     * 月份增减
     *
     * @param date  当前日期
     * @param month 增减几个月
     * @return
     */
    public static Date getLastDate(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    /**
     * 获取下个月第一天的日期
     *
     * @param date
     * @return
     */
    public static Date getNextMonthFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号
        Date start = cal.getTime();
        return start;
    }

    /**
     * 获取下个月最后一天日期
     *
     * @param date
     * @return
     */
    public static Date getNextMonthLastDate() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, 2);
        cal.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号
        Date start = cal.getTime();
        return start;
    }

    /**
     * 获取当个月第一天日期
     *
     * @param date
     * @return
     */
    public static Date getCurrentMonthFirstDate() {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号
        Date start = cal.getTime();
        return start;
    }

    /**
     * 获取上个月第一天的日期
     *
     * @param date
     * @return
     */
    public static Date getLastFirstDate(Date date) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号
        Date start = cal.getTime();
        return start;
    }

    /**
     * 获取上个月最后一天的日期
     *
     * @param date
     * @return
     */
    public static Date getLastEndDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
        Date end = cal.getTime();
        return end;
    }

    /**
     * 获取当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getCurrentMonthLastEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        Date end = cal.getTime();
        return end;
    }

    /*
     * 获取给定日期所在月份的最后一天，并将时间设置为23:59:59
     * */
    public static Date getLastSecondOfMonth(Date date) {
        // 创建一个Calendar实例并设置为给定日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        // 将日期设置为下个月的第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, 1);

        // 将时间设置为23:59:59
        calendar.add(Calendar.SECOND, -1);

        // 返回结果
        return calendar.getTime();
    }

    public static String getFirstOfMonth(Date date) {
        // 创建一个Calendar实例并设置为给定日期
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//
//		// 将日期设置为下个月的第一天
//		calendar.set(Calendar.DAY_OF_MONTH, -1);
//		calendar.add(Calendar.MONTH, 1);
//		// 返回结果
//		return DateUtils.parseDateToStr(format, calendar.getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        Date end = cal.getTime();
        return DateUtils.parseDateToStr(YYYY_MM_DD, end);
    }

    /**
     * 获取上个月最后一天
     *
     * @return
     */
    public static Date getPrevMonthLastEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.add(Calendar.MONTH, 0);
        cal.add(Calendar.DATE, -1);
        Date end = cal.getTime();
        return end;
    }

    /**
     * 获取上上个月最后一天
     *
     * @return
     */
    public static Date getPrevPrevMonthLastEndDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        cal.add(Calendar.MONTH, -1);
        cal.add(Calendar.DATE, -1);
        Date end = cal.getTime();
        return end;
    }

    /**
     * 判断日期是否是周末
     *
     * @param bDate
     * @return
     * @throws ParseException
     */
    public static boolean isWeekend(Date bDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(bDate);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }
    }

    // 获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
        }
        return new Timestamp(calendar.getTimeInMillis());
    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
            calendar.set(Calendar.MILLISECOND, 999);
        }
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 获取上个月第一天的日期
     *
     * @param date
     * @return
     */
    public static Date getLastFirstDate1(Date date) {
        Calendar cal = Calendar.getInstance();// 获取当前日期
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号
        Date start = cal.getTime();
        return start;
    }

    /**
     * 获取上个月最后一天的日期
     *
     * @param date
     * @return
     */
    public static Date getLastEndDate1(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 0);// 设置为1号,当前日期既为本月第一天
        Date end = cal.getTime();
        return end;
    }


    /**
     * 获取本周的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfWeek(Date date) {
        // Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取本周的结束时间
     *
     * @return
     */
    public static Date getEndDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    /**
     * 获取对应月份的第一天时间
     *
     * @return
     */
    public static Date getFirstDayByMonth(int month) {
        Calendar calstr = Calendar.getInstance();
        calstr.set(Calendar.MONTH, month);
        // 设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH, 1);
        return calstr.getTime();
    }

    /**
     * 获取对应月份的最后一天时间
     *
     * @return
     */
    public static Date getLastDayByMonth(int month) {
        Calendar calstr = Calendar.getInstance();
        calstr.set(Calendar.MONTH, month);
        // 设置为1号为本月第一天
        calstr.set(Calendar.DAY_OF_MONTH, 1);
        // 设置当月为最后一天
        calstr.set(Calendar.DAY_OF_MONTH, calstr.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calstr.getTime();
    }


    /**
     * 给日期增加天数
     */
    public static Date getDateSumDays(Date date, int days) {
        System.out.print("增加前====" + date + "====增加天数===" + days);
        Calendar calstr = Calendar.getInstance();
        calstr.setTime(date);
        calstr.add(Calendar.DATE, days);
        System.out.print("增加后====" + calstr.getTime());
        return calstr.getTime();
    }

    /**
     * 根据年月获取月初第一天日期
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDay(int year, int month, String format) {
        Calendar cale = Calendar.getInstance();

        cale.set(Calendar.YEAR, year);    // 赋值年份
        cale.set(Calendar.MONTH, month - 1);// 赋值月份
        int lastDay = cale.getActualMinimum(Calendar.DAY_OF_MONTH);// 获取月最大天数
        cale.set(Calendar.DAY_OF_MONTH, lastDay);// 设置日历中月份的最大天数
        SimpleDateFormat sdf = new SimpleDateFormat(format);// 格式化日期yyyy-MM-dd
        String lastDayOfMonth = sdf.format(cale.getTime());
        return lastDayOfMonth;
    }


    /**
     * 根据年月获取月末最后一天日期
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(int year, int month, String format) {
        Calendar cale = Calendar.getInstance();

        cale.set(Calendar.YEAR, year);// 赋值年份
        cale.set(Calendar.MONTH, month - 1);// 赋值月份
        int lastDay = cale.getActualMaximum(Calendar.DAY_OF_MONTH);// 获取月最大天数
        cale.set(Calendar.DAY_OF_MONTH, lastDay);// 设置日历中月份的最大天数
        SimpleDateFormat sdf = new SimpleDateFormat(format);    // 格式化日期yyyy-MM-dd
        String lastDayOfMonth = sdf.format(cale.getTime());
        return lastDayOfMonth;
    }

    /**
     * @return 返回最近12个月的日期
     */
    public static List<String> getLast12Months() {
        ArrayList<String> latest12Months = new ArrayList<>(12);
        Date date = new Date();
        date = DateUtils.getLastDate(date, -1);
        Instant instant = date.toInstant();
        LocalDate today = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        for (long i = 11L; i >= 0L; i--) {
            LocalDate localDate = today.minusMonths(i);
            String ss = localDate.toString().substring(0, 7);
            // ss = ss + "-01";
            latest12Months.add(ss);
        }
        return latest12Months;
    }

    /**
     * 获取当前系统时间最近几个月月的年月（含当月）
     * 2018-04~2019-03
     */
    public static List<String> getLastMonths(Integer month) {
        ArrayList<String> latest12Months = new ArrayList<>(month);
        Date date = new Date();
        // date = DateUtils.getLastDate(date,-1);

        Instant instant = date.toInstant();
        LocalDate today = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        for (long i = 12L; i >= 0L; i--) {
            LocalDate localDate = today.minusMonths(i);
            String ss = localDate.toString().substring(0, 7);
            // ss = ss + "-01";
            latest12Months.add(ss);
        }
        return latest12Months;
    }

    /**
     * 传入两个时间范围，返回这两个时间范围内的所有日期，并保存在一个集合中
     *
     * @param beginTime
     * @param endTime
     * @return
     * @throws Exception
     */
    public static List<String> findEveryDay(Date beginTime, Date endTime)
            throws Exception {
        // 创建一个放所有日期的集合
        List<String> dates = new ArrayList();

        // 创建时间解析对象规定解析格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 将传入的时间解析成Date类型,相当于格式化
        Date dBegin = sdf.parse(sdf.format(beginTime));

        // 将传入的时间解析成Date类型,相当于格式化
        Date dEnd = sdf.parse(sdf.format(endTime));

        // 将格式化后的第一天添加进集合
        dates.add(sdf.format(dBegin));

        // 使用本地的时区和区域获取日历
        Calendar calBegin = Calendar.getInstance();

        // 传入起始时间将此日历设置为起始日历
        calBegin.setTime(dBegin);

        // 判断结束日期前一天是否在起始日历的日期之后
        while (dEnd.after(calBegin.getTime())) {

            // 根据日历的规则:月份中的每一天，为起始日历加一天
            calBegin.add(Calendar.DAY_OF_MONTH, 1);

            // 得到的每一天就添加进集合
            dates.add(sdf.format(calBegin.getTime()));
            // 如果当前的起始日历超过结束日期后,就结束循环
        }
        return dates;
    }


    /**
     * @Description: 获取季度最后一天
     * @Author: wsp
     **/
    public static Date getQuarterEnd(Date date) { // 季度结束
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        // 计算季度数：由于月份从0开始，即1月份的Calendar.MONTH值为0,所以计算季度的第三个月份只需 月份 / 3 * 3 + 2
        endCalendar.set(Calendar.MONTH, (((int) endCalendar.get(Calendar.MONTH)) / 3) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return endCalendar.getTime();
    }
//	/**
//	 * 月份增减
//	 * @param date  当前日期
//	 * @param month 增减几个月
//	 * @return
//	 */
//	public static Date getLastDate(Date date, int month) {
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		calendar.add(Calendar.MONTH, month);
//		return calendar.getTime();
//	}

    /**
     * 获取两个日期相差的月数
     *
     * @param d1 较大的日期
     * @param d2 较小的日期
     * @return 如果d1>d2返回 月数差 否则返回0
     */
    public static int getMonthDiff(Date d1, Date d2) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(d1);
        c2.setTime(d2);
        if (c1.getTimeInMillis() < c2.getTimeInMillis()) return 0;
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        int yearInterval = year1 - year2;
        if (month1 < month2 || month1 == month2 && day1 < day2) yearInterval--;
        int monthInterval = (month1 + 12) - month2;
        if (day1 < day2) monthInterval--;
        monthInterval %= 12;
        return yearInterval * 12 + monthInterval;
    }

    /**
     * 根据传的月份数量返回最近的月份集合
     * 2018-04~2019-03
     */
    public static List<String> getLastMonthsByMonth(Integer month) {
        ArrayList<String> latest12Months = new ArrayList<>(month);
        Date date = new Date();
        // date = DateUtils.getLastDate(date,-1);

        Instant instant = date.toInstant();
        LocalDate today = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        for (long i = month - 1; i >= 0; i--) {
            LocalDate localDate = today.minusMonths(i);
            String ss = localDate.toString().substring(0, 7);
            // ss = ss + "-01";
            latest12Months.add(ss);
        }
        return latest12Months;
    }

    /**
     * 获取上个月月份
     *
     * @return
     */
    public static final String getLastMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        // 设置为上一个月
        // calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        date = calendar.getTime();
        return format.format(date);
    }
}
