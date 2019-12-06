package com.payumoney.sdkui.ui.utils;

public class PPLogger {
    private static PPLogger a;
    private boolean b = true;

    private PPLogger() {
    }

    public static PPLogger getInstance() {
        if (a == null) {
            synchronized (PPLogger.class) {
                if (a == null) {
                    a = new PPLogger();
                }
            }
        }
        return a;
    }

    public boolean isEnableLogs() {
        return this.b;
    }

    public void d(String message, Object... args) {
        boolean z = this.b;
    }

    public void w(String message, Object... args) {
        boolean z = this.b;
    }

    public void i(String message, Object... args) {
        boolean z = this.b;
    }

    public void json(String jsonString) {
        boolean z = this.b;
    }

    public void e(String message, Object... args) {
        boolean z = this.b;
    }

    public void e(String message, Exception e) {
        boolean z = this.b;
    }

    public void v(String message, Object... args) {
        boolean z = this.b;
    }
}
