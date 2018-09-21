package com.hjn.year_cake.activity;

import android.os.Bundle;

import com.hjn.year_cake.R;
import com.hjn.year_cake.base.BaseActivity;
import com.hjn.year_cake.base.BasePresenter;

/**
 * Created by YearCake on 2018/9/21.
 * description:
 * version  1.0.0
 */

public class SplashActivity extends BaseActivity{
    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_splash;
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }
}
