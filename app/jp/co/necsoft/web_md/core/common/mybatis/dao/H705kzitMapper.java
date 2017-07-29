package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H705kzit;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H705kzitExample;
import org.apache.ibatis.annotations.Param;

public interface H705kzitMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(H705kzitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(H705kzitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(H705kzit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(H705kzit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<H705kzit> selectByExample(H705kzitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") H705kzit record, @Param("example") H705kzitExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H705KZIT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") H705kzit record, @Param("example") H705kzitExample example);
}