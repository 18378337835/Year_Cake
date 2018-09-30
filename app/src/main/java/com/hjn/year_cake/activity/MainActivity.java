package com.hjn.year_cake.activity;


import android.os.Bundle;

import com.hjn.year_cake.R;
import com.hjn.year_cake.base.BaseActivity;
import com.hjn.year_cake.base.BaseView;
import com.hjn.year_cake.contract.MainPresenter;

public class MainActivity extends BaseActivity<BaseView, MainPresenter>
        implements BaseView {

    private MainPresenter mainPresenter;

    @Override
    public int getLayoutId(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public MainPresenter getPresenter() {
        mainPresenter=new MainPresenter(this,this);
        return mainPresenter;
    }

    @Override
    public void loadSuccess(Object data) {

    }

    @Override
    public void loadFailed(String message) {

    }

    @Override
    public void loadBefore(int message) {

    }

    @Override
    public void loadAfter() {

    }


}
