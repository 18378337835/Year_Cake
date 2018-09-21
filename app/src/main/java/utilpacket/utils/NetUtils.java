package utilpacket.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;

/**
 * Created by jrweid on 2017/6/5.
 * Description：网络相关的工具类
 * version 1.0 Content：
 */
public class NetUtils
{
    private NetUtils()
    {
		/* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context)
    {

        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity)
        {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断是否是wifi连接
     */
    public static boolean isWifi(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm == null)
            return false;
        return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    /**
     * 打开网络设置界面
     */
    public static void openNetSetting(Activity activity)
    {
        Intent intent = new Intent("/");
        ComponentName cm = new ComponentName("com.android.settings",
                                             "com.android.settings.WirelessSettings");
        intent.setComponent(cm);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }


    /**
     * 判断Network具体类型（联通移动wap，电信wap，其他net）
     * @param mContext
     * @return
     */
    public static String checkNetworkType(Context mContext) {
        final String CTWAP = "ctwap";
        final String CTNET = "ctnet";
        final String CMWAP = "cmwap";
        final String CMNET = "cmnet";
        final String NET_3G = "3gnet";
        final String WAP_3G = "3gwap";
        final String UNIWAP = "uniwap";
        final String UNINET = "uninet";

        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo mobNetInfoActivity = connectivityManager
                    .getActiveNetworkInfo();
            if (mobNetInfoActivity == null || !mobNetInfoActivity.isAvailable()) {
                // 注意一：
                // NetworkInfo 为空或者不可以用的时候正常情况应该是当前没有可用网络，
                // 但是有些电信机器，仍可以正常联网，
                // 所以当成net网络处理依然尝试连接网络。
                // （然后在socket中捕捉异常，进行二次判断与用户提示）。
                return "TYPE_NO";
            } else {
                // NetworkInfo不为null开始判断是网络类型
                int netType = mobNetInfoActivity.getType();
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    // wifi net处理
                    return "TYPE_Wifi";
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    // 注意二：
                    // 判断是否电信wap:
                    // 不要通过getExtraInfo获取接入点名称来判断类型，
                    // 因为通过目前电信多种机型测试发现接入点名称大都为#777或者null，
                    // 电信机器wap接入点中要比移动联通wap接入点多设置一个用户名和密码,
                    // 所以可以通过这个进行判断！

                    boolean is3G = isFastMobileNetwork(mContext);
                    Uri PREFERRED_APN_URI = Uri
                            .parse("content://telephony/carriers/preferapn");

                    final Cursor c = mContext.getContentResolver().query(
                            PREFERRED_APN_URI, null, null, null, null);
                    if (c != null) {
                        c.moveToFirst();
                        final String user = c.getString(c
                                                                .getColumnIndex("user"));
                        if (!TextUtils.isEmpty(user)) {
                            if (user.startsWith(CTWAP)) {
                                return is3G ? "TYPE_CT_WAP" : "TYPE_CT_WAP_2G";
                            } else if (user.startsWith(CTNET)) {
                                return is3G ? "TYPE_CT_NET" : "TYPE_CT_NET_2G";
                            }
                        }
                    }
                    c.close();

                    // 注意三：
                    // 判断是移动联通wap:
                    // 其实还有一种方法通过getString(c.getColumnIndex("proxy")获取代理ip
                    // 来判断接入点，10.0.0.172就是移动联通wap，10.0.0.200就是电信wap，但在
                    // 实际开发中并不是所有机器都能获取到接入点代理信息，例如魅族M9 （2.2）等...
                    // 所以采用getExtraInfo获取接入点名字进行判断

                    String netMode = mobNetInfoActivity.getExtraInfo();
                    Log.i("", "==================netmode:" + netMode);
                    if (netMode != null) {
                        // 通过apn名称判断是否是联通和移动wap
                        netMode = netMode.toLowerCase();

                        if (netMode.equals(CMWAP)) {
                            return is3G ? "TYPE_CM_WAP" : "TYPE_CM_WAP_2G";
                        } else if (netMode.equals(CMNET)) {
                            return is3G ? "TYPE_CM_NET" : "TYPE_CM_NET_2G";
                        } else if (netMode.equals(NET_3G)
                                || netMode.equals(UNINET)) {
                            return is3G ? "TYPE_CU_NET" : "TYPE_CU_NET_2G";
                        } else if (netMode.equals(WAP_3G)
                                || netMode.equals(UNIWAP)) {
                            return is3G ? "TYPE_CU_WAP" : "TYPE_CU_WAP_2G";
                        }
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            return "TYPE_OTHER";
        }

        return "TYPE_OTHER";
    }

    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);

        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }


    /**
     * 获取内网ip地址
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }

    /**
     * 获取外网IP地址
     * @return
     */
    public static String getNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String line = "";
        try {
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                while ((line = reader.readLine()) != null)
                    strber.append(line + "\n");
                inStream.close();
                // 从反馈的结果中提取出IP地址
                int start = strber.indexOf("{");
                int end = strber.indexOf("}");
                String json = strber.substring(start, end + 1);
                if (json != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        line = jsonObject.optString("cip");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }


}
