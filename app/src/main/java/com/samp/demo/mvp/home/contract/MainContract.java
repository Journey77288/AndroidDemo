package com.samp.demo.mvp.home.contract;

import com.samp.demo.callback.CommonListCallback;
import com.samp.demo.mvp.base.BaseModel;
import com.samp.demo.mvp.base.BasePresent;
import com.samp.demo.mvp.base.BaseView;

import java.util.List;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/1
 *     desc   : 主页契约类
 * </pre>
 */
public interface MainContract {
    interface View extends BaseView {
        void setListData(List<String> data);
    }

    interface Model extends BaseModel {
        void getData(CommonListCallback<String> callback);
    }

    abstract class Present extends BasePresent<View, Model> {
        public Present(View view) {
            super(view);
        }

        public abstract void getData();
    }
}
