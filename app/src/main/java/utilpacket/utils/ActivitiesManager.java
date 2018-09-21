package utilpacket.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * @author Koma
 * @date 2017-12-15
 * Description：Activity管理类
 */

public class ActivitiesManager {
    public static Stack<Activity> activities = new Stack<>();

    /**
     * 创建activity
     * @param activity
     */
    public static void createActivity(Activity activity){
        if(activity ==null){
            return;
        }
        activities.add(activity);
    }

    /**
     * 移除当前activity
     * @param activity
     */
    public static void removeActivity(Activity activity){
        if(activity ==null){
            return;
        }
        activities.remove(activity);
    }

    /**
     * 关闭所有的activity
     */
    public static void closeAll(){
        Iterator<Activity> iterator =  activities.iterator();
        while(iterator.hasNext()){
            Activity activity = iterator.next();
            activity.finish();
            iterator.remove();
        }
    }

    /**
     * 关闭当前的 activity
     * @param activity
     */
    public  static void  closeThis(Activity activity){
        if(activity ==null){
            return;
        }
        Iterator<Activity> iterator =  activities.iterator();
        while(iterator.hasNext()){
            Activity a = iterator.next();
            if(a==activity){
                a.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 关闭除了当前这一个activity 的其他activity
     * @param activity
     */
    public static void closeExcept(Activity activity){
        if(activity ==null){
            return;
        }
        Iterator<Activity> iterator =  activities.iterator();
        while(iterator.hasNext()){
            Activity a = iterator.next();
            if(a!=activity){
                a.finish();
                iterator.remove();
            }
        }
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public static Activity currentActivity() {
        if (activities.isEmpty()) {
            return null;
        }
        Activity activity = activities.lastElement();
        return activity;
    }

    /**
     * 根据给定的class进行Aactivity跳转
     * @param targetActivity
     */
    public static void intentActivity(Class<? extends Activity> targetActivity){
        intentActivity(targetActivity, false);
    }

    /**
     * 根据给定的class进行Aactivity跳转
     * @param targetActivity      要跳转的Activity
     * @param isFinish            是否关闭Activity对象
     */
    public static void intentActivity(Class<? extends Activity> targetActivity, boolean isFinish){
        if(null != currentActivity() && null != targetActivity){
            currentActivity().startActivity(new Intent(currentActivity(), targetActivity));
            if(isFinish){
                currentActivity().finish();
            }
        }
    }

    /**
     * 根据给定的Intent进行Activity跳转
     * @param intent
     */
    public static void intentActivity(Intent intent){
        intentActivity(intent, false);
    }

    /**
     * 根据给定的Intent进行Activity跳转
     * @param intent            要传递的Intent对象
     * @param isFinish          是否关闭Activity对象
     */
    public static void intentActivity(Intent intent, boolean isFinish){
        if(null != currentActivity()){
            currentActivity().startActivity(intent);
            if(isFinish){
                currentActivity().finish();
            }
        }
    }


    /**
     * 根据给定的class，并且带参数进行Aactivity跳转
     * @param targetActivity      要跳转的Activity
     * @param params              传递的数据集合
     * @param isFinish            是否关闭Activity对象
     */
    public static void intentActivity(Class<? extends Activity> targetActivity,
                                      Map<String,Object> params, boolean isFinish){
        if( null != params ){
            Intent intent = new Intent(currentActivity(), targetActivity);
            for(Map.Entry<String, Object> entry : params.entrySet()){
                setValueToIntent(intent, entry.getKey(), entry.getValue());
            }
            intentActivity(intent, isFinish);
        }
    }

    /**
     * @param intent         Inent对象
     * @param key            Inent对象的key
     * @param val            Inent对象的值
     */
    public static void setValueToIntent(Intent intent, String key, Object val) {
        if (val instanceof Boolean) {
            intent.putExtra(key, (Boolean) val);
        } else if (val instanceof Boolean[]) {
            intent.putExtra(key, (Boolean[]) val);
        } else if (val instanceof String) {
            intent.putExtra(key, (String) val);
        } else if (val instanceof String[]) {
            intent.putExtra(key, (String[]) val);
        } else if (val instanceof Integer) {
            intent.putExtra(key, (Integer) val);
        } else if (val instanceof Integer[]) {
            intent.putExtra(key, (Integer[]) val);
        } else if (val instanceof Long) {
            intent.putExtra(key, (Long) val);
        } else if (val instanceof Long[]) {
            intent.putExtra(key, (Long[]) val);
        } else if (val instanceof Double) {
            intent.putExtra(key, (Double) val);
        } else if (val instanceof Double[]) {
            intent.putExtra(key, (Double[]) val);
        } else if (val instanceof Float) {
            intent.putExtra(key, (Float) val);
        } else if (val instanceof Float[]) {
            intent.putExtra(key, (Float[]) val);
        }
    }

    /**
     * 判断object是否为null，不为null就跳转
     * @param o
     * @param targetActivity
     */
    public static void clickIntent(Object o, Class<? extends Activity> targetActivity){
        if(null != o){
            intentActivity(targetActivity);
        }
    }

    /**
     * 界面跳转并携带序列号数据
     * @param o
     * @param targetActivity
     */
    public static void clickIntentValue(Object o, Class<? extends Activity> targetActivity){
        if(null != o){
            Intent intent = new Intent(currentActivity(), targetActivity);
            intent.putExtra(o.getClass().getSimpleName(), (Serializable)o);
            intentActivity(intent);
        }
    }
    /**
     * 获取activities的长度
     * @return
     */
    public static int getActivitiesSize(){
        return activities.size();
    }

}
