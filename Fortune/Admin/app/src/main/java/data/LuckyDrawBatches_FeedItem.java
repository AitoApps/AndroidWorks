package data;

public class LuckyDrawBatches_FeedItem {
    private String sn,batchname,discription,price,customercount,startdate;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getCustomercount() {
        return customercount;
    }

    public void setCustomercount(String customercount) {
        this.customercount = customercount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getBatchname() {
        return batchname;
    }

    public void setBatchname(String batchname) {
        this.batchname = batchname;
    }
}
