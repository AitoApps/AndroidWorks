package data;

public class Order_List_FeedItem {
    private String fishid;
    private String fishname;
    private String imgsig;
    private String price;
    private String qty;
    private String regdate;
    private String sn;
    private String userid;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price2) {
        price = price2;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn2) {
        sn = sn2;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid2) {
        userid = userid2;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate2) {
        regdate = regdate2;
    }

    public String getFishid() {
        return fishid;
    }

    public void setFishid(String fishid2) {
        fishid = fishid2;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty2) {
        qty = qty2;
    }

    public String getFishname() {
        return fishname;
    }

    public void setFishname(String fishname2) {
        fishname = fishname2;
    }

    public String getImgsig() {
        return imgsig;
    }

    public void setImgsig(String imgsig2) {
        imgsig = imgsig2;
    }
}
