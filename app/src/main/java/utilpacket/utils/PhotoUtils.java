package utilpacket.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import com.hjn.year_cake.R;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author koma
 * 拍照工具类
 */
public class PhotoUtils {

    /**
     * 拍照
     */
    public static final int REQUEST_CAMERA = 200;
    /**
     * 图片名字
     */
    public static final String IMAGE_NAME = "image.jpg";
    public static final String IMAGE_NAME1 = "image1.jpg";

    public static int IMAGE_REQUEST_CODE=300;
    public static int RESULT_REQUEST_CODE=400;
    public static File TEMP_FILE = null;
    public static final int PiC_FROM_CROP = 3;
    /**
     * 获取相机权限
     * @param activity
     * @return
     */
    public static boolean getCameraPermission(Activity activity){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//            if(PermissionUtils.checkPermission(null, activity, Manifest.permission.CAMERA,
//                    R.string.need_camera_permission, REQUEST_CAMERA)){
//                if(PermissionUtils.checkPermission(null, activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                        R.string.need_stogafe_permission, REQUEST_CAMERA)){
//                    return true;
//                }
//            }
//        }else{
//            if(PermissionUtils.cameraIsCanUse()){
//                return true;
//            }
//        }
        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //进行权限请求
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
            return false;
        }
        return true;
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

    /**
     * 打开相机
     * @param activity
     */
    public static void openCamera(Activity activity){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PermissionUtils.checkPermission(null, activity, Manifest.permission.CAMERA,
                    R.string.need_camera_permission, REQUEST_CAMERA)){
                if(PermissionUtils.checkPermission(null, activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        R.string.need_stogafe_permission, REQUEST_CAMERA)){
                    takeCamera(activity);
                }
            }
        }else{
            if(PermissionUtils.cameraIsCanUse()){
                takeCamera(activity);
            }else{
                PermissionUtils.showDialog(activity, R.string.need_camera_permission);
            }
        }
    }



    /**
     * 拍照
     */
    public static void takeCamera(Activity activity){
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri ;
        File file = new File(SDCardUtils.getSDPath(), IMAGE_NAME);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            uri = FileProvider.getUriForFile(activity,
                    "com.jrweid.dkzx.fileProvider", file);
            intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }else{
            uri = Uri.fromFile(file);
        }
//            intentFromCapture.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intentFromCapture, REQUEST_CAMERA);
    }


    public static void requestphoto(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PermissionUtils.checkPermission(null, activity, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                   "打开相册",IMAGE_REQUEST_CODE )){

            }
        }else{
            if(PermissionUtils.cameraIsCanUse()){
                openphoto(activity);
            }else{
                PermissionUtils.showDialog(activity, R.string.need_camera_permission);
            }
        }
    }
    public static void openphoto(Activity activity){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        Uri uri ;
        File file = new File(SDCardUtils.getSDPath(), IMAGE_NAME1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//如果大于等于7.0使用FileProvider
            Uri uriForFile = FileProvider.getUriForFile
                    (activity, "com.jrweid.dkzx.fileProvider", file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);

        } else {
            uri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(intent, IMAGE_REQUEST_CODE);
        }
    }



    /**
     * 打开相机
     * @param fragment
     */
    public static void openCamera(Fragment fragment){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(PermissionUtils.checkPermission(null, fragment.getActivity(), Manifest.permission.CAMERA,
                    R.string.need_camera_permission, REQUEST_CAMERA)){
                if(PermissionUtils.checkPermission(null, fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        R.string.need_stogafe_permission, REQUEST_CAMERA)){
                    takeCamera(fragment);
                }
            }
        }else{
            if(PermissionUtils.cameraIsCanUse()){
                takeCamera(fragment);
            }else{
                PermissionUtils.showDialog(fragment.getActivity(), R.string.need_camera_permission);
            }
        }
    }

    /**
     * 拍照
     */
    public static void takeCamera(Fragment fragment){
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri uri ;
        if (SDCardUtils.isSDCardEnable()) {
            File file = new File(SDCardUtils.getSDPath(), IMAGE_NAME);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                uri = FileProvider.getUriForFile(fragment.getActivity(),
                        "com.wdjk.inacashloan.fileProvider", file);
                intentFromCapture.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else{
                uri = Uri.fromFile(file);
            }
            intentFromCapture.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        fragment.startActivityForResult(intentFromCapture, REQUEST_CAMERA);
    }

    /**
     * 图片质量压缩并输出文件
     * @param image
     * @return
     */
    public static File compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024 > 100) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return saveBitmapFile(bitmap);
    }

    /**
     * 图片尺寸压缩
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * Bitmap转化为file
     * @param bitmap
     */
    public static File saveBitmapFile(Bitmap bitmap){
        File file = new File(SDCardUtils.getSDPath(), System.currentTimeMillis() + ".jpg");//将要保存图片的路径
        if(null == bitmap){
            return file;
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * @param path 原图的路径
     * @return 压缩后的图片
     */
    public static Bitmap getCompressPhoto(String path) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = calculateInSampleSize(options, 800, 600);
        options.inJustDecodeBounds = false;
        try {
            bitmap = BitmapFactory.decodeFile(path, options);
        } catch (Exception e) {
            options.inSampleSize = calculateInSampleSize(options, 600, 400);
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(path, options);
        }
        options = null;
        return bitmap;
    }

    /**
     * 处理旋转后的图片
     * @param originpath 原图路径
     * @param context 上下文
     * @return 返回修复完毕后的图片路径
     */
    public static File amendRotatePhoto(String originpath, Context context) {

        // 取得图片旋转角度
        int angle = readPictureDegree(originpath);

        // 把原图压缩后得到Bitmap对象
        Bitmap bmp = getCompressPhoto(originpath);;

        // 修复图片被旋转的角度
        Bitmap bitmap = rotaingImageView(angle, bmp);

        // 保存修复后的图片并返回保存后的图片路径
        return saveBitmapFile(bitmap);
    }

    /**
     * 读取照片旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 旋转图片
     * @param angle 被旋转角度
     * @param bitmap 图片对象
     * @return 旋转后的图片
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        Bitmap returnBm = null;
        if(null == bitmap){
            return returnBm;
        }
        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        }
        if (returnBm == null) {
            returnBm = bitmap;
        }
        if (bitmap != returnBm) {
            bitmap.recycle();
        }
        return returnBm;
    }





}
