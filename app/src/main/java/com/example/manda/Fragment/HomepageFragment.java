package com.example.manda.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.manda.R;
import com.example.manda.feedback;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.widget.KJSlidingMenu;

public class HomepageFragment extends KJFragment {
    @BindView(id=R.id.titlebar_img_tool , click = true)
    private ImageView tools;
    @BindView(id=R.id.titlebar_img_search, click = true)
    private  ImageView search;

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
                Intent intent5=new Intent(getActivity(),feedback.class);
                startActivity(intent5);
                break;
            default:
                break;
        }
    }
}