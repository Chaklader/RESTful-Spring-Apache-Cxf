package mobi.puut.services.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import mobi.puut.database.def.IUserDao;
import mobi.puut.database.def.IWalletInfoDao;
import mobi.puut.entities.*;
import mobi.puut.services.utils.RandomString;
import mobi.puut.services.utils.WalletManager;
import mobi.puut.services.utils.WalletModel;
import mobi.puut.services.def.IWalletService;
import mobi.puut.services.utils.RestService;
import org.bitcoinj.core.*;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static mobi.puut.services.utils.WalletManager.networkParameters;

/**
 * Created by Chaklader on 6/24/17.
 */
@Service("walletService")
@RestService
public class WalletServiceImpl implements IWalletService {

    // class to generate random file name
    RandomString randomString = new RandomString();

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    @Autowired
//    private IUserDao iUserDao;
//
//    @Autowired
//    private IStatusDao iStatusDao;

    @Autowired
    private IWalletInfoDao iWalletInfoDao;

//    private Map<String, WalletManager> genWalletMap = new ConcurrentHashMap<>();


    private Map<Long, WalletManager> walletMangersMap = new ConcurrentHashMap<>();


    /**
     * get the WalletInfo from the wallet Id
     *
     * @param walletId the wallet Id used to get the WalletInfo entity
     * @return the WalletInfo entity
     */
    public WalletInfo getWalletInfo(Long walletId) {
        WalletInfo walletInfo = iWalletInfoDao.getById(walletId);
        return walletInfo;
    }

    /**
     * get the statuses (transactions) from the wallet Id
     *
     * @param id the Wallet id
     * @return
     */
//    public List<Status> getWalletStatuses(final Long id) {
//        return iStatusDao.getByWalletId(id);
//    }

    /**
     * @return return all the walletInfo as list
     */
    public List<WalletInfo> getAllWallets() {

        List<WalletInfo> walletInfos = iWalletInfoDao.getAllWallets();
        return walletInfos;
    }


    /**
     * take wallet name and the ccurrency as input parameter and
     * generate WalletInfo entity for the respective parameters
     *
     * @param generateWallet
     * @return
     */
    public synchronized WalletInfo generateAddress(GenerateWallet generateWallet) {

        CountDownLatch finshedSetup = new CountDownLatch(1);

        final WalletInfo walletInfo = new WalletInfo();

        String fileDirectoryName = randomString.nextString();

        String currencyName = generateWallet.getCurrencyName();

        String currency = currencyName.toUpperCase();

        switch (currency) {

            case "BITCOIN":

                try {
                    final WalletManager walletManager = WalletManager.setupWallet(fileDirectoryName);

                    walletManager.addWalletSetupCompletedListener((wallet) -> {

                        Address address = wallet.currentReceiveAddress();
                        WalletInfo newWallet = createWalletInfo("BTC", currencyName, address.toString());

                        // set the properties of the walletInfo
                        walletInfo.setId(newWallet.getId());
                        walletInfo.setCode(newWallet.getCode());
                        walletInfo.setAddress(newWallet.getAddress());
                        walletInfo.setCurrency(newWallet.getCurrency());

                        walletMangersMap.put(newWallet.getId(), walletManager);
//                        genWalletMap.remove(fileDirectoryName);

                        // start the count down
                        finshedSetup.countDown();
                    });

//                    genWalletMap.put(fileDirectoryName, walletManager);

                    // wait for the completion of the thread
                    finshedSetup.await();
                    return walletInfo;
                } catch (InterruptedException ex) {

                }
                break;

            case "ETHEREUM":
                break;

            default:
                break;
        }

//        if (currency.equals("BITCOIN")) {
//
//            try {
//                final WalletManager walletManager = WalletManager.setupWallet(fileDirectoryName);
//
//                walletManager.addWalletSetupCompletedListener((wallet) -> {
//
//                    Address address = wallet.currentReceiveAddress();
//                    WalletInfo newWallet = createWalletInfo("BTC", currencyName, address.toString());
//
//                    // set the properties of the walletInfo
//                    walletInfo.setId(newWallet.getId());
//                    walletInfo.setCode(newWallet.getCode());
//                    walletInfo.setAddress(newWallet.getAddress());
//                    walletInfo.setCurrency(newWallet.getCurrency());
//
//                    walletMangersMap.put(newWallet.getId(), walletManager);
//                    genWalletMap.remove(fileDirectoryName);
//
//                    // start the count down
//                    finshedSetup.countDown();
//                });
//
//                genWalletMap.put(fileDirectoryName, walletManager);
//
//                // wait for the completion of the thread
//                finshedSetup.await();
//                return walletInfo;
//            } catch (InterruptedException ex) {
//
//            }
//        } else if (currency.equals("ETHEREUM")) {
//            return walletInfo;
//        } else {
//            return walletInfo;
//        }

        return null;
    }


//    public WalletInfo getWalletInfoWithCurrencyAndWalletName(String walletName, String currencyName) {
//        return iWalletInfoDao.getWalletInfoWithWalletNameAndCurrency(walletName, currencyName);
//    }

