package com.example.manda.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manda.Data.NewWordsData;
import com.example.manda.R;
import com.nhaarman.listviewanimations.ArrayAdapter;
import com.nhaarman.listviewanimations.appearance.StickyListHeadersAdapterDecorator;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo.UndoAdapter;

import java.util.ArrayList;
import java.util.List;



public class NewWordsAdapter extends ArrayAdapter<String> implements UndoAdapter{

    private final Context mContext;
    private List<NewWordsData> wordsData;

    public NewWordsAdapter(final Context context,List<NewWordsData> w) {
        this.mContext = context;
        this.wordsData = w;
        for (int i = 0; i < w.size(); i++) {
            add(String.valueOf(i));
        }
    }

    @Override
    public long getItemId(final int position) {
        return getItem(position).hashCode();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布局，所以第二个参数通常设置为null
            convertView = LayoutInflater.from(mContext).inflate(R.layout.mynewwords, parent, false);

            //对viewHolder的属性进行赋值
            viewHolder.name = (TextView) convertView.findViewById(R.id.newword_show);
            viewHolder.type = (TextView) convertView.findViewById(R.id.word_interpre);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        } else {//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 取出word对象
        NewWordsData word = wordsData.get(Integer.parseInt((getItem(position))));

        // 设置控件的数据
        viewHolder.name.setText(word.getWord());
        viewHolder.type.setText(word.getInterpre());

        return convertView;

    }

    @NonNull
    @Override
    public View getUndoView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.undo_row, parent, false);
        }
        return view;
    }

    @NonNull
    @Override
    public View getUndoClickView(@NonNull final View view) {
        return view.findViewById(R.id.undo_row_undobutton);
    }


    // ViewHolder用于缓存控件，两个属性分别对应item布局文件的两个控件
    class ViewHolder {
        public TextView name;
        public TextView type;
    }
}