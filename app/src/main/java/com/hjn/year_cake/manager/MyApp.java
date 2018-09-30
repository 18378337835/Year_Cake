package com.hjn.year_cake.manager;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import com.hjn.year_cake.service.MyPushIntentService;
import com.jrwd.okhttputils.OkHttpUtils;
import com.jrwd.okhttputils.cookie.store.PersistentCookieStore;
import com.jrwd.okhttputils.model.HttpHeaders;
import com.jrwd.okhttputils.model.HttpParams;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;

import utilpacket.utils.AppUtils;
import utilpacket.utils.LogUtils;
import utilpacket.utils.SPUtil;
import utilpacket.utils.StringUtils;


/**
 * Created by Year_Cake on 2018/8/28.
 * decription:
 */

public class MyApp extends Application implements Thread.UncaughtExceptionHandler{
    private static String appName;

    public static Context context;

    public static String app_no="Dk";
    public static String channel_no="Ba";
    private static MyApp mInstance = null;
    {
        PlatformConfig.setWeixin("wx788cb56854977112", "61dd2fa0d524269789745c0d5a0edd8b");
        PlatformConfig.setQQZone("1107808386", "dfKN8Cr8CGkgFs0Z");
    }

    public static Context getInstance(){
        return context;
    }

    public static MyApp getMyApp() {
        return mInstance;
    }
    public static String getAppName(){
        return appName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        getChannel(context);
        LogUtils.isDebug = false;
        mInstance = this;
        UMConfigure.setLogEnabled(true);
        //初始化组件化基础库, 统计SDK/推送SDK/分享SDK都必须调用此初始化接口

        UMConfigure.init(this,2,"a81b04e6d147bd316df740b741c011ff");
        initPushAgent();
        intHttp();
        Thread.setDefaultUncaughtExceptionHandler(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycle());
        /**
         * Bugly
         */
        CrashReport.initCrashReport(getApplicationContext(), "52c8275016", true);

    }

    private void intHttp() {
        OkHttpUtils.init(this);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("Authorization", SPUtil.getString(context, SpKey.TOKEN, ""));

        HttpParams httpParams = new HttpParams();
        httpParams.put("appVersion", AppUtils.getVersionName(this));
        httpParams.put("app_no", app_no);
        httpParams.put("channel_no", channel_no);
        httpParams.put("clientOs", "2");
        httpParams.put("scene_id", "2");

        OkHttpUtils.getInstance()
                .addCommonHeaders(httpHeaders)
                .addCommonParams(httpParams)
                .setConnectTimeout(10 * 1000)
                .setReadTimeOut(10 * 1000)
                .setWriteTimeOut(10 * 1000)
                .setCookieStore(new PersistentCookieStore(this));
//                .debug("DKZX_HTTP");
    }

    private void initPushAgent() {

        PushAgent mPushAgent = PushAgent.getInstance(this);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                LogUtils.e("deviceToken-->"+deviceToken);
                SPUtil.putString(getApplicationContext(), SpKey.DEVICE_TOKEN, deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {

            }
        });
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
    }

    //获取渠道号
    //获取渠道号和应用号
    private void getChannel(Context context){
        PackageManager packageManager = context.getPackageManager();

        try {
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if(!StringUtils.isEmpty(applicationInfo.metaData.getString("CHANNEL_NO"))){
                channel_no = applicationInfo.metaData.getString("CHANNEL_NO");
                SPUtil.putString(context, SpKey.CHANNEL, applicationInfo.metaData.getString("CHANNEL_NO"));
            }
            if(!StringUtils.isEmpty(applicationInfo.metaData.getString("APP_NO"))){
                app_no = applicationInfo.metaData.getString("APP_NO");
                SPUtil.putString(context, SpKey.APP_NO, applicationInfo.metaData.getString("APP_NO"));
            }
            if(!StringUtils.isEmpty(applicationInfo.metaData.getString("APP_NAME"))){
                appName = applicationInfo.metaData.getString("APP_NAME");
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        LogUtils.e("application捕获到异常:" + thread + "\n" + ex);
//        try {
//            Intent intent = new Intent(ActivitiesManager.currentActivity(), SplashActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            ActivitiesManager.closeAll();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
