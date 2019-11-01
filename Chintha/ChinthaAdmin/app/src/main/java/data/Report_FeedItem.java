package data;

public class Report_FeedItem {
    private String photodemension;
    private String photourl;
    private String reporttype;
    private String reportuserid;
    private String sn;
    private String status;
    private String statusid;
    private String statustype;
    private String statustype1;
    private String statususerid;

    public Report_FeedItem() {
    }

    public Report_FeedItem(String sn2, String reportuserid2, String statususerid2, String statusid2, String status2, String reporttype2, String statustype2, String statustype12, String photourl2, String photodemension2) {
        sn = sn2;
        reportuserid = reportuserid2;
        statususerid = statususerid2;
        statusid = statusid2;
        status = status2;
        reporttype = reporttype2;
        statustype = statustype2;
        statustype1 = statustype12;
        photourl = photourl2;
        photodemension = photodemension2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getreportuserid() {
        return reportuserid;
    }

    public void setreportuserid(String reportuserid2) {
        reportuserid = reportuserid2;
    }

    public String getstatususerid() {
        return statususerid;
    }

    public void setstatususerid(String statususerid2) {
        statususerid = statususerid2;
    }

    public String getstatusid() {
        return statusid;
    }

    public void setstatusid(String statusid2) {
        statusid = statusid2;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status2) {
        status = status2;
    }

    public String getreporttype() {
        return reporttype;
    }

    public void setreporttype(String reporttype2) {
        reporttype = reporttype2;
    }

    public String getstatustype() {
        return statustype;
    }

    public void setstatustype(String statustype2) {
        statustype = statustype2;
    }

    public String getstatustype1() {
        return statustype1;
    }

    public void setstatustype1(String statustype12) {
        statustype1 = statustype12;
    }

    public String getphotourl() {
        return photourl;
    }

    public void setphotourl(String photourl2) {
        photourl = photourl2;
    }

    public String getphotodemension() {
        return photodemension;
    }

    public void setphotodemension(String photodemension2) {
        photodemension = photodemension2;
    }
}
