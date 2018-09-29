package com.hjn.year_cake.utils;

import android.content.Context;
import android.support.annotation.Nullable;

import com.hjn.year_cake.manager.Api;
import com.hjn.year_cake.manager.Constant;
import com.hjn.year_cake.manager.MyApp;
import com.jrwd.okhttputils.OkHttpUtils;
import com.jrwd.okhttputils.callback.StringCallback;
import okhttp3.Request;
import okhttp3.Response;
import utilpacket.utils.DeviceUtil;
import utilpacket.utils.LogUtils;

/**
 * Created by Year_Cake on 2018/9/4.
 * descripton: 统计埋点
 */


public class StatisticsUtils {
    /**
     * 埋点统计
     * @param context
     */
    public static void tjPoint(final Context context, final String type, final String oper_type){
        LogUtils.e("统计了 position-->"+type+"oper_type-->"+oper_type);
        String data = Constant.getParams()
                              .setParams("position", type)
                              .setParams("device_id", DeviceUtil.getUniqueId(context.getApplicationContext()))
                              .setParams("device_type", "2")
                              .setParams("channel", MyApp.channel_no)
                              .setParams("oper_type", oper_type)
                              .build();
        OkHttpUtils.post(Api.TJ_POINT)
                   .params("encryData",data)
                   .execute(new StringCallback() {
                       @Override
                       public void onResponse(boolean b,
                                              String s,
                                              Request request,
                                              @Nullable Response response)
                       {

                       }
                   });

    }
}
