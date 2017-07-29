package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S701sihp;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S701sihpExample;
import org.apache.ibatis.annotations.Param;

public interface S701sihpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(S701sihpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(S701sihpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(S701sihp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(S701sihp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<S701sihp> selectByExample(S701sihpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") S701sihp record, @Param("example") S701sihpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S701SIHP
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") S701sihp record, @Param("example") S701sihpExample example);
}