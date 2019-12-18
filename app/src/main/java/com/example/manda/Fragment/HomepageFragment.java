package com.example.manda.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.manda.Adapter.NewWordsAdapter;
import com.example.manda.CountDaoUtils;
import com.example.manda.Data.NewWordsData;
import com.example.manda.DetailEveryday;
import com.example.manda.Manda;
import com.example.manda.R;
import com.example.manda.TestStudy;
import com.example.manda.Translation;
import com.example.manda.feedback;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingRightInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.widget.KJSlidingMenu;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends KJFragment {
    @BindView(id=R.id.homepage_choice_left,click = true)
    private TextView exercise;
    @BindView(id=R.id.homepage_choice_right,click = true)
    private TextView mynewwords;
    @BindView(id=R.id.search_word,click = true)
    private EditText searchword;
    @BindView(id=R.id.search_button,click = true)
    private Button search;
    @BindView(id=R.id.exercise)
    private ScrollView myexercise;
    @BindView(id=R.id.wordlist)
    private DynamicListView wordlistview;
    @BindView(id=R.id.detail,click=true)
    private Button detail;
    @BindView(id=R.id.continue_study,click=true)
    private Button learning;

    private List<NewWordsData> words=new ArrayList<NewWordsData>();


    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
                                Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.frag_homepage, null);
        return view;
    }

    @Override
    protected void initData(){
        super.initData();
        //假数据
        /*words.add(new NewWordsData((long)1,"22","111"));
        words.add(new NewWordsData((long)2,"2","11431"));
        words.add(new NewWordsData((long)3,"52","34111"));*/
        CountDaoUtils.inserCountryList(words);
    }

    @Override
    protected void initWidget(View parentView){
        super.initWidget(parentView);
    }

    public void widgetClick(View v){
        super.widgetClick(v);
        switch(v.getId()){
            case R.id.homepage_choice_left:
                myexercise.setVisibility(View.VISIBLE);
                wordlistview.setVisibility(View.GONE);
                break;
            case R.id.homepage_choice_right:
                myexercise.setVisibility(View.GONE);
                wordlistview.setVisibility(View.VISIBLE);
                initWords();
                break;
            case R.id.search_word:
                Intent search = new Intent(getActivity(), Translation.class);
                startActivity(search);
                break;
            case R.id.detail:
                Intent detail = new Intent(getActivity(), DetailEveryday.class);
                startActivity(detail);
                break;
            case R.id.continue_study:
                Intent a = new Intent(getActivity(), TestStudy.class);
                startActivity(a);
                break;
            default:
                break;
        }
    }

    private void initWords(){
        //NewWordsData
        List<NewWordsData> words2;
        words2 = CountDaoUtils.queryAllCountry();
        final NewWordsAdapter myAdapter = new NewWordsAdapter(Manda.getContext(),words2);
        SimpleSwipeUndoAdapter swipeUndoAdapter = new SimpleSwipeUndoAdapter(myAdapter, Manda.getContext(),
                new OnDismissCallback() {
                    @Override
                    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {
                            CountDaoUtils.deleteCountry(myAdapter.getWordId(position));
                            myAdapter.remove(position);
                        }
                    }
                }
        );
        swipeUndoAdapter.setAbsListView(wordlistview);
        wordlistview.setAdapter(swipeUndoAdapter);
        wordlistview.enableSimpleSwipeUndo();
    }

}