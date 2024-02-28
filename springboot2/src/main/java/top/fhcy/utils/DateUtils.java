package top.fhcy.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    /**
     * 获取当前时间时间戳
     */
    public static long nowTimestamp() {
        return System.currentTimeMillis();
    }

    /**
     * 格式化date
     * @param date date
     * @param pattern 格式 yyyy-MM-dd
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date, String pattern) {
        String formatDate;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, DateUtils.DATE_FORMAT_YYYY_MM_DD);
        }
        return formatDate;
    }

    /**
     * 再当前date基础上add day
     * @param date date
     * @param num 加多少天
     * @return date
     */
    public static Date addDays(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, num);
        date = calendar.getTime();
        return date;
    }

    /**
     * 再当前date基础上add month
     * @param date date
     * @param num 加多少月
     * @return date
     */
    public static Date addMonths(Date date, Integer num) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        date = calendar.getTime();
        return date;
    }
}

