package com.hjn.year_cake.utils;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Toast;
import com.hjn.year_cake.R;
import com.hjn.year_cake.manager.MyApp;
import com.hjn.year_cake.model.UpdateResponse;
import com.jrwd.okhttputils.OkHttpUtils;
import com.jrwd.okhttputils.callback.FileCallback;
import com.jrwd.okhttputils.request.BaseRequest;
import java.io.File;
import java.net.URLDecoder;

import anet.channel.util.StringUtils;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import utilpacket.utils.AppUtils;
import utilpacket.utils.NetUtils;
import utilpacket.utils.PermissionUtils;
import utilpacket.utils.ToastUtils;

/**
 * Created by Year_Cake on 2018/9/4.
 * descripton: 更新工具类
 */

public class UpdateUtil {
    public static final int PERMISSION_ALBUM = 100;

    private Activity       mContext;
    private MyApp app;
    private UpdateResponse updateInfo;
    private Context        context;

    private ProgressDialog pBar;
    private String apkStorePath;
    private String fileName;

    public UpdateUtil(Activity mContext, UpdateResponse updateInfo) {
        this.mContext = mContext;
        this.app = MyApp.getMyApp();
        this.updateInfo = updateInfo;
    }

    /*
     *enterance 判断该方法的调用时机
     * true表示该方法检测之后需要弹出已是最新版本的提示
     * false表示不需要
     *
     * */

    public void update(boolean enterance) {

        String serverVersion = updateInfo.getResult().getVersionNumber();
        String clientVersion = AppUtils.getVersionName(mContext);
        serverVersion = serverVersion.replace(".","");
        clientVersion = clientVersion.replace(".","");

        if(Integer.parseInt(clientVersion) < Integer.parseInt(serverVersion)){
            if (StringUtils.isBlank(updateInfo.getResult().getFileUrl())) {
                Toast.makeText(mContext, "检测失败", Toast.LENGTH_SHORT);
                return;
            }
            String msg = !TextUtils.isEmpty(updateInfo.getResult().getUpdateDesc()) ? updateInfo.getResult().getUpdateDesc() : "发现新版本";
            String title = !TextUtils.isEmpty(updateInfo.getResult().getUpdateTitle()) ? updateInfo.getResult().getUpdateTitle(): "软件更新";
            String cancelTitle = updateInfo.getResult().getIsForceUpdate().equals("1") ? "退出" : "取消";
            DialogUtil.showUpdateDialog(mContext, title, msg, cancelTitle, "确定", new DialogUtil.OnDialogListener() {
                @Override
                public void onSure() {
                    if(PermissionUtils.checkPermission(null, mContext,
                                                       Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                       "下载安装包需要获取读取存储权限，请设置", 101)){
                        DialogUtil.dismiss();
                        onUpdateApp(updateInfo.getResult().getFileUrl());
                    }
                }

                @Override
                public void onCancel() {

                    if(updateInfo.getResult().getIsForceUpdate().equals("1")){
                        //						 ExitUtil.exitApp(mContext);
                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                }
            });
        }else{
            if(enterance) {
                Toast.makeText(mContext, "已是最新版本", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void onUpdateApp(String url) {

        if (NetUtils.isConnected(mContext)) {
            String apkName;
            if(url.endsWith(".apk")){
                apkName = url.substring(url.lastIndexOf('/') + 1);
            }else{
                apkName = System.currentTimeMillis() + ".apk";
            }

            fileName = URLDecoder.decode(apkName);
            apkStorePath = Environment.getExternalStorageDirectory().getPath() + "/";

            downAPK(url);
        } else {
            ToastUtils.showShort(mContext, "网络不给力");
        }
    }



    private void downAPK(String url) {
        OkHttpUtils.get(url)
                   .execute(new FileCallback(apkStorePath, fileName) {
                       @Override
                       public void onResponse(boolean b, File file, Request request, @Nullable Response response) {
                           if(null != file) {
                               ToastUtils.showShort(mContext, "下载成功");
                               if(pBar != null){
                                   pBar.dismiss();
                               }
                               VersionUtil.installAPK(mContext, file);
                           }else{
                               ToastUtils.showShort(mContext, "下载失败");
                           }
                       }

                       @Override
                       public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                           super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                           pBar.setProgress((int) (progress * 100));
                       }

                       @Override
                       public void onBefore(BaseRequest request) {
                           super.onBefore(request);
                           if (pBar == null) {
                               pBar = new ProgressDialog(mContext);
                               pBar.setTitle("正在下载");
                               pBar.setMessage("请稍候...");
                               pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                               pBar.setCancelable(false);
                           }
                           pBar.show();
                       }

                       @Override
                       public void onAfter(boolean isFromCache, @Nullable File file, Call call, @Nullable Response response, @Nullable Exception e) {
                           super.onAfter(isFromCache, file, call, response, e);
                       }

                       @Override
                       public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
                           super.onError(isFromCache, call, response, e);
                           ToastUtils.showShort(mContext, R.string.server_unconnect);
                       }
                   });
    }
}
