package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H110denm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.H110denmExample;
import org.apache.ibatis.annotations.Param;

public interface H110denmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    int countByExample(H110denmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    int deleteByExample(H110denmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    int insert(H110denm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    int insertSelective(H110denm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    List<H110denm> selectByExample(H110denmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    int updateByExampleSelective(@Param("record") H110denm record, @Param("example") H110denmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMDTEST.H110DENM
     *
     * @mbggenerated Thu Apr 09 09:38:05 ICT 2015
     */
    int updateByExample(@Param("record") H110denm record, @Param("example") H110denmExample example);
}