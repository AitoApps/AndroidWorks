package data;

public class Wallet_Report_FeedItem {
    private String amount;
    private String contact;
    private String name;
    private String userid;

    public Wallet_Report_FeedItem() {
    }

    public Wallet_Report_FeedItem(String userid2, String name2, String contact2, String amount2) {
        this.userid = userid2;
        this.name = name2;
        this.contact = contact2;
        this.amount = amount2;
    }

    public String getamount() {
        return this.amount;
    }

    public void setamount(String amount2) {
        this.amount = amount2;
    }

    public String getcontact() {
        return this.contact;
    }

    public void setcontact(String contact2) {
        this.contact = contact2;
    }

    public String getuserid() {
        return this.userid;
    }

    public void setuserid(String userid2) {
        this.userid = userid2;
    }

    public String getname() {
        return this.name;
    }

    public void setname(String name2) {
        this.name = name2;
    }
}
