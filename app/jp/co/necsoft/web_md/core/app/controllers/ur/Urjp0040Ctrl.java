///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 日別店別部門別売上修正
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.6   PhucLT      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.controllers.ur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.dto.ur.U011tenjResult;
import jp.co.necsoft.web_md.core.app.dto.ur.U012bmnJm004tBumM003bmnmResult;
import jp.co.necsoft.web_md.core.app.dto.ur.U012bmnjResult;
import jp.co.necsoft.web_md.core.app.dto.ur.U022bmnjResult;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp0040Dto;
import jp.co.necsoft.web_md.core.app.dto.ur.Urjp0040Grid2Dto;
import jp.co.necsoft.web_md.core.app.forms.ur.Urjp0040ResultForm;
import jp.co.necsoft.web_md.core.common.CCKubunConst;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.forms.ErrorInfo;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M001jgymMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M006tenmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M019ymdmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M020ctlmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U011tenjMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U012bmnjMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U021tenjMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U022bmnjMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U041tentMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dao.U042bmntMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgym;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M001jgymExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M019ymdm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M019ymdmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M020ctlmExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U011tenj;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U011tenjExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U012bmnj;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U012bmnjExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U021tenj;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U021tenjExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U022bmnj;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U022bmnjExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U041tent;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U041tentExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U042bmnt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U042bmntExample;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.joda.time.DateTime;
import org.mybatis.guice.transactional.Transactional;
import org.springframework.beans.BeanUtils;

import play.data.Form;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

