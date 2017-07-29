package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T004Facingdesign;
import jp.co.necsoft.web_md.core.common.mybatis.dto.T004FacingdesignExample;
import org.apache.ibatis.annotations.Param;

public interface T004FacingdesignMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(T004FacingdesignExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(T004FacingdesignExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(T004Facingdesign record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(T004Facingdesign record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<T004Facingdesign> selectByExample(T004FacingdesignExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") T004Facingdesign record, @Param("example") T004FacingdesignExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.T004_FACINGDESIGN
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") T004Facingdesign record, @Param("example") T004FacingdesignExample example);
}