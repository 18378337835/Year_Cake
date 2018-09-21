package com.hjn.year_cake.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * @author koma
 * 懒加载fragment基类
 */

public abstract class BaseLazyLoadFragment<V extends BaseView, T extends BasePresenter<V>>
        extends BaseFragment {


    /**
     * 是否已经初始化结束
     */
    private boolean isPrepare;

    private boolean isPullData = false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setLazyLoad(true);
        isPrepare = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 创建时要判断是否已经显示给用户，加载数据
        onVisibleToUser();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 显示状态发生变化
        onVisibleToUser();
    }

    /**
     * 判断是否需要加载数据
     */
    private void onVisibleToUser() {
        // 如果已经初始化完成，并且显示给用户
        if (isPrepare && getUserVisibleHint() && !isPullData) {
            loadData();
            isPullData = true;
        }
    }

    /**
     * 加载数据
     */
    public void loadData(){}

}
