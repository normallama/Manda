package com.example.manda;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class User extends KJActivity {

    @BindView(id=R.id.returnback,click = true)
    private Button mReturnButton;
    @BindView(id=R.id.titlebar_back,click = true)
    private ImageView backbutton;
    @BindView(id=R.id.textView)
    private TextView welcomeText;

    private SharedPreferences login_sp;   //本地保存信息

    @Override
    public void setRootView() {
        setContentView(R.layout.user);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        login_sp = getSharedPreferences("userInfo", 0);
        String username=login_sp.getString("USER_NAME","");
        welcomeText.setText( (String)(username +"您好，欢迎回来"));
    }

    public void back_to_login(View view) {
        SharedPreferences.Editor editor =login_sp.edit();
        editor.putBoolean("isLogin",false);
        editor.commit();
        Intent intent3 = new Intent(User.this,Login.class) ;
        startActivity(intent3);
        finish();
    }
    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.returnback:
                back_to_login(v);
                break;
            case R.id.titlebar_back:
                finish();
                break;
                default:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
