package com.payu.custombrowser.bean;

import java.util.ArrayList;

public class ReviewOrderBundle {
    ArrayList<ReviewOrderData> a = new ArrayList<>();

    public ArrayList<ReviewOrderData> getReviewOrderDatas() {
        return this.a;
    }

    public void addOrderDetails(String key, String value) {
        this.a.add(new ReviewOrderData(key, value));
    }
}
