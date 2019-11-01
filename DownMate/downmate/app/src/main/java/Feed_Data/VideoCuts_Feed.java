package Feed_Data;

import android.graphics.Bitmap;

public class VideoCuts_Feed {

	private String title,fpath,fileduration;
	private Bitmap bmp;
	public VideoCuts_Feed() {
	}

	public VideoCuts_Feed(Bitmap bmp, String fileduration,String title, String fpath) {
		super();
		this.title=title;
		this.bmp=bmp;
		this.fpath=fpath;
		this.fileduration=fileduration;
	}

	public void setfileduration(String fileduration) {
		this.fileduration=fileduration;
	}
	public String getfileduration() {
		return fileduration;
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

	public Bitmap getbmp() {
		return bmp;
	}
	public void setbmp(Bitmap bmp) {
		this.bmp=bmp;
	}

}
