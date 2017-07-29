package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T007erfl;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T007erflExample;
import org.apache.ibatis.annotations.Param;

public interface T007erflMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int countByExample(T007erflExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int deleteByExample(T007erflExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insert(T007erfl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int insertSelective(T007erfl record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    List<T007erfl> selectByExample(T007erflExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExampleSelective(@Param("record") T007erfl record, @Param("example") T007erflExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T007ERFL
     *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
     */
    int updateByExample(@Param("record") T007erfl record, @Param("example") T007erflExample example);
}