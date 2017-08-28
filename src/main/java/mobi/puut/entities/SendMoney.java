package mobi.puut.entities;

/*
* the entity for the sending money to the external party
* */
public class SendMoney {

    String address;

    String amount;

    public SendMoney(String address, String amount) {
        this.address = address;
        this.amount = amount;
    }

    public SendMoney() {

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
