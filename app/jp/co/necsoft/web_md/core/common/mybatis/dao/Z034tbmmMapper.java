package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z034tbmm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z034tbmmExample;
import org.apache.ibatis.annotations.Param;

public interface Z034tbmmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int countByExample(Z034tbmmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int deleteByExample(Z034tbmmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insert(Z034tbmm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insertSelective(Z034tbmm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    List<Z034tbmm> selectByExample(Z034tbmmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExampleSelective(@Param("record") Z034tbmm record, @Param("example") Z034tbmmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z034TBMM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExample(@Param("record") Z034tbmm record, @Param("example") Z034tbmmExample example);
}