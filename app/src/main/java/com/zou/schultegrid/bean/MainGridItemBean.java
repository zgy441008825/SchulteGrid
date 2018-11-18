package com.zou.schultegrid.bean;

/**
 * ************************************************************
 * description:  <br>
 * packageName:com.zou.schultegrid.bean mode:SchulteGrid <br>
 * created by 邹高原 on 2018/11/12 10:47<br>
 * last modified time：2018/11/12 10:47 <br>
 *
 * @author 邹高原
 * ************************************************************
 */
public class MainGridItemBean {

    private int index;

    private int icon;

    private boolean isEnable;

    private String text;

    public int getIndex() {
        return index;
    }

    public MainGridItemBean setIndex(int index) {
        this.index = index;
        return this;
    }

    public int getIcon() {
        return icon;
    }

    public MainGridItemBean setIcon(int icon) {
        this.icon = icon;
        return this;
    }

    public String getText() {
        return text;
    }

    public MainGridItemBean setText(String text) {
        this.text = text;
        return this;
    }

    public boolean checkItem(Integer clickCount) {
        return clickCount == index;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public MainGridItemBean setEnable(boolean enable) {
        isEnable = enable;
        return this;
    }
}
