package tech.yunjing.biconlife.liblkclass.common.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class LKTimeUtil {


    /**
     * 带今昨前天时间获取
     *
     * @param mil
     * @return
     */
    public static String getTime(long mil) {
        Calendar cale = Calendar.getInstance();
        String str;
        if (mil == 0) {
            str = "刚刚";
            return str;
        }

        cale.setTime(new Date(mil));
        long diffDay = 0L;

        Calendar curDate = new GregorianCalendar();
        long curTime = System.currentTimeMillis();
        curDate.setTime(new Date(curTime));
        curDate.set(Calendar.HOUR_OF_DAY, 23);
        curDate.set(Calendar.MINUTE, 59);
        curDate.set(Calendar.SECOND, 59);
        curDate.set(Calendar.MILLISECOND, 999);

        diffDay = (curDate.getTimeInMillis() - mil) / 86400000L;
        String time = getStrTime(mil, "HH:mm");
        boolean judge = diffDay < 0 || diffDay > 2;
        if (judge) {
            str = getStrTime(mil, "yyyy/MM/dd");
            // 显示原始时间
        } else if (diffDay > 1) {
            str = "前天";
        } else if (diffDay > 0) {
            str = "昨天";
        } else {
            str = time;
        }
        return str;
    }

    private static String getHoursMin(long miln) {
        String str = "";
        int hours = (int) miln / 3600;
        int hoursYu = (int) miln % 3600;
        if (hours > 0) {
            str = hours + "小时";
            return str;
        }
        if (hoursYu > 0) {
            str = str + hoursYu / 60 + "分钟";
        }

        return str;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param lon   long类型时间
     * @param model 时间显示模式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStrTime(Long lon, String model) {
        // "yyyy年MM月dd日HH时mm分ss秒"
        String reStrTime;
        SimpleDateFormat sdf = new SimpleDateFormat(model);
        // 例如：cc_time=1291778220
        reStrTime = sdf.format(new Date(lon));
        return reStrTime;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param userTime 时间字符串
     * @param model     时间显示模式
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getTime(String userTime, String model) {
        String reTime = null;
        // "yyyy年MM月dd日HH时mm分ss秒"
        SimpleDateFormat sdf = new SimpleDateFormat(model);
        Date d;
        try {
            d = sdf.parse(userTime);
            long l = d.getTime();
            String str = String.valueOf(l);
            reTime = str.substring(0, str.length());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reTime;
    }


    public static final int SECONDS_IN_DAY = 60 * 60 * 24;
    public static final long MILLIS_IN_DAY = 1000L * SECONDS_IN_DAY;

    /**
     * 判断是否为一天
     *
     * @param ms1
     * @param ms2
     * @return
     */
    public static boolean isSameDayOfMillis(final long ms1, final long ms2) {
        final long interval = ms1 - ms2;
        return interval < MILLIS_IN_DAY
                && interval > -1L * MILLIS_IN_DAY
                && toDay(ms1) == toDay(ms2);
    }

    private static long toDay(long millis) {
        return (millis + TimeZone.getDefault().getOffset(millis)) / MILLIS_IN_DAY;
    }

    /**
     * 获取时间字符串，今天为时分显示，非今天为日期显示
     *
     * @param millis
     * @return
     */
    public static String getDayOrHours(String millis) {
        String strTime = "";
        if (!TextUtils.isEmpty(millis)) {
            Long millisLong = Long.valueOf(millis);
            Date data = new Date();
            long nowTime = data.getTime();
            boolean sameDayOfMillis = isSameDayOfMillis(nowTime, millisLong);
            //"yyyy年MM月dd日HH时mm分ss秒"
            if (sameDayOfMillis) {
                strTime = getStrTime(millisLong, "HH:mm");
            } else {
                strTime = getStrTime(millisLong, "yyyy/MM/dd");
//              strTime = getStrTime(millisLong, "MM月dd日 HH:mm");
            }
        }
        return strTime;
    }

    /**
     * 获取两个时间之间的间隔
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getTimeInterval(String time1, String time2) {
        long longIntervalTime = Math.abs(Long.valueOf(time1) - Long.valueOf(time2));
        //间隔的分钟
        int timeInterval = (int) (longIntervalTime / 1000 / 60);
        return timeInterval;
    }

    /**
     * 获取两个时间之间的间隔
     *
     * @param time1
     * @param time2
     * @return
     */
    public static int getTimeInterval(long time1, long time2) {
        long longIntervalTime = Math.abs(time1 - time2);
        //间隔的分钟
        int timeInterval = (int) (longIntervalTime / 1000 / 60);
        return timeInterval;
    }


    /**
     * 获取时间字符串，今天为时分显示，非今天为日期显示
     *
     * @param millis
     * @return
     */
    public static String getDayOrHours(long millis) {
        String strTime = "";
        Date data = new Date();
        long nowTime = data.getTime();
        boolean sameDayOfMillis = isSameDayOfMillis(nowTime, millis);
        //"yyyy年MM月dd日HH时mm分ss秒"
        if (sameDayOfMillis) {
            strTime = getStrTime(millis, "HH:mm");
        } else {
            strTime = getStrTime(millis, "MM/dd HH:mm");
        }
        return strTime;
    }

    /**
     * 具体时间转换成时间戳
     */
    public static long getLongTime(String curtime, String model) throws ParseException {
        long time;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(model);
        Date date = simpleDateFormat.parse(curtime);
        time = date.getTime();
        return time;
    }

    /**
     * 对时间做处理，显示时间与当前时间的对比结果，如今天、昨天
     */
    public static String getQuLiangTime(long mil) {
        String str = "";
        Calendar cale = Calendar.getInstance();
        try {
            cale.setTime(new Date(mil));
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
        long diffDay = 0L;

        Calendar curDate = new GregorianCalendar();
        long curTime = System.currentTimeMillis();
        curDate.setTime(new Date(curTime));
        curDate.set(Calendar.HOUR_OF_DAY, 23);
        curDate.set(Calendar.MINUTE, 59);
        curDate.set(Calendar.SECOND, 59);
        curDate.set(Calendar.MILLISECOND, 999);

        diffDay = (curDate.getTimeInMillis() - mil) / 86400000L;
        String time = getStrTime(mil, "HH:mm");
        boolean judge1 = diffDay < 0 || diffDay > 2;
        if (judge1) {
            /*
             * if (curDate.get(Calendar.YEAR) == cale.get(Calendar.YEAR)) { str
			 * = getStrTime(mil, "MM月dd日"); } else { str = getStrTime(mil,
			 * "yyyy年MM月dd日"); }
			 */
            str = getStrTime(mil, "yyyy-MM-dd");
            // 显示原始时间
        } else if (diffDay > 1) {
            str = "前天" + " " + time;
        } else if (diffDay > 0) {
            str = "昨天" + " " + time;
        } else {
            str = getStrTime(mil, "yyyy年MM月dd日 HH:mm");
            System.out.println("kankan:" + str);
            diffDay = (curTime - mil) / 1000L;
            System.out.println("diffDay:" + diffDay);
            boolean judge2 = diffDay > 7200;
            boolean judge3 = diffDay > 60;
            if (judge2) {
                str = "今天" + " " + time;
            } else if (judge3) {
                str = getHoursMin(diffDay) + "前";
            } else {
                str = "刚刚";
            }
        }
        return str;
    }

    public static String getCQTime(long mil) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(new Date(mil));
        String str = "";
        long diffDay = 0L;

        Calendar curDate = new GregorianCalendar();
        long curTime = System.currentTimeMillis();
        curDate.setTime(new Date(curTime));
        curDate.set(Calendar.HOUR_OF_DAY, 23);
        curDate.set(Calendar.MINUTE, 59);
        curDate.set(Calendar.SECOND, 59);
        curDate.set(Calendar.MILLISECOND, 999);

        diffDay = (curDate.getTimeInMillis() - mil) / 86400000L;

        if (diffDay < 0 || diffDay > 1) {
            str = getWeekday(mil);
        } else if (diffDay > 0) {
            str = "昨天";
        } else {
            str = "今天";
        }
        return str;
    }

    /**
     * 实现给定某日期，判断是星期几
     *
     * @param longDate
     * @return
     */
    public static String getWeekday(long longDate) {// 必须yyyy-MM-dd
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        String date = sd.format(longDate);
        Date d = null;
        try {
            d = sd.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 安卓手机显示周几的方式不同。有的显示为星期，有的显示为周。所以这里只取最后一位，然后固定为周
        String res = sdw.format(d);
        String s = "周" + res.substring(res.length() - 1);
        return s;
    }

    /**
     * 获取现在时间(String 类型)
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static String getNowDateString() {
        try {
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // new Date()为获取当前系统时间
            return df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取现在时间(String 类型)
     *
     * @param dateFormat 时间返回格式
     * @return 返回时间类型 默认yyyy-MM-dd HH:mm:ss，如传入dateFormat,则转换为dateFormat；
     */
    public static String getNowDateString(String dateFormat) {
        String format = "yyyy-MM-dd HH:mm:ss";
        if (!TextUtils.isEmpty(dateFormat)) {
            format = dateFormat;
        }
        try {
            //设置日期格式
            SimpleDateFormat df = new SimpleDateFormat(format);
            // new Date()为获取当前系统时间
            return df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 返回一个月有多少个周
     *
     * @param year  当前年份
     * @param month 当前月份
     * @return 一月有多少个周书
     */
    public static int getMonthHaveWeeks(int year, int month) {
        Calendar c = Calendar.getInstance();
        //年
        c.set(Calendar.YEAR, year);
        //月
        c.set(Calendar.MONTH, month - 1);
        //周数据
        int weekNum = c.getActualMaximum(Calendar.WEEK_OF_MONTH);
        return weekNum;
    }

    /**
     * 返回一个月有多少天
     *
     * @param year  当前年份
     * @param month 当前月份
     * @return 一月有多少个周书
     */
    public static int getMonthHaveDays(int year, int month) {
        Calendar c = Calendar.getInstance();
        //年
        c.set(Calendar.YEAR, year);
        //月
        c.set(Calendar.MONTH, month - 1);
        //天数据
        int days = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        return days;
    }

    /**
     * 判断距离今天多少天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     *
     * @throws ParseException
     */
    public static int getIndex(String day){

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            return diffDay;
        }
        return 0;
    }
    /**
     * 判断是否是今天(效率比较高)
     * @param day 传入的 时间  "2016-06-28 10:10:30" "2016-06-28" 都可以
     *
     * @throws ParseException
     */
    public static boolean isToday(String day){

        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);

        Calendar cal = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Date date = getDateFormat().parse(day);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);
            if(diffDay==0){
                return true;
            }
        }
        return false;
    }
}
