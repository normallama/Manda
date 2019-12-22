package com.example.manda;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import com.example.manda.Adapter.FileUtils;
import com.example.manda.Data.GetSpelling;
import com.example.manda.Fragment.HomepageFragment;
import com.example.manda.Fragment.MycourseFragment;
import com.example.manda.Fragment.PersonalFragment;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class Manda extends KJActivity {

    @BindView(id = R.id.navigation_home, click = true)
    private RadioButton mRbtn1;
    @BindView(id = R.id.navigation_dashboard, click = true)
    private RadioButton mRbtn2;
    @BindView(id = R.id.navigation_notifications, click = true)
    private RadioButton mRbtn3;

    private static Context context;


    @Override
    public void setRootView() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = Manda.this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Manda.this.getResources().getColor(R.color.colorPrimary));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_manda);
    }

    private HomepageFragment home;
    private MycourseFragment course;
    private PersonalFragment personal;



    @Override
    public void initData() {
        super.initData();
        home=new HomepageFragment();
        course=new MycourseFragment();
        personal=new PersonalFragment();
        context=getApplicationContext();
        FileUtils.getInstance(context).copyAssetsToSD("","mandatest");
    }

    @Override
    public void initWidget() {
        super.initWidget();
        changeFragment(R.id.main_content,home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  //申请权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);

            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Manda.this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.INTERNET},1);
            }
        }
    }


    @Override
    public void widgetClick(View v) {
        BottomNavigationView navigation;
        navigation =  findViewById(R.id.navigation);
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.navigation_home:
                changeFragment(R.id.main_content,home);
                navigation.setSelectedItemId(navigation.getMenu().getItem(0).getItemId());//默认选择
                break;
            case R.id.navigation_dashboard:
                changeFragment(R.id.main_content,course);
                navigation.setSelectedItemId(navigation.getMenu().getItem(1).getItemId());//默认选择
                break;
            case R.id.navigation_notifications:
                changeFragment(R.id.main_content,personal);
                navigation.setSelectedItemId(navigation.getMenu().getItem(2).getItemId());//默认选择
                break;
            default:
                break;
        }
    }
    public static Context getContext(){
        return context;
    }

}
