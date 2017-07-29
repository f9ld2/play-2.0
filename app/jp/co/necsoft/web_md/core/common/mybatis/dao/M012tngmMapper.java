package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;

import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeMaster;
import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeName;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M012tngm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M012tngmExample;
import org.apache.ibatis.annotations.Param;

public interface M012tngmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(M012tngmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(M012tngmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(M012tngm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(M012tngm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<M012tngm> selectByExample(M012tngmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") M012tngm record, @Param("example") M012tngmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.M012TNGM
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") M012tngm record, @Param("example") M012tngmExample example);
    
    /**
     * 
     */
    List<CodeMaster> selectCodeMasterByExample(@Param("key") String key, @Param("grpCd") String grpCd, @Param("hakkoDay") String hakkoDay);

    List<CodeMaster> selectCodeMasterByExample2(M012tngmExample example);
}