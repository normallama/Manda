package com.example.manda.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.manda.Login;
import com.example.manda.R;
import com.example.manda.User;
import com.example.manda.accounter;
import com.example.manda.feedback;
import com.example.manda.register;
import com.zhy.autolayout.AutoRelativeLayout;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.widget.RoundImageView;

public class PersonalFragment extends KJFragment {
    @BindView(id=R.id.personal_rl_favor, click = true)
    private AutoRelativeLayout p_favor;
    @BindView(id=R.id.personal_rl_course, click = true)
    private AutoRelativeLayout p_course;
    @BindView(id=R.id.personal_rl_question, click = true)
    private AutoRelativeLayout p_question;
    @BindView(id=R.id.personal_rl_setting, click = true)
    private AutoRelativeLayout p_setting;
    @BindView(id=R.id.personal_rl_wallet,click = true)
    private AutoRelativeLayout p_wallet;
    @BindView(id=R.id.personal_rl_invite,click = true)
    private AutoRelativeLayout p_invite;



    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
                                Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.frag_personal, null);
        return view;
    }

    @Override
    protected void initData(){
        super.initData();
    }

    @Override
    protected void initWidget(View parentView){
        super.initWidget(parentView);
    }

    protected void widgetClick(View v){
        super.widgetClick(v);
        switch(v.getId()){
            case R.id.personal_rl_favor:
                Intent intent1=new Intent(getActivity(),feedback.class);
                startActivity(intent1);
                break;
            case R.id.personal_rl_wallet:
                Intent intent2=new Intent(getActivity(),feedback.class);
                startActivity(intent2);
                break;
            case R.id.personal_rl_course:
                Intent intent3=new Intent(getActivity(),feedback.class);
                startActivity(intent3);
                break;
            case R.id.personal_rl_invite:
                Intent intent4=new Intent(getActivity(),feedback.class);
                startActivity(intent4);
                break;
            case R.id.personal_rl_question:
                Intent intent5=new Intent(getActivity(),feedback.class);
                startActivity(intent5);
                break;
            case R.id.personal_rl_setting:
                Intent intent6=new Intent(getActivity(),feedback.class);
                startActivity(intent6);
                break;
            default:
                break;
        }
    }
}
