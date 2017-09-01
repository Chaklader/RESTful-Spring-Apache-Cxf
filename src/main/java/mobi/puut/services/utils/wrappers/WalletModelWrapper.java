package mobi.puut.services.utils.wrappers;

public class WalletModelWrapper {

    String address;

    String balance;

    String userId;

    public WalletModelWrapper(String address, String balance, String userId) {
        this.address = address;
        this.balance = balance;
        this.userId = userId;
    }

    public WalletModelWrapper(String address, String balance) {
        this.address = address;
        this.balance = balance;
    }

    public WalletModelWrapper() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
