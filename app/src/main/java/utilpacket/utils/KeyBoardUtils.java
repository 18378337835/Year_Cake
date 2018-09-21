package utilpacket.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @author Koma
 * @date 2017/12/20.
 */
public class KeyBoardUtils {
    /**
     * 打卡软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext  上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * Hide keyboard
     *
     * @param activity
     */
    public static void closeKeyboard(Activity activity) {
        if (activity == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != imm && imm.isActive()) {
                if (activity.getCurrentFocus() == null || activity.getCurrentFocus().getWindowToken() == null) {
                    return;
                }
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 展示软键盘
    public static void showkeyboard(final EditText edit, final Activity activity) {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {
                        InputMethodManager m = (InputMethodManager) edit.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        m.showSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), InputMethodManager.SHOW_FORCED);
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
