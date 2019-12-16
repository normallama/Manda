package com.example.manda;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Toast;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class Login extends KJActivity {                 //登录界面活动

    public int pwdresetFlag=0;
    @BindView(id=R.id.login_et_uid)
    private EditText mAccount;                        //用户名编辑
    @BindView(id=R.id.login_et_pwd)
    private EditText mPwd;                            //密码编辑
    @BindView(id=R.id.login_btn_login,click = true)
    private Button mLoginButton;                      //登录按钮
    @BindView(id=R.id.titlebar_back,click = true)
    private ImageView mCancleButton;
    @BindView(id=R.id.register,click = true)
    private Button mRegisterButton;                   //注册按钮
    @BindView(id=R.id.login_remember,click = true)
    private CheckBox mRememberCheck;
    @BindView(id=R.id.forget,click = true)
    private Button mForget;

    private SharedPreferences login_sp;           //本地保存信息
    private DataManage mUserDataManager;         //用户数据管理类


    @Override
    public void setRootView() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.login);
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
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.register:                            //登录界面的注册按钮
                Intent intent_Login_to_Register = new Intent(Login.this, register.class);    //切换Login Activity至User Activity
                startActivity(intent_Login_to_Register);
                break;
            case R.id.login_btn_login:                              //登录界面的登录按钮
                login();
                break;
            case R.id.titlebar_back:                             //登录界面的返回按钮
                finish();
                break;
        }
    }

    @Override
    public void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);


            login_sp = getSharedPreferences("userInfo", 0);
            String name = login_sp.getString("USER_NAME", "");
            String pwd = login_sp.getString("PASSWORD", "");
            boolean choseRemember = login_sp.getBoolean("mRememberCheck", false);
            //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
            if (choseRemember) {
                mAccount.setText(name);
                mPwd.setText(pwd);
                mRememberCheck.setChecked(true);
            }



            if (mUserDataManager == null) {
                mUserDataManager = new DataManage(this);
                mUserDataManager.openDataBase();                              //建立本地数据库
            }
    }

    public void login() {                                              //登录按钮监听事件
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();    //获取当前输入的用户名和密码信息
            String userPwd = mPwd.getText().toString().trim();
            SharedPreferences.Editor editor =login_sp.edit();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd);
            if(result!=0){                                             //返回1说明用户名和密码均正确
                //保存用户名和密码
                editor.putString("USER_NAME", userName);
                editor.putString("PASSWORD", userPwd);

                //是否记住密码
                if(mRememberCheck.isChecked()){
                    editor.putBoolean("mRememberCheck", true);
                }else{
                    editor.putBoolean("mRememberCheck", false);
                }
                editor.putBoolean("isLogin",true);
                editor.commit();

                Intent intent = new Intent(Login.this,User.class) ;    //切换Login Activity至User Activity
                startActivity(intent);
                finish();
                Toast.makeText(this, getString(R.string.login_success),Toast.LENGTH_SHORT).show();//登录成功提示
            }else {
                Toast.makeText(this, getString(R.string.login_fail),Toast.LENGTH_SHORT).show();  //登录失败提示
            }
        }
    }

    public boolean isUserNameAndPwdValid() {
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    protected void onResume() {
        if (mUserDataManager == null) {
            mUserDataManager = new DataManage(this);
            mUserDataManager.openDataBase();
        }
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        if (mUserDataManager != null) {
            mUserDataManager.closeDataBase();
            mUserDataManager = null;
        }
        super.onPause();
    }
}