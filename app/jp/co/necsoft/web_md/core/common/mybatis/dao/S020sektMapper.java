package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S020sekt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S020sektExample;
import org.apache.ibatis.annotations.Param;

public interface S020sektMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(S020sektExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(S020sektExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(S020sekt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(S020sekt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<S020sekt> selectByExample(S020sektExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") S020sekt record, @Param("example") S020sektExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S020SEKT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") S020sekt record, @Param("example") S020sektExample example);
}