/*
 * ====================================================================
 * 
 * 機能名称 : 日付時間編集クラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.05.23 ToanPQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.common.utils;

import java.util.Calendar;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;

import org.joda.time.DateTime;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * 日付時間編集クラス
 *
 */
public class CCDateUtil {
    /**
     * 日付のフォーマットエラー
     */
    public static final int DATE_FORMAT_ERROR = -9999;
    /**
    * 年フィールド値。
    **/
    public static final int YEAR = Calendar.YEAR;
    /**
    * 月フィールド値。
    **/
    public static final int MONTH = Calendar.MONTH;
    /**
    * 日フィールド値。
    **/
    public static final int DATE = Calendar.DATE;
    /**
    * 時(24時間表記)フィールド値。
    **/
    public static final int HOUR_OF_DAY = Calendar.HOUR_OF_DAY;
    /**
    * 分フィールド値。
    **/
    public static final int MINUTE = Calendar.MINUTE;
    /**
    * 秒フィールド値。
    **/
    public static final int SECOND = Calendar.SECOND;
    /**
    * ミリ秒フィールド値。
    **/
    public static final int MILLISECOND = Calendar.MILLISECOND;

    /** yyyyMMdd */
    public static final String DATETIME_FORMAT_DATE_YYYYMMDD = "yyyyMMdd";

    /** yyyy/MM/dd */
    public static final String DATETIME_FORMAT_DATE = "yyyy/MM/dd";

    /**DATETIME_FORMAT_HOUR*/
    public static final String DATETIME_FORMAT_TIME = "HHmmss";
    
    /**DATETIME_FORMAT_HOUR*/
    public static final String DATETIME_FORMAT_HHMM = "HHmm";
    
    /** yyyyMMdd */
    public static final String DATETIME_FORMAT_DATE_YYYYMMDDHHMISS = "yyyyMMddHHmmss";

    /** CCSystemContext */
    @Inject
    private CCSystemContext context;

    /**
     * 日付情報
     *
     */
    private static class CmDateInfo {
        /** 年 */
        private int year;
        /** 月 */
        private int month;
        /** 日 */
        private int day;
    }

    /**
    * 現在の年を取得します。<BR>
    * @return 現在の年(yyyy)
    **/
    public static String getSysYear() {
        DateTime dt = new DateTime();
        int year = dt.getYear();
        return year + "";
    }

    /**
    * 現在の月を取得します。<BR>
    * @return 現在の月(mm)
    **/
    public static String getSysMonth() {
        DateTime dt = new DateTime();
        int month = dt.getMonthOfYear();
        String aftermonth = String.valueOf(month);
        if (month < 10) {
            aftermonth = "0" + aftermonth;
        }
        return aftermonth;
    }

    /**
    * 現在の日を取得します。<BR>
    * @return 現在の日(dd)
    **/
    public static String getSysDate() {
        DateTime dt = new DateTime();
        int date = dt.getDayOfMonth();
        String afterdate = String.valueOf(date);
        if (date < 10) {
            afterdate = "0" + afterdate;
        }
        return afterdate;
    }

    /**
    * 現在の曜日番号を取得します。<BR>
    * @return 現在の曜日番号<BR>
    * ７：日<BR>
    * １：月<BR>
    * ２：火<BR>
    * ３：水<BR>
    * ４：木<BR>
    * ５：金<BR>
    * ６：土<BR>
    **/
    public static int getSysDowInt() {
        return getSysDowInt(Integer.parseInt(getSysYear()), Integer.parseInt(getSysMonth()),
                Integer.parseInt(getSysDate()));
    }

    /**
    * 指定した年月日(yyyyMMddまたはyyyy/MM/dd)の曜日番号を取得します。<BR>
    * @param strdate 年月日
    * @return 指定した年月日の曜日番号<BR>
    * ７：日<BR>
    * １：月<BR>
    * ２：火<BR>
    * ３：水<BR>
    * ４：木<BR>
    * ５：金<BR>
    * ６：土<BR>
    **/
    public static int getSysDowInt(String strdate) {
        CmDateInfo dateinfo = new CmDateInfo();
        if (!getDateInfo(strdate, dateinfo)) {
            return -1;
        }
        return getSysDowInt(dateinfo.year, dateinfo.month, dateinfo.day);
    }

