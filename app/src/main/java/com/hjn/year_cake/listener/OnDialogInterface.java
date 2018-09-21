package com.hjn.year_cake.listener;

/**
 * @author koma
 *
 */

public abstract class OnDialogInterface {

    /**
     * 确认按钮点击事件
     */
    public void onSureClickListener(){};

    /**
     * 取消按钮点击事件
     */
    public void onCancleClickListener(){};

    /**
     * 确认按钮传参
     * @param o
     */
    public void onSureClickListener(Object o){}

    /**
     * 日期弹框选择事件
     * @param year       年
     * @param month      月
     * @param day        日
     */
    public void onDateSelectListener(int year, int month, int day){}

}
