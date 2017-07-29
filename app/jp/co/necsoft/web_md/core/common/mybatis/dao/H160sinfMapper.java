package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H160sinf;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H160sinfExample;
import org.apache.ibatis.annotations.Param;

public interface H160sinfMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int countByExample(H160sinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int deleteByExample(H160sinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int insert(H160sinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int insertSelective(H160sinf record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    List<H160sinf> selectByExample(H160sinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int updateByExampleSelective(@Param("record") H160sinf record, @Param("example") H160sinfExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H160SINF
     *
     * @mbggenerated Mon Jun 08 13:53:13 ICT 2015
     */
    int updateByExample(@Param("record") H160sinf record, @Param("example") H160sinfExample example);
}