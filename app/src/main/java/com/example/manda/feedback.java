package com.example.manda;

import android.os.Build;
import android.support.design.widget.BottomNavigationView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class feedback extends KJActivity {
    @BindView(id = R.id.titlebar_back, click = true)
    private RadioButton mRbtn1;

    public void setRootView() {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Window window = feedback.this.getWindow();

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(feedback.this.getResources().getColor(R.color.colorPrimary));

            }

        } catch (Exception e) {

            e.printStackTrace();

        }
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
    protected void onPause() {
        super.onPause();
        this.overridePendingTransition(R.anim.activity_close,R.anim.activity_open);
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
