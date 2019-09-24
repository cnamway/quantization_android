package com.spark.qtz.bean;

import android.databinding.BaseObservable;

/**
 * ================================================
 * 作    者：ccs
 * 版    本：1.0.0
 * 创建日期：2019/4/16
 * 描    述：title
 * 修订历史：
 * ================================================
 */
public class TitleBean extends BaseObservable {
    private boolean isShowLeftImg = true;//默认显示
    private boolean isShowRightImg;
    private String leftTV;
    private String rightTV;
    private boolean isShowLeftTV;
    private boolean isShowRightTV;
    private boolean isShowTitleLine;
    private String titleName;
    private String titleNameLeft;

    public TitleBean() {
    }

    public boolean isShowLeftImg() {
        return isShowLeftImg;
    }

    public void setShowLeftImg(boolean showLeftImg) {
        isShowLeftImg = showLeftImg;
    }

    public boolean isShowRightImg() {
        return isShowRightImg;
    }

    public void setShowRightImg(boolean showRightImg) {
        isShowRightImg = showRightImg;
    }

    public String getLeftTV() {
        return leftTV;
    }

    public void setLeftTV(String leftTV) {
        this.leftTV = leftTV;
    }

    public String getRightTV() {
        return rightTV;
    }

    public void setRightTV(String rightTV) {
        this.rightTV = rightTV;
    }

    public boolean isShowLeftTV() {
        return isShowLeftTV;
    }

    public void setShowLeftTV(boolean showLeftTV) {
        isShowLeftTV = showLeftTV;
    }

    public boolean isShowRightTV() {
        return isShowRightTV;
    }

    public void setShowRightTV(boolean showRightTV) {
        isShowRightTV = showRightTV;
    }

    public boolean isShowTitleLine() {
        return isShowTitleLine;
    }

    public void setShowTitleLine(boolean showTitleLine) {
        isShowTitleLine = showTitleLine;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getTitleNameLeft() {
        return titleNameLeft;
    }

    public void setTitleNameLeft(String titleNameLeft) {
        this.titleNameLeft = titleNameLeft;
    }
}
