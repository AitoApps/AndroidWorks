package data;

public class Productcat_Feeditem {
    private String imgsig;
    private String productname;
    private String sn;

    public Productcat_Feeditem() {
    }

    public Productcat_Feeditem(String sn2, String productname2, String imgsig2) {
        sn = sn2;
        productname = productname2;
        imgsig = imgsig2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getproductname() {
        return productname;
    }

    public void setproductname(String productname2) {
        productname = productname2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }
}
