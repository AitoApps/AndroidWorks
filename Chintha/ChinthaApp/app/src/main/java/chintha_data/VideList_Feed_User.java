package chintha_data;

public class VideList_Feed_User {
    private String downlink;
    private String duration;
    private String imgsig;
    private String playcount;
    private String showads;
    private String title;
    private String videoid;

    public VideList_Feed_User() {
    }

    public String getdownlink() {
        return downlink;
    }

    public void setdownlink(String downlink2) {
        downlink = downlink2;
    }

    public String getvideoid() {
        return videoid;
    }

    public void setvideoid(String videoid2) {
        videoid = videoid2;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title2) {
        title = title2;
    }

    public String get_plyduration() {
        return duration;
    }

    public void set_playduration(String duration2) {
        duration = duration2;
    }

    public String getimgsig() {
        return imgsig;
    }

    public void setimgsig(String imgsig2) {
        imgsig = imgsig2;
    }

    public String getshowads() {
        return showads;
    }

    public void setshowads(String showads2) {
        showads = showads2;
    }

    public String get_playcount() {
        return playcount;
    }

    public void set_playcount(String viewcount) {
        playcount = viewcount;
    }
}
