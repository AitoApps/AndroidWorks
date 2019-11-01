package data;

public class Notifications_FeedItem {
    private String message;
    private String notititle;
    private String notitype;
    private String sn;
    private String time;

    public Notifications_FeedItem() {
    }

    public Notifications_FeedItem(String notitype2, String notititle2, String sn2, String message2, String time2) {
        sn = sn2;
        message = message2;
        time = time2;
        notitype = notitype2;
        notititle = notititle2;
    }

    public String getnotitype() {
        return notitype;
    }

    public void setnotitype(String notitype2) {
        notitype = notitype2;
    }

    public String getnotititle() {
        return notititle;
    }

    public void setnotititle(String notititle2) {
        notititle = notititle2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getmessage() {
        return message;
    }

    public void setmessage(String message2) {
        message = message2;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time2) {
        time = time2;
    }
}
