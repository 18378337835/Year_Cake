package utilpacket.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author  Koma
 * @date 2017-12-15
 */

public class ToastUtils {

    private ToastUtils()
    {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message)
    {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration)
    {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration)
    {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 中部显示
     * @param context
     * @param meassage
     * @param duration
     */
    public static void showCenter(Context context, int meassage, int duration){
        if(isShow){
            Toast toast =   Toast.makeText(context, meassage, duration);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
    /**
     * 中部显示
     * @param context
     * @param meassage
     * @param duration
     */
    public static void showCenter(Context context, String meassage, int duration){
        if(isShow){
            Toast toast =   Toast.makeText(context,meassage,duration);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

    }

}