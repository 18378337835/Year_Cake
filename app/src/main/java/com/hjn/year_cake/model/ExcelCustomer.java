package com.hjn.year_cake.model;

/**
 * Created by YearCake on 2018/9/30.
 * description:
 * version v1.0 content:
 */
public class ExcelCustomer {

    /**
     * 时间   客户类型   姓名   身份证号    手机号   性别    年龄    所在城市  资金需求  期望期限
     */
    private String time;
    private String customerType;
    private String name;
    private String idNo;
    private String mobile;
    private String sex;
    private String age;
    private String city;
    private String loanNeed;
    private String loanTime;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLoanNeed() {
        return loanNeed;
    }

    public void setLoanNeed(String loanNeed) {
        this.loanNeed = loanNeed;
    }

    public String getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(String loanTime) {
        this.loanTime = loanTime;
    }
}
