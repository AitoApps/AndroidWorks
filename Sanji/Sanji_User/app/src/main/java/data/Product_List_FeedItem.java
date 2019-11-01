package data;

public class Product_List_FeedItem {
    private String catname;
    private String ddsic;
    private String deli_type;
    private String dlcharge;
    private String imgsig1;
    private String itemdiscription;
    private String itemname;
    private String location;
    private String maxorder;
    private String ogprice;
    private String price;
    private String productcat;
    private String shopid;
    private String shopmobile;
    private String shopname;
    private String shopplace;
    private String shoptime;
    private String sn;
    private String type;

    public Product_List_FeedItem() {
    }

    public Product_List_FeedItem(String catname2, String deli_type2, String type2, String sn2, String productcat2, String shopid2, String itemname2, String price2, String itemdiscription2, String imgsig12, String ogprice2, String maxorder2, String dlcharge2, String ddsic2, String shopname2, String shopplace2, String shopmobile2, String shoptime2, String location2) {
        catname = catname2;
        deli_type = deli_type2;
        sn = sn2;
        type = type2;
        productcat = productcat2;
        shopid = shopid2;
        itemname = itemname2;
        price = price2;
        ogprice = ogprice2;
        itemdiscription = itemdiscription2;
        imgsig1 = imgsig12;
        maxorder = maxorder2;
        dlcharge = dlcharge2;
        ddsic = ddsic2;
        shopname = shopname2;
        shopmobile = shopmobile2;
        shopplace = shopplace2;
        shoptime = shoptime2;
        location = location2;
    }

    public String getlocation() {
        return location;
    }

    public void setlocation(String location2) {
        location = location2;
    }

    public String getshoptime() {
        return shoptime;
    }

    public void setshoptime(String shoptime2) {
        shoptime = shoptime2;
    }

    public String getcatname() {
        return catname;
    }

    public void setcatname(String catname2) {
        catname = catname2;
    }

    public String getshopplace() {
        return shopplace;
    }

    public void setshopplace(String shopplace2) {
        shopplace = shopplace2;
    }

    public String getshopmobile() {
        return shopmobile;
    }

    public void setshopmobile(String shopmobile2) {
        shopmobile = shopmobile2;
    }

    public String getshopname() {
        return shopname;
    }

    public void setshopname(String shopname2) {
        shopname = shopname2;
    }

    public String getddsic() {
        return ddsic;
    }

    public void setddsic(String ddsic2) {
        ddsic = ddsic2;
    }

    public String getdlcharge() {
        return dlcharge;
    }

    public void setdlcharge(String dlcharge2) {
        dlcharge = dlcharge2;
    }

    public String getdeli_type() {
        return deli_type;
    }

    public void setdeli_type(String deli_type2) {
        deli_type = deli_type2;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type2) {
        type = type2;
    }

    public String getmaxorder() {
        return maxorder;
    }

    public void setmaxorder(String maxorder2) {
        maxorder = maxorder2;
    }

    public String getogprice() {
        return ogprice;
    }

    public void setogprice(String ogprice2) {
        ogprice = ogprice2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getproductcat() {
        return productcat;
    }

    public void setproductcat(String productcat2) {
        productcat = productcat2;
    }

    public String getshopid() {
        return shopid;
    }

    public void setshopid(String shopid2) {
        shopid = shopid2;
    }

    public String getitemname() {
        return itemname;
    }

    public void setitemname(String itemname2) {
        itemname = itemname2;
    }

    public String getprice() {
        return price;
    }

    public void setprice(String price2) {
        price = price2;
    }

    public String getitemdiscription() {
        return itemdiscription;
    }

    public void setitemdiscription(String itemdiscription2) {
        itemdiscription = itemdiscription2;
    }

    public String getimgsig1() {
        return imgsig1;
    }

    public void setimgsig1(String imgsig12) {
        imgsig1 = imgsig12;
    }
}
