package com.hjn.year_cake.utils;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import utilpacket.utils.LogUtils;

/**
 * @author koma
 */
public class ShareUtils {

    /**
     * 获取文件类型
     * @param filePath
     * @return
     */
    public static String getFileType(String filePath) {
        return mFileTypes.get(getFileHeader(filePath));
    }

    private static final HashMap<String, String> mFileTypes = new HashMap<String, String>();

    // judge file type by file header content
    static {
        mFileTypes.put("ffd8ffe000104a464946", "jpg"); //JPEG (jpg)
        mFileTypes.put("89504e470d0a1a0a0000", "png"); //PNG (png)
        mFileTypes.put("47494638396126026f01", "gif"); //GIF (gif)
        mFileTypes.put("49492a00227105008037", "tif"); //TIFF (tif)
        mFileTypes.put("424d228c010000000000", "bmp"); //16色位图(bmp)
        mFileTypes.put("424d8240090000000000", "bmp"); //24位位图(bmp)
        mFileTypes.put("424d8e1b030000000000", "bmp"); //256色位图(bmp)
        mFileTypes.put("41433130313500000000", "dwg"); //CAD (dwg)
        mFileTypes.put("3c21444f435459504520", "html"); //HTML (html)
        mFileTypes.put("3c21646f637479706520", "htm"); //HTM (htm)
        mFileTypes.put("48544d4c207b0d0a0942", "css"); //css
        mFileTypes.put("696b2e71623d696b2e71", "js"); //js
        mFileTypes.put("7b5c727466315c616e73", "rtf"); //Rich Text Format (rtf)
        mFileTypes.put("38425053000100000000", "psd"); //Photoshop (psd)
        mFileTypes.put("46726f6d3a203d3f6762", "eml"); //Email [Outlook Express 6] (eml)
        mFileTypes.put("d0cf11e0a1b11ae10000", "doc"); //MS Excel 注意：word、msi 和 excel的文件头一样
        mFileTypes.put("d0cf11e0a1b11ae10000", "vsd"); //Visio 绘图
        mFileTypes.put("5374616E64617264204A", "mdb"); //MS Access (mdb)
        mFileTypes.put("252150532D41646F6265", "ps");
        mFileTypes.put("255044462d312e350d0a", "pdf"); //Adobe Acrobat (pdf)
        mFileTypes.put("2e524d46000000120001", "rmvb"); //rmvb/rm相同
        mFileTypes.put("464c5601050000000900", "flv"); //flv与f4v相同
        mFileTypes.put("00000020667479706d70", "mp4");
        mFileTypes.put("49443303000000002176", "mp3");
        mFileTypes.put("000001ba210001000180", "mpg"); //
        mFileTypes.put("3026b2758e66cf11a6d9", "wmv"); //wmv与asf相同
        mFileTypes.put("52494646e27807005741", "wav"); //Wave (wav)
        mFileTypes.put("52494646d07d60074156", "avi");
        mFileTypes.put("4d546864000000060001", "mid"); //MIDI (mid)
        mFileTypes.put("504b0304140000000800", "zip");
        mFileTypes.put("526172211a0700cf9073", "rar");
        mFileTypes.put("235468697320636f6e66", "ini");
        mFileTypes.put("504b03040a0000000000", "jar");
        mFileTypes.put("4d5a9000030000000400", "exe");//可执行文件
        mFileTypes.put("3c25402070616765206c", "jsp");//jsp文件
        mFileTypes.put("4d616e69666573742d56", "mf");//MF文件
        mFileTypes.put("3c3f786d6c2076657273", "xml");//xml文件
        mFileTypes.put("494e5345525420494e54", "sql");//xml文件
        mFileTypes.put("7061636b616765207765", "java");//java文件
        mFileTypes.put("406563686f206f66660d", "bat");//bat文件
        mFileTypes.put("1f8b0800000000000000", "gz");//gz文件
        mFileTypes.put("6c6f67346a2e726f6f74", "properties");//bat文件
        mFileTypes.put("cafebabe0000002e0041", "class");//bat文件
        mFileTypes.put("49545346030000006000", "chm");//bat文件
        mFileTypes.put("04000000010000001300", "mxp");//bat文件
        mFileTypes.put("504b0304140006000800", "docx");//docx文件
        mFileTypes.put("d0cf11e0a1b11ae10000", "wps");//WPS文字wps、表格et、演示dps都是一样的
        mFileTypes.put("6431303a637265617465", "torrent");


        mFileTypes.put("6D6F6F76", "mov"); //Quicktime (mov)
        mFileTypes.put("FF575043", "wpd"); //WordPerfect (wpd)
        mFileTypes.put("CFAD12FEC5FD746F", "dbx"); //Outlook Express (dbx)
        mFileTypes.put("2142444E", "pst"); //Outlook (pst)
        mFileTypes.put("AC9EBD8F", "qdf"); //Quicken (qdf)
        mFileTypes.put("E3828596", "pwl"); //Windows Password (pwl)
        mFileTypes.put("2E7261FD", "ram"); //Real Audio (ram)
        mFileTypes.put("null", null); //null
    }

    /**
     * 获取文件头信息
     * @param filePath
     * @return
     */
    public static String getFileHeader(String filePath) {
        File file=new File(filePath);
        if(!file.exists() || file.length()<11){
            return "null";
        }
        FileInputStream is = null;
        String value = null;
        try {
            is = new FileInputStream(file);
            byte[] b = new byte[10];
            is.read(b, 0, b.length);
            value = bytesToHexString(b);
        } catch (Exception e) {
        } finally {
            if(null != is) {
                try {
                    is.close();
                } catch (IOException e) {}
            }
        }
        return value;
    }

    /**
     * 将byte字节转换为十六进制字符串
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder builder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        String hv;
        for (int i = 0; i < src.length; i++) {
            hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();
            if (hv.length() < 2) {
                builder.append(0);
            }
            builder.append(hv);
        }
        return builder.toString();
    }

    public static void shareFile(Context context, String filePath) {
        if (context == null || TextUtils.isEmpty(filePath)){
            LogUtils.e("shareFile context is null or filePath is empty.");
            return;
        }

        Uri uri = null;

        File file = new File(filePath);
        if (file != null && file.exists()){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory("android.intent.category.DEFAULT");

            ComponentName comp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI");
            intent.setComponent(comp);

            intent.putExtra(Intent.EXTRA_STREAM, uri);
            // 授予目录临时共享权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            String fileType = null;
            Uri fileUri = getFileUri(context, file);
            if (fileUri != null && !TextUtils.isEmpty(fileUri.toString())) {
                ContentResolver contentResolver = context.getContentResolver();
                fileType = contentResolver.getType(fileUri);
            }

            if (TextUtils.isEmpty(fileType)){
                fileType = getFileType(filePath); // 使用上面的根据文件头信息获取文件类型的方法
            }

            if (TextUtils.isEmpty(fileType)){
                fileType =  "*/*";
            }

            LogUtils.d("shareFile fileType " + fileType);
            LogUtils.d("shareFile uri: " + uri);

            intent.setDataAndType(uri, fileType);

            try {
                context.startActivity(Intent.createChooser(intent, file.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Uri getFileUri(Context context, File file){
        Uri uri;
        // 低版本直接用 Uri.fromFile
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(file);
        }else {
            //  使用 FileProvider 会在某些 app 下不支持（在使用FileProvider 方式情况下QQ不能支持图片、视频分享，微信不支持视频分享）
            uri = FileProvider.getUriForFile(context,
                    "com.jrweid.mm.fileprovider",
                    file);

            ContentResolver cR = context.getContentResolver();
            if (uri != null && !TextUtils.isEmpty(uri.toString())) {
                String fileType = cR.getType(uri);
// 使用 MediaStore 的 content:// 而不是自己 FileProvider 提供的uri，不然有些app无法适配
                if (!TextUtils.isEmpty(fileType)){
                    if (fileType.contains("video/")){
                        uri = getVideoContentUri(context, file);
                    }else if (fileType.contains("image/")){
                        uri = getImageContentUri(context, file);
                    }else if (fileType.contains("audio/")){
                        uri = getAudioContentUri(context, file);
                    }
                }
            }
        }
        return uri;
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Images.Media._ID }, MediaStore.Images.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context
     * @param videoFile
     * @return content Uri
     */
    public static Uri getVideoContentUri(Context context, File videoFile) {
        String filePath = videoFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Video.Media._ID }, MediaStore.Video.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/video/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (videoFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Video.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context
     * @param audioFile
     * @return content Uri
     */
    public static Uri getAudioContentUri(Context context, File audioFile) {
        String filePath = audioFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[] { MediaStore.Audio.Media._ID }, MediaStore.Audio.Media.DATA + "=? ",
                new String[] { filePath }, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/audio/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (audioFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Audio.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


}
