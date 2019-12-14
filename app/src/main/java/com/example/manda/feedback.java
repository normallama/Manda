package com.example.manda;

import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.widget.RadioButton;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class feedback extends KJActivity {
    @BindView(id = R.id.titlebar_back, click = true)
    private RadioButton mRbtn1;

    public void setRootView() {
        setContentView(R.layout.setting);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void widgetClick(View v) {
        super.widgetClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                finish();
                break;
        }
    }
}
