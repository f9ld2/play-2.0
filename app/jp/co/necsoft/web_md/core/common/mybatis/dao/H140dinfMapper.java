package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H140dinf;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H140dinfExample;
import org.apache.ibatis.annotations.Param;

public interface H140dinfMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int countByExample(H140dinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int deleteByExample(H140dinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int insert(H140dinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int insertSelective(H140dinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    List<H140dinf> selectByExample(H140dinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int updateByExampleSelective(@Param("record") H140dinf record, @Param("example") H140dinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H140DINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int updateByExample(@Param("record") H140dinf record, @Param("example") H140dinfExample example);
}