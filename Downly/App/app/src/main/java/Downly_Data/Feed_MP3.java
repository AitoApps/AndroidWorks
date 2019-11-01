package Downly_Data;

import android.graphics.Bitmap;

public class Feed_MP3 {
	private String title,fpath;
	public Feed_MP3() {
	}
	public Feed_MP3(String title, String fpath) {
		super();
		this.title=title;
		this.fpath=fpath;
	}
	public String get_fpath() {
		return fpath;
	}
	public void setfpath(String fpath) {
		this.fpath=fpath;
	}

	public String get_title() {
		return title;
	}
	public void settitle(String title) {
		this.title=title;
	}

}
