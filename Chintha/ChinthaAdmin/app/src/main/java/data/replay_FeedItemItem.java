package data;

public class replay_FeedItemItem {
    private String commentid;
    private String replay;
    private String time;

    public replay_FeedItemItem() {
    }

    public replay_FeedItemItem(String replay2, String time2, String commentid2) {
        replay = replay2;
        time = time2;
        commentid = commentid2;
    }

    public String getreplay() {
        return replay;
    }

    public void setreplay(String replay2) {
        replay = replay2;
    }

    public String gettime() {
        return time;
    }

    public void settime(String time2) {
        time = time2;
    }

    public String getcommentid() {
        return commentid;
    }

    public void setcommentid(String commentid2) {
        commentid = commentid2;
    }
}
