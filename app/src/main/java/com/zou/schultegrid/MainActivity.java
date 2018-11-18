package com.zou.schultegrid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zou.schultegrid.adapter.MainRecyclerViewGridViewAdapter;
import com.zou.schultegrid.bean.MainGridItemBean;
import com.zou.schultegrid.sound.MusicHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private static final String TAG = "MainActivity";

    @BindView(R.id.mainRvGrid)
    RecyclerView mainRvGrid;
    @BindView(R.id.mainBtStart)
    Button mainBtStart;

    private MainRecyclerViewGridViewAdapter adapter;

    private List<MainGridItemBean> gridItemBeans = new ArrayList<>();

    private long startTime;

    /**
     * 点击次数记数
     */
    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new MainRecyclerViewGridViewAdapter(gridItemBeans, this);
//        initGridData();
        adapter.setOnItemClickListener(onClickListener);
        mainRvGrid.setLayoutManager(new GridLayoutManager(this, 5));
        mainRvGrid.setAdapter(adapter);
        MusicHelper.getInstance().init();
        MusicHelper.getInstance().playGameBg();
    }

    private View.OnClickListener onClickListener = v -> {
        int position = (int) v.getTag();
        if (gridItemBeans.get(position).checkItem(clickCount)) {
            clickCount++;
            gridItemBeans.get(position).setEnable(false);
            adapter.notifyDataSetChanged();
            MusicHelper.getInstance().play(MusicHelper.MUSIC_KEY_RIGHT);
            isOver();
        } else {
            MusicHelper.getInstance().play(MusicHelper.MUSIC_KEY_ERROT);
        }
    };

    /**
     * 检查是不是完成了
     */
    private void isOver() {
        if (clickCount == gridItemBeans.size()) {
            showOverDialog();
            mainBtStart.setVisibility(View.VISIBLE);
        }
    }

    private void showOverDialog() {
        long stopTime = System.currentTimeMillis();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_game_over, null, false);
        DisplayMetrics de = getResources().getDisplayMetrics();
        int w = (int) (de.widthPixels * 0.8);
        int h = (int) (de.heightPixels * 0.5);
        view.setLayoutParams(new ViewGroup.LayoutParams(w, h));
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.addContentView(view, new ViewGroup.LayoutParams(w, h));
        dialog.show();
    }

    private void initGridData() {
        for (int i = 0; i < 25; i++) {
            gridItemBeans.add(new MainGridItemBean().setText(String.valueOf(i + 1)).setIndex(i));
        }
        refreshGridData();
    }

    private void refreshGridData() {
        clickCount = 0;
        for (MainGridItemBean bean : gridItemBeans)
            bean.setEnable(true);
        Collections.shuffle(gridItemBeans);
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.mainBtStart)
    public void onStartClick() {
        if (gridItemBeans.isEmpty()) {
            initGridData();
        } else
            refreshGridData();
        startTime = System.currentTimeMillis();
        mainBtStart.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicHelper.getInstance().releaseSound();
        MusicHelper.getInstance().stopGameBg();
        System.exit(0);
    }
}