package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T009kkmy;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T009kkmyExample;
import org.apache.ibatis.annotations.Param;

public interface T009kkmyMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(T009kkmyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(T009kkmyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(T009kkmy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(T009kkmy record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<T009kkmy> selectByExample(T009kkmyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") T009kkmy record, @Param("example") T009kkmyExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T009KKMY
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") T009kkmy record, @Param("example") T009kkmyExample example);
}