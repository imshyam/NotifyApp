package com.shyam.notifyapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shyam.listview.R;

/**
 * Created by shyam on 29/6/15.
 */
public class AskFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_ask,container,false);

        return rootView;
    }
}
