package data;

public class StatusMedia_Feeditem {
    private String mediatype;
    private String path;
    private String regdate;
    private String sn;
    private String title;
    private String userid;

    public String getSn() {
        return sn;
    }

    public void setSn(String sn2) {
        sn = sn2;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate2) {
        regdate = regdate2;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid2) {
        userid = userid2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title2) {
        title = title2;
    }

    public String getMediatype() {
        return mediatype;
    }

    public void setMediatype(String mediatype2) {
        mediatype = mediatype2;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path2) {
        path = path2;
    }
}