    /**
     * get the wallet model with the provided ID
     *
     * @param walletId
     * @return
     */
    public WalletModel getWalletModel(final Long walletId) {

        WalletManager walletManager = getWalletManager(walletId);
        WalletModel model = walletManager == null ? null : walletManager.getModel();

        if (Objects.isNull(model)) {
            logger.info("Wallet with the Id {} is not available", walletId);
        }

        return model;
    }

    /**
     * Send money from the wallet using the wallet name, address and amount
     *
     * @param walletId  takes the wallet name
     * @param sendMoney
     * @return return wallet model with subtracted transaction amount
     */

//    public WalletModel sendMoney(final Long walletId, final SendMoney sendMoney) {
//
//        String address = sendMoney.getAddress();
//
//        String amount = sendMoney.getAmount();
//
//        User user = getCurrentUser();
//
//        WalletModel model = null;
//
//        WalletManager walletManager = getWalletManager(walletId);
//
//        if (walletManager != null) {
//
//            Wallet wallet = walletManager.getBitcoin().wallet();
//
//            if (Objects.isNull(wallet)) {
//                return model;
//            }
//
//            send(user, walletId, wallet, address, amount);
//            model = walletManager.getModel();
//        }
//        return model;
//    }

    /**
     * take the amount as Stirng and parse it as Satoshi coin
     *
     * @param amountStr wallet money amount as String
     * @return
     */
//    protected Coin parseCoin(final String amountStr) {
//
//        try {
//            Coin amount = Coin.parseCoin(amountStr);
//            if (amount.getValue() <= 0) {
//                throw new IllegalArgumentException("Invalid amount: " + amountStr);
//            }
//            return amount;
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid amount: " + amountStr);
//        }
//    }

