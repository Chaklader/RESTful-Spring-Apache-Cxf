package mobi.puut.database.def;

import mobi.puut.entities.WalletInfo;

import java.util.List;


public interface IWalletInfoData {

    List<WalletInfo> getAllWallets();

    WalletInfo getById(final Long id);

    WalletInfo create(String code, String currency, String address);

    void deleteWalletInfoByWalletId(Long walletId);

    WalletInfo getWalletInfoByCurrencyAndAddress(String currency, String address);
}
