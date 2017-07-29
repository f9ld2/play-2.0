package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S008bhkt;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S008bhktExample;
import org.apache.ibatis.annotations.Param;

public interface S008bhktMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(S008bhktExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(S008bhktExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(S008bhkt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(S008bhkt record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<S008bhkt> selectByExample(S008bhktExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") S008bhkt record, @Param("example") S008bhktExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S008BHKT
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") S008bhkt record, @Param("example") S008bhktExample example);
}