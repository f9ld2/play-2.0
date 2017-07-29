package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Y013tssb;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Y013tssbExample;
import org.apache.ibatis.annotations.Param;

public interface Y013tssbMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    int countByExample(Y013tssbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    int deleteByExample(Y013tssbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    int insert(Y013tssb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    int insertSelective(Y013tssb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    List<Y013tssb> selectByExample(Y013tssbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    int updateByExampleSelective(@Param("record") Y013tssb record, @Param("example") Y013tssbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y013TSSB
     *
     * @mbggenerated Thu Jan 22 09:41:43 ICT 2015
     */
    int updateByExample(@Param("record") Y013tssb record, @Param("example") Y013tssbExample example);
}