package utilpacket.utils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author koma
 * @date 2018/1/8
 * @describe
 */

public class EventBusUtils {

    /**
     * EventBus 的注册
     * @param subscriber
     */
    public static void register(Object subscriber){
        EventBus.getDefault().register(subscriber);
    }

    /**
     * EventBus 的解耦
     * @param subscriber
     */
    public static void unregister(Object subscriber){
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 发送事件
     * @param event
     */
    public static void post(Object event){
        EventBus.getDefault().post(event);
    }

    /**
     * 发送黏性事件
     * @param event
     */
    public static void postSticky(Object event){
        EventBus.getDefault().postSticky(event);
    }

}
