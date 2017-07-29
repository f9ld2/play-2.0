package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X703exmn;
import jp.co.necsoft.web_md.core.common.mybatis.dto.X703exmnExample;
import org.apache.ibatis.annotations.Param;

public interface X703exmnMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int countByExample(X703exmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int deleteByExample(X703exmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insert(X703exmn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int insertSelective(X703exmn record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    List<X703exmn> selectByExample(X703exmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExampleSelective(@Param("record") X703exmn record, @Param("example") X703exmnExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.X703EXMN
     *
     * @mbggenerated Mon Jun 08 13:53:15 ICT 2015
     */
    int updateByExample(@Param("record") X703exmn record, @Param("example") X703exmnExample example);
}