package com.example.manda.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.manda.DesignPattern.NewWordsAdapter;
import com.example.manda.CountDaoUtils;
import com.example.manda.Data.NewWordsData;
import com.example.manda.DetailEveryday;
import com.example.manda.Manda;
import com.example.manda.R;
import com.example.manda.TestStudy;
import com.example.manda.Translation;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.SimpleSwipeUndoAdapter;

import org.json.JSONException;
import org.json.JSONObject;
import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.ui.BindView;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.utils.KJLoger;

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
    @BindView(id=R.id.everydayWord)
    private TextView everydayword;

    private List<NewWordsData> words=new ArrayList<NewWordsData>();
    private static final String EVERY_DAY_WORD_API = "https://api.ooopn.com/ciba/api.php";



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
        words.add(new NewWordsData((long)3,"522","34111"));
        words.add(new NewWordsData((long)34,"542","34111"));
        words.add(new NewWordsData((long)35,"552","34111"));
        words.add(new NewWordsData((long)33,"562","34111"));
        words.add(new NewWordsData((long)32,"572","34111"));
        words.add(new NewWordsData((long)31,"582","34111"));
        words.add(new NewWordsData((long)36,"592","34111"));
        words.add(new NewWordsData((long)37,"502","34111"));
        words.add(new NewWordsData((long)38,"502","34111"));
        words.add(new NewWordsData((long)39,"5s2","34111"));
        words.add(new NewWordsData((long)30,"5qs2","34111"));
        words.add(new NewWordsData((long)39,"5a2","34111"));
        words.add(new NewWordsData((long)23,"5z2","34111"));
        words.add(new NewWordsData((long)323,"c52","34111"));
        words.add(new NewWordsData((long)213,"5bg2","34111"));
        words.add(new NewWordsData((long)3321,"5ju2","34111"));
        words.add(new NewWordsData((long)33321,"5m2","34111"));
        words.add(new NewWordsData((long)321,"52nb","34111"));
        words.add(new NewWordsData((long)3212,"152","34111"));

        CountDaoUtils.inserCountryList(words);*/
        KJHttp kjh = new KJHttp();
        kjh.get(EVERY_DAY_WORD_API, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                try {
                    JSONObject tmp = new JSONObject(t);
                    String a = tmp.getString("ciba");
                    everydayword.setText(a);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                KJLoger.debug("log:" + t.toString());
            }
        });
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