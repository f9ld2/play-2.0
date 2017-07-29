///////////////////////////////////////////////////////////////////////
//Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : コントロールマスタメンテナンス
 * 
 * 改版履歴
 * 
 * Rev. 改版年月日 改版者名 内容
 * 
 * 1.0 20140310 PhongTQ 新規作成
 * 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.kk;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp0080ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K008trhkMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K008trhk;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K008trhkExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*取引項目ﾏｽﾀﾒﾝﾃﾅﾝｽ
*のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Kkjp0080Ctrl extends Controller {

    /** K008TRHK Mappper */
    @Inject
    private K008trhkMapper k008trhkMapper;
    /** Error message */
    @Inject
    private ErrorResponse errRes;
    /** System Context */
    @Inject
    private CCSystemContext context;
    /** cmJKKCommon */
    @Inject
    private CCKKCommon kkCommon;
    /** EMPTY = "" */
    private static final String EMPTY = "";

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * 
     * @param kaisyaCd 会社コード
     * @param kakToriKmk 取引項目コード
     * @return クライアントへ返却する結果
     */
    public Result query(String kaisyaCd, String kakToriKmk) {

        String kaisyaCdQuery = kaisyaCd;
        String kakToriKmkQuery = kakToriKmk;

        // 登録データ取得処理を行う。
        K008trhkExample example = new K008trhkExample();
        // 会社コード、 取引項目コード
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCdQuery).andKakToriKmkEqualTo(kakToriKmkQuery);
        List<K008trhk> list = this.k008trhkMapper.selectByExample(example);

        if (list.size() == 0) {
            return notFound();
        }

        List<Kkjp0080ResultForm> resultList = new ArrayList<Kkjp0080ResultForm>();
        Kkjp0080ResultForm resultForm = new Kkjp0080ResultForm();
        
        //copy from entity to result form
        BeanUtils.copyProperties(list.get(0), resultForm);

        // ResultFormClassに登録データを設定する。
        resultList.add(resultForm);

        return ok(Json.toJson(resultList));
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
     * 
     * @param kaisyaCd 会社コード
     * @param kakToriKmk 取引項目コード
     * @return クライアントへ返却する結果
     */
    public Result save(String kaisyaCd, String kakToriKmk) {
        DateTime dt = new DateTime();
        Form<Kkjp0080ResultForm> emptyForm = new Form<Kkjp0080ResultForm>(Kkjp0080ResultForm.class);
        Form<Kkjp0080ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp0080ResultForm req = reqForm.get();

            // 入力チェック
            boolean bResult = checkInputData(req.getToriNm(), req.getKanjoKbn(), req.getKmkCd(), req.getTaxKbn());
            if (!bResult) {
                return badRequest(Json.toJson(errRes));
            }

            K008trhk rec = new K008trhk();
            BeanUtils.copyProperties(req, rec);
            rec.setCmnTantoCd(context.getTantoCode());
            rec.setCmnTermId(context.getTermName());
            rec.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
            rec.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
            rec.setCmnInsdd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
            if (this.k008trhkMapper.insertSelective(rec) <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
            }
            return created();
        }
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBの該当データを更新(論理)する。*
     * 
     * @param kaisyaCd 会社コード
     * @param kakToriKmk 取引項目コード
     * @return クライアントへ返却する結果
     */
    public Result update(String kaisyaCd, String kakToriKmk) {
        DateTime dt = new DateTime();
        Form<Kkjp0080ResultForm> emptyForm = new Form<Kkjp0080ResultForm>(Kkjp0080ResultForm.class);
        Form<Kkjp0080ResultForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            Kkjp0080ResultForm req = reqForm.get();

            // 入力チェック
            boolean bResult = checkInputData(req.getToriNm(), req.getKanjoKbn(), req.getKmkCd(), req.getTaxKbn());
            if (!bResult) {
                return badRequest(Json.toJson(errRes));
            }

            // 指定された発効日で有効なレコードが存在するかチェックする
            K008trhkExample example = new K008trhkExample();
            example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andKakToriKmkEqualTo(kakToriKmk);
            K008trhk rec = new K008trhk();
            BeanUtils.copyProperties(req, rec);

            rec.setCmnTantoCd(context.getTantoCode());
            rec.setCmnTermId(context.getTermName());
            rec.setCmnUpddd(dt.toString(CCDateUtil.DATETIME_FORMAT_DATE_YYYYMMDD));
            rec.setCmnUpdtime(dt.toString(CCDateUtil.DATETIME_FORMAT_TIME));
            if (this.k008trhkMapper.updateByExampleSelective(rec, example) <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }

            return ok();
        }
    }

    /**
     * 引数とリクエスト(パラメータ)を条件にDBの該当データを削除(論理)する*
     * 
     * @param kaisyaCd 会社コード
     * @param kakToriKmk 取引項目コード
     * @return クライアントへ返却する結果
     */
    public Result delete(String kaisyaCd, String kakToriKmk) {
        K008trhkExample example = new K008trhkExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andKakToriKmkEqualTo(kakToriKmk);
        List<K008trhk> list = this.k008trhkMapper.selectByExample(example);
        if (list.size() > 0) {
            if (this.k008trhkMapper.deleteByExample(example) <= 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
            }

            return ok();
        } else {
            // 排他エラー(排他キーを使って排他制御すれば発生しないはず)
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_GET_DELETE_DATA_FAIL);
            return badRequest(Json.toJson(errRes));
        }
    }
    
    /**
     * 入力チェック
     * 
     * @param sToriName 取引項目名称
     * @param sKanjoKbn 勘定区分
     * @param sKmkCd 科目
     * @param sTaxKbn 税区分
     * @return 正常：true / 異常：false
     */
    private boolean checkInputData(String sToriName, String sKanjoKbn, String sKmkCd, String sTaxKbn) {

        String sResult = EMPTY;
        String sDate = EMPTY;

        if (CCStringUtil.isEmpty(sToriName)) {
            // C0012
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("toriNm", CCMessageConst.MSG_KEY_NOT_KIP_ITEM);
            return false;
        } else {
            if (!CCStringUtil.propByte(sToriName)) {
                // K1004
                errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                errRes.addErrorInfo("toriNm", CCMessageConst.MSG_KEY_INPUT_NOT_VALID_1BYPTES_OR_2BYTES);
                return false;
            } else {
                int bytelen = CCStringUtil.getByteLen(sToriName);
                if (bytelen == sToriName.length() && bytelen > 40) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("toriNm", CCMessageConst.MSG_KEY_INPUT_TEXT_1BYTE_LIMIT40);
                    return false;
                } else if (bytelen > 40) {
                    errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
                    errRes.addErrorInfo("toriNm", CCMessageConst.MSG_KEY_INPUT_TEXT_2BYTE_LIMIT20);
                    return false;
                }
            }
        }
        // 勘定区分チェック
        // 画面「勘定区分」!=1 AND 画面「勘定区分」!=2の場合
        if (!CCKubunConst.KBN_VAL_K_KANJO_KBN_PAYABLE.equals(sKanjoKbn)
                && !CCKubunConst.KBN_VAL_K_KANJO_KBN_OFFSET.equals(sKanjoKbn)) {
            // 「K1005」メッセージが表示される。
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("kanjoKbn", CCMessageConst.MSG_KEY_INPUT_KANJO_INVALID);
            return false;
        }
        sResult = kkCommon.getKnajoKbnNm(sDate, sKanjoKbn);
        if (EMPTY.equals(sResult)) {
            // 「K1006」メッセージが表示される。
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("kanjoKbn", CCMessageConst.MSG_KEY_INPUT_KANJO_NOT_EXISTS);
            return false;
        }

        // 科目チェック
        sResult = kkCommon.getKmkCdNm(sDate, sKmkCd);
        if (EMPTY.equals(sResult)) {
            // 「K1007」メッセージが表示される。
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("kmkCd", CCMessageConst.MSG_KEY_INPUT_KMK_NOT_EXISTS);
            return false;
        }

        // 税区分チェック
        // 画面「税区分」< 1 OR 画面「税区分」> 9の場合
        int iTaxKbn = Integer.parseInt(sTaxKbn);
        if (iTaxKbn < 1 || iTaxKbn > 9) {
            // 「K1005」メッセージが表示される。
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("taxKbn", CCMessageConst.MSG_KEY_INPUT_TAXKBN_INVALID);
            return false;
        }
        sResult = kkCommon.getTaxKbnNm(sDate, sTaxKbn);
        if (EMPTY.equals(sResult)) {
            // 「K1009」メッセージが表示される。
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo("taxKbn", CCMessageConst.MSG_KEY_INPUT_TAXKBN_NOT_EXISTS);
            return false;
        }

        return true;
    }
}
