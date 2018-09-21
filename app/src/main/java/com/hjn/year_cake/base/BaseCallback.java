package com.hjn.year_cake.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hjn.year_cake.R;
import com.hjn.year_cake.manager.SpKey;
import com.hjn.year_cake.model.HttpRespone;
import com.jrwd.okhttputils.OkHttpUtils;
import com.jrwd.okhttputils.callback.AbsCallback;
import com.jrwd.okhttputils.request.BaseRequest;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;
import utilpacket.utils.AESUtils;
import utilpacket.utils.ActivitiesManager;
import utilpacket.utils.LogUtils;
import utilpacket.view.LoadingDialog;


public abstract class BaseCallback<T> extends AbsCallback<T> {

    private Class<T> clazz;
    private Context context;
    private boolean isShow;          //是否显示菊花

    /**
     * 需要传入是否显示菊花
     * @param mContext
     * @param clazz
     * @param isShow
     */
    public BaseCallback(Context mContext, Class<T> clazz, boolean isShow){
        this.clazz = clazz;
        this.context = mContext;
        this.isShow = isShow;
    }

    /**
     * 默认不显示菊花
     * @param clazz
     */
    public BaseCallback(Class<T> clazz){
        this.clazz = clazz;
    }

    @Override
    public T parseNetworkResponse(Response response) throws Exception {

        String responseData = response.body().string();
        if (TextUtils.isEmpty(responseData)){
            return null;
        }

        JSONObject obj = new JSONObject(responseData);
        int code = obj.optInt("code");
        final String message = obj.optString("message");

        String data = "";
        if(!TextUtils.isEmpty(obj.optString("data"))){
            data = obj.optString("data");
            if(!TextUtils.isEmpty(data)){
                try{
                    data = new AESUtils().decrypt(data);
                    LogUtils.e(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        LogUtils.e("code--->"+code+"   msg--->"+message+"   data--->"+data);

        if(null != context && isShow){
            LoadingDialog.dismiss(context);
        }
        if(code == 401){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkHttpUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                    try {
                        ActivitiesManager.closeAll();
                        Intent intent = new Intent(OkHttpUtils.getContext(),
                                                   Class.forName("com.jrweid.dkzx.activity.LoginActivity"));
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);

                        SharedPreferences sp = OkHttpUtils.getContext().getSharedPreferences("share_data",
                                                                                             Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("token", "");
                        editor.putBoolean("islogin", false);
                        editor.putString("userCity", "");
                        editor.putString("mOrder_range", "");
                        editor.putString("mApply_amount", "");
                        editor.putString("mSalary", "");
                        editor.putString("mProperty", "");
                        editor.putString("order_position", "");
                        editor.putString("amount_position", "");
                        editor.putString("salary_position", "");
                        editor.putString("property_position", "");
                        editor.putBoolean(SpKey.ISINCENTER, false);
                        editor.commit();
                        OkHttpUtils.getContext().startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });
            return null;
        }
        if(code != 200){
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkHttpUtils.getContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }else{
            if (clazz == String.class){
                return (T) data;
            }

            if(clazz == HttpRespone.class){
                return JSON.parseObject(responseData, clazz);
            }
            if (clazz != null){
                return JSON.parseObject(data, clazz);
            }
            return null;
        }
    }

    @Override
    public void onError(boolean isFromCache, Call call, @Nullable final Response response, @Nullable Exception e) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(OkHttpUtils.getContext(), R.string.server_unconnect, Toast.LENGTH_SHORT).show();
            }
        });
        super.onError(isFromCache, call, response, e);

    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        if(null != context && isShow){
            LoadingDialog.show(context, false);
        }
    }

    @Override
    public void onAfter(boolean isFromCache, @Nullable T t, Call call, @Nullable Response response, @Nullable Exception e) {
        super.onAfter(isFromCache, t, call, response, e);
        if(null != context && isShow){
            LoadingDialog.dismiss(context);
        }
    }
}
