// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 移動伝票入力 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014/04/05 Hungtb 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.common.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.CodeNameCondForm;
import jp.co.necsoft.web_md.core.common.forms.CommonMasterCondForm;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.K008trhkMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M002dbumMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M003bmnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M005bnrmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M012tngmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M013makmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M016tanmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M017meimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M018bnkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M708dpkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T000kkkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.T005okkmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.X007kbnmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.Y001tntmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeMaster;
import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeName;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K008trhkExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M002dbumExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M003bmnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M005bnrmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M012tngmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M013makmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M016tanmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M017meimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M018bnkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M708dpkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T000kkkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T005okkmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X007kbnmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Y001tntmExample;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 
 * @author hungtbz
 * @since 2014/04/05
 */
public class CodeNameCtrl extends Controller {

    /**  削除Flag:Normal */
    private static final String DELETED_FLAG_NORMAL = "0";
    
    /** 取引先休止区分 */
    private static final String TORI_STOP_KBN_NORMAL = "9";
    
    /** 伝票サブ区分 */
    private static final String DNP_SUB_KBN_PARENT = "00";

    /** K008trhkMapper mapper of table K008trhk */
    @Inject
    private K008trhkMapper k008trhkMapper;
    /** M000kaimMapper mapper of table M000kaim */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** M001jgymMapper mapper of table M001jgym */
    @Inject
    private M001jgymMapper m001jgymMapper;
    /** M002dbumMapper mapper of table M002dbqueryM012tngmum */
    @Inject
    private M002dbumMapper m002dbumMapper;
    /** M003bmnmMapper mapper of table M003bmnm */
    @Inject
    private M003bmnmMapper m003bmnmMapper;
    /** M005bnrmMapper mapper of table M005bnrm */
    @Inject
    private M005bnrmMapper m005bnrmMapper;
    /** M007kijmMapper mapper of table M007kijm */
    @Inject
    private M007kijmMapper m007kijmMapper;
    /** M017meimMapper mapper of table M017meim */
    @Inject
    private M017meimMapper m017meimMapper;
    /** M013makmMapper mapper of table M013makm */
    @Inject
    private M013makmMapper m013makmMapper;
    /** M006tenmMapper mapper of table M006tenm */
    @Inject
    private M006tenmMapper m006tenmMapper;
    /** M011trimMapper mapper of table M011trim */
    @Inject
    private M011trimMapper m011trimMapper;
    /** X007kbnmMapper mapper of table X007kbnm */
    @Inject
    private X007kbnmMapper x007kbnmMapper;
    /** T005okkmMapper mapper of table T005okkm */
    @Inject
    private T005okkmMapper t005okkmMapper;
    /** T000kkkmMapper mapper of table T000kkkm */
    @Inject
    private T000kkkmMapper t000kkkmMapper;
    /** M018bnkmMapper mapper of table M018bnkm */
    @Inject
    private M018bnkmMapper m018bnkmMapper;
    /** M016tanmMapper mapper of table M016tanm */
    @Inject
    private M016tanmMapper m016tanmMapper;
    /** M012tngmMapper mapper of table M012tngm */
    @Inject
    private M012tngmMapper m012tngmMapper;
    /** y001tntmMapper mapper of table T001tntm */
    @Inject
    private Y001tntmMapper y001tntmMapper;
    
    /** M708dpkm's Mapper */
    @Inject
    private M708dpkmMapper m708dpkmMapper;
    /** ErrorResponse */
    @Inject
    private ErrorResponse errRes;
    /** CCSystemContext */
    @Inject
    private CCSystemContext context;

