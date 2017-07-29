package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.N009simd;
import jp.co.necsoft.web_md.core.common.mybatis.dto.N009simdExample;
import org.apache.ibatis.annotations.Param;

public interface N009simdMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(N009simdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(N009simdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(N009simd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(N009simd record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<N009simd> selectByExample(N009simdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") N009simd record, @Param("example") N009simdExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.N009SIMD
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") N009simd record, @Param("example") N009simdExample example);
}