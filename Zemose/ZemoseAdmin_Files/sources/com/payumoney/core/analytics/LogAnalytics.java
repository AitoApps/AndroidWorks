package com.payumoney.core.analytics;

import android.content.Context;
import com.payumoney.core.utils.AnalyticsConstant;
import java.util.HashMap;

public class LogAnalytics {
    private LogAnalytics() {
    }

    public static void logEvent(Context context, String eventName, HashMap<String, Object> eventData, String type) {
        if (type.equalsIgnoreCase(AnalyticsConstant.CLEVERTAP)) {
            AnalyticsDataManager.getInstance(context, AnalyticsConstant.ClEVERTAP_FILE).logEvent(eventName, eventData);
        }
    }

    public static void pushAllPendingEvents(Context context, String type) {
        if (type.equalsIgnoreCase(AnalyticsConstant.CLEVERTAP)) {
            AnalyticsDataManager.getInstance(context, AnalyticsConstant.ClEVERTAP_FILE).pushAllPendingEvents();
        }
    }
}
