package data;

public class RequestPro_FeedItem {
    private String itemdisc;
    private String itemname;
    private String regdate;
    private String sn;
    private String supcontact;
    private String supid;
    private String supnam;

    public RequestPro_FeedItem() {
    }

    public RequestPro_FeedItem(String sn2, String itemname2, String itemdisc2, String supid2, String supnam2, String supcontact2, String regdate2) {
        this.sn = sn2;
        this.itemname = itemname2;
        this.itemdisc = itemdisc2;
        this.supid = supid2;
        this.supnam = supnam2;
        this.supcontact = supcontact2;
        this.regdate = regdate2;
    }

    public String getregdate() {
        return this.regdate;
    }

    public void setregdate(String regdate2) {
        this.regdate = regdate2;
    }

    public String getsn() {
        return this.sn;
    }

    public void setsn(String sn2) {
        this.sn = sn2;
    }

    public String getitemname() {
        return this.itemname;
    }

    public void setitemname(String itemname2) {
        this.itemname = itemname2;
    }

    public String getitemdisc() {
        return this.itemdisc;
    }

    public void setitemdisc(String itemdisc2) {
        this.itemdisc = itemdisc2;
    }

    public String getsupid() {
        return this.supid;
    }

    public void setsupid(String supid2) {
        this.supid = supid2;
    }

    public String getsupnam() {
        return this.supnam;
    }

    public void setsupnam(String supnam2) {
        this.supnam = supnam2;
    }

    public String getsupcontact() {
        return this.supcontact;
    }

    public void setsupcontact(String supcontact2) {
        this.supcontact = supcontact2;
    }
}
