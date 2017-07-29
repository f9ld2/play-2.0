package jp.co.necsoft.web_md.core.common.mybatis.dto;

import java.util.ArrayList;
import java.util.List;

public class O008trhkExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public O008trhkExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
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
     * This method corresponds to the database table WEBMD.O008TRHK
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
     * This method corresponds to the database table WEBMD.O008TRHK
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.O008TRHK
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
     * This class corresponds to the database table WEBMD.O008TRHK
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

        public Criteria andKaisyaCdIsNull() {
            addCriterion("KAISYA_CD is null");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdIsNotNull() {
            addCriterion("KAISYA_CD is not null");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdEqualTo(String value) {
            addCriterion("KAISYA_CD =", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdNotEqualTo(String value) {
            addCriterion("KAISYA_CD <>", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdGreaterThan(String value) {
            addCriterion("KAISYA_CD >", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdGreaterThanOrEqualTo(String value) {
            addCriterion("KAISYA_CD >=", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdLessThan(String value) {
            addCriterion("KAISYA_CD <", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdLessThanOrEqualTo(String value) {
            addCriterion("KAISYA_CD <=", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdLike(String value) {
            addCriterion("KAISYA_CD like", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdNotLike(String value) {
            addCriterion("KAISYA_CD not like", value, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdIn(List<String> values) {
            addCriterion("KAISYA_CD in", values, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdNotIn(List<String> values) {
            addCriterion("KAISYA_CD not in", values, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdBetween(String value1, String value2) {
            addCriterion("KAISYA_CD between", value1, value2, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKaisyaCdNotBetween(String value1, String value2) {
            addCriterion("KAISYA_CD not between", value1, value2, "kaisyaCd");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkIsNull() {
            addCriterion("KAK_TORI_KMK is null");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkIsNotNull() {
            addCriterion("KAK_TORI_KMK is not null");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkEqualTo(String value) {
            addCriterion("KAK_TORI_KMK =", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkNotEqualTo(String value) {
            addCriterion("KAK_TORI_KMK <>", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkGreaterThan(String value) {
            addCriterion("KAK_TORI_KMK >", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkGreaterThanOrEqualTo(String value) {
            addCriterion("KAK_TORI_KMK >=", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkLessThan(String value) {
            addCriterion("KAK_TORI_KMK <", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkLessThanOrEqualTo(String value) {
            addCriterion("KAK_TORI_KMK <=", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkLike(String value) {
            addCriterion("KAK_TORI_KMK like", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkNotLike(String value) {
            addCriterion("KAK_TORI_KMK not like", value, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkIn(List<String> values) {
            addCriterion("KAK_TORI_KMK in", values, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkNotIn(List<String> values) {
            addCriterion("KAK_TORI_KMK not in", values, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkBetween(String value1, String value2) {
            addCriterion("KAK_TORI_KMK between", value1, value2, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andKakToriKmkNotBetween(String value1, String value2) {
            addCriterion("KAK_TORI_KMK not between", value1, value2, "kakToriKmk");
            return (Criteria) this;
        }

        public Criteria andToriNmIsNull() {
            addCriterion("TORI_NM is null");
            return (Criteria) this;
        }

        public Criteria andToriNmIsNotNull() {
            addCriterion("TORI_NM is not null");
            return (Criteria) this;
        }

        public Criteria andToriNmEqualTo(String value) {
            addCriterion("TORI_NM =", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmNotEqualTo(String value) {
            addCriterion("TORI_NM <>", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmGreaterThan(String value) {
            addCriterion("TORI_NM >", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmGreaterThanOrEqualTo(String value) {
            addCriterion("TORI_NM >=", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmLessThan(String value) {
            addCriterion("TORI_NM <", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmLessThanOrEqualTo(String value) {
            addCriterion("TORI_NM <=", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmLike(String value) {
            addCriterion("TORI_NM like", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmNotLike(String value) {
            addCriterion("TORI_NM not like", value, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmIn(List<String> values) {
            addCriterion("TORI_NM in", values, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmNotIn(List<String> values) {
            addCriterion("TORI_NM not in", values, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmBetween(String value1, String value2) {
            addCriterion("TORI_NM between", value1, value2, "toriNm");
            return (Criteria) this;
        }

        public Criteria andToriNmNotBetween(String value1, String value2) {
            addCriterion("TORI_NM not between", value1, value2, "toriNm");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnIsNull() {
            addCriterion("KANJO_KBN is null");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnIsNotNull() {
            addCriterion("KANJO_KBN is not null");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnEqualTo(String value) {
            addCriterion("KANJO_KBN =", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnNotEqualTo(String value) {
            addCriterion("KANJO_KBN <>", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnGreaterThan(String value) {
            addCriterion("KANJO_KBN >", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnGreaterThanOrEqualTo(String value) {
            addCriterion("KANJO_KBN >=", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnLessThan(String value) {
            addCriterion("KANJO_KBN <", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnLessThanOrEqualTo(String value) {
            addCriterion("KANJO_KBN <=", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnLike(String value) {
            addCriterion("KANJO_KBN like", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnNotLike(String value) {
            addCriterion("KANJO_KBN not like", value, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnIn(List<String> values) {
            addCriterion("KANJO_KBN in", values, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnNotIn(List<String> values) {
            addCriterion("KANJO_KBN not in", values, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnBetween(String value1, String value2) {
            addCriterion("KANJO_KBN between", value1, value2, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKanjoKbnNotBetween(String value1, String value2) {
            addCriterion("KANJO_KBN not between", value1, value2, "kanjoKbn");
            return (Criteria) this;
        }

        public Criteria andKmkCdIsNull() {
            addCriterion("KMK_CD is null");
            return (Criteria) this;
        }

        public Criteria andKmkCdIsNotNull() {
            addCriterion("KMK_CD is not null");
            return (Criteria) this;
        }

        public Criteria andKmkCdEqualTo(String value) {
            addCriterion("KMK_CD =", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdNotEqualTo(String value) {
            addCriterion("KMK_CD <>", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdGreaterThan(String value) {
            addCriterion("KMK_CD >", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdGreaterThanOrEqualTo(String value) {
            addCriterion("KMK_CD >=", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdLessThan(String value) {
            addCriterion("KMK_CD <", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdLessThanOrEqualTo(String value) {
            addCriterion("KMK_CD <=", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdLike(String value) {
            addCriterion("KMK_CD like", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdNotLike(String value) {
            addCriterion("KMK_CD not like", value, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdIn(List<String> values) {
            addCriterion("KMK_CD in", values, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdNotIn(List<String> values) {
            addCriterion("KMK_CD not in", values, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdBetween(String value1, String value2) {
            addCriterion("KMK_CD between", value1, value2, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andKmkCdNotBetween(String value1, String value2) {
            addCriterion("KMK_CD not between", value1, value2, "kmkCd");
            return (Criteria) this;
        }

        public Criteria andTaxKbnIsNull() {
            addCriterion("TAX_KBN is null");
            return (Criteria) this;
        }

        public Criteria andTaxKbnIsNotNull() {
            addCriterion("TAX_KBN is not null");
            return (Criteria) this;
        }

        public Criteria andTaxKbnEqualTo(String value) {
            addCriterion("TAX_KBN =", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnNotEqualTo(String value) {
            addCriterion("TAX_KBN <>", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnGreaterThan(String value) {
            addCriterion("TAX_KBN >", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnGreaterThanOrEqualTo(String value) {
            addCriterion("TAX_KBN >=", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnLessThan(String value) {
            addCriterion("TAX_KBN <", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnLessThanOrEqualTo(String value) {
            addCriterion("TAX_KBN <=", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnLike(String value) {
            addCriterion("TAX_KBN like", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnNotLike(String value) {
            addCriterion("TAX_KBN not like", value, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnIn(List<String> values) {
            addCriterion("TAX_KBN in", values, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnNotIn(List<String> values) {
            addCriterion("TAX_KBN not in", values, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnBetween(String value1, String value2) {
            addCriterion("TAX_KBN between", value1, value2, "taxKbn");
            return (Criteria) this;
        }

        public Criteria andTaxKbnNotBetween(String value1, String value2) {
            addCriterion("TAX_KBN not between", value1, value2, "taxKbn");
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
     * This class corresponds to the database table WEBMD.O008TRHK
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
     * This class corresponds to the database table WEBMD.O008TRHK
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