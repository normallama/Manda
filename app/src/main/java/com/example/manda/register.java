package com.example.manda;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.manda.Data.userData;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class register extends KJActivity {
    @BindView(id=R.id.register_et_uid)
    private EditText mAccount;                        //用户名编辑
    @BindView(id=R.id.register_et_pwd)
    private EditText mPwd;                            //密码编辑
    @BindView(id=R.id.register_aga_pwd)
    private EditText mPwdCheck;                       //密码编辑
    @BindView(id=R.id.register_btn_login,click = true)
    private Button mSureButton;                       //确定按钮
    @BindView(id=R.id.titlebar_back,click = true)
    private ImageView mCancelButton;                     //取消按钮
    @BindView(id=R.id.check_read)
    private CheckBox mCheckRead;                      //确认按钮
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
        setContentView(R.layout.register);
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
            case R.id.register_btn_login:                       //确认按钮的监听事件
                register_check();
                break;
            case R.id.titlebar_back:                     //取消按钮的监听事件,由注册界面返回登录界面
                finish();
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (mUserDataManager == null) {
            mUserDataManager = new DataManage(this);
            mUserDataManager.openDataBase();                              //建立本地数据库
        }
    }

    public void register_check() {                                //确认按钮的监听事件
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd = mPwd.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();
            //检查用户是否存在
            int count=mUserDataManager.findUserByName(userName);
            AlertDialog.Builder builder = new AlertDialog.Builder(register.this);
            builder.setTitle(R.string.hint);
            //用户已经存在时返回，给出提示文字
            if(count>0){
                builder.setMessage( getString(R.string.name_already_exist)).show();
                builder.setPositiveButton(R.string.yes, null);
                builder.show();
                return ;
            }
            if(!userPwd.equals(userPwdCheck)){     //两次密码输入不一样
                builder.setMessage( getString(R.string.pwd_not_the_same));
                builder.setPositiveButton(R.string.yes, null);
                builder.show();
            } else if(!mCheckRead.isChecked()) {
                builder.setMessage(R.string.read_words);
                builder.setPositiveButton(R.string.yes, null);
                builder.show();
            } else {
                userData mUser = new userData(userName, userPwd);
                mUserDataManager.openDataBase();
                long flag = mUserDataManager.insertUserData(mUser); //新建用户信息
                if (flag == -1) {
                    builder.setMessage( getString(R.string.register_fail));
                    builder.setPositiveButton(R.string.yes, null);
                    builder.show();
                }else{
                    Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show();
                    Intent intent_Register_to_Login = new Intent(register.this,Login.class) ;    //切换User Activity至Login Activity
                    startActivity(intent_Register_to_Login);
                    finish();
                }
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
        }else if(mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
