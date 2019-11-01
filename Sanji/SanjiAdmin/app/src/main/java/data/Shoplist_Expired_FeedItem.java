package data;

public class Shoplist_Expired_FeedItem {
    private String expireddt;
    private String imgsig;
    private String latitude;
    private String longtitude;
    private String mobile1;
    private String mobile2;
    private String ownername;
    private String place;
    private String shopname;
    private String sn;
    private String status;

    public Shoplist_Expired_FeedItem() {
    }

    public Shoplist_Expired_FeedItem(String sn2, String shopname2, String ownername2, String mobile12, String mobile22, String place2, String latitude2, String longtitude2, String imgsig2, String status2, String expireddt2) {
        sn = sn2;
        shopname = shopname2;
        ownername = ownername2;
        mobile1 = mobile12;
        mobile2 = mobile22;
        place = place2;
        latitude = latitude2;
        longtitude = longtitude2;
        imgsig = imgsig2;
        status = status2;
        expireddt = expireddt2;
    }

    public String getexpireddt() {
        return expireddt;
    }

    public void setexpireddt(String expireddt2) {
        expireddt = expireddt2;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status2) {
        status = status2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getshopname() {
        return shopname;
    }

    public void setshopname(String shopname2) {
        shopname = shopname2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getownername() {
        return ownername;
    }

    public void setownername(String ownername2) {
        ownername = ownername2;
    }

    public String getmobile1() {
        return mobile1;
    }

    public void setmobile1(String mobile12) {
        mobile1 = mobile12;
    }

    public String getmobile2() {
        return mobile2;
    }

    public void setmobile2(String mobile22) {
        mobile2 = mobile22;
    }

    public String getplace() {
        return place;
    }

    public void setplace(String place2) {
        place = place2;
    }

    public String getlatitude() {
        return latitude;
    }

    public void setlatitude(String latitude2) {
        latitude = latitude2;
    }

    public String getlongtitude() {
        return longtitude;
    }

    public void setlongtitude(String longtitude2) {
        longtitude = longtitude2;
    }
}
