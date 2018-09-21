package com.hjn.year_cake.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.hjn.year_cake.R;
import com.hjn.year_cake.manager.LoadingHelper;
import com.hjn.year_cake.utils.CacheUtil;
import com.jrwd.okhttputils.OkHttpUtils;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;
import utilpacket.glideutil.GlideApp;
import utilpacket.utils.ActivitiesManager;
import utilpacket.utils.EventBusUtils;
import utilpacket.utils.StringUtils;
import utilpacket.viewhelp.VaryViewHelperX;

/**
 * @author Koma
 * Activity基类
 */

public abstract class BaseActivity<V extends BaseView,
        T extends BasePresenter<V>>
        extends AppCompatActivity
{


    public T mPresenter;

    public Context mContext;

    public Toolbar toolbar;

    /**
     * 右标题
     */
    public TextView tvRight;
    /**
     * 右图标
     */
    public ImageView ivRight;
    public CacheUtil mCacheUtil;
    private LoadingHelper loadViewHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId(savedInstanceState));
        setStatusBar();
        mContext = this;
        mPresenter = getPresenter();
        mCacheUtil = CacheUtil.get(mContext);
        if(isRegisterEvent()){
            EventBusUtils.register(this);
        }

        initView();
    }

    public void setStatusBar(){
        ImmersionBar.with(this)
                    .transparentStatusBar()
                .keyboardEnable(false)
                    .init();
//        StatusBarUtils.setStatusBarAlpha(this, 0, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(null != mPresenter){
            mPresenter.onAttach((V)this);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        GlideApp.get(this).clearMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != mPresenter){
            mPresenter.onDetch();
        }
        ImmersionBar.with(this).destroy();
        if(isRegisterEvent()){
            EventBusUtils.unregister(this);
        }
        UMShareAPI.get(this).release();
        OkHttpUtils.getInstance().cancelTag(this);
    }

    /**
     * 返回layout资源文件
     * @param savedInstanceState
     * @return
     */
    public abstract int getLayoutId(Bundle savedInstanceState);

    /**
     * 初始化
     */
    public void initView(){
        ButterKnife.bind(this);
    }


    /**
     * 获取presenter
     * @return
     */
    public abstract T getPresenter();

    /**********************************************************************************************/


    /**
     * 设置toolbar
     * @param back
     * @param title
     */
    public void setToolbar(final int back, @Nullable String title){

        if(toolbar == null){
            toolbar = findViewById(R.id.toolBar);
        }
        if(!StringUtils.isEmpty(title)){
            toolbar.setTitle(title);
        }
        setSupportActionBar(toolbar);
        if(back == 0){
            toolbar.setNavigationIcon(R.mipmap.icon_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivitiesManager.closeThis(ActivitiesManager.currentActivity());
                }
            });
        }else{
            toolbar.setNavigationIcon(back);
        }
    }

    /**
     * 设置toolbar
     * @param back
     * @param resId
     */
    public void setToolbar(int back, int resId){
        setToolbar(back, mContext.getResources().getString(resId));
    }

    /**
     * 设置toolbar
     * @param title
     */
    public void setToolbar(@Nullable String title){
        setToolbar(0, title);
    }

    /**
     * 设置toolbar
     * @param resId
     */
    public void setToolbar(int resId){
        setToolbar(0, resId);
    }

    /**
     * toolbar按钮点击事件
     * @param click
     */
    public void setOnBackClick(@Nullable View.OnClickListener click){

        if(toolbar != null){
            if(click != null){
                toolbar.setNavigationOnClickListener(click);
            }
        }
    }


    /**
     * 设置有标题文字
     * @param right
     * @param clickListener
     */
    public void setBarRightText(@Nullable String right, @Nullable View.OnClickListener clickListener){
        if(tvRight == null){
            tvRight = findViewById(R.id.tv_right);
        }
        if(!StringUtils.isEmpty(right)){
            tvRight.setText(right);
        }
        if(clickListener != null){
            tvRight.setOnClickListener(clickListener);
        }
    }

    /**
     * 设置右标题
     * @param right
     * @param clickListener
     */
    public void setBarRightText(int right, @Nullable View.OnClickListener clickListener){
        setBarRightText(mContext.getResources().getString(right), clickListener);
    }

    /**
     * 设置有标题显示隐藏
     * @param visiable
     */
    public void setBarRightTextVisiable(boolean visiable){
        if(tvRight == null){
            tvRight = findViewById(R.id.tv_right);
        }
        tvRight.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    /**
     * 设置右图标
     * @param resId
     * @param clickListener
     */
    public void setBarRightImage(int resId, @Nullable View.OnClickListener clickListener){
        if(ivRight == null){
            ivRight = findViewById(R.id.iv_right);
        }
        if(resId != 0){
            ivRight.setImageResource(resId);
        }
        if(clickListener != null){
            ivRight.setOnClickListener(clickListener);
        }
    }

    /**********************************************************************************************/

    /**
     * 是否注册EventBus
     * @return
     */
    public boolean isRegisterEvent(){
        return false;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = new int[2];
            //获取当前输入框相对于屏幕的位置
            v.getLocationOnScreen(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getRawX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
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
