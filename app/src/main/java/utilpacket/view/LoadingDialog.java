package utilpacket.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.hjn.year_cake.R;


/**
 * Created by Koma on 2017/8/11.
 * Description：加载进度框
 * version 1.0 Content：
 */

public class LoadingDialog extends Dialog {



    private static LoadingDialog loadDialog;

    private boolean cancelable;

    private String tipMsg;

    public LoadingDialog(Context ctx){
        this(ctx, false, R.string.data_loading);
    }

    public LoadingDialog(Context ctx, boolean cancelable){
        this(ctx, cancelable, R.string.data_loading);
    }

    public LoadingDialog(final Context ctx, boolean cancelable, int tipMsg) {
        super(ctx);

        this.cancelable = cancelable;
        this.tipMsg = ctx.getResources().getString(tipMsg);

        this.getContext().setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.dialog_loading);
        // 必须放在加载布局后
        setparams();
        TextView tv = (TextView) findViewById(R.id.tvLoad);
        if (!TextUtils.isEmpty(ctx.getResources().getString(tipMsg))) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(tipMsg);
        }
    }

    public LoadingDialog(final Context ctx, boolean cancelable, String tipMsg) {
        super(ctx);

        this.cancelable = cancelable;
        this.tipMsg = tipMsg;

        this.getContext().setTheme(android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        setContentView(R.layout.dialog_loading);
        // 必须放在加载布局后
        setparams();
        TextView tv = (TextView) findViewById(R.id.tvLoad);
        if (!TextUtils.isEmpty(tipMsg)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(tipMsg);
        }
    }

    private void setparams() {
        this.setCancelable(cancelable);
        this.setCanceledOnTouchOutside(false);
        WindowManager windowManager = getWindow().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        // Dialog宽度
        lp.width = (int) (display.getWidth() * 0.7);
        Window window = getWindow();
        window.setAttributes(lp);
        if(null != window.getDecorView() &&
                null != window.getDecorView().getBackground()){
            window.getDecorView().getBackground().setAlpha(0);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!cancelable) {
                Toast.makeText(getContext(), tipMsg, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * show the dialog
     *
     * @param context
     */
    public static void show(Context context) {
        show(context, null, true);
    }

    /**
     * show dialog
     * @param context
     * @param cancelable
     */
    public static void show(Context context, boolean cancelable) {
        show(context, null, cancelable);
    }
    /**
     * show the dialog
     *
     * @param context Context
     * @param message String
     */
    public static void show(Context context, String message) {
        show(context, message, true);
    }

    /**
     * show the dialog
     *
     * @param context    Context
     * @param resourceId resourceId
     */
    public static void show(Context context, int resourceId) {
        show(context, context.getResources().getString(resourceId), true);
    }

    /**
     * show the dialog
     *
     * @param context    Context
     * @param message    String, show the message to user when isCancel is true.
     * @param cancelable boolean, true is can't dimiss，false is can dimiss
     */
    public static void show(Context context, String message, boolean cancelable) {
        if (context instanceof Activity) {
            if (((Activity) context).isFinishing()) {
                return;
            }
        }
        if (loadDialog != null && loadDialog.isShowing()) {
            return;
        }
        loadDialog = new LoadingDialog(context, cancelable, message);
        loadDialog.show();
    }

    /**
     * dismiss the dialog
     */
    public static void dismiss(Context context) {
        try {
            if (context instanceof Activity) {
                if (((Activity) context).isFinishing()) {
                    loadDialog = null;
                    return;
                }
            }

            if (loadDialog != null && loadDialog.isShowing()) {
                Context loadContext = loadDialog.getContext();
                if (loadContext != null && loadContext instanceof Activity) {
                    if (((Activity) loadContext).isFinishing()) {
                        loadDialog = null;
                        return;
                    }
                }
                loadDialog.dismiss();
                loadDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            loadDialog = null;
        }
    }


}
