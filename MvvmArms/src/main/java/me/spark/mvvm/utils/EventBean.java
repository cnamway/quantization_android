package me.spark.mvvm.utils;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/9
 * 描    述：EventBus对象基类
 * 修订历史：
 * ================================================
 */
public class EventBean<T> {
    private String origin;                      //Tag
    private boolean statue;                     //状态
    private int type;                           //viewpage index
    private int code;                           //状态码
    private String message;                     //消息
    private T object;

    public EventBean() {
    }

    public EventBean(T object) {
        this.object = object;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public boolean isStatue() {
        return statue;
    }

    public void setStatue(boolean statue) {
        this.statue = statue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
