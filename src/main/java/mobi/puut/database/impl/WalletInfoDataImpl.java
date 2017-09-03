package mobi.puut.database.impl;

import mobi.puut.database.def.IStatusData;
import mobi.puut.database.def.IWalletInfoData;
import mobi.puut.entities.WalletInfo;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * Created by Chaklader on 6/24/17.
 */
@Repository
public class WalletInfoDataImpl implements IWalletInfoData {

    // provide a logger for the class
    private final Logger loggger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStatusData iStatusData;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(rollbackFor = Exception.class)
    public List<WalletInfo> getAllWallets() {
        return sessionFactory.getCurrentSession()
                .createQuery("from WalletInfo").getResultList();
    }

    @Transactional(rollbackFor = Exception.class)
    public WalletInfo getById(final Long id) {
        return sessionFactory.getCurrentSession().get(WalletInfo.class, id);
    }

    /**
     * @param code    name of the wallet
     * @param address address of the wallet
     * @return return the created WalletInfo object with provided name and address
     */
    @Transactional(rollbackFor = Exception.class)
    public WalletInfo create(String code, String currency, String address) {

        // saveOrUpdate the WalletInfo entity with provided name and address
        WalletInfo walletInfo = new WalletInfo();
        walletInfo.setAddress(address);
        walletInfo.setCode(code);
        walletInfo.setCurrency(currency);

        // persist the created instance into the database
        sessionFactory.getCurrentSession().persist(walletInfo);
        return walletInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteWalletInfoByWalletId(Long walletId) {

        if (iStatusData.getStatusRetentionInfoByWalletId(walletId)) {
            return;
        }

        sessionFactory.getCurrentSession().createQuery("delete WalletInfo where id = :id")
                .setParameter("id", walletId).executeUpdate();
    }


    @Transactional(rollbackFor = Exception.class)
    public WalletInfo getWalletInfoByCurrencyAndAddress(String currency, String address) {

        // How to make sure the currency String matching is not based on the small and capital letters?

        List<WalletInfo> walletInfos = sessionFactory.getCurrentSession()
                .createQuery("from WalletInfo where currency = :currency and address = :address")
                .setParameter("currency", currency)
                .setParameter("address", address).getResultList();

        return Objects.isNull(walletInfos) || walletInfos.isEmpty() ?
                null : walletInfos.get(0);
    }
}