/**
*日別店別部門別売上修正のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Urjp0040Ctrl extends Controller {

    /** ACT_KBN_DELETE = "9" */
    private static final String ACT_KBN_DELETE = "9";

    /** errRes */
    @Inject
    private ErrorResponse errRes;

    /** M020ctlmMapper */
    @Inject
    private M020ctlmMapper m020ctlmMapper;

    /** M000kaimMapper */
    @Inject
    private M000kaimMapper m000kaimMapper;

    /** M001jgymMapper */
    @Inject
    private M001jgymMapper m001jgymMapper;

    /** M006tenmMapper */
    @Inject
    private M006tenmMapper m006tenmMapper;

    /** M019ymdmMapper */
    @Inject
    private M019ymdmMapper m019ymdmMapper;

    /** U011tenjMapper */
    @Inject
    private U011tenjMapper u011tenjMapper;

    /** U012bmnjMapper */
    @Inject
    private U012bmnjMapper u012bmnjMapper;

    /** U021tenjMapper */
    @Inject
    private U021tenjMapper u021tenjMapper;

    /** U022bmnjMapper */
    @Inject
    private U022bmnjMapper u022bmnjMapper;

    /** U041tentMapper */
    @Inject
    private U041tentMapper u041tentMapper;

    /** U042bmntMapper */
    @Inject
    private U042bmntMapper u042bmntMapper;

    /** context */
    @Inject
    private CCSystemContext context;

    /** mybatisDao */
    @Inject
    private MyBatisSqlMapDao mybatisDao;

    /** DATETIME_FORMAT_DATE */
    private static final String DATETIME_FORMAT_DATE = "yyyyMMdd";

    /** DATETIME_FORMAT_TIME */
    private static final String DATETIME_FORMAT_TIME = "HHmmss";

    /**
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    public Result delete(String kaisyaCd, String jigyobuCd, String tenCd, String uriDate) {
        Form<Urjp0040ResultForm> emptyForm = new Form<Urjp0040ResultForm>(Urjp0040ResultForm.class);
        Form<Urjp0040ResultForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        Urjp0040ResultForm resultForm = reqForm.get();

        return deleteTransaction(resultForm, kaisyaCd, jigyobuCd, tenCd, uriDate);
    }

    /**
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    public Result query(String kaisyaCd, String jigyobuCd, String tenCd, String uriDate) {

        M020ctlm m020ctlm = new M020ctlm();
        if (!checkCond(kaisyaCd, jigyobuCd, tenCd, uriDate, m020ctlm)) {
            ErrorInfo error = errRes.getErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.getErrors().add(0, error);
            return badRequest(Json.toJson(errRes));
        }
        String dataStr = m020ctlm.getData();

        boolean kakuteiFlag =
                CCStringUtil.cnvStrToInt(uriDate.substring(0, 6)) <= CCStringUtil.cnvStrToInt(dataStr.substring(38,
                        44));

        String unyobi = dataStr.substring(0, 8);

        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;

        Urjp0040ResultForm resultForm = new Urjp0040ResultForm();

        // 日別店別売上実績データ存在チェック
        U011tenjExample u011tenjExample = new U011tenjExample();
        u011tenjExample.createCriteria().andUriDateEqualTo(uriDate).andTenCdEqualTo(fullTenCd);

        List<U011tenj> u011tenjList = u011tenjMapper.selectByExample(u011tenjExample);

        if (u011tenjList.isEmpty()) {
            resultForm.setInsert(true);
        } else {
            resultForm.setInsert(false);
        }

        // 明細グリッドの表示データ取得処理
        Map<String, String> parameter = new HashMap<String, String>();
        parameter.put("hakkoDay", unyobi);
        parameter.put("jigyobuCd", jigyobuCd);
        parameter.put("tenCd", fullTenCd);
        parameter.put("uriDate", uriDate);

        List<U012bmnJm004tBumM003bmnmResult> dataList1 =
                mybatisDao.selectMany("selectU012bmnj01", parameter, U012bmnJm004tBumM003bmnmResult.class);

        if (dataList1.isEmpty()) {
            // C0105
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        long sumUriKin = 0;
        long sumTax = 0;
        long sumUtiTax = 0;


        List<Urjp0040Dto> bmnBaiyoArea = new ArrayList<Urjp0040Dto>();
        for (int i = 0; i < dataList1.size(); i++) {
            Urjp0040Dto record = new Urjp0040Dto();
            BeanUtils.copyProperties(dataList1.get(i), record);

            bmnBaiyoArea.add(record);
            sumUriKin += dataList1.get(i).getUriKin();
            sumTax += dataList1.get(i).getTax();
            sumUtiTax += dataList1.get(i).getUtiTax();
        }

        resultForm.setBmnBaiyoArea(bmnBaiyoArea);
        resultForm.setUriKin(sumUriKin);
        resultForm.setTax(sumTax);
        resultForm.setUtiTax(sumUtiTax);

        HashMap<String, String> hashParameter = new HashMap<String, String>();
        hashParameter.put("tenCode", fullTenCd);
        hashParameter.put("uriDate", uriDate);

        List<U011tenjResult> dataList2 =
                mybatisDao.selectMany("selectU011tenj04", hashParameter, U011tenjResult.class);

        ArrayList<Urjp0040Grid2Dto> grid2Area = new ArrayList<Urjp0040Grid2Dto>();

        for (int i = 0; i < 14; i++) {
            grid2Area.add(new Urjp0040Grid2Dto());
        }

        if (dataList2.isEmpty()) {
            resultForm.setTenKyakSu(0);
            resultForm.setWeather("");

            grid2Area.get(0).setKingaku(0L);
            grid2Area.get(1).setKingaku(0L);
            grid2Area.get(3).setKingaku(0L);
            grid2Area.get(4).setKingaku(0L);
            grid2Area.get(5).setKingaku(0L);
            grid2Area.get(6).setKingaku(0L);
            grid2Area.get(7).setKingaku(0L);
            grid2Area.get(8).setKingaku(0L);
            grid2Area.get(9).setKingaku(0L);
            grid2Area.get(10).setKingaku(0L);
            grid2Area.get(11).setKingaku(0L);

            grid2Area.get(3).setMaiSu(0);
            grid2Area.get(4).setMaiSu(0);
            grid2Area.get(5).setMaiSu(0);
            grid2Area.get(6).setMaiSu(0);
            grid2Area.get(7).setMaiSu(0);
            grid2Area.get(8).setMaiSu(0);
            grid2Area.get(9).setMaiSu(0);
            grid2Area.get(10).setMaiSu(0);
        } else {
            U011tenjResult u011tenjResult = dataList2.get(0);
            resultForm.setTenKyakSu(u011tenjResult.getKyaksu());
            resultForm.setWeather(CCStringUtil.trimBoth(u011tenjResult.getWeather()));

            grid2Area.get(0).setKingaku(u011tenjResult.getUriKin());
            grid2Area.get(1).setKingaku(u011tenjResult.getGnkUriKin());
            grid2Area.get(3).setKingaku(u011tenjResult.getCreditUtiwake06());
            grid2Area.get(4).setKingaku(u011tenjResult.getCreditUtiwake05());
            grid2Area.get(5).setKingaku(u011tenjResult.getCreditUtiwake09());
            grid2Area.get(6).setKingaku(u011tenjResult.getCreditUtiwake02());
            grid2Area.get(7).setKingaku(u011tenjResult.getCreditUtiwake01());
            grid2Area.get(8).setKingaku(u011tenjResult.getCreditUtiwake07());
            grid2Area.get(9).setKingaku(u011tenjResult.getCreditUtiwake08());
            grid2Area.get(10).setKingaku(u011tenjResult.getCreditUtiwake14());
            grid2Area.get(11).setKingaku(u011tenjResult.getTax());

            grid2Area.get(3).setMaiSu(u011tenjResult.getCreditUtiwakeSu06());
            grid2Area.get(4).setMaiSu(u011tenjResult.getCreditUtiwakeSu05());
            grid2Area.get(5).setMaiSu(u011tenjResult.getCreditUtiwakeSu09());
            grid2Area.get(6).setMaiSu(u011tenjResult.getCreditUtiwakeSu02());
            grid2Area.get(7).setMaiSu(u011tenjResult.getCreditUtiwakeSu01());
            grid2Area.get(8).setMaiSu(u011tenjResult.getCreditUtiwakeSu07());
            grid2Area.get(9).setMaiSu(u011tenjResult.getCreditUtiwakeSu08());
            grid2Area.get(10).setMaiSu(u011tenjResult.getCreditUtiwakeSu14());
        }

        resultForm.setGrid2Area(grid2Area);
        resultForm.setKakuteiFlag(kakuteiFlag);

        return ok(Json.toJson(resultForm));
    }

    /**
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    public Result save(String kaisyaCd, String jigyobuCd, String tenCd, String uriDate) {
        Form<Urjp0040ResultForm> emptyForm = new Form<Urjp0040ResultForm>(Urjp0040ResultForm.class);
        Form<Urjp0040ResultForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        Urjp0040ResultForm resultForm = reqForm.get();

        return saveTransaction(resultForm, kaisyaCd, jigyobuCd, tenCd, uriDate);
    }

    /**
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    public Result update(String kaisyaCd, String jigyobuCd, String tenCd, String uriDate) {
        Form<Urjp0040ResultForm> emptyForm = new Form<Urjp0040ResultForm>(Urjp0040ResultForm.class);
        Form<Urjp0040ResultForm> reqForm = emptyForm.bindFromRequest();

        if (reqForm.hasErrors()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errRes.addErrorInfo(reqForm.errors());
            return badRequest(Json.toJson(errRes));
        }

        Urjp0040ResultForm resultForm = reqForm.get();

        return updateTransaction(resultForm, kaisyaCd, jigyobuCd, tenCd, uriDate);
    }

    /**
     * @param resultForm resultForm
     * @param errResponse errResponse
     * @return boolean
     */
    private boolean checkEmptyForm(Urjp0040ResultForm resultForm, ErrorResponse errResponse) {

        boolean newRecord = false;
        // 売上月次確定以前のデータは更新しない
        if (!resultForm.isKakuteiFlag()) {
            List<Urjp0040Dto> bmnGrid = resultForm.getBmnBaiyoArea();
            for (int i = 0; i < bmnGrid.size(); i++) {
                long uriKin = bmnGrid.get(i).getUriKin();
                int kyakusu = bmnGrid.get(i).getKyaksu();
                int uriSu = bmnGrid.get(i).getUriSu();
                long tax = bmnGrid.get(i).getTax();
                long utiTax = bmnGrid.get(i).getUtiTax();

                if (uriKin != 0 || tax != 0 || utiTax != 0 || kyakusu != 0 || uriSu != 0) {
                    newRecord = true;
                    break;
                }
            }
        }

        if ("0".equals(resultForm.getTenKyakSu()) && "".equals(CCStringUtil.trimBoth(resultForm.getWeather()))
                && "0".equals(resultForm.getGrid2Area().get(0).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(1).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(3).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(3).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(4).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(4).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(5).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(5).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(6).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(6).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(7).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(7).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(8).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(8).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(9).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(9).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(10).getKingaku())
                && "0".equals(resultForm.getGrid2Area().get(10).getMaiSu())
                && "0".equals(resultForm.getGrid2Area().get(11).getKingaku()) && !newRecord) {
            errResponse.addErrorInfo(CCMessageConst.MSG_KEY_ONE_SPECIFICATION_DOES_NOT_ENTER);
            return false;
        }
        return true;
    }

    /**
     * @param resultForm resultForm
     * @param errResponse errResponse
     * @return boolean
     */
    private boolean checkResultForm(Urjp0040ResultForm resultForm, ErrorResponse errResponse) {
        String weather = resultForm.getWeather();

        if (!CCKubunConst.KBN_VAL_U_TENKYU_KBN_SUNNY.equals(weather)
                && !CCKubunConst.KBN_VAL_U_TENKYU_KBN_CLOUD.equals(weather)
                && !CCKubunConst.KBN_VAL_U_TENKYU_KBN_RAIN.equals(weather)
                && !CCKubunConst.KBN_VAL_U_TENKYU_KBN_SNOW.equals(weather)
                && !CCKubunConst.KBN_VAL_U_TENKYU_KBN_TYPHOON.equals(weather)
                && !CCKubunConst.KBN_VAL_U_TENKYU_KBN_OTHER.equals(weather)
                && !"".equals(CCStringUtil.trimBoth(weather))) {
            // C0254
            errResponse.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errResponse.addErrorInfo("weather", CCMessageConst.MSG_KEY_INVALID_INPUT_0106);
            return false;
        }

        if (resultForm.getUriKin().compareTo(resultForm.getGrid2Area().get(0).getKingaku()) != 0) {
            // U1036
            errResponse.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errResponse.addErrorInfo("kingaku0", CCMessageConst.MSG_KEY_SALE_NOT_MATCH);
            return false;
        }
        
        if (resultForm.getGrid2Area().get(11).getKingaku().compareTo(resultForm.getTax() + resultForm.getUtiTax()) != 0) {
            // U1036
            errResponse.addErrorInfo(CCMessageConst.MSG_KEY_VALIDATION_ERROR);
            errResponse.addErrorInfo("kingaku11", CCMessageConst.MSG_KEY_BMNURI_TOTAL_TAX_NOT_EQUAL);
            return false;
        }
        return true;
    }

    /**
     * @param resultForm resultForm
     * @return Result
     */
    /**
     * @param resultForm resultForm
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    @Transactional
    private Result deleteTransaction(Urjp0040ResultForm resultForm, String kaisyaCd, String jigyobuCd, String tenCd,
            String uriDate) {
        DateTime dt = new DateTime();

        if (resultForm.isKakuteiFlag()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_CAN_NOT_DELETE);
            return badRequest(Json.toJson(errRes));
        }

        setGridDefaultValue(resultForm);

        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;

        // 日別店別売上実績のデータ取得
        U011tenjExample u011tenjExample = new U011tenjExample();

        u011tenjExample.createCriteria().andTenCdEqualTo(fullTenCd).andUriDateEqualTo(uriDate);

        List<U011tenj> u011tenjList = u011tenjMapper.selectByExample(u011tenjExample);

        // 日別店別売上実績Delete
        int count = u011tenjMapper.deleteByExample(u011tenjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
        }

        // 月別店別売上実績のデータ取得
        U021tenjExample u021tenjExample = new U021tenjExample();
        u021tenjExample.createCriteria().andTenCdEqualTo(fullTenCd).andYyyymmEqualTo(uriDate.substring(0, 6));

        List<U021tenj> u021tenjList = u021tenjMapper.selectByExample(u021tenjExample);

        // ３．データベースへデータ更新処理
        U011tenj u011tenj = u011tenjList.get(0);
        U021tenj u021tenj = u021tenjList.get(0);
        Long uriage = u021tenj.getUriKin() - u011tenj.getUriKin();
        Integer kyakusu = u021tenj.getKyaksu() - u011tenj.getKyaksu();
        Integer tensu = u021tenj.getUriSu() - u011tenj.getUriTensu();
        Long tax = u021tenj.getTax() - u021tenj.getTax();
        Long tukiGiftKenKin = u021tenj.getGftKenKin() - u011tenj.getGftKenKin();
        Integer tukiCreditSu = u021tenj.getCrdSu() - u011tenj.getCrdSu();
        Long tukiCreditKin = u021tenj.getCrdKin() - u011tenj.getCrdKin();
        Long tukiJihankiKin = u021tenj.getSaiKei() - u011tenj.getSaiKei();
        Integer tukiOrikoSu = u021tenj.getCreditUtiwakeSu01() - u011tenj.getCreditUtiwakeSu01();
        Long tukiOrikoKin = u021tenj.getCreditUtiwake01() - u011tenj.getCreditUtiwake01();
        Integer tukiUCVISASu = u021tenj.getCreditUtiwakeSu02() - u011tenj.getCreditUtiwakeSu02();
        Long tukiUCVISAKin = u021tenj.getCreditUtiwake02() - u011tenj.getCreditUtiwake02();

        U021tenj toBeUpdatedU021tenj = new U021tenj();
        toBeUpdatedU021tenj.setUriKin(uriage);
        toBeUpdatedU021tenj.setKyaksu(kyakusu);
        toBeUpdatedU021tenj.setUriSu(tensu);
        toBeUpdatedU021tenj.setTenKyaku(kyakusu);
        toBeUpdatedU021tenj.setTax(tax);

        toBeUpdatedU021tenj.setGftKenKin(tukiGiftKenKin);
        toBeUpdatedU021tenj.setCrdSu(tukiCreditSu);
        toBeUpdatedU021tenj.setCrdKin(tukiCreditKin);
        toBeUpdatedU021tenj.setSaiKei(tukiJihankiKin);
        toBeUpdatedU021tenj.setCreditUtiwakeSu01(tukiOrikoSu);
        toBeUpdatedU021tenj.setCreditUtiwake01(tukiOrikoKin);
        toBeUpdatedU021tenj.setCreditUtiwakeSu02(tukiUCVISASu);
        toBeUpdatedU021tenj.setCreditUtiwake02(tukiUCVISAKin);

        toBeUpdatedU021tenj.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU021tenj.setCmnTermId(context.getTermId());

        toBeUpdatedU021tenj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU021tenj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU021tenj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        count = u021tenjMapper.updateByExampleSelective(toBeUpdatedU021tenj, u021tenjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        // 店別売上修正トランUpdate
        U041tentExample u041tentExample = new U041tentExample();
        u041tentExample.createCriteria().andUriDateEqualTo(uriDate).andTenCdEqualTo(fullTenCd);

        List<U041tent> u041tentList = u041tentMapper.selectByExample(u041tentExample);

        if (!u041tentList.isEmpty()) {
            U041tent u041tent = new U041tent();

            u041tent.setUriKin(0L);
            u041tent.setKyaksu(0);
            u041tent.setUriSu(0);
            u041tent.setTenKyaku(0);
            u041tent.setTax(0L);
            u041tent.setGftKenSu(0);
            u041tent.setGftKenKin(0L);
            u041tent.setShnKenSu(0);
            u041tent.setShnKenKin(0L);
            u041tent.setCrdSu(0);
            u041tent.setCrdKin(0L);
            u041tent.setKaiKenSu(0);
            u041tent.setKaiKenKin(0L);
            u041tent.setEnvStampSu(0);
            u041tent.setEnvStampKin(0L);
            u041tent.setUrikakeSu(0);
            u041tent.setUrikakeKin(0L);
            u041tent.setKafusoku(0L);
            u041tent.setGnkNyukin(0L);
            u041tent.setSaiKei(0L);
            u041tent.setJihankiKin(0L);
            u041tent.setKogutiGenkin(0L);
            u041tent.setTenyoKin(0L);
            u041tent.setTenant(0L);
            u041tent.setAzukari02(0L);
            u041tent.setAzukari03(0L);
            u041tent.setCreditUtiwakeSu01(0);
            u041tent.setCreditUtiwake01(0L);
            u041tent.setCreditUtiwakeSu02(0);
            u041tent.setCreditUtiwake02(0L);
            u041tent.setCreditUtiwakeSu03(0);
            u041tent.setCreditUtiwake03(0L);
            u041tent.setCreditUtiwakeSu04(0);
            u041tent.setCreditUtiwake04(0L);
            u041tent.setCreditUtiwakeSu05(0);
            u041tent.setCreditUtiwake05(0L);
            u041tent.setCreditUtiwakeSu10(0);
            u041tent.setCreditUtiwake10(0L);
            u041tent.setRomuNinzu01((short) 0);
            u041tent.setRomuJikan01(0);
            u041tent.setRomuNinzu03((short) 0);
            u041tent.setRomuJikan03(0);
            u041tent.setRomuNinzu04((short) 0);
            u041tent.setRomuJikan04(0);
            u041tent.setSyoriFlg("0");

            count = u041tentMapper.updateByExampleSelective(u041tent, u041tentExample);
            if (count == 0) {
                throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
            }
        }

        for (int i = 0; i < resultForm.getBmnBaiyoArea().size(); i++) {
            // 月別店別部門別売上実績のデータ取得
            Urjp0040Dto urjp0040Dto = resultForm.getBmnBaiyoArea().get(i);
            String bmnCd = jigyobuCd + urjp0040Dto.getBmnCd();

            HashMap<String, String> parameter = new HashMap<String, String>();
            parameter.put("tenCd", fullTenCd);
            parameter.put("bmnCd", bmnCd);
            parameter.put("yyyymm", uriDate.substring(0, 6));

            List<U022bmnjResult> u022bmnjResultList =
                    mybatisDao.selectMany("selectU022bmnj02", parameter, U022bmnjResult.class);

            if (u022bmnjResultList.isEmpty()) {
                continue;
            }

            // 売上高
            long uriKin2 = u022bmnjResultList.get(0).getUriKin() - urjp0040Dto.getUriKin();
            long tax2 = u022bmnjResultList.get(0).getTax() - urjp0040Dto.getTax();
            long utiTax2 = u022bmnjResultList.get(0).getUtiTax() - urjp0040Dto.getUtiTax();
            int kyakusu2 = u022bmnjResultList.get(0).getKyaksu() - urjp0040Dto.getKyaksu();
            int uriSu2 = u022bmnjResultList.get(0).getUriSu() - urjp0040Dto.getUriSu();

            updateU022(resultForm, urjp0040Dto, uriKin2, tax2, utiTax2, uriSu2, kyakusu2, kaisyaCd, jigyobuCd,
                    fullTenCd, uriDate);

            // 日別店別部門別売上実績Delete
            parameter.clear();
            parameter.put("tenCd", fullTenCd);
            parameter.put("bmnCd", bmnCd);
            parameter.put("uriDate", uriDate);

            List<U012bmnjResult> u012bmnjResultList =
                    mybatisDao.selectMany("selectU012bmnj03", parameter, U012bmnjResult.class);

            if (!u012bmnjResultList.isEmpty()) {
                U012bmnjExample u012bmnjExample = new U012bmnjExample();
                u012bmnjExample.createCriteria().andTenCdEqualTo(fullTenCd).andBmnCdEqualTo(bmnCd)
                        .andUriDateEqualTo(uriDate);

                count = u012bmnjMapper.deleteByExample(u012bmnjExample);
                if (count == 0) {
                    throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_DELETE_NO_RECORD));
                }
            }

            // 部門別売上修正トランUpdate
            U042bmntExample u042bmntExample = new U042bmntExample();
            u042bmntExample.createCriteria().andTenCdEqualTo(fullTenCd).andBmnCdEqualTo(bmnCd)
                    .andUriDateEqualTo(uriDate);

            List<U042bmnt> u042bmntList = u042bmntMapper.selectByExample(u042bmntExample);

            if (!u042bmntList.isEmpty()) {
                updateU042(u042bmntExample, 0, 0, 0);
            }
        }

        return ok();
    }

    /**
     * @param resultForm resultForm
     * @param nenDo nenDo
     * @param weekNo weekNo
     * @param weekDay weekDay
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void insertU011(Urjp0040ResultForm resultForm, String nenDo, String weekNo, String weekDay,
            String kaisyaCd, String jigyobuCd, String fullTenCd, String uriDate) {
        // １．データベースへデータ登録処理
        DateTime dt = new DateTime();

        U011tenj u011tenj = new U011tenj();
        u011tenj.setTenCd(fullTenCd);
        u011tenj.setUriDate(uriDate);
        u011tenj.setNendo(nenDo);
        u011tenj.setWeekNo(weekNo);
        u011tenj.setWeekDay(weekDay);
        u011tenj.setWeather(resultForm.getWeather());

        u011tenj.setUriKin(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(0).getKingaku() : 0);
        u011tenj.setKyaksu(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());
        u011tenj.setTenKyaku(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());
        u011tenj.setTax(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(11).getKingaku() : 0);
        
        u011tenj.setGnkUriKin(resultForm.getGrid2Area().get(1).getKingaku());
        u011tenj.setCreditUtiwake06(resultForm.getGrid2Area().get(3).getKingaku());
        u011tenj.setCreditUtiwake05(resultForm.getGrid2Area().get(4).getKingaku());
        u011tenj.setCreditUtiwake09(resultForm.getGrid2Area().get(5).getKingaku());
        u011tenj.setCreditUtiwake02(resultForm.getGrid2Area().get(6).getKingaku());
        u011tenj.setCreditUtiwake01(resultForm.getGrid2Area().get(7).getKingaku());
        u011tenj.setCreditUtiwake07(resultForm.getGrid2Area().get(8).getKingaku());
        u011tenj.setCreditUtiwake08(resultForm.getGrid2Area().get(9).getKingaku());
        u011tenj.setCreditUtiwake14(resultForm.getGrid2Area().get(10).getKingaku());
        u011tenj.setCreditUtiwakeSu06(resultForm.getGrid2Area().get(3).getMaiSu());
        u011tenj.setCreditUtiwakeSu05(resultForm.getGrid2Area().get(4).getMaiSu());
        u011tenj.setCreditUtiwakeSu09(resultForm.getGrid2Area().get(5).getMaiSu());
        u011tenj.setCreditUtiwakeSu02(resultForm.getGrid2Area().get(6).getMaiSu());
        u011tenj.setCreditUtiwakeSu01(resultForm.getGrid2Area().get(7).getMaiSu());
        u011tenj.setCreditUtiwakeSu07(resultForm.getGrid2Area().get(8).getMaiSu());
        u011tenj.setCreditUtiwakeSu08(resultForm.getGrid2Area().get(9).getMaiSu());
        u011tenj.setCreditUtiwakeSu14(resultForm.getGrid2Area().get(10).getMaiSu());
        u011tenj.setNyukinDate("0");

        u011tenj.setCmnTantoCd(context.getTantoCode());
        u011tenj.setCmnTermId(context.getTermId());
        u011tenj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        u011tenj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        u011tenj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u011tenjMapper.insertSelective(u011tenj);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    // ６．データベースへデータ登録処理

    /**
     * @param resultForm resultForm
     * @param bmnGridRow bmnGridRow
     * @param nenDo nenDo
     * @param weekNo weekNo
     * @param weekDay weekDay
     * @param uriKin uriKin
     * @param tax tax
     * @param utiTax utiTax
     * @param uriSu uriSu
     * @param kyakusu kyakusu
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void insertU012(Urjp0040ResultForm resultForm, Urjp0040Dto bmnGridRow, String nenDo, String weekNo,
            String weekDay, long uriKin, long tax, long utiTax, int uriSu, int kyakusu, String kaisyaCd, String jigyobuCd,
            String fullTenCd, String uriDate) {
        DateTime dt = new DateTime();

        U012bmnj u012bmnj = new U012bmnj();

        u012bmnj.setTenCd(fullTenCd);
        u012bmnj.setBmnCd(jigyobuCd + bmnGridRow.getBmnCd());
        u012bmnj.setUriDate(uriDate);
        u012bmnj.setNendo(nenDo);
        u012bmnj.setWeekNo(weekNo);
        u012bmnj.setWeekDay(weekDay);
        u012bmnj.setUriSu(uriSu);
        u012bmnj.setTax(tax);
        u012bmnj.setUtiTax(utiTax);
        u012bmnj.setUriKin(uriKin);
        u012bmnj.setKyaksu(kyakusu);

        u012bmnj.setCmnTantoCd(context.getTantoCode());
        u012bmnj.setCmnTermId(context.getTermId());

        u012bmnj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        u012bmnj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        u012bmnj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u012bmnjMapper.insertSelective(u012bmnj);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    // ２．データベースへデータ登録処理
    /**
     * @param resultForm resultForm
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void insertU021(Urjp0040ResultForm resultForm, String kaisyaCd, String jigyobuCd, String fullTenCd,
            String uriDate) {
        DateTime dt = new DateTime();

        U021tenj u021tenj = new U021tenj();

        u021tenj.setTenCd(fullTenCd);
        u021tenj.setYyyymm(uriDate.substring(0, 6));
        u021tenj.setUriKin(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(0).getKingaku() : 0);
        u021tenj.setKyaksu(resultForm.getTenKyakSu() == null ? 0 : (resultForm.getTenKyakSu()));
        u021tenj.setTenKyaku(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());
        u021tenj.setTax(!resultForm.isKakuteiFlag() ? (resultForm.getGrid2Area().get(8).getKingaku()) : 0);

        u021tenj.setGnkUriKin(resultForm.getGrid2Area().get(1).getKingaku());
        u021tenj.setCreditUtiwake06(resultForm.getGrid2Area().get(3).getKingaku());
        u021tenj.setCreditUtiwake05(resultForm.getGrid2Area().get(4).getKingaku());
        u021tenj.setCreditUtiwake09(resultForm.getGrid2Area().get(5).getKingaku());
        u021tenj.setCreditUtiwake02(resultForm.getGrid2Area().get(6).getKingaku());
        u021tenj.setCreditUtiwake01(resultForm.getGrid2Area().get(7).getKingaku());
        u021tenj.setCreditUtiwake07(resultForm.getGrid2Area().get(8).getKingaku());
        u021tenj.setCreditUtiwake08(resultForm.getGrid2Area().get(9).getKingaku());
        u021tenj.setCreditUtiwake14(resultForm.getGrid2Area().get(10).getKingaku());
        u021tenj.setCreditUtiwakeSu06(resultForm.getGrid2Area().get(3).getMaiSu());
        u021tenj.setCreditUtiwakeSu05(resultForm.getGrid2Area().get(4).getMaiSu());
        u021tenj.setCreditUtiwakeSu09(resultForm.getGrid2Area().get(5).getMaiSu());
        u021tenj.setCreditUtiwakeSu02(resultForm.getGrid2Area().get(6).getMaiSu());
        u021tenj.setCreditUtiwakeSu01(resultForm.getGrid2Area().get(7).getMaiSu());
        u021tenj.setCreditUtiwakeSu07(resultForm.getGrid2Area().get(8).getMaiSu());
        u021tenj.setCreditUtiwakeSu08(resultForm.getGrid2Area().get(9).getMaiSu());
        u021tenj.setCreditUtiwakeSu14(resultForm.getGrid2Area().get(10).getMaiSu());

        u021tenj.setNyukinDate("0");
        u021tenj.setCmnTantoCd(context.getTantoCode());
        u021tenj.setCmnTermId(context.getTermId());

        u021tenj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        u021tenj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        u021tenj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u021tenjMapper.insertSelective(u021tenj);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    // ７．データベースへデータ登録処理
    /**
     * @param resultForm resultForm
     * @param bmnGridRow bmnGridRow
     * @param uriKin uriKin
     * @param tax tax
     * @param utiTax utiTax
     * @param uriSu uriSu
     * @param kyakusu kyakusu
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void insertU022(Urjp0040ResultForm resultForm, Urjp0040Dto bmnGridRow, long uriKin, long tax, long utiTax,
            int uriSu, int kyakusu, String kaisyaCd, String jigyobuCd, String fullTenCd, String uriDate) {
        DateTime dt = new DateTime();

        U022bmnj u022bmnj = new U022bmnj();

        u022bmnj.setTenCd(fullTenCd);
        u022bmnj.setBmnCd(jigyobuCd + bmnGridRow.getBmnCd());
        u022bmnj.setYyyymm(uriDate.substring(0, 6));
        u022bmnj.setUriSu(uriSu);
        u022bmnj.setUriKin(uriKin);
        u022bmnj.setTax(tax);
        u022bmnj.setUtiTax(utiTax);
        u022bmnj.setKyaksu(kyakusu);

        u022bmnj.setCmnTantoCd(context.getTantoCode());
        u022bmnj.setCmnTermId(context.getTermId());

        u022bmnj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        u022bmnj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        u022bmnj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u022bmnjMapper.insertSelective(u022bmnj);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    // ４．データベースへデータ登録処理
    /**
     * @param resultForm resultForm
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void insertU041(Urjp0040ResultForm resultForm, String kaisyaCd, String jigyobuCd, String fullTenCd,
            String uriDate) {
        DateTime dt = new DateTime();

        U041tent u041tent = new U041tent();

        u041tent.setTenCd(fullTenCd);
        u041tent.setUriDate(uriDate);
        u041tent.setWeather(resultForm.getWeather());
        u041tent.setUriKin(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(0).getKingaku() : 0);
        u041tent.setKyaksu(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());
        u041tent.setTenKyaku(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());
        u041tent.setTax(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(11).getKingaku() : 0);
        u041tent.setGnkUriKin(resultForm.getGrid2Area().get(1).getKingaku());
        u041tent.setCreditUtiwake06(resultForm.getGrid2Area().get(3).getKingaku());
        u041tent.setCreditUtiwake05(resultForm.getGrid2Area().get(4).getKingaku());
        u041tent.setCreditUtiwake09(resultForm.getGrid2Area().get(5).getKingaku());
        u041tent.setCreditUtiwake02(resultForm.getGrid2Area().get(6).getKingaku());
        u041tent.setCreditUtiwake01(resultForm.getGrid2Area().get(7).getKingaku());
        u041tent.setCreditUtiwake07(resultForm.getGrid2Area().get(8).getKingaku());
        u041tent.setCreditUtiwake08(resultForm.getGrid2Area().get(9).getKingaku());
        u041tent.setCreditUtiwake14(resultForm.getGrid2Area().get(10).getKingaku());
        
        u041tent.setCreditUtiwakeSu06(resultForm.getGrid2Area().get(3).getMaiSu());
        u041tent.setCreditUtiwakeSu05(resultForm.getGrid2Area().get(4).getMaiSu());
        u041tent.setCreditUtiwakeSu09(resultForm.getGrid2Area().get(5).getMaiSu());
        u041tent.setCreditUtiwakeSu02(resultForm.getGrid2Area().get(6).getMaiSu());
        u041tent.setCreditUtiwakeSu01(resultForm.getGrid2Area().get(7).getMaiSu());
        u041tent.setCreditUtiwakeSu07(resultForm.getGrid2Area().get(8).getMaiSu());
        u041tent.setCreditUtiwakeSu08(resultForm.getGrid2Area().get(9).getMaiSu());
        u041tent.setCreditUtiwakeSu14(resultForm.getGrid2Area().get(10).getMaiSu());
        u041tent.setNyukinDate("0");
        u041tent.setSyoriFlg("0");

        u041tent.setCmnTantoCd(context.getTantoCode());
        u041tent.setCmnTermId(context.getTermId());

        u041tent.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        u041tent.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        u041tent.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u041tentMapper.insertSelective(u041tent);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    // ９．データベースへデータ登録処理
    /**
     * @param resultForm resultForm
     * @param bmnGridRow bmnGridRow
     * @param uriKin uriKin
     * @param uriSu uriSu
     * @param kyakusu kyakusu
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void insertU042(Urjp0040ResultForm resultForm, Urjp0040Dto bmnGridRow, long uriKin, int uriSu, int kyakusu,
            String kaisyaCd, String jigyobuCd, String fullTenCd, String uriDate) {
        DateTime dt = new DateTime();

        U042bmnt u042bmnt = new U042bmnt();

        u042bmnt.setTenCd(fullTenCd);
        u042bmnt.setBmnCd(jigyobuCd + bmnGridRow.getBmnCd());
        u042bmnt.setUriDate(uriDate);
        u042bmnt.setUriSu(uriSu);
        u042bmnt.setUriKin(uriKin);
        u042bmnt.setSyoriFlg("0");

        u042bmnt.setCmnTantoCd(context.getTantoCode());
        u042bmnt.setCmnTermId(context.getTermId());

        u042bmnt.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        u042bmnt.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        u042bmnt.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u042bmntMapper.insertSelective(u042bmnt);
        if (count <= 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_INSERT_NO_RECORD));
        }
    }

    /**
     * @param resultForm resultForm
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    @Transactional
    private Result saveTransaction(Urjp0040ResultForm resultForm, String kaisyaCd, String jigyobuCd, String tenCd,
            String uriDate) {
        if (!checkResultForm(resultForm, errRes)) {
            return badRequest(Json.toJson(errRes));
        }

        setGridDefaultValue(resultForm);

        if (!checkEmptyForm(resultForm, errRes)) {
            return badRequest(Json.toJson(errRes));
        }

        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;

        // 年月日マスタのデータ取得
        M019ymdmExample m019ymdmExample = new M019ymdmExample();
        m019ymdmExample.createCriteria().andCalDateEqualTo(uriDate).andTenCdEqualTo(fullTenCd);

        List<M019ymdm> m019ymdmList = m019ymdmMapper.selectByExample(m019ymdmExample);

        if (m019ymdmList.isEmpty()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAILED_TO_GET_DATE_MASTER);
            return badRequest(Json.toJson(errRes));
        }

        M019ymdm m019ymdm = m019ymdmList.get(0);

        String nenDo = m019ymdm.getNendo() == null ? " " : m019ymdm.getNendo();
        String weekNo = m019ymdm.getWeekNo() == null ? " " : m019ymdm.getWeekNo();
        String weekDay = m019ymdm.getWeekDay() == null ? " " : m019ymdm.getWeekDay();

        // 日別店別売上実績の登録
        insertU011(resultForm, nenDo, weekNo, weekDay, kaisyaCd, jigyobuCd, fullTenCd, uriDate);

        U021tenjExample u021tenjExample = new U021tenjExample();
        u021tenjExample.createCriteria().andYyyymmEqualTo(uriDate.substring(0, 6)).andTenCdEqualTo(fullTenCd);

        List<U021tenj> u021tenjList = u021tenjMapper.selectByExample(u021tenjExample);

        // 月別店別売上実績のデータ取得
        if (u021tenjList.isEmpty()) {
            // 月別店別売上実績のデータ取得でデータが存在しない場合
            insertU021(resultForm, kaisyaCd, jigyobuCd, fullTenCd, uriDate);
        } else {
            // 月別店別売上実績のデータ取得でデータが存在する場合
            U021tenj existedU021tenj = u021tenjList.get(0);
            updateU021(resultForm, existedU021tenj, u021tenjExample);
        }

        // U041tentMapperのselectByExampleを使用して、店別売上修正トランのデータ取得
        U041tentExample u041tenjExample = new U041tentExample();
        u041tenjExample.createCriteria().andUriDateEqualTo(uriDate).andTenCdEqualTo(fullTenCd);

        List<U041tent> u041tenjList = u041tentMapper.selectByExample(u041tenjExample);

        if (u041tenjList.isEmpty()) {
            // 店別売上修正トランInsert
            insertU041(resultForm, kaisyaCd, jigyobuCd, fullTenCd, uriDate);
        } else {
            // 店別売上修正トランUpDate
            updateU041(resultForm, u041tenjExample);
        }

        // 売上月次確定以前のデータは更新しない
        if (!resultForm.isKakuteiFlag()) {
            List<Urjp0040Dto> bmnGrid = resultForm.getBmnBaiyoArea();
            for (int i = 0; i < bmnGrid.size(); i++) {
                long uriKin = bmnGrid.get(i).getUriKin();
                long tax = bmnGrid.get(i).getTax();
                long utiTax = bmnGrid.get(i).getUtiTax();
                int kyakusu = bmnGrid.get(i).getKyaksu();
                int uriSu = bmnGrid.get(i).getUriSu();

                String bmnCd = jigyobuCd + bmnGrid.get(i).getBmnCd();

                if (uriKin != 0 || kyakusu != 0 || uriSu != 0 || tax != 0 || utiTax != 0) {
                    // 日別店別部門別売上実績Insert
                    insertU012(resultForm, bmnGrid.get(i), nenDo, weekNo, weekDay, uriKin, tax, utiTax, uriSu, kyakusu,
                            kaisyaCd, jigyobuCd, fullTenCd, uriDate);

                    // Urjp0040MapperのselectU022bmnj02を使用して、月別店別部門別売上実績のデータ取得処理
                    HashMap<String, String> hashParameter = new HashMap<String, String>();
                    hashParameter.put("tenCd", fullTenCd);
                    hashParameter.put("bmnCd", bmnCd);
                    hashParameter.put("yyyymm", uriDate.substring(0, 6));

                    List<U022bmnjResult> u022bmnjResultList =
                            mybatisDao.selectMany("selectU022bmnj02", hashParameter, U022bmnjResult.class);

                    if (u022bmnjResultList.isEmpty()) {
                        // ７．データベースへデータ登録処理
                        insertU022(resultForm, bmnGrid.get(i), uriKin, tax, utiTax, uriSu, kyakusu, kaisyaCd, jigyobuCd,
                                fullTenCd, uriDate);
                    } else {
                        // ８．データベースへデータ更新処理
                        long uriKin2 = u022bmnjResultList.get(0).getUriKin() + uriKin;
                        long tax2 = u022bmnjResultList.get(0).getTax() + tax;
                        long utiTax2 = u022bmnjResultList.get(0).getUtiTax() + utiTax;
                        int kyakusu2 = u022bmnjResultList.get(0).getKyaksu() + kyakusu;
                        int uriSu2 = u022bmnjResultList.get(0).getUriSu() + uriSu;

                        updateU022(resultForm, bmnGrid.get(i), uriKin2, tax2, utiTax2, uriSu2, kyakusu2, kaisyaCd,
                                jigyobuCd, fullTenCd, uriDate);
                    }

                    // U042bmntMapperのselectByExampleを使用して、部門別売上修正トランのデータ有無取得処理
                    U042bmntExample u042bmntExample = new U042bmntExample();
                    u042bmntExample.createCriteria().andTenCdEqualTo(fullTenCd).andBmnCdEqualTo(bmnCd)
                            .andUriDateEqualTo(uriDate);

                    List<U042bmnt> u042bmntList = u042bmntMapper.selectByExample(u042bmntExample);

                    if (u042bmntList.isEmpty()) {
                        // ９．データベースへデータ登録処理
                        insertU042(resultForm, bmnGrid.get(i), uriKin, uriSu, kyakusu, kaisyaCd, jigyobuCd,
                                fullTenCd, uriDate);
                    } else {
                        updateU042(u042bmntExample, uriKin, uriSu, kyakusu);
                    }
                }
            }
        }

        return created();
    }

    /**
     * @param resultForm resultForm
     */
    private void setGridDefaultValue(Urjp0040ResultForm resultForm) {
        for (int i = 0; i < resultForm.getBmnBaiyoArea().size(); i++) {
            Urjp0040Dto dto = resultForm.getBmnBaiyoArea().get(i);
            if (dto.getUriKin() == null) {
                dto.setUriKin((long) 0);
            }
            
            if (dto.getTax() == null) {
                dto.setTax((long) 0);
            }

            if (dto.getUtiTax() == null) {
                dto.setUtiTax((long) 0);
            }


            if (dto.getKyaksu() == null) {
                dto.setKyaksu(0);
            }

            if (dto.getUriSu() == null) {
                dto.setUriSu(0);
            }
        }

        for (int i = 0; i < resultForm.getGrid2Area().size(); i++) {
            Urjp0040Grid2Dto dto = resultForm.getGrid2Area().get(i);
            if (dto.getKingaku() == null) {
                dto.setKingaku(0L);
            }

            if (dto.getMaiSu() == null) {
                dto.setMaiSu(0);
            }
        }

        if (resultForm.getTenKyakSu() == null) {
            resultForm.setTenKyakSu(0);
        }

        if (CCStringUtil.isEmpty(resultForm.getWeather())) {
            resultForm.setWeather("  ");
        }

    }

    /**
     * @param resultForm resultForm
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @return Result
     */
    @Transactional
    private Result updateTransaction(Urjp0040ResultForm resultForm, String kaisyaCd, String jigyobuCd, String tenCd,
            String uriDate) {
        if (!checkResultForm(resultForm, errRes)) {
            return badRequest(Json.toJson(errRes));
        }

        setGridDefaultValue(resultForm);

        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;

        // 年月日マスタのデータ取得
        M019ymdmExample m019ymdmExample = new M019ymdmExample();
        m019ymdmExample.createCriteria().andCalDateEqualTo(uriDate).andTenCdEqualTo(fullTenCd);

        List<M019ymdm> m019ymdmList = m019ymdmMapper.selectByExample(m019ymdmExample);

        if (m019ymdmList.isEmpty()) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAILED_TO_GET_DATE_MASTER);
            return badRequest(Json.toJson(errRes));
        }

        M019ymdm m019ymdm = m019ymdmList.get(0);

        String nenDo = m019ymdm.getNendo() == null ? " " : m019ymdm.getNendo();
        String weekNo = m019ymdm.getWeekNo() == null ? " " : m019ymdm.getWeekNo();
        String weekDay = m019ymdm.getWeekDay() == null ? " " : m019ymdm.getWeekDay();

        U011tenjExample u011tenjExample = new U011tenjExample();
        u011tenjExample.createCriteria().andTenCdEqualTo(fullTenCd).andUriDateEqualTo(uriDate);
        U011tenj u011tenj = u011tenjMapper.selectByExample(u011tenjExample).get(0);

        U021tenjExample u021tenjExample = new U021tenjExample();
        u021tenjExample.createCriteria().andTenCdEqualTo(fullTenCd).andYyyymmEqualTo(uriDate.substring(0, 6));
        U021tenj u021tenj = u021tenjMapper.selectByExample(u021tenjExample).get(0);

        updateU011AndU021(resultForm, u011tenj, u021tenj, u011tenjExample, u021tenjExample, nenDo, weekNo, weekDay);

        // U041tentMapperのselectByExampleを使用して、店別売上修正トランのデータ取得
        U041tentExample u041tenjExample = new U041tentExample();
        u041tenjExample.createCriteria().andUriDateEqualTo(uriDate).andTenCdEqualTo(fullTenCd);

        List<U041tent> u041tenjList = u041tentMapper.selectByExample(u041tenjExample);

        if (u041tenjList.isEmpty()) {
            // 店別売上修正トランInsert
            insertU041(resultForm, kaisyaCd, jigyobuCd, fullTenCd, uriDate);
        } else {
            // 店別売上修正トランUpDate
            updateU041(resultForm, u041tenjExample);
        }

        // 売上月次確定以前のデータは更新しない
        if (!resultForm.isKakuteiFlag()) {
            List<Urjp0040Dto> bmnGrid = resultForm.getBmnBaiyoArea();
            for (int i = 0; i < bmnGrid.size(); i++) {
                long uriKin = bmnGrid.get(i).getUriKin();
                long tax = bmnGrid.get(i).getTax();
                long utiTax = bmnGrid.get(i).getUtiTax();
                int kyakusu = bmnGrid.get(i).getKyaksu();
                int uriSu = bmnGrid.get(i).getUriSu();
                String bmnCd = jigyobuCd + bmnGrid.get(i).getBmnCd();

                // Urjp0040MapperのselectU012bmnj03を使用して、部門別売上実績の金額、客数、点数を取得
                HashMap<String, String> hashParameter = new HashMap<String, String>();
                hashParameter.put("tenCd", fullTenCd);
                hashParameter.put("bmnCd", bmnCd);
                hashParameter.put("uriDate", uriDate);

                List<U012bmnjResult> u012bmnjResultList =
                        mybatisDao.selectMany("selectU012bmnj03", hashParameter, U012bmnjResult.class);

                // Urjp0040MapperのselectU022bmnj02を使用して、月別店別部門別売上実績のデータ取得処理
                hashParameter.clear();
                hashParameter.put("tenCd", fullTenCd);
                hashParameter.put("bmnCd", bmnCd);
                hashParameter.put("yyyymm", uriDate.substring(0, 6));

                List<U022bmnjResult> u022bmnjResultList =
                        mybatisDao.selectMany("selectU022bmnj02", hashParameter, U022bmnjResult.class);

                if (u022bmnjResultList.isEmpty()) {
                    if (uriKin != 0 || kyakusu != 0 || uriSu != 0 || tax != 0 || utiTax != 0) {
                        // 日別店別部門別売上実績Insert
                        insertU012(resultForm, bmnGrid.get(i), nenDo, weekNo, weekDay, uriKin, tax, utiTax, uriSu,
                                kyakusu, kaisyaCd, jigyobuCd, fullTenCd, uriDate);

                        // 月別店別部門別売上実績Insert
                        insertU022(resultForm, bmnGrid.get(i), uriKin, tax, utiTax, uriSu, kyakusu, kaisyaCd, jigyobuCd,
                                fullTenCd, uriDate);

                        // 部門別売上修正トランInsert
                        insertU042(resultForm, bmnGrid.get(i), uriKin, uriSu, kyakusu, kaisyaCd, jigyobuCd,
                                fullTenCd, uriDate);
                    }
                } else {
                    if (u012bmnjResultList.isEmpty()) {
                        if (uriKin != 0 || kyakusu != 0 || uriSu != 0 || tax != 0 || utiTax != 0) {
                            long uriKin2 = u022bmnjResultList.get(0).getUriKin() + uriKin;
                            long tax2 = u022bmnjResultList.get(0).getTax() + tax;
                            long utiTax2 = u022bmnjResultList.get(0).getUtiTax() + utiTax;
                            int kyakusu2 = u022bmnjResultList.get(0).getKyaksu() + kyakusu;
                            int uriSu2 = u022bmnjResultList.get(0).getUriSu() + uriSu;

                            // 日別店別部門別売上実績Insert
                            insertU012(resultForm, bmnGrid.get(i), nenDo, weekNo, weekDay, uriKin, tax, utiTax, uriSu,
                                    kyakusu, kaisyaCd, jigyobuCd, fullTenCd, uriDate);

                            // 月別店別部門別売上実績Update
                            updateU022(resultForm, bmnGrid.get(i), uriKin2, tax2, utiTax2, uriSu2, kyakusu2, kaisyaCd, jigyobuCd,
                                    fullTenCd, uriDate);

                            U042bmntExample u042bmntExample = new U042bmntExample();
                            u042bmntExample.createCriteria().andTenCdEqualTo(fullTenCd).andBmnCdEqualTo(bmnCd)
                                    .andUriDateEqualTo(uriDate);

                            List<U042bmnt> u042bmntList = u042bmntMapper.selectByExample(u042bmntExample);

                            if (!u042bmntList.isEmpty()) {
                                // 部門別売上修正トランUpdate
                                updateU042(u042bmntExample, uriKin, uriSu, kyakusu);
                            } else {
                                // 部門別売上修正トランInsert
                                insertU042(resultForm, bmnGrid.get(i), uriKin, uriSu, kyakusu, kaisyaCd, jigyobuCd,
                                        fullTenCd, uriDate);
                            }
                        }
                    } else {
                        long uriKin2 = uriKin - u012bmnjResultList.get(0).getUriKin();
                        long tax2 = tax - u012bmnjResultList.get(0).getTax();
                        long utiTax2 = utiTax - u012bmnjResultList.get(0).getUtiTax();
                        int kyakusu2 = kyakusu - (int) u012bmnjResultList.get(0).getKyaksu();
                        int uriSu2 = uriSu - (int) u012bmnjResultList.get(0).getUriSu();

                        if (uriKin2 != 0 || kyakusu2 != 0 || uriSu2 != 0 || tax2 != 0 || utiTax2 != 0) {
                            long uriKin3 = uriKin2 + u022bmnjResultList.get(0).getUriKin(); // 売上高
                            long tax3 = tax2 + u022bmnjResultList.get(0).getTax();
                            long utiTax3 = tax2 + u022bmnjResultList.get(0).getUtiTax();
                            int kyakusu3 = kyakusu2 + u022bmnjResultList.get(0).getKyaksu(); // 客数
                            int uriSu3 = uriSu2 + u022bmnjResultList.get(0).getUriSu(); // 点数

                            // 日別店別部門別売上実績Update
                            updateU012(resultForm, bmnGrid.get(i), nenDo, weekNo, weekDay, uriKin, tax, utiTax, uriSu,
                                    kyakusu, kaisyaCd, jigyobuCd, fullTenCd, uriDate);

                            // 月別店別部門別売上実績Update
                            updateU022(resultForm, bmnGrid.get(i), uriKin3, tax3, utiTax3, uriSu3, kyakusu3, kaisyaCd,
                                    jigyobuCd, fullTenCd, uriDate);

                            U042bmntExample u042bmntExample = new U042bmntExample();
                            u042bmntExample.createCriteria().andTenCdEqualTo(fullTenCd).andBmnCdEqualTo(bmnCd)
                                    .andUriDateEqualTo(uriDate);

                            List<U042bmnt> u042bmntList = u042bmntMapper.selectByExample(u042bmntExample);

                            if (!u042bmntList.isEmpty()) {
                                updateU042(u042bmntExample, uriKin, uriSu, kyakusu);
                            } else {
                                insertU042(resultForm, bmnGrid.get(i), uriKin, uriSu, kyakusu, kaisyaCd, jigyobuCd,
                                        fullTenCd, uriDate);
                            }
                        }
                    }
                }
            }
        }

        return ok();
    }

    // １１．データベースへデータ更新処理
    // １２．データベースへデータ更新処理
    /**
     * @param resultForm resultForm
     * @param u011tenj u011tenj
     * @param u021tenj u021tenj
     * @param u011tenjExample u011tenjExample
     * @param u021tenjExample u021tenjExample
     * @param nenDo nenDo
     * @param weekNo weekNo
     * @param weekDay weekDay
     */
    private void updateU011AndU021(Urjp0040ResultForm resultForm, U011tenj u011tenj, U021tenj u021tenj,
            U011tenjExample u011tenjExample, U021tenjExample u021tenjExample, String nenDo, String weekNo,
            String weekDay) {
        DateTime dt = new DateTime();

        List<Urjp0040Grid2Dto> grid2 = resultForm.getGrid2Area();

        // grid 2
        Long uriKin = grid2.get(0).getKingaku();
        Long gnkUriKin = grid2.get(1).getKingaku();
        Long crd06 = grid2.get(3).getKingaku();
        Long crd05 = grid2.get(4).getKingaku();
        Long crd09 = grid2.get(5).getKingaku();
        Long crd02 = grid2.get(6).getKingaku();
        Long crd01 = grid2.get(7).getKingaku();
        Long crd07 = grid2.get(8).getKingaku();
        Long crd08 = grid2.get(9).getKingaku();
        Long crd14 = grid2.get(10).getKingaku();
        Long tax = grid2.get(11).getKingaku();

        Integer crdSu06 = grid2.get(3).getMaiSu();
        Integer crdSu05 = grid2.get(4).getMaiSu();
        Integer crdSu09 = grid2.get(5).getMaiSu();
        Integer crdSu02 = grid2.get(6).getMaiSu();
        Integer crdSu01 = grid2.get(7).getMaiSu();
        Integer crdSu07 = grid2.get(8).getMaiSu();
        Integer crdSu08 = grid2.get(9).getMaiSu();
        Integer crdSu14 = grid2.get(10).getMaiSu();

        // 売上合計
        Long tukiUriKin;
        if (!resultForm.isKakuteiFlag()) { // 売上確定以前は更新しない
            tukiUriKin = u021tenj.getUriKin() + uriKin - u011tenj.getUriKin();
        } else {
            tukiUriKin = u021tenj.getUriKin();
        }
        Long tukiGnkUriKin = u021tenj.getGnkUriKin() + gnkUriKin - u011tenj.getGnkUriKin();
        Long tukiCrd06 = u021tenj.getCreditUtiwake06() + crd06 - u011tenj.getCreditUtiwake06();
        Long tukiCrd05 = u021tenj.getCreditUtiwake05() + crd05 - u011tenj.getCreditUtiwake05();
        Long tukiCrd09 = u021tenj.getCreditUtiwake09() + crd09 - u011tenj.getCreditUtiwake09();
        Long tukiCrd02 = u021tenj.getCreditUtiwake02() + crd02 - u011tenj.getCreditUtiwake02();
        Long tukiCrd01 = u021tenj.getCreditUtiwake01() + crd01 - u011tenj.getCreditUtiwake01();
        Long tukiCrd07 = u021tenj.getCreditUtiwake07() + crd07 - u011tenj.getCreditUtiwake07();
        Long tukiCrd08 = u021tenj.getCreditUtiwake08() + crd08 - u011tenj.getCreditUtiwake08();
        Long tukiCrd14 = u021tenj.getCreditUtiwake14() + crd14 - u011tenj.getCreditUtiwake14();
        
        Integer tukiCrdSu06 = u021tenj.getCreditUtiwakeSu06() + crdSu06 - u011tenj.getCreditUtiwakeSu06();
        Integer tukiCrdSu05 = u021tenj.getCreditUtiwakeSu05() + crdSu05 - u011tenj.getCreditUtiwakeSu05();
        Integer tukiCrdSu09 = u021tenj.getCreditUtiwakeSu09() + crdSu09 - u011tenj.getCreditUtiwakeSu09();
        Integer tukiCrdSu02 = u021tenj.getCreditUtiwakeSu02() + crdSu02 - u011tenj.getCreditUtiwakeSu02();
        Integer tukiCrdSu01 = u021tenj.getCreditUtiwakeSu01() + crdSu01 - u011tenj.getCreditUtiwakeSu01();
        Integer tukiCrdSu07 = u021tenj.getCreditUtiwakeSu07() + crdSu07 - u011tenj.getCreditUtiwakeSu07();
        Integer tukiCrdSu08 = u021tenj.getCreditUtiwakeSu08() + crdSu08 - u011tenj.getCreditUtiwakeSu08();
        Integer tukiCrdSu14 = u021tenj.getCreditUtiwakeSu14() + crdSu14 - u011tenj.getCreditUtiwakeSu14();

        // 消費税
        Long tukiTax;
        // 売上確定以前は更新しない
        if (!resultForm.isKakuteiFlag()) {
            tukiTax = u021tenj.getTax() + tax - u011tenj.getTax();
        } else {
            tukiTax = u021tenj.getTax();
        }
        
        // 客数
        Integer tenKyaksu = resultForm.getTenKyakSu();
        Integer tukiTenKyakusu = u021tenj.getKyaksu() + tenKyaksu - u011tenj.getKyaksu();

        U011tenj toBeUpdatedU011tenj = new U011tenj();
        toBeUpdatedU011tenj.setNendo(nenDo);
        toBeUpdatedU011tenj.setWeekNo(weekNo);
        toBeUpdatedU011tenj.setWeekDay(weekDay);
        toBeUpdatedU011tenj.setWeather(resultForm.getWeather());

        if (!resultForm.isKakuteiFlag()) {
            toBeUpdatedU011tenj.setUriKin(uriKin);
            toBeUpdatedU011tenj.setTax(tax);
        }

        toBeUpdatedU011tenj.setGnkUriKin(gnkUriKin);
        toBeUpdatedU011tenj.setCreditUtiwake06(crd06);
        toBeUpdatedU011tenj.setCreditUtiwake05(crd05);
        toBeUpdatedU011tenj.setCreditUtiwake09(crd09);
        toBeUpdatedU011tenj.setCreditUtiwake02(crd02);
        toBeUpdatedU011tenj.setCreditUtiwake01(crd01);
        toBeUpdatedU011tenj.setCreditUtiwake07(crd07);
        toBeUpdatedU011tenj.setCreditUtiwake08(crd08);
        toBeUpdatedU011tenj.setCreditUtiwake14(crd14);
        toBeUpdatedU011tenj.setCreditUtiwakeSu06(crdSu06);
        toBeUpdatedU011tenj.setCreditUtiwakeSu05(crdSu05);
        toBeUpdatedU011tenj.setCreditUtiwakeSu09(crdSu09);
        toBeUpdatedU011tenj.setCreditUtiwakeSu02(crdSu02);
        toBeUpdatedU011tenj.setCreditUtiwakeSu01(crdSu01);
        toBeUpdatedU011tenj.setCreditUtiwakeSu07(crdSu07);
        toBeUpdatedU011tenj.setCreditUtiwakeSu08(crdSu08);
        toBeUpdatedU011tenj.setCreditUtiwakeSu14(crdSu14);
        toBeUpdatedU011tenj.setKyaksu(tenKyaksu == null ? 0 : tenKyaksu);
        toBeUpdatedU011tenj.setTenKyaku(tenKyaksu == null ? 0 : tenKyaksu);
        toBeUpdatedU011tenj.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU011tenj.setCmnTermId(context.getTermId());
        toBeUpdatedU011tenj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU011tenj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU011tenj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u011tenjMapper.updateByExampleSelective(toBeUpdatedU011tenj, u011tenjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }

        // 月別店別売上実績Update
        U021tenj toBeUpdatedU021tenj = new U021tenj();

        toBeUpdatedU021tenj.setUriKin(tukiUriKin);
        toBeUpdatedU021tenj.setKyaksu(tukiTenKyakusu);
        toBeUpdatedU021tenj.setTenKyaku(tukiTenKyakusu);
        toBeUpdatedU021tenj.setTax(tukiTax);
        toBeUpdatedU021tenj.setGnkUriKin(tukiGnkUriKin);
        toBeUpdatedU021tenj.setCreditUtiwake06(tukiCrd06);
        toBeUpdatedU021tenj.setCreditUtiwake05(tukiCrd05);
        toBeUpdatedU021tenj.setCreditUtiwake09(tukiCrd09);
        toBeUpdatedU021tenj.setCreditUtiwake02(tukiCrd02);
        toBeUpdatedU021tenj.setCreditUtiwake01(tukiCrd01);
        toBeUpdatedU021tenj.setCreditUtiwake07(tukiCrd07);
        toBeUpdatedU021tenj.setCreditUtiwake08(tukiCrd08);
        toBeUpdatedU021tenj.setCreditUtiwake14(tukiCrd14);
        toBeUpdatedU021tenj.setCreditUtiwakeSu06(tukiCrdSu06);
        toBeUpdatedU021tenj.setCreditUtiwakeSu05(tukiCrdSu05);
        toBeUpdatedU021tenj.setCreditUtiwakeSu09(tukiCrdSu09);
        toBeUpdatedU021tenj.setCreditUtiwakeSu02(tukiCrdSu02);
        toBeUpdatedU021tenj.setCreditUtiwakeSu01(tukiCrdSu01);
        toBeUpdatedU021tenj.setCreditUtiwakeSu07(tukiCrdSu07);
        toBeUpdatedU021tenj.setCreditUtiwakeSu08(tukiCrdSu08);
        toBeUpdatedU021tenj.setCreditUtiwakeSu14(tukiCrdSu14);

        toBeUpdatedU021tenj.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU021tenj.setCmnTermId(context.getTermId());
        toBeUpdatedU021tenj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU021tenj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU021tenj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        count = u021tenjMapper.updateByExampleSelective(toBeUpdatedU021tenj, u021tenjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }

    // １３．データベースへデータ更新処理
    /**
     * @param resultForm resultForm
     * @param bmnGridRow bmnGridRow
     * @param nenDo nenDo
     * @param weekNo weekNo
     * @param weekDay weekDay
     * @param uriKin uriKin
     * @param tax tax
     * @param utiTax utiTax
     * @param uriSu uriSu
     * @param kyakusu kyakusu
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void updateU012(Urjp0040ResultForm resultForm, Urjp0040Dto bmnGridRow, String nenDo, String weekNo,
            String weekDay, long uriKin, long tax, long utiTax, int uriSu, int kyakusu, String kaisyaCd, String jigyobuCd,
            String fullTenCd, String uriDate) {
        DateTime dt = new DateTime();

        U012bmnj toBeUpdatedU012bmnj = new U012bmnj();
        toBeUpdatedU012bmnj.setNendo(nenDo);
        toBeUpdatedU012bmnj.setWeekNo(weekNo);
        toBeUpdatedU012bmnj.setWeekDay(weekDay);

        toBeUpdatedU012bmnj.setUriSu(uriSu);
        toBeUpdatedU012bmnj.setUriKin(uriKin);
        toBeUpdatedU012bmnj.setKyaksu(kyakusu);
        toBeUpdatedU012bmnj.setTax(tax);
        toBeUpdatedU012bmnj.setUtiTax(utiTax);

        toBeUpdatedU012bmnj.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU012bmnj.setCmnTermId(context.getTermId());

        toBeUpdatedU012bmnj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU012bmnj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU012bmnj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        U012bmnjExample u012bmnjExample = new U012bmnjExample();
        u012bmnjExample.createCriteria().andTenCdEqualTo(fullTenCd)
                .andBmnCdEqualTo(jigyobuCd + bmnGridRow.getBmnCd()).andUriDateEqualTo(uriDate);

        int count = u012bmnjMapper.updateByExampleSelective(toBeUpdatedU012bmnj, u012bmnjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }

    // ３．データベースへデータ更新処理
    /**
     * @param resultForm resultForm
     * @param existedU021tenj existedU021tenj
     * @param u021tenjExample u021tenjExample
     */
    private void updateU021(Urjp0040ResultForm resultForm, U021tenj existedU021tenj, U021tenjExample u021tenjExample) {
        long uriage;
        long tax;

        DateTime dt = new DateTime();
        
        if (!resultForm.isKakuteiFlag()) {
            uriage = existedU021tenj.getUriKin() + resultForm.getGrid2Area().get(0).getKingaku();
            tax = existedU021tenj.getTax() + resultForm.getGrid2Area().get(11).getKingaku();
        } else {
            uriage = existedU021tenj.getUriKin();
            tax = existedU021tenj.getTax();
        }
        
        Long tukiCrd06 = existedU021tenj.getCreditUtiwake06() + resultForm.getGrid2Area().get(3).getKingaku();
        Long tukiCrd05 = existedU021tenj.getCreditUtiwake05() + resultForm.getGrid2Area().get(4).getKingaku();
        Long tukiCrd09 = existedU021tenj.getCreditUtiwake09() + resultForm.getGrid2Area().get(5).getKingaku();
        Long tukiCrd02 = existedU021tenj.getCreditUtiwake02() + resultForm.getGrid2Area().get(6).getKingaku();
        Long tukiCrd01 = existedU021tenj.getCreditUtiwake01() + resultForm.getGrid2Area().get(7).getKingaku();
        Long tukiCrd07 = existedU021tenj.getCreditUtiwake07() + resultForm.getGrid2Area().get(8).getKingaku();
        Long tukiCrd08 = existedU021tenj.getCreditUtiwake08() + resultForm.getGrid2Area().get(9).getKingaku();
        Long tukiCrd14 = existedU021tenj.getCreditUtiwake14() + resultForm.getGrid2Area().get(10).getKingaku();
        
        Integer tukiCrdSu06 = existedU021tenj.getCreditUtiwakeSu06() + resultForm.getGrid2Area().get(3).getMaiSu();
        Integer tukiCrdSu05 = existedU021tenj.getCreditUtiwakeSu05() + resultForm.getGrid2Area().get(4).getMaiSu();
        Integer tukiCrdSu09 = existedU021tenj.getCreditUtiwakeSu09() + resultForm.getGrid2Area().get(5).getMaiSu();
        Integer tukiCrdSu02 = existedU021tenj.getCreditUtiwakeSu02() + resultForm.getGrid2Area().get(6).getMaiSu();
        Integer tukiCrdSu01 = existedU021tenj.getCreditUtiwakeSu01() + resultForm.getGrid2Area().get(7).getMaiSu();
        Integer tukiCrdSu07 = existedU021tenj.getCreditUtiwakeSu07() + resultForm.getGrid2Area().get(8).getMaiSu();
        Integer tukiCrdSu08 = existedU021tenj.getCreditUtiwakeSu08() + resultForm.getGrid2Area().get(9).getMaiSu();
        Integer tukiCrdSu14 = existedU021tenj.getCreditUtiwakeSu14() + resultForm.getGrid2Area().get(10).getMaiSu();



        // ３．データベースへデータ更新処理
        U021tenj toBeUpdatedU021tenj = new U021tenj();
        toBeUpdatedU021tenj.setUriKin(uriage);
        toBeUpdatedU021tenj.setTax(tax);
        toBeUpdatedU021tenj.setCreditUtiwake06(tukiCrd06);
        toBeUpdatedU021tenj.setCreditUtiwake05(tukiCrd05);
        toBeUpdatedU021tenj.setCreditUtiwake09(tukiCrd09);
        toBeUpdatedU021tenj.setCreditUtiwake02(tukiCrd02);
        toBeUpdatedU021tenj.setCreditUtiwake01(tukiCrd01);
        toBeUpdatedU021tenj.setCreditUtiwake07(tukiCrd07);
        toBeUpdatedU021tenj.setCreditUtiwake08(tukiCrd08);
        toBeUpdatedU021tenj.setCreditUtiwake14(tukiCrd14);
        
        toBeUpdatedU021tenj.setCreditUtiwakeSu06(tukiCrdSu06);
        toBeUpdatedU021tenj.setCreditUtiwakeSu05(tukiCrdSu05);
        toBeUpdatedU021tenj.setCreditUtiwakeSu09(tukiCrdSu09);
        toBeUpdatedU021tenj.setCreditUtiwakeSu02(tukiCrdSu02);
        toBeUpdatedU021tenj.setCreditUtiwakeSu01(tukiCrdSu01);
        toBeUpdatedU021tenj.setCreditUtiwakeSu07(tukiCrdSu07);
        toBeUpdatedU021tenj.setCreditUtiwakeSu08(tukiCrdSu08);
        toBeUpdatedU021tenj.setCreditUtiwakeSu14(tukiCrdSu14);

        toBeUpdatedU021tenj.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU021tenj.setCmnTermId(context.getTermId());

        toBeUpdatedU021tenj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU021tenj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU021tenj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u021tenjMapper.updateByExampleSelective(toBeUpdatedU021tenj, u021tenjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }

    // ８．データベースへデータ更新処理
    /**
     * @param resultForm resultForm
     * @param bmnGridRow bmnGridRow
     * @param uriKin uriKin
     * @param tax tax
     * @param utiTax utiTax
     * @param uriSu uriSu
     * @param kyakusu kyakusu
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param fullTenCd fullTenCd
     * @param uriDate uriDate
     */
    private void updateU022(Urjp0040ResultForm resultForm, Urjp0040Dto bmnGridRow, long uriKin, long tax, long utiTax,
            int uriSu, int kyakusu, String kaisyaCd, String jigyobuCd, String fullTenCd, String uriDate) {
        DateTime dt = new DateTime();

        U022bmnj toBeUpdatedU022bmnj = new U022bmnj();
        toBeUpdatedU022bmnj.setUriSu(uriSu);
        toBeUpdatedU022bmnj.setUriKin(uriKin);
        toBeUpdatedU022bmnj.setTax(tax);
        toBeUpdatedU022bmnj.setUtiTax(utiTax);
        toBeUpdatedU022bmnj.setKyaksu(kyakusu);

        toBeUpdatedU022bmnj.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU022bmnj.setCmnTermId(context.getTermId());

        toBeUpdatedU022bmnj.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU022bmnj.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU022bmnj.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        U022bmnjExample u022bmnjExample = new U022bmnjExample();
        u022bmnjExample.createCriteria().andTenCdEqualTo(fullTenCd)
                .andBmnCdEqualTo(jigyobuCd + bmnGridRow.getBmnCd()).andYyyymmEqualTo(uriDate.substring(0, 6));

        int count = u022bmnjMapper.updateByExampleSelective(toBeUpdatedU022bmnj, u022bmnjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }

    // ５．データベースへデータ更新処理
    /**
     * @param resultForm resultForm resultForm
     * @param u041tenjExample u041tenjExample u041tenjExample
     */
    private void updateU041(Urjp0040ResultForm resultForm, U041tentExample u041tenjExample) {
        DateTime dt = new DateTime();
        U041tent toBeUpdatedU041tent = new U041tent();

        toBeUpdatedU041tent.setWeather(resultForm.getWeather());

        if (resultForm.isInsert() || !resultForm.isKakuteiFlag()) {
            toBeUpdatedU041tent.setUriKin(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(0)
                    .getKingaku() : 0);
            toBeUpdatedU041tent.setTax(!resultForm.isKakuteiFlag() ? resultForm.getGrid2Area().get(8).getKingaku()
                    : 0);
        }

        toBeUpdatedU041tent.setKyaksu(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());
        toBeUpdatedU041tent.setTenKyaku(resultForm.getTenKyakSu() == null ? 0 : resultForm.getTenKyakSu());

        toBeUpdatedU041tent.setGnkUriKin(resultForm.getGrid2Area().get(1).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake06(resultForm.getGrid2Area().get(3).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake05(resultForm.getGrid2Area().get(4).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake09(resultForm.getGrid2Area().get(5).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake02(resultForm.getGrid2Area().get(6).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake01(resultForm.getGrid2Area().get(7).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake07(resultForm.getGrid2Area().get(8).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake08(resultForm.getGrid2Area().get(9).getKingaku());
        toBeUpdatedU041tent.setCreditUtiwake14(resultForm.getGrid2Area().get(10).getKingaku());
        
        toBeUpdatedU041tent.setCreditUtiwakeSu06(resultForm.getGrid2Area().get(3).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu05(resultForm.getGrid2Area().get(4).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu09(resultForm.getGrid2Area().get(5).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu02(resultForm.getGrid2Area().get(6).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu01(resultForm.getGrid2Area().get(7).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu07(resultForm.getGrid2Area().get(8).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu08(resultForm.getGrid2Area().get(9).getMaiSu());
        toBeUpdatedU041tent.setCreditUtiwakeSu14(resultForm.getGrid2Area().get(10).getMaiSu());

        toBeUpdatedU041tent.setSyoriFlg("0");
        toBeUpdatedU041tent.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU041tent.setCmnTermId(context.getTermId());

        toBeUpdatedU041tent.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU041tent.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU041tent.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u041tentMapper.updateByExampleSelective(toBeUpdatedU041tent, u041tenjExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }

    // １０．データベースへデータ更新処理
    /**
     * @param u042bmntExample u042bmntExample
     * @param uriKin uriKin
     * @param uriSu uriSu
     * @param kyakusu kyakusu
     */
    private void updateU042(U042bmntExample u042bmntExample, long uriKin, int uriSu, int kyakusu) {
        DateTime dt = new DateTime();

        U042bmnt toBeUpdatedU042bmnt = new U042bmnt();

        toBeUpdatedU042bmnt.setUriSu(uriSu);
        toBeUpdatedU042bmnt.setUriKin(uriKin);
        toBeUpdatedU042bmnt.setKyaksu(kyakusu);
        toBeUpdatedU042bmnt.setSyoriFlg("0");

        toBeUpdatedU042bmnt.setCmnTantoCd(context.getTantoCode());
        toBeUpdatedU042bmnt.setCmnTermId(context.getTermId());

        toBeUpdatedU042bmnt.setCmnInsdd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU042bmnt.setCmnUpddd(dt.toString(DATETIME_FORMAT_DATE));
        toBeUpdatedU042bmnt.setCmnUpdtime(dt.toString(DATETIME_FORMAT_TIME));

        int count = u042bmntMapper.updateByExampleSelective(toBeUpdatedU042bmnt, u042bmntExample);
        if (count == 0) {
            throw new ChaseException(Messages.get(CCMessageConst.MSG_KEY_EXCEPTION_UPDATE_NO_RECORD));
        }
    }

    /**
     * @param kaisyaCd kaisyaCd
     * @param jigyobuCd jigyobuCd
     * @param tenCd tenCd
     * @param uriDate uriDate
     * @param m020ctlm m020ctlm
     * @return boolean
     */
    private boolean checkCond(String kaisyaCd, String jigyobuCd, String tenCd, String uriDate, M020ctlm m020ctlm) {
        boolean flag = true;

        // キー項目入力チェック処理
        M020ctlmExample m020ctlmExample = new M020ctlmExample();
        m020ctlmExample.createCriteria().andCdEqualTo("DATE");

        List<M020ctlm> m020ctlmList = m020ctlmMapper.selectByExample(m020ctlmExample);

        // 戻る値 データ == ""の場合
        if (m020ctlmList.isEmpty()) {
            // C0251
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_FAILED_TO_GET_DATA_MASTER);
            return false;
        }

        BeanUtils.copyProperties(m020ctlmList.get(0), m020ctlm);
        String dataStr = m020ctlm.getData();

        String unyobi = dataStr.substring(0, 8);

        // 売上日 > 運用日の場合
        if (!CCDateUtil.isDate(uriDate)) {
            errRes.addErrorInfo("uriDate", CCMessageConst.MSG_KEY_ITEM_INPUT_IS_ERROR);
            flag = false;
        }

        if (CCStringUtil.cnvStrToInt(uriDate) > CCStringUtil.cnvStrToInt(unyobi)) {
            // U0001
            errRes.addErrorInfo("uriDate", CCMessageConst.MSG_KEY_DATE_BEFORE);
            flag = false;
        }

        // 事業部存在チェック
        M000kaimExample m000kaimExample = new M000kaimExample();
        m000kaimExample.createCriteria().andKaisyaCdEqualTo(kaisyaCd);
        m000kaimExample.setSearchDate(unyobi);

        List<M000kaim> m000kaimList = m000kaimMapper.selectByExample(m000kaimExample);

        if (m000kaimList.isEmpty() || ACT_KBN_DELETE.equals(m000kaimList.get(0).getActKbn())) {
            // C0004
            errRes.addErrorInfo("kaisyaCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            flag = false;
        }

        // 事業部存在チェック
        M001jgymExample m001jgymExample = new M001jgymExample();
        m001jgymExample.createCriteria().andKaisyaCdEqualTo(kaisyaCd).andJigyobuCdEqualTo(jigyobuCd);

        m001jgymExample.setSearchDate(unyobi);

        List<M001jgym> m001jgymList = m001jgymMapper.selectByExample(m001jgymExample);

        if (m001jgymList.isEmpty() || ACT_KBN_DELETE.equals(m001jgymList.get(0).getActKbn())) {
            // C0004
            errRes.addErrorInfo("jigyobuCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            flag = false;
        }

        // 店舗存在チェック
        String fullTenCd = kaisyaCd + jigyobuCd + tenCd;
        M006tenmExample m006tenmExample = new M006tenmExample();
        m006tenmExample.createCriteria().andTenCdEqualTo(fullTenCd);

        m006tenmExample.setSearchDate(unyobi);

        List<M006tenm> m006tenmList = m006tenmMapper.selectByExample(m006tenmExample);

        if (m006tenmList.isEmpty() || ACT_KBN_DELETE.equals(m006tenmList.get(0).getActKbn())) {
            // C0004
            errRes.addErrorInfo("tenCd", CCMessageConst.MSG_KEY_NOT_EXIST_REGISTERED);
            flag = false;
        }

        return flag;
    }
}
