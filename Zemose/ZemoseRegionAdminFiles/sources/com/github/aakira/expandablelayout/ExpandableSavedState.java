package com.github.aakira.expandablelayout;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View.BaseSavedState;

public class ExpandableSavedState extends BaseSavedState {
    public static final Creator<ExpandableSavedState> CREATOR = new Creator<ExpandableSavedState>() {
        public ExpandableSavedState createFromParcel(Parcel in) {
            return new ExpandableSavedState(in);
        }

        public ExpandableSavedState[] newArray(int size) {
            return new ExpandableSavedState[size];
        }
    };
    private int size;
    private float weight;

    ExpandableSavedState(Parcelable superState) {
        super(superState);
    }

    private ExpandableSavedState(Parcel in) {
        super(in);
        this.size = in.readInt();
        this.weight = in.readFloat();
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size2) {
        this.size = size2;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight2) {
        this.weight = weight2;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(this.size);
        out.writeFloat(this.weight);
    }
}
