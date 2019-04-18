package com.zou.schultegrid.view.activity;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zou.schultegrid.R;
import com.zou.schultegrid.adapter.MainRecyclerViewGridViewAdapter;
import com.zou.schultegrid.bean.MainGridItemBean;
import com.zou.schultegrid.sound.MusicHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SchulteActivity extends AppCompatActivity {

    private static final String TAG = "SchulteActivity";

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
        setContentView(R.layout.activity_schulte);
        ButterKnife.bind(this);
        adapter = new MainRecyclerViewGridViewAdapter(gridItemBeans, this);
        adapter.setOnItemClickListener(onClickListener);
        mainRvGrid.setLayoutManager(new GridLayoutManager(this, 5));
        mainRvGrid.setAdapter(adapter);
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
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_game_over, null, false);
        TextView textView = view.findViewById(R.id.dialogGameOverTime);
        view.findViewById(R.id.mainBtStart).setOnClickListener(v -> {
            startGame();
            dialog.dismiss();
        });
        DisplayMetrics de = getResources().getDisplayMetrics();
        int w = (int) (de.widthPixels * 0.8);
        int h = (int) (de.heightPixels * 0.5);
        view.setLayoutParams(new ViewGroup.LayoutParams(w, h));
        dialog.addContentView(view, new ViewGroup.LayoutParams(w, h));
        dialog.show();
        showTimeAnimation(textView, (int) ((stopTime - startTime) / 1000));
    }

    private void showTimeAnimation(TextView textView, int param) {
        ValueAnimator animator = ValueAnimator.ofInt(param);
        animator.setDuration(2000);
//        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            Log.d(TAG, "showTimeAnimation: " + value);
            textView.setText(String.valueOf(value));
        });
        animator.start();
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
        }
        startGame();
    }

    private void startGame() {
        refreshGridData();
        startTime = System.currentTimeMillis();
        mainBtStart.setVisibility(View.GONE);
    }

}
