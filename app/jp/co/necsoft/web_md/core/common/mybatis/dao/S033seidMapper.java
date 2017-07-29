package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S033seid;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S033seidExample;
import org.apache.ibatis.annotations.Param;

public interface S033seidMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(S033seidExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(S033seidExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(S033seid record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(S033seid record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<S033seid> selectByExample(S033seidExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") S033seid record, @Param("example") S033seidExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S033SEID
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") S033seid record, @Param("example") S033seidExample example);
}