package data;

public class ApprovelProduct_FeedItem {
    private String imgsig1;
    private String itemname;
    private String minqty;
    private String offerprice;
    private String orginalprice;
    private String shopid;
    private String shopmobile;
    private String shopname;
    private String sn;
    private String unit;

    public ApprovelProduct_FeedItem() {
    }

    public ApprovelProduct_FeedItem(String sn2, String itemname2, String offerprice2, String orginalprice2, String imgsig12, String shopid2, String shopname2, String shopmobile2, String minqty2, String unit2) {
        sn = sn2;
        itemname = itemname2;
        offerprice = offerprice2;
        orginalprice = orginalprice2;
        imgsig1 = imgsig12;
        shopid = shopid2;
        shopname = shopname2;
        shopmobile = shopmobile2;
        minqty = minqty2;
        unit = unit2;
    }

    public String getMinqty() {
        return minqty;
    }

    public void setMinqty(String minqty2) {
        minqty = minqty2;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit2) {
        unit = unit2;
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

    public String getitemname() {
        return itemname;
    }

    public void setitemname(String itemname2) {
        itemname = itemname2;
    }

    public String getofferprice() {
        return offerprice;
    }

    public void setofferprice(String offerprice2) {
        offerprice = offerprice2;
    }

    public String getorginalprice() {
        return orginalprice;
    }

    public void setorginalprice(String orginalprice2) {
        orginalprice = orginalprice2;
    }

    public String getimgsig1() {
        return imgsig1;
    }

    public void setimgsig1(String imgsig12) {
        imgsig1 = imgsig12;
    }
}
