/*
 * COPYRIGHT (C) 2014 NEC CORPORATION
 * 
 * ALL RIGHTS RESERVED BY NEC CORPORATION, THIS PROGRAM MUST BE USED SOLELY FOR THE PURPOSE FOR WHICH IT WAS FURNISHED
 * BY NEC CORPORATION, NO PART OF THIS PROGRAM MAY BE REPRODUCED OR DISCLOSED TO OTHERS, IN ANY FORM WITHOUT THE PRIOR
 * WRITTEN PERMISSION OF NEC CORPORATION.
 * 
 * NEC CONFIDENTIAL AND PROPRIETARY
 */
package jp.co.necsoft.web_md.common.mybatis.dao;

import java.util.List;

import javax.inject.Inject;

import jp.co.necsoft.web_md.common.mybatis.message.MybatisMessages;
import jp.co.necsoft.web_md.common.mybatis.message.MybatisMessagesConst;
import jp.co.necsoft.web_md.core.common.utils.DataUtil;

import org.apache.ibatis.session.SqlSession;

import play.Logger;

/**
 * メーカマスタメンテのMyBatisSqlMapDaoクラス
 * @author NECVN
 *
 */
public class MyBatisSqlMapDao {

    /** @Inject */
    @Inject
    private SqlSession sqlSession;

    /** MyBatisSqlMapDao */
    public MyBatisSqlMapDao() {
    }

    /**
     * getSqlSession.
     * @return SqlSession
     */
    private SqlSession getSqlSession() {
        return this.sqlSession;
    }

    /**
     * deleteOne.
     * @param id String
     * @param parameterObject Object
     */
    public int deleteOne(String id, Object parameterObject) {
        if (id == null) {
            Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
            throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
        }

        final int rows = getSqlSession().delete(id, parameterObject);
        if (rows == 0) {
            Logger.warn(MybatisMessages.getFullMessage(MybatisMessagesConst.W520002, id));
        }
        if (1 < rows) {
            Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520003, id));
        }
        
        return rows;
    }

    /**
     * insertOne.
     * @param id String
     * @param parameterObject Object
     */
    public int insertOne(String id, Object parameterObject) {
        if (id == null) {
            Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
            throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
        }

        final int rows = getSqlSession().insert(id, parameterObject);
        
        return rows;
    }

    /**
     * selectOne
     * @param <T> Object
     * @param id String
     * @param parameterObject Object
     * @return <T>
     */
    @SuppressWarnings("unchecked")
    public <T> T selectOne(String id, Object parameterObject) {
        if (id == null) {
            if (id == null) {
                Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
                throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
            }
        }

        return (T) DataUtil.trimObjectValue(getSqlSession().selectOne(id, parameterObject));
    }

    /**
     * selectMany
     * @param <T> Object
     * @param id String
     * @param parameterObject Object
     * @param resultClass Class<T>
     * @return <T> Object
     */
    public <T> List<T> selectMany(String id, Object parameterObject, Class<T> resultClass) {
        if (id == null) {
            Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
            throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
        }

        @SuppressWarnings("unchecked")
        List<T> list = (List<T>) getSqlSession().selectList(id, parameterObject);
        checkResultClass(list, resultClass);
        for (T obj : list) {
            DataUtil.trimObjectValue(obj);
        }
        return list;

    }

    /**
     * updateOne
     * @param id String
     * @param parameterObject Object
     */
    public int updateOne(String id, Object parameterObject) {
        if (id == null) {
            Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
            throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520001));
        }

        final int rows = getSqlSession().update(id, parameterObject);
        if (rows == 0) {
            Logger.warn(MybatisMessages.getFullMessage(MybatisMessagesConst.W520001, id));
        }
        
        return rows;
    }

    /**
     * 指定のリスト中に含まれているオブジェクトが 指定の結果クラスの型と同じかどうかチェックします。 同じなら、正常終了します。 同じでなければ
     * IllegalArgumentException をスローします。
     * 
     * @param list
     *            チェック対象のリスト
     * @param resultClass
     *            結果クラス
     */
    private static void checkResultClass(List<?> list, Class<?> resultClass) {
        if (resultClass == null) {
            Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520004));
            throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520004));
        }

        if (list.isEmpty()) {
            return;
        }
        Object elem = list.get(0);
        Class<?> elemClass = elem.getClass();
        if (resultClass.equals(elemClass)) {
            return;
        }
        Logger.error(MybatisMessages.getFullMessage(MybatisMessagesConst.E520005, resultClass.getCanonicalName(),
                elem.getClass().getCanonicalName()));
        throw new IllegalArgumentException(MybatisMessages.getFullMessage(MybatisMessagesConst.E520005,
                resultClass.getCanonicalName(), elem.getClass().getCanonicalName()));
    }

}
