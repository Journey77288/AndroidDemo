package com.samp.demo.mvp.home.model;

import com.samp.demo.callback.CommonListCallback;
import com.samp.demo.mvp.home.contract.MainContract;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : 主页Model
 * </pre>
 */
public class MainModel implements MainContract.Model {

    @Override
    public void getData(CommonListCallback<String> callback) {
        List<String> data = new ArrayList<>();
        data.add("RecyclerView");
        data.add("ViewPager");
        callback.onSuccess(data);
    }
}
