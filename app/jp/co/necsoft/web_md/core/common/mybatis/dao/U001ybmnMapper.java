package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U001ybmn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.U001ybmnExample;
import org.apache.ibatis.annotations.Param;

public interface U001ybmnMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(U001ybmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(U001ybmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(U001ybmn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(U001ybmn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<U001ybmn> selectByExample(U001ybmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") U001ybmn record, @Param("example") U001ybmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.U001YBMN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") U001ybmn record, @Param("example") U001ybmnExample example);
}