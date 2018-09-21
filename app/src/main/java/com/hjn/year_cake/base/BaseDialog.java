package com.hjn.year_cake.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.hjn.year_cake.listener.OnDialogInterface;

import butterknife.ButterKnife;
import utilpacket.utils.ScreenUtils;

/**
 * @author koma
 * 弹框基类
 */

public abstract class BaseDialog<V extends BaseView, T extends BasePresenter<V>>
        extends DialogFragment
{

    public OnDialogInterface mOnDialogInterface;

    public void setOnDialogInterface(OnDialogInterface onDialogInterface) {
        mOnDialogInterface = onDialogInterface;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(savedInstanceState), null);
        initView(view);
        return view;
    }

    public abstract int getLayoutId(@Nullable Bundle savedInstanceState);

    public void initView(View view){
        ButterKnife.bind(this, view);
        setCancelable(getCancelable());

        if(isBackDismiss()){
            this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
                @Override
                public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                    if(keyCode == KeyEvent.KEYCODE_BACK){
                        dismiss();
                        return true;
                    }else {
                        //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
                        return false;
                    }
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics( dm );
        WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ScreenUtils.getScreenWidth(getActivity()) * 9 / 10;
        getDialog().getWindow().setAttributes(params);

    }

    /**
     * 是否可取消
     * @return
     */
    public boolean getCancelable(){
        return false;
    }

    /**
     * 点下返回键是否消失
     * @return
     */
    public boolean isBackDismiss(){
        return true;
    }

}
