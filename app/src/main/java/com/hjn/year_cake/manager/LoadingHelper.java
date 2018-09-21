package com.hjn.year_cake.manager;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.hjn.year_cake.R;
import utilpacket.viewhelp.IVaryViewHelper;
import utilpacket.viewhelp.VaryViewHelper;

/**
 * Created by Year_Cake on 2018/9/13.
 */

public class LoadingHelper {

    private Animation mAnimation1;
    private Animation mAnimation;

    public Context context;

    private IVaryViewHelper helper;

    public LoadingHelper(View view) {
        this(new VaryViewHelper(view));
    }

    public LoadingHelper(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * 数据错误时显示的布局
     * @param onClickListener
     */
    public void showError(View.OnClickListener onClickListener) {
        showError(onClickListener, R.string.data_error);
    }

    /**
     * 数据错误时显示的布局
     * @param onClickListener
     */
    public void showError(View.OnClickListener onClickListener, int id) {
        View layout = helper.inflate(R.layout.dkzx_load_error);
        TextView tv = (TextView) layout.findViewById(R.id.tv_error);
        tv.setText(id);
        layout.setOnClickListener(onClickListener);
        helper.showLayout(layout);
    }


    /**
     * 空数据时显示默认的图片
     * @param onClickListener：布局点击事件
     */
    public void showEmpty(View.OnClickListener onClickListener) {
        	//	showEmpty(onClickListener,R.mipmap.icon_no_data);
    }

    /**
     * 空数据时要显示的布局
     * @param onClickListener：布局点击事件
     * @param imageId：要显示的图片Id
     */
    public void showEmpty(View.OnClickListener onClickListener, int imageId) {
        View layout    = helper.inflate(R.layout.dkzx_load_empty);
        ImageView imageView = (ImageView) layout.findViewById(R.id.iv_empty);
        imageView.setImageResource(imageId);
        layout.setOnClickListener(onClickListener);
        helper.showLayout(layout);
    }

    /**
     * 空数据时要显示的布局
     * @param onClickListener：布局点击事件
     * @param resId：要显示的图片布局
     */
    public void showEmpty(int resId,View.OnClickListener onClickListener) {
        View layout = helper.inflate(resId);
        layout.setOnClickListener(onClickListener);
        helper.showLayout(layout);
    }

    public void showLoading(String loadText) {
        View layout = helper.inflate(R.layout.dkzx_load_ing);

        ImageView mImageView=layout.findViewById(R.id.loading_icon);
        ImageView mImageView1=layout.findViewById(R.id.loading_icon1);
        mAnimation = AnimationUtils.loadAnimation(MyApp.context, R.anim.loading_big);
        mAnimation1 = AnimationUtils.loadAnimation(MyApp.context,R.anim.loading_small);
        mImageView.setAnimation(mAnimation );
        mImageView1.setAnimation(mAnimation1 );
        mAnimation.start();
        mAnimation1.start();

        TextView textView = (TextView) layout.findViewById(R.id.tv_empty);
        textView.setText(loadText);
        helper.showLayout(layout);
    }


    /**
     * 无网络时显示的布局
     * @param onClickListener
     */
    public void showNetwordNoConnect(View.OnClickListener onClickListener, int id) {
        View layout = helper.inflate(R.layout.dkzx_no_network);
        TextView tv     = (TextView) layout.findViewById(R.id.tv_empty);
        tv.setText(id);
        layout.setOnClickListener(onClickListener);
        helper.showLayout(layout);
    }

    /**
     * 无网络时显示的布局
     * @param onClickListener
     */
    public void showNetwordNoConnect(View.OnClickListener onClickListener) {
        showNetwordNoConnect(onClickListener, R.string.server_unconnect);
    }


    public void restore() {
        helper.restoreView();
    }
}
