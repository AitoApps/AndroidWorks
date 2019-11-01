package data;

public class Recharge_FeedItem {
    private String coponcode;
    private String operator;
    private String sn;
    private String userid;

    public Recharge_FeedItem() {
    }

    public Recharge_FeedItem(String sn2, String operator2, String coponcode2, String userid2) {
        sn = sn2;
        operator = operator2;
        coponcode = coponcode2;
        userid = userid2;
    }

    public String getuserid() {
        return userid;
    }

    public void setuserid(String userid2) {
        userid = userid2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getoperator() {
        return operator;
    }

    public void setoperator(String operator2) {
        operator = operator2;
    }

    public String getcoponcode() {
        return coponcode;
    }

    public void setcoponcode(String coponcode2) {
        coponcode = coponcode2;
    }
}
