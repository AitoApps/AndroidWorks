package data;
public class Feed_KitView {
	private String kitname,imgurl;
	public Feed_KitView() {
	}
	public Feed_KitView(String kitname, String imgurl) {
		super();
		this.kitname =kitname;
		this.imgurl=imgurl;
	}

	public String getkitname() {
		return kitname;
	}
	public void setkitname(String kitname) {
		this.kitname =kitname;
	}

	public String getimgurl() {
		return imgurl;
	}
	public void setimgurl(String imgurl) {
		this.imgurl=imgurl;
	}
}
