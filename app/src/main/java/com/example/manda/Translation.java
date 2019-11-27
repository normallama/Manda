package com.example.manda;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.manda.TransApi.TransApi;

import org.json.JSONException;
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
    private String lan_From = "zh";
    private String lan_To = "en";

    @Override
    public void setRootView() {
        try {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                Window window = Translation.this.getWindow();

                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

                window.setStatusBarColor(Translation.this.getResources().getColor(R.color.colorPrimary));


            }

        } catch (Exception e) {

            e.printStackTrace();

        }
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
                try {
                    Tran_text.setText(getTrans.getTransResult(text_to_Tran.toString(), lan_From, lan_To));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Tran_text.setVisibility(View.VISIBLE);
                break;
            case R.id.translate_bar_back:                     //取消按钮的监听事件，返回上个界面
                finish();
                break;
            case R.id.spinner:
                String[] choice = getResources().getStringArray(R.array.translate);
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, choice);
                tran_choice.setAdapter(adapter);
                tran_choice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (position == 0) {
                            lan_From = "zh";
                            lan_To = "en";
                        } else {
                            lan_From = "en";
                            lan_To = "zh";
                        }
                    }
                });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
