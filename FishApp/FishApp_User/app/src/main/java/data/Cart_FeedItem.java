package data;

public class Cart_FeedItem {
    private String fishid;
    private String fishname;
    private String imgsig;
    private String ogprice;
    private String ogqty;
    private String ogunit;
    private String pkey;
    private String qty;
    private String totalprice;

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey2) {
        pkey = pkey2;
    }

    public String getFishid() {
        return fishid;
    }

    public void setFishid(String fishid2) {
        fishid = fishid2;
    }

    public String getFishname() {
        return fishname;
    }

    public void setFishname(String fishname2) {
        fishname = fishname2;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty2) {
        qty = qty2;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice2) {
        totalprice = totalprice2;
    }

    public String getImgsig() {
        return imgsig;
    }

    public void setImgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getOgunit() {
        return ogunit;
    }

    public void setOgunit(String ogunit2) {
        ogunit = ogunit2;
    }

    public String getOgqty() {
        return ogqty;
    }

    public void setOgqty(String ogqty2) {
        ogqty = ogqty2;
    }

    public String getOgprice() {
        return ogprice;
    }

    public void setOgprice(String ogprice2) {
        ogprice = ogprice2;
    }
}
