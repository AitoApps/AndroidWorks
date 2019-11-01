package chintha_data;

public class ChinthakarFeed {
    private String dppic;
    private String get_useid;
    private String name;

    public ChinthakarFeed() {
    }

    public String getuserid() {
        return get_useid;
    }

    public void setuserid(String userid) {
        get_useid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name2) {
        name = name2;
    }

    public String getDppic() {
        return dppic;
    }

    public void setDppic(String dppic2) {
        dppic = dppic2;
    }
}
