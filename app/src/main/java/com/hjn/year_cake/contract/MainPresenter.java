package com.hjn.year_cake.contract;

import android.content.Context;
import android.support.annotation.Nullable;

import com.hjn.year_cake.base.BaseCallback;
import com.hjn.year_cake.base.BasePresenter;
import com.hjn.year_cake.base.BaseView;
import com.hjn.year_cake.manager.Api;
import com.hjn.year_cake.manager.Constant;
import com.hjn.year_cake.model.HttpRespone;
import com.jrwd.okhttputils.OkHttpUtils;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by YearCake on 2018/9/21.
 * description:
 * version v1.0 content:
 */

public class MainPresenter extends BasePresenter<BaseView> {
    private Context context;
    private BaseView baseView;
    public MainPresenter(Context context,BaseView baseView) {
        this.context=context;
        this.baseView=baseView;
    }

    /**
     * 获取首页选项数据
     */
    public void getAuthStatus(){
        String data = Constant.getParams()
                .build();
        OkHttpUtils.post(Api.GET_AUTH_STATUS)
                .params("encryData",data)
                .execute(new BaseCallback<HttpRespone>(HttpRespone.class) {
                    @Override
                    public void onResponse(boolean b,
                                           HttpRespone httpRespone,
                                           Request request,
                                           @Nullable Response response)
                    {
                        if(null != httpRespone){
                            baseView.loadSuccess("成功");
                        }
                    }
                });
    }
}
