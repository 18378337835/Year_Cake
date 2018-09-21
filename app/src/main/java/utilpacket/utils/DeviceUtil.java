package utilpacket.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by Koma on 2017/6/5.
 * Description：设备工具类
 * version 1.0 Content：
 */

public class DeviceUtil {

    /**
     * 获取手机型号
     * @param
     * @return
     */
    public static String getDeviceModel(){
        return Build.MODEL;
    }

    /**
     * 获取手机产商
     * @return
     */
    public static String getDeviceManufacturer(){
        return Build.MANUFACTURER;
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }


    /**
     * 获取设备IMEI码
     * @param context 上下文
     * @return IMEI码，获取失败则返回null
     */
    private  static String getDeviceId(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

    /**
     * 获取设备IMSI码
     * @param context 上下文
     * @return IMSI码，获取失败则返回null
     */
    private static String getIMSI(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
    }

    /**
     * 获取手机号码
     * @param context 上下文
     * @return 手机号码，获取失败则返回null
     */
    private static String getSimNumber(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
    }

    /**
     * 获取sim卡序列号
     * @param context 上下文
     * @return sim卡序列号，获取失败返回null
     */
    private static String getSimSerialNumber(Context context){
        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
    }

    /**
     * android id
     * @param context 上下文
     * @return android id ，获取失败则返回null
     */
    @SuppressWarnings("deprecation")
    public static String getAndroidId(Context context){
        return Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
    }

    /**
     * 获取设备唯一标识码，优先顺序是：手机码号，IMEI，IMSI，手机序列号，android id
     * @param context 上下文
     * @return 返回手机唯一标识，一定会返回一个唯一标识码，可能是上面5种码
     */
    public static String getUniqueId(Context context){
        String id = null;
        try {
            id = getDeviceId(context);//IMEI
        } catch (Exception e) {

        } catch (Error e) {

        }
        if(id == null || id.equals("")){
            try {
                id = getIMSI(context);//IMSI
            } catch (Exception e) {

            } catch (Error e) {

            }
            if(id == null || id.equals("")){
                try {
                    id = getSimSerialNumber(context);//序列号
                } catch (Exception e) {

                } catch (Error e) {

                }
                if(id == null || id.equals("")){
                    try {
                        id = getSimNumber(context);//获取手机号码
                    } catch (Exception e) {

                    } catch (Error e) {

                    }
                    if(id != null && !id.equals("")){
                        return id;
                    }
                }else{
                    return id;
                }
            }else{
                return id;
            }
        }else{
            return id;
        }
        return getAndroidId(context);//获取android id
    }

    /**
     * 获取用户ip
     * @param context
     * @return
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo    = wifiManager.getConnectionInfo();
                String ipAddress   = intIP2StringIP(wifiInfo.getIpAddress());
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 获取用户ip
     * @param context
     * @return
     */
    public static int getNetWorkType(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                return 1;
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return 2;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return 1;
    }
    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
