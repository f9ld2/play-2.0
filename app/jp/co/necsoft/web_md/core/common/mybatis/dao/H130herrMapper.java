package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H130herr;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H130herrExample;
import org.apache.ibatis.annotations.Param;

public interface H130herrMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int countByExample(H130herrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int deleteByExample(H130herrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int insert(H130herr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int insertSelective(H130herr record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    List<H130herr> selectByExample(H130herrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int updateByExampleSelective(@Param("record") H130herr record, @Param("example") H130herrExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H130HERR
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int updateByExample(@Param("record") H130herr record, @Param("example") H130herrExample example);
}