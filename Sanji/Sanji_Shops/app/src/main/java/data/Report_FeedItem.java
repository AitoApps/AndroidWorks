package data;

public class Report_FeedItem {
    private String imgsig;
    private String itemid;
    private String itemname;
    private String vcount_cancelled;
    private String vcount_pedning;
    private String vcount_sales;

    public Report_FeedItem() {
    }

    public Report_FeedItem(String itemid2, String itemname2, String vcount_cancelled2, String vcount_sales2, String vcount_pedning2, String imgsig2) {
        itemname = itemname2;
        itemid = itemid2;
        vcount_cancelled = vcount_cancelled2;
        imgsig = imgsig2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getitemname() {
        return itemname;
    }

    public void setitemname(String itemname2) {
        itemname = itemname2;
    }

    public String getitemid() {
        return itemid;
    }

    public void setitemid(String itemid2) {
        itemid = itemid2;
    }

    public String getvcount_cancelled() {
        return vcount_cancelled;
    }

    public void setvcount_cancelled(String vcount_cancelled2) {
        vcount_cancelled = vcount_cancelled2;
    }

    public String getvcount_sales() {
        return vcount_sales;
    }

    public void setvcount_sales(String vcount_sales2) {
        vcount_sales = vcount_sales2;
    }

    public String getvcount_pedning() {
        return vcount_pedning;
    }

    public void setvcount_pedning(String vcount_pedning2) {
        vcount_pedning = vcount_pedning2;
    }
}
