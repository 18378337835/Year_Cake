package com.hjn.year_cake.utils;

import android.app.Activity;
import android.os.SystemClock;
import android.text.TextUtils;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.hjn.year_cake.model.AliPayResult;
import com.hjn.year_cake.model.PayInfoResponse;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;


/**
 * Created by Year_Cake on 2018/9/4.
 * descripton: 用于支付宝和微信支付
 */

public class PayUtils {
    private IWXAPI api;
    private Activity mContext;
    private onAliPayListener listener;
    private PayInfoResponse data;

    public PayUtils(Activity mContext, onAliPayListener listener){
        this.mContext=mContext;
        this.listener=listener;
    }

    public void aliPay(final String payInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mContext);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                AliPayResult payResult = new AliPayResult(result);
                String resultStatus = payResult.getResultStatus();

                if (TextUtils.equals(resultStatus, "9000")) {//支付成功
                    listener.onAliPaySuccess();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {//支付结果确认中
                        listener.onAliPayUnknown();
                    } else {//支付失败
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        listener.onAliPayFail();
                    }
                }
            }
        }).start();
    }

    private String platform;
    private int vip_type;

    public void wXPay(PayInfoResponse data, final IWXAPI api,String platform,int vip_type) {
        this.api=api;
        this.data=data;
        this.platform=platform;
        this.vip_type=vip_type;
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if(!api.isWXAppInstalled()){
            Toast.makeText(mContext,"请先安装微信",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isPaySupported){
            Toast.makeText(mContext,"正在启动微信",Toast.LENGTH_SHORT).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    api.openWXApp();
                    SystemClock.sleep(4000);
                    wxPay();
                }
            }).start();
        }else {
            wxPay();
        }
    }

    private void wxPay(){
        PayReq req = new PayReq();
        req.appId = data.appid;
        req.partnerId = data.partnerid;
        req.prepayId = data.prepayid;
        req.nonceStr = data.noncestr;
        req.timeStamp = data.timestamp;
        req.packageValue = "Sign=WXPay";
        req.sign = data.sign;
        req.extData = "app data"; // optional
        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
        // 将该app注册到微信
        api.sendReq(req);

    }


    public interface onAliPayListener{
        void onAliPaySuccess();

        void onAliPayFail();

        void onAliPayUnknown();
    }
}
