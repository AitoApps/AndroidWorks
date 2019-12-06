package data;

public class PaymentHistory_FeedItem {
    private String paiddate,custid,weekid,weekdate,amount,paidvia,reference,remarks,status;

    public String getPaiddate() {
        return paiddate;
    }

    public void setPaiddate(String paiddate) {
        this.paiddate = paiddate;
    }

    public String getCustid() {
        return custid;
    }

    public void setCustid(String custid) {
        this.custid = custid;
    }

    public String getWeekid() {
        return weekid;
    }

    public void setWeekid(String weekid) {
        this.weekid = weekid;
    }

    public String getWeekdate() {
        return weekdate;
    }

    public void setWeekdate(String weekdate) {
        this.weekdate = weekdate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaidvia() {
        return paidvia;
    }

    public void setPaidvia(String paidvia) {
        this.paidvia = paidvia;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
