package mobi.puut.services.utils.wrappers;

public class StatusWrapper {

    String address;

    String balance;

    String transactions;

    public StatusWrapper(String address, String balance, String transactions) {

        this.address = address;
        this.balance = balance;
        this.transactions = transactions;
    }

    public StatusWrapper() {
    }


    public StatusWrapper(String address, String balance) {
        this.address = address;
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }
}

