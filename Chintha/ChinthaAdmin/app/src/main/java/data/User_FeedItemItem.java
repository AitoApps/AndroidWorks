package data;

public class User_FeedItemItem {
    private String mobile;
    private String name;
    private String sn;

    public User_FeedItemItem() {
    }

    public User_FeedItemItem(String sn2, String name2, String mobile2) {
        sn = sn2;
        mobile = mobile2;
        name = name2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getmobile() {
        return mobile;
    }

    public void setmobile(String mobile2) {
        mobile = mobile2;
    }

    public String getname() {
        return name;
    }

    public void setname(String name2) {
        name = name2;
    }
}
