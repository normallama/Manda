package com.example.manda;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class DetailEveryday extends KJActivity {
    @BindView(id=R.id.titlebar_back,click = true)
    private Button back;

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
        setContentView(R.layout.detail_everyday);
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
            case R.id.titlebar_back:
                finish();
                break;
        }
    }
}
