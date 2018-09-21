package com.hjn.year_cake.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.hjn.year_cake.R;

/**
 * Created by ${templefck} on 2018/8/29.
 */
public class TopView extends RelativeLayout {
    private LinearLayout   mBack;
    private ImageView      mBackImg;
    private TextView       mBackTxt;
    private TextView       mTitle;
    private ImageView      mTitleimg;
    private LinearLayout   mFuction;
    private ImageView      mFucImgLeft;
    private TextView       mFucTxt;
    private ImageView      mFucImgRight;
    private RelativeLayout mBar;

    public TopView(Context context) {

        super(context);

        initView(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    public TopView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(context);
    }


    private void initView(Context mContext) {

        setFitsSystemWindows(true);
        setClipToPadding(true);

        LayoutInflater.from(mContext).inflate(R.layout.top_view, this, true);

        mBack = (LinearLayout) this.findViewById(R.id.mBack);
        mBackImg = (ImageView) this.findViewById(R.id.mBackImg);
        mBackTxt = (TextView) this.findViewById(R.id.mBackTxt);
        mTitle = (TextView) this.findViewById(R.id.mTitle);
        mTitleimg = (ImageView) this.findViewById(R.id.mTitleimg);
        mFuction = (LinearLayout) this.findViewById(R.id.mFuction);
        mFucImgLeft = (ImageView) this.findViewById(R.id.mFucImgLeft);
        mFucTxt = (TextView) this.findViewById(R.id.mFucTxt);
        mFucImgRight = (ImageView) this.findViewById(R.id.mFucImgRight);
        mBar= (RelativeLayout) this.findViewById(R.id.rl_title_bar);
        setBackground(R.color.grey_theme);
    }

    public void init(String title, final OnClickTopListener clickTopListener) {
        mTitle.setText(title);
        setView(true, 0, 0, 0, 0, clickTopListener);
    }

    public void init(boolean isBack, int backResId, int titleResId, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {
        mTitle.setText(titleResId);
        setView(isBack, backResId, fucLeftImg, fucResId, fucRightImg, clickTopListener);
    }

    public void init(boolean isBack, int backResId, int titleResId, int titleImg, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {
        mTitle.setText(titleResId);
        setView(isBack, backResId, fucLeftImg, fucResId, fucRightImg, clickTopListener);
        setTitleImg(titleImg);
    }

    public void init(boolean isBack, int backResId, String title, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {
        mTitle.setText(title);
        setView(isBack, backResId, fucLeftImg, fucResId, fucRightImg, clickTopListener);
    }

    private void setView(boolean isBack, int backResId, int fucLeftImg, int fucResId, int fucRightImg, final OnClickTopListener clickTopListener) {

        if (isBack) {
            mBackImg.setVisibility(View.VISIBLE);
        } else {
            mBackImg.setVisibility(View.GONE);
        }

        if (backResId != 0) {
            mBackTxt.setText(backResId);
        }

        if (clickTopListener != null) {
            mBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTopListener.onLeft();
                }
            });
            mTitle.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTopListener.onTitle();
                }
            });
            mFuction.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickTopListener.onRight();
                }
            });
        }

        mFucImgLeft.setImageResource(fucLeftImg);
        if (fucResId != 0) {
            mFucTxt.setText(fucResId);
        }
        mFucImgRight.setImageResource(fucRightImg);

        mTitleimg.setVisibility(View.GONE);
    }

    public void setLeftImg(int resId){

        mBackImg.setImageResource(resId);

    }



    public void setTitleImg(int resId) {
        if (resId != 0) {
            mTitleimg.setVisibility(View.VISIBLE);
            mTitleimg.setImageResource(resId);
        } else {
            mTitleimg.setVisibility(View.GONE);
            mTitleimg.setImageResource(resId);
        }
    }

    public void setTitleColor(int color){
        mTitle.setTextColor(getResources().getColor(color));
    }

    public String getRightText(){
        return mFucTxt.getText().toString();

    }

    public TextView getRightTextView(){
        return mFucTxt;
    }

    public void setRightTextVisibility(int visibility){

        mFucTxt.setVisibility(visibility);
    }

    public void setRightVisibility(int visibility){

        mFuction.setVisibility(visibility);
    }

    public void goneFuc() {
        mFucTxt.setVisibility(View.GONE);
    }

    public void visibleFuc() {
        mFucTxt.setVisibility(View.VISIBLE);
    }

    public void setRightText(String text) {
        mFucTxt.setText(text);
    }

    public void setRightTextSize(float size){
        mFucTxt.setTextSize(size);
    }

    public void setRightTextColor(int resId){
        mFucTxt.setTextColor(getResources().getColor(resId));
    }

    public void setRightTextEnable(boolean enable){
        mFucTxt.setEnabled(enable);
    }

    public void setmBackImg(int resId){
        mBackImg.setImageResource(resId);

    }


    public void setTitleText(String text) {
        mTitle.setText(text);
    }

    public void setLeftText(String resId){
        mBackTxt.setText(resId);
    }
    public void setTitleSize(float size){
        mTitle.setTextSize(size);
    }

    public void setFucLeftImg(int resId) {
        mFucImgLeft.setImageResource(resId);
    }

    public void setFucRightImg(int resId) {
        if(resId != 0){
            mFucImgRight.setImageResource(resId);
            mFucImgRight.setVisibility(View.VISIBLE);
        }else{
            mFucImgRight.setVisibility(View.GONE);
        }

    }

    public void setBackground(int color){

        mBar.setBackgroundColor(getResources().getColor(color));
    }

    public void setBackgroundResource(int res){
        mBar.setBackgroundResource(res);
    }

    public static abstract class OnClickTopListener {

        public void onLeft() {
        }

        public void onTitle() {
        }

        public void onRight() {
        }
    }

}
