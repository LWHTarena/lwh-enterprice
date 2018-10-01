package com.lwhtarena.company.sys.util;

import com.lwhtarena.company.sys.obj.DateEl;
import com.lwhtarena.company.sys.obj.DatetimeSpanSuffixTxt;
import com.lwhtarena.company.sys.obj.TimeSpanUnit;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author：liwh
 * @Description:
 * @Date 11:25 2018/8/4
 * @Modified By:
 * <h1>
 * <ol></ol>
 * </h1>
 */
public class TimeUtil {

    public static DateEl getEl(Date date) {
        Calendar c = Calendar.getInstance();
        if (date != null) {
            c.setTime(date);
        }

        DateEl de = new DateEl();
        de.setYear(c.get(1));
        de.setMonth(c.get(2) + 1);
        de.setDay(c.get(5));
        de.setHour(c.get(10));
        de.setMinute(c.get(12));
        de.setSecond(c.get(13));
        de.setMillisecond(c.get(14));
        de.setDayOfWeek(c.get(7));
        return de;
    }

    public static Long getCurrTime() {
        Calendar todayStart = Calendar.getInstance();
        return todayStart.getTime().getTime();
    }

    public static Long firstDay(int stamp) {
        Calendar curr = Calendar.getInstance();
        switch(stamp) {
            case 1:
                curr.set(7, 1);
                break;
            case 2:
                curr.set(5, 1);
                break;
            case 3:
                int tmp = curr.get(2) / 3 + 1;
                int month = (tmp - 1) * 3 + 1;
                curr.set(2, month - 1);
                curr.set(5, 1);
                break;
            case 4:
                curr.set(6, 1);
        }

        curr.set(11, 0);
        curr.set(12, 0);
        curr.set(13, 0);
        curr.set(14, 0);
        return curr.getTime().getTime();
    }

