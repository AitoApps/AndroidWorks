package com.payu.custombrowser.c;

import com.payu.custombrowser.d.b;
import com.payu.custombrowser.d.c;
import com.payu.custombrowser.util.PaymentOption;

public class a {
    private PaymentOption a;
    private com.payu.custombrowser.d.a b;

    public a(PaymentOption paymentOption) {
        this.a = paymentOption;
    }

    public com.payu.custombrowser.d.a a() {
        switch (this.a) {
            case PHONEPE:
                this.b = b();
                break;
            case SAMSUNGPAY:
                this.b = c();
                break;
        }
        return this.b;
    }

    private com.payu.custombrowser.d.a b() {
        return new b();
    }

    private com.payu.custombrowser.d.a c() {
        return new c();
    }
}
