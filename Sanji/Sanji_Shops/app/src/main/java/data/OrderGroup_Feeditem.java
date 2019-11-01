package data;

public class OrderGroup_Feeditem {
    private String delicharge;
    private String groupid;
    private String orderdate;
    private String total;

    public OrderGroup_Feeditem() {
    }

    public OrderGroup_Feeditem(String groupid2, String total2, String orderdate2, String delicharge2) {
        groupid = groupid2;
        total = total2;
        orderdate = orderdate2;
        delicharge = delicharge2;
    }

    public String getdelicharge() {
        return delicharge;
    }

    public void setdelicharge(String delicharge2) {
        delicharge = delicharge2;
    }

    public String getgroupid() {
        return groupid;
    }

    public void setgroupid(String groupid2) {
        groupid = groupid2;
    }

    public String gettotal() {
        return total;
    }

    public void settotal(String total2) {
        total = total2;
    }

    public String getorderdate() {
        return orderdate;
    }

    public void setorderdate(String orderdate2) {
        orderdate = orderdate2;
    }
}
