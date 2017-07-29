///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 仮締設定入力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.03.28   PhucLT      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.core.app.controllers.si;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.core.app.forms.si.Sijp0190ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
 * 仮締設定入力のControllerクラス
 * 
 */
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Sijp0190Ctrl extends Controller {
    /** errRes */
    @Inject
    private ErrorResponse errRes;

    /** m020ctlmMapper */
    @Inject
    private M020ctlmMapper m020ctlmMapper;

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
     * 
     * @return クライアントへ返却する結果
     */
    public Result show() {
        Sijp0190ResultForm resultForm = new Sijp0190ResultForm();

        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("SIR00");

        List<M020ctlm> dataList = m020ctlmMapper.selectByExample(example);

        if (dataList.isEmpty()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        String hnhnymd =
                "00000000".equals(CCStringUtil.trimBoth(dataList.get(0).getData().substring(39, 47))) ? null
                        : CCStringUtil.trimBoth(dataList.get(0).getData().substring(39, 47));
        String tnhnymd =
                "00000000".equals(CCStringUtil.trimBoth(dataList.get(0).getData().substring(47, 55))) ? null
                        : CCStringUtil.trimBoth(dataList.get(0).getData().substring(47, 55));

        resultForm.setHnhnymd(hnhnymd);
        resultForm.setTnhnymd(tnhnymd);

        return ok(Json.toJson(resultForm));
    }

    /**
     * 引数とリクエスト(ボディ)からレコードを編集しDBに登録(論理)する。*
     * 
     * @return クライアントへ返却する結果
     */
    public Result save() {

        @SuppressWarnings({"unchecked", "rawtypes" })
        Form<Sijp0190ResultForm> emptyForm = new Form(Sijp0190ResultForm.class);
        Form<Sijp0190ResultForm> reqForm = emptyForm.bindFromRequest();

        Sijp0190ResultForm resultForm = reqForm.get();

        M020ctlmExample example = new M020ctlmExample();
        example.createCriteria().andCdEqualTo("SIR00");

        List<M020ctlm> dataList = m020ctlmMapper.selectByExample(example);

        if (dataList.isEmpty()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        String hnhnymd = resultForm.getHnhnymd() == null ? "00000000" : resultForm.getHnhnymd();
        String tnhnymd = resultForm.getTnhnymd() == null ? "00000000" : resultForm.getTnhnymd();

        String dataString =
                dataList.get(0).getData().substring(0, 39) + hnhnymd + tnhnymd
                        + dataList.get(0).getData().substring(55);

        dataString = CCStringUtil.fixString(dataString, 200);

        M020ctlm record = new M020ctlm();
        record.setData(dataString);

        int count = m020ctlmMapper.updateByExampleSelective(record, example);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        return ok();
    }
}
