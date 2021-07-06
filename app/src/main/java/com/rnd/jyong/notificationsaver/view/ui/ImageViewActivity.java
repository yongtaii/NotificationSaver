package com.rnd.jyong.notificationsaver.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rnd.jyong.notificationsaver.R;
import com.rnd.jyong.notificationsaver.data.model.NotiMessage;
import com.rnd.jyong.notificationsaver.utils.CommonUtil;

public class ImageViewActivity extends AppCompatActivity {

    public static final String EXTRA_NOTIMSG = "EXTRA_NOTIMSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            // hide status bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            // hide action bar
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_image_view);

        ImageView ivMain = findViewById(R.id.ivMain);
        LinearLayout infoLayout = findViewById(R.id.infoLayout);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvTime = findViewById(R.id.tvTime);

        NotiMessage notiMessage = (NotiMessage) getIntent().getSerializableExtra(EXTRA_NOTIMSG);

        if(notiMessage == null) return;

        tvName.setText(notiMessage.name);
        tvTime.setText(CommonUtil.convertTimeToSimpleTime(notiMessage.time));

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(notiMessage.image)
                .into(ivMain);


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
    }
}