package data;

public class Towns_FeedItem {
    private String areaname;
    private String sn;
    private String townid;

    public Towns_FeedItem() {
    }

    public Towns_FeedItem(String sn2, String areaname2, String townid2) {
        sn = sn2;
        areaname = areaname2;
        townid = townid2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getareaname() {
        return areaname;
    }

    public void setareaname(String areaname2) {
        areaname = areaname2;
    }

    public String gettownid() {
        return townid;
    }

    public void settownid(String townid2) {
        townid = townid2;
    }
}
