package jp.co.necsoft.web_md.core.common.mybatis.dto;

import java.util.ArrayList;
import java.util.List;

public class Sc24wstmExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public Sc24wstmExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
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
     * This method corresponds to the database table WEBMD.SC24WSTM
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
     * This method corresponds to the database table WEBMD.SC24WSTM
     *
     * @mbggenerated Wed Jan 22 09:53:12 JST 2014
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table WEBMD.SC24WSTM
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
     * This class corresponds to the database table WEBMD.SC24WSTM
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

        public Criteria andBatIdIsNull() {
            addCriterion("BAT_ID is null");
            return (Criteria) this;
        }

        public Criteria andBatIdIsNotNull() {
            addCriterion("BAT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBatIdEqualTo(String value) {
            addCriterion("BAT_ID =", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdNotEqualTo(String value) {
            addCriterion("BAT_ID <>", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdGreaterThan(String value) {
            addCriterion("BAT_ID >", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdGreaterThanOrEqualTo(String value) {
            addCriterion("BAT_ID >=", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdLessThan(String value) {
            addCriterion("BAT_ID <", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdLessThanOrEqualTo(String value) {
            addCriterion("BAT_ID <=", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdLike(String value) {
            addCriterion("BAT_ID like", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdNotLike(String value) {
            addCriterion("BAT_ID not like", value, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdIn(List<String> values) {
            addCriterion("BAT_ID in", values, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdNotIn(List<String> values) {
            addCriterion("BAT_ID not in", values, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdBetween(String value1, String value2) {
            addCriterion("BAT_ID between", value1, value2, "batId");
            return (Criteria) this;
        }

        public Criteria andBatIdNotBetween(String value1, String value2) {
            addCriterion("BAT_ID not between", value1, value2, "batId");
            return (Criteria) this;
        }

        public Criteria andPgIdIsNull() {
            addCriterion("PG_ID is null");
            return (Criteria) this;
        }

        public Criteria andPgIdIsNotNull() {
            addCriterion("PG_ID is not null");
            return (Criteria) this;
        }

        public Criteria andPgIdEqualTo(String value) {
            addCriterion("PG_ID =", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdNotEqualTo(String value) {
            addCriterion("PG_ID <>", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdGreaterThan(String value) {
            addCriterion("PG_ID >", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdGreaterThanOrEqualTo(String value) {
            addCriterion("PG_ID >=", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdLessThan(String value) {
            addCriterion("PG_ID <", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdLessThanOrEqualTo(String value) {
            addCriterion("PG_ID <=", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdLike(String value) {
            addCriterion("PG_ID like", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdNotLike(String value) {
            addCriterion("PG_ID not like", value, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdIn(List<String> values) {
            addCriterion("PG_ID in", values, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdNotIn(List<String> values) {
            addCriterion("PG_ID not in", values, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdBetween(String value1, String value2) {
            addCriterion("PG_ID between", value1, value2, "pgId");
            return (Criteria) this;
        }

        public Criteria andPgIdNotBetween(String value1, String value2) {
            addCriterion("PG_ID not between", value1, value2, "pgId");
            return (Criteria) this;
        }

        public Criteria andWeekIsNull() {
            addCriterion("WEEK is null");
            return (Criteria) this;
        }

        public Criteria andWeekIsNotNull() {
            addCriterion("WEEK is not null");
            return (Criteria) this;
        }

        public Criteria andWeekEqualTo(String value) {
            addCriterion("WEEK =", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotEqualTo(String value) {
            addCriterion("WEEK <>", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThan(String value) {
            addCriterion("WEEK >", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekGreaterThanOrEqualTo(String value) {
            addCriterion("WEEK >=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThan(String value) {
            addCriterion("WEEK <", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLessThanOrEqualTo(String value) {
            addCriterion("WEEK <=", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekLike(String value) {
            addCriterion("WEEK like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotLike(String value) {
            addCriterion("WEEK not like", value, "week");
            return (Criteria) this;
        }

        public Criteria andWeekIn(List<String> values) {
            addCriterion("WEEK in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotIn(List<String> values) {
            addCriterion("WEEK not in", values, "week");
            return (Criteria) this;
        }

        public Criteria andWeekBetween(String value1, String value2) {
            addCriterion("WEEK between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andWeekNotBetween(String value1, String value2) {
            addCriterion("WEEK not between", value1, value2, "week");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuIsNull() {
            addCriterion("TTL_KAISU is null");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuIsNotNull() {
            addCriterion("TTL_KAISU is not null");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuEqualTo(Integer value) {
            addCriterion("TTL_KAISU =", value, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuNotEqualTo(Integer value) {
            addCriterion("TTL_KAISU <>", value, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuGreaterThan(Integer value) {
            addCriterion("TTL_KAISU >", value, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuGreaterThanOrEqualTo(Integer value) {
            addCriterion("TTL_KAISU >=", value, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuLessThan(Integer value) {
            addCriterion("TTL_KAISU <", value, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuLessThanOrEqualTo(Integer value) {
            addCriterion("TTL_KAISU <=", value, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuIn(List<Integer> values) {
            addCriterion("TTL_KAISU in", values, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuNotIn(List<Integer> values) {
            addCriterion("TTL_KAISU not in", values, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuBetween(Integer value1, Integer value2) {
            addCriterion("TTL_KAISU between", value1, value2, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlKaisuNotBetween(Integer value1, Integer value2) {
            addCriterion("TTL_KAISU not between", value1, value2, "ttlKaisu");
            return (Criteria) this;
        }

        public Criteria andTtlSecIsNull() {
            addCriterion("TTL_SEC is null");
            return (Criteria) this;
        }

        public Criteria andTtlSecIsNotNull() {
            addCriterion("TTL_SEC is not null");
            return (Criteria) this;
        }

        public Criteria andTtlSecEqualTo(Long value) {
            addCriterion("TTL_SEC =", value, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecNotEqualTo(Long value) {
            addCriterion("TTL_SEC <>", value, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecGreaterThan(Long value) {
            addCriterion("TTL_SEC >", value, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecGreaterThanOrEqualTo(Long value) {
            addCriterion("TTL_SEC >=", value, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecLessThan(Long value) {
            addCriterion("TTL_SEC <", value, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecLessThanOrEqualTo(Long value) {
            addCriterion("TTL_SEC <=", value, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecIn(List<Long> values) {
            addCriterion("TTL_SEC in", values, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecNotIn(List<Long> values) {
            addCriterion("TTL_SEC not in", values, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecBetween(Long value1, Long value2) {
            addCriterion("TTL_SEC between", value1, value2, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlSecNotBetween(Long value1, Long value2) {
            addCriterion("TTL_SEC not between", value1, value2, "ttlSec");
            return (Criteria) this;
        }

        public Criteria andTtlKensuIsNull() {
            addCriterion("TTL_KENSU is null");
            return (Criteria) this;
        }

        public Criteria andTtlKensuIsNotNull() {
            addCriterion("TTL_KENSU is not null");
            return (Criteria) this;
        }

        public Criteria andTtlKensuEqualTo(Long value) {
            addCriterion("TTL_KENSU =", value, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuNotEqualTo(Long value) {
            addCriterion("TTL_KENSU <>", value, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuGreaterThan(Long value) {
            addCriterion("TTL_KENSU >", value, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuGreaterThanOrEqualTo(Long value) {
            addCriterion("TTL_KENSU >=", value, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuLessThan(Long value) {
            addCriterion("TTL_KENSU <", value, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuLessThanOrEqualTo(Long value) {
            addCriterion("TTL_KENSU <=", value, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuIn(List<Long> values) {
            addCriterion("TTL_KENSU in", values, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuNotIn(List<Long> values) {
            addCriterion("TTL_KENSU not in", values, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuBetween(Long value1, Long value2) {
            addCriterion("TTL_KENSU between", value1, value2, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andTtlKensuNotBetween(Long value1, Long value2) {
            addCriterion("TTL_KENSU not between", value1, value2, "ttlKensu");
            return (Criteria) this;
        }

        public Criteria andAveSecIsNull() {
            addCriterion("AVE_SEC is null");
            return (Criteria) this;
        }

        public Criteria andAveSecIsNotNull() {
            addCriterion("AVE_SEC is not null");
            return (Criteria) this;
        }

        public Criteria andAveSecEqualTo(Integer value) {
            addCriterion("AVE_SEC =", value, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecNotEqualTo(Integer value) {
            addCriterion("AVE_SEC <>", value, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecGreaterThan(Integer value) {
            addCriterion("AVE_SEC >", value, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecGreaterThanOrEqualTo(Integer value) {
            addCriterion("AVE_SEC >=", value, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecLessThan(Integer value) {
            addCriterion("AVE_SEC <", value, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecLessThanOrEqualTo(Integer value) {
            addCriterion("AVE_SEC <=", value, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecIn(List<Integer> values) {
            addCriterion("AVE_SEC in", values, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecNotIn(List<Integer> values) {
            addCriterion("AVE_SEC not in", values, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecBetween(Integer value1, Integer value2) {
            addCriterion("AVE_SEC between", value1, value2, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveSecNotBetween(Integer value1, Integer value2) {
            addCriterion("AVE_SEC not between", value1, value2, "aveSec");
            return (Criteria) this;
        }

        public Criteria andAveTimeIsNull() {
            addCriterion("AVE_TIME is null");
            return (Criteria) this;
        }

        public Criteria andAveTimeIsNotNull() {
            addCriterion("AVE_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andAveTimeEqualTo(String value) {
            addCriterion("AVE_TIME =", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeNotEqualTo(String value) {
            addCriterion("AVE_TIME <>", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeGreaterThan(String value) {
            addCriterion("AVE_TIME >", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeGreaterThanOrEqualTo(String value) {
            addCriterion("AVE_TIME >=", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeLessThan(String value) {
            addCriterion("AVE_TIME <", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeLessThanOrEqualTo(String value) {
            addCriterion("AVE_TIME <=", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeLike(String value) {
            addCriterion("AVE_TIME like", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeNotLike(String value) {
            addCriterion("AVE_TIME not like", value, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeIn(List<String> values) {
            addCriterion("AVE_TIME in", values, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeNotIn(List<String> values) {
            addCriterion("AVE_TIME not in", values, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeBetween(String value1, String value2) {
            addCriterion("AVE_TIME between", value1, value2, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveTimeNotBetween(String value1, String value2) {
            addCriterion("AVE_TIME not between", value1, value2, "aveTime");
            return (Criteria) this;
        }

        public Criteria andAveKensuIsNull() {
            addCriterion("AVE_KENSU is null");
            return (Criteria) this;
        }

        public Criteria andAveKensuIsNotNull() {
            addCriterion("AVE_KENSU is not null");
            return (Criteria) this;
        }

        public Criteria andAveKensuEqualTo(Long value) {
            addCriterion("AVE_KENSU =", value, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuNotEqualTo(Long value) {
            addCriterion("AVE_KENSU <>", value, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuGreaterThan(Long value) {
            addCriterion("AVE_KENSU >", value, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuGreaterThanOrEqualTo(Long value) {
            addCriterion("AVE_KENSU >=", value, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuLessThan(Long value) {
            addCriterion("AVE_KENSU <", value, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuLessThanOrEqualTo(Long value) {
            addCriterion("AVE_KENSU <=", value, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuIn(List<Long> values) {
            addCriterion("AVE_KENSU in", values, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuNotIn(List<Long> values) {
            addCriterion("AVE_KENSU not in", values, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuBetween(Long value1, Long value2) {
            addCriterion("AVE_KENSU between", value1, value2, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andAveKensuNotBetween(Long value1, Long value2) {
            addCriterion("AVE_KENSU not between", value1, value2, "aveKensu");
            return (Criteria) this;
        }

        public Criteria andP1000SecIsNull() {
            addCriterion("P1000_SEC is null");
            return (Criteria) this;
        }

        public Criteria andP1000SecIsNotNull() {
            addCriterion("P1000_SEC is not null");
            return (Criteria) this;
        }

        public Criteria andP1000SecEqualTo(Integer value) {
            addCriterion("P1000_SEC =", value, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecNotEqualTo(Integer value) {
            addCriterion("P1000_SEC <>", value, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecGreaterThan(Integer value) {
            addCriterion("P1000_SEC >", value, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecGreaterThanOrEqualTo(Integer value) {
            addCriterion("P1000_SEC >=", value, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecLessThan(Integer value) {
            addCriterion("P1000_SEC <", value, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecLessThanOrEqualTo(Integer value) {
            addCriterion("P1000_SEC <=", value, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecIn(List<Integer> values) {
            addCriterion("P1000_SEC in", values, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecNotIn(List<Integer> values) {
            addCriterion("P1000_SEC not in", values, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecBetween(Integer value1, Integer value2) {
            addCriterion("P1000_SEC between", value1, value2, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000SecNotBetween(Integer value1, Integer value2) {
            addCriterion("P1000_SEC not between", value1, value2, "p1000Sec");
            return (Criteria) this;
        }

        public Criteria andP1000TimeIsNull() {
            addCriterion("P1000_TIME is null");
            return (Criteria) this;
        }

        public Criteria andP1000TimeIsNotNull() {
            addCriterion("P1000_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andP1000TimeEqualTo(String value) {
            addCriterion("P1000_TIME =", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeNotEqualTo(String value) {
            addCriterion("P1000_TIME <>", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeGreaterThan(String value) {
            addCriterion("P1000_TIME >", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeGreaterThanOrEqualTo(String value) {
            addCriterion("P1000_TIME >=", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeLessThan(String value) {
            addCriterion("P1000_TIME <", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeLessThanOrEqualTo(String value) {
            addCriterion("P1000_TIME <=", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeLike(String value) {
            addCriterion("P1000_TIME like", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeNotLike(String value) {
            addCriterion("P1000_TIME not like", value, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeIn(List<String> values) {
            addCriterion("P1000_TIME in", values, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeNotIn(List<String> values) {
            addCriterion("P1000_TIME not in", values, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeBetween(String value1, String value2) {
            addCriterion("P1000_TIME between", value1, value2, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andP1000TimeNotBetween(String value1, String value2) {
            addCriterion("P1000_TIME not between", value1, value2, "p1000Time");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdIsNull() {
            addCriterion("UPD_TANTOCD is null");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdIsNotNull() {
            addCriterion("UPD_TANTOCD is not null");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdEqualTo(String value) {
            addCriterion("UPD_TANTOCD =", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdNotEqualTo(String value) {
            addCriterion("UPD_TANTOCD <>", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdGreaterThan(String value) {
            addCriterion("UPD_TANTOCD >", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdGreaterThanOrEqualTo(String value) {
            addCriterion("UPD_TANTOCD >=", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdLessThan(String value) {
            addCriterion("UPD_TANTOCD <", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdLessThanOrEqualTo(String value) {
            addCriterion("UPD_TANTOCD <=", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdLike(String value) {
            addCriterion("UPD_TANTOCD like", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdNotLike(String value) {
            addCriterion("UPD_TANTOCD not like", value, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdIn(List<String> values) {
            addCriterion("UPD_TANTOCD in", values, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdNotIn(List<String> values) {
            addCriterion("UPD_TANTOCD not in", values, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdBetween(String value1, String value2) {
            addCriterion("UPD_TANTOCD between", value1, value2, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdTantocdNotBetween(String value1, String value2) {
            addCriterion("UPD_TANTOCD not between", value1, value2, "updTantocd");
            return (Criteria) this;
        }

        public Criteria andUpdDateIsNull() {
            addCriterion("UPD_DATE is null");
            return (Criteria) this;
        }

        public Criteria andUpdDateIsNotNull() {
            addCriterion("UPD_DATE is not null");
            return (Criteria) this;
        }

        public Criteria andUpdDateEqualTo(String value) {
            addCriterion("UPD_DATE =", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateNotEqualTo(String value) {
            addCriterion("UPD_DATE <>", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateGreaterThan(String value) {
            addCriterion("UPD_DATE >", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateGreaterThanOrEqualTo(String value) {
            addCriterion("UPD_DATE >=", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateLessThan(String value) {
            addCriterion("UPD_DATE <", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateLessThanOrEqualTo(String value) {
            addCriterion("UPD_DATE <=", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateLike(String value) {
            addCriterion("UPD_DATE like", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateNotLike(String value) {
            addCriterion("UPD_DATE not like", value, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateIn(List<String> values) {
            addCriterion("UPD_DATE in", values, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateNotIn(List<String> values) {
            addCriterion("UPD_DATE not in", values, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateBetween(String value1, String value2) {
            addCriterion("UPD_DATE between", value1, value2, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdDateNotBetween(String value1, String value2) {
            addCriterion("UPD_DATE not between", value1, value2, "updDate");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIsNull() {
            addCriterion("UPD_TIME is null");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIsNotNull() {
            addCriterion("UPD_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andUpdTimeEqualTo(String value) {
            addCriterion("UPD_TIME =", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotEqualTo(String value) {
            addCriterion("UPD_TIME <>", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeGreaterThan(String value) {
            addCriterion("UPD_TIME >", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UPD_TIME >=", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLessThan(String value) {
            addCriterion("UPD_TIME <", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLessThanOrEqualTo(String value) {
            addCriterion("UPD_TIME <=", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeLike(String value) {
            addCriterion("UPD_TIME like", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotLike(String value) {
            addCriterion("UPD_TIME not like", value, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeIn(List<String> values) {
            addCriterion("UPD_TIME in", values, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotIn(List<String> values) {
            addCriterion("UPD_TIME not in", values, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeBetween(String value1, String value2) {
            addCriterion("UPD_TIME between", value1, value2, "updTime");
            return (Criteria) this;
        }

        public Criteria andUpdTimeNotBetween(String value1, String value2) {
            addCriterion("UPD_TIME not between", value1, value2, "updTime");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table WEBMD.SC24WSTM
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
     * This class corresponds to the database table WEBMD.SC24WSTM
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