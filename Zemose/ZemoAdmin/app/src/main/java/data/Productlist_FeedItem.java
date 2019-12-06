package data;

public class Productlist_FeedItem {
    private String imgpath;
    private String name;
    private String productid;
    private String productqty;
    private String sn;
    private String status;

    public Productlist_FeedItem() {
    }

    public Productlist_FeedItem(String sn2, String productid2, String name2, String productqty2, String status2, String imgpath2) {
        this.sn = sn2;
        this.name = name2;
        this.productid = productid2;
        this.productqty = productqty2;
        this.imgpath = imgpath2;
        this.status = status2;
    }

    public String getsn() {
        return this.sn;
    }

    public void setsn(String sn2) {
        this.sn = sn2;
    }

    public String getimgpath() {
        return this.imgpath;
    }

    public void setimgpath(String imgpath2) {
        this.imgpath = imgpath2;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String name2) {
        this.name = name2;
    }

    public String getproductid() {
        return this.productid;
    }

    public void setproductid(String productid2) {
        this.productid = productid2;
    }

    public String getproductqty() {
        return this.productqty;
    }

    public void setproductqty(String productqty2) {
        this.productqty = productqty2;
    }

    public String getstatus() {
        return this.status;
    }

    public void setstatus(String status2) {
        this.status = status2;
    }
}
