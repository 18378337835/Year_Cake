package com.hjn.year_cake.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.hjn.year_cake.model.NotificationBean;
import com.umeng.message.entity.UMessage;

/**
 * @author Year_Cake
 * @date 2018/6/19
 * @describe 消息通知类
 */

public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";
    private Context context;

    public NotificationUtils(Context context){
        super(context);
        this.context = context;
    }

    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(id, name,
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setShowBadge(false);
            channel.setSound(null, null);
            getManager().createNotificationChannel(channel);
        }
    }

    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public Notification.Builder getChannelNotification(UMessage msg, NotificationBean notificationBean,
                                                       String title, String content){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return new Notification.Builder(getApplicationContext(), id)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setSmallIcon(context.getApplicationInfo().icon)
                    .setContentIntent(getPendingIntent(msg, notificationBean))
                    .setAutoCancel(true)
                    .setDefaults(~0)
                    .setPriority(Notification.PRIORITY_HIGH);
        }
        return null;
    }

    public NotificationCompat.Builder getNotification(UMessage msg, NotificationBean notificationBean,
                                                         String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(context.getApplicationInfo().icon)
                .setContentIntent(getPendingIntent(msg,notificationBean))
                .setAutoCancel(true)
                .setDefaults(~0)
                .setPriority(Notification.PRIORITY_HIGH);
    }

    public void sendNotification(UMessage msg, NotificationBean notificationBean, String title, String content){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (msg, notificationBean, title, content).build();
            if(null != notification){
                notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
                getManager().notify(1, notification);
                ((Service)context).startForeground(678567400, notification);
            }
        }else{
            Notification notification = getNotification(msg, notificationBean, title, content).build();
            notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音

            getManager().notify(1, notification);
        }
    }


    public PendingIntent getPendingIntent(UMessage msg, NotificationBean notificationBean){
        Intent resultIntent;
        PendingIntent resultPendingIntent = null;
        if(null != msg.extra){
            if(msg.extra.get("dataType").equals("13")){
//                resultIntent = new Intent(context,
//                                          GrabOrderDetailAct.class);
//                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                resultIntent.putExtra("orderID", notificationBean.getExtra().getCustomerInfo());
//                resultPendingIntent = PendingIntent.getActivity(
//                        context, 0, resultIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
            }else if (msg.extra.get("dataType").equals("3")){
//                resultIntent = new Intent(context,
//                                          WebViewActivity.class);
//                String banalce = SPUtil.getString(this, SpKey.BALANCE, "");
//                resultIntent.putExtra(SpKey.H5_URL,banalce);
//                resultIntent.putExtra("rightText","余额明细");
//                resultIntent.putExtra("rightUrl",SPUtil.getString(this, SpKey.BALANCE_DETAIL, ""));
//                resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                resultPendingIntent = PendingIntent.getActivity(
//                        context, 0, resultIntent,
//                        PendingIntent.FLAG_UPDATE_CURRENT);
            }
        }
        return resultPendingIntent;
    }
}
