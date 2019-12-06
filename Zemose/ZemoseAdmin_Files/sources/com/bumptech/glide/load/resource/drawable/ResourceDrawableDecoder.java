package com.bumptech.glide.load.resource.drawable;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import java.util.List;

public class ResourceDrawableDecoder implements ResourceDecoder<Uri, Drawable> {
    private static final int ID_PATH_SEGMENTS = 1;
    private static final int NAME_PATH_SEGMENT_INDEX = 1;
    private static final int NAME_URI_PATH_SEGMENTS = 2;
    private static final int RESOURCE_ID_SEGMENT_INDEX = 0;
    private static final int TYPE_PATH_SEGMENT_INDEX = 0;
    private final Context context;

    public ResourceDrawableDecoder(Context context2) {
        this.context = context2.getApplicationContext();
    }

    public boolean handles(@NonNull Uri source, @NonNull Options options) {
        return source.getScheme().equals("android.resource");
    }

    @Nullable
    public Resource<Drawable> decode(@NonNull Uri source, int width, int height, @NonNull Options options) {
        int resId = loadResourceIdFromUri(source);
        String packageName = source.getAuthority();
        return NonOwnedDrawableResource.newInstance(DrawableDecoderCompat.getDrawable(this.context, packageName.equals(this.context.getPackageName()) ? this.context : getContextForPackage(source, packageName), resId));
    }

    @NonNull
    private Context getContextForPackage(Uri source, String packageName) {
        try {
            return this.context.createPackageContext(packageName, 0);
        } catch (NameNotFoundException e) {
            StringBuilder sb = new StringBuilder();
            sb.append("Failed to obtain context or unrecognized Uri format for: ");
            sb.append(source);
            throw new IllegalArgumentException(sb.toString(), e);
        }
    }

    @DrawableRes
    private int loadResourceIdFromUri(Uri source) {
        List<String> segments = source.getPathSegments();
        Integer result = null;
        if (segments.size() == 2) {
            String typeName = (String) segments.get(0);
            String resourceName = (String) segments.get(1);
            result = Integer.valueOf(this.context.getResources().getIdentifier(resourceName, typeName, source.getAuthority()));
        } else if (segments.size() == 1) {
            try {
                result = Integer.valueOf((String) segments.get(0));
            } catch (NumberFormatException e) {
            }
        }
        if (result == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unrecognized Uri format: ");
            sb.append(source);
            throw new IllegalArgumentException(sb.toString());
        } else if (result.intValue() != 0) {
            return result.intValue();
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append("Failed to obtain resource id for: ");
            sb2.append(source);
            throw new IllegalArgumentException(sb2.toString());
        }
    }
}
