// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 日付時間編集クラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.06 HaiNP 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.StringTokenizer;

import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;

/**
 * 数値編集クラス
 *
 */
public class CCNumberUtil {
    /** double型システム精度(小数点以下第５位) */
    private static final int SYS_KETA = 5;
    /** Double Sysseido */
    private static final double DBL_SYSSEIDO = Math.pow(10.0, SYS_KETA);
    /** Specified Pow Number */
    private static final double DBL_POW_NUMBER = 10.0;
    /** Add positive */
    private static final double POSITIVE_VALUE_ADD = 0.5;
    /** Add Negative*/
    private static final double NEGATIVE_VALUE_ADD = -0.5;

    // 丸めタイプ
    /**
    * 切上げです。 絶対値の大きい方に丸めます。 
    **/
    public static final int ROUND_UP = BigDecimal.ROUND_UP;
    /**
    * 切捨てです。 ゼロに近い方に丸めます。 
    **/
    public static final int ROUND_DOWN = BigDecimal.ROUND_DOWN;
    /**
    * 大きい方に丸めます。 
    **/
    public static final int ROUND_CEILING = BigDecimal.ROUND_CEILING;
    /**
    * 小さい方に丸めます。 
    **/
    public static final int ROUND_FLOOR = BigDecimal.ROUND_FLOOR;
    /**
    * 四捨五入です。 最も近い整数が二つあれば，絶対値の大きい方に丸めます。
    **/
    public static final int ROUND_HALF_UP = BigDecimal.ROUND_HALF_UP;
    /**
    * 四捨五入の変形です。 最も近い整数が二つあれば，ゼロに近い方に丸めます。
    **/
    public static final int ROUND_HALF_DOWN = BigDecimal.ROUND_HALF_DOWN;
    /**
    * 標準的な丸め方です。 最も近い整数が二つあれば，末位が偶数になる方に丸めます。
    **/
    public static final int ROUND_HALF_EVEN = BigDecimal.ROUND_HALF_EVEN;
    /**
    * これは丸められては困るときに指定します。<BR>
    * 与えられた精度で正確に表せない場合は ArithmeticException が投げられます。
    **/
    public static final int ROUND_UNNECESSARY = BigDecimal.ROUND_UNNECESSARY;

    /**
    * doubleの値をシステム精度で四捨五入した値を返します。
    * @param dblnum 対象double値
    * @return システム精度で四捨五入した値(double型)
    **/
    public static double getSysDouble(double dblnum) {
        return Math.round(dblnum * DBL_SYSSEIDO) / DBL_SYSSEIDO;
    }

    /**
    * double型の加算を行います。結果のdouble値を返却します。
    * @param dblnum1 対象double値
    * @param dblnum2 対象double値
    * @return 加算結果(dblnum1+dblnum2)のdouble値
    **/
    public static double addDouble(double dblnum1, double dblnum2) {
        BigDecimal bignum1 = new BigDecimal(dblnum1);
        BigDecimal bignum2 = new BigDecimal(dblnum2);
        BigDecimal bignumr = bignum1.add(bignum2);
        return getSysDouble(bignumr.doubleValue());
    }

    /**
    * double型の減算を行います。結果のdouble値を返却します。
    * @param dblnum1 対象double値
    * @param dblnum2 対象double値
    * @return 減算結果(dblnum1-dblnum2)のdouble値
    **/
    public static double subtractDouble(double dblnum1, double dblnum2) {
        BigDecimal bignum1 = new BigDecimal(dblnum1);
        BigDecimal bignum2 = new BigDecimal(dblnum2);
        BigDecimal bignumr = bignum1.subtract(bignum2);
        return getSysDouble(bignumr.doubleValue());
    }

    /**
    * double型の乗算を行います。結果のdouble値を返却します。
    * @param dblnum1 対象double値
    * @param dblnum2 対象double値
    * @return 乗算結果(dblnum1*dblnum2)のdouble値
    **/
    public static double multiplyDouble(double dblnum1, double dblnum2) {
        BigDecimal bignum1 = new BigDecimal(dblnum1);
        BigDecimal bignum2 = new BigDecimal(dblnum2);
        BigDecimal bignumr = bignum1.multiply(bignum2);
        return getSysDouble(bignumr.doubleValue());
    }

    /**
    * double型の除算を行います。結果のdouble値を返却します。
    * @param dblnum1 対象double値
    * @param dblnum2 対象double値
    * @param roundtype 丸めタイプ
    * @return 除算結果(dblnum1/dblnum2)のdouble値
    **/
    public static double divideDouble(double dblnum1, double dblnum2, int roundtype) {
        BigDecimal bignum1 = new BigDecimal(dblnum1);
        BigDecimal bignum2 = new BigDecimal(dblnum2);
        BigDecimal bignumr = bignum1.divide(bignum2, SYS_KETA, roundtype);
        return getSysDouble(bignumr.doubleValue());
    }
    
