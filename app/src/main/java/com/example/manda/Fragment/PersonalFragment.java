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

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.widget.RoundImageView;

public class PersonalFragment extends KJFragment {
    @BindView(id=R.id.personal_rl_favor, click = true)
    private RelativeLayout p_favor;
    @BindView(id=R.id.personal_rl_course, click = true)
    private RelativeLayout p_course;
    @BindView(id=R.id.personal_rl_feedback, click = true)
    private RelativeLayout p_feedback;
    @BindView(id=R.id.personal_rl_question, click = true)
    private RelativeLayout p_question;
    @BindView(id=R.id.personal_rl_setting, click = true)
    private RelativeLayout p_setting;
    @BindView(id = R.id.find_img_head, click = true)
    private RoundImageView mImgHead;



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
                Intent intent5=new Intent(getActivity(),feedback.class);
                startActivity(intent5);
                break;
            case R.id.personal_rl_course:
                Intent intent=new Intent(getActivity(),feedback.class);
                startActivity(intent);
                break;
            case R.id.personal_rl_feedback:
                Intent intent2=new Intent(getActivity(),feedback.class);
                startActivity(intent2);
                break;
            case R.id.personal_rl_question:
                Intent intent3=new Intent(getActivity(),feedback.class);
                startActivity(intent3);
                break;
            case R.id.personal_rl_setting:
                Intent intent4=new Intent(getActivity(),feedback.class);
                startActivity(intent4);
                break;
            case R.id.find_img_head:
                Intent intent6=new Intent(getActivity(),accounter.class);
                startActivity(intent6);
                break;
            default:
                break;
        }
    }
}
