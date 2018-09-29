package com.hjn.year_cake.model;

/**
 * Created by YearCake on 2018/9/29.
 * description:
 * version v1.0 content:
 */

public class PayInfoResponse extends HttpRespone {

    public String logId;
    public String appid;
    public String partnerid;
    public String noncestr;
    public String sign;
    public String prepayid;
    public String timestamp;

    public String payInfo;


    @Override
    public String toString() {
        return "ResultEntity{" +
                "appid='" + appid + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", noncestr='" + noncestr + '\'' +
                ", sign='" + sign + '\'' +
                ", prepayid='" + prepayid + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", payInfo='" + payInfo + '\'' +
                '}';
    }
}
