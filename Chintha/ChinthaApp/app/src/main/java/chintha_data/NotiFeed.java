package chintha_data;

public class NotiFeed {
    private String count;
    private String datecreated;
    private String imgsig;
    private String isread;
    private String lastname;
    private String ogid;
    private String photodimension;
    private String photourl;
    private String pkey;
    private String status_userid;
    private String status_username;
    private String statustype;
    private String text;
    private String type;
    private String userid;

    public NotiFeed() {
    }
    public String get_ogId() {
        return ogid;
    }

    public void set_ogId(String ogid2) {
        ogid = ogid2;
    }

    public String getcount() {
        return count;
    }

    public void setcount(String count2) {
        count = count2;
    }

    public String getlastname() {
        return lastname;
    }

    public void setlastname(String lastname2) {
        lastname = lastname2;
    }

    public String gettext() {
        return text;
    }

    public void settext(String text2) {
        text = text2;
    }

    public String get_createddate() {
        return datecreated;
    }

    public void set_createddate(String datecreated2) {
        datecreated = datecreated2;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid2) {
        userid = userid2;
    }

    public String getpkey() {
        return pkey;
    }

    public void setpkey(String pkey2) {
        pkey = pkey2;
    }

    public String get_chinthauserid() {
        return status_userid;
    }

    public void set_chinthauserid(String status_userid2) {
        status_userid = status_userid2;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type2) {
        type = type2;
    }

    public String get_chintha_username() {
        return status_username;
    }

    public void set_chintha_username(String status_username2) {
        status_username = status_username2;
    }

    public String get_imgsig() {
        return imgsig;
    }

    public void set_imgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getstatustype() {
        return statustype;
    }

    public void setstatustype(String statustype2) {
        statustype = statustype2;
    }

    public String get_picurl() {
        return photourl;
    }

    public void set_picurl(String photourl2) {
        photourl = photourl2;
    }

    public String get_isread() {
        return isread;
    }

    public void set_isread(String isread2) {
        isread = isread2;
    }

    public String get_photodim() {
        return photodimension;
    }

    public void set_photodim(String photodimension2) {
        photodimension = photodimension2;
    }
}
