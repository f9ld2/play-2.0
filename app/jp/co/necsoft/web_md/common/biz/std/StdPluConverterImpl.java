///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : PLUコード変換
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.11.12   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.common.biz.std;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.biz.IPluConverter;
import jp.co.necsoft.web_md.common.constants.CodeConst;
import jp.co.necsoft.web_md.core.common.mybatis.dao.M007kijmMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M007kijmExample;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * @author 1134140119159
 *
 */
public class StdPluConverterImpl implements IPluConverter {
	// インストアコードの桁数
	private static final int LEN_INSTORE_CODE = 8;
	// 計量コードの桁数
	private static final int LEN_KEIRYO_CODE = 8;
	// 代表コードの桁数
	private static final int LEN_DAIHYO_CODE = 9;
	// NON-PLU 販売価格の桁数(2～8)
	private static final int LEN_NONPLU_PRICE = 5;
	// NON-PLU 開始コード(2桁)
	private static final String START_NONPLU = "26";
	
	private static final int LEN_PLU_CODE = 13;

	// PLU区分
	private static final String PLU_KBN_JAN			= "00";
	private static final String PLU_KBN_UPC			= "01";
	private static final String PLU_KBN_INSTORE	= "02";
	private static final String PLU_KBN_EAN			= "03";
	private static final String PLU_KBN_KEIRYO		= "04";
	private static final String PLU_KBN_NONPLU		= "05";
	private static final String PLU_KBN_DAIHYO 	= "06";
	
	private static final String ZERO2 = "00";
	private static final String ZERO5 = "00000";
	private static final String ZERO7 = "0000000";
	
    /** M007kijmMapper */
    @Inject
    private M007kijmMapper m007kijmMapper;

	@Override
    public String toDispCode(String sDbCode, String sDate) {
    	String trimCode = sDbCode.trim();
        String sRet = trimCode;
        String strZero;
        
        // 日付を省略した場合はシステム日付を使う
        if (StringUtils.isEmpty(sDate)) {
        	DateTime dt = new DateTime();
            sDate = dt.toString(ISODateTimeFormat.basicDate());
        }

        // PLU区分取得
        String sPluKbn = getPluKbn(sDbCode, sDate);
        if (sPluKbn == null) {
        	return sRet;
        }

        // 00:JAN（13）
        if (sPluKbn.equals(PLU_KBN_JAN)) {
            // JAN（8）
        	strZero = ZERO5;
        	int st = strZero.length();
            if (trimCode.substring(0, st).equals(strZero)) {
                sRet = trimCode.substring(st, LEN_PLU_CODE);
            } else {
                sRet = trimCode;
            }

        // 01:UPC 頭"00"の場合（11）、それ以外（12）
        } else
        if (sPluKbn.equals(PLU_KBN_UPC)) {

            // 頭"0000000"の場合（6）
        	strZero = ZERO7;
        	int st = strZero.length();
            if (trimCode.substring(0, st).equals(strZero)) {
                sRet = trimCode.substring(st, LEN_PLU_CODE);
            } else {
                // 頭"00000"の場合（8）
            	strZero = ZERO5;
            	st = strZero.length();
                if (trimCode.substring(0, st).equals(strZero)) {
                    sRet = trimCode.substring(st, LEN_PLU_CODE);
                } else {
                    // 頭"00"の場合（11）
                	strZero = ZERO2;
                	st = strZero.length();
                    if (trimCode.substring(0, st).equals(strZero)) {
                        sRet = trimCode.substring(st, LEN_PLU_CODE);

                        // それ以外（12）
                    } else {
                        sRet = trimCode.substring(1, LEN_PLU_CODE);
                    }
                }
            }

        // 02:ｲﾝｽﾄｱ（8）
//        } else
//        if (sPluKbn.equals(PLU_KBN_INSTORE)) {
//        	int st = LEN_PLU_CODE - LEN_INSTORE_CODE;
//            sRet = trimCode.substring(st, LEN_PLU_CODE);
//
        // 03:EAN 頭"00000"の場合（8）、それ以外（13）
        } else
        if (sPluKbn.equals(PLU_KBN_EAN)) {
        	strZero = ZERO5;
        	int st = strZero.length();
            if (trimCode.substring(0, st).equals(strZero)) {
                sRet = trimCode.substring(st, LEN_PLU_CODE);
            } else {
                sRet = trimCode;
            }

        // 04:計量（8）
        } else
        if (sPluKbn.equals(PLU_KBN_KEIRYO)) {
        	int st = LEN_PLU_CODE - LEN_KEIRYO_CODE;
            sRet = trimCode.substring(st, LEN_PLU_CODE);

        // 05:NONｰPLU（13）
        } else
        if (sPluKbn.equals(PLU_KBN_NONPLU)) {
            sRet = trimCode;

        // 06:代表中分類コード（9）
        } else
        if (sPluKbn.equals(PLU_KBN_DAIHYO)) {
        	int st = LEN_PLU_CODE - LEN_DAIHYO_CODE;
            sRet = trimCode.substring(st, LEN_PLU_CODE);

        // その他
        } else {
            sRet = trimCode;
        }

        return sRet;
    }

	@Override
    public String getPluKbn(String sDbCode, String sDate) {
        if (StringUtils.isEmpty(sDbCode)) {
            return null;
        }
        // DBの項目定義の長さと異なっている場合は補完する
        if (sDbCode.length() != CodeConst.LEN_JAN_CD) {
        	sDbCode = toDbCode(sDbCode);
        }
        if (StringUtils.isEmpty(sDate)) {
            // 日付を省略した場合はシステム日付を使う
            if (StringUtils.isEmpty(sDate)) {
            	DateTime dt = new DateTime();
                sDate = dt.toString(ISODateTimeFormat.basicDate());
            }
        }

        // 基本情報マスタ検索オブジェクト作成
        M007kijmExample example = new M007kijmExample();
        example.createCriteria().andJanCdEqualTo(sDbCode);
        example.setSearchDate(sDate);

        List<M007kijm> result = m007kijmMapper.selectByExample(example);

        if (result.size() == 0) {
        	return null;
        }
        return  StringUtils.trim(result.get(0).getShnCdPluKbn());
    }

	@Override
    public String toDbCode(String sDispCode) {
    	String sJanCd = sDispCode.trim();

    	int len = sJanCd.length();
    	switch (len) {
    	case 6:
    	case 7:
    	case 8:
    	case 9:
    	case 11:
    	case 12:
    	case 13:
    	case 14:
    		break;
    	default:
    		// そのまま返す
    		return sDispCode;
    	}

        // NON-PLUのときは販売価格とチェックディジットの桁を"0"にする
        if (isNonPlu(sJanCd)) {
        	int itemCdLen = LEN_PLU_CODE - LEN_NONPLU_PRICE - 1;
        	sJanCd = sJanCd.substring(0, itemCdLen);
            sJanCd = StringUtils.rightPad(sJanCd, LEN_PLU_CODE, '0');
        }

        // 13桁に満たない場合は、前ZEROを付加して13桁に変換
        sJanCd = StringUtils.leftPad(sJanCd, LEN_PLU_CODE, '0');

        // 13桁の場合は、スペースを先頭に付加する
        sJanCd = StringUtils.leftPad(sJanCd, LEN_PLU_CODE + 1);

        return sJanCd;
    }

	@Override
	public boolean isNonPlu(String code) {
    	if (StringUtils.isEmpty(code)) {
    		return false;
    	}
    	if (code.length() != LEN_PLU_CODE) {
    		return false;
    	}
    	int len = START_NONPLU.length();
    	String head2 = code.substring(0, len);
    	
    	return (head2.equals(START_NONPLU));
	}
	
	@Override
	public int getNonPluPrice(String code) {
    	if (isNonPlu(code)) {
        	int st = LEN_PLU_CODE - LEN_NONPLU_PRICE - 1;
        	int ed = st + LEN_NONPLU_PRICE;
        	String sPrice = code.substring(st, ed);
    		return NumberUtils.toInt(sPrice);
    	} else {
    		return -1;
    	}
	}
}
