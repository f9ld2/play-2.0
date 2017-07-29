package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Sc78ymdm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.Sc78ymdmExample;
import org.apache.ibatis.annotations.Param;

public interface Sc78ymdmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int countByExample(Sc78ymdmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int deleteByExample(Sc78ymdmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insert(Sc78ymdm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int insertSelective(Sc78ymdm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    List<Sc78ymdm> selectByExample(Sc78ymdmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExampleSelective(@Param("record") Sc78ymdm record, @Param("example") Sc78ymdmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC78YMDM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    int updateByExample(@Param("record") Sc78ymdm record, @Param("example") Sc78ymdmExample example);
}