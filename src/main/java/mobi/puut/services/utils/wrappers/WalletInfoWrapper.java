package mobi.puut.services.utils.wrappers;

public class WalletInfoWrapper {

    String name;

    String address;

    // currency such as bitcoin, ethereum, litecoin, nem, ripple, dash etc
    String currencyName;

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public WalletInfoWrapper() {
    }

    public WalletInfoWrapper(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public WalletInfoWrapper(String name, String address, String currencyName) {
        this.name = name;
        this.address = address;
        this.currencyName = currencyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
