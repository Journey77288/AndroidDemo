package com.samp.demo.mvp.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.samp.demo.R;

/**
 * <pre>
 *     author : Journey
 *     time   : 2023/2/24
 *     desc   : 视频流 Fragment
 * </pre>
 */
public class VideoStreamFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_stream, container, false);
    }
}
