package com.payumoney.core;

import com.payu.custombrowser.bean.ReviewOrderBundle;
import com.payumoney.core.listener.ReviewOrderImpl;

public class PayUmoneyConfig {
    private static PayUmoneyConfig instance = null;
    private String accentColor = PayUmoneyConstants.accentColor;
    private String colorPrimary = PayUmoneyConstants.colorPrimary;
    private String colorPrimaryDark = PayUmoneyConstants.colorPrimaryDark;
    private boolean disableExitConfirmation = false;
    private String doneButtonText;
    private boolean enableReviewOrder = false;
    private String payUmoneyActivityTitle = null;
    private ReviewOrderBundle reviewOrderBundle;
    private ReviewOrderImpl reviewOrderImpl;
    private String reviewOrderMenuText;
    private int reviewOrderToolbarBgColor = -1;
    private int reviewOrderToolbarTextColor = -1;
    private String textColorPrimary = PayUmoneyConstants.textColor;

    public boolean isExitConfirmationDisabled() {
        return this.disableExitConfirmation;
    }

    public void disableExitConfirmation(boolean disableExitConfirmation2) {
        this.disableExitConfirmation = disableExitConfirmation2;
    }

    private PayUmoneyConfig() {
    }

    public String getDoneButtonText() {
        return this.doneButtonText;
    }

    public void setDoneButtonText(String doneButtonText2) {
        this.doneButtonText = doneButtonText2;
    }

    public static PayUmoneyConfig getInstance() {
        if (instance == null) {
            synchronized (PayUmoneyConfig.class) {
                if (instance == null) {
                    instance = new PayUmoneyConfig();
                }
            }
        }
        return instance;
    }

    public String getColorPrimaryDark() {
        return this.colorPrimaryDark;
    }

    public synchronized void setColorPrimaryDark(String colorPrimaryDark2) {
        this.colorPrimaryDark = colorPrimaryDark2;
    }

    public String getColorPrimary() {
        return this.colorPrimary;
    }

    public synchronized void setColorPrimary(String colorPrimary2) {
        this.colorPrimary = colorPrimary2;
    }

    public String getTextColorPrimary() {
        return this.textColorPrimary;
    }

    public synchronized void setTextColorPrimary(String textColorPrimary2) {
        this.textColorPrimary = textColorPrimary2;
    }

    public String getAccentColor() {
        return this.accentColor;
    }

    public synchronized void setAccentColor(String accentColor2) {
        this.accentColor = accentColor2;
    }

    public String getPayUmoneyActivityTitle() {
        return this.payUmoneyActivityTitle;
    }

    public void setPayUmoneyActivityTitle(String payUmoneyActivityTitle2) {
        this.payUmoneyActivityTitle = payUmoneyActivityTitle2;
    }

    public void setEnableReviewOrder(boolean enable) {
        this.enableReviewOrder = enable;
    }

    public boolean isEnableReviewOrder() {
        return this.enableReviewOrder;
    }

    public ReviewOrderBundle getReviewOrderBundle() {
        return this.reviewOrderBundle;
    }

    public void setReviewOrderBundle(ReviewOrderBundle reviewOrderBundle2) {
        this.reviewOrderBundle = reviewOrderBundle2;
    }

    public ReviewOrderImpl getReviewOrderImpl() {
        return this.reviewOrderImpl;
    }

    public void setReviewOrderImpl(ReviewOrderImpl reviewOrderImpl2) {
        this.reviewOrderImpl = reviewOrderImpl2;
    }

    public String getReviewOrderMenuText() {
        return this.reviewOrderMenuText;
    }

    public void setReviewOrderMenuText(String reviewOrderMenuText2) {
        this.reviewOrderMenuText = reviewOrderMenuText2;
    }

    public int getReviewOrderToolbarBgColor() {
        return this.reviewOrderToolbarBgColor;
    }

    public void setReviewOrderToolbarBgColor(int reviewOrderToolbarBgColor2) {
        this.reviewOrderToolbarBgColor = reviewOrderToolbarBgColor2;
    }

    public int getReviewOrderToolbarTextColor() {
        return this.reviewOrderToolbarTextColor;
    }

    public void setReviewOrderToolbarTextColor(int reviewOrderToolbarTextColor2) {
        this.reviewOrderToolbarTextColor = reviewOrderToolbarTextColor2;
    }
}
