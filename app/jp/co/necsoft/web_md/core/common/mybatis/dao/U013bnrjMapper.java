package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U013bnrj;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U013bnrjExample;
import org.apache.ibatis.annotations.Param;

public interface U013bnrjMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int countByExample(U013bnrjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int deleteByExample(U013bnrjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insert(U013bnrj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insertSelective(U013bnrj record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    List<U013bnrj> selectByExample(U013bnrjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExampleSelective(@Param("record") U013bnrj record, @Param("example") U013bnrjExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U013BNRJ
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExample(@Param("record") U013bnrj record, @Param("example") U013bnrjExample example);
}