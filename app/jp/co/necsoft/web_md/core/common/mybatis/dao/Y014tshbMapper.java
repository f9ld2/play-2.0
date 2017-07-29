package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Y014tshb;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Y014tshbExample;
import org.apache.ibatis.annotations.Param;

public interface Y014tshbMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    int countByExample(Y014tshbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    int deleteByExample(Y014tshbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    int insert(Y014tshb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    int insertSelective(Y014tshb record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    List<Y014tshb> selectByExample(Y014tshbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    int updateByExampleSelective(@Param("record") Y014tshb record, @Param("example") Y014tshbExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.Y014TSHB
     *
     * @mbggenerated Thu Jan 22 09:41:57 ICT 2015
     */
    int updateByExample(@Param("record") Y014tshb record, @Param("example") Y014tshbExample example);
}