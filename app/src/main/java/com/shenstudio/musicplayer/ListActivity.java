package com.shenstudio.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ListActivity extends BaseActivity {
    private TextView list_name;
    private ImageView back;


    void resetTitle() {
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        list_name.setText(type);
    }

    void init() {
        list_name = (TextView) findViewById(R.id.list_name);
        back = (ImageView) findViewById(R.id.list_back);

        back.setOnClickListener(new ClickLIstener());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        init();

        resetTitle();
    }


    private class ClickLIstener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.list_back:
                    finish();
                    break;
            }
        }
    }
}
