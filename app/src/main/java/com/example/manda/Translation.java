package com.example.manda;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.manda.TransApi.TransApi;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.BindView;

public class Translation extends KJActivity {
    @BindView(id=R.id.to_translate , click = true)
    private EditText text_to_Tran;                        //用户输入的
    @BindView(id=R.id.success_tran , click = true)
    private TextView Tran_text;                        //翻译出来的文本控件
    @BindView(id=R.id.button_translate , click = true)
    private Button tran;                               //翻译的按钮
    @BindView(id=R.id.spinner , click = true)
    private Spinner tran_choice;
    @BindView(id=R.id.translate_bar_back , click=true)
    private RadioButton back;

    private static final String APP_ID = "20190731000322884";
    private static final String SECURITY_KEY = "jupaRv7jQyfljW7HZEs2";

    @Override
    public void setRootView() {
        setContentView(R.layout.translation);
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
            case R.id.button_translate:
                TransApi getTrans = new TransApi(APP_ID, SECURITY_KEY);
                Tran_text.setText(getTrans.getTransResult(text_to_Tran.toString(), "auto", "en"));
                Tran_text.setVisibility(View.VISIBLE);
                break;
            case R.id.translate_bar_back:                     //取消按钮的监听事件，返回上个界面
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
