package data;

public class Productlist_FeedItem {
    private String catid;
    private String discription;
    private String imgsig1;
    private String itemkeyword;
    private String itemname;
    private String minorder;
    private String offerprice;
    private String orginalprice;
    private String sn;
    private String status;
    private String unittype;

    public Productlist_FeedItem() {
    }

    public Productlist_FeedItem(String sn2, String catid2, String itemname2, String offerprice2, String orginalprice2, String minorder2, String unittype2, String discription2, String imgsig12, String status2, String itemkeyword2) {
        sn = sn2;
        catid = catid2;
        itemname = itemname2;
        itemkeyword = itemkeyword2;
        offerprice = offerprice2;
        orginalprice = orginalprice2;
        minorder = minorder2;
        discription = discription2;
        imgsig1 = imgsig12;
        status = status2;
        unittype = unittype2;
        itemkeyword = itemkeyword2;
    }

    public String getItemkeyword() {
        return itemkeyword;
    }

    public void setItemkeyword(String itemkeyword2) {
        itemkeyword = itemkeyword2;
    }

    public String getMinorder() {
        return minorder;
    }

    public void setMinorder(String minorder2) {
        minorder = minorder2;
    }

    public String getUnittype() {
        return unittype;
    }

    public void setUnittype(String unittype2) {
        unittype = unittype2;
    }

    public String getcatid() {
        return catid;
    }

    public void setcatid(String catid2) {
        catid = catid2;
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

    public String getdiscription() {
        return discription;
    }

    public void setdiscription(String discription2) {
        discription = discription2;
    }

    public String getimgsig1() {
        return imgsig1;
    }

    public void setimgsig1(String imgsig12) {
        imgsig1 = imgsig12;
    }
}
