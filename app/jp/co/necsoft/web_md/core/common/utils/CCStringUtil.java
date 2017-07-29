/*
 * ====================================================================
 * 
 * 機能名称 : 文字列編集クラス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 2014.04.03 HaiNP 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.common.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;

/**
 * 文字列編集クラス
 *
 */
public class CCStringUtil {
    /**
     * SEPARATE CHAR
     */
    private static final String SEPARATE_CHAR = "、";
    /**
    * 指定した文字列の半角をチェックする。
    * @param str 対象文字列
    * @return true：半角、false；全角
    **/
    public static boolean checkString1Byte(String str) {
        byte[] b = null;
        boolean is1Byte = true;
        try {
            b = str.getBytes("UTF-8");
            if (b.length != str.length()) {
                is1Byte = false;
            }
        } catch (UnsupportedEncodingException e) {
            is1Byte = false;
        }

        return is1Byte;
    }

    /**
    * 指定した文字列のバイト長を取得します。
    * @param instr 対象文字列
    * @return バイト長
    **/
    public static int getByteLen(String instr) {
        try {
            byte[] value = instr.getBytes("MS932");
            return value.length;
        } catch (UnsupportedEncodingException e) {
            byte[] value = instr.getBytes();
            return value.length;
        }
    }

    /**
    * 指定した文字列を右パディングします。
    * @param instr 対象文字列
    * @param keta 桁数
    * @param c パディング文字
    * @return 右パディング後の文字列
    **/
    public static String puddRight(String instr, int keta, char c) {
        if (keta == instr.length() || keta < instr.length()) {
            return instr;
        }

        String pud = puddComPrt(instr, keta, c);
        return instr + pud;

    }

    /**
    * 指定した文字列を左パディングします。
    * @param instr 対象文字列
    * @param keta 桁数
    * @param c パディング文字
    * @return 左パディング後の文字列
    **/
    public static String puddLeft(String instr, int keta, char c) {
        if (keta == instr.length() || keta < instr.length()) {
            return instr;
        }

        String pud = puddComPrt(instr, keta, c);
        return pud + instr;
    }

    /**
    * 指定した文字列を左パディングします。
    * @param instr 対象文字列
    * @param keta 桁数
    * @param c パディング文字
    * @return 左パディング後の文字列
    **/
    private static String puddComPrt(String instr, int keta, char c) {
        String pud = "";

        if (keta > instr.length()) {
            int marg = keta - instr.length();
            for (int i = 0; i < marg; i++) {
                pud += c;
            }
        }
        return pud;

    }

    /**
    * 指定した文字列を整形(右スペースパディング)した文字列を返します。
    * @param instr 対象文字列
    * @param keta 桁数
    * @return 整形後の文字列
    **/
    public static String fixString(String instr, int keta) {
        if (instr == null) {
            instr = "";
        }
        int bytelen = getByteLen(instr);
        if (bytelen == instr.length()) { // １バイト文字のみ
            return puddRight(instr, keta, ' ');
        } else if (bytelen == instr.length() * 2) { // ２バイト文字のみ
            int spcnt = (keta - instr.length() * 2) / 2;
            String strsp = "";
            for (int i = 0; i < spcnt; i++) {
                strsp += "　";
            }
            return instr + strsp;
        } else {
            throw new ChaseException("指定した文字列が１バイト文字２バイト文字混在の場合");
        }
    }

    /**
    * 全角の省略可能文字パディング処理を行います。<BR>
    * @param  sInpMoji :編集対象の文字列
    * @param  iKeta :パディングする桁数
    * @return パディング後の文字列
    **/
    public static String puddZenkaku(String sInpMoji, int iKeta) {
        String sOutMoji = "";
        if (isEmpty(sInpMoji)) {
            sOutMoji = fixString("　", iKeta);
        } else {
            sOutMoji = fixString(sInpMoji, iKeta);
        }

        return sOutMoji;
    }

    /**
    * 全角の省略可能文字パディング処理を行います。<BR>
    * @param  sInpMoji :編集対象の文字列
    * @return パディング後の文字列
    **/
    public static String fixStringEmpty(String sInpMoji) {
        String sOutMoji = "";
        if (isEmpty(sInpMoji)) {
            sOutMoji = " ";
        } else {
            sOutMoji = sInpMoji.trim();
        }

        return sOutMoji;
    }

    /**
    * 全角の省略可能文字パディング処理を行います。<BR>
    * @param  sInpMoji :編集対象の文字列
    * @return パディング後の文字列
    **/
    public static String puddZenkakuEmpty(String sInpMoji) {
        String sOutMoji = "";
        if (isEmpty(sInpMoji)) {
            sOutMoji = "　";
        } else {
            sOutMoji = sInpMoji.trim();
        }

        return sOutMoji;
    }

    /**
    * 指定した文字列の右端のスペースを除去します。
    * @param instr 対象文字列
    * @return 右端トリム後の文字列
    **/
    public static String trimRight(String instr) {
        String strret = "";
        boolean sflag = false;
        for (int i = instr.length() - 1; i >= 0; i--) {
            String strone = instr.substring(i, i + 1);
            if (!strone.equals(" ") && !strone.equals("　")) {
                sflag = true;
            }
            if (sflag) {
                strret = strone + strret;
            }
        }
        return strret;
    }

    /**
    * 指定した文字列の左端のスペースを除去します。
    * @param instr 対象文字列
    * @return 左端トリム後の文字列
    **/
    public static String trimLeft(String instr) {
        String strret = "";
        boolean sflag = false;
        for (int i = 0; i < instr.length(); i++) {
            String strone = instr.substring(i, i + 1);
            if (!strone.equals(" ") && !strone.equals("@")) {
                sflag = true;
            }
            if (sflag) {
                strret += strone;
            }
        }
        return strret;
    }

    /**
    * 指定した文字列の両端のスペースを除去します。
    * @param instr 対象文字列
    * @return 両端トリム後の文字列
    **/
    public static String trimBoth(String instr) {
        String strret = instr;
        strret = trimRight(strret);
        strret = trimLeft(strret);
        return strret;
    }

    /**
    * 指定した文字列をゼロサプレスします。
    * @param instr 対象文字列
    * @param keta 桁数
    * @return ゼロサプレス後の文字列
    **/
    public static String suppZero(String instr, int keta) {
        return puddLeft(instr, keta, '0');
    }

    /*****************************************************************
    * StringをIntegerに変換処理.
    * <p>
    * 機能概要：<br>
    * StringをIntegerに変換します。<br>
    * @param sStrVal String
    * @return Integer
    *****************************************************************/
    public static int cnvStrToInt(String sStrVal) {
        int iRet = 0;
        if (isEmpty(sStrVal)) {
            iRet = 0;
        } else {
            iRet = Integer.parseInt(sStrVal.trim());
        }
        return iRet;
    }

    /*****************************************************************
    * StringをLongに変換処理.
    * <p>
    * 機能概要：<br>
    * StringをLongに変換します。<br>
    * <p> 
    * @param sStrVal String
    * @return Integer
    *****************************************************************/
    public static long cnvStrToLong(String sStrVal) {
        long iRet = 0;
        if (isEmpty(sStrVal)) {
            iRet = 0;
        } else {
            iRet = Long.parseLong(sStrVal.trim());
        }
        return iRet;
    }

    /*****************************************************************
     * StringをBigDecimalに変換処理.
     * <p>
     * 機能概要：<br>
     * StringをBigDecimalに変換します。<br>
     * <p> 
     * @param sStrVal String
     * @return BigDecimal
     *****************************************************************/
    public static BigDecimal cnvStrToBigDecimal(String sStrVal) {
        BigDecimal iRet;
        if (isEmpty(sStrVal)) {
            iRet = new BigDecimal(0);
        } else {
            iRet = new BigDecimal(sStrVal.trim());
        }
        return iRet;
    }

    /**
    * StringをDoubleに変換処理.
    * <p>
    * 機能概要：<br>
    * StringをDoubleに変換します。<br>
    * <p> 
    * @param sStrVal String
    * @return Double
    */
    public static double cnvStrToDbl(String sStrVal) {
        double dblRet = 0;
        sStrVal = sStrVal.trim();
        if (isEmpty(sStrVal)) {
            dblRet = 0;
        } else {
            dblRet = Double.parseDouble(sStrVal);
        }
        return dblRet;
    }

    /**
     * チェック文字列がnullである
     * @param str 対象文字列
     * @return 虚実
     **/
    public static boolean isEmpty(String str) {
        if (str == null || str.trim().equals("")) {
            return true;
        }
        return false;
    }

    /**
    * 指定した文字列が１バイト文字と２バイト文字混在の場合はfalseを返します。
    * @param instr 対象文字列
    * @return true：１バイト文字のみまたは２バイト文字のみ、false：１バイト文字と２バイト文字混在
    **/
    public static boolean propByte(String instr) {
        int bytelen = getByteLen(instr);

        if (bytelen == instr.length() || bytelen == instr.length() * 2) {
            return true;
        }
        return false;
    }

    /**
     * サブストリング
     * @param str 文字列
     * @param start インデックス開始
     * @param end インデックスが終了
     * @return 文字列
     */
    public static String subString(String str, int start, int end) {
        if (isEmpty(str) || str.length() < (end - 1)) {
            return "";
        }

        return str.substring(start, end);
    }

    /**
    * 指定した文字列を指定の分割文字で分割して配列に格納します。
    * @param instr 対象文字列
    * @param intoken 分割文字
    * @return 分割した文字列配列
    **/
    public static String[] splitStr(String instr, String intoken) {
        StringTokenizer st = new StringTokenizer(instr, intoken);
        String[] strarray = new String[st.countTokens()];
        int i = 0;
        while (st.hasMoreTokens()) {
            strarray[i] = st.nextToken();
            i++;
        }
        return strarray;
    }

    /**
    * 指定した文字列の右側文字列を指定した桁数分返します。
    * @param instr 対象文字列
    * @param len 桁数
    * @return 右側文字列
    **/
    public static String getRightStr(String instr, int len) {
        return subString(instr, instr.length() - len, instr.length());
    }

    /**
     * 指定した文字列の左側文字列を指定した桁数分返します。
     * @param instr 対象文字列
     * @param len 桁数
     * @return 左側文字列
     **/
    public static String getLeftStr(String instr, int len) {
        return instr.substring(0, len);
    }

    /**
    * 指定した取引先コードをハイフン付き(10桁)またはハイフン無し(9桁)で返却します。<BR>
    * @param toricd 数値文字列
    * @param ishyp ハイフン付き(true)またはハイフン無し(false)
    * @return 変換した取引先コード
    **/
    public static String frmtToriCode(String toricd, boolean ishyp) {
        String toricdwk = toricd;
        toricdwk = trimBoth(toricdwk);
        if (toricdwk.equals("")) {
            return toricd;
        }
        if (toricd.equals("")) {
            return "";
        }
        if (toricd.length() != 9 && toricd.length() != 10) {
            return null;
        }
        if (toricd.length() == 10) {
            if (!toricd.substring(6, 7).equals("-")) {
                return null;
            }
            toricd = toricd.substring(0, 6) + toricd.substring(7, 10);
        }
        if (!CCNumberUtil.isNumeric(toricd)) {
            return null;
        }
        if (ishyp) {
            return toricd.substring(0, 6) + "-" + toricd.substring(6, 9);
        } else {
            return toricd.substring(0, 6) + toricd.substring(6, 9);
        }
    }

    /**
     * method to check byte
     * @param control control
     * @param value value
     * @param offset offset
     * @return  boolean
     */
    public static boolean byteCheck(String control, String value, int offset, ErrorResponse errRes) {
        int byteLength = CCStringUtil.getByteLen(value);
        int stringLength = value.length();
        if (byteLength != stringLength * offset) {
            if (offset == 2) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_ZENKAKU_FAILED);
                return false;
            } else if (offset == 1) {
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_HANKAKU_FAILED);
                return false;
            }
        }
        return true;
    }

    /**
     * method to check max length
     * @param control control
     * @param value value
     * @param inputLength inputLength
     * @return  boolean
     */
    public static boolean checkMaxLength(String control, String value, int inputLength, ErrorResponse errRes) {
        int length = value.length();
        if (length > inputLength) {
            ArrayList<String> arr = new ArrayList<String>();
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            arr.add(String.valueOf(inputLength));
            errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_CC_MAX_LENGHT, arr);
            return false;
        }
        return true;
    }
    
    /**
     * method to check max length
     * @param control control
     * @param value value
     * @param inputLength inputLength
     * @return  boolean
     */
    public static boolean checkLength(String control, String value, int inputLength, ErrorResponse errRes) {
        int length = value.length();
        if (length != inputLength) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(control, CCMessageConst.MSG_KEY_VALUE_OVER_LIMIT);
            return false;
        }
        return true;
    }
    
    /**
     * method to check max length for multiple select control
     * @param control control
     * @param value value
     * @param inputLength inputLength
     * @return  boolean
     */
    public static boolean checkLengthMultiple(String control, List<String> lsValue, int inputLength, ErrorResponse errRes) {
        if (lsValue != null && lsValue.size() > 0) {
            String errorString = StringUtils.EMPTY;
            Boolean isError = false;
            for (String value : lsValue) {
                int length = value.length();
                if (length != inputLength) {
                    isError = true;
                    if (errorString == StringUtils.EMPTY){
                        errorString =  value;
                    }else{
                        errorString = errorString + SEPARATE_CHAR + value;
                    }
                }
            }
            if (isError){
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                ArrayList<String> param = new ArrayList<String>();
                param.add(errorString);
                errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_MULTICODE_CHARNUMBER_LIMIT_ERROR,param);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * method to check max length
     * @param control control
     * @param value value
     * @param inputLength inputLength
     * @return  boolean
     */
    public static boolean checkByteLength(String control, String value, int inputLength, ErrorResponse errRes) {
        int length = getByteLen(value);
        if (length > inputLength) {
            ArrayList<String> arr = new ArrayList<String>();
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            arr.add(String.valueOf(inputLength));
            errRes.addErrorInfoWithParam(control, CCMessageConst.MSG_KEY_BYTE_RANGE_INVALID, arr);
            return false;
        }
        return true;
    }
}
