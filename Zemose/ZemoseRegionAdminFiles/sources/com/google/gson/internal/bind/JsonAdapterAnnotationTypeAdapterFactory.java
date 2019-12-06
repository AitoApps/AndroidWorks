package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor2) {
        this.constructorConstructor = constructorConstructor2;
    }

    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> targetType) {
        JsonAdapter annotation = (JsonAdapter) targetType.getRawType().getAnnotation(JsonAdapter.class);
        if (annotation == null) {
            return null;
        }
        return getTypeAdapter(this.constructorConstructor, gson, targetType, annotation);
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [com.google.gson.TypeAdapter, com.google.gson.TypeAdapter<?>] */
    /* JADX WARNING: type inference failed for: r1v14, types: [com.google.gson.TypeAdapter] */
    /* JADX WARNING: type inference failed for: r1v16, types: [com.google.gson.TypeAdapter] */
    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 2 */
    public TypeAdapter<?> getTypeAdapter(ConstructorConstructor constructorConstructor2, Gson gson, TypeToken<?> type, JsonAdapter annotation) {
        TreeTypeAdapter treeTypeAdapter;
        Object instance = constructorConstructor2.get(TypeToken.get(annotation.value())).construct();
        if (instance instanceof TypeAdapter) {
            treeTypeAdapter = (TypeAdapter) instance;
        } else if (instance instanceof TypeAdapterFactory) {
            treeTypeAdapter = ((TypeAdapterFactory) instance).create(gson, type);
        } else if ((instance instanceof JsonSerializer) || (instance instanceof JsonDeserializer)) {
            JsonDeserializer jsonDeserializer = null;
            JsonSerializer jsonSerializer = instance instanceof JsonSerializer ? (JsonSerializer) instance : null;
            if (instance instanceof JsonDeserializer) {
                jsonDeserializer = (JsonDeserializer) instance;
            }
            TreeTypeAdapter treeTypeAdapter2 = new TreeTypeAdapter(jsonSerializer, jsonDeserializer, gson, type, null);
            treeTypeAdapter = treeTypeAdapter2;
        } else {
            throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer reference.");
        }
        if (treeTypeAdapter == 0 || !annotation.nullSafe()) {
            return treeTypeAdapter;
        }
        return treeTypeAdapter.nullSafe();
    }
}
