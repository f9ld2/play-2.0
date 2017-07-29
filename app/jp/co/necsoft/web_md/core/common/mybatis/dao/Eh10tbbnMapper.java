package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Eh10tbbn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Eh10tbbnExample;
import org.apache.ibatis.annotations.Param;

public interface Eh10tbbnMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int countByExample(Eh10tbbnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int deleteByExample(Eh10tbbnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int insert(Eh10tbbn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int insertSelective(Eh10tbbn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    List<Eh10tbbn> selectByExample(Eh10tbbnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int updateByExampleSelective(@Param("record") Eh10tbbn record, @Param("example") Eh10tbbnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.EH10TBBN
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int updateByExample(@Param("record") Eh10tbbn record, @Param("example") Eh10tbbnExample example);
}