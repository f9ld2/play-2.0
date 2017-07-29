package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z009eobd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z009eobdExample;
import org.apache.ibatis.annotations.Param;

public interface Z009eobdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(Z009eobdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(Z009eobdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(Z009eobd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(Z009eobd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<Z009eobd> selectByExample(Z009eobdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") Z009eobd record, @Param("example") Z009eobdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z009EOBD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") Z009eobd record, @Param("example") Z009eobdExample example);
}