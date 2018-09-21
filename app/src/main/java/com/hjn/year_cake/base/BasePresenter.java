package com.hjn.year_cake.base;

/**
 * @author Koma
 * presenter处理（逻辑处理）
 */

public abstract class BasePresenter<T> {

    T view;

    /**
     *  获取 view 的view 实例
     * @param view
     */
    void onAttach(T view){
        this.view = view;
    }

    /**
     * 解绑 view 层
     */
    void onDetch(){
        this.view = null;
    }

}

