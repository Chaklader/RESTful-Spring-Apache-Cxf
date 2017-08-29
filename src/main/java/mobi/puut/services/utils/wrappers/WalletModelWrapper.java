package mobi.puut.services.utils.wrappers;

public class WalletModelWrapper {

    String address;

    String balance;

    public WalletModelWrapper(String address, String balance) {
        this.address = address;
        this.balance = balance;
    }

    public WalletModelWrapper() {
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
}
