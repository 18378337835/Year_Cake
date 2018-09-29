package com.hjn.year_cake.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.hjn.year_cake.R;
import com.hjn.year_cake.base.BaseActivity;
import com.hjn.year_cake.ui.SelectListDialog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import utilpacket.utils.AppUtils;
import utilpacket.utils.LogUtils;
import utilpacket.utils.PermissionUtils;
import utilpacket.utils.SDCardUtils;

/**
 * Created by Year_Cake on 2018/9/4.
 * descripton: 相机工具类
 */


public class PhotoUtil {
    public static final int PiC_FROM_ALBUM = 1;
    public static final int PiC_FROM_CAMERA = 2;
    public static final int PiC_FROM_CROP = 3;
    public static final int TAG_ADD_CREDIT = 11;
    public static final int TAG_ADD_CUSTOMER = 12;
    public static final int TAG_ADD_SHOP = 13;
    public static final int TAG_ADD_SHOP_FACE = 14;
    public static final int TAG_CREDIT_GOOD = 15;
    public static final int TAG_EDIT_SHOP = 16;
    public static final int TAG_IDENTIFY = 17;
    public static final int TAG_CUSTOMER_INFO = 18;
    public static final String[] PIC_ITEM = new String[] { "拍照", "从相册选择" };
    public static final String IMAGE_NAME = "image.jpg";

    public static final int PERMISSION_CAMERA = 100;
    public static final int PERMISSION_ALBUM = 101;

    public static File TEMP_FILE = null;


    public static void getPicDialog(final Activity mContext) {

        final SelectListDialog picImgDialog = new SelectListDialog(mContext, "请选择", PIC_ITEM, true);
        picImgDialog.setListItemOnClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (picImgDialog != null) {
                    picImgDialog.dismiss();
                }
                switch (position) {
                    case 0:
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.REQUEST_INSTALL_PACKAGES, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
                                ActivityCompat.requestPermissions(mContext, mPermissionList, 133);
                            }
                            if(PermissionUtils.checkPermission(null, mContext, Manifest.permission.CAMERA,
                                                               R.string.no_camera_permission, PERMISSION_CAMERA)){
                                if(PermissionUtils.checkPermission(null, mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                                   R.string.no_album_permission, PERMISSION_ALBUM)){
                                    getPhotoFromCarema(mContext);
                                }
                            }
                        }else{
                            if(PermissionUtils.cameraIsCanUse()){
                                getPhotoFromCarema(mContext);
                            }else{
                                PermissionUtils.showDialog(mContext, R.string.no_camera_permission);
                            }
                        }
                        break;

                    case 1:
                        if(PermissionUtils.checkPermission(null, mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                           R.string.no_album_permission, PERMISSION_ALBUM)){
                            getPhotoFromAlbum(mContext);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        if (picImgDialog != null) {
            picImgDialog.show();
        }
    }


    // 相册去图片
    public static void getPhotoFromAlbum(Activity mContext) {

        Intent getAlbum;
        if (Build.VERSION.SDK_INT < 19) {
            getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
            getAlbum.setType("image/*");
            mContext.startActivityForResult(getAlbum, PiC_FROM_ALBUM);
        } else {
            getAlbum = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            mContext.startActivityForResult(getAlbum, PiC_FROM_ALBUM);

        }
    }


    // 拍照取图片
    public static void getPhotoFromCarema(Activity mContext) {
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri ;
        if (AppUtils.hasSdcard()) {
            File file = new File(SDCardUtils.getSDPath(), IMAGE_NAME);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                uri = FileProvider.getUriForFile(mContext, mContext.getPackageName()+".fileProvider", file);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else{
                uri = Uri.fromFile(file);
            }
            intentFromCapture.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        mContext.startActivityForResult(intentFromCapture, PiC_FROM_CAMERA);
    }

    //	// 拍照取图片
    // 切图
    public static void zoomPhoto(Activity mContext, Uri uri, int aspectX, int aspectY, int outputX, int outputY) {

        LogUtils.e("uri---->"+uri.toString());
        String name = DateFormat.format("yyyyMMddHHmmss", System.currentTimeMillis()) + ".jpg";
        TEMP_FILE = new File(SDCardUtils.getSDPath(), name);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(TEMP_FILE));

        intent.putExtra("scale", true);

        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);

        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);

        intent.putExtra("crop", "true");
        //		intent.putExtra("return-data", true);

        intent.putExtra("noFaceDetection", true);
        mContext.startActivityForResult(intent, PiC_FROM_CROP);
    }



    public static String reSizeImg(String imgFile, float size) {

        Bitmap image = reSizeImg(imgFile, 800, 800);

        File f = new File(imgFile);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            image.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgFile;
    }

    public static Bitmap reSizeImg(String imgFile, int weight, int height) {
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgFile, o);
        int scale = 0;
        while ((o.outWidth >> scale) > weight || (o.outHeight >> scale) > height) {
            scale++;
        }
        o = new Options();
        o.inSampleSize = 1 << scale;

        return BitmapFactory.decodeFile(imgFile, o);
    }

    public static String reSizeImg(String imgFile) {
        return reSizeImg(imgFile, 200);
    }

    public static Bitmap reSizeImgWH(String imgFile) {
        return reSizeImg(imgFile, 800, 800);
    }


    public static void selectPhoto(final BaseActivity mContext, final OnSelectPhoto onSelectPhoto) {

        final SelectListDialog picImgDialog = new SelectListDialog(mContext, "选择图片", PIC_ITEM, false);
        picImgDialog.setListItemOnClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (picImgDialog != null) {
                    picImgDialog.dismiss();
                }
                switch (position) {
                    case 0:
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(PermissionUtils.checkPermission(null, mContext, Manifest.permission.CAMERA,
                                                               R.string.no_camera_permission, PERMISSION_CAMERA)){
                                //								if(!SPUtil.contains(mContext, "hasCamarea")){
                                //									onSelectPhoto.cancel();
                                //								}
                                //								SPUtil.put(mContext, "hasCamarea", 1);
                                if(PermissionUtils.checkPermission(null, mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                                   R.string.no_album_permission, PERMISSION_ALBUM)){
                                    //									if(!SPUtil.contains(mContext, "hasStorage")){
                                    //										onSelectPhoto.cancel();
                                    //									}
                                    //									SPUtil.put(mContext, "hasStorage", 1);
                                    if(null != onSelectPhoto){
                                        onSelectPhoto.OpenCarema();
                                    }
                                }else{
                                    onSelectPhoto.cancel();
                                }
                            }else{
                                onSelectPhoto.cancel();
                            }
                        }else{
                            if(PermissionUtils.cameraIsCanUse()){
                                //								if(!SPUtil.contains(mContext, "hasCamarea")){
                                //									onSelectPhoto.cancel();
                                //								}
                                //								SPUtil.put(mContext, "hasCamarea", 1);
                                if(null != onSelectPhoto){
                                    onSelectPhoto.OpenCarema();
                                }
                            }else{
                                PermissionUtils.showDialog(mContext, R.string.no_camera_permission);
                                onSelectPhoto.cancel();
                            }
                        }
                        break;

                    case 1:
                        if(PermissionUtils.checkPermission(null, mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                           R.string.no_album_permission, PERMISSION_ALBUM)){
                            //							if(!SPUtil.contains(mContext, "hasStorage")){
                            //								onSelectPhoto.cancel();
                            //							}
                            //							SPUtil.put(mContext, "hasStorage", 1);
                            if(null != onSelectPhoto){
                                onSelectPhoto.OpenAlbum();
                            }
                        }else{
                            onSelectPhoto.cancel();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        picImgDialog.setOnCancleClick(new SelectListDialog.OnCancleClick() {
            @Override
            public void cancleClick() {
                onSelectPhoto.cancel();
            }
        });
        picImgDialog.setCanceledOnTouchOutside(false);
        if (picImgDialog != null) {
            picImgDialog.show();
        }
    }

    public interface OnSelectPhoto{
        void OpenCarema();
        void OpenAlbum();
        void cancel();
    }

}
