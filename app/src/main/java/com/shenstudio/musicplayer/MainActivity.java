package com.shenstudio.musicplayer;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private final static String TAG = "MainActivity";

    private Toolbar titlebar;
    private ViewPager content;
    private RadioButton title_MyMusic;
    private RadioButton title_MusicStore;
    private RadioGroup title_group;
    private RelativeLayout home_musicDetail;
    private TextView music_title;
    private TextView music_artist;
    private ImageView music_next;
    private ImageView music_playCon;
    private RelativeLayout activity_main;

    //获取全局的上下文
    private APP app;

    void initUI() {
        titlebar = (Toolbar) findViewById(R.id.home_titlebar);
        content = (ViewPager) findViewById(R.id.home_content);
        title_MyMusic = (RadioButton) findViewById(R.id.title_MyMusic);
        title_MusicStore = (RadioButton) findViewById(R.id.title_MusicStore);
        title_group = (RadioGroup) findViewById(R.id.title_group);
        home_musicDetail = (RelativeLayout) findViewById(R.id.home_musicDetail);
        music_next = (ImageView) findViewById(R.id.home_music_next);
        music_playCon = (ImageView) findViewById(R.id.home_music_playCon);
        music_title = (TextView) findViewById(R.id.home_MusicTitle);
        music_artist = (TextView) findViewById(R.id.home_MusicArtist);
        activity_main = (RelativeLayout) findViewById(R.id.activity_main);

        setSupportActionBar(titlebar);
        title_group.setOnCheckedChangeListener(new MyCheckChangeListener());

        home_musicDetail.setOnClickListener(new HomePageClickListener());
        music_next.setOnClickListener(new HomePageClickListener());
        music_playCon.setOnClickListener(new HomePageClickListener());
    }

    /**
     * * 初始化viewpager
     */
    void initViewPager() {
        MyMusicFragment myMusicFragment = new MyMusicFragment();
        MusicStoreFragment musicStoreFragment = new MusicStoreFragment();

        List<Fragment> list = new ArrayList<>();
        list.add(0, myMusicFragment);
        list.add(1, musicStoreFragment);

        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), list);
        content.setAdapter(adapter);

        content.setCurrentItem(0);

        content.addOnPageChangeListener(new MyPagerChangListener());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        initViewPager();

        app = (APP) getApplicationContext();
        Log.d(TAG, "app = " + app);
    }

    /**
     * 顶部栏的标题转换监听
     */
    private class MyCheckChangeListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.title_MyMusic:
                    content.setCurrentItem(0, true);
                    break;
                case R.id.title_MusicStore:
                    content.setCurrentItem(1, true);
                    break;
            }
        }
    }

    /**
     * Viewpager页面切换监听
     */
    private class MyPagerChangListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            switch (position) {

                case 0:
                    title_group.check(R.id.title_MyMusic);
                    title_MyMusic.setTextColor(Color.parseColor("#FFFFFFFF"));
                    title_MusicStore.setTextColor(Color.parseColor("#88FFFFFF"));
                    break;
                case 1:
                    title_group.check(R.id.title_MusicStore);
                    title_MyMusic.setTextColor(Color.parseColor("#88FFFFFF"));
                    title_MusicStore.setTextColor(Color.parseColor("#FFFFFFFF"));
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 主页上的按钮点击事件控制
     */
    private class HomePageClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_musicDetail:
                    final Intent intent = new Intent(MainActivity.this, MusicDetailActivity.class);
                    Animation tanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottombar_hide);
                    tanim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            startActivityForResult(intent, 0);
                            Bundle bundle = new Bundle();
                            if (app != null) {
                                bundle.putSerializable("info", app.getCurrInfo());
                            } else {
                                Log.d(TAG, "app出现指针异常");
                            }
                            intent.putExtras(bundle);
                            overridePendingTransition(R.anim.activity_bottom_show, 0);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    tanim.setFillAfter(true);
                    home_musicDetail.startAnimation(tanim);
                    break;

                case R.id.home_music_playCon:
                    Utils.showToast(MainActivity.this, "PlayCon");
                    break;
                case R.id.home_music_next:
                    Utils.showToast(MainActivity.this, "Next");
                    break;
            }
        }
    }

    //从音乐播放的详情页退回后回调方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Animation tanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottombar_show);
        tanim.setFillAfter(true);
        home_musicDetail.startAnimation(tanim);
    }
}
