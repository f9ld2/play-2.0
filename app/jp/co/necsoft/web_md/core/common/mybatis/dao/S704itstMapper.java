package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S704itst;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S704itstExample;
import org.apache.ibatis.annotations.Param;

public interface S704itstMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(S704itstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(S704itstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(S704itst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(S704itst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<S704itst> selectByExample(S704itstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") S704itst record, @Param("example") S704itstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S704ITST
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") S704itst record, @Param("example") S704itstExample example);
}