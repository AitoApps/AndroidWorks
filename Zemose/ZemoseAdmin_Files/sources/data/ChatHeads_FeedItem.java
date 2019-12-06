package data;

public class ChatHeads_FeedItem {
    private String chattime;
    private String issupplier;
    private String pkey;
    private String userid;
    private String username;

    public ChatHeads_FeedItem() {
    }

    public ChatHeads_FeedItem(String pkey2, String userid2, String username2, String chattime2, String issupplier2) {
        this.pkey = pkey2;
        this.userid = userid2;
        this.username = username2;
        this.chattime = chattime2;
        this.issupplier = issupplier2;
    }

    public String getissupplier() {
        return this.issupplier;
    }

    public void setissupplier(String issupplier2) {
        this.issupplier = issupplier2;
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
