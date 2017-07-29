package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K006ssan;
import jp.co.necsoft.web_md.core.common.mybatis.dto.K006ssanExample;
import org.apache.ibatis.annotations.Param;

public interface K006ssanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(K006ssanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(K006ssanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(K006ssan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(K006ssan record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<K006ssan> selectByExample(K006ssanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") K006ssan record, @Param("example") K006ssanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.K006SSAN
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") K006ssan record, @Param("example") K006ssanExample example);
}