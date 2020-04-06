package com.example.manda.Data;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.io.Serializable;
import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.example.manda.greenDao.db.DaoSession;
import com.example.manda.greenDao.db.NewWordsDataDao;
import com.example.manda.greenDao.db.userDataDao;

@Entity
public class userData {

    public static String mysession=null;

    private String userName;                  //用户名
    private String userPwd;                   //用户密码
    private String nickname;                    //  昵称

    @Id(autoincrement = true)
    private Long userId;                       //用户ID号
    private int learnTime;                    //学习时长
    private int wallet;                       //余额
    @Generated(hash = 1165525998)
    public userData(String userName, String userPwd, String nickname, Long userId, int learnTime,
            int wallet) {
        this.userName = userName;
        this.userPwd = userPwd;
        this.nickname = nickname;
        this.userId = userId;
        this.learnTime = learnTime;
        this.wallet = wallet;
    }
    @Generated(hash = 351031987)
    public userData() {
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserPwd() {
        return this.userPwd;
    }
    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
    public Long getUserId() {
        return this.userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public int getLearnTime() {
        return this.learnTime;
    }
    public void setLearnTime(int learnTime) {
        this.learnTime = learnTime;
    }
    public int getWallet() {
        return this.wallet;
    }
    public void setWallet(int wallet) {
        this.wallet = wallet;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */

    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    
}

