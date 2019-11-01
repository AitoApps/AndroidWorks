package data;

public class Product_cat_FeedItem {
    private String catorder;
    private String imgsig;
    private String sn;
    private String title;

    public Product_cat_FeedItem() {
    }

    public Product_cat_FeedItem(String sn2, String title2, String imgsig2, String catorder2) {
        sn = sn2;
        title = title2;
        imgsig = imgsig2;
        catorder = catorder2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title2) {
        title = title2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getcatorder() {
        return catorder;
    }

    public void setcatorder(String catorder2) {
        catorder = catorder2;
    }
}
