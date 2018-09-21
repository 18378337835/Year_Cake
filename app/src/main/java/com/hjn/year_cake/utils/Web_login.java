package com.hjn.year_cake.utils;

import android.content.Context;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by YearCake on 2018/9/6.
 * 用于SpannableString的网页点击事件
 */

public class Web_login extends ClickableSpan {
    public String murl;
    public Context mcontext;

    public Web_login(Context context, String murl) {
        this.murl = murl;
        this.mcontext=context;
    }

    @Override
    public void onClick(View view) {

//        Intent intent=new Intent(mcontext,WebViewActivity.class);
//        ActivitiesManager.setValueToIntent(intent, SpKey.H5_URL,murl);
//        mcontext.startActivity(intent);
    }
}
