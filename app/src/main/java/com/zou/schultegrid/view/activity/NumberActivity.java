package com.zou.schultegrid.view.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.zou.schultegrid.R;
import com.zou.schultegrid.common.NumberHelperKt;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NumberActivity extends AppCompatActivity {

    private static final String TAG = "NumberActivity";

    @BindView(R.id.acNumberNumberPicker)
    NumberPicker acNumberNumberPicker;
    @BindView(R.id.acNumberShowNumber)
    TextView acNumberShowNumber;
    @BindView(R.id.acNumberStart)
    Button acNumberStart;

    private int numberLen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        ButterKnife.bind(this);
        acNumberNumberPicker.setMaxValue(15);
        acNumberNumberPicker.setMinValue(6);
        acNumberNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberLen = acNumberNumberPicker.getMinValue();
        acNumberNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            Log.d(TAG, "onCreate: " + oldVal + " " + newVal);
            numberLen = newVal;
        });
    }

    @OnClick(R.id.acNumberStart)
    public void startClick() {
        if (disposable == null) {
            startShowNumber();
            acNumberStart.setText("Stop");
        } else {
            disposable.dispose();
            disposable = null;
            acNumberStart.setText("Start");
        }
    }

    private Disposable disposable;

    private void startShowNumber() {
        Log.d(TAG, "startShowNumber: " + numberLen);
        disposable = Flowable.interval(10, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(aLong -> acNumberShowNumber.setText(NumberHelperKt.getRandomString(numberLen)));
    }
}