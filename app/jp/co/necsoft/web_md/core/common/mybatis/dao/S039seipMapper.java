package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S039seip;
import jp.co.necsoft.web_md.core.common.mybatis.dto.S039seipExample;
import org.apache.ibatis.annotations.Param;

public interface S039seipMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(S039seipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(S039seipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(S039seip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(S039seip record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<S039seip> selectByExample(S039seipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") S039seip record, @Param("example") S039seipExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.S039SEIP
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") S039seip record, @Param("example") S039seipExample example);
}