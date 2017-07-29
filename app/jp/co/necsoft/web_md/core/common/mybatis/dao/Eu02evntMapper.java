package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Eu02evnt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Eu02evntExample;
import org.apache.ibatis.annotations.Param;

public interface Eu02evntMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    int countByExample(Eu02evntExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    int deleteByExample(Eu02evntExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    int insert(Eu02evnt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    int insertSelective(Eu02evnt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    List<Eu02evnt> selectByExample(Eu02evntExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    int updateByExampleSelective(@Param("record") Eu02evnt record, @Param("example") Eu02evntExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.EU02EVNT
     *
     * @mbggenerated Mon Sep 15 11:01:46 ICT 2014
     */
    int updateByExample(@Param("record") Eu02evnt record, @Param("example") Eu02evntExample example);
}