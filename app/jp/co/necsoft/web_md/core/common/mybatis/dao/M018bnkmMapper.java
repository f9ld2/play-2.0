package jp.co.necsoft.web_md.core.common.mybatis.dao;

import java.util.List;

import jp.co.necsoft.web_md.core.common.mybatis.dto.CodeMaster;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M018bnkm;
import jp.co.necsoft.web_md.core.common.mybatis.dto.M018bnkmExample;
import org.apache.ibatis.annotations.Param;

public interface M018bnkmMapper {
	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	int countByExample(M018bnkmExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	int deleteByExample(M018bnkmExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	int insert(M018bnkm record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	int insertSelective(M018bnkm record);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	List<M018bnkm> selectByExample(M018bnkmExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	int updateByExampleSelective(@Param("record") M018bnkm record, @Param("example") M018bnkmExample example);

	/**
	 * This method was generated by MyBatis Generator.
	 * This method corresponds to the database table WEBMD.M018BNKM
	 *
     * @mbggenerated Mon Jun 08 13:53:14 ICT 2015
	 */
	int updateByExample(@Param("record") M018bnkm record, @Param("example") M018bnkmExample example);

	/**
	*
	*/
	List<CodeMaster> selectCodeMasterByExample(M018bnkmExample example);
}