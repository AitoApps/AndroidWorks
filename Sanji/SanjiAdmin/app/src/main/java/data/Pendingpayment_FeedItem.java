package data;

public class Pendingpayment_FeedItem {
    private String amount;
    private String shopid;
    private String shopmobile;
    private String shopname;
    private String sn;

    public Pendingpayment_FeedItem() {
    }

    public Pendingpayment_FeedItem(String sn2, String shopid2, String shopname2, String shopmobile2, String amount2) {
        sn = sn2;
        shopid = shopid2;
        shopname = shopname2;
        shopmobile = shopmobile2;
        amount = amount2;
    }

    public String getshopmobile() {
        return shopmobile;
    }

    public void setshopmobile(String shopmobile2) {
        shopmobile = shopmobile2;
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

    public String getshopname() {
        return shopname;
    }

    public void setshopname(String shopname2) {
        shopname = shopname2;
    }

    public String getamount() {
        return amount;
    }

    public void setamount(String amount2) {
        amount = amount2;
    }
}
