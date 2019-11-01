package Feed_Data;

import android.graphics.Bitmap;

public class MP3_Files {
	private String title,fpath;
	public MP3_Files() {
	}
	public MP3_Files(String title, String fpath) {
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
