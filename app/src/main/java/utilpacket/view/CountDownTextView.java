package utilpacket.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Koma
 * @date 2018/1/8
 * 时间控制按钮
 */

public class CountDownTextView extends AppCompatTextView implements View.OnClickListener{

    private long lenght = 120 ; // 倒计时长度,这里给了默认60秒
    private String textafter = "s";
    private String textbefore = "获取验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time;

    private MyHandler myHandler;

    public CountDownTextView(Context context){
        super(context);
        setOnClickListener(this);
        myHandler = new MyHandler(this);
    }

    public CountDownTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        myHandler = new MyHandler(this);
        setOnClickListener(this);
    }

    static class MyHandler extends Handler {

        private WeakReference weakReference;

        public MyHandler(CountDownTextView countDownTextView){
            weakReference = new WeakReference<>(countDownTextView);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountDownTextView countDownTextView = (CountDownTextView) weakReference.get();
            if(msg.what == 0){
                countDownTextView.setText(countDownTextView.time / 1000 + countDownTextView.textafter);
                countDownTextView.time -= 1000;
                if (countDownTextView.time < 0) {
                    countDownTextView.setEnabled(true);
                    countDownTextView.setText(countDownTextView.textbefore);
                    countDownTextView.clearTimer();
                }
            }
        }
    }

    //开始倒计时
    private void initTimer() {
        time = lenght * 1000;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                myHandler.sendEmptyMessage(0);
            }
        };
    }

    //倒计时结束
    public void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null) {
            t.cancel();
        }
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        if (l instanceof CountDownTextView) {
            super.setOnClickListener(l);
        } else {
            this.mOnclickListener = l;
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null) {
            mOnclickListener.onClick(v);
        }
    }

    /***
     * 开始倒计时
     */
    public void startTimer(){
        initTimer();
        this.setText(time / 1000 + textafter);
        this.setEnabled(false);
        t.schedule(tt, 0, 1000);
    }


    /** * 设置计时结束时候显示的文本 */
    public CountDownTextView setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    /** * 设置点击之前的文本 */
    public CountDownTextView setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    /**
     * 设置到计时长度
     *
     * @param lenght
     *            时间 默认毫秒
     * @return
     */
    public CountDownTextView setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }




}
