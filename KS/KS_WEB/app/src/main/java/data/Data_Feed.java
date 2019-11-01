package data;

public class Data_Feed {
    private String id;
    private String title;

    public Data_Feed() {
    }

    public Data_Feed(String id2, String title2) {
        id = id2;
        title = title2;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title2) {
        title = title2;
    }

    public String getid() {
        return id;
    }

    public void setid(String id2) {
        id = id2;
    }
}
