package com.payumoney.core.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
    public static JSONObject mapToJson(Map<?, ?> data2) {
        JSONObject jSONObject = new JSONObject();
        for (Entry entry : data2.entrySet()) {
            String str = (String) entry.getKey();
            if (str != null) {
                try {
                    jSONObject.put(str, a(entry.getValue()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                throw new NullPointerException("key == null");
            }
        }
        return jSONObject;
    }

    /* JADX WARNING: Incorrect type for immutable var: ssa=java.util.Collection, code=java.util.Collection<java.lang.Object>, for r3v0, types: [java.util.Collection<java.lang.Object>, java.util.Collection] */
    public static JSONArray collectionToJson(Collection<Object> data2) {
        JSONArray jSONArray = new JSONArray();
        if (data2 != null) {
            for (Object a : data2) {
                jSONArray.put(a(a));
            }
        }
        return jSONArray;
    }

    public static JSONArray arrayToJson(Object data2) throws JSONException {
        if (data2.getClass().isArray()) {
            int length = Array.getLength(data2);
            JSONArray jSONArray = new JSONArray();
            for (int i = 0; i < length; i++) {
                jSONArray.put(a(Array.get(data2, i)));
            }
            return jSONArray;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Not a primitive data: ");
        sb.append(data2.getClass());
        throw new JSONException(sb.toString());
    }

    private static Object a(Object obj) {
        if (obj == null) {
            return null;
        }
        if ((obj instanceof JSONArray) || (obj instanceof JSONObject)) {
            return obj;
        }
        try {
            if (obj instanceof Collection) {
                return collectionToJson((Collection) obj);
            }
            if (obj.getClass().isArray()) {
                return arrayToJson(obj);
            }
            if (obj instanceof Map) {
                return mapToJson((Map) obj);
            }
            if (!(obj instanceof Boolean) && !(obj instanceof Byte) && !(obj instanceof Character) && !(obj instanceof Double) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Long) && !(obj instanceof Short)) {
                if (!(obj instanceof String)) {
                    if (obj.getClass().getPackage().getName().startsWith("java.")) {
                        return obj.toString();
                    }
                    return null;
                }
            }
            return obj;
        } catch (Exception e) {
        }
    }
}
