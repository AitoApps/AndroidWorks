package chintha_data;

public class MyChinthakalFeed {
    private String ads_show;
    private String comment_count;
    private String fvrt;
    private String imgsig;
    private String lock_comments;
    private String photodim;
    private String photourl;
    private String postdate;
    private String status;
    private String statusid;
    private String statustype;

    public MyChinthakalFeed() {
    }
    public String get_lockedcomments() {
        return lock_comments;
    }

    public void set_lockedcomments(String commentlock) {
        lock_comments = commentlock;
    }

    public String getshowads() {
        return ads_show;
    }

    public void setshowads(String showads) {
        ads_show = showads;
    }

    public String get_chinthaid() {
        return statusid;
    }

    public void set_countcomment(String cmntcount) {
        comment_count = cmntcount;
    }

    public String getpostdate() {
        return postdate;
    }

    public void setpostdate(String postdate2) {
        postdate = postdate2;
    }

    public void setstatus(String status2) {
        status = status2;
    }

    public String get_fvrt() {
        return fvrt;
    }

    public void setfvrt(String fvrt2) {
        fvrt = fvrt2;
    }

    public String getcmntcount() {
        return comment_count;
    }

    public String get_statustype() {
        return statustype;
    }

    public void setstatustype(String statustype2) {
        statustype = statustype2;
    }

    public String getphotourl() {
        return photourl;
    }

    public void setphotourl(String photourl2) {
        photourl = photourl2;
    }

    public String get_photodim() {
        return photodim;
    }

    public void set_photodim(String photodimension) {
        photodim = photodimension;
    }

    public String getimg_sig() {
        return imgsig;
    }

    public void set_imgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public void setstatusid(String statusid2) {
        statusid = statusid2;
    }

    public String getchintha() {
        return status;
    }
}
