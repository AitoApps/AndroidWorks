package data;

public class Salesmanlist_FeedItem {
    private String address;
    private String idcard;
    private String imgsig;
    private String mobile;
    private String name;
    private String place;
    private String shopid;
    private String sn;
    private String status;

    public Salesmanlist_FeedItem() {
    }

    public Salesmanlist_FeedItem(String sn2, String shopid2, String mobile2, String place2, String name2, String idcard2, String address2, String imgsig2, String status2) {
        sn = sn2;
        shopid = shopid2;
        name = name2;
        mobile = mobile2;
        place = place2;
        idcard = idcard2;
        address = address2;
        imgsig = imgsig2;
        status = status2;
    }

    public String getidcard() {
        return idcard;
    }

    public void setidcard(String idcard2) {
        idcard = idcard2;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address2) {
        address = address2;
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

    public String getshopid() {
        return shopid;
    }

    public void setshopid(String shopid2) {
        shopid = shopid2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getname() {
        return name;
    }

    public void setname(String name2) {
        name = name2;
    }

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile2) {
        mobile = mobile2;
    }

    public String getplace() {
        return place;
    }

    public void setplace(String place2) {
        place = place2;
    }
}