    /**
     * @return result
     */
    public Result queryM000kaim() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M000kaimExample example = new M000kaimExample();
            example.createCriteria().andActKbnNotEqualTo("9");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("KAISYA_CD");
            List<CodeName> list = this.m000kaimMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
    * @return result
    */
    public Result queryM000kaim2() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M000kaimExample example = new M000kaimExample();
            example.createCriteria().andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("KAISYA_CD");
            List<CodeMaster> list = this.m000kaimMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param janCd ＪＡＮコード
     * @return result
     */
    public Result queryM007kijm(String janCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M007kijmExample example = new M007kijmExample();
            example.createCriteria().andJanCdEqualTo(janCd);
            example.setSearchDate(req.getHakkoDay());
            // example.setOrderByClause("JAN_CD");

            List<CodeMaster> list = this.m007kijmMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param kaisyaCd 会社
     * @return result
     */
    public Result queryM001jgym(String kaisyaCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M001jgymExample example = new M001jgymExample();
            example.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andActKbnNotEqualTo("9");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("JIGYOBU_CD");
            List<CodeName> list = this.m001jgymMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param kaisyaCd 会社
     * @return result
     */
    public Result queryM001jgym2(String kaisyaCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M001jgymExample example = new M001jgymExample();
            example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("JIGYOBU_CD");
            List<CodeMaster> list = this.m001jgymMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * @return result
     */
    public Result queryM002dbum() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M002dbumExample example = new M002dbumExample();
            example.createCriteria().andActKbnNotEqualTo("9");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("DBU_CD");
            List<CodeName> list = this.m002dbumMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * @return result
     */
    public Result queryM002dbum2() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M002dbumExample example = new M002dbumExample();
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("DBU_CD");
            List<CodeMaster> list = this.m002dbumMapper.selectCodeMaterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param jigyobuCd 事業部
     * @return result
     */
    public Result queryM003bmnm(String jigyobuCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M003bmnmExample example = new M003bmnmExample();
            example.createCriteria().andBmnCdLike(jigyobuCd + "%").andActKbnNotEqualTo("9");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("BMN_CD");
            List<CodeName> list = this.m003bmnmMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param jigyobuCd 事業部
     * @return result
     */
    public Result queryM003bmnm2(String jigyobuCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            HashMap<String, String> parameter = new HashMap<String, String>();
            parameter.put("searchDate", req.getHakkoDay());
            parameter.put("ccDelexist", "'" + req.getCcDelexist() + "'");
            parameter.put("bmnCd", jigyobuCd);
            List<CodeMaster> list = this.m003bmnmMapper.selectCodeNameByExample2(parameter);

            return ok(Json.toJson(list));
        }
    }

    /**
     * queryM003bmnm2
     * @return result
     */
    public Result queryM003bmnm2s() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            HashMap<String, String> parameter = new HashMap<String, String>();
            parameter.put("searchDate", req.getHakkoDay());
            parameter.put("ccDelexist", "'" + req.getCcDelexist() + "'");
            List<CodeMaster> list = this.m003bmnmMapper.selectCodeNameByExample2(parameter);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param bmnCd 部門
     * @param chuBnrCd 中分類コード
     * @return result
     */
    public Result queryM005bnrm(String bmnCd, String chuBnrCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M005bnrmExample example = new M005bnrmExample();

            example.createCriteria().andBmnCdEqualTo(bmnCd).andActKbnNotEqualTo("9");
            example.setSearchDate(req.getHakkoDay());
            if (chuBnrCd == null) {
                example.getOredCriteria().get(0).andChuBnrCdNotEqualTo("0000").andShoBnrCdEqualTo("0000");
                example.setCodeNameLevel("CHU_BNR");
                example.setOrderByClause("CHU_BNR_CD");
            } else {
                example.getOredCriteria().get(0).andChuBnrCdEqualTo(chuBnrCd).andShoBnrCdNotEqualTo("0000");
                example.setCodeNameLevel("SHO_BNR");
                example.setOrderByClause("SHO_BNR_CD");
            }
            List<CodeName> list = this.m005bnrmMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param cdKbn コード
     * @param cd コード
     * @return result
     */
    public Result queryM017meim(String cdKbn, String cd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M017meimExample example = new M017meimExample();
            example.createCriteria().andCdKbnEqualTo(cdKbn).andCdLike(cd + "%").andActKbnNotEqualTo("9");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("CD");
            List<CodeName> list = this.m017meimMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * M017meimコードを取得する
     * @param cdKbn コード
     * @param cd コード
     * @return result
     */
    public Result queryM017meim2(String cdKbn, String cd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M017meimExample example = new M017meimExample();
            example.createCriteria().andCdKbnEqualTo(cdKbn).andCdLike(cd + "%");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("CD");
            List<CodeMaster> list = this.m017meimMapper.selectCodeMasterByExample(example);
            return ok(Json.toJson(list));
        }
    }

    /**
     * 全てdKbn コードを取得する
     * @return result
     */
    public Result queryAllX007kbnm() {
        X007kbnmExample example = new X007kbnmExample();
        example.createCriteria().andCdNotEqualTo("*").andActKbnNotEqualTo("9")
            .andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);
        example.setOrderByClause("CD");
        List<CodeMaster> list = this.x007kbnmMapper.selectCodeMasterByExample(example);
        return ok(Json.toJson(list));
    }

    /**
     * X007kbnmコードを取得する
     * @param cdKbn コード
     * @return result
     */
    public Result queryX007kbnm(String cdKbn) {
        X007kbnmExample example = new X007kbnmExample();
        example.createCriteria().andCdKbnEqualTo(cdKbn).andCdNotEqualTo("*").andActKbnNotEqualTo("9");
        example.setOrderByClause("CD");
        List<CodeMaster> list = this.x007kbnmMapper.selectCodeMasterByExample(example);
        return ok(Json.toJson(list));
    }

    /**
     * メーカー名称取征E
     * 
     * @param mkrCd
     *            メーカーコーチE
     * @return result
     */
    public Result queryM013makm(String mkrCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M013makmExample example = new M013makmExample();
            example.createCriteria().andMkrCdLike(mkrCd + "%");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("MKR_CD");

            List<CodeMaster> list = this.m013makmMapper.selectCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 刁E��名称取征E
     * 
     * @param jigyobuCd 事業部
     * @param rejiCd レジ部門コード
     * @return result
     */
    public Result queryBrnNmByRejiCd(String rejiCd, String jigyobuCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            String subRejiCd = rejiCd.substring(1, 4);

            List<CodeMaster> list =
                    this.m005bnrmMapper.selectCodeName1ByExample(rejiCd, subRejiCd, jigyobuCd, req.getHakkoDay());

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param tenCd 店舗
     * @param kaisyaCd 会社
     * @param jigyobuCd 事業部
     * @return result
     */
    public Result queryM006tenm(String tenCd, String kaisyaCd, String jigyobuCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M006tenmExample example = new M006tenmExample();
            example.createCriteria().andTenCdEqualTo(kaisyaCd + jigyobuCd + tenCd);
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m006tenmMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }
    
    /**
     * 
     * @param tenCd 店舗
     * @param kaisyaCd 会社
     * @param jigyobuCd 事業部
     * @param tenCd
     * @return result
     */
    public Result queryY001tntm(String tenantCd, String kaisyaCd, String jigyobuCd, String tenCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            Y001tntmExample example = new Y001tntmExample();
            example.createCriteria().andTenCdEqualTo(kaisyaCd + jigyobuCd + tenCd)
            .andTenantCdEqualTo(tenantCd);
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.y001tntmMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }
    
    /**
     * Select tenant list for build tenant combobox directive
     * @param kaisyaCd 会社
     * @param jigyobuCd 事業部
     * @param tenCd 店舗
     * @return result
     */
    public Result queryY001tntm2(String kaisyaCd, String jigyobuCd, String tenCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            Y001tntmExample example = new Y001tntmExample();
            example.createCriteria().andTenCdEqualTo(kaisyaCd + jigyobuCd + tenCd);
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("TENANT_CD");
            example.setDistinct(true);

            List<CodeMaster> list = this.y001tntmMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }
    
    /**
     * get list k008trhk
     * @param kaisyaCd
     * @return Result
     */
    public Result queryK008trhk2(String kaisyaCd) {
        K008trhkExample example = new K008trhkExample();
        example.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        List<CodeMaster> list = this.k008trhkMapper.selectCodeMasterByExample(example);
        return ok(Json.toJson(list));
    }

    /**
     * 
     * @param kaisyaCd 会社
     * @param jigyobuCd 事業部
     * @return result
     */
    public Result queryM006tenm2(String kaisyaCd, String jigyobuCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("searchDate", req.getHakkoDay());
            params.put("kaisyaCd", kaisyaCd);
            params.put("jigyobuCd", jigyobuCd);

            List<CodeMaster> list = this.m006tenmMapper.selectTenCdsByKaisyaJigyobuHakkoDay(params);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param shobnrCd 小分類コード
     * @param jigyobuCd 事業部
     * @param bmnCd 部門
     * @param chubnrCd 中分類コード
     * @return result
     */
    public Result queryM005bnrm2(String shobnrCd, String jigyobuCd, String bmnCd, String chubnrCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M005bnrmExample example = new M005bnrmExample();
            example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                    .andChuBnrCdEqualTo(chubnrCd).andShoBnrCdEqualTo(shobnrCd).andYotoKbnEqualTo("0000");
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m005bnrmMapper.selectNameShortnameKubunByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param chubnrCd 中分類コード
     * @param jigyobuCd 事業部
     * @param bmnCd 部門
     * @return result
     */
    public Result queryM005bnrm3(String chubnrCd, String jigyobuCd, String bmnCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M005bnrmExample example = new M005bnrmExample();
            example.createCriteria().andBmnCdEqualTo(jigyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                    .andChuBnrCdEqualTo(chubnrCd).andYotoKbnEqualTo("0000").andShoBnrCdEqualTo("0000");
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m005bnrmMapper.selectNameShortnameKubunByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param igyobuCd 事業部
     * @param bmnCd 部門
     * @return result
     */
    public Result queryM005bnrm4(String igyobuCd, String bmnCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M005bnrmExample example = new M005bnrmExample();
            example.createCriteria().andBmnCdEqualTo(igyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                    .andChuBnrCdNotEqualTo("0000").andYotoKbnEqualTo("0000").andShoBnrCdEqualTo("0000");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("CHU_BNR_CD");

            List<CodeMaster> list = this.m005bnrmMapper.selectChubnrCdByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param igyobuCd 事業部
     * @param bmnCd 部門
     * @param chubnrCd 中分類コード
     * @return result
     */
    public Result queryM005bnrm5(String igyobuCd, String bmnCd, String chubnrCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M005bnrmExample example = new M005bnrmExample();
            example.createCriteria().andBmnCdEqualTo(igyobuCd + bmnCd).andRejiCdEqualTo("0" + bmnCd)
                    .andChuBnrCdEqualTo(chubnrCd).andYotoKbnEqualTo("0000").andShoBnrCdNotEqualTo("0000");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("SHO_BNR_CD");

            List<CodeMaster> list = this.m005bnrmMapper.selectShobnrCdByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param jigyobuCd 事業部
     * @return result
     */
    public Result queryM005bnrm6(String jigyobuCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            HashMap<String, String> params = new HashMap<String, String>();
            params.put("searchDate", req.getHakkoDay());
            params.put("jigyobuCd", jigyobuCd);

            List<CodeMaster> list = this.m005bnrmMapper.selectBmnCdByJigyobuCd(params);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 取引�E名称取征E
     * 
     * @param triCd
     *            取引�EコーチE
     * @return result
     */
    public Result queryM011trim(String triCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M011trimExample example = new M011trimExample();
            example.createCriteria().andTriCdEqualTo(triCd).andToriStopKbnEqualTo(TORI_STOP_KBN_NORMAL);
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m011trimMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param triCd
     *            取引�EコーチE
     * @return result
     */
    public Result queryM011trim2(String triCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M011trimExample example = new M011trimExample();
            example.createCriteria().andTriCdEqualTo(triCd + "000").andDeleteFlgEqualTo(DELETED_FLAG_NORMAL)
                    .andToriStopKbnEqualTo(TORI_STOP_KBN_NORMAL);
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m011trimMapper.selectCodeNameShortKubunByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @return result
     */
    public Result queryM011trim3() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M011trimExample example = new M011trimExample();
            example.createCriteria().andDeleteFlgEqualTo(DELETED_FLAG_NORMAL)
                    .andToriStopKbnEqualTo(TORI_STOP_KBN_NORMAL);
            example.setDistinct(true);
            example.setSearchDate(req.getHakkoDay());

            List<CodeName> list = this.m011trimMapper.selectListCodeNameByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @return result
     */
    public Result queryM011trim4() {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M011trimExample example = new M011trimExample();
            example.createCriteria().andToriStopKbnEqualTo(TORI_STOP_KBN_NORMAL);
            example.setDistinct(true);
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("TRI_CD");

            List<CodeMaster> list = this.m011trimMapper.selectAllCodeMaster(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param tantoCd 担当者コード
     * @return result
     */
    public Result queryM016tanm(String tantoCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M016tanmExample example = new M016tanmExample();
            example.createCriteria().andTantoCdEqualTo(tantoCd);
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m016tanmMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param grpCd グループコード
     * @param key キーコード
     * @param jigyobuCd 事業部
     * @param bmnCd 部門
     * @return result
     */
    public Result queryM012tngm(String grpCd, String key, String jigyobuCd, String bmnCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();

            List<CodeMaster> list =
                    this.m012tngmMapper.selectCodeMasterByExample(key, (jigyobuCd + bmnCd + grpCd),
                            req.getHakkoDay());

            return ok(Json.toJson(list));
        }
    }
    
    /**
     *  queryM012tngm2
     * @param key キーコード
     * @param jigyobuCd 事業部
     * @param bmnCd 部門
     * @return result
     */
    public Result queryM012tngm2(String key, String jigyobuCd, String bmnCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M012tngmExample example = new M012tngmExample();
            example.createCriteria().andGrpKbnEqualTo(key).andGrpCdLike(jigyobuCd + bmnCd + "%");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("GRP_CD");
            example.setDistinct(true);
            List<CodeMaster> list = this.m012tngmMapper.selectCodeMasterByExample2(example);
            return ok(Json.toJson(list));
        }
    }
    
    /**
     * Get data from table M012tngm.
     * @param key キーコード
     * @return result
     */
    public Result queryM012tngm3(String key) {
        Form<CodeNameCondForm> emptyForm = new Form<CodeNameCondForm>(CodeNameCondForm.class);
        Form<CodeNameCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CodeNameCondForm req = reqForm.get();
            String grpCd = "";
            String jigyobuCd = req.getJigyobuCd();
            String bmnCd = req.getBmnCd();
            if (jigyobuCd != null && !"".equals(jigyobuCd)) {
                grpCd = grpCd + jigyobuCd;
            }
            
            if (bmnCd != null && !"".equals(bmnCd)) {
                if (jigyobuCd != null) {
                    grpCd = grpCd + bmnCd;
                } else {
                    grpCd = "%" + grpCd + bmnCd;
                }
            }
            
            M012tngmExample example = new M012tngmExample();
            example.createCriteria().andGrpKbnEqualTo(key).andGrpCdLike(grpCd + "%");
            example.setSearchDate(req.getHakkoDay());
            example.setOrderByClause("GRP_CD");
            example.setDistinct(true);
            List<CodeMaster> list = this.m012tngmMapper.selectCodeMasterByExample2(example);
            return ok(Json.toJson(list));
        }
    }

    /**
     * 
     * @param kakToriKmk 取引項目コード
     * @param kaisyaCd 会社
     * @return result
     */
    public Result queryK008trhk(String kakToriKmk, String kaisyaCd) {
        K008trhkExample example = new K008trhkExample();
        example.createCriteria().andKakToriKmkEqualTo(kakToriKmk).andKaisyaCdEqualTo(kaisyaCd)
            .andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);

        List<CodeMaster> list = this.k008trhkMapper.selectCodeMasterByExample(example);

        return ok(Json.toJson(list));
    }

    /**
     * 親企画名称取征
     * @param oyaKikakuCd 親企画コード
     * @param kaisyaCd 会社 会社コード
     * @param jigyobuCd 事業部 事業部コード
     * @param nendo 年度
     * @return result CodeMaster list
     */
    public Result queryT005okkm(String oyaKikakuCd, String kaisyaCd, String jigyobuCd, String nendo) {
        T005okkmExample example = new T005okkmExample();
        example.createCriteria().andOyaKikakuCdEqualTo(oyaKikakuCd).andKaisyaCdEqualTo(kaisyaCd)
                .andJigyobuCdEqualTo(jigyobuCd).andNendoEqualTo(nendo).andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);

        List<CodeMaster> list = this.t005okkmMapper.selectCodeMasterByExample(example);

        return ok(Json.toJson(list));
    }

    /**
     * 銀行支店略称取征
     * 
     * @param bnkStnCd
     *            支店コード
     * @param bknCd
     *            銀行コード
     * @return result CodeMaster list
     */
    public Result queryM018bnkm(String bnkStnCd, String bknCd) {
        Form<CommonMasterCondForm> emptyForm = new Form<CommonMasterCondForm>(CommonMasterCondForm.class);
        Form<CommonMasterCondForm> reqForm = emptyForm.bindFromRequest();
        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        } else {
            CommonMasterCondForm req = reqForm.get();
            M018bnkmExample example = new M018bnkmExample();
            example.createCriteria().andBankBranchCdEqualTo(bnkStnCd).andBankCdEqualTo(bknCd);
            example.setSearchDate(req.getHakkoDay());

            List<CodeMaster> list = this.m018bnkmMapper.selectCodeMasterByExample(example);

            return ok(Json.toJson(list));
        }
    }

    /**
     * 企画名称取得
     * @param kikakuCd 企画コード
     * @param kaisyaCd 会社 会社コード
     * @param jigyobuCd 事業部 事業部コード
     * @param nendo 年度
     * @param bmnCd 部門 部門コード
     * @return result CodeMaster list
     */
    public Result queryT000kkkm(String kikakuCd, String kaisyaCd, String jigyobuCd, String nendo, String bmnCd) {
        T000kkkmExample example = new T000kkkmExample();
        example.createCriteria().andKikakuCdEqualTo(kikakuCd).andKaisyaCdEqualTo(kaisyaCd)
                .andJigyobuCdEqualTo(jigyobuCd).andNendoEqualTo(nendo).andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);
        example.setJigyobuCd(jigyobuCd);
        if (!bmnCd.equals("empty")) {
            example.setBmnCd(bmnCd);
        }

        List<CodeMaster> list = this.t000kkkmMapper.selectCodeMasterByExample(example);

        return ok(Json.toJson(list));
    }
    
    /**
     * 伝票区分マスタから伝票区分を取得する。手書入力に該当する項目のみ取得する。
     * @param denKbns 伝票区分名称
     * @return result
     */
    public Result queryM708dpkm(String denKbns) {
        List<CodeMaster> list = new ArrayList<CodeMaster>();
        if (CCStringUtil.isEmpty(denKbns)) {
            return ok(Json.toJson(list));
        }
        String[] dpyKbnArr = denKbns.split(",");
        List<String> dpyKbnList =  Arrays.asList(dpyKbnArr);
        
        M708dpkmExample example = new M708dpkmExample();
        example.createCriteria().andDenKbnIn(dpyKbnList).andDnpSubKbnEqualTo(DNP_SUB_KBN_PARENT)
                .andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);

        example.setOrderByClause("DEN_KBN");
        list = this.m708dpkmMapper.selectCodeMasterByExample(example);

        return ok(Json.toJson(list));
    }
    
    /**
     * 伝票区分マスタから伝票サブ区分から取得し、手書入力区分に表示する。
     * @param denKbns 伝票区分名称
     * @return result
     */
    public Result queryM708dpkm1(String denKbn) {
        List<CodeMaster> list = new ArrayList<CodeMaster>();
        if (CCStringUtil.isEmpty(denKbn)) {
            return ok(Json.toJson(list));
        }
        
        M708dpkmExample example = new M708dpkmExample();
        example.createCriteria().andDenKbnEqualTo(denKbn).andDnpSubKbnNotEqualTo(DNP_SUB_KBN_PARENT)
                .andDeleteFlgEqualTo(DELETED_FLAG_NORMAL);

        example.setOrderByClause("DEN_KBN");
        list = this.m708dpkmMapper.selectCodeMasterByExample1(example);

        return ok(Json.toJson(list));
    }

    /**
     * 未設定の場合は運用日付を取得して代用する。
     * @return result String
     */
    public Result getUnyoDate() {
        return ok(context.getUnyoDate());
    }
}
