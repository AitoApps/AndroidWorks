package data;

public class Supplierlist_FeedItem {
    private String imgpath;
    private String imgsig;
    private String supid;
    private String supllocation;
    private String supmobile;
    private String supname;
    private String supplace;
    private String walletamount;

    public Supplierlist_FeedItem() {
    }

    public Supplierlist_FeedItem(String supid2, String supname2, String supmobile2, String supplace2, String supllocation2, String walletamount2, String imgpath2, String imgsig2) {
        this.supid = supid2;
        this.supname = supname2;
        this.supmobile = supmobile2;
        this.supplace = supplace2;
        this.supllocation = supllocation2;
        this.walletamount = walletamount2;
        this.imgpath = imgpath2;
        this.imgsig = imgsig2;
    }

    public String getimgpath() {
        return this.imgpath;
    }

    public void setimgpath(String imgpath2) {
        this.imgpath = imgpath2;
    }

    public String getimgsig() {
        return this.imgsig;
    }

    public void setimgsig(String imgsig2) {
        this.imgsig = imgsig2;
    }

    public String getsupid() {
        return this.supid;
    }

    public void setsupid(String supid2) {
        this.supid = supid2;
    }

    public String getsupname() {
        return this.supname;
    }

    public void setsupname(String supname2) {
        this.supname = supname2;
    }

    public String getsupmobile() {
        return this.supmobile;
    }

    public void setsupmobile(String supmobile2) {
        this.supmobile = supmobile2;
    }

    public String getsupplace() {
        return this.supplace;
    }

    public void setsupplace(String supplace2) {
        this.supplace = supplace2;
    }

    public String getsupllocation() {
        return this.supllocation;
    }

    public void setsupllocation(String supllocation2) {
        this.supllocation = supllocation2;
    }

    public String getwalletamount() {
        return this.walletamount;
    }

    public void setwalletamount(String walletamount2) {
        this.walletamount = walletamount2;
    }
}
