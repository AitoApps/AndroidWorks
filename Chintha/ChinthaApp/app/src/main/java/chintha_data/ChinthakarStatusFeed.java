package chintha_data;

public class ChinthakarStatusFeed {
    private String chintha_id;
    private String cmntcount;
    private String commentlock;
    private String fvrt;
    private String imgsig;
    private String photodim;
    private String photourl;
    private String postdate;
    private String showads;
    private String status;
    private String statustype;

    public ChinthakarStatusFeed() {
    }

    public String get_iscomment() {
        return commentlock;
    }

    public void set_iscommentlock(String commentlock2) {
        commentlock = commentlock2;
    }

    public String getshowads() {
        return showads;
    }

    public void setshowads(String showads2) {
        showads = showads2;
    }

    public String get_likes() {
        return fvrt;
    }

    public void set_likes(String fvrt2) {
        fvrt = fvrt2;
    }

    public String getcmntcount() {
        return cmntcount;
    }

    public void setcmntcount(String cmntcount2) {
        cmntcount = cmntcount2;
    }

    public String getstatustype() {
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

    public String getphotodimension() {
        return photodim;
    }

    public String get_imgsig() {
        return imgsig;
    }

    public void set_imgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public void set_photodim(String photodimension) {
        photodim = photodimension;
    }

    public String get_chinthaid() {
        return chintha_id;
    }

    public void setstatusid(String statusid) {
        chintha_id = statusid;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status2) {
        status = status2;
    }

    public String getpostdate() {
        return postdate;
    }

    public void setpostdate(String postdate2) {
        postdate = postdate2;
    }
}
