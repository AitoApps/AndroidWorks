package data;

public class Order_Report_Feed {
    private String dlcharge;
    private String groupid;
    private String itemdetails;
    private String orderdate;
    private String smmobile;
    private String smname;
    private String status;
    private String totalamount;

    public Order_Report_Feed() {
    }

    public Order_Report_Feed(String orderdate2, String groupid2, String status2, String totalamount2, String dlcharge2, String smname2, String smmobile2, String itemdetails2) {
        orderdate = orderdate2;
        groupid = groupid2;
        smname = smname2;
        smmobile = smmobile2;
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

    public String getsmname() {
        return smname;
    }

    public void setsmname(String smname2) {
        smname = smname2;
    }

    public String getsmmobile() {
        return smmobile;
    }

    public void setsmmobile(String smmobile2) {
        smmobile = smmobile2;
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
