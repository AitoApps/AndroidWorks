package data;

import android.graphics.Bitmap;

import ja.burhanrashid52.photoeditor.PhotoFilter;

public class Filters_FeedItem {
    private PhotoFilter styleid;
    private String stylename="";
    private int drawid;

    public int getDrawid() {
        return drawid;
    }

    public void setDrawid(int drawid) {
        this.drawid = drawid;
    }

    public PhotoFilter getStyleid() {
        return styleid;
    }

    public void setStyleid(PhotoFilter styleid) {
        this.styleid = styleid;
    }

    public String getStylename() {
        return stylename;
    }

    public void setStylename(String stylename) {
        this.stylename = stylename;
    }
}
