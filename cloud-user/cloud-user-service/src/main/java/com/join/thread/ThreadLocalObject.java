package com.join.thread;

/**
 * @author join
 * @Description
 * @date 2023/3/1 15:28
 */
public class ThreadLocalObject {

    private static final ThreadLocal<Object> threadLocal=new ThreadLocal<>();

    private ThreadLocalObject(){}

    public static void put(Object value){
        threadLocal.set(value);
    }

    public static Object get(){
        return threadLocal.get();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
