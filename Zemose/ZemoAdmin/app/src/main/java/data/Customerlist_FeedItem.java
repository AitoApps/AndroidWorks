package data;

public class Customerlist_FeedItem {
    private String custid;
    private String custmobile;
    private String custname;

    public Customerlist_FeedItem() {
    }

    public Customerlist_FeedItem(String custid2, String custname2, String custmobile2) {
        this.custid = custid2;
        this.custname = custname2;
        this.custmobile = custmobile2;
    }

    public String getcustid() {
        return this.custid;
    }

    public void setcustid(String custid2) {
        this.custid = custid2;
    }

    public String getcustname() {
        return this.custname;
    }

    public void setcustname(String custname2) {
        this.custname = custname2;
    }

    public String getcustmobile() {
        return this.custmobile;
    }

    public void setcustmobile(String custmobile2) {
        this.custmobile = custmobile2;
    }
}
