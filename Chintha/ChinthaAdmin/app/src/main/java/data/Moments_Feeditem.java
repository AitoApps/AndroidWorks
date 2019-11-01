package data;

public class Moments_Feeditem {
    private String extension;
    private String regdate;
    private String sn;
    private String title;
    private String types;
    private String userid;

    public Moments_Feeditem() {
    }

    public Moments_Feeditem(String sn2, String regdate2, String userid2, String types2, String extension2, String title2) {
        sn = sn2;
        regdate = regdate2;
        userid = userid2;
        types = types2;
        extension = extension2;
        title = title2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getregdate() {
        return regdate;
    }

    public void setregdate(String regdate2) {
        regdate = regdate2;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid2) {
        userid = userid2;
    }

    public String gettypes() {
        return types;
    }

    public void settypes(String types2) {
        types = types2;
    }

    public String getextension() {
        return extension;
    }

    public void setextension(String extension2) {
        extension = extension2;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title2) {
        title = title2;
    }
}