    /**
    * 指定した年月日の曜日番号を取得します。<BR>
    * @param iyear 年
    * @param imonth 月 
    * @param iday 日
    * @return 指定した年月日の曜日番号<BR>
    * ７：日<BR>
    * １：月<BR>
    * ２：火<BR>
    * ３：水<BR>
    * ４：木<BR>
    * ５：金<BR>
    * ６：土<BR>
    **/
    public static int getSysDowInt(int iyear, int imonth, int iday) {
        DateTime dt = new DateTime();
        dt = dt.withDate(iyear, imonth, iday);
        return dt.getDayOfWeek();
    }

    /**
    * 現在の曜日を取得します。<BR>
    * @return 現在の曜日("月", "火", "水", "木", "金", "土", "日")
    **/
    public static String getSysDow() {
        return getSysDow(Integer.parseInt(getSysYear()), Integer.parseInt(getSysMonth()),
                Integer.parseInt(getSysDate()));
    }

    /**
    * 指定した年月日(yyyyMMddまたはyyyy/MM/dd)の曜日を取得します。<BR>
    * @param strdate 年月日
    * @return 指定した年月日の曜日("月", "火", "水", "木", "金", "土", "日")
    **/
    public static String getSysDow(String strdate) {
        CmDateInfo dateinfo = new CmDateInfo();
        if (!getDateInfo(strdate, dateinfo)) {
            return "";
        }
        return getSysDow(dateinfo.year, dateinfo.month, dateinfo.day);
    }

    /**
    * 指定した年月日の曜日を取得します。<BR>
    * @param iyear 年
    * @param imonth 月 
    * @param iday 日
    * @return 指定した年月日の曜日("日", "月", "火", "水", "木", "金", "土")
    **/
    public static String getSysDow(int iyear, int imonth, int iday) {
        String[] youbi = {"", "月", "火", "水", "木", "金", "土", "日" };
        DateTime dt = new DateTime();
        dt = dt.withDate(iyear, imonth, iday);
        int dow = dt.getDayOfWeek();
        return youbi[dow];
    }

    /**
    * 現在の時を取得します。<BR>
    * @return 現在の時(hh)
    **/
    public static String getSysHour() {
        DateTime dt = new DateTime();
        int hour = dt.getHourOfDay();
        String afterhour = String.valueOf(hour);
        if (hour < 10) {
            afterhour = "0" + afterhour;
        }
        return afterhour;
    }

    /**
    * 現在の分を取得します。<BR>
    * @return 現在の分(mm)
    **/
    public static String getSysMinute() {
        DateTime dt = new DateTime();
        int minute = dt.getMinuteOfHour();
        String afterminute = String.valueOf(minute);
        if (minute < 10) {
            afterminute = "0" + afterminute;
        }
        return afterminute;
    }

    /**
    * 現在の秒を取得します。<BR>
    * @return 現在の秒(ss)
    **/
    public static String getSysSecond() {
        DateTime dt = new DateTime();
        int second = dt.getSecondOfMinute();
        String aftersecond = String.valueOf(second);
        if (second < 10) {
            aftersecond = "0" + aftersecond;
        }
        return aftersecond;
    }

    /**
    * 現在のミリ秒を取得します。<BR>
    * @return 現在のミリ秒(ms)
    **/
    public static String getSysMilliSecond() {
        DateTime dt = new DateTime();
        int mmsecond = dt.getMillisOfSecond();
        String aftermmsecond = String.valueOf(mmsecond);
        if (mmsecond < 10) {
            aftermmsecond = "0" + "0" + aftermmsecond;
            return aftermmsecond;
        } else {
            if (mmsecond < 100) {
                aftermmsecond = "0" + aftermmsecond;
                return aftermmsecond;
            }
        }
        return aftermmsecond;
    }

    /**
    * 現在の日付を引数のフォーマットに変換した文字列を返します。<BR>
    * @param frmt 日付フォーマット
    * @return 変換した文字列
    **/
    public static String getSysDateFormat(String frmt) {
        DateTime dt = new DateTime();
        return dt.toString(DateTimeFormat.forPattern(frmt));
    }

    /**
    * 与えられた日付文字列を指定のフォーマットに変換した文字列を返します。<BR>
    * @param strdate 日付文字列(yyyyMMddまたはyyyy/MM/ddで指定してください)
    * @param frmt 日付フォーマット
    * @return 変換した文字列
    **/
    public static String getDateFormat(String strdate, String frmt) {
        CmDateInfo dateinfo = new CmDateInfo();
        if (!getDateInfo(strdate, dateinfo)) {
            return strdate;
        }
        DateTime dt = new DateTime();
        dt = dt.withDate(dateinfo.year, dateinfo.month, dateinfo.day);
        return dt.toString(DateTimeFormat.forPattern(frmt));
    }

    /**
    * 与えられた時間文字列を指定のフォーマットに変換した文字列を返します。<BR>
    * @param strtime 時間文字列(hhmmssまたはhh:mm:ss、hhmmまたはhh:mmで指定してください)
    * @param frmt 時間フォーマット("HH:mm:ss"等)
    * @return 変換した文字列
    **/
    public static String getTimeFormat(String strtime, String frmt) {
        int iyear, imonth, iday;
        int ihour, imin, isec;
        if (!CCNumberUtil.isNumeric(strtime)) {
            return strtime; // エラーの場合はそのまま返却
        }
        iyear = Integer.parseInt(getSysYear());
        imonth = Integer.parseInt(getSysMonth());
        iday = Integer.parseInt(getSysDate());

        if (strtime.length() == 8) { // hh:mm:ss
            ihour = Integer.parseInt(strtime.substring(0, 2));
            imin = Integer.parseInt(strtime.substring(3, 5));
            isec = Integer.parseInt(strtime.substring(6, 8));
        } else if (strtime.length() == 6) { // hhmmss
            ihour = Integer.parseInt(strtime.substring(0, 2));
            imin = Integer.parseInt(strtime.substring(2, 4));
            isec = Integer.parseInt(strtime.substring(4, 6));
        } else if (strtime.length() == 5) { // hh:mm
            ihour = Integer.parseInt(strtime.substring(0, 2));
            imin = Integer.parseInt(strtime.substring(3, 5));
            isec = 0;
        } else if (strtime.length() == 4) { // hhmm
            ihour = Integer.parseInt(strtime.substring(0, 2));
            imin = Integer.parseInt(strtime.substring(2, 4));
            isec = 0;
        } else {
            return strtime; // エラーの場合はそのまま返却
        }
        DateTime dt = new DateTime(iyear, imonth, iday, ihour, imin, isec);
        return dt.toString(DateTimeFormat.forPattern(frmt));
    }

    /**
    * フィールドからDurationFieldTypeを取得します。<BR>
    * @param field フィールド
    * @return DurationFieldType
    **/
    private static DurationFieldType getDurationFieldType(int field) {
        DurationFieldType dtfieldType;
        switch (field) {
        case YEAR:
            dtfieldType = DurationFieldType.years();
            break;
        case MONTH:
            dtfieldType = DurationFieldType.months();
            break;
        case DATE:
            dtfieldType = DurationFieldType.days();
            break;
        case HOUR_OF_DAY:
            dtfieldType = DurationFieldType.hours();
            break;
        case MINUTE:
            dtfieldType = DurationFieldType.minutes();
            break;
        case SECOND:
            dtfieldType = DurationFieldType.seconds();
            break;
        case MILLISECOND:
            dtfieldType = DurationFieldType.millis();
            break;
        default:
            return null;
        }
        return dtfieldType;
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果の年を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果の年(yyyy)
    **/
    public static String getSysYear(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int year = dt.getYear();
        return year + "";
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果の月を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果の月(mm)
    **/
    public static String getSysMonth(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int month = dt.getMonthOfYear();
        String aftermonth = String.valueOf(month);
        if (month < 10) {
            aftermonth = "0" + aftermonth;
        }
        return aftermonth;
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果の日を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果の日(dd)
    **/
    public static String getSysDate(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int date = dt.getDayOfMonth();
        String afterdate = String.valueOf(date);
        if (date < 10) {
            afterdate = "0" + afterdate;
        }
        return afterdate;
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果の時を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果の時(hh)
    **/
    public static String getSysHour(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int hour = dt.getHourOfDay();
        String afterhour = String.valueOf(hour);
        if (hour < 10) {
            afterhour = "0" + afterhour;
        }
        return afterhour;
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果の分を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果の分(mm)
    **/
    public static String getSysMinute(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int minute = dt.getMinuteOfHour();
        String afterminute = String.valueOf(minute);
        if (minute < 10) {
            afterminute = "0" + afterminute;
        }
        return afterminute;
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果の秒を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果の秒(ss)
    **/
    public static String getSysSecond(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int second = dt.getSecondOfMinute();
        String aftersecond = String.valueOf(second);
        if (second < 10) {
            aftersecond = "0" + aftersecond;
        }
        return aftersecond;
    }

    /**
    * 指定したフィールドと日付または時刻の量を演算した結果のミリ秒を取得します。<BR>
    * @param field フィールド
    * @param amount 額
    * @return 演算した結果のミリ秒(ms)
    **/
    public static String getSysMilliSecond(int field, int amount) {
        DateTime dt = new DateTime();
        dt = dt.withFieldAdded(getDurationFieldType(field), amount);
        int mmsecond = dt.getMillisOfSecond();
        String aftermmsecond = String.valueOf(mmsecond);
        if (mmsecond < 10) {
            aftermmsecond = "0" + "0" + aftermmsecond;
            return aftermmsecond;
        } else {
            if (mmsecond < 100) {
                aftermmsecond = "0" + aftermmsecond;
                return aftermmsecond;
            }
        }
        return aftermmsecond;
    }

    /**
    * 今月の月末日付を取得します。
    * @return 月末日付
    **/
    public static int getLastDay() {
        DateTime dt = new DateTime();
        return dt.dayOfMonth().withMaximumValue().getDayOfMonth();
    }

    /**
    * 指定した月の月末日付を取得します。
    * @param iyear 年
    * @param imonth 月
    * @return 月末日付
    **/
    public static int getLastDay(int iyear, int imonth) {
        if (iyear < 0) {
            return -1;
        }
        if (imonth < 1 || imonth > 12) {
            return -1;
        }
        DateTime dt = new DateTime();
        dt = dt.withYear(iyear).withMonthOfYear(imonth);
        return dt.dayOfMonth().withMaximumValue().getDayOfMonth();
    }

    /**
    * 2つの日付の日数の差を返します。<BR>
    * dateto－datefromを計算します。
    * @param dateto Calendar型日付
    * @param datefrom Calendar型日付
    * @return 日数の差
    **/
    public static long getDateBetween(DateTime dateto, DateTime datefrom) {
        // ２つの日付のﾐﾘ秒の差を，86400000ﾐﾘ秒(=1日)で割り，日数を計算する
        return (long) (dateto.getMillis() - datefrom.getMillis()) / 86400000;
    }

    /**
    * 今日と指定の日付の日数の差を返します。<BR>
    * indate－TODAYを計算します。
    * @param indate Calendar型日付
    * @return 日数の差
    **/
    public static long getDateBetween(DateTime indate) {
        DateTime clcur = new DateTime();
        // ２つの日付のﾐﾘ秒の差を，86400000ﾐﾘ秒(=1日)で割り，日数を計算する
        return (long) (indate.getMillis() - clcur.getMillis()) / 86400000;
    }

    /**
    * 2つの日付の日数の差を返します。<BR>
    * (iyearto年imonthto月idayto日)－(iyearfrom年imonthfrom月idayfrom日)を計算します。
    * @param iyearto 年
    * @param imonthto 月
    * @param idayto 日
    * @param iyearfrom 年
    * @param imonthfrom 月
    * @param idayfrom 日
    * @return 日数の差
    **/
    public static long getDateBetween(int iyearto, int imonthto, int idayto, int iyearfrom, int imonthfrom,
            int idayfrom) {
        DateTime clto = new DateTime();
        DateTime clfrom = new DateTime();

        clto = clto.withDate(iyearto, imonthto, idayto);
        clfrom = clfrom.withDate(iyearfrom, imonthfrom, idayfrom);
        return getDateBetween(clto, clfrom);
    }

    /**
    * 今日と指定の日付の日数の差を返します。<BR>
    * (iyear年imonth月iday日)－TODAYを計算します。
    * @param iyear 年
    * @param imonth 月
    * @param iday 日
    * @return 日数の差
    **/
    public static long getDateBetween(int iyear, int imonth, int iday) {
        DateTime clto = new DateTime();
        clto = clto.withDate(iyear, imonth, iday);
        return getDateBetween(clto, new DateTime());
    }

    /**
    * 2つの日付の日数の差を返します。<BR>
    * (strdateto(yyyymmdd,yyyy/mm/dd))－(strdatefrom(yyyymmdd,yyyy/mm/dd))を計算します。
    * @param strdateto 年月日(yyyyMMddまたはyyyy/MM/ddで指定してください)
    * @param strdatefrom 年月日(yyyyMMddまたはyyyy/MM/ddで指定してください)
    * @return 日数の差
    **/
    public static long getDateBetween(String strdateto, String strdatefrom) {
        CmDateInfo dateinfoTo = new CmDateInfo();
        CmDateInfo dateinfoFrom = new CmDateInfo();
        if (!getDateInfo(strdateto, dateinfoTo)) {
            return DATE_FORMAT_ERROR; // エラーの場合はDATE_FORMAT_ERROR返却
        }
        if (!getDateInfo(strdatefrom, dateinfoFrom)) {
            return DATE_FORMAT_ERROR; // エラーの場合はDATE_FORMAT_ERROR返却
        }
        return getDateBetween(dateinfoTo.year, dateinfoTo.month, dateinfoTo.day, dateinfoFrom.year,
                dateinfoFrom.month, dateinfoFrom.day);
    }

    /**
    * 今日と指定の日付の日数の差を返します。<BR>
    * (strdate(yyyymmdd,yyyy/mm/dd))－TODAYを計算します。
    * @param strdate 年月日(yyyyMMddまたはyyyy/MM/ddで指定してください)
    * @return 日数の差
    **/
    public static long getDateBetween(String strdate) {
        CmDateInfo dateinfo = new CmDateInfo();
        if (!getDateInfo(strdate, dateinfo)) {
            return DATE_FORMAT_ERROR; // エラーの場合はDATE_FORMAT_ERROR返却
        }
        return getDateBetween(dateinfo.year, dateinfo.month, dateinfo.day);
    }

    /**
    * 日付の妥当性をチェックします。
    * @param year 年
    * @param month 月
    * @param date 日
    * @return true：正常、false；異常
    **/
    public static boolean isDate(int year, int month, int date) {
        if (month <= 0 || month > 12) {
            return false;
        }

        // 日の判断
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            if (date < 1 || date >= 31) {
                // 日付エラー
                return false;
            }
        }

        // 日の判断
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            if (date < 1 || date >= 32) {
                // 日付エラー
                return false;
            }
        }

        // うるう年チェック
        if (month == 2) {
            int calc1 = year;
            int calc2 = year;
            int calc3 = year;
            calc1 %= 4;
            calc2 %= 100;
            calc3 %= 400;

            // うるう年でない
            if (calc1 != 0) {
                if (date < 1 || date >= 29) {
                    return false;
                }
            }

            // うるう年でない
            if (calc1 == 0 && calc2 == 0 && calc3 != 0) {
                if (date < 1 || date >= 29) {
                    return false;
                }
            }

            // うるう年
            if (calc1 == 0 && calc2 != 0) {
                if (date < 1 || date >= 30) {
                    return false;
                }
            }

            // うるう年
            if (calc1 == 0 && calc2 == 0 && calc3 == 0) {
                if (date < 1 || date >= 30) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
    * 日付の妥当性をチェックします。<BR>
    * "yyyymmdd"のフォーマット以外の文字列は文字判断のみでfalseを返します。
    * @param indate チェック対象の年月日文字列
    * @return true：正常、false；異常
    **/
    public static boolean isDate(String indate) {
        if (indate.length() != 8) {
            return false;
        }

        String stryear = indate.substring(0, 4);
        String strmonth = indate.substring(4, 6);
        String strday = indate.substring(6, 8);
        if (!CCNumberUtil.isNumeric(indate)) {
            return false;
        }

        int intyear = Integer.parseInt(stryear);
        int intmonth = Integer.parseInt(strmonth);
        int intday = Integer.parseInt(strday);
        return isDate(intyear, intmonth, intday);
    }

    /**
     * 日付情報を取得
     * @param strdate 日付
     * @param datainfo データ情報
     * @return true/false
     */
    private static boolean getDateInfo(String strdate, CmDateInfo datainfo) {
        if (!CCNumberUtil.isNumeric(strdate)) {
            return false;
        }
        if (strdate.length() == 8) {
            datainfo.year = Integer.parseInt(strdate.substring(0, 4));
            datainfo.month = Integer.parseInt(strdate.substring(4, 6));
            datainfo.day = Integer.parseInt(strdate.substring(6, 8));
        } else if (strdate.length() == 10) {
            datainfo.year = Integer.parseInt(strdate.substring(0, 4));
            datainfo.month = Integer.parseInt(strdate.substring(5, 7));
            datainfo.day = Integer.parseInt(strdate.substring(8, 10));
        } else {
            return false;
        }
        return true;
    }

    /**
     * 指定された日付と日数で指定日＋日数の日付を返します。
     * @param kijunDay 基準日
     * @param nissu 日数
     * @return 日付（基準日に日数を加算した結果）
     **/
    public static String getParamDate(String kijunDay, int nissu) {
        int y = Integer.parseInt(kijunDay.substring(0, 4));
        int m = Integer.parseInt(kijunDay.substring(4, 6));
        int d = Integer.parseInt(kijunDay.substring(6, 8));

        DateTime dt = new DateTime();
        dt.withDate(y, m, d);
        dt = dt.withFieldAdded(DurationFieldType.days(), nissu);
        return dt.toString(DateTimeFormat.forPattern(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
    }

    /**
     * Function check unyoDate
     * 
     * @param date date
     * @return return check date greater unyoDate
     */
    public boolean compareUnyoDate(String date) {
        // 発効日が運用日以降かチェックする
        if (date.compareTo(context.getUnyoDate()) < 0) {
            return true;
        }

        return false;
    }

    /*******************************************************************************
    * 日付計算処理.
    * <p>
    * 概要：<br>
    * 日付の計算を行う。<br>
    * <p>
    * 更新日：<br>
    *
    * <p>
    * @param sDate 計算対象日付(YYYYMMDD)
    * @param sField 計算フィールド("DAY_OF_MONTH":月末日, "DAY":日, "MONTH":月)　　 
    * @param iAmount 加算量
    * @return String 計算後日付(YYYYMMDD)
    *******************************************************************************/
    public static String doCalcDate(String sDate, String sField, int iAmount) {
        int iYear = 0;
        int iMonth = 0;
        int iDay = 0;
        int iHour = 0;
        int iMinute = 0;
        int iSecond = 0;
        String sReturnDate = "";

        if (!CCNumberUtil.isNumeric(sDate)) {
            return null;
        }

        iYear = Integer.parseInt(sDate.substring(0, 4));
        iMonth = Integer.parseInt(sDate.substring(4, 6));
        iDay = Integer.parseInt(sDate.substring(6, 8));
        DateTime dt = new DateTime(iYear, iMonth, iDay, iHour, iMinute, iSecond);

        if (sField.equals("DAY_OF_MONTH")) {
            int intMaxDay = dt.dayOfMonth().withMaximumValue().getDayOfMonth();
            dt = dt.withDayOfMonth(intMaxDay);
        } else if (sField.equals("DAY")) {
            dt = dt.withFieldAdded(DurationFieldType.days(), iAmount);
        } else if (sField.equals("MONTH")) {
            dt = dt.withFieldAdded(DurationFieldType.months(), iAmount);
        }

        iYear = dt.getYear();
        iMonth = dt.getMonthOfYear();
        iDay = dt.getDayOfMonth();

        sReturnDate = CCStringUtil.puddLeft(Integer.toString(iYear), 4, '0');
        sReturnDate += CCStringUtil.puddLeft(Integer.toString(iMonth), 2, '0');
        sReturnDate += CCStringUtil.puddLeft(Integer.toString(iDay), 2, '0');

        return sReturnDate;
    }

    /**
     * 日付計算処理.
     * <p>
     * 概要：<br>
     *  日付の計算を行う。<br>
     * 
     * @param sDate 計算対象日付(YYYYMMDD)
     * @param iAmount 加算量(日)
     * @return String 計算後年月(YYYYMM)
     */
    public static String doCalcDate(String sDate, int iAmount) {
        int iYear = 0;
        int iMonth = 0;
        int iDay = 0;
        int iHour = 0;
        int iMinute = 0;
        int iSecond = 0;
        String sYYYYMM = "";
        iYear = Integer.parseInt(sDate.substring(0, 4));
        iMonth = Integer.parseInt(sDate.substring(4, 6));
        iDay = Integer.parseInt(sDate.substring(6, 8));
        DateTime dt = new DateTime(iYear, iMonth, iDay, iHour, iMinute, iSecond);

        // 日加算
        dt = dt.withFieldAdded(DurationFieldType.days(), iAmount);

        iYear = dt.getYear();
        iMonth = dt.getMonthOfYear();
        iDay = dt.getDayOfMonth();
        if (iMonth == 0) {
            iMonth = 12;
            iYear = iYear - 1;
        }

        // YYYYMMに編集
        sYYYYMM = CCStringUtil.puddLeft(Integer.toString(iYear), 4, '0');
        sYYYYMM += CCStringUtil.puddLeft(Integer.toString(iMonth), 2, '0');

        return sYYYYMM;
    }

    /**
     * Get date in String with end day of month in pattern: yyyyMMdd.
     * @param yyyy year
     * @param mm month
     * @return String: date with end day of month in pattern: yyyyMMdd
     */
    public static String getDateEndOfMonth(String yyyy, String mm) {
        String dateBegin = yyyy + mm + "01";
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyyMMdd");
        String endOfMonthValue = String.valueOf(dtf.parseDateTime(dateBegin).dayOfMonth().getMaximumValue());

        String dateEnd = yyyy + mm + endOfMonthValue;
        return dateEnd;
    }

    /**
     * Get DateTime object of the input date string
     * @param yyyymmdd date in format yyyymmdd
     * @return DateTime
     */
    public static DateTime getDateTime(String yyyymmdd) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATETIME_FORMAT_DATE_YYYYMMDD);
        return formatter.parseDateTime(yyyymmdd);
    }
    
    /**
     * Get DateTime object of the input date string
     * @param yyyymmdd date in format yyyymmdd
     * @return DateTime
     */
    public static int getPeriodBetween2Month(String date1, String date2) {
        return Months.monthsBetween(toYearMonth(date1),toYearMonth(date2)).getMonths();
    }
    
    /**
     * Get Year Month object
     * @param date DAte
     * @return YearMonth
     */
    public static YearMonth toYearMonth(String date) {
        LocalDate localDate = new LocalDate(date);
        return new YearMonth(localDate.getYear(), localDate.getMonthOfYear());
    }
    
    
    /**
     * Check Format Date
     * @param control ID on screen
     * @param value input value
     * @param errRes Error Response Object
     * @return true is date or false not date
     */
    public static boolean checkFormatDate(String control, String value, ErrorResponse errRes) {
        if (!isDate(value)) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_DATE_ERROR);
            return false;
        }
        
        return true;
    }
    
    /**
     * Check from date is not greater than to number
     * @param control Item on screen
     * @param fromDate From Date
     * @param toDate To Date
     * @param errRes ErrorResponse
     * @return true is less than
     */
    public static boolean checkDateFromTo(String control, String fromDate,
            String toDate, ErrorResponse errRes) {
        if (fromDate.compareTo(toDate) > 0) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_GREATER_TO);
            return false;
        }

        return true;
    }
}
