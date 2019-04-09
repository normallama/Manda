package com.example.manda;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.manda.Fragment.HomepageFragment;
import com.example.manda.Fragment.MycourseFragment;
import com.example.manda.Fragment.PersonalFragment;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.SupportActivity;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJActivityStack;

public class Manda extends KJActivity {

    @BindView(id = R.id.navigation_home, click = true)
    private RadioButton mRbtn1;
    @BindView(id = R.id.navigation_dashboard, click = true)
    private RadioButton mRbtn2;
    @BindView(id = R.id.navigation_notifications, click = true)
    private RadioButton mRbtn3;

    @Override
    public void setRootView() {
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
    }

    @Override
    public void initWidget() {
        super.initWidget();
        changeFragment(R.id.main_content,home);
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
}
