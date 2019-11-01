package data;

public class Franchisis_FeedItem {
    private String address;
    private String agreid;
    private String area;
    private String mobile;
    private String name;
    private String sn;

    public Franchisis_FeedItem() {
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn2) {
        sn = sn2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name2) {
        name = name2;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area2) {
        area = area2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile2) {
        mobile = mobile2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address2) {
        address = address2;
    }

    public String getAgreid() {
        return agreid;
    }

    public void setAgreid(String agreid2) {
        agreid = agreid2;
    }

    public Franchisis_FeedItem(String sn2, String name2, String area2, String mobile2, String address2, String agreid2) {
        sn = sn2;
        name = name2;
        area = area2;
        mobile = mobile2;
        address = address2;
        agreid = agreid2;
    }
}
