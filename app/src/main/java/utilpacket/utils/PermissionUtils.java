package utilpacket.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.hjn.year_cake.R;

/**
 * 获取权限的工具类
 */
public class PermissionUtils {

    private static AlertDialog alertDialog;

    public static final int REQUEST_INTENT_CODE = 200;

    /**
     *
     * @param activity
     * @param permission
     * @param hint
     * @param requestCode
     * @return
     */
    public static boolean checkPermission(Fragment fragment, Activity activity, String permission, String hint,
                                          int requestCode){

        if(ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED){
            LogUtils.e("----------有权限----------------");
            //有权限
            return true;
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)){
                //不在访问
                LogUtils.e("----------不再访问----------------");
                showDialog(activity, hint);
            }else{
                LogUtils.e("----------可以访问----------------");
                if(fragment == null){
                    ActivityCompat.requestPermissions(activity,
                            new String[]{permission}, requestCode);
                }else {
                    fragment.requestPermissions(
                            new String[]{permission},
                            requestCode);
                }
            }
            return false;
        }
    }

    /**
     *
     * @param activity
     * @param permission
     * @param hint
     * @param requestCode
     * @return
     */
    public static boolean checkPermission(Fragment fragment, Activity activity, String permission,
                                          int hint, int requestCode){
        return checkPermission(fragment, activity, permission, activity.getString(hint), requestCode);
    }

    /**
     *
     * @param activity
     * @param hint
     */
    public static void showDialog(final Activity activity, String hint){
        if(null == activity || activity.isFinishing()){
            return;
        }
        alertDialog = new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.warm_hint))
                .setMessage(hint)
                .setNegativeButton(activity.getString(R.string.cancle), null)
                .setPositiveButton(activity.getString(R.string.to_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        alertDialog = null;
                        Intent localIntent=new Intent();
                        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        if(Build.VERSION.SDK_INT >= 9){
                            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                            localIntent.setData(Uri.fromParts("package", activity.getPackageName(),null));
                        }else if(Build.VERSION.SDK_INT <= 8){
                            localIntent.setAction(Intent.ACTION_VIEW);
                            localIntent.setClassName("com.android.settings","com.android.setting.InstalledAppDetails");
                            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                        }
                        activity.startActivityForResult(localIntent, REQUEST_INTENT_CODE);
                    }
                })
                .show();
    }

    /**
     *
     * @param activity
     * @param hint
     */
    public static void showDialog(final Activity activity, int hint){
        showDialog(activity, activity.getString(hint));
    }

    /**
     * 跳转到安装未知应用来源
     * @param activity
     * @param hint
     * @param i
     */
    public static void showDialog(final Activity activity, String hint, int i){
        if(null == activity || activity.isFinishing()){
            return;
        }
        alertDialog = new AlertDialog.Builder(activity)
                .setTitle(activity.getString(R.string.warm_hint))
                .setMessage(hint)
                .setNegativeButton(activity.getString(R.string.cancle), null)
                .setPositiveButton(activity.getString(R.string.to_setting), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        alertDialog = null;
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                            activity.startActivityForResult(intent, 10086);
                        }
                    }
                })
                .show();
    }


    /**
     * 通过尝试打开相机的方式判断有无拍照权限（在6.0以下使用拥有root权限的管理软件可以管理权限）
     *
     * @return
     */
    public static boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);
        } catch (Exception e) {
            isCanUse = false;
        }

        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e) {
                e.printStackTrace();
                return isCanUse;
            }
        }
        return isCanUse;
    }


    /**
     * 申请权限
     * @param activity
     * @param permission
     * @param requestCode
     */
    public static void requestPermission(Activity activity, String permission, int requestCode){
        ActivityCompat.requestPermissions(activity,
                new String[]{permission}, requestCode);
    }

    /**
     * 申请权限
     * @param activity
     * @param permission
     * @param requestCode
     */
    public static void requestPermission(Activity activity, String[] permission, int requestCode){
        ActivityCompat.requestPermissions(activity,
                permission, requestCode);
    }


    /**
     * 判断权限是否开启
     * @param activity
     * @param permission
     * @return
     */
    public static boolean  hasPermission(Activity activity, String permission){
        return ContextCompat.checkSelfPermission(activity, permission) ==
                PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取权限状态
     * @param activity
     * @param permission
     * @return
     */
    public static boolean getPermission(Activity activity, String permission){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ContextCompat.checkSelfPermission(activity, permission) ==
                    PackageManager.PERMISSION_GRANTED){
                LogUtils.e("----------有权限----------------");
                //有权限
                return true;
            }
            return false;
        }
        return true;
    }

    /**
     * 获取相机权限
     * @param activity
     * @return
     */
    public static boolean getCameraPermission(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PermissionUtils.checkPermission(null, activity, Manifest.permission.CAMERA,
                                               "app需要读取相机权限", 202)){
                if(PermissionUtils.checkPermission(null, activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                   "app需要读取内存权限", 203)){
                    return true;
                }
            }
        }else{
            if(PermissionUtils.cameraIsCanUse()){
                return true;
            }
        }
        return false;
    }
}
