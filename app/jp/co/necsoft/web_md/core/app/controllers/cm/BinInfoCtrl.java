// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ====================================================================
 * 
 * 機能名称 : 商品マスタレイアウト変更
 * 
 * Rev.改版年月日 改版者名 内容
 * 
 * 1.0 2014/09/05 hainp 新規作成
 * 
 * ===================================================================
 */
package jp.co.necsoft.web_md.core.app.controllers.cm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.app.dto.cm.BinInfoHacchyuDto;
import jp.co.necsoft.web_md.core.app.forms.cm.BinInfoCondForm;
import jp.co.necsoft.web_md.core.app.forms.cm.BinInfoResultForm;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M011trimMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trim;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M011trimExample;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * 商品マスタレイアウト変更のControllerクラス
 */
public class BinInfoCtrl extends Controller {

    /**  削除Flag:Normal */
    private static final String DELETED_FLAG_NORMAL = "0";
    /**  削除 */
    private static final String TYPE_ACT_KBN_DELETED = "9";
    
    /** Empty Sting */
    private static final String EMPTY_STRING = "";

    /** M011trimMapper */
    @Inject
    private M011trimMapper m011trimMapper;


    /**
     * 引数とリクエスト(パラメータ)を条件にDBからデータ(複数件)を検索し返却する。
     * @param triCd  取引先
     * @return クライアントへ返却する結果
     */
    public Result query(String triCd) {
        Form<BinInfoCondForm> form = new Form<BinInfoCondForm>(BinInfoCondForm.class);
        form = form.bindFromRequest();

        BinInfoCondForm condForm = form.get();
        String hakkoDay = condForm.getHakkoDay();
        
        // M011trimMapperのselectByExampleを使用する。
        M011trimExample m011trimExample = new M011trimExample();
        m011trimExample.createCriteria().andTriCdEqualTo(triCd);
        m011trimExample.setSearchDate(hakkoDay);
        List<M011trim> m011trims = m011trimMapper.selectByExample(m011trimExample);
        
        BinInfoResultForm resultForm = null;
        if (m011trims.size() == 0 || TYPE_ACT_KBN_DELETED.equals(m011trims.get(0).getActKbn())
                || DELETED_FLAG_NORMAL.equals(m011trims.get(0).getDeleteFlg())) {
            resultForm = new BinInfoResultForm();
            resultForm.setHacchyuDtoIsNull();
        } else {
            resultForm = getResultForm(m011trims);
        }
        
        return ok(Json.toJson(resultForm));
    }

    /**
     * 結果フォームを取得
     * @param m011trims データm011trims
     * @return 結果フォーム
     */
    private BinInfoResultForm getResultForm(List<M011trim> m011trims) {
        BinInfoResultForm resultForm = new BinInfoResultForm();
        // 登録データ
        M011trim m011trim = m011trims.get(0);
        
        List<BinInfoHacchyuDto> hacchyuDtos = new ArrayList<BinInfoHacchyuDto>();
        BinInfoHacchyuDto hacchyuDto = new BinInfoHacchyuDto();
        // 月 = 登録データ[ HATP_MON_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpMon1().toString())) {
            hacchyuDto.setHatpMon(null);
        } else {
            hacchyuDto.setHatpMon(m011trim.getHatpMon1().toString());
        }
        // 火 = 登録データ[ HATP_TUE_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpTue1().toString())) {
            hacchyuDto.setHatpTue(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpTue(m011trim.getHatpTue1().toString());
        }
        // 水 = 登録データ[ HATP_WED_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpWed1().toString())) {
            hacchyuDto.setHatpWed(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpWed(m011trim.getHatpWed1().toString());
        }
        // 木 = 登録データ[ HATP_THU_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpThu1().toString())) {
            hacchyuDto.setHatpThu(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpThu(m011trim.getHatpThu1().toString());
        }
        // 金 = 登録データ[ HATP_FRI_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpFri1().toString())) {
            hacchyuDto.setHatpFri(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpFri(m011trim.getHatpFri1().toString());
        }
        // 土 = 登録データ[ HATP_SAT_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpSat1().toString())) {
            hacchyuDto.setHatpSat(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpSat(m011trim.getHatpSat1().toString());
        }
        // 日 = 登録データ[ HATP_SUN_1 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpSun1().toString())) {
            hacchyuDto.setHatpSun(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpSun(m011trim.getHatpSun1().toString());
        }
        hacchyuDtos.add(hacchyuDto);
        resultForm.setHacchyuArea1(hacchyuDtos);

