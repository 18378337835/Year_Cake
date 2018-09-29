package com.hjn.year_cake.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

import utilpacket.utils.LogUtils;
import utilpacket.utils.PermissionUtils;
import utilpacket.utils.ToastUtils;

/**
 * Created by Year_Cake on 2018/9/4.
 * description: 用于版本更新和强制更新
 */

public class VersionUtil {

    /**
     * 获取APP的版本号 失败为null
       * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;

        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 获取APP的版本号序列  获取失败则为 0 ;
     *
     * @param context
     * @return
     */
    public static int getVersionCoder(Context context) {
        int versionCode = 0;

        try {
            versionCode = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 安装APK
     * @param context  上下文
     * @param filePath apk下载的地址
     */
    public static void installAPK(Activity context, String filePath){
        installAPK(context, new File(filePath));
    }

    public static void installAPK(Activity activity, File file){
        Uri uri;
        if(file ==null){
            return;
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            if(!activity.getPackageManager().canRequestPackageInstalls()){
                PermissionUtils.showDialog(activity,
                                           "安装app需要安装应用来源权限", 0);
                return;
            }
        }
        LogUtils.e("path--->"+file.getAbsolutePath());

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, "com.jrweid.dkzx.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        activity.startActivity(intent);
    }


    /** 根据应用包名，跳转到应用市场
     *
     * @param activity    承载跳转的Activity
     * @param packageName 所需下载（评论）的应用包名
     */
    public static void shareAppShop(Activity activity, String packageName) {
        try {
            Uri uri = Uri.parse("market://details?id="+ packageName);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        } catch (Exception e) {
            ToastUtils.showShort(activity, "您没有安装应用市场");
        }
    }
}
