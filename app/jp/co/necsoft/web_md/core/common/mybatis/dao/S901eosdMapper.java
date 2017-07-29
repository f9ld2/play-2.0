package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S901eosd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S901eosdExample;
import org.apache.ibatis.annotations.Param;

public interface S901eosdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(S901eosdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(S901eosdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(S901eosd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(S901eosd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<S901eosd> selectByExample(S901eosdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") S901eosd record, @Param("example") S901eosdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S901EOSD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") S901eosd record, @Param("example") S901eosdExample example);
}