    public static Long getTodayStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(10, 0);
        todayStart.set(12, 0);
        todayStart.set(13, 0);
        todayStart.set(14, 0);
        return todayStart.getTime().getTime();
    }

    public static Long getTodayEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(10, 23);
        todayEnd.set(12, 59);
        todayEnd.set(13, 59);
        todayEnd.set(14, 999);
        return todayEnd.getTime().getTime();
    }

    public static Timestamp coverLongToTimestamp(long timeLong) {
        Timestamp t = new Timestamp(timeLong);
        return t;
    }

    public static long coverStrToLong(String dtStr) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return formatter.parse(dtStr).getTime();
        } catch (ParseException var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    public static String coverLongToStr(Long t, String fmt) {
        if (t == 0L) {
            return "";
        } else {
            Timestamp tt = new Timestamp(t);
            SimpleDateFormat formatter = new SimpleDateFormat(fmt);
            return formatter.format(tt);
        }
    }

    public static String coverLongStrToDateStr(String t, String fmt) {
        System.out.println("t:" + t);
        long time = Long.valueOf(t);
        if (time == 0L) {
            return "";
        } else {
            Timestamp tt = new Timestamp(time);
            SimpleDateFormat formatter = new SimpleDateFormat(fmt);
            return formatter.format(tt);
        }
    }

    public static java.util.Date getYearFirstDay(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        java.util.Date currYearFirst = calendar.getTime();
        return currYearFirst;
    }

    public static java.util.Date getYearLastDay(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(1, year);
        calendar.roll(6, -1);
        java.util.Date currYearLast = calendar.getTime();
        return currYearLast;
    }

    public static Timestamp getCurTime() {
        return new Timestamp((new java.util.Date()).getTime());
    }

    public static Date getCurDate() {
        return new Date((new java.util.Date()).getTime());
    }

    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.add(5, -1);
        return new Date(c.getTime().getTime());
    }

    public static String getDate(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            return c.get(1) + "年" + (c.get(2) + 1) + "月" + c.get(5) + "日";
        }
    }

    public static String getDate(Date date) {
        if (date == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            return c.get(1) + "年" + (c.get(2) + 1) + "月" + c.get(5) + "日";
        }
    }

    public static String getTimestamp(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            return c.get(1) + "年" + (c.get(2) + 1) + "月" + c.get(5) + "日" + " " + c.get(11) + ":" + c.get(12) + ":" + c.get(13);
        }
    }

    public static String getSQLTimestamp(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            return c.get(1) + "-" + (c.get(2) + 1) + "-" + c.get(5) + "-" + " " + c.get(11) + ":" + c.get(12) + ":" + c.get(13);
        }
    }

    public static String getCurTimeString() {
        java.util.Date date = new java.util.Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return "" + c.get(1) + (c.get(2) + 1) + c.get(5) + c.get(11) + c.get(12) + c.get(13);
    }

    public static Timestamp createTimestamp(int year, int month, int day, int hour, int minute, int second) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day, hour, minute, second);
        return new Timestamp(c.getTimeInMillis());
    }

    public static Date createDate(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month - 1, day);
        return new Date(c.getTimeInMillis());
    }

    public static Timestamp testCreateTimestamp(String str, String format, int datePostion) throws ParseException {
        DateFormat df = new SimpleDateFormat(format);
        ParsePosition pos = new ParsePosition(datePostion);
        df.setLenient(false);

        try {
            df.parse(str.toString(), pos);
            Timestamp a = Timestamp.valueOf(str);
            return a;
        } catch (Exception var6) {
            return null;
        }
    }

    public static String createCurrTimestampStr(String format) {
        DateFormat f = new SimpleDateFormat(format);
        return f.format(new Timestamp((new java.util.Date()).getTime()));
    }

    public static boolean testCreateDate(String format, int datePostion, int year, int month, int day) {
        try {
            DateFormat dataFormat = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(datePostion);
            dataFormat.setLenient(false);
            dataFormat.parse(year + "-" + month + "-" + day, pos);
            return true;
        } catch (Exception var7) {
            return false;
        }
    }

    public static String getJavaScriptTimeString(Timestamp ts) {
        if (ts == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            return "new Date(" + c.get(1) + "," + c.get(2) + "," + c.get(5) + "," + c.get(11) + "," + c.get(12) + "," + c.get(13) + ")";
        }
    }

    public static String getTimestampVar(Timestamp ts, int arg) {
        if (ts == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(ts);
            switch(arg) {
                case 1:
                    return String.valueOf(c.get(2) + 1);
                case 2:
                    return String.valueOf(c.get(1));
                default:
                    return String.valueOf(c.get(5));
            }
        }
    }

    public static String getDateVar(Date date, int arg) {
        if (date == null) {
            return "";
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            switch(arg) {
                case 1:
                    return String.valueOf(c.get(2) + 1);
                case 2:
                    return String.valueOf(c.get(1));
                default:
                    return String.valueOf(c.get(5));
            }
        }
    }

    public static String getWeekDay(Date date) {
        SimpleDateFormat FormatterWeekDay = new SimpleDateFormat("E");
        String weekDay = FormatterWeekDay.format(date).trim();
        if (weekDay.equals("Sun")) {
            weekDay = "星期日";
        } else if (weekDay.equals("Mon")) {
            weekDay = "星期一";
        } else if (weekDay.equals("Tue")) {
            weekDay = "星期二";
        } else if (weekDay.equals("Wed")) {
            weekDay = "星期三";
        } else if (weekDay.equals("Thu")) {
            weekDay = "星期四";
        } else if (weekDay.equals("Fri")) {
            weekDay = "星期五";
        } else if (weekDay.equals("Sat")) {
            weekDay = "星期六";
        }

        return weekDay;
    }

    public static String timestampToStr(Timestamp t, String fmt) {
        if (fmt == null || fmt.trim().equals("")) {
            fmt = "yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        String tmp = sdf.format(t);
        return tmp;
    }

    public String timestampToStr(Timestamp t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String tmp = sdf.format(t);
        return tmp;
    }

    public static Timestamp stringToTimestamp(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        try {
            Date date = (Date)sdf.parse(dateStr);
            date.getTime();
            cal.setTime(date);
            return new Timestamp(cal.getTimeInMillis());
        } catch (ParseException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public boolean isSameWeek(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int subYear = cal1.get(1) - cal2.get(1);
        if (subYear == 0) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (subYear == 1 && cal2.get(2) == 11) {
            if (cal1.get(3) == cal2.get(3)) {
                return true;
            }
        } else if (subYear == -1 && cal1.get(2) == 11 && cal1.get(3) == cal2.get(3)) {
            return true;
        }

        return false;
    }

    public static java.util.Date firstDayAtWeek(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(7, cal.getFirstDayOfWeek());
        return cal.getTime();
    }

    public static java.util.Date lastDayAtWeek(java.util.Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(7, cal.getFirstDayOfWeek() + 6);
        return cal.getTime();
    }

    public static java.util.Date dayAtWeek(java.util.Date date, int dn) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(7, dn);
        return cal.getTime();
    }

    public static int quarter(long date) {
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        String month = formatterMonth.format(date);
        int quarter = Integer.parseInt(month) / 4 + 1;
        String year = formatterYear.format(date);
        quarter += Integer.parseInt(year) * 10;
        return quarter;
    }

    public static int quarter(java.util.Date date) {
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        String month = formatterMonth.format(date.getTime());
        int quarter = Integer.parseInt(month) / 4 + 1;
        String year = formatterYear.format(date.getTime());
        quarter += Integer.parseInt(year) * 10;
        return quarter;
    }

    public static int timeNum(String key, int offset) {
        if (key == null) {
            return 0;
        } else {
            int timeKey;
            if (!key.trim().equalsIgnoreCase("curYear") && !key.trim().equalsIgnoreCase("year")) {
                Calendar cal;
                String week;
                SimpleDateFormat formatterWeek;
                if (!key.trim().equalsIgnoreCase("curMonth") && !key.trim().equalsIgnoreCase("month")) {
                    if (!key.trim().equalsIgnoreCase("curWeek") && !key.trim().equalsIgnoreCase("week")) {
                        if (!key.trim().equalsIgnoreCase("curQuarter") && !key.trim().equalsIgnoreCase("quarter")) {
                            timeKey = 0;
                        } else {
                            timeKey = quarter(offset);
                        }
                    } else {
                        formatterWeek = new SimpleDateFormat("yyyyMMdd");
                        if (offset > 0) {
                            cal = Calendar.getInstance();
                            cal.add(5, (0 - offset) * 7);
                            java.util.Date weekStamp = firstDayAtWeek(new Date(cal.getTimeInMillis()));
                            week = formatterWeek.format(new Timestamp(weekStamp.getTime()));
                            timeKey = Integer.valueOf(week);
                        } else {
                            java.util.Date weekStamp = firstDayAtWeek(new Date(System.currentTimeMillis()));
                            week = formatterWeek.format(new Timestamp(weekStamp.getTime()));
                            timeKey = Integer.valueOf(week);
                        }
                    }
                } else {
                    formatterWeek = new SimpleDateFormat("yyyyMM");
                    if (offset > 0) {
                        cal = Calendar.getInstance();
                        cal.set(2, cal.get(2) - offset);
                        week = formatterWeek.format(cal.getTime());
                        timeKey = Integer.valueOf(week);
                    } else {
                        week = formatterWeek.format(new Timestamp(System.currentTimeMillis()));
                        timeKey = Integer.valueOf(week);
                    }
                }
            } else {
                SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
                String curYear = formatterYear.format(new Timestamp(System.currentTimeMillis()));
                timeKey = Integer.valueOf(curYear) - offset;
            }

            return timeKey;
        }
    }

    private static int quarter(int offset) {
        Calendar cal = Calendar.getInstance();
        cal.set(2, cal.get(2));
        SimpleDateFormat formatterMonth = new SimpleDateFormat("MM");
        SimpleDateFormat formatterYear = new SimpleDateFormat("yyyy");
        int year = Integer.parseInt(formatterYear.format(cal.getTime()));
        int month = Integer.parseInt(formatterMonth.format(cal.getTime()));
        int quarter = (month - 1) / 3 + 1;
        int qtmp = quarter;
        if (offset > 0) {
            year -= offset / 4;
            quarter -= offset % 4;
            if (quarter <= 0) {
                year -= Math.abs(quarter) / 4 + 1;
                if (quarter == 0) {
                    quarter = 4;
                } else {
                    quarter = qtmp + 4 - offset % 4;
                    if (quarter > 4) {
                        quarter -= 4;
                    }
                }
            }
        }

        int r = year * 10 + quarter;
        return r;
    }

    public static int weekTimeNum(int offset, int dn) {
        SimpleDateFormat formatterWeek = new SimpleDateFormat("yyyyMMdd");
        int timeKey;
        String week;
        if (offset > 0) {
            Calendar cal = Calendar.getInstance();
            cal.add(5, (0 - offset) * 7);
            java.util.Date weekStamp = dayAtWeek(new Date(cal.getTimeInMillis()), dn);
            week = formatterWeek.format(new Timestamp(weekStamp.getTime()));
            timeKey = Integer.valueOf(week);
        } else {
            java.util.Date weekStamp = firstDayAtWeek(new Date(System.currentTimeMillis()));
            week = formatterWeek.format(new Timestamp(weekStamp.getTime()));
            timeKey = Integer.valueOf(week);
        }

        return timeKey;
    }

    public static int daysSubByTimestamp(Timestamp ta, Timestamp tb) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int da = Integer.valueOf(formatter.format(ta));
        int db = Integer.valueOf(formatter.format(tb));
        return db - da;
    }

    public static int covTimeToDateInt(Timestamp t) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d = Integer.valueOf(formatter.format(t));
        return d;
    }

    public static DateEl diff(long start, long end) {
        long l = end - start;
        long day = l / 86400000L;
        long hour = l / 3600000L - day * 24L;
        long min = l / 60000L - day * 24L * 60L - hour * 60L;
        long s = l / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L - min * 60L;
        DateEl del = new DateEl();
        del.setDay(Integer.valueOf(String.valueOf(day)));
        del.setHour(Integer.valueOf(String.valueOf(hour)));
        del.setMinute(Integer.valueOf(String.valueOf(min)));
        del.setSecond(Integer.valueOf(String.valueOf(s)));
        return del;
    }

    public static Timestamp cal(Timestamp t, int pos, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(t);
        switch(pos) {
            case 1:
                cal.add(1, value);
                break;
            case 2:
                cal.add(2, value);
                break;
            case 3:
                cal.add(5, value);
                break;
            case 4:
                cal.add(10, value);
                break;
            case 5:
                cal.add(12, value);
                break;
            case 6:
                cal.add(13, value);
        }

        t.setTime(cal.getTimeInMillis());
        return t;
    }

    public static long cal(long t, int pos, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(t);
        switch(pos) {
            case 1:
                cal.add(1, value);
                break;
            case 2:
                cal.add(2, value);
                break;
            case 3:
                cal.add(5, value);
                break;
            case 4:
                cal.add(10, value);
                break;
            case 5:
                cal.add(12, value);
                break;
            case 6:
                cal.add(13, value);
        }

        return cal.getTimeInMillis();
    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess = true;

        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(false);
            format.parse(str);
        } catch (Exception var3) {
            convertSuccess = false;
        }

        return convertSuccess;
    }

    public static DatetimeSpanSuffixTxt dsst(String configStr) {
        DatetimeSpanSuffixTxt dsst = new DatetimeSpanSuffixTxt();
        if (configStr != null && !configStr.trim().equals("")) {
            String[] sArray = configStr.split("\\|");

            for(int i = 0; i < sArray.length; ++i) {
                switch(i) {
                    case 0:
                        dsst.setYear(sArray[i]);
                        break;
                    case 1:
                        dsst.setMonth(sArray[i]);
                        break;
                    case 2:
                        dsst.setWeek(sArray[i]);
                        break;
                    case 3:
                        dsst.setDay(sArray[i]);
                        break;
                    case 4:
                        dsst.setHour(sArray[i]);
                        break;
                    case 5:
                        dsst.setMinute(sArray[i]);
                        break;
                    case 6:
                        dsst.setSecond(sArray[i]);
                }
            }
        }

        if (dsst.getYear() == null || dsst.getYear().trim().equals("")) {
            dsst.setYear("years ago");
        }

        if (dsst.getMonth() == null || dsst.getMonth().trim().equals("")) {
            dsst.setMonth("months ago");
        }

        if (dsst.getWeek() == null || dsst.getWeek().trim().equals("")) {
            dsst.setWeek("weeks ago");
        }

        if (dsst.getDay() == null || dsst.getDay().trim().equals("")) {
            dsst.setDay("days ago");
        }

        if (dsst.getHour() == null || dsst.getHour().trim().equals("")) {
            dsst.setHour("hours ago");
        }

        if (dsst.getMinute() == null || dsst.getMinute().trim().equals("")) {
            dsst.setMinute("minutes ago");
        }

        if (dsst.getSecond() == null || dsst.getSecond().trim().equals("")) {
            dsst.setSecond("seconds ago");
        }

        return dsst;
    }

    public static String statTimesLosted(long timeNum, DatetimeSpanSuffixTxt dsst) {
        long nowTimeNum = System.currentTimeMillis();
        long timeSpan = nowTimeNum - timeNum;
        long ltmp = Long.parseLong("31536000000");
        int yearsSpan = (int)(timeSpan / ltmp);
        ltmp = Long.parseLong("2592000000");
        int monthsSpan = (int)(timeSpan / ltmp);
        ltmp = Long.parseLong("604800000");
        int weeksSpan = (int)(timeSpan / ltmp);
        ltmp = Long.parseLong("86400000");
        int daysSpan = (int)(timeSpan / ltmp);
        ltmp = Long.parseLong("3600000");
        int hoursSpan = (int)(timeSpan / ltmp);
        ltmp = Long.parseLong("60000");
        int minutesSpan = (int)(timeSpan / ltmp);
        ltmp = Long.parseLong("1000");
        int secondsSpan = (int)(timeSpan / ltmp);
        if (yearsSpan > 0) {
            return yearsSpan + dsst.getYear();
        } else if (monthsSpan > 0) {
            return monthsSpan + dsst.getMonth();
        } else if (weeksSpan > 0) {
            return monthsSpan + dsst.getWeek();
        } else if (daysSpan > 0) {
            return daysSpan + dsst.getDay();
        } else if (hoursSpan > 0) {
            return hoursSpan + dsst.getHour();
        } else {
            return minutesSpan > 0 ? minutesSpan + dsst.getMinute() : secondsSpan + dsst.getSecond();
        }
    }

    public static TimeSpanUnit unixLenAtFmt(String str) {
        TimeSpanUnit tsu = new TimeSpanUnit();
        if (str != null && !str.trim().equals("")) {
            str = str.trim();
            String fmtChar = str.substring(str.length() - 1, str.length());
            long span;
            int spanLen;
            if (StringUtil.isNumber(fmtChar)) {
                spanLen = Integer.valueOf(str);
                span = (long)spanLen * Long.valueOf(1000L);
                tsu.setSpanSize(spanLen);
            } else {
                char strChar = fmtChar.charAt(0);
                spanLen = Integer.valueOf(str.substring(0, str.length() - 1));
                tsu.setSpanSize(spanLen);
                switch(strChar) {
                    case 'H':
                        span = (long)spanLen * Long.valueOf(1000L) * Long.valueOf(3600L);
                        tsu.setSpanTitle("h");
                        break;
                    case 'M':
                        span = (long)spanLen * Long.valueOf(1000L) * Long.valueOf(2592000L);
                        tsu.setSpanTitle("M");
                        break;
                    case 'd':
                        span = (long)spanLen * Long.valueOf(1000L) * Long.valueOf(86400L);
                        tsu.setSpanTitle("d");
                        break;
                    case 'm':
                        span = (long)spanLen * Long.valueOf(1000L) * Long.valueOf(60L);
                        tsu.setSpanTitle("m");
                        break;
                    case 'w':
                        span = (long)spanLen * Long.valueOf(1000L) * Long.valueOf(604800L);
                        tsu.setSpanTitle("w");
                        break;
                    case 'y':
                        span = (long)spanLen * Long.valueOf(1000L) * Long.valueOf(31536000L);
                        tsu.setSpanTitle("y");
                        break;
                    default:
                        span = (long)spanLen * Long.valueOf(1000L);
                        tsu.setSpanTitle("s");
                }
            }

            tsu.setSeconds(span);
        } else {
            tsu.setSeconds(0L);
            tsu.setSpanTitle("s");
            tsu.setSpanSize(0);
        }

        return tsu;
    }

    public static long unixLenAtCurr(int mod) {
        long r;
        switch(mod) {
            case 1:
                r = Long.valueOf(1000L);
                break;
            case 2:
                r = Long.valueOf(1000L) * Long.valueOf(60L);
                break;
            case 3:
                r = Long.valueOf(1000L) * Long.valueOf(3600L);
                break;
            case 4:
                r = Long.valueOf(1000L) * Long.valueOf(86400L);
                break;
            case 5:
                r = Long.valueOf(1000L) * Long.valueOf(604800L);
                break;
            case 6:
                r = Long.valueOf(1000L) * Long.valueOf(2592000L);
                break;
            case 7:
                r = Long.valueOf(1000L) * Long.valueOf(31536000L);
                break;
            default:
                r = 0L;
        }

        long curr = System.currentTimeMillis();
        return curr - r;
    }

    public static int getAge(java.util.Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(date)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        } else {
            int yearNow = cal.get(1);
            int monthNow = cal.get(2);
            int dayOfMonthNow = cal.get(5);
            cal.setTime(date);
            int yearBirth = cal.get(1);
            int monthBirth = cal.get(2);
            int dayOfMonthBirth = cal.get(5);
            int age = yearNow - yearBirth;
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        --age;
                    }
                } else {
                    --age;
                }
            }

            return age;
        }
    }

    public static String birthdayFromIDNumber(String idnumber) {
        String regex = "^(\\d{6})(\\d{8})(.*)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(idnumber);
        return m.find() ? m.group(0) : null;
    }

    public static int ageFromIDNumber(String idnumber) throws ParseException, Exception {
        String regex = "\\d{8}(?=[\\d\\D]{4}$)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(idnumber);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return m.find() ? getAge(sdf.parse(m.group(0))) : -1;
    }

    public static void main(String[] args) throws ParseException, Exception {
        Calendar cal = Calendar.getInstance();
        int hourNow = cal.get(11);
        System.out.println("Hour:" + hourNow);
    }
}
