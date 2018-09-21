package com.hjn.year_cake.service;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.hjn.year_cake.model.NotificationBean;
import com.hjn.year_cake.enent.Msg;
import com.hjn.year_cake.utils.NotificationUtils;
import com.umeng.message.UmengMessageService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import utilpacket.utils.ActivitiesManager;
import utilpacket.utils.LogUtils;

/**
 * 自定义消息推送
 */

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName();

    private String dataType;
    private String description;
    private String sendTime;
    private NotificationUtils notificationUtils;
    private String customerInfo;
    private NotificationBean notificationBean;

    //  private Realm realm;

    @Override
    public void onMessage(final Context context, Intent intent) {

        notificationUtils = new NotificationUtils(context);

        //可以通过MESSAGE_BODY取得消息体
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            final UMessage msg = new UMessage(new JSONObject(message));

          //  showNotification(context,msg);
            if(msg.extra==null){
                dataType="0";
            }else{
                dataType=msg.extra.get("dataType");
                description=msg.extra.get("description");
                sendTime=msg.extra.get("sendTime");
                LogUtils.e("dataType-->"+dataType);
                notificationBean = JSON.parseObject(message,
                        NotificationBean.class);
                if (dataType.equals("6")){

                    Activity activity = ActivitiesManager.currentActivity();
                    if(null != activity){
                        if(activity instanceof AppCompatActivity){
//                            AuthRewardDialog authRewardDialog = new AuthRewardDialog();
//                            authRewardDialog.show(((AppCompatActivity)activity).getSupportFragmentManager(), "");
                        }
                    }
                }
            }

//            final SimpleDateFormat format = new SimpleDateFormat(
//                    "yyyy年MM月dd日");

          notificationUtils.sendNotification(msg, notificationBean, msg.title, description);

            new Thread(new Runnable() {
                @Override
                public void run() {
//                    Message message;
                    if(!dataType.equals("6")&&!dataType.equals("13")){
//                        PushMessageData.insert("",notificationBean.getMsg_id(), notificationBean.getRandom_min(),msg.text,
//                                notificationBean.getBody().getAfter_open(),notificationBean.getBody().getTicker(),sendTime,notificationBean.getBody().getPlay_sound(),
//                               notificationBean.getExtra().getDataType(), notificationBean.getBody().getTitle(),description);

                        EventBus.getDefault().post(new Msg("umeng"));

                    }
                }
            }).start();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
