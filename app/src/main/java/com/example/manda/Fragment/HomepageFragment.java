package com.example.manda.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manda.R;
import com.example.manda.Translation;
import com.example.manda.feedback;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.widget.KJSlidingMenu;

public class HomepageFragment extends KJFragment {
    @BindView(id=R.id.homepage_choice_left,click = true)
    private TextView exercise;
    @BindView(id=R.id.homepage_choice_right,click = true)
    private TextView mynewwords;
    @BindView(id=R.id.search_word,click = true)
    private EditText searchword;
    @BindView(id=R.id.search,click = true)
    private ImageView search;

    private MyNewWordFragment newword;


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
                                Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.frag_homepage, null);
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

    public void widgetClick(View v){
        super.widgetClick(v);
        switch(v.getId()){
            case R.id.homepage_choice_left:
                break;
            case R.id.homepage_choice_right:
                break;
            case R.id.search:
                break;
            default:
                break;
        }
    }
}