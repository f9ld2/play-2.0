package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S028tgkp;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S028tgkpExample;
import org.apache.ibatis.annotations.Param;

public interface S028tgkpMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int countByExample(S028tgkpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int deleteByExample(S028tgkpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int insert(S028tgkp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int insertSelective(S028tgkp record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    List<S028tgkp> selectByExample(S028tgkpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int updateByExampleSelective(@Param("record") S028tgkp record, @Param("example") S028tgkpExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S028TGKP
     *
     * @mbggenerated Wed Jan 22 09:53:11 JST 2014
     */
    int updateByExample(@Param("record") S028tgkp record, @Param("example") S028tgkpExample example);
}