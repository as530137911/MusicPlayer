package com.shenstudio.musicplayer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shenstudio.musicplayer.DetailFragment.CoverPicture;
import com.shenstudio.musicplayer.DetailFragment.MusicLIst;
import com.shenstudio.musicplayer.DetailFragment.lyrics;

import java.util.ArrayList;
import java.util.List;

public class MusicDetailActivity extends BaseActivity {
    private final static String TAG = "MusicDetailActivity";
    private LinearLayout detail_root;
    private TextView detail_MusicTitle;
    private ImageView back;
    private ViewPager detail_container;
    private LinearLayout dot;

    void init() {
        //绑定视图控件
        detail_MusicTitle = (TextView) findViewById(R.id.detail_MusicTitle);
        back = (ImageView) findViewById(R.id.detail_back);
        detail_container = (ViewPager) findViewById(R.id.detail_container);
        dot = (LinearLayout) findViewById(R.id.dot);

        //绑定视图点击事件
        if (back != null)
            back.setOnClickListener(new ClickLIstener());
        else
            Log.d(TAG, "back =" + back);

        initViewPager();
    }

    /**
     * 处理中间区域的切换显示
     */
    void initViewPager() {
        final List<Fragment> list = new ArrayList<>();
        list.add(new MusicLIst());
        list.add(new CoverPicture());
        list.add(new lyrics());
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), list);
        detail_container.setAdapter(adapter);
        detail_container.setCurrentItem(1);

        for (int i = 0; i < list.size(); i++) {
            dot.addView(createDot());
        }
        //处理中间区域的滑动时的位置指示
        detail_container.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //标识当前的页面
                for (int i = 0; i < list.size(); i++) {
                    if (i == position) {
                        dot.getChildAt(position).setSelected(true);
                    } else {
                        dot.getChildAt(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    View createDot() {
        return LayoutInflater.from(this).inflate(R.layout.layout_dot, null);
    }

    void getInfoFromIntent() {
        Intent intent = getIntent();
        MusicInfo info = intent.getParcelableExtra("info");
        if (info == null){
            //将歌曲名和封面设置为默认
        }else {
            //正常从info中获取到信息，进行配置
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_detail);
        init();
        setBackGroundFromMusicCover();

        getInfoFromIntent();
    }

    /**
     * 从音乐的封面中抽取出一种色调作为背景主色调
     */
    void setBackGroundFromMusicCover() {
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_icon);
        Palette palette = Palette.from(bitmap).generate();
        int bg_Color = palette.getDarkVibrantColor(Color.parseColor("#88917201"));
        detail_root = (LinearLayout) findViewById(R.id.detail_root);
        detail_root.setBackgroundColor(bg_Color);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.activity_bottom_gone);
    }


    private class ClickLIstener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.detail_back:
                    finish();
                    break;
            }
        }
    }
}
