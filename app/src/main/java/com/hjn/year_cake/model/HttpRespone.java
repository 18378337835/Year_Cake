package com.hjn.year_cake.model;

/**
 * Created by YearCake on 2018/9/21.
 * description:
 * version v1.0 content:
 */

public class HttpRespone {
    /**
     * code : int
     * success : false
     * message : string
     * data : {"vc":"string"}
     */

    private int code;
    private String   message;
    private String data;
    private boolean success;

    public int getCode() { return code;}

    public void setCode(int code) { this.code = code;}

    public String getMessage() { return message;}

    public void setMessage(String message) { this.message = message;}

    public boolean getSuccess() { return success;}

    public void setSuccess(boolean success) { this.success = success;}
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return this != null && code == 200;
    }
}
