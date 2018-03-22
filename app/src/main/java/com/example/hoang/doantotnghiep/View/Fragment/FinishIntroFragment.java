package com.example.hoang.doantotnghiep.View.Fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.hoang.doantotnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishIntroFragment extends Fragment {
    ImageView imgleft, imgright;
    Button btnGoHome;
    mHandler mhandler;
    Animation animation_out;
    FinishGuide finishGuide;

    public FinishIntroFragment() {
    }
    @SuppressLint("ValidFragment")
    public FinishIntroFragment(FinishGuide finishGuide) {
        this.finishGuide = finishGuide;
        // Required empty public constructor
    }



    public static FinishIntroFragment newInstance(FinishGuide finishGuide) {
        FinishIntroFragment fragment = new FinishIntroFragment(finishGuide);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finish_intro, container, false);
        btnGoHome = view.findViewById(R.id.btn_go_home);
        mhandler = new mHandler();
        animation_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        imgleft = view.findViewById(R.id.img_left);
        imgright = view.findViewById(R.id.img_right);

        ThreadData threadData = new ThreadData();
        threadData.start();

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishGuide.onFinish();
//                Intent iHome = new Intent(getActivity(), HomeActivity.class);
//                startActivity(iHome);
            }
        });

        return view;
    }

    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    imgright.setImageResource(R.drawable.iconbirthday1);
                    imgleft.setImageResource(R.drawable.iconbirthday);
                    imgleft.setAnimation(animation_out);
                    imgright.setAnimation(animation_out);
                }
                break;
                case 1: {
                    imgleft.setImageResource(android.R.color.transparent);
                    imgright.setImageResource(android.R.color.transparent);
                }
                break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            mhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mhandler.obtainMessage(1);
            mhandler.sendMessage(message);
            super.run();
        }
    }

    public interface FinishGuide{
        void onFinish();
    }
}
