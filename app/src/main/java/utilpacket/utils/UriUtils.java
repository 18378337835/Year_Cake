package utilpacket.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * @author koma
 * @date 2018/5/29
 * @describe
 */

public class UriUtils {

    /**
     * 文件转uri
     * @param context
     * @param file
     * @return
     */
    public static Uri getUriForFile(Context context, final File file) {
        if (file == null) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = context.getPackageName() + ".utilcode.provider";
            return FileProvider.getUriForFile(context, authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * @param context
     * @param uri
     * @return
     */
    public static String getPathForUri(Context context, Uri uri){

        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri,
                    filePathColumn, null, null, null);
            if(null != cursor){
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                return cursor.getString(columnIndex);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }

}
