package com.example.byfastapp.event;


import io.reactivex.Observable;

/**
 * Created by Administrator on 2017/4/20.
 *
 */

public interface BusInterface {
    //普通发送,不需要指定观察者
    void send(Object object);
    //携带key发送
    void send(int key, Object object);
    //String 作为key
    void send(String key, Object object);

    //发送Sticky事件,处理事件发送太超前
    void sendSticky(Object object);
    void sendSticky(int key, Object object);
    void sendSticky(String key, Object object);

    //普通收消息
    Observable<Object> toObservable();
    Observable<Object> toObservable(int key);
    Observable<Object> toObservable(String key);
    //收取某种类型数据
    <T> Observable<T> toObservable(Class<T> eventType);
    <T> Observable<T> toObservable(int key, Class<T> eventType);
    <T> Observable<T> toObservable(String key, Class<T> eventType);

    //收取sticky事件
    Observable<Object> toObservableSticky();
    Observable<Object> toObservableSticky(int key);
    Observable<Object> toObservableSticky(String key);

    <T> Observable<T> toObservableSticky(Class<T> eventType);
    <T> Observable<T> toObservableSticky(int key, Class<T> eventType);
    <T> Observable<T> toObservableSticky(String key, Class<T> eventType);


}
