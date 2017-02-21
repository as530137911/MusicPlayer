package com.shenstudio.musicplayer.DetailFragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenstudio.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CoverPicture extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cover_picture, container, false);
    }

}
