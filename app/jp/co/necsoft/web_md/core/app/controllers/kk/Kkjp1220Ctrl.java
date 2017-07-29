///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 機能名称 : 支払予定額一覧表
 * 改版履歴
 * Rev. 改版年月日 改版者名 内容
 * 1.0 2014.04.24 VuQQ 新規作成
 * ===================================================================
 */
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
import jp.co.necsoft.web_md.core.app.file.report.Kkpr1220Report;
import jp.co.necsoft.web_md.core.app.forms.kk.Kkjp1220ResultForm;
import jp.co.necsoft.web_md.core.common.CCMessageConst;
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
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.orangesignal.csv.manager.CsvManager;
import com.orangesignal.csv.manager.CsvManagerFactory;

/**
 * 支払予定額一覧表
 * のControllerクラス
 * 
 */
public class Kkjp1220Ctrl extends Controller {

    /** PROGRAM ID */
    private static final String PROGRAM_ID = "KKPR1220";
    /** PROGRAM VERSION */
    private static final String PROGRAM_VERSION = "V1.00";

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
     * 
     * @param taisyodateY 対象年
     * @param taisyodateM 月
     * @return クライアントへ返却する結果
     */
    public Result query(String taisyodateY, String taisyodateM) {
        List<Kkjp1220ResultForm> resultList = new ArrayList<Kkjp1220ResultForm>();
        Kkjp1220ResultForm resultForm = new Kkjp1220ResultForm();
        String kaisyaNm;

        String dateEnd = CCDateUtil.getDateEndOfMonth(taisyodateY, taisyodateM);

        // 入力された条件に該当する中分類コードを取得する
        Map<String, String> hashMap = new HashMap<String, String>();

        hashMap.put("kaisyaCd", cmJKKCom.getKaisyaCode());
        hashMap.put("jotaiKbn", String.valueOf(JYOTAI_KBN.KBN_KAKUTEI));

        List<K003rshr> listK003rshr = dao.selectMany("selectKkjp1220K003M011", hashMap, K003rshr.class);
        int iCnt = listK003rshr.size();
        if (iCnt == 0) {
            errRes.addErrorInfo(CCMessageConst.MSG_KEY_NO_FOUND_DATA);
            return badRequest(Json.toJson(errRes));
        }

        CCReportUtil cru = new CCReportUtil("KKJP1220", context);
        CsvManager csvManager = CsvManagerFactory.newCsvManager();
        List<Kkpr1220Report> csvBean = new ArrayList<Kkpr1220Report>();
        Map<String, String> kaisyaNmMap = new HashMap<String, String>();
        Map<String, String> m011Map = new HashMap<String, String>();
        String date = CCDateUtil.getSysYear() + CCDateUtil.getSysMonth() + CCDateUtil.getSysDate();
        String time = CCDateUtil.getSysHour() + CCDateUtil.getSysMinute();
        String tantoCode = context.getTantoCode();

        for (int i = 0; i < iCnt; i++) {
            Kkpr1220Report objKkpr1220Report = new Kkpr1220Report();

            String kaisyaCode = cmJKKCom.getKaisyaCode();
            if (kaisyaNmMap.containsKey(kaisyaCode)) {
                kaisyaNm = kaisyaNmMap.get(kaisyaCode);
            } else {
                // 会社名を取得する
                M000kaimExample m000kaimExample = new M000kaimExample();
                m000kaimExample.createCriteria().andKaisyaCdEqualTo(cmJKKCom.getKaisyaCode());
                m000kaimExample.setSearchDate(dateEnd);
                List<M000kaim> listM000kaim = this.m000kaimMapper.selectByExample(m000kaimExample);
    
                // 取得データが存在しない場合
                if (listM000kaim.size() != 0) {
                    kaisyaNm = listM000kaim.get(0).getKaiNm();
                } else {
                    kaisyaNm = "";
                }
                kaisyaNmMap.put(kaisyaCode, kaisyaNm);
            }

            objKkpr1220Report.h00_progid = '"' + PROGRAM_ID + '"';
            objKkpr1220Report.h00_version = '"' + PROGRAM_VERSION + '"';
            objKkpr1220Report.h00_sdate = '"' + date + '"';
            objKkpr1220Report.h00_stime = '"' + time + '"';
            objKkpr1220Report.h00_page = '"' + "" + '"';
            objKkpr1220Report.h01_tanto_code = '"' + tantoCode + '"';
            objKkpr1220Report.h01_kaisya_code = '"' + CCKKCommon.CON_KAISHA_CODE + '"';
            objKkpr1220Report.h01_kaisya_name = '"' + kaisyaNm + '"';
            objKkpr1220Report.h01_buffer_01 = '"' + "" + '"';
            objKkpr1220Report.h01_buffer_02 = '"' + "" + '"';
            objKkpr1220Report.m01_syori_no = listK003rshr.get(i).getShrSyoriNo();
            objKkpr1220Report.m01_shr_date = '"' + listK003rshr.get(i).getShrDate() + '"';

            objKkpr1220Report.m01_sir_cd = '"' + listK003rshr.get(i).getMainToriCd() + '"';
            String mainToriCd = listK003rshr.get(i).getMainToriCd();
            String mainToriNm = "";

            if (m011Map.containsKey(mainToriCd)) {
                mainToriNm = m011Map.get(mainToriCd);
            } else {
                M011trim toriNmRecord = cmJKKCom.getComM011trim(dateEnd, listK003rshr.get(i).getMainToriCd());
                if (toriNmRecord != null) {
                    mainToriNm = toriNmRecord.getTriNm();
                } else {
                    mainToriNm = "";
                }
                m011Map.put(mainToriCd, mainToriNm);
            }
            objKkpr1220Report.m01_sir_name = '"' + mainToriNm + '"';

            objKkpr1220Report.m01_komoku = '"' + listK003rshr.get(i).getKakToriKmk() + '"';
            objKkpr1220Report.m01_tekiyo = '"' + listK003rshr.get(i).getTekiyo() + '"';
            objKkpr1220Report.m01_kingk = '"' + String.valueOf(listK003rshr.get(i).getShrKin()) + '"';
            objKkpr1220Report.m01_stax = '"' + String.valueOf(listK003rshr.get(i).getSotoTaxKin()) + '"';
            objKkpr1220Report.m01_gokei = '"' + String.valueOf(listK003rshr.get(i).getShrKinAll()) + '"';

            csvBean.add(objKkpr1220Report);
        }
        // ＣＳＶ作成（データ出力）
        try {
            csvManager.save(csvBean, Kkpr1220Report.class).to(new File(cru.getCsvFilePath()),
                    CCReportUtil.CSV_OUTPUT_ENCODING);
        } catch (Exception e) {
            throw new ChaseException(e);
        }

        cru.makePDF();

        try {
            URL uPdfUrl = new URL(cru.getPdfUrl());
            resultForm.setPdfUrl(uPdfUrl);
        } catch (Exception e) {
            throw new ChaseException(e);
        }

        resultList.add(resultForm);

        // gc対応
        cru = null;
        System.gc();

        return ok(Json.toJson(resultList));
    }

}
