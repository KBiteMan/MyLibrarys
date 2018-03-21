package date.bitman.utils_lib.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * <p>Title:时间转换类</p>
 * <p>Description:用于时间的转换</p>
 *
 * @author 张禹
 *
 */

public class TimeUtil {
    private static TimeZone mTimeZone = TimeZone.getTimeZone("Asia/Shanghai");
    public static void initTimeZone(String timeZone){
        mTimeZone = TimeZone.getTimeZone(timeZone);
    }
    /**
     * long类型时间转成字符串时间
     * @param ms    long类型时间
     * @param format    转换格式
     * @return  转换得到的字符串
     */
    public static String ms2String(long ms,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(mTimeZone);
        return sdf.format(ms);
    }

    public static String getNowTime(String format){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(mTimeZone);
        return sdf.format(date);
    }

    public static Date string2Date(String time,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(mTimeZone);
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long string2Long(String time,String format){
        Date date = string2Date(time, format);
        return date.getTime();
    }
}