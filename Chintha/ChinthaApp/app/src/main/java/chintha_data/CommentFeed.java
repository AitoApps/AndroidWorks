package chintha_data;

public class CommentFeed {
    private String comments;
    private String imgsig;
    private String name;
    private String postdate;
    private String profilePic;
    private String replay;
    private String sn;
    private String userid;

    public CommentFeed() {
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid2) {
        userid = userid2;
    }

    public String get_name() {
        return name;
    }

    public void set_name(String name2) {
        name = name2;
    }

    public String getcomments() {
        return comments;
    }

    public void setcomments(String comments2) {
        comments = comments2;
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

    public void setpostdate(String postdate2) {
        postdate = postdate2;
    }

    public String getreplay() {
        return replay;
    }

    public void setreplay(String replay2) {
        replay = replay2;
    }

    public String get_imgsig() {
        return imgsig;
    }

    public void set_imgsig(String imgsig2) {
        imgsig = imgsig2;
    }
}
