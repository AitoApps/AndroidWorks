package chintha_data;

public class Replay_Feed {
    private String cmnt;
    private String imgsig;
    private String name;
    private String postdate;
    private String profilePic;
    private String sn;
    private String userid;

    public Replay_Feed() {
    }
    public String get_imgsig() {
        return imgsig;
    }

    public void set_imgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String get_sn() {
        return sn;
    }

    public void set_sn(String sn2) {
        sn = sn2;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid2) {
        userid = userid2;
    }

    public String get_cmnts() {
        return cmnt;
    }

    public void set_cmnts(String comments) {
        cmnt = comments;
    }

    public String get_dppic() {
        return profilePic;
    }

    public void set_dppic(String profilePic2) {
        profilePic = profilePic2;
    }

    public String get_postdate() {
        return postdate;
    }

    public void set_postdate(String postdate2) {
        postdate = postdate2;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name2) {
        name = name2;
    }
}
