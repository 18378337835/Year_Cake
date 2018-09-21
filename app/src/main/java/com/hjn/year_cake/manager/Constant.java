package com.hjn.year_cake.manager;

import org.json.JSONObject;
import utilpacket.utils.AESUtils;
import utilpacket.utils.AppUtils;
import utilpacket.utils.DeviceUtil;
import utilpacket.utils.LogUtils;
import utilpacket.utils.PostParamsBuilder;
import utilpacket.utils.SPUtil;

/**
 * Created by ${templefck} on 2018/8/28.
 */
public class Constant {


    public static final String apply_amount = "apply_amount";
    public static final String company_type = "company_type";
    public static final String credit_card_amount = "credit_card_amount";
    public static final String credit_report = "credit_report";
    public static final String customer_type = "customer_type";
    public static final String deadline = "deadline";
    public static final String education = "education";
    public static final String has_car = "has_car";
    public static final String has_house = "has_house";
    public static final String has_insurance = "has_insurance";
    public static final String income_tax = "income_tax";
    public static final String marriage = "marriage";
    public static final String net_loan = "net_loan";
    public static final String operat_life = "operat_life";
    public static final String own_fund = "own_fund";
    public static final String own_social_insurance = "own_social_insurance";
    public static final String pay_method = "pay_method";
    public static final String position = "position";
    public static final String salary = "salary";
    public static final String sesame_points = "sesame_points";
    public static final String sex = "sex";
    public static final String stock = "stock";
    public static final String trade_license = "trade_license";
    public static final String trade_priviate_amount = "trade_priviate_amount";
    public static final String trade_public_amount = "trade_public_amount";
    public static final String usage = "usage";
    public static final String weilidai = "weilidai";
    public static final String working_time = "working_time";
    public static final String follow_label = "follow_label";
    public static final String appeal_reason = "appeal_reason";

    public static final String[] TIMES = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00",
            "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00"};

    /**
     * 全局参数
     * @return
     */
    public static PostParamsBuilder getParams(){
        return new PostParamsBuilder()
                .setParams("time", String.valueOf(System.currentTimeMillis() / 1000))
                .setParams("version", AppUtils.getVersionName(MyApp.getInstance()))
                .setParams("app_no", MyApp.app_no)
                .setParams("device_id", DeviceUtil.getUniqueId(MyApp.getInstance()))
                .setParams("token", SPUtil.getString(MyApp.getInstance(), SpKey.TOKEN, ""));
    }

    /**
     * 数据加密
     * @param json
     * @return
     */
    public static String encryJson(com.alibaba.fastjson.JSONObject json){

        LogUtils.e("加密前数据--->"+json.toString());

        try{
            LogUtils.e("加密后数据--->"+new AESUtils().encrypt(json.toJSONString()));
            return new AESUtils().encrypt(json.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 数据加密
     * @param json
     * @return
     */
    public static String encryJson(JSONObject json){

        LogUtils.e("加密前数据--->"+json.toString());

        try{
            LogUtils.e("加密后数据--->"+new AESUtils().encrypt(json.toString()));
            return new AESUtils().encrypt(json.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
