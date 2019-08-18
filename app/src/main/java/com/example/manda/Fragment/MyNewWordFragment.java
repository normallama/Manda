package com.example.manda.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manda.R;

import org.kymjs.kjframe.ui.KJFragment;

public class MyNewWordFragment extends KJFragment {

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
                                Bundle bundle) {
        View view = View.inflate(getActivity(), R.layout.my_newword, null);
        return view;
    }

}
