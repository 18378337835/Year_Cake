package com.hjn.year_cake.model;

/**
 * Created by YearCake on 2018/9/30.
 * description:
 * version v1.0 content:
 */
public class ExportClientResponse {

    /**
     * real_name : 余敏
     * mobile : 17681845246
     * city : 杭州
     * age : 18
     * sex : 男
     * apply_amount : 30000
     * deadline : 12个月
     * create_time : 2017-08-02 08:58:44
     * identify_no : null
     * customer_type : 上班族
     */

    private String real_name;
    private String mobile;
    private String city;
    private String age;
    private String sex;
    private String apply_amount;
    private String deadline;
    private String created_time;
    private String identify_no;
    private String customer_type;
    private String apply_time;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getApply_amount() {
        return apply_amount;
    }

    public void setApply_amount(String apply_amount) {
        this.apply_amount = apply_amount;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getIdentify_no() {
        return identify_no;
    }

    public void setIdentify_no(String identify_no) {
        this.identify_no = identify_no;
    }

    public String getCustomer_type() {
        return customer_type;
    }

    public void setCustomer_type(String customer_type) {
        this.customer_type = customer_type;
    }

    public String getApply_time() {
        return apply_time;
    }

    public void setApply_time(String apply_time) {
        this.apply_time = apply_time;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "real_name='" + real_name + '\'' +
                ", mobile='" + mobile + '\'' +
                ", city='" + city + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", apply_amount='" + apply_amount + '\'' +
                ", deadline='" + deadline + '\'' +
                ", create_time='" + created_time + '\'' +
                ", identify_no='" + identify_no + '\'' +
                ", customer_type='" + customer_type + '\'' +
                ", apply_time='" + apply_time + '\'' +
                '}';
    }
}
