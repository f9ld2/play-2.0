///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 支払金額調整 改版履歴
 * 改版履歴 
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.07   ToanPQ      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.common.biz.kk;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M018bnkm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

/**
*支払金額調整
の振込手数料の取得クラス
*
*/
public class CCKK0050Furi {
    /** cmJKKCommon: common control. */
    @Inject
    private CCKKCommon cCKKCommon;

    /** cCKK0010Tax:  消費税算出部品オブジェクトの生成. */
    @Inject
    private CCKK0010Tax cCKK0010Tax;

    /** M020ctlm Mapper */
    @Inject
    private M020ctlmMapper m020ctlmMapper;

    /**入力*/

    /**支払日*/
    private String shrDate = "";

    /**仕向銀行*/
    private String simukeBank = "";

    /**仕向支店*/
    private String simukeSiten = "";

    /**被仕向銀行ｺｰﾄﾞ*/
    private String bankCd = "";

    /**被仕向支店ｺｰﾄﾞ*/
    private String sitenCd = "";

    /**振込予定金額*/
    private String kingk = "";

    /**税区分*/
    private String taxKbn = "";

    /**出力*/
    /**振込手数料(税込)*/
    private String furiTesuryo = "";

    /**振込手数料(課税金額)*/
    private String junFuri = "";

    /**振込手数料の税額*/
    private String furiTax = "";

    /**
    * コントロールＭ手数料テーブルカラム数(M020CTLM)
    **/
    public static final int M020_TESURYO_COL = 5;

    /**
    * コントロールマスタ抽出キー
    **/
    private String m020KeyH = "";
    /**
    * 仕向先銀行1桁目
    **/
    private static final String M020_KEY_SIMUKE_1 = "0";
    /**
    * 仕向先銀行2桁目
    **/
    private String m020KeySimuke2 = "";

    /**
    * コントロールマスタ取得データ
    **/

    /**端数区分*/
    @SuppressWarnings("unused")
    private String zeihasuKbn = "";

    /**基本手数料*/
    private String min = "";

    /**対象金額*/
    private String[] kingkFromArray = new String[M020_TESURYO_COL];

    /**振込手数料*/
    private String[] tensuryoArray = new String[M020_TESURYO_COL];

    /**piIDXY*/
    private int piIDXY = 0;

    /**
     * Beanオブジェクトを構築します.
     * <p>
     * 概要：<br>
     *  Beanオブジェクトを構築します。<br>
     * <p>
     */
    public CCKK0050Furi() {
        // メンバの初期化
        clear();
    }

    /**
     * メンバの初期化処理.
     * <p>
     * 機能概要：<br>
     * メンバの初期化処理を行います。（実装必須メソッド）
     * <p> 
     */
    public void clear() {
        /**入力*/
        shrDate = ""; // 支払日
        simukeBank = ""; // 仕向銀行
        simukeSiten = ""; // 仕向支店
        bankCd = ""; // 被仕向銀行ｺｰﾄﾞ
        sitenCd = ""; // 被仕向支店ｺｰﾄﾞ
        kingk = ""; // 振込予定金額
        taxKbn = ""; // 税区分

        /**出力*/
        furiTesuryo = ""; // 振込手数料(税込)
        junFuri = ""; // 振込手数料(課税金額)
        furiTax = ""; // 振込手数料の税額

        /**ControlM*/
        zeihasuKbn = ""; // 端数区分
        min = ""; // 基本手数料

        for (int i = 0; i < M020_TESURYO_COL; i++) {
            kingkFromArray[i] = ""; // 対象金額
            tensuryoArray[i] = ""; // 振込手数料
        }
        piIDXY = 0;
    }

    /**
     * メイン処理.
     * <p>
     * 機能概要：<br>
     * メイン処理を行います。
     * <p> 
     * @param なし
     * @return true：処理成功、false：処理失敗
     */
    public boolean checkFuriKingk() {
        // 振込手数料情報編集処理
        if (!checkFuriMainProc()) {
            return false;
        }

        // 振込手数料算出 終了処理
        if (!checkFuriFinalProc()) {
            return false;
        }

        return true;
    }

    /**
     * 振込手数料算出メイン処理.
     * <p>
     * 機能概要：<br>
     * 振込手数料算出処理を行います。
     * <p> 
     * @param なし
     * @return true：処理成功、false：処理失敗
     */
    public boolean checkFuriMainProc() {
        // --------------
        // 仕向銀行 銀行ｺｰﾄﾞ･支店ｺｰﾄﾞ取得処理
        // --------------
        // 仕向銀行 銀行ｺｰﾄﾞ･支店ｺｰﾄﾞ 被仕向 銀行ｺｰﾄﾞ･支店ｺｰﾄﾞ比較
        // 同銀行･同支店
        // 銀行ＭにKEY設定
        M018bnkm m018bnkm = cCKKCommon.getComM018bnkm(shrDate, simukeBank, simukeSiten);

        // 銀行Ｍ読込み
        if (m018bnkm == null) {
            return false;
        }

        // 仕向先区分を取得する
        m020KeySimuke2 = m018bnkm.getSimukeKbn();

        if (simukeBank.equals(bankCd)) {
            if (simukeSiten.equals(sitenCd)) {
                piIDXY = 1;
                m020KeyH = "FE1";
            } else { // 同銀行･他支店
                piIDXY = 2;
                m020KeyH = "FE2";
            }
        } else { // 他銀行
            piIDXY = 3;
            m020KeyH = "FE3";
        }

        // ｺﾝﾄﾛｰﾙﾏｽﾀ読込み
        if (!checkKingkTensuryo()) {
            return false;
        }

        // 振込手数料算出処理
        if (!checkFuriTesuryo()) {
            return false;
        }
        return true;
    }

    /**
     * 振込手数料算出 終了処理.
     * <p>
     * 機能概要：<br>
     * 振込手数料算出 終了処理を行います。
     * <p> 
     * @param なし
     * @return true：処理成功、false：処理失敗
     */
    public boolean checkFuriFinalProc() {
        // --------
        // 振込手数料消費税計算
        // --------

        // 対象金額のセット
        cCKK0010Tax.setKingkIn(furiTesuryo);

        // 税区分のセット
        cCKK0010Tax.setTaxKbn(taxKbn);

        // 端数区分のセット
        cCKK0010Tax.setHasuKbn("0");

        boolean bRet = cCKK0010Tax.doCheckTaxKingk();

        if (!bRet) {
            return false;
        } else {
            if (cCKK0010Tax.getTaxSyu().equals(CCKK0010Tax.ZEI_KBN.ZEI_SYU_5)
                    || cCKK0010Tax.getTaxSyu().equals(CCKK0010Tax.ZEI_KBN.ZEI_SYU_6)
                    || cCKK0010Tax.getTaxSyu().equals(CCKK0010Tax.ZEI_KBN.ZEI_SYU_7)
                    || cCKK0010Tax.getTaxSyu().equals(CCKK0010Tax.ZEI_KBN.ZEI_SYU_8)) {
                long lFuri = 0;
                long lTax = 0;
                if ("".equals(furiTesuryo)) {
                    lFuri = 0;
                } else {
                    lFuri = Long.parseLong(furiTesuryo);
                }
                String taxK = cCKK0010Tax.getTaxKingk();
                if ("".equals(taxK)) {
                    lTax = 0;
                } else {
                    lTax = Long.parseLong(taxK);
                }
                lFuri += lTax;
                furiTesuryo = String.valueOf(lFuri);
            }

            junFuri = cCKK0010Tax.getKingkOt();
            furiTax = cCKK0010Tax.getTaxKingk();
        }
        return true;
    }

    /**
     * 振込手数料算出処理.
     * <p>
     * 機能概要：<br>
     * 振込手数料算出処理を行います。
     * <p> 
     * @param なし
     * @return true：処理成功、false：処理失敗
     */
    public boolean checkFuriTesuryo() {
        if (piIDXY < 0) {
            furiTesuryo = "0";
        } else {
            furiTesuryo = min;

            for (int i = 0; i < M020_TESURYO_COL; i++) {
                if (CCStringUtil.cnvStrToLong(kingk) <= CCStringUtil.cnvStrToLong(kingkFromArray[i])) {
                    furiTesuryo = tensuryoArray[i];
                    if (CCStringUtil.cnvStrToLong(furiTesuryo) < CCStringUtil.cnvStrToLong(min)) {
                        furiTesuryo = min;
                    }
                    break;
                }
            }
        }
        return true;
    }

    /**
     * コントロールマスタ取得処理.
     * <p>
     * 機能概要：<br>
     * コントロールマスタから税レコードを取得します。
     * <p> 
     * @param なし
     * @return true：処理成功、false：処理失敗
     */

    public boolean checkKingkTensuryo() {
        String sRet = "";
        // コントロールマスタ検索オブジェクト作成
        M020ctlmExample m020ctlmExample = new M020ctlmExample();

        // 検索条件のセット
        m020ctlmExample.createCriteria().andCdEqualTo(m020KeyH + M020_KEY_SIMUKE_1 + m020KeySimuke2);
        List<M020ctlm> m020ctlmList = m020ctlmMapper.selectByExample(m020ctlmExample);

        if (m020ctlmList != null && m020ctlmList.size() > 0) {
            sRet = m020ctlmList.get(0).getData();

            // 税区分
            taxKbn = sRet.substring(0, 1);

            // 端数区分
            zeihasuKbn = sRet.substring(1, 2);

            // 基本手数料
            min = sRet.substring(2, 8);

            for (int i = 0; i < M020_TESURYO_COL; i++) {
                // 対象金額
                kingkFromArray[i] = sRet.substring((i * 16) + 8, (i * 16) + 18);

                // 振込手数料
                tensuryoArray[i] = sRet.substring((i * 16) + 18, (i * 16) + 24);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * 支払日取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされているを支払日取得します。
     * <p>
     * @param なし
     * @return 支払日
     */
    public String getShrDate() {
        return shrDate;
    }

    /**
    * 支払日のセット処理.
    * <p>
    * 機能概要：<br>
    * 支払日をセットします。
    * <p> 
    * @param shrDate 支払日
     */
    public void setShrDate(String shrDate) {
        this.shrDate = shrDate;
    }

    /**
    * 仕向銀行取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている仕向銀行を取得します。
    * <p> 
    * @return 仕向銀行
    */
    public String getSimukeBank() {
        return simukeBank;
    }

    /**
    * 仕向銀行のセット処理.
    * <p>
    * 機能概要：<br>
    * 仕向銀行をセットします。
    * <p> 
    * @param simukeBank 仕向銀行
    */
    public void setSimukeBank(String simukeBank) {
        this.simukeBank = simukeBank;
    }

    /**
     * 仕向支店取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされている仕向支店を取得します。
     * <p> 
     * @param なし
     * @return 仕向支店
     */
    public String getSimukeSiten() {
        return simukeSiten;
    }

    /**
    * 仕向支店のセット処理.
    * <p>
    * 機能概要：<br>
    * 仕向支店をセットします。
    * <p> 
    * @param simukeSiten 仕向支店
    */
    public void setSimukeSiten(String simukeSiten) {
        this.simukeSiten = simukeSiten;
    }

    /**
    * 被仕向銀行ｺｰﾄﾞ取得処理.
    * <p>
    * 機能概要：<br>
    * メンバ変数にセットされている被仕向銀行ｺｰﾄﾞを取得します。
    * <p> 
    * @return 被仕向銀行ｺｰﾄﾞ 1:外税 2:内税 3:非課税
    */
    public String getBankCd() {
        return bankCd;
    }

    /**
    * 被仕向銀行ｺｰﾄﾞのセット処理.
    * <p>
    * 機能概要：<br>
    * 被仕向銀行ｺｰﾄﾞをセットします。
    * <p> 
    * @param bankCd 被仕向銀行ｺｰﾄﾞ 1:外税 2:内税 3:非課税
    */
    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    /**
     * 被仕向支店ｺｰﾄﾞ取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされている被仕向支店ｺｰﾄﾞを取得します。
     * <p> 
     * @param なし
     * @return 被仕向支店ｺｰﾄﾞ
     */
    public String getSitenCd() {
        return sitenCd;
    }

    /**
     * 被仕向支店ｺｰﾄﾞのセット処理.
     * <p>
     * 機能概要：<br>
     * 被仕向支店ｺｰﾄﾞをセットします。
     * <p> 
     * @param sitenCd 被仕向支店ｺｰﾄﾞ
     */
    public void setSitenCd(String sitenCd) {
        this.sitenCd = sitenCd;
    }

    /**
     * 振込予定金額取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされている振込予定金額を取得します。
     * <p> 
     * @param なし
     * @return 振込予定金額
     */
    public String getKingk() {
        return kingk;
    }

    /**
     * 振込予定金額のセット処理.
     * <p>
     * 機能概要：<br>
     * 振込予定金額をセットします。
     * <p> 
     * @param kingk 振込予定金額
     */
    public void setKingk(String kingk) {
        this.kingk = kingk;
    }

    /**
     * 税区分取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされている税区分を取得します。
     * <p> 
     * @return 税区分
     */
    public String getTaxKbn() {
        return taxKbn;
    }

    /**
     * 税区分のセット処理.
     * <p>
     * 機能概要：<br>
     * 税区分をセットします。
     * <p> 
     * @param taxKbn 税区分
     */
    public void setTaxKbn(String taxKbn) {
        this.taxKbn = taxKbn;
    }

    /**
     * 振込手数料(課税金額)取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされているを振込手数料(課税金額)取得します。
     * <p> 
     * @param なし
     * @return 振込手数料(課税金額)
     */
    public String getJunFuri() {
        return junFuri;
    }

    /**
    * 振込手数料(課税金額)のセット処理.
    * <p>
    * 機能概要：<br>
    * 振込手数料(課税金額)をセットします。
    * <p> 
    * @param junFuri 振込手数料(課税金額)
    */
    public void setJunFuri(String junFuri) {
        this.junFuri = junFuri;
    }

    /**
     * 振込手数料の税額取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされているを振込手数料の税額取得します。
     * <p> 
     * @param なし
     * @return 振込手数料の税額
     */
    public String getFuriTax() {
        return furiTax;
    }

    /**
     * 振込手数料の税額のセット処理.
     * <p>
     * 機能概要：<br>
     * 振込手数料の税額をセットします。
     * <p> 
     * @param furiTax 振込手数料の税額
     */
    public void setFuriTax(String furiTax) {
        this.furiTax = furiTax;
    }

    /**
     * 振込手数料(税込)のセット処理.
     * <p>
     * 機能概要：<br>
     * 振込手数料(税込)をセットします。
     * @param furiTesuryo 振込手数料(税込)
     */
    public void setFuriTesuryo(String furiTesuryo) {
        this.furiTesuryo = furiTesuryo;
    }

    /**
     * 振込手数料(税込)取得処理.
     * <p>
     * 機能概要：<br>
     * メンバ変数にセットされているを振込手数料(税込)取得します。
     * <p> 
     * @return 振込手数料(税込)
     */
    public String getFuriTesuryo() {
        return this.furiTesuryo;
    }
}
