package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.B112hbmj;
import jp.co.necsoft.web_md.core.common.mybatis.dto.B112hbmjExample;
import org.apache.ibatis.annotations.Param;

public interface B112hbmjMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(B112hbmjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(B112hbmjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(B112hbmj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(B112hbmj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<B112hbmj> selectByExample(B112hbmjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") B112hbmj record, @Param("example") B112hbmjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.B112HBMJ
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") B112hbmj record, @Param("example") B112hbmjExample example);
}