    /**
     * Execute the sending from the user to the external wallet
     *
     * @param user      wallet user
     * @param walletId  wallet ID for the user
     * @param wallet    wallet belongs to the user
     * @param address   external address to send to BTC
     * @param amountStr amount to send to the external user
     */
//    protected void send(final User user, final Long walletId, final Wallet wallet,
//                        final String address, final String amountStr) {
//
//        // Address exception cannot happen as we validated it beforehand.
//        Coin balance = wallet.getBalance();
//
//        try {
//            Coin amount = parseCoin(amountStr);
//            Address destination = Address.fromBase58(networkParameters, address);
//
//            SendRequest req;
//            if (amount.equals(balance))
//                req = SendRequest.emptyWallet(destination);
//            else
//                req = SendRequest.to(destination, amount);
//
//            Wallet.SendResult sendResult = wallet.sendCoins(req);
//
//            Futures.addCallback(sendResult.broadcastComplete, new FutureCallback<Transaction>() {
//                @Override
//                public void onSuccess(@Nullable Transaction result) {
//                    String message = result.toString();
//                    saveTransaction(user, walletId, address, message, balance);
//                }
//
//                @Override
//                public void onFailure(Throwable t) {
//                    String error = "Could send money. " + t.getMessage();
//                    saveTransaction(user, walletId, address, error, balance);
//                }
//            });
//            sendResult.tx.getConfidence().addEventListener((tx, reason) -> {
//                if (reason == TransactionConfidence.Listener.ChangeReason.SEEN_PEERS) {
//                    //todo
//                }
//            });
//        } catch (InsufficientMoneyException e) {
//            String error = "Could not empty the wallet. " +
//                    "You may have too little money left in the wallet to make a transaction.";
//            saveTransaction(user, walletId, address, error, balance);
//
//        } catch (ECKey.KeyIsEncryptedException e) {
//            String error = "Could send money. " + "Remove the wallet password protection.";
//            saveTransaction(user, walletId, address, error, balance);
//        } catch (AddressFormatException e) {
//            String error = "Could send money. Invalid address: " + e.getMessage();
//            saveTransaction(user, walletId, address, error, balance);
//        } catch (Exception e) {
//            String error = "Could send money. " + e.getMessage();
//            saveTransaction(user, walletId, address, error, balance);
//        }
//    }

    /**
     * find the wallet manager with provided ID
     *
     * @param id
     * @return
     */
    public synchronized WalletManager getWalletManager(final Long id) {

        WalletManager walletManager = walletMangersMap.get(id);

        if (walletManager == null) {

//            WalletInfo walletInfo = iWalletInfoDao.getById(id);
            WalletInfo walletInfo = null;

            if (walletInfo != null) {
                String name = walletInfo.getCode();
                walletManager = WalletManager.setupWallet(name);
                walletMangersMap.put(walletInfo.getId(), walletManager);
            }
        }
        return walletManager;
    }

    /**
     * @return return the user of concern
     */
//    protected User getCurrentUser() {
//        User user = iUserDao.getById(1); //TODO
//        return user;
//    }

    /**
     * saveOrUpdate instances in the wallet_info table with the wallet name and the address
     *
     * @param code
     * @param address
     * @return
     */
    protected WalletInfo createWalletInfo(final String code, final String currency, final String address) {
        return iWalletInfoDao.create(code, currency, address);
    }

    /**
     * save the transaction statuses to the status database table
     *
     * @param user
     * @param walletId
     * @param address  external address to send the transactions
     * @param message
     * @param balance
     * @return the generated status instance
     */
//    protected Status saveTransaction(final User user, final Long walletId, final String address,
//                                     final String message, final Coin balance) {
//        Status status = new Status();
//        status.setAddress(address.length() > 90 ? address.substring(0, 89) : address);
//
//        status.setUser_id(user.getId());
//        status.setWallet_id(walletId);
//        status.setTransaction(message.length() > 90 ? message.substring(0, 89) : message);
//        status.setBalance(balance.getValue());
//        return iStatusDao.saveStatus(status);
//    }

//    public void deleteWalletInfoById(Long id) {
//        iWalletInfoDao.deleteWalletInfoByWalletId(id);
//    }

//    public String getWalletBalanceById(final long id) {
//
//        WalletModel walletModel = getWalletModel(id);
//        String balance = String.valueOf(walletModel.getBalance());
//
//        return balance;
//    }

//    public String getWalletsCount() {
//
//        List<WalletInfo> walletInfos = iWalletInfoDao.getAllWallets();
//        return String.valueOf(walletInfos.size());
//    }


    // TODO
    // write a RESTful method for the receiving operations

    // TODO
    // write a RESTful method using the wallet id to get all the info's from the status table

    // TODO
    // get the list of the transactions for the particular user

    // TODO
    // Wire out every info of an wallet
    // Update the delete method to provide an option for the hard delete
    // so, even if the wallet has child tables e.g Status, everything will be deleted
}
