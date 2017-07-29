// /////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
// /////////////////////////////////////////////////////////////////////

/*
 * ==================================================================== 機能名称 : 共通エラーレスポンス 改版履歴 Rev. 改版年月日 改版者名 内容 1.0
 * 2014.02.04 H.Okuhara 新規作成 ===================================================================
 */
package jp.co.necsoft.web_md.core.common.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import jp.co.necsoft.web_md.core.common.CCMessageConst;
import jp.co.necsoft.web_md.core.common.mybatis.dao.C001msgfMapper;
import jp.co.necsoft.web_md.core.common.mybatis.dto.C001msgf;
import jp.co.necsoft.web_md.core.common.mybatis.dto.C001msgfExample;
import play.data.validation.ValidationError;

/**
 * 共通エラーレスポンスクラス
 * 
 */
public class ErrorResponse {

	/** c001msgfMapper */
    @Inject
    private C001msgfMapper c001msgfMapper;

    /** EMBED　タグ */
    private static final String EMBED_TAG = "<%S%>";

    /** エラー */
    private List<ErrorInfo> errors;

    /** グリッドエラー */
    private List<HashMap<String, Boolean>> gridErrors;

    /**
     * エラー情報のリストを取得する。
     * 
     * @return エラー情報のリスト
     */
    public List<ErrorInfo> getErrors() {
        return this.errors;
    }

    /**
     * エラー情報のリストをセットする。
     * 
     * @param errors
     *            エラー情報のリスト
     */
    public void setErrors(List<ErrorInfo> errors) {
        this.errors = errors;
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            エラーコード(C001MSGF.KEY)
     */
    public void addErrorInfo(String code) {
        addErrorInfo(null, code);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            入力項目名
     * @param param
     *            エラーコード(C001MSGF.KEY)
     * @return message
     *            メッセージ
     */
    public String getMessageByCode(String code, ArrayList<String> param) {

        String message = "";
        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            message = embedMessage(list.get(0).getMsg(), param);
        }

        return message;
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @return message
     *            メッセージ
     */
    public String getMessageByCode(String code) {

        String message = "";
        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            message = list.get(0).getMsg();
        }

        return message;
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param code
     *            エラーコード(C001MSGF.KEY)
     */
    public void addErrorInfo(String name, String code) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        ErrorInfo err = new ErrorInfo();
        err.setName(name);
        err.setCode(code);

        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            err.setLevel(list.get(0).getLvl());
            err.setMessage(list.get(0).getMsg());
        }

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     */
    public void addErrorInfoWithMessage(String nameMessage) {
        addErrorInfoWithMessage(null, nameMessage);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     */
    public void addErrorInfoWithMessage(String name, String nameMessage) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        ErrorInfo err = new ErrorInfo();

        if (name != null) {
            err.setName(name);
        } else {
            err.setName("");
        }

        err.setCode("");

        err.setLevel("E");
        err.setMessage(nameMessage);

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     * @param lvl
     *            レベル
     */
    public void addMessageInfo(String nameMessage, String lvl) {
        addMessageInfo(null, nameMessage, lvl);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     * @param lvl
     *            レベル
     */
    public void addMessageInfo(String name, String nameMessage, String lvl) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        ErrorInfo err = new ErrorInfo();

        if (name != null) {
            err.setName(name);
        } else {
            err.setName("");
        }

        err.setCode("");

        err.setLevel(lvl);
        err.setMessage(nameMessage);

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     * @param lvl
     *            レベル
     * @param param
     *            パラメータ
     */
    public void addMessageInfo(String nameMessage, String lvl, ArrayList<String> param) {
        addMessageInfo(null, nameMessage, lvl, param);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     * @param lvl
     *            レベル
     * @param param
     *            パラメータ
     */
    public void addMessageInfo(String name, String nameMessage, String lvl, ArrayList<String> param) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        name = embedMessage(nameMessage, param);

        ErrorInfo err = new ErrorInfo();

        if (name != null) {
            err.setName(name);
        } else {
            err.setName("");
        }

        err.setCode("");

        err.setLevel(lvl);
        err.setMessage(nameMessage);

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @param lvl
     *            レベル
     */
    public void addMessageInfoCode(String name, String code, String lvl) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        ErrorInfo err = new ErrorInfo();
        err.setName(name);
        err.setCode(code);

        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            err.setLevel(list.get(0).getLvl());
            err.setMessage(list.get(0).getMsg());

        }

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param nameMessage
     *            エラーコード(C001MSGF.KEY)
     * @param lvl
     *            レベル
     */
    public void addErrorInfoWithMessageAndLvl(String nameMessage, String lvl) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }
        ErrorInfo err = new ErrorInfo();
        err.setName("");
        err.setCode("");
        err.setLevel(lvl);
        err.setMessage(nameMessage);

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @param param
     *            パラメータ
     */
    public void addErrorInfoWithParam(String code, ArrayList<String> param) {
        addErrorInfoWithParam(null, code, param, null);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @param param
     *            パラメータ
     * @param str
     *            文字列
     */
    public void addErrorInfoWithParam(String code, ArrayList<String> param, String str) {
        addErrorInfoWithParam(null, code, param, str);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @param param
     *            パラメータ
     * @param str
     *            文字列
     */
    public void addErrorInfoWithParam(String name, String code, ArrayList<String> param, String str) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        ErrorInfo err = new ErrorInfo();
        err.setName(name);
        err.setCode(code);

        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            err.setLevel(list.get(0).getLvl());
            if (str != null) {
                err.setMessage(embedMessage(list.get(0).getMsg(), param) + str);
            } else {
                err.setMessage(embedMessage(list.get(0).getMsg(), param));
            }

        }

        errors.add(err);
    }

    /**
     * エラー情報を追加する。
     * 
     * @param name
     *            入力項目名
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @param param
     *            パラメータ
     */
    public void addErrorInfoWithParam(String name, String code, ArrayList<String> param) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        // check error is add
        if (!checkDuplicateError(name)) {
            return;
        }

        ErrorInfo err = new ErrorInfo();
        err.setName(name);
        err.setCode(code);

        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            err.setLevel(list.get(0).getLvl());
            err.setMessage(embedMessage(list.get(0).getMsg(), param));
        }

        errors.add(err);
    }

