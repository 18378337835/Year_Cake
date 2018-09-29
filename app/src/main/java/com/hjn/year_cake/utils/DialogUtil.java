package com.hjn.year_cake.utils;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.hjn.year_cake.R;

/**
 * Created by ${templefck} on 2018/9/10.
 */
public class DialogUtil {
    public static Dialog dialog;


    public static void showUpdateDialog(Activity activity, String title, String content, String leftBtnText, String rightBtnText, final OnDialogListener mListener){

        if(activity.isFinishing()){
            return;
        }

        final Dialog udpateDialog = new Dialog(activity, R.style.updata_dialog);
        udpateDialog.setContentView(R.layout.updata_dialog);
            TextView tv_title = (TextView) udpateDialog.findViewById(R.id.tv_title);
            TextView tv_content = (TextView) udpateDialog.findViewById(R.id.tv_content);
            TextView btn_left = (TextView) udpateDialog.findViewById(R.id.btn_cancel);
            TextView btn_right = (TextView) udpateDialog.findViewById(R.id.btn_sure);
            View divider_view = udpateDialog.findViewById(R.id.diver_view);
            udpateDialog.setCanceledOnTouchOutside(false);
            udpateDialog.setCancelable(false);
            tv_title.setText(title);
            tv_content.setText(content);
            udpateDialog.setCanceledOnTouchOutside(false);
            if (TextUtils.isEmpty(leftBtnText)) {
                btn_left.setVisibility(View.GONE);
                divider_view.setVisibility(View.GONE);
            } else {
                btn_left.setText(leftBtnText);

            }
            if (!TextUtils.isEmpty(rightBtnText)) {
                btn_right.setText(rightBtnText);
            }
            btn_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    udpateDialog.cancel();
                    if (mListener != null) {
                        mListener.onCancel();
                    }
                }
            });

            btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    udpateDialog.cancel();
                    if (mListener != null) {
                        mListener.onSure();
                    }
                }
            });
           udpateDialog.show();
        }



    /**
     * Dialog消失
     */
    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
            dialog = null;
        }
    }

    public interface OnDialogListener{
        void onSure();
        void onCancel();

    }

    public interface OnGrabTypeListener{
        void onSure(int type);
    }
}
