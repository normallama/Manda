package com.example.manda.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.manda.R;
import com.example.manda.Translation;
import com.example.manda.feedback;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.widget.KJSlidingMenu;

public class HomepageFragment extends KJFragment {
    @BindView(id=R.id.titlebar_img_tool , click = true)
    private ImageView tools;
    @BindView(id=R.id.titlebar_img_search, click = true)
    private  ImageView search;
    @BindView(id=R.id.homepage_translate, click = true)
    private  ImageView translate;
    @BindView(id=R.id.homepage_newwords, click = true)
    private  ImageView newwords;
    @BindView(id=R.id.homepage_dictionary, click = true)
    private  ImageView dicitionary;
    @BindView(id=R.id.homepage_practice, click = true)
    private  ImageView practice;

    private int count=1;  //辅助判断侧滑框

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
                                Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.frag_homepage, null);
        return view;
    }
    @BindView(id = R.id.main_group)
    private KJSlidingMenu slidingMenu;

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
            case R.id.titlebar_img_tool:
                count=-count;
                if(count==-1) {
                    slidingMenu.openMenu();
                }
                else {
                    slidingMenu.closeMenu();
                }
                break;
            case R.id.titlebar_img_search:
                Intent intent1=new Intent(getActivity(),feedback.class);
                startActivity(intent1);
                break;
            case R.id.homepage_translate:
                Intent intent2=new Intent(getActivity(),Translation.class);
                startActivity(intent2);
                break;
            case R.id.homepage_newwords:
                Intent intent3=new Intent(getActivity(),feedback.class);
                startActivity(intent3);
                break;
            case R.id.homepage_dictionary:
                Intent intent4=new Intent(getActivity(),feedback.class);
                startActivity(intent4);
                break;
            case R.id.homepage_practice:
                Intent intent5=new Intent(getActivity(),feedback.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }
}