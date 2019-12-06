package data;

public class Wallet_History_FeedItem {
    private String amount;
    private String paymentMethod;
    private String remarks;
    private String shopId;
    private String shopname;
    private String timeStamp;

    public Wallet_History_FeedItem() {
    }

    public Wallet_History_FeedItem(String timeStamp2, String shopId2, String remarks2, String paymentMethod2, String amount2, String shopname2) {
        this.shopId = shopId2;
        this.remarks = remarks2;
        this.paymentMethod = paymentMethod2;
        this.amount = amount2;
        this.timeStamp = timeStamp2;
        this.shopname = shopname2;
    }

    public String getshopname() {
        return this.shopname;
    }

    public void setshopname(String shopname2) {
        this.shopname = shopname2;
    }

    public String gettimeStamp() {
        return this.timeStamp;
    }

    public void settimeStamp(String timeStamp2) {
        this.timeStamp = timeStamp2;
    }

    public String getamount() {
        return this.amount;
    }

    public void setamount(String amount2) {
        this.amount = amount2;
    }

    public String getpaymentMethod() {
        return this.paymentMethod;
    }

    public void setpaymentMethod(String paymentMethod2) {
        this.paymentMethod = paymentMethod2;
    }

    public String getshopId() {
        return this.shopId;
    }

    public void setshopId(String shopId2) {
        this.shopId = shopId2;
    }

    public String getremarks() {
        return this.remarks;
    }

    public void setremarks(String remarks2) {
        this.remarks = remarks2;
    }
}
