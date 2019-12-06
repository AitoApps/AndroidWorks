package data;

public class PaymentApprove_FeedItem {
    private String amount;
    private String bankcode;
    private String cardnum;
    private String mode;
    private String payid;
    private String paymentid;
    private String shopContact;
    private String shopId;
    private String shopName;
    private String txtnid;

    public PaymentApprove_FeedItem() {
    }

    public PaymentApprove_FeedItem(String payid2, String paymentid2, String mode2, String txtnid2, String amount2, String bankcode2, String cardnum2, String shopId2, String shopName2, String shopContact2) {
        this.payid = payid2;
        this.paymentid = paymentid2;
        this.mode = mode2;
        this.txtnid = txtnid2;
        this.amount = amount2;
        this.bankcode = bankcode2;
        this.cardnum = cardnum2;
        this.shopId = shopId2;
        this.shopName = shopName2;
        this.shopContact = shopContact2;
    }

    public String getshopName() {
        return this.shopName;
    }

    public void setshopName(String shopName2) {
        this.shopName = shopName2;
    }

    public String getshopContact() {
        return this.shopContact;
    }

    public void setshopContact(String shopContact2) {
        this.shopContact = shopContact2;
    }

    public String getpayid() {
        return this.payid;
    }

    public void setpayid(String payid2) {
        this.payid = payid2;
    }

    public String getpaymentid() {
        return this.paymentid;
    }

    public void setpaymentid(String paymentid2) {
        this.paymentid = paymentid2;
    }

    public String getmode() {
        return this.mode;
    }

    public void setmode(String mode2) {
        this.mode = mode2;
    }

    public String gettxtnid() {
        return this.txtnid;
    }

    public void settxtnid(String txtnid2) {
        this.txtnid = txtnid2;
    }

    public String getamount() {
        return this.amount;
    }

    public void setamount(String amount2) {
        this.amount = amount2;
    }

    public String getbankcode() {
        return this.bankcode;
    }

    public void setbankcode(String bankcode2) {
        this.bankcode = bankcode2;
    }

    public String getcardnum() {
        return this.cardnum;
    }

    public void setcardnum(String cardnum2) {
        this.cardnum = cardnum2;
    }

    public String getshopId() {
        return this.shopId;
    }

    public void setshopId(String shopId2) {
        this.shopId = shopId2;
    }
}
