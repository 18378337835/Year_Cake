package utilpacket.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Koma on 2017/6/5.
 * Description：版本工具类
 * version 1.0.0
 */
public class AppUtils {


    /**
     * 获取APP的版本号 失败为null
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            versionName = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
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
                        "", 0);
                return;
            }
        }
        LogUtils.e("path--->"+file.getAbsolutePath());
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileProvider", file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        LogUtils.e("contentUri--->"+uri);
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


    /**
     * 根据url跳应用市场
     * @param activity
     * @param url
     * @param type
     */
    public static void jumpAppStore(Activity activity, String url, String packageName, int type){
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setComponent(new ComponentName("com.android.vending", "com.google.android.finsky.activities.LaunchUrlHandlerActivity"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if(type == 1){
                if(TextUtils.isEmpty(packageName)){
                    packageName = url.substring(url.lastIndexOf("?")+1);
                }
                intent.setData(Uri.parse("market://details?" + packageName));
            }else{
                intent.setData(Uri.parse(url));
            }

            activity.startActivity(intent);
        } catch (Exception e) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }else {
                ToastUtils.showShort(activity, "您没有安装Google Play");
            }
        }
    }

    public static String getText(String str){
        if(TextUtils.isEmpty(str)){
            return "";
        }else{
            return str;
        }
    }


    public static String assignTimeStr(String realseStr){

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            Date date_cur     = new Date();
            Date date_release = df.parse(realseStr);
            long diff         = date_cur.getTime() - date_release.getTime();//这样得到的差值是微秒级别
            LogUtils.e("diff--->"+diff+"ms");

            long min = diff / (1000*60);
            LogUtils.e("min--->"+min+"分钟");
            if(min / 60 >= 24 * 40){
                return "40天前";
            }else if(min / 60 >= 24){
                return "1天前";
            }else{
                if(min/60==0){
                    if(min==0){
                        return "刚刚";
                    }else{
                        return min+"分钟前";
                    }
                }else{
                    return min/60+"小时前";
                }
            }
        }catch (Exception e){
            LogUtils.e(e.getMessage());
        }
        return realseStr;
    }

    public static String getOpptionInfo(String str, com.alibaba.fastjson.JSONObject jsonObject) throws JSONException
    {
        String info="";
        if(TextUtils.isEmpty(str)){
            info="未知";
        }else {
            List<String> keys = StringUtils.sortList(
                    getJsonObjectKey(jsonObject));
            Map<String, String> map = new HashMap<>();
            for(int i = 0; i < keys.size(); i++){
                map.put(keys.get(i), jsonObject.getString(keys.get(i)));
            }
            info=map.get(str);
        }
        return info;
    }

    public static String getArrayInfo(String str, JSONArray jsonArray) throws JSONException {
        String info="";
        try{
            if(Integer.parseInt(str) <= jsonArray.size()){
                int type = Integer.parseInt(str);
                info = jsonArray.get(type).toString();
            }else{
                info="";
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return info;
    }
    /**
     * 获取jsonobject的key值
     * @param json
     * @return
     */
    public static List<String> getJsonObjectKey(com.alibaba.fastjson.JSONObject json){
        LogUtils.e("json--->"+json.toJSONString());
        List<String> keys = new ArrayList<>();
        LinkedHashMap<String, String> linkedHashMap = JSON.parseObject(json.toString(),
                                                                       new TypeReference<LinkedHashMap<String, String>>() {
                                                                       });
        for (Map.Entry<String, String> entry : linkedHashMap.entrySet()) {
            keys.add(entry.getKey());
            LogUtils.e(entry.getKey() + ":" + entry.getValue());
        }
        return keys;
    }

    //根据value值获取到对应的一个key值
    public static String getKey(String value, com.alibaba.fastjson.JSONObject jsonObject) throws JSONException {
        String key = null;
        if(TextUtils.isEmpty(value) || value.equals("0")){
            key = "0";
        }else {
            List<String> keys = getJsonObjectKey(jsonObject);
            Map<String, String> map = new HashMap<>();
            for(int i = 0; i < keys.size(); i++){
                map.put(keys.get(i), jsonObject.getString(keys.get(i)));
            }
            for (String getKey : map.keySet()) {
                if (map.get(getKey).equals(value)) {
                    key = getKey;
                }
            }
        }
        return key;
    }

    public static String bitmap2Base64String(Bitmap bm) {
        if(null == bm){
            return "";
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, bos);// 参数100表示不压缩
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }
    /**
     * 判断是否存在sd卡
     *
     * @return
     */
    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED) ? true : false;
    }

    //字符串转时间戳
    public static String getTime(String timeString){
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }
}
