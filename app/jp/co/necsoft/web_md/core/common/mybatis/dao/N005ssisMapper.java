package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.N005ssis;
import jp.co.necsoft.web_md.core.common.mybatis.dto.N005ssisExample;
import org.apache.ibatis.annotations.Param;

public interface N005ssisMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(N005ssisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(N005ssisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(N005ssis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(N005ssis record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<N005ssis> selectByExample(N005ssisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") N005ssis record, @Param("example") N005ssisExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N005SSIS
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") N005ssis record, @Param("example") N005ssisExample example);
}