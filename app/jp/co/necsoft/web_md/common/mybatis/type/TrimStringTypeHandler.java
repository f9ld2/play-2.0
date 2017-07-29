///////////////////////////////////////////////////////////////////////
// Copyright(C) 2015 NEC Solution Innovators, Ltd. All Rights Reserved.
///////////////////////////////////////////////////////////////////////

/* ====================================================================
 * 機能名称 : Trim String Type Handler
 * 改版履歴
 * Rev.  改版年月日   改版者名       内容
 * 1.0   2014.02.04   H.Okuhara      新規作成
 * =================================================================== */
package jp.co.necsoft.web_md.common.mybatis.type;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.necsoft.web_md.core.common.utils.CCStringUtil;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

/**
 * Trim String Type Handler
 *
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
public class TrimStringTypeHandler extends BaseTypeHandler<String> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
            throws SQLException {
        if (parameter == null || "".equals(parameter.trim())) {
            parameter = " ";
        }
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        if (rs.getString(columnName) == null) {
            return "";
        }
        return CCStringUtil.trimRight(rs.getString(columnName));
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        if (rs.getString(columnIndex) == null) {
            return "";
        }
        return CCStringUtil.trimRight(rs.getString(columnIndex));
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return CCStringUtil.trimRight(cs.getString(columnIndex));
    }
}
