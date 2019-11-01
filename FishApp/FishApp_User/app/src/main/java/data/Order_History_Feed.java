package data;

public class Order_History_Feed {
    private String dlcharge;
    private String groupid;
    private String itemdetails;
    private String orderdate;
    private String status;
    private String totalamount;

    public Order_History_Feed() {
    }

    public Order_History_Feed(String orderdate2, String groupid2, String status2, String totalamount2, String dlcharge2, String itemdetails2) {
        orderdate = orderdate2;
        groupid = groupid2;
        totalamount = totalamount2;
        status = status2;
        dlcharge = dlcharge2;
        itemdetails = itemdetails2;
    }

    public String getitemdetails() {
        return itemdetails;
    }

    public void setitemdetails(String itemdetails2) {
        itemdetails = itemdetails2;
    }

    public String getdlcharge() {
        return dlcharge;
    }

    public void setdlcharge(String dlcharge2) {
        dlcharge = dlcharge2;
    }

    public String getorderdate() {
        return orderdate;
    }

    public void setorderdate(String orderdate2) {
        orderdate = orderdate2;
    }

    public String getgroupid() {
        return groupid;
    }

    public void setgroupid(String groupid2) {
        groupid = groupid2;
    }

    public String gettotalamount() {
        return totalamount;
    }

    public void settotalamount(String totalamount2) {
        totalamount = totalamount2;
    }

    public String getstatus() {
        return status;
    }

    public void setstatus(String status2) {
        status = status2;
    }
}
