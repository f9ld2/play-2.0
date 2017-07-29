///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : 買掛金支払チェックリスト出力
 * 改版履歴
 * Rev. 改版年月日   改版者名       内容
 * 1.0   2014.04.05   VuQQ      新規作成
 * =================================================================== */

package jp.co.necsoft.web_md.core.app.controllers.kk;



import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.ChaseException;
import jp.co.necsoft.web_md.common.mybatis.dao.MyBatisSqlMapDao;
import jp.co.necsoft.web_md.core.app.file.report.Kkpr1180Report;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp1180ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.CCSecurityAuthenticator;
import jp.co.necsoft.web_md.core.common.CCSystemContext;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon;
import jp.co.necsoft.web_md.core.common.biz.kk.CCKKCommon.JYOTAI_KBN;
import jp.co.necsoft.web_md.core.common.forms.ErrorResponse;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M000kaimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K003rshr;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M000kaimExample;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.utils.CCDateUtil;
import jp.co.necsoft.web_md.core.common.utils.CCReportUtil;
import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
*買掛金支払チェックリスト出力のControllerクラス
*
*/
@Security.Authenticated(CCSecurityAuthenticator.class)
public class Kkjp1180Ctrl extends Controller {
    
    /** PROGRAM ID */
    static final String PROGRAM_ID = "KKJP1180";
    /** PROGRAM VERSION */
    static final String PROGRAM_VERSION = "V1.00";
    /** DATE FORMAT */
    static final String DATETIME_FORMAT_DATE = "yyyyMMdd";
    /** TIME  FORMAT */
    static final String DATETIME_FORMAT_TIME = "HHmm";
    
