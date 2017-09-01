package mobi.puut.entities;

public class GenerateWallet {

    String currencyName;

    public GenerateWallet(String currencyName) {
        this.currencyName = currencyName;
    }

    public GenerateWallet() {
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
}
