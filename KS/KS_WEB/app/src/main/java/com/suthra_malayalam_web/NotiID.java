package com.suthra_malayalam_web;

import java.util.concurrent.atomic.AtomicInteger;

public class NotiID {
    private static final AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }
}
