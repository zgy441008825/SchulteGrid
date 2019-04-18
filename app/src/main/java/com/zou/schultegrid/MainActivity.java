package com.zou.schultegrid;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.zou.schultegrid.sound.MusicHelper;
import com.zou.schultegrid.view.activity.NumberActivity;
import com.zou.schultegrid.view.activity.SchulteActivity;

/**
 * ************************************************************
 * 文件：MainActivity 包名:com.zou.schultegrid 项目:舒尔特方格 <br>
 * 创建时间：11/12 0012 9:30 <br>
 * 上次修改时间：11/12 0012 9:30 <br>
 * 作者：邹高原<br>
 * Copyright (c) 11/12 0012 9:30 <br>
 * 描述:  <br>
 * ************************************************************
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicHelper.getInstance().init();
        MusicHelper.getInstance().playGameBg();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicHelper.getInstance().releaseSound();
        MusicHelper.getInstance().stopGameBg();
        System.exit(0);
    }

    public void onSchulteOnClick(View view) {
        startActivity(new Intent(this, SchulteActivity.class));
    }

    public void startNumberActivity(View view) {
        startActivity(new Intent(this, NumberActivity.class));
    }

}