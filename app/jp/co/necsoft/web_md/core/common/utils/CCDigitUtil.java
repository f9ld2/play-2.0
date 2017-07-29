// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 桁のユーティリティクラス
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

/**
 * 桁のユーティリティクラス
 *
 */
public class CCDigitUtil {

    /**
     * CCDigitUtilオブジェクトを構築します。<BR>
     **/
    public CCDigitUtil() {

    }

    /**
     * チェックデジット付き伝票番号返却関数 処理内容としては、 入力値（7桁の数字）の末尾にチェックデジットを付加して返します。
     * 入力値が不正な値の場合は変換を行わずにそのまま値を返します。
     * ※基本的にcmJM022SIBN（伝票番号採番JavaBeansクラス）での使用を想定したメソッドです。
     * 
     * @param instr
     *            入力された文字列（7桁の現在番号）
     * @return チェックデジット付き伝票番号
     **/
    public String getCdDenNo(String instr) {
        if (instr.length() <= 8) {
            // 入力値の各桁が全て数字か判定
            if (!CCNumberUtil.isNumeric(instr)) {
                // 引数が整数に変換できない値の場合
                return instr;
            }
            String inDenpyoNo = "";

            inDenpyoNo = CCStringUtil.suppZero(instr, 7);
            inDenpyoNo = inDenpyoNo.substring(0, 7);

            // 上7桁を取得
            String outDenpyoNo = inDenpyoNo + getDigitDen(inDenpyoNo);
            return outDenpyoNo;
        }
        // 8桁以上の場合
        return instr;
    }

    /**
     * チェックデジット付き伝票番号返却関数 処理内容としては、 入力値（7桁の数字）の末尾にチェックデジットを付加して返します。
     * 入力値が不正な値の場合は変換を行わずにそのまま値を返します。 ※手書伝票用に末尾を編集したチェックデジットを返します。
     * 
     * @param instr
     *            入力された文字列（7桁の現在番号）
     * @return チェックデジット付き伝票番号
     **/
    public String getCdDenNoTgk(String instr) {
        if (instr.length() <= 8) {
            // 入力値の各桁が全て数字か判定
            if (!CCNumberUtil.isNumeric(instr)) {
                // 引数が整数に変換できない値の場合
                return instr;
            }
            String inDenpyoNo = "";

            inDenpyoNo = CCStringUtil.suppZero(instr, 7);

            // 上7桁を取得
            inDenpyoNo = inDenpyoNo.substring(0, 7);
            String outDenpyoNo = inDenpyoNo + getDigitDenTgk(inDenpyoNo);

            return outDenpyoNo;
        }
        // 8桁以上の場合
        return instr;
    }

    /**
     * 入力コードでチェックデジットを算出(モジュラス１０)し、 入力コードの最終桁を算出したチェックデジットに 置き換えたコードを返します。
     * 
     * 例： 入力値が8桁の場合 入力値：12345678 返却値：1234567+"チェックデジット"
     * 入力値の8桁目をチェックデジットに入れ替えた値を返します。
     * 
     * @param instr
     *            入力コード（桁数が2桁以上、13桁以下で、各桁が半角数字（0～9）とします）
     * @return 変換後のコード（入力コードが不正な値の場合は入力コードをそのまま返します）
     **/
    public String getChangeCd(String instr) {
        if (instr.length() >= 2 && instr.length() <= 13) {
            // 入力値の各桁が全て数字か判定
            for (int i = 0; i < instr.length(); i++) {
                String chkstr = instr.substring(i, i + 1);
                if (!CCNumberUtil.isNumeric(chkstr)) {
                    return instr;
                }
            }
            String inDenpyoNo = "";

            inDenpyoNo = CCStringUtil.suppZero(instr.substring(0, instr.length() - 1), 12) + "0";
            String outDenpyoNo = instr.substring(0, instr.length() - 1) + this.getDigit13(inDenpyoNo);
            return outDenpyoNo;
        }
        // 13桁以上または1桁以下の場合
        return instr;
    }

    /**
     * 8桁の伝票番号を8桁のチェックデジット付き伝票番号に変換して返却する関数(モジュラス１０) 例： 入力値：12345678
     * 返却値：1234567+"チェックデジット" 入力値の8桁目をチェックデジットに入れ替えた値を返します。
     * 
     * @param instr
     *            入力された文字列（伝票番号）
     * @return 8桁のチェックデジット付き伝票番号
     **/
    public String getCdDenNo8(String instr) {
        String inDenpyoNo = "";

        inDenpyoNo = "00000" + instr.substring(0, 7) + "0";

        String outDenpyoNo = instr.substring(0, 7) + this.getDigit13(inDenpyoNo);
        return outDenpyoNo;
    }

    /**
     * 13桁商品コードチェックデジット返却関数(モジュラス１０)
     * 
     * @param inNumber
     *            13桁の商品コード
     * @return CheckDigit
     **/
    public String getDigit13(String inNumber) {
        int intsum = 0;
        String strmoji = "";
        for (int i = 0; i < 13; i++) {
            strmoji = inNumber.substring(i, i + 1);
            int intculc = 0;
            if ((i % 2) == 0) {
                intculc = Integer.parseInt(strmoji) * 1;
            } else {
                intculc = Integer.parseInt(strmoji) * 3;
            }
            intsum += intculc;
        }

        int iStart = String.valueOf(intsum).length() - 1;
        int iEnd = String.valueOf(intsum).length();
        String strCD = (String.valueOf(intsum)).substring(iStart, iEnd);
        if (!strCD.equals("0")) {
            strCD = String.valueOf(10 - Integer.parseInt(strCD));
        }
        return (strCD);

    }

    /**
     * 伝票番号用チェックデジットを取得します。 (引数が不正な値の場合は空文字列を返します。)
     * 
     * @param inNumber
     *            伝票番号の元となる値（7桁） （伝票番号の上7桁になるもの）
     * @return CheckDigit（伝票番号の8桁目になるもの）
     **/
    public String getDigitDen(String inNumber) {
        int intsum = 0;
        String strmoji = "";
        // チェックデジット
        String sCD = "";
        if (!CCNumberUtil.isNumeric(inNumber)) {
            // 引数が整数に変換できない値の場合
            return "";
        }

        if (inNumber.length() != 7) {
            // 引数が7桁以外
            return "";
        }
        // ７桁の伝票番号の各桁の値にそれぞれの桁の重みを乗じます。
        for (int i = 0; i < 7; i++) {
            strmoji = inNumber.substring(i, i + 1);
            int intculc = 0;
            intculc = Integer.parseInt(strmoji) * (8 - i);
            intsum += intculc;
        }

        // 除算した結果の小数点以下を切り捨て
        int iAmari = intsum / 11;

        int iChk = intsum - iAmari * 11;

        // チェックデジット
        int iCD = 0;

        if (iChk == 0 || iChk == 1) {
            iCD = iChk;
        } else {
            iCD = 11 - iChk;
        }

        sCD = String.valueOf(iCD);

        return sCD;
    }

    /**
     * 伝票番号用チェックデジットを取得します。 (引数が不正な値の場合は空文字列を返します。) ※手書伝票用に末尾を編集したチェックデジットを返します。
     * 
     * @param inNumber
     *            伝票番号の元となる値（7桁） （伝票番号の上7桁になるもの）
     * @return CheckDigit（伝票番号の8桁目になるもの）
     **/
    public String getDigitDenTgk(String inNumber) {
        int intsum = 0;
        String strmoji = "";
        // チェックデジット
        String sCD = "";
        if (!CCNumberUtil.isNumeric(inNumber)) {
            // 引数が整数に変換できない値の場合
            return "";
        }
        if (inNumber.length() != 7) {
            // 引数が7桁以外
            return "";
        }
        // ７桁の伝票番号の各桁の値にそれぞれの桁の重みを乗じます。
        for (int i = 0; i < 7; i++) {
            strmoji = inNumber.substring(i, i + 1);
            int intculc = 0;
            intculc = Integer.parseInt(strmoji) * (8 - i);
            intsum += intculc;
        }

        // 除算した結果の小数点以下を切り捨て
        int iAmari = intsum / 11;

        int iChk = intsum - iAmari * 11;

        // チェックデジット
        int iCD = 0;

        if (iChk == 0 || iChk == 1) {
            if (iChk == 1) {
                iCD = 0;
            } else {
                iCD = 1;
            }

        } else {
            iCD = 11 - iChk;
        }

        sCD = String.valueOf(iCD);

        return sCD;
    }

    /**
     * ギフト商品コードチェックデジット返却関数
     * 
     * @param inNumber
     *            13桁の商品コード
     * @return strCheckDigit
     **/
    public String getDigit13Gift(String inNumber) {
        int intSum = 0;
        int intAmari = 0;
        String strCheckDigit = "";

        // ８桁目(ウエイト６)
        intSum += Integer.parseInt(inNumber.substring(7, 8)) * 6;

        // ９桁目(ウエイト５)
        intSum += Integer.parseInt(inNumber.substring(8, 9)) * 5;

        // １０桁目(ウエイト４)
        intSum += Integer.parseInt(inNumber.substring(9, 10)) * 4;

        // １１桁目(ウエイト３)
        intSum += Integer.parseInt(inNumber.substring(10, 11)) * 3;

        // １２桁目(ウエイト２)
        intSum += Integer.parseInt(inNumber.substring(11, 12)) * 2;

        // 合算した値を１１で割り、余りを求める。
        intAmari = intSum % 11;

        // 余りが０以外の場合
        if (intAmari != 0) {

            // 余りが１の場合、チェックデジットは「０」
            if (intAmari == 1) {
                strCheckDigit = "0";
            } else {
                // １１から余りを引いた値がチェックデジット
                strCheckDigit = String.valueOf(11 - intAmari);
            }
        } else {
            // 余りが０の場合
            // 余りが０の場合、チェックデジットは「０」
            strCheckDigit = "0";
        }
        return (strCheckDigit);
    }
}
