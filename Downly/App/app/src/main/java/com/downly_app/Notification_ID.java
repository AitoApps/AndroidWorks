package com.downly_app;

import java.util.concurrent.atomic.AtomicInteger;

public class Notification_ID {
    private static final AtomicInteger c = new AtomicInteger(0);

    public static int getID() {
        return c.incrementAndGet();
    }
}
