package com.hjn.year_cake.base;

/**
 * @author Koma
 * view处理
 */

public interface BaseView {

    /**
     * 加载数据成功
     */
    void loadSuccess(Object data);

    /**
     * 加载数据失败
     */
    void loadFailed(String message);

    /**
     * 加载数据前
     */
    void loadBefore(int message);

    /**
     * 加载数据后
     */
    void loadAfter();

}