    /**
     * Embed params to string based on a format string. 
     * 
     * @param msgstr メッセージ
     * @param param パラメータ
     * @return メッセージ
     */
    private String embedMessage(String msgstr, List<String> param) {
        StringBuffer buf = new StringBuffer(msgstr);

        int lastIndex = 0;
        int count = 0;
        while (lastIndex != -1) {
            lastIndex = msgstr.indexOf(EMBED_TAG, lastIndex);
            if (lastIndex != -1) {

                int start = lastIndex;
                lastIndex += EMBED_TAG.length();
                int end = lastIndex;
                if (param.size() > count) {
                    buf.replace(start, end, param.get(count));
                }

                count++;
                lastIndex = 0;
                msgstr = buf.toString();
            }
        }

        return msgstr;
    }

    /**
     * エラー情報を追加する。
     * 
     * @param map
     *            Form#errorsの結果
     */
    public void addErrorInfo(Map<String, List<ValidationError>> map) {
        if (errors == null) {
            errors = new ArrayList<ErrorInfo>();
        }

        if (map != null) {
            for (String key : map.keySet()) {
                for (ValidationError err : map.get(key)) {
                    ErrorInfo error = new ErrorInfo();
                    String[] keys = key.split("\\.");
                    if (keys.length > 1) {
                        error.setName(keys[keys.length - 1]);
                    } else {
                        error.setName(key);
                    }

                    String message = "";
                    if ("error.min".equals(err.message())) {
                        ArrayList<String> param = new ArrayList<String>();
                        param.add(err.arguments().get(0).toString());
                        message = getMessageByCode(CCMessageConst.MSG_KEY_CC_MIN, param);
                    } else if ("error.required".equals(err.message())) {
                        message = getMessageByCode(CCMessageConst.MSG_KEY_INPUT_REQUIRED_ENTER);
                    } else if ("error.max".equals(err.message())) {
                        ArrayList<String> param = new ArrayList<String>();
                        param.add(err.arguments().get(0).toString());
                        message = getMessageByCode(CCMessageConst.MSG_KEY_CC_MAX, param);
                    } else if ("error.minLength".equals(err.message())) {
                        ArrayList<String> param = new ArrayList<String>();
                        param.add(err.arguments().get(0).toString());
                        message = getMessageByCode(CCMessageConst.MSG_KEY_CC_MIN_LENGHT, param);
                    } else if ("error.maxLength".equals(err.message())) {
                        ArrayList<String> param = new ArrayList<String>();
                        param.add(err.arguments().get(0).toString());
                        message = getMessageByCode(CCMessageConst.MSG_KEY_CC_MAX_LENGHT, param);
                    } else if ("error.decimalmax".equals(err.message())) {
                        ArrayList<String> param = new ArrayList<String>();
                        param.add(err.arguments().get(0).toString());
                        message = getMessageByCode(CCMessageConst.MSG_KEY_MIN_VALUE, param);
                    } else if ("error.decimalmin".equals(err.message())) {
                        ArrayList<String> param = new ArrayList<String>();
                        param.add(err.arguments().get(0).toString());
                        message = getMessageByCode(CCMessageConst.MSG_KEY_MAX_VALUE, param);
                    }
                    error.setLevel("E");
                    error.setMessage(message);
                    errors.add(error);

                }
            }
        }
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @return エラー
     */
    public ErrorInfo getErrorInfo(String code) {
        ErrorInfo err = null;

        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            err = new ErrorInfo();
            err.setCode(code);
            err.setLevel(list.get(0).getLvl());
            err.setMessage(list.get(0).getMsg());
        }
        return err;
    }

    /**
     * エラー情報を追加する。
     * 
     * @param code
     *            エラーコード(C001MSGF.KEY)
     * @param param
     *            パラメータ
     *  @return err エラー
     */
    public ErrorInfo getErrorInfo(String code, ArrayList<String> param) {
        ErrorInfo err = null;

        C001msgfExample example = new C001msgfExample();
        example.createCriteria().andKeyEqualTo(code);
        List<C001msgf> list = this.c001msgfMapper.selectByExample(example);
        if (list.size() > 0) {
            err = new ErrorInfo();
            err.setCode(code);
            err.setLevel(list.get(0).getLvl());
            err.setMessage(embedMessage(list.get(0).getMsg(), param));
        }
        return err;
    }

    /**
     * @return the gridErrors
     */
    public List<HashMap<String, Boolean>> getGridErrors() {
        return gridErrors;
    }

    /**
     * @param gridErrors
     *            the gridErrors to set
     */
    public void setGridErrors(List<HashMap<String, Boolean>> gridErrors) {
        this.gridErrors = gridErrors;
    }

    /**
     * Check error is available in the error list.
     * @param name name
     * @return error or not error
     */
    public boolean checkDuplicateError(String name) {

        ErrorInfo info = null;
        for (int i = 0; i < errors.size(); i++) {
            info = errors.get(i);
            if (info.getName() != null && name != null) {
                if (info.getName().trim().equals(name.trim())) {
                    return false;
                }
            }
        }
        return true;
    }
}
