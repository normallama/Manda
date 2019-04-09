package com.example.manda;

import android.content.Intent;
import android.content.SharedPreferences;

import org.kymjs.kjframe.KJActivity;

public class accounter extends KJActivity {
    private SharedPreferences login_sp;   //本地保存信息
    boolean mUser;

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public void setRootView() {
        login_sp = getSharedPreferences("userInfo", 0);
        mUser=login_sp.getBoolean("isLogin",false);
        if(mUser){
            Intent intent7=new Intent(accounter.this,User.class);
            startActivity(intent7);
            finish();
        } else {
            Intent intent6 = new Intent(accounter.this, Login.class);
            startActivity(intent6);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
