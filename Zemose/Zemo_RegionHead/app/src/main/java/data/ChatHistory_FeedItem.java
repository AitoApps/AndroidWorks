package data;

public class ChatHistory_FeedItem {
    private String chattime;
    private String chattype;
    private String msg;
    private String pkey;
    private String userid;
    private String username;

    public ChatHistory_FeedItem() {
    }

    public ChatHistory_FeedItem(String pkey2, String userid2, String username2, String chattime2, String msg2, String chattype2) {
        this.pkey = pkey2;
        this.userid = userid2;
        this.username = username2;
        this.chattime = chattime2;
        this.msg = msg2;
        this.chattype = chattype2;
    }

    public String getchattype() {
        return this.chattype;
    }

    public void setchattype(String chattype2) {
        this.chattype = chattype2;
    }

    public String getmsg() {
        return this.msg;
    }

    public void setmsg(String msg2) {
        this.msg = msg2;
    }

    public String getchattime() {
        return this.chattime;
    }

    public void setchattime(String chattime2) {
        this.chattime = chattime2;
    }

    public String getpkey() {
        return this.pkey;
    }

    public void setpkey(String pkey2) {
        this.pkey = pkey2;
    }

    public String getuserid() {
        return this.userid;
    }

    public void setuserid(String userid2) {
        this.userid = userid2;
    }

    public String getusername() {
        return this.username;
    }

    public void setusername(String username2) {
        this.username = username2;
    }
}
