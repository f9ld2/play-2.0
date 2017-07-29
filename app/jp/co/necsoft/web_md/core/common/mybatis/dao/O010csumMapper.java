package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.O010csum;
import jp.co.necsoft.web_md.core.common.mybatis.dto.O010csumExample;
import org.apache.ibatis.annotations.Param;

public interface O010csumMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(O010csumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(O010csumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(O010csum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(O010csum record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<O010csum> selectByExample(O010csumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") O010csum record, @Param("example") O010csumExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O010CSUM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") O010csum record, @Param("example") O010csumExample example);
}