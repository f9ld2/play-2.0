package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X003gmnm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X003gmnmExample;
import org.apache.ibatis.annotations.Param;

public interface X003gmnmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int countByExample(X003gmnmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int deleteByExample(X003gmnmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insert(X003gmnm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insertSelective(X003gmnm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    List<X003gmnm> selectByExample(X003gmnmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExampleSelective(@Param("record") X003gmnm record, @Param("example") X003gmnmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X003GMNM
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExample(@Param("record") X003gmnm record, @Param("example") X003gmnmExample example);
}