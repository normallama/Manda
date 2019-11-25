package com.example.manda;

import android.content.Context;

import com.example.manda.Data.NewWordsData;
import com.example.manda.greenDao.db.NewWordsDataDao;

import java.io.InputStream;
import java.util.List;

public class CountDaoUtils {
    /**
     * 单条插入
     *
     */
    public static void inserCountry(NewWordsData word) {
        DaoManager.getInstance().getDaoSession()
                .insertOrReplace(word);
    }

    /**
     * 批量增加
     */
    public static void inserCountryList(List<NewWordsData> word) {
        DaoManager.getInstance().getDaoSession()
                .getNewWordsDataDao()
                .insertOrReplaceInTx(word);
    }

    /**
     * 批量修改数据
     */
    public static void updateInTx(List<NewWordsData> word) {
        DaoManager.getInstance().getDaoSession().getNewWordsDataDao().updateInTx(word);
    }

    /**
     * 修改单条数据
     */
    public static void updateOne(NewWordsData word) {
        DaoManager.getInstance().getDaoSession().getNewWordsDataDao().update(word);
    }

    /**
     * 根据ID删除
     *
     * @param id id
     */
    public static void deleteCountry(long id) {
        DaoManager.getInstance().getDaoSession()
                .getNewWordsDataDao()
                .deleteByKey(id);
    }

    /**
     * 删除所有
     *
     */
    public static void deleteAll() {
        DaoManager.getInstance().getDaoSession().deleteAll(NewWordsData.class);
    }
    /**
     * 查询所有
     *
     * @return List
     */
    public static List<NewWordsData> queryAllCountry() {
        return DaoManager.getInstance().getDaoSession().loadAll(NewWordsData.class);
    }

    /**
     * 查询单条
     *
     * @return word
     */
    public static NewWordsData queryOne(long id) {
        return DaoManager.getInstance().getDaoSession().load(NewWordsData.class,id);
    }


    /**
     * 按条件查询
     *
     * @param id 名称
     * @return List
     */
    public static List<NewWordsData> queryByID(int id) {
        return DaoManager.getInstance().getDaoSession()
                .queryBuilder(NewWordsData.class)
                .where(NewWordsDataDao.Properties.WordId.eq(id))
                .list();
    }

}