    /**
    * BigDecimal型の加算を行います。結果のBigDecimal値を返却します。
    * @param bignum1 対象BigDecimal値
    * @param bignum2 対象BigDecimal値
    * @return 加算結果(bignum1+bignum2)のBigDecimal値
    **/
    public static BigDecimal addBigDecimal(BigDecimal bignum1, BigDecimal bignum2) {
        if (bignum1 == null) {
            bignum1 = new BigDecimal(0);
        }
        
        if (bignum2 == null) {
            bignum2 = new BigDecimal(0);
        }
        BigDecimal bignumr = bignum1.add(bignum2);
        return BigDecimal.valueOf(getSysDouble(bignumr.doubleValue()));
    }

    /**
    * BigDecimal型の減算を行います。結果のBigDecimal値を返却します。
    * @param bignum1 対象BigDecimal値
    * @param bignum2 対象BigDecimal値
    * @return 減算結果(bignum1-bignum2)のBigDecimal値
    **/
    public static BigDecimal subtractBigDecimal(BigDecimal bignum1, BigDecimal bignum2) {
        if (bignum1 == null) {
            bignum1 = new BigDecimal(0);
        }
        
        if (bignum2 == null) {
            bignum2 = new BigDecimal(0);
        }
        BigDecimal bignumr = bignum1.subtract(bignum2);
        return BigDecimal.valueOf(getSysDouble(bignumr.doubleValue()));
    }

    /**
    * double型の乗算を行います。結果のdouble値を返却します。
    * @param bignum1 対象double値
    * @param bignum2 対象double値
    * @return 乗算結果(dblnum1*dblnum2)のdouble値
    **/
    public static double multiplyBigDecimal(BigDecimal bignum1, BigDecimal bignum2) {
        BigDecimal bignumr = bignum1.multiply(bignum2);
        return getSysDouble(bignumr.doubleValue());
    }

    /**
     * double型の除算を行います。結果のdouble値を返却します。
     * @param bignum1 対象double値
     * @param bignum2 対象double値
     * @param roundtype 丸めタイプ
     * @return 除算結果(bignum1/bignum2)のdouble値
     **/
    public static double divideBigDecimal(BigDecimal bignum1, BigDecimal bignum2, int roundtype) {
        BigDecimal bignumr = bignum1.divide(bignum2, SYS_KETA, roundtype);
        return getSysDouble(bignumr.doubleValue());
    }
    
    /**
     * Check string value is numeric.
     * 
     * @param instr string input
     * @return true if parse valid
     */
    public static boolean isNumeric(String instr) {
        try {
            Double.valueOf(instr);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    /**
    * 指定した数値文字列を指定したフォーマット文字列で変換した文字列を返します。<BR>
    * 小数点以下有効桁数フォーマット時に四捨五入を行います。
    * 使用例：<BR>
    * frmtNumber( "-12345.678", "###,###,###.00" ) →　"-12,345.68"<BR>
    * frmtNumber( "-12,345.678", "#.00" ) →　"-12345.68"<BR>
    * frmtNumber( ".", "#0.00" ) →　"0.00"<BR>
    * frmtNumber( ".123", "#0.00" ) →　"0.12"<BR>
    * @param strnum 数値文字列
    * @param strfrmt フォーマット文字列(例:"#.#"、"###,###,###,###.000")
    * @return 変換した文字列
    * @exception Exception
    **/
    public static String frmtNumber(String strnum, String strfrmt) {
        return frmtNumber(strnum, strfrmt, true);
    }

    /**
    * 指定した数値文字列を指定したフォーマット文字列で変換した文字列を返します。<BR>
    * 使用例：<BR>
    * frmtNumber( "-12345.678", "###,###,###.00" ) →　is4s5n=true:"-12,345.68"　is4s5n=false:"-12,345.67"<BR>
    * frmtNumber( "-12,345.678", "#.00" ) →　is4s5n=true:"-12345.68"　is4s5n=false:"-12345.67"<BR>
    * frmtNumber( ".", "#0.00" ) →　"0.00"<BR>
    * frmtNumber( ".123", "#0.00" ) →　"0.12"<BR>
    * @param strnum 数値文字列
    * @param strfrmt フォーマット文字列(例:"#.#"、"###,###,###,###.000")
    * @param is4s5n 小数点以下有効桁数フォーマット時に四捨五入を行うかどうかを指定します。true:四捨五入する　false:四捨五入しない
    * @return 変換した文字列
    * @exception Exception
    **/
    public static String frmtNumber(String strnum, String strfrmt, boolean is4s5n) {
        if ("".equals(strnum)) {
            return "";
        }

        if (".".equals(strnum)) {
            strnum = "0.0";
        }
        StringTokenizer stoken = new StringTokenizer(strnum, ",");
        strnum = "";
        int tokcnt = stoken.countTokens();
        for (int i = 0; i < tokcnt; i++) {
            strnum += stoken.nextToken();
        }
        double dblnum = Double.valueOf(strnum).doubleValue();
        if (!is4s5n) { // ・ｽﾃ佛ｽﾃ娯堙・ｽﾅｾ
            int ipos = strfrmt.indexOf(".");
            int iketa = 0;
            if (ipos >= 0) {
                iketa = strfrmt.length() - ipos - 1;
                double dblwk = CCNumberUtil.getSysDouble(dblnum * Math.pow(DBL_POW_NUMBER, iketa));
                dblnum = (double) ((long) dblwk) / Math.pow(DBL_POW_NUMBER, iketa);
            } else {
                dblnum = (double) ((long) dblnum);
            }
        } else { // ﾅｽlﾅｽﾃ固津懌愿ｼﾅｽﾅｾ
            int ipos = strfrmt.indexOf(".");
            int iketa = 0;
            double dadd = (dblnum >= 0) ? POSITIVE_VALUE_ADD : NEGATIVE_VALUE_ADD;
            if (ipos >= 0) {
                iketa = strfrmt.length() - ipos - 1;
                double dblwk = CCNumberUtil.getSysDouble(dblnum * Math.pow(DBL_POW_NUMBER, iketa));
                dblnum = (double) ((long) (dblwk + dadd)) / Math.pow(DBL_POW_NUMBER, iketa);
            } else {
                dblnum = (double) ((long) (dblnum + dadd));
            }
        }
        DecimalFormat dfmt = new DecimalFormat(strfrmt);
        return dfmt.format(dblnum);
    }
    
    /**
     * Check valid number
     * @param control ID on screen
     * @param value input value
     * @param errRes Error Response Object
     * @return true is date or false not date
     */
    public static boolean checkNumber(String control, String value, ErrorResponse errRes) {
        if (!isNumeric(value)) {
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_INPUT_NUMBER_CHARACTER);
            return false;
        }
        
        return true;
    }
    
    /**
     * Check number in range (For BigDecimal object)
     * @param control ID on screen
     * @param value input value
     * @param fromNumber From Number
     * @param toNumber To Number
     * @param errRes  Error Response
     * @return true in range
     */
    public static boolean checkNumberInRange(String control, BigDecimal value, BigDecimal fromNumber,
            BigDecimal toNumber, ErrorResponse errRes) {
        if (value.compareTo(fromNumber) < 0 || value.compareTo(toNumber) > 0) {
            ArrayList<String> paramValue = new ArrayList<String>();
            paramValue.add(fromNumber.toPlainString());
            paramValue.add(toNumber.toPlainString());
            errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_NUMBER_RANGE_INVALID, paramValue);
            return false;
        }

        return true;
    }
    
    /**
     * Check number in range (For long object)
     * @param control ID on screen
     * @param value input value
     * @param fromNumber From Number
     * @param toNumber To Number
     * @param errRes  Error Response
     * @return true in range
     */
    public static boolean checkNumberInRange(String control, Long value, Long fromNumber,
            Long toNumber, ErrorResponse errRes) {
        if (value.longValue() < fromNumber.longValue() || value.longValue() > toNumber.longValue()) {
            ArrayList<String> paramValue = new ArrayList<String>();
            paramValue.add(fromNumber.toString());
            paramValue.add(toNumber.toString());
            errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_NUMBER_RANGE_INVALID, paramValue);
            return false;
        }

        return true;
    }
    
    /**
     * Check number in range (For Integer object)
     * @param control ID on screen
     * @param value input value
     * @param fromNumber From Number
     * @param toNumber To Number
     * @param errRes  Error Response
     * @return true in range
     */
    public static boolean checkNumberInRange(String control, Integer value, Integer fromNumber,
            Integer toNumber, ErrorResponse errRes) {
        if (value.intValue() < fromNumber.intValue() || value.intValue() > toNumber.intValue()) {
            ArrayList<String> paramValue = new ArrayList<String>();
            paramValue.add(fromNumber.toString());
            paramValue.add(toNumber.toString());
            errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_NUMBER_RANGE_INVALID, paramValue);
            return false;
        }

        return true;
    }
    
    /**
     * Check from number is not greater than to number
     * @param control Item ID on screen
     * @param fromNumber From Number
     * @param toNumber To Number
     * @param errRes Error Response
     * @return true or false
     */
    public static boolean checkNumberFromTo(String control, Integer fromNumber,
            Integer toNumber, ErrorResponse errRes) {
        if (fromNumber.intValue() > toNumber.intValue()) {
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_GREATER_TO);
            return false;
        }

        return true;
    }
    
    /**
     * Check from number is not greater than to number
     * @param control Item ID on screen
     * @param fromNumber From Number
     * @param toNumber To Number
     * @param errRes Error Response
     * @return true or false
     */
    public static boolean checkNumberFromTo(String control, Long fromNumber,
            Long toNumber, ErrorResponse errRes) {
        if (fromNumber.longValue() > toNumber.longValue()) {
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_GREATER_TO);
            return false;
        }

        return true;
    }
    
    /**
     * Check from number is not greater than to number
     * @param control Item ID on screen
     * @param fromNumber From Number
     * @param toNumber To Number
     * @param errRes Error Response
     * @return true or false
     */
    public static boolean checkNumberFromTo(String control, BigDecimal fromNumber,
            BigDecimal toNumber, ErrorResponse errRes) {
        if (fromNumber.compareTo(toNumber) > 0) {
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_GREATER_TO);
            return false;
        }

        return true;
    }
}
