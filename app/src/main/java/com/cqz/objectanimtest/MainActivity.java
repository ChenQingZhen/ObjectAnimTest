package com.cqz.objectanimtest;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.ListViewCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RelativeLayout mHeader;
    private LinearLayout mSearch;
    private ListView mTestLv;
    private int mScreenHeight;
    private int mHeaderHeight;
    private int mSearchHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    private void initView() {
        mHeaderHeight = dip2px(56);
        mSearchHeight = dip2px(48);
        mHeader = (RelativeLayout) findViewById(R.id.header);
        mSearch = (LinearLayout) findViewById(R.id.search);
        mTestLv = (ListView) findViewById(R.id.lv_test);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mTestLv.getLayoutParams();
        layoutParams.setMargins(0, mSearchHeight + mSearchHeight, 0, 0);
        mTestLv.setLayoutParams(layoutParams);
        Button btnAnim = (Button) findViewById(R.id.btn_anim);
        btnAnim.setOnClickListener(new View.OnClickListener() {
            boolean flag = false;

            @Override
            public void onClick(View view) {
                if (!flag) {
                    ObjectAnimator.ofFloat(mHeader, "TranslationY", -mHeader.getHeight()).setDuration(300).start();
                    ObjectAnimator.ofFloat(mSearch, "TranslationY", -mHeader.getHeight()).setDuration(300).start();
                    ValueAnimator containerHeightAnim = ValueAnimator.ofInt(mSearchHeight + mSearchHeight, mSearchHeight);
                    containerHeightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int val = (Integer) animation.getAnimatedValue();
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mTestLv.getLayoutParams();
                            layoutParams.setMargins(0, val, 0, 0);
                            mTestLv.setLayoutParams(layoutParams);
                        }
                    });
                    containerHeightAnim.setDuration(300);
                    containerHeightAnim.start();
                    flag = true;
                } else {
                    ObjectAnimator.ofFloat(mHeader, "TranslationY", 0).setDuration(300).start();
                    ObjectAnimator.ofFloat(mSearch, "TranslationY", 0).setDuration(300).start();
                    ValueAnimator containerHeightAnim = ValueAnimator.ofInt(mSearchHeight, mSearchHeight + mSearchHeight);
                    containerHeightAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int val = (Integer) animation.getAnimatedValue();
                            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mTestLv.getLayoutParams();
                            layoutParams.setMargins(0, val, 0, 0);
                            mTestLv.setLayoutParams(layoutParams);
                        }
                    });
                    containerHeightAnim.setDuration(300);
                    containerHeightAnim.start();
                    flag = false;
                }
            }
        });
    }

    private void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenHeight = dm.heightPixels;
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("item" + i);
        }
        mTestLv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList));
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