    /** m000kaimMapper Object */
    @Inject
    private M000kaimMapper m000kaimMapper;
    /** MyBatisSqlMapDao Object */
    @Inject
    private MyBatisSqlMapDao dao;
    /** ErrorResponse Object */
    @Inject
    private ErrorResponse errRes;
    /** CCSystemContext Object */
    @Inject
    private CCSystemContext context;
    /** cmJKKCommon */
    @Inject
    private CCKKCommon cmJKKCom;

    
/**
* 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。*
 * @param taisyodateY 対象年
 * @param taisyodateM 月
*  @return クライアントへ返却する結果
*/
public Result query(String taisyodateY, String taisyodateM) {
    List<Kkjp1180ResultForm> resultList = new ArrayList<Kkjp1180ResultForm>();
    Kkjp1180ResultForm resultForm = new Kkjp1180ResultForm();
    String kaisyaNm;
    String yyyyMm;
    String dateEnd;
    
    if (taisyodateY.length() != 0) {
    	taisyodateY = CCStringUtil.suppZero(taisyodateY, 4);
    }
    if (taisyodateM.length() != 0) {
    	taisyodateM = CCStringUtil.suppZero(taisyodateM, 2);
	}
    
    yyyyMm = taisyodateY + taisyodateM;
    dateEnd = CCDateUtil.getDateEndOfMonth(taisyodateY, taisyodateM);;
    
    //入力された条件に該当する中分類コードを取得する
    Map<String, String> hashMap = new HashMap<String, String>();
    
    hashMap.put("kaisyaCd", CCKKCommon.CON_KAISHA_CODE);
    hashMap.put("jotaiKbn", String.valueOf(JYOTAI_KBN.KBN_SHRZUMI));
    hashMap.put("friEndDate01", yyyyMm + "01");
    hashMap.put("friEndDate02", dateEnd);
    
    List<K003rshr> listK003rshr = dao.selectMany("selectKkjp1180K003rshrK008trhk", hashMap, K003rshr.class);
    int iCnt = listK003rshr.size();
    if (iCnt == 0) {
        errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
        return badRequest(Json.toJson(errRes));
    }
    
    CCReportUtil cru = new CCReportUtil("KKSV1180", context);
    CsvManager csvManager = CsvManagerFactory.newCsvManager();
    List<Kkpr1180Report> csvBean = new ArrayList<Kkpr1180Report>();
     
    String kikanTo = "";
    for (int i = 0; i < iCnt; i++) {
        Kkpr1180Report objKkpr1180Report = new Kkpr1180Report();
        
        // 会社名を取得する 
        M000kaimExample m000kaimExample = new M000kaimExample();
        m000kaimExample.createCriteria().andKaisyaCdEqualTo(CCKKCommon.CON_KAISHA_CODE);
        m000kaimExample.setSearchDate(dateEnd);
        List<M000kaim> listM000kaim = this.m000kaimMapper.selectByExample(m000kaimExample);

        // 取得データが存在しない場合 
        if (listM000kaim.size() != 0) {
            kaisyaNm = listM000kaim.get(0).getKaiNm();
        } else {
            kaisyaNm = "";
        }
        
        if (CCStringUtil.cnvStrToInt(CCDateUtil.getSysDate())  >= 1 
                    ||  CCStringUtil.cnvStrToInt(CCDateUtil.getSysDate()) <= 15) {
            kikanTo = CCDateUtil.doCalcDate(yyyyMm + "01", "DAY_OF_MONTH", 1);
        } else {
            kikanTo = yyyyMm + "15";
        }
        
        objKkpr1180Report.h00_progid = '"' + PROGRAM_ID + '"';
        objKkpr1180Report.h00_version = '"' + PROGRAM_VERSION + '"';
        objKkpr1180Report.h00_sdate = '"' + CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() 
                                        + CCDateUtil.getSysDate() + '"';
        objKkpr1180Report.h00_stime = '"' +  CCDateUtil.getSysHour() +  CCDateUtil.getSysMinute()  + '"';
        objKkpr1180Report.h00_page = '"' + "" + '"';
        objKkpr1180Report.h01_tanto_code = '"' + context.getTantoCode() + '"';
        objKkpr1180Report.h01_kaisya_code = '"' + CCKKCommon.CON_KAISHA_CODE + '"';
        objKkpr1180Report.h01_kaisya_name = '"' + kaisyaNm + '"';
        objKkpr1180Report.h02_kikan_from = '"' + yyyyMm + "01" + '"';
        objKkpr1180Report.h02_kikan_to =  '"' + kikanTo + '"';
        objKkpr1180Report.h01_buffer_01 = '"' + "" + '"';
        objKkpr1180Report.h01_buffer_02 = '"' + "" + '"';
        
        objKkpr1180Report.m01_sir_cd = '"' + listK003rshr.get(i).getMainToriCd() + '"';
        M011trim toriNmList = cmJKKCom.getComM011trim(dateEnd, listK003rshr.get(i).getMainToriCd());
        if (toriNmList != null) {
        	objKkpr1180Report.m01_sir_name = '"' + toriNmList.getTantoNm() + '"';
        } else {
        	objKkpr1180Report.m01_sir_name = '"' + "" + '"';
        }
        objKkpr1180Report.m01_shr_date = '"' + listK003rshr.get(i).getFriEndDate() + '"';
        objKkpr1180Report.m01_sime_date = '"' + listK003rshr.get(i).getSimeDate() + '"';
        objKkpr1180Report.m01_shr_kingk = '"' + String.valueOf(listK003rshr.get(i).getShrKin()) + '"';
        objKkpr1180Report.m02_shr_kingk = '"' + "" + '"';
        
        csvBean.add(objKkpr1180Report);
    }
    // ＣＳＶ作成（データ出力） 
    try {
            csvManager.save(csvBean, Kkpr1180Report.class).to(new File(cru.getCsvFilePath()),
                    CCReportUtil.CSV_OUTPUT_ENCODING);
    } catch (Exception e) {
        throw new ChaseException("CSV生成エラー:[" + e + "]");
    }
    
    cru.makePDF();
    
    try {
        URL uPdfUrl = new URL(cru.getPdfUrl());
        resultForm.setPdfUrl(uPdfUrl);
    } catch (Exception e) {
        throw new ChaseException("URL生成エラー:[" + e + "]");
    }
    
    resultList.add(resultForm);

    // gc対応 
    cru = null;
    System.gc();
    
    return ok(Json.toJson(resultList));

}

}
