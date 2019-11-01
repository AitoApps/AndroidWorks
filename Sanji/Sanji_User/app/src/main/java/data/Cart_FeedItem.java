package data;

public class Cart_FeedItem {
    private String imgsig;
    private String itemid;
    private String itemname;
    private String itemprice;
    private String minqty;
    private String pkey;
    private String qty;
    private String shopid;
    private String total;
    private String unittype;

    public Cart_FeedItem() {
    }

    public Cart_FeedItem(String pkey2, String shopid2, String itemid2, String itemname2, String itemprice2, String qty2, String total2, String imgsig2, String minqty2, String unittype2) {
        pkey = pkey2;
        shopid = shopid2;
        itemid = itemid2;
        itemname = itemname2;
        itemprice = itemprice2;
        qty = qty2;
        total = total2;
        imgsig = imgsig2;
        minqty = minqty2;
        unittype = unittype2;
    }

    public String getMinqty() {
        return minqty;
    }

    public void setMinqty(String minqty2) {
        minqty = minqty2;
    }

    public String getUnittype() {
        return unittype;
    }

    public void setUnittype(String unittype2) {
        unittype = unittype2;
    }

    public String getshopid() {
        return shopid;
    }

    public void setshopid(String shopid2) {
        shopid = shopid2;
    }

    public String getpkey() {
        return pkey;
    }

    public void setpkey(String pkey2) {
        pkey = pkey2;
    }

    public String getitemid() {
        return itemid;
    }

    public void setitemid(String itemid2) {
        itemid = itemid2;
    }

    public String getqty() {
        return qty;
    }

    public void setqty(String qty2) {
        qty = qty2;
    }

    public String gettotal() {
        return total;
    }

    public void settotal(String total2) {
        total = total2;
    }

    public String getitemname() {
        return itemname;
    }

    public void setitemname(String itemname2) {
        itemname = itemname2;
    }

    public String getitemprice() {
        return itemprice;
    }

    public void setitemprice(String itemprice2) {
        itemprice = itemprice2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }
}
