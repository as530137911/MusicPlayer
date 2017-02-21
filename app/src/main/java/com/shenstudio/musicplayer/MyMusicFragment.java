package com.shenstudio.musicplayer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


public class MyMusicFragment extends Fragment {
    private LinearLayout local_music;
    private LinearLayout all_music;
    private LinearLayout favorite_muisc;
    private TextView creat_list;

    void init(View view) {
        local_music = (LinearLayout) view.findViewById(R.id.local_music);
        all_music = (LinearLayout) view.findViewById(R.id.all_music);
        favorite_muisc = (LinearLayout) view.findViewById(R.id.favorite_muisc);
        creat_list = (TextView) view.findViewById(R.id.creat_list);

        local_music.setOnClickListener(new ListTypeClickListener());
        all_music.setOnClickListener(new ListTypeClickListener());
        favorite_muisc.setOnClickListener(new ListTypeClickListener());

        //处理创建列表的简单逻辑
        creat_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText list_name = new EditText(getContext());
                list_name.setHint(R.string.new_music_list_name);
                new AlertDialog.Builder(getContext())
                        .setView(list_name)
                        .setTitle(R.string.creat_new_muisc_list)
                        .setPositiveButton(R.string.create, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String str = list_name.getText().toString().trim();
                                Toast.makeText(getContext(), "创建音乐列表" + str, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.cancel, null)
                        .show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_music, container, false);
        init(view);
        return view;
    }

    /**
     * 启动对应的页面
     * */
    private class ListTypeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), ListActivity.class);
            switch (v.getId()) {
                case R.id.local_music:
                    intent.putExtra("type", getString(R.string.music_local));
                    break;
                case R.id.all_music:
                    intent.putExtra("type", getString(R.string.music_all));
                    break;
                case R.id.favorite_muisc:
                    intent.putExtra("type", getString(R.string.music_favorite));
                    break;

            }
            startActivity(intent);
        }
    }
}
