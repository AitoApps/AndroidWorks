package data;

public class MainCart_FeedItem {
    private String delidisc;
    private String deliverycharge;
    private String imgsig;
    private String miniordramt;
    private String shopid;
    private String shopname;
    private String totalcharge;

    public MainCart_FeedItem() {
    }

    public MainCart_FeedItem(String shopid2, String shopname2, String totalcharge2, String deliverycharge2, String miniordramt2, String delidisc2, String imgsig2) {
        shopid = shopid2;
        shopname = shopname2;
        totalcharge = totalcharge2;
        deliverycharge = deliverycharge2;
        miniordramt = miniordramt2;
        delidisc = delidisc2;
        imgsig = imgsig2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getDelidisc() {
        return delidisc;
    }

    public void setDelidisc(String delidisc2) {
        delidisc = delidisc2;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid2) {
        shopid = shopid2;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname2) {
        shopname = shopname2;
    }

    public String getTotalcharge() {
        return totalcharge;
    }

    public void setTotalcharge(String totalcharge2) {
        totalcharge = totalcharge2;
    }

    public String getDeliverycharge() {
        return deliverycharge;
    }

    public void setDeliverycharge(String deliverycharge2) {
        deliverycharge = deliverycharge2;
    }

    public String getMiniordramt() {
        return miniordramt;
    }

    public void setMiniordramt(String miniordramt2) {
        miniordramt = miniordramt2;
    }
}
