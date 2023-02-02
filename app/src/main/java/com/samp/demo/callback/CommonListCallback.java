package com.samp.demo.callback;

import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : 列表公共回调
 * </pre>
 */
public abstract class CommonListCallback<T> {
    public abstract void onSuccess(List<T> data);

    public abstract void onError(List<T> data);
}
