package jp.co.necsoft.web_md.core.common.mybatis.dto;

import java.util.ArrayList;
import java.util.List;

public class H190santExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public H190santExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andShnCdIsNull() {
            addCriterion("SHN_CD is null");
            return (Criteria) this;
        }

        public Criteria andShnCdIsNotNull() {
            addCriterion("SHN_CD is not null");
            return (Criteria) this;
        }

        public Criteria andShnCdEqualTo(String value) {
            addCriterion("SHN_CD =", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdNotEqualTo(String value) {
            addCriterion("SHN_CD <>", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdGreaterThan(String value) {
            addCriterion("SHN_CD >", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdGreaterThanOrEqualTo(String value) {
            addCriterion("SHN_CD >=", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdLessThan(String value) {
            addCriterion("SHN_CD <", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdLessThanOrEqualTo(String value) {
            addCriterion("SHN_CD <=", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdLike(String value) {
            addCriterion("SHN_CD like", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdNotLike(String value) {
            addCriterion("SHN_CD not like", value, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdIn(List<String> values) {
            addCriterion("SHN_CD in", values, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdNotIn(List<String> values) {
            addCriterion("SHN_CD not in", values, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdBetween(String value1, String value2) {
            addCriterion("SHN_CD between", value1, value2, "shnCd");
            return (Criteria) this;
        }

        public Criteria andShnCdNotBetween(String value1, String value2) {
            addCriterion("SHN_CD not between", value1, value2, "shnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdIsNull() {
            addCriterion("BMN_CD is null");
            return (Criteria) this;
        }

        public Criteria andBmnCdIsNotNull() {
            addCriterion("BMN_CD is not null");
            return (Criteria) this;
        }

        public Criteria andBmnCdEqualTo(String value) {
            addCriterion("BMN_CD =", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdNotEqualTo(String value) {
            addCriterion("BMN_CD <>", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdGreaterThan(String value) {
            addCriterion("BMN_CD >", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdGreaterThanOrEqualTo(String value) {
            addCriterion("BMN_CD >=", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdLessThan(String value) {
            addCriterion("BMN_CD <", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdLessThanOrEqualTo(String value) {
            addCriterion("BMN_CD <=", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdLike(String value) {
            addCriterion("BMN_CD like", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdNotLike(String value) {
            addCriterion("BMN_CD not like", value, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdIn(List<String> values) {
            addCriterion("BMN_CD in", values, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdNotIn(List<String> values) {
            addCriterion("BMN_CD not in", values, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdBetween(String value1, String value2) {
            addCriterion("BMN_CD between", value1, value2, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andBmnCdNotBetween(String value1, String value2) {
            addCriterion("BMN_CD not between", value1, value2, "bmnCd");
            return (Criteria) this;
        }

        public Criteria andSantiNmIsNull() {
            addCriterion("SANTI_NM is null");
            return (Criteria) this;
        }

        public Criteria andSantiNmIsNotNull() {
            addCriterion("SANTI_NM is not null");
            return (Criteria) this;
        }

        public Criteria andSantiNmEqualTo(String value) {
            addCriterion("SANTI_NM =", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmNotEqualTo(String value) {
            addCriterion("SANTI_NM <>", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmGreaterThan(String value) {
            addCriterion("SANTI_NM >", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmGreaterThanOrEqualTo(String value) {
            addCriterion("SANTI_NM >=", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmLessThan(String value) {
            addCriterion("SANTI_NM <", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmLessThanOrEqualTo(String value) {
            addCriterion("SANTI_NM <=", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmLike(String value) {
            addCriterion("SANTI_NM like", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmNotLike(String value) {
            addCriterion("SANTI_NM not like", value, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmIn(List<String> values) {
            addCriterion("SANTI_NM in", values, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmNotIn(List<String> values) {
            addCriterion("SANTI_NM not in", values, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmBetween(String value1, String value2) {
            addCriterion("SANTI_NM between", value1, value2, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiNmNotBetween(String value1, String value2) {
            addCriterion("SANTI_NM not between", value1, value2, "santiNm");
            return (Criteria) this;
        }

        public Criteria andSantiCdIsNull() {
            addCriterion("SANTI_CD is null");
            return (Criteria) this;
        }

        public Criteria andSantiCdIsNotNull() {
            addCriterion("SANTI_CD is not null");
            return (Criteria) this;
        }

        public Criteria andSantiCdEqualTo(String value) {
            addCriterion("SANTI_CD =", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdNotEqualTo(String value) {
            addCriterion("SANTI_CD <>", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdGreaterThan(String value) {
            addCriterion("SANTI_CD >", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdGreaterThanOrEqualTo(String value) {
            addCriterion("SANTI_CD >=", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdLessThan(String value) {
            addCriterion("SANTI_CD <", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdLessThanOrEqualTo(String value) {
            addCriterion("SANTI_CD <=", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdLike(String value) {
            addCriterion("SANTI_CD like", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdNotLike(String value) {
            addCriterion("SANTI_CD not like", value, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdIn(List<String> values) {
            addCriterion("SANTI_CD in", values, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdNotIn(List<String> values) {
            addCriterion("SANTI_CD not in", values, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdBetween(String value1, String value2) {
            addCriterion("SANTI_CD between", value1, value2, "santiCd");
            return (Criteria) this;
        }

        public Criteria andSantiCdNotBetween(String value1, String value2) {
            addCriterion("SANTI_CD not between", value1, value2, "santiCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdIsNull() {
            addCriterion("CMN_TANTO_CD is null");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdIsNotNull() {
            addCriterion("CMN_TANTO_CD is not null");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdEqualTo(String value) {
            addCriterion("CMN_TANTO_CD =", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdNotEqualTo(String value) {
            addCriterion("CMN_TANTO_CD <>", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdGreaterThan(String value) {
            addCriterion("CMN_TANTO_CD >", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdGreaterThanOrEqualTo(String value) {
            addCriterion("CMN_TANTO_CD >=", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdLessThan(String value) {
            addCriterion("CMN_TANTO_CD <", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdLessThanOrEqualTo(String value) {
            addCriterion("CMN_TANTO_CD <=", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdLike(String value) {
            addCriterion("CMN_TANTO_CD like", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdNotLike(String value) {
            addCriterion("CMN_TANTO_CD not like", value, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdIn(List<String> values) {
            addCriterion("CMN_TANTO_CD in", values, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdNotIn(List<String> values) {
            addCriterion("CMN_TANTO_CD not in", values, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdBetween(String value1, String value2) {
            addCriterion("CMN_TANTO_CD between", value1, value2, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTantoCdNotBetween(String value1, String value2) {
            addCriterion("CMN_TANTO_CD not between", value1, value2, "cmnTantoCd");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdIsNull() {
            addCriterion("CMN_TERM_ID is null");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdIsNotNull() {
            addCriterion("CMN_TERM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdEqualTo(String value) {
            addCriterion("CMN_TERM_ID =", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdNotEqualTo(String value) {
            addCriterion("CMN_TERM_ID <>", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdGreaterThan(String value) {
            addCriterion("CMN_TERM_ID >", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdGreaterThanOrEqualTo(String value) {
            addCriterion("CMN_TERM_ID >=", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdLessThan(String value) {
            addCriterion("CMN_TERM_ID <", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdLessThanOrEqualTo(String value) {
            addCriterion("CMN_TERM_ID <=", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdLike(String value) {
            addCriterion("CMN_TERM_ID like", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdNotLike(String value) {
            addCriterion("CMN_TERM_ID not like", value, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdIn(List<String> values) {
            addCriterion("CMN_TERM_ID in", values, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdNotIn(List<String> values) {
            addCriterion("CMN_TERM_ID not in", values, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdBetween(String value1, String value2) {
            addCriterion("CMN_TERM_ID between", value1, value2, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnTermIdNotBetween(String value1, String value2) {
            addCriterion("CMN_TERM_ID not between", value1, value2, "cmnTermId");
            return (Criteria) this;
        }

        public Criteria andCmnInsddIsNull() {
            addCriterion("CMN_INSDD is null");
            return (Criteria) this;
        }

        public Criteria andCmnInsddIsNotNull() {
            addCriterion("CMN_INSDD is not null");
            return (Criteria) this;
        }

        public Criteria andCmnInsddEqualTo(String value) {
            addCriterion("CMN_INSDD =", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddNotEqualTo(String value) {
            addCriterion("CMN_INSDD <>", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddGreaterThan(String value) {
            addCriterion("CMN_INSDD >", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddGreaterThanOrEqualTo(String value) {
            addCriterion("CMN_INSDD >=", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddLessThan(String value) {
            addCriterion("CMN_INSDD <", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddLessThanOrEqualTo(String value) {
            addCriterion("CMN_INSDD <=", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddLike(String value) {
            addCriterion("CMN_INSDD like", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddNotLike(String value) {
            addCriterion("CMN_INSDD not like", value, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddIn(List<String> values) {
            addCriterion("CMN_INSDD in", values, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddNotIn(List<String> values) {
            addCriterion("CMN_INSDD not in", values, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddBetween(String value1, String value2) {
            addCriterion("CMN_INSDD between", value1, value2, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnInsddNotBetween(String value1, String value2) {
            addCriterion("CMN_INSDD not between", value1, value2, "cmnInsdd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddIsNull() {
            addCriterion("CMN_UPDDD is null");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddIsNotNull() {
            addCriterion("CMN_UPDDD is not null");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddEqualTo(String value) {
            addCriterion("CMN_UPDDD =", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddNotEqualTo(String value) {
            addCriterion("CMN_UPDDD <>", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddGreaterThan(String value) {
            addCriterion("CMN_UPDDD >", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddGreaterThanOrEqualTo(String value) {
            addCriterion("CMN_UPDDD >=", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddLessThan(String value) {
            addCriterion("CMN_UPDDD <", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddLessThanOrEqualTo(String value) {
            addCriterion("CMN_UPDDD <=", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddLike(String value) {
            addCriterion("CMN_UPDDD like", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddNotLike(String value) {
            addCriterion("CMN_UPDDD not like", value, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddIn(List<String> values) {
            addCriterion("CMN_UPDDD in", values, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddNotIn(List<String> values) {
            addCriterion("CMN_UPDDD not in", values, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddBetween(String value1, String value2) {
            addCriterion("CMN_UPDDD between", value1, value2, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdddNotBetween(String value1, String value2) {
            addCriterion("CMN_UPDDD not between", value1, value2, "cmnUpddd");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeIsNull() {
            addCriterion("CMN_UPDTIME is null");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeIsNotNull() {
            addCriterion("CMN_UPDTIME is not null");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeEqualTo(String value) {
            addCriterion("CMN_UPDTIME =", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeNotEqualTo(String value) {
            addCriterion("CMN_UPDTIME <>", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeGreaterThan(String value) {
            addCriterion("CMN_UPDTIME >", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeGreaterThanOrEqualTo(String value) {
            addCriterion("CMN_UPDTIME >=", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeLessThan(String value) {
            addCriterion("CMN_UPDTIME <", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeLessThanOrEqualTo(String value) {
            addCriterion("CMN_UPDTIME <=", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeLike(String value) {
            addCriterion("CMN_UPDTIME like", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeNotLike(String value) {
            addCriterion("CMN_UPDTIME not like", value, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeIn(List<String> values) {
            addCriterion("CMN_UPDTIME in", values, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeNotIn(List<String> values) {
            addCriterion("CMN_UPDTIME not in", values, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeBetween(String value1, String value2) {
            addCriterion("CMN_UPDTIME between", value1, value2, "cmnUpdtime");
            return (Criteria) this;
        }

        public Criteria andCmnUpdtimeNotBetween(String value1, String value2) {
            addCriterion("CMN_UPDTIME not between", value1, value2, "cmnUpdtime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated do_not_delete_during_merge Wed Jan 22 09:53:12 JST 2014
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WEBMD.H190SANT
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}