package com.zou.schultegrid;

import android.app.Application;

import org.xutils.x;

/**
 * ************************************************************
 * description:  <br>
 * packageName:com.zou.schultegrid mode:SchulteGrid <br>
 * created by 邹高原 on 2018/11/12 09:53<br>
 * last modified time：2018/11/12 09:53 <br>
 *
 * @author 邹高原
 * ************************************************************
 */
public class SchulteApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
