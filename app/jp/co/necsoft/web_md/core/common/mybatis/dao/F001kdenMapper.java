package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.F001kden;
import jp.co.necsoft.web_md.core.common.mybatis.dto.F001kdenExample;
import org.apache.ibatis.annotations.Param;

public interface F001kdenMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(F001kdenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(F001kdenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(F001kden record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(F001kden record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<F001kden> selectByExample(F001kdenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") F001kden record, @Param("example") F001kdenExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.F001KDEN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") F001kden record, @Param("example") F001kdenExample example);
}