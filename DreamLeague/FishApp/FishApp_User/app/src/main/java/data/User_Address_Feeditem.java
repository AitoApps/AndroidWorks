package data;

public class User_Address_Feeditem {
    private String pincode;
    private String sn;
    private String user_address;
    private String user_landmark;
    private String user_mobile1;
    private String user_mobile2;
    private String user_name;
    private String user_place;

    public User_Address_Feeditem() {
    }

    public User_Address_Feeditem(String sn2, String user_name2, String user_place2, String user_mobile12, String user_mobile22, String user_address2, String user_landmark2, String pincode2) {
        sn = sn2;
        user_name = user_name2;
        user_place = user_place2;
        user_mobile1 = user_mobile12;
        user_mobile2 = user_mobile22;
        user_address = user_address2;
        user_landmark = user_landmark2;
        pincode = pincode2;
    }

    public String getsn() {
        return sn;
    }

    public void setsn(String sn2) {
        sn = sn2;
    }

    public String getuser_name() {
        return user_name;
    }

    public void setuser_name(String user_name2) {
        user_name = user_name2;
    }

    public String getuser_place() {
        return user_place;
    }

    public void setuser_place(String user_place2) {
        user_place = user_place2;
    }

    public String getuser_mobile1() {
        return user_mobile1;
    }

    public void setuser_mobile1(String user_mobile12) {
        user_mobile1 = user_mobile12;
    }

    public String getuser_mobile2() {
        return user_mobile2;
    }

    public void setuser_mobile2(String user_mobile22) {
        user_mobile2 = user_mobile22;
    }

    public String getuser_address() {
        return user_address;
    }

    public void setuser_address(String user_address2) {
        user_address = user_address2;
    }

    public String getuser_landmark() {
        return user_landmark;
    }

    public void setuser_landmark(String user_landmark2) {
        user_landmark = user_landmark2;
    }

    public String getpincode() {
        return pincode;
    }

    public void setpincode(String pincode2) {
        pincode = pincode2;
    }
}
