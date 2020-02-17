package com.example.manda;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.utils.KJLoger;

public class DetailEveryday extends KJActivity {
    @BindView(id=R.id.word)
    private TextView word;
    @BindView(id=R.id.everyday_en)
    private TextView en;
    @BindView(id=R.id.titlebar_back,click = true)
    private Button back;

    private static final String EVERY_DAY_WORD = "https://api.ooopn.com/ciba/api.php";

    @Override
    public void setRootView() {
        setContentView(R.layout.detail_everyday);
    }

    @Override
    public void initData() {
        super.initData();
        KJHttp kjh = new KJHttp();
        kjh.get(EVERY_DAY_WORD, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                try {
                    JSONObject tmp = new JSONObject(t);
                    String a = tmp.getString("ciba");
                    String b = tmp.getString("ciba-en");
                    word.setText(a);
                    en.setText(b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                KJLoger.debug("log:" + t.toString());
            }
        });
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