        hacchyuDtos = new ArrayList<BinInfoHacchyuDto>();
        hacchyuDto = new BinInfoHacchyuDto();
        // 月 = 登録データ[ HATP_MON_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpMon2().toString())) {
            hacchyuDto.setHatpMon(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpMon(m011trim.getHatpMon2().toString());
        }
        // 火 = 登録データ[ HATP_TUE_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpTue2().toString())) {
            hacchyuDto.setHatpTue(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpTue(m011trim.getHatpTue2().toString());
        }
        // 水 = 登録データ[ HATP_WED_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpWed2().toString())) {
            hacchyuDto.setHatpWed(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpWed(m011trim.getHatpWed2().toString());
        }
        // 木 = 登録データ[ HATP_THU_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpThu2().toString())) {
            hacchyuDto.setHatpThu(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpThu(m011trim.getHatpThu2().toString());
        }
        // 金 = 登録データ[ HATP_FRI_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpFri2().toString())) {
            hacchyuDto.setHatpFri(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpFri(m011trim.getHatpFri2().toString());
        }
        // 土 = 登録データ[ HATP_SAT_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpSat2().toString())) {
            hacchyuDto.setHatpSat(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpSat(m011trim.getHatpSat2().toString());
        }
        // 日 = 登録データ[ HATP_SUN_2 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpSun2().toString())) {
            hacchyuDto.setHatpSun(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpSun(m011trim.getHatpSun2().toString());
        }
        hacchyuDtos.add(hacchyuDto);
        resultForm.setHacchyuArea2(hacchyuDtos);

        hacchyuDtos = new ArrayList<BinInfoHacchyuDto>();
        hacchyuDto = new BinInfoHacchyuDto();
        // 月 = 登録データ[ HATP_MON_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpMon3().toString())) {
            hacchyuDto.setHatpMon(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpMon(m011trim.getHatpMon3().toString());
        }
        // 火 = 登録データ[ HATP_TUE_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpTue3().toString())) {
            hacchyuDto.setHatpTue(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpTue(m011trim.getHatpTue3().toString());
        }
        // 水 = 登録データ[ HATP_WED_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpWed3().toString())) {
            hacchyuDto.setHatpWed(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpWed(m011trim.getHatpWed3().toString());
        }
        // 木 = 登録データ[ HATP_THU_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpThu3().toString())) {
            hacchyuDto.setHatpThu(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpThu(m011trim.getHatpThu3().toString());
        }
        // 金 = 登録データ[ HATP_FRI_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpFri3().toString())) {
            hacchyuDto.setHatpFri(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpFri(m011trim.getHatpFri3().toString());
        }
        // 土 = 登録データ[ HATP_SAT_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpSat3().toString())) {
            hacchyuDto.setHatpSat(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpSat(m011trim.getHatpSat3().toString());
        }
        // 日 = 登録データ[ HATP_SUN_3 ] ※発注パターン=="0"の場合、固定値[""]を設定
        if ("0".equals(m011trim.getHatpSun3().toString())) {
            hacchyuDto.setHatpSun(EMPTY_STRING);
        } else {
            hacchyuDto.setHatpSun(m011trim.getHatpSun3().toString());
        }
        hacchyuDtos.add(hacchyuDto);
        resultForm.setHacchyuArea3(hacchyuDtos);

        return resultForm;
    }
}
