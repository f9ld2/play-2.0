package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.HashMap;
import java.util.List;

import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeMaster;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M006tenmExample;

import org.apache.ibatis.annotations.Param;

public interface M006tenmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(M006tenmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(M006tenmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(M006tenm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(M006tenm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<M006tenm> selectByExample(M006tenmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") M006tenm record, @Param("example") M006tenmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M006TENM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") M006tenm record, @Param("example") M006tenmExample example);
    
    /**
    *
    */
    List<CodeMaster> selectCodeMasterByExample(M006tenmExample example);
    
    /**
     * 
     */
    List<CodeMaster> selectTenCdsByKaisyaJigyobuHakkoDay(HashMap<String, String> params);
    
}