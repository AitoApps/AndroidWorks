package data;

public class Sna_FeedItem {
    private String customercontact;
    private String customername;
    private String imgsig;
    private String orderdate;
    private String productimage;
    private String productname;

    public Sna_FeedItem() {
    }

    public Sna_FeedItem(String productname2, String orderdate2, String customername2, String customercontact2, String productimage2, String imgsig2) {
        this.productname = productname2;
        this.orderdate = orderdate2;
        this.customername = customername2;
        this.customercontact = customercontact2;
        this.productimage = productimage2;
        this.imgsig = imgsig2;
    }

    public String getimgsig() {
        return this.imgsig;
    }

    public void setimgsig(String imgsig2) {
        this.imgsig = imgsig2;
    }

    public String getproductimage() {
        return this.productimage;
    }

    public void setproductimage(String productimage2) {
        this.productimage = productimage2;
    }

    public String getcustomercontact() {
        return this.customercontact;
    }

    public void setcustomercontact(String customercontact2) {
        this.customercontact = customercontact2;
    }

    public String getcustomername() {
        return this.customername;
    }

    public void setcustomername(String customername2) {
        this.customername = customername2;
    }

    public String getproductname() {
        return this.productname;
    }

    public void setproductname(String productname2) {
        this.productname = productname2;
    }

    public String getorderdate() {
        return this.orderdate;
    }

    public void setorderdate(String orderdate2) {
        this.orderdate = orderdate2;
    }
}
