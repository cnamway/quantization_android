package me.spark.mvvm.utils;


import org.greenrobot.eventbus.EventBus;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/9
 * 描    述：EventBus工具类
 * 修订历史：
 * ================================================
 */

public class EventBusUtils {
    /**
     * 注册EventBus
     *
     * @param object
     */
    public static void register(Object object) {
        if (!EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().register(object);
        }
    }


    /**
     * 反注册EventBus
     *
     * @param object
     */
    public static void unRegister(Object object) {
        if (EventBus.getDefault().isRegistered(object)) {
            EventBus.getDefault().unregister(object);
        }
    }

    /**
     * @param eventBean
     */
    public static void postEvent(EventBean eventBean) {
        EventBus.getDefault().post(eventBean);
    }

    public static void postEvent(WebSocketResponse webSocketResponse) {
        EventBus.getDefault().post(webSocketResponse);
    }

    public static void postEvent(WebSocketRequest webSocketRequest) {
        EventBus.getDefault().post(webSocketRequest);
    }

    public static void postEvent(ServiceOpenSuccessEvent serviceOpenSuccessEvent) {
        EventBus.getDefault().post(serviceOpenSuccessEvent);
    }

    /**
     * post success
     *
     * @param origin
     * @param code
     * @param message
     * @param object
     */
    public static void postSuccessEvent(String origin, int code, String message, Object object) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(true);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setMessage(message);
        eventBean.setObject(object);
        EventBusUtils.postEvent(eventBean);
    }

    public static void postSuccessEvent(String origin, int code, String message, int type, Object object) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(true);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setType(type);
        eventBean.setMessage(message);
        eventBean.setObject(object);
        EventBusUtils.postEvent(eventBean);
    }

    public static void postSuccessEvent(String origin, int code, String message) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(true);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setMessage(message);
        EventBusUtils.postEvent(eventBean);
    }

    public static void postSuccessEvent(String origin, int code, String message, int type) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(true);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setType(type);
        eventBean.setMessage(message);
        EventBusUtils.postEvent(eventBean);
    }


    public static void postErrorEvent(String origin, int code, String message, Object object) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(false);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setMessage(message);
        eventBean.setObject(object);
        EventBusUtils.postEvent(eventBean);
    }

    public static void postErrorEvent(String origin, int code, String message, int type, Object object) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(false);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setType(type);
        eventBean.setMessage(message);
        eventBean.setObject(object);
        EventBusUtils.postEvent(eventBean);
    }

    public static void postJumpEvent(String origin) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(true);
        eventBean.setOrigin(origin);
        EventBusUtils.postEvent(eventBean);
    }

    /**
     * post error
     *
     * @param origin
     * @param code
     * @param message
     */
    public static void postErrorEvent(String origin, int code, String message) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(false);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setMessage(message);
        EventBusUtils.postEvent(eventBean);
    }

    public static void postErrorEvent(String origin, int code, String message, int type) {
        EventBean eventBean = new EventBean();
        eventBean.setStatue(false);
        eventBean.setOrigin(origin);
        eventBean.setCode(code);
        eventBean.setType(type);
        eventBean.setMessage(message);
        EventBusUtils.postEvent(eventBean);
    }
}
