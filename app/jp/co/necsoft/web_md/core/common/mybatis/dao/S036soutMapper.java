package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S036sout;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S036soutExample;
import org.apache.ibatis.annotations.Param;

public interface S036soutMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(S036soutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(S036soutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(S036sout record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(S036sout record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<S036sout> selectByExample(S036soutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") S036sout record, @Param("example") S036soutExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S036SOUT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") S036sout record, @Param("example") S036soutExample example);
}