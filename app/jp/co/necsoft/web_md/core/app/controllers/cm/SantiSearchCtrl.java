// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 
 * 機能名称 : 産地検索 改版履歴 
 * Rev. 改版年月日 改版者名 内容 1.0
 * 2014.10.14 phuclt 新規作成 
 * ===================================================================
 */

package jp.co.necsoft.web_md.core.app.controllers.cm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;

import jp.co.necsoft.web_md.core.app.dto.cm.SantiSearchResult;
import jp.co.necsoft.web_md.core.app.forms.cm.SantiSearchCondForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.H200santMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H200sant;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H200santExample;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 産地検索のControllerクラス
 *
 */
public class SantiSearchCtrl extends Controller {

    /**  削除Flag:Normal */
    private static final String DELETED_FLAG_NORMAL = "0";
    /** context */
    @Inject
    private CCSystemContext context;

    /** errRes */
    @Inject
    private ErrorResponse errRes;

    /** Mapper of h200sant */
    @Inject
    private H200santMapper h200santMapper;

    /** MAX_RECORD **/
    private static final String MAX_RECORD = "cc.app.cm.maxrecord";

    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @return クライアントへ返却する結果
     */
    public Result query() {
        Form<SantiSearchCondForm> form = new Form<SantiSearchCondForm>(SantiSearchCondForm.class);
        form = form.bindFromRequest();

        SantiSearchCondForm condForm = form.get();

        H200santExample h200santExample = new H200santExample();
        H200santExample.Criteria criteria = h200santExample.createCriteria();

        if (!CCStringUtil.isEmpty(condForm.getSantiCd())) {
            criteria.andSantiCdLike("%" + condForm.getSantiCd() + "%");
        }
        if (!CCStringUtil.isEmpty(condForm.getSantiNm())) {
            criteria.andSantiNmLike("%" + condForm.getSantiNm() + "%");
        }
        if (!CCStringUtil.isEmpty(condForm.getSantiNmR())) {
            criteria.andSantiNmRLike("%" + condForm.getSantiNmR() + "%");
        }
        if (!CCStringUtil.isEmpty(condForm.getSantiNmA())) {
            criteria.andSantiNmALike("%" + condForm.getSantiNmA() + "%");
        }
        
        criteria.andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);

        List<H200sant> dtoList = h200santMapper.selectByExample(h200santExample);
        List<SantiSearchResult> resultList = new ArrayList<SantiSearchResult>();

        if (dtoList.isEmpty()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_SEARCH_NO_RECORDS);
            return badRequest(Json.toJson(errRes));
        } else if (dtoList.size() > getMaxRecord()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_SEARCH_NUMBER_TOO_MANY);
            return badRequest(Json.toJson(errRes));
        } else {
            for (int i = 0; i < dtoList.size(); i++) {
                H200sant dto = dtoList.get(i);
                SantiSearchResult result = new SantiSearchResult();
                BeanUtils.copyProperties(dto, result);
                resultList.add(result);
            }
            return ok(Json.toJson(resultList));
        }
    }

    /**
     * getMaxRecord
     * @return String
     */
    private int getMaxRecord() {
        return Integer.parseInt(context.getContextProperty(MAX_RECORD));
    }
}
