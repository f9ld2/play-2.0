package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T003kkok;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T003kkokExample;
import org.apache.ibatis.annotations.Param;

public interface T003kkokMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(T003kkokExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(T003kkokExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(T003kkok record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(T003kkok record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<T003kkok> selectByExample(T003kkokExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") T003kkok record, @Param("example") T003kkokExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T003KKOK
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") T003kkok record, @Param("example") T003kkokExample example);
}