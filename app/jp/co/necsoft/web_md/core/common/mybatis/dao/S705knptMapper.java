package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S705knpt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S705knptExample;
import org.apache.ibatis.annotations.Param;

public interface S705knptMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(S705knptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(S705knptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(S705knpt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(S705knpt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<S705knpt> selectByExample(S705knptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") S705knpt record, @Param("example") S705knptExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S705KNPT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") S705knpt record, @Param("example") S705knptExample example);
}