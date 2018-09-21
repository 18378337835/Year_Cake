package utilpacket.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Koma on 2017/6/5.
 * Description：分享偏好保存工具类
 * version 1.0 Content：
 */
public class SPUtil {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    /**
     * 保存整型数据
     * @param context
     * @param key
     * @param value
     */
    public static void putInt(Context context, String key, int value) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 保存字符串
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        SharedPreferencesCompat.apply(editor);
    }


    public static void putObject(Context context, String key, Object obj) {

        try {
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                                                                Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            ByteArrayOutputStream e   = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(e);
            oos.writeObject(obj);
            //            String stringBase64 = new String(Base64.encodeBase64(e.toByteArray()));
            String stringBase64 = new String(Base64.encode(e.toByteArray(), 0));
            editor.putString(key, stringBase64);
            SharedPreferencesCompat.apply(editor);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }



    /**
     * 保存布尔值
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 保存浮点型数据
     * @param context
     * @param key
     * @param value
     */
    public static void putFloat(Context context, String key, float value) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 保存长整型数据
     * @param context
     * @param key
     * @param value
     */
    public static void putLong(Context context, String key, long value) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(key, value);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 获取整型数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 获取字符串
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getString(Context context, String key, String defaultValue){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    public static Object getObject(Context context, String key) {
        try {
            SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                                                                Context.MODE_PRIVATE);
            String e = sp.getString(key, "");
            if (TextUtils.isEmpty(e)) {
                return null;
            } else {
                byte[]               base64Bytes = Base64.decode(e.getBytes(),0);
                ByteArrayInputStream bais        = new ByteArrayInputStream(base64Bytes);
                ObjectInputStream ois         = new ObjectInputStream(bais);
                return ois.readObject();
            }
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }
    /**
     * 获取布尔值
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Boolean getBoolean(Context context, String key, boolean defaultValue){
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getBoolean(key, defaultValue);
    }

    /**
     * 获取长整型数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Long getLong(Context context, String key, long defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    /**
     * 获取浮点型数据
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static Float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getFloat(key, defaultValue);
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void remove(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }


    /**
     * 查询某个key是否已经存在
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat
    {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod()
        {
            try
            {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e)
            {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor)
        {
            try
            {
                if (sApplyMethod != null)
                {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e)
            {
            } catch (IllegalAccessException e)
            {
            } catch (InvocationTargetException e)
            {
            }
            editor.commit();
        }
    }


}
