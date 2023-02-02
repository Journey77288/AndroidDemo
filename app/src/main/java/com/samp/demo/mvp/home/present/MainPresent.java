package com.samp.demo.mvp.home.present;

import com.samp.demo.callback.CommonListCallback;
import com.samp.demo.mvp.home.contract.MainContract;
import com.samp.demo.mvp.home.model.MainModel;

import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : 主页Present
 * </pre>
 */
public class MainPresent extends MainContract.Present {

    public MainPresent(MainContract.View view) {
        super(view);
    }

    @Override
    protected void initModel() {
        super.initModel();
        mModel = new MainModel();
    }

    @Override
    public void getData() {
        mModel.getData(new CommonListCallback<String>() {
            @Override
            public void onSuccess(List<String> data) {
                mView.setListData(data);
            }

            @Override
            public void onError(List<String> data) {

            }
        });
    }
}
