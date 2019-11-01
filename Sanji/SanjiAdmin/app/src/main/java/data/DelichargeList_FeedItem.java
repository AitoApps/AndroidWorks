package data;

public class DelichargeList_FeedItem {
    private String charge;
    private String toamt;

    public DelichargeList_FeedItem() {
    }
    public DelichargeList_FeedItem(String toamt2, String charge2) {
        toamt = toamt2;
        charge = charge2;
    }
    public String getToamt() {
        return toamt;
    }

    public void setToamt(String toamt2) {
        toamt = toamt2;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge2) {
        charge = charge2;
    }
}
