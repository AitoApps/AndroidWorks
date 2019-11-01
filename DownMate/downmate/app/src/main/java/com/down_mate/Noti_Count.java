package com.down_mate;

import java.util.concurrent.atomic.AtomicInteger;

public class Noti_Count {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
