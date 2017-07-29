package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.O011tsum;
import jp.co.necsoft.web_md.core.common.mybatis.dto.O011tsumExample;
import org.apache.ibatis.annotations.Param;

public interface O011tsumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(O011tsumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(O011tsumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(O011tsum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(O011tsum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<O011tsum> selectByExample(O011tsumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") O011tsum record, @Param("example") O011tsumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O011TSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") O011tsum record, @Param("example") O011tsumExample example);
}