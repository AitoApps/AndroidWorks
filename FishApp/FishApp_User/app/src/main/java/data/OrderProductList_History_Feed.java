package data;

public class OrderProductList_History_Feed {
    private String itemname;
    private String qty;
    private String totalamount;

    public OrderProductList_History_Feed() {
    }

    public OrderProductList_History_Feed(String itemname2, String qty2, String totalamount2) {
        itemname = itemname2;
        qty = qty2;
        totalamount = totalamount2;
    }

    public String getitemname() {
        return itemname;
    }

    public void setitemname(String itemname2) {
        itemname = itemname2;
    }

    public String getqty() {
        return qty;
    }

    public void setqty(String qty2) {
        qty = qty2;
    }

    public String gettotalamount() {
        return totalamount;
    }

    public void settotalamount(String totalamount2) {
        totalamount = totalamount2;
    }
}
