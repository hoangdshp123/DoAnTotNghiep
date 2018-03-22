package com.example.hoang.doantotnghiep.View.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.hoang.doantotnghiep.R;
import com.example.hoang.doantotnghiep.View.activitys.IntroActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {
    Button btnStart;


    public WelcomeFragment() {
        // Required empty public constructor
    }

    public static WelcomeFragment newInstance() {
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        return welcomeFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        btnStart = view.findViewById(R.id.btn_start);
        btnStart.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                ((IntroActivity) getActivity()).nextFragment();
                break;
        }
    }
}
