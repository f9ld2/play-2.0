package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z701dmth;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Z701dmthExample;
import org.apache.ibatis.annotations.Param;

public interface Z701dmthMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int countByExample(Z701dmthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int deleteByExample(Z701dmthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insert(Z701dmth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insertSelective(Z701dmth record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    List<Z701dmth> selectByExample(Z701dmthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExampleSelective(@Param("record") Z701dmth record, @Param("example") Z701dmthExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.Z701DMTH
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExample(@Param("record") Z701dmth record, @Param("example") Z701dmthExample example);
}