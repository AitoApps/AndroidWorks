package data;

public class Order_List_FeedItem {
    private String imgsig;
    private String orderdate;
    private String orderid;
    private String productid;
    private String productname;
    private String productprice;
    private String productqty;
    private String producttotal;

    public Order_List_FeedItem() {
    }

    public Order_List_FeedItem(String orderid2, String orderdate2, String productid2, String productname2, String productprice2, String productqty2, String producttotal2, String imgsig2) {
        orderid = orderid2;
        orderdate = orderdate2;
        productprice = productprice2;
        productqty = productqty2;
        productid = productid2;
        productname = productname2;
        producttotal = producttotal2;
        imgsig = imgsig2;
    }

    public String getorderid() {
        return orderid;
    }

    public void setorderid(String orderid2) {
        orderid = orderid2;
    }

    public String getorderdate() {
        return orderdate;
    }

    public void setorderdate(String orderdate2) {
        orderdate = orderdate2;
    }

    public String getproductid() {
        return productid;
    }

    public void setproductid(String productid2) {
        productid = productid2;
    }

    public String getproductname() {
        return productname;
    }

    public void setproductname(String productname2) {
        productname = productname2;
    }

    public String getproductprice() {
        return productprice;
    }

    public void setproductprice(String productprice2) {
        productprice = productprice2;
    }

    public String getproductqty() {
        return productqty;
    }

    public void setproductqty(String productqty2) {
        productqty = productqty2;
    }

    public String getproducttotal() {
        return producttotal;
    }

    public void setproducttotal(String producttotal2) {
        producttotal = producttotal2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }
}
