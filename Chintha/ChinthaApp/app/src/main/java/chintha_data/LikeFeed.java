package chintha_data;

public class LikeFeed {
    private String imgsig;
    private String name;
    private String profilePic;
    private String userid;

    public LikeFeed() {
    }
    public String get_userid() {
        return userid;
    }

    public void set_userid(String userid2) {
        userid = userid2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name2) {
        name = name2;
    }

    public String get_dppic() {
        return profilePic;
    }

    public void set_dppic(String profilePic2) {
        profilePic = profilePic2;
    }

    public String get_imgsig() {
        return imgsig;
    }

    public void set_imgsig(String imgsig2) {
        imgsig = imgsig2;
    }
}
