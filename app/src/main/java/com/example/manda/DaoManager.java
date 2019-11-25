package com.example.manda;

import com.example.manda.greenDao.db.DaoMaster;
import com.example.manda.greenDao.db.DaoSession;
import com.example.manda.greenDao.db.DaoMaster.DevOpenHelper;

public class DaoManager {
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DaoManager mInstance;//单例模式
    private DaoManager() {
        init();
    }
    /**
     * 静态内部类，实例化对象使用
     */
    private static class SingleInstanceHolder {
        private static final DaoManager INSTANCE = new DaoManager();
    }
    /**
     * 对外唯一实例的接口
     *
     * @return
     */
    public static DaoManager getInstance() {
        return SingleInstanceHolder.INSTANCE;
    }

    /**
     * 初始化数据
     */
    private void init() {
        DevOpenHelper devOpenHelper = new DevOpenHelper(Manda.getContext(), "EnglishWords.db");//创建数据库
        mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());//获得可写数据库对象
        mDaoSession = mDaoMaster.newSession();
    }


    public DaoMaster getDaoMaster() {
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
