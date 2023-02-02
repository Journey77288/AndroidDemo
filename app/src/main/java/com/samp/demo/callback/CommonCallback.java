package com.samp.demo.callback;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : 公共回调
 * </pre>
 */
public abstract class CommonCallback<T> {
    public abstract void onSuccess(T data);

    public abstract void onError(String msg);
}
