package com.hjn.year_cake.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjn.year_cake.manager.LoadingHelper;
import com.hjn.year_cake.utils.CacheUtil;
import com.jrwd.okhttputils.OkHttpUtils;

import butterknife.ButterKnife;
import utilpacket.glideutil.GlideApp;
import utilpacket.utils.EventBusUtils;
import utilpacket.viewhelp.VaryViewHelperX;

/**
 * @author koma
 * Fragment基类
 */

public abstract class BaseFragment <V extends BaseView, T extends BasePresenter<V>>
        extends Fragment
{

    public Context mContext;

    public T mPresenter;

    /**
     * 是否启用懒加载，此属性仅对BaseLazyLoadFragment有效
     * */
    private boolean isLazyLoad;

    private View rootView;
    public CacheUtil mCacheUtil;
    private LoadingHelper loadViewHelper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(savedInstanceState), null);
        mContext = getActivity();
        mCacheUtil = CacheUtil.get(mContext);
        mPresenter = getPresenter();
        initView(view);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isRegisterEvent()){
            EventBusUtils.register(this);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 如果不是懒加载模式，创建就加载数据
        if (!isLazyLoad){
            pullData();
        }
    }

    public abstract int getLayoutId(@Nullable Bundle savedInstanceState);

    public void initView(View view){
        this.rootView = view;
        ButterKnife.bind(this, view);
    }

    public abstract T getPresenter();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public Context getMyContext(){
        return mContext;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(null != mPresenter){
            mPresenter.onAttach((V)this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        GlideApp.get(getActivity()).clearMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(null != mPresenter){
            mPresenter.onDetch();
        }
        if(isRegisterEvent()){
            EventBusUtils.unregister(this);
        }
        OkHttpUtils.getInstance().cancelTag(this);
    }


    /*************************************************************************************/

    /**
     * 是否注册EventBus
     * @return
     */
    public boolean isRegisterEvent(){
        return false;
    }

    /**
     * 懒加载获取数据
     */
    public void pullData(){}

    /**
     * 是否启用懒加载，此方法仅对BaseLazyLoadFragment有效
     * */
    public void setLazyLoad(boolean lazyLoad) {
        isLazyLoad = lazyLoad;
    }


    /**
     * 初始化显示各种状态界面
     * @param container
     */
    public void initLoadingHelper(View container){
        if(loadViewHelper == null){
            loadViewHelper = new LoadingHelper(new VaryViewHelperX(container));
        }
    }
    public void showEmptyLayout(int resId,View.OnClickListener clickListener){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showEmpty(resId,clickListener);
    }

    /**
     * 显示空数据view
     * @param clickListener
     */
    public void showEmptyView(View.OnClickListener clickListener){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showEmpty(clickListener);
    }

    /**
     * 显示空数据view
     * @param resId
     * @param clickListener
     */
    public void showEmptyView(int resId, View.OnClickListener clickListener){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showEmpty(clickListener, resId);
    }

    /**
     * 显示错误View
     * @param onClickListener
     */
    public void showErrorView(View.OnClickListener onClickListener){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showError(onClickListener);
    }

    /**
     * 显示错误View
     * @param onClickListener
     * @param resId
     */
    public void showErrorView(View.OnClickListener onClickListener, int resId){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showError(onClickListener, resId);
    }

    /**
     * 显示加载数据View
     */
    public void showLoadingView(){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showLoading("数据加载中");
    }

    /**
     * 显示加载数据View
     * @param message
     */
    public void showLoadingView(String message){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showLoading(message);
    }

    /**
     * 显示网络无连接View
     * @param clickListener
     */
    public void showNetworkNoConnect(View.OnClickListener clickListener){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showNetwordNoConnect(clickListener);
    }

    /**
     * 显示网络无连接View
     * @param clickListener
     * @param resId
     */
    public void showNetworkNoConnect(View.OnClickListener clickListener, int resId){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.showNetwordNoConnect(clickListener, resId);
    }

    /**
     * 显示数据加载成功后的界面
     */
    public void showSuccessView(){
        if(loadViewHelper == null){
            return;
        }
        loadViewHelper.restore();
    }
}
