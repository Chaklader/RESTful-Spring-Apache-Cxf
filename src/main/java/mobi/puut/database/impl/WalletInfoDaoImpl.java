package mobi.puut.database.impl;

import mobi.puut.database.def.IWalletInfoDao;
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
public class WalletInfoDaoImpl implements IWalletInfoDao {

    // provide a logger for the class
    private final Logger loggger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    mobi.puut.database.def.IStatusDao IStatusDao;

    @Autowired
    private SessionFactory sessionFactory;

//    @Transactional(rollbackFor = Exception.class)
//    public List<WalletInfo> getAllWallets() {
//        return sessionFactory.getCurrentSession()
//                .createQuery("from WalletInfo").getResultList();
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public WalletInfo getByName(String walletName) {
//        List<WalletInfo> walletInfos = sessionFactory.getCurrentSession().createQuery("from WalletInfo where code = :code")
//                .setParameter("name", walletName).getResultList();
//
//        return Objects.isNull(walletInfos) || walletInfos.isEmpty()
//                ? null : walletInfos.get(0);
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public WalletInfo getById(final Long id) {
//        return sessionFactory.getCurrentSession().get(WalletInfo.class, id);
//    }

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

//    @Transactional(rollbackFor = Exception.class)
//    public void deleteWalletInfoByWalletId(Long walletId) {
//
//        // transaction is recored in the status table with the wallet Id
//        // we cant delete the WalletInfo entity as being used foreign key in the Status table
//        if (IStatusDao.getStatusRetentionInfoByWalletId(walletId)) {
//            loggger.info("\n\nUnable to delete the wallet with id {} as being used foregin key " +
//                    "in the Status table\n\n", walletId);
//            return;
//        }
//
//        sessionFactory.getCurrentSession().createQuery("delete WalletInfo where id = :id")
//                .setParameter("id", walletId).executeUpdate();
//    }

//    @Transactional(rollbackFor = Exception.class)
//    public WalletInfo getWalletInfoWithWalletNameAndCurrency(String code, String currencyName) {
//
//        List<WalletInfo> walletInfos = sessionFactory.getCurrentSession()
//                .createQuery("from WalletInfo where code = :code and currency = :currency")
//                .setParameter("code", code)
//                .setParameter("currency", currencyName).getResultList();
//
//        return Objects.isNull(walletInfos) || walletInfos.isEmpty() ?
//                null : walletInfos.get(0);
//    }
}
