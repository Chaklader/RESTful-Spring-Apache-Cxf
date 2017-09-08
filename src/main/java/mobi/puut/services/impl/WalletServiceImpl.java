package mobi.puut.services.impl;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import mobi.puut.database.def.IStatusData;
import mobi.puut.database.def.IUserData;
import mobi.puut.database.def.IWalletInfoData;
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

    @Autowired
    private IUserData iUserData;

    @Autowired
    private IStatusData iStatusData;

    @Autowired
    private IWalletInfoData iWalletInfoData;

    private Map<Long, WalletManager> walletMangersMap = new ConcurrentHashMap<>();


    /**
     * get the WalletInfo from the wallet Id
     *
     * @param walletId the wallet Id used to get the WalletInfo entity
     * @return the WalletInfo entity
     */
    public WalletInfo getWalletInfo(Long walletId) {
        WalletInfo walletInfo = iWalletInfoData.getById(walletId);
        return walletInfo;
    }

    /**
     * get the statuses (transactions) from the wallet Id
     *
     * @param id the Wallet id
     * @return
     */
    public List<Status> getWalletStatuses(final Long id) {
        return iStatusData.getByWalletId(id);
    }

    /**
     * @return return all the walletInfo as list
     */
    public List<WalletInfo> getAllWallets() {
        List<WalletInfo> walletInfos = iWalletInfoData.getAllWallets();
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

        // make in capital order and remove all the white space and invisible characters like "\n"
        String currency = currencyName.toUpperCase().replaceAll("\\s+", "");

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

                        // start the count down
                        finshedSetup.countDown();
                    });

                    // wait for the completion of the thread
                    finshedSetup.await();
                    return walletInfo;
                } catch (InterruptedException ex) {

                }
                break;

            case "ETHEREUM":
                break;

            case "LITECOIN":
                break;

            case "BITCOINCASH":
                break;

            default:
                break;
        }
        return null;
    }

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

    public WalletModel sendMoney(final Long walletId, final SendMoney sendMoney) {

        // external address to send out the moeny
        String address = sendMoney.getAddress();

        String amount = sendMoney.getAmount();

        User user = getCurrentUser();

        WalletModel model = null;

        WalletManager walletManager = getWalletManager(walletId);

        if (walletManager != null) {

            Wallet wallet = walletManager.getBitcoin().wallet();

            if (Objects.isNull(wallet)) {
                return model;
            }

            send(user, walletId, wallet, address, amount);
            model = walletManager.getModel();
        }

        // use WalletModelWrapper here instead of the walletModel

        return model;
    }


    // store the info for the receiving transactions
    public Status storeReceivedMoney(final Long walletId) {

        User user = getCurrentUser();

        WalletModel model = null;

        WalletManager walletManager = getWalletManager(walletId);

        if (walletManager != null) {

            Wallet wallet = walletManager.getBitcoin().wallet();

            if (Objects.isNull(wallet)) {
                return null;
            }

            model = walletManager.getModel();

//            if (model.isLastTransactionReceiving()) {
//
//                Address address = model.getAddress();
//                Coin balance = model.getBalance();
//
//                Status status = saveTransaction(user, walletId, address.toString(),
//                        model.addTransactionHistory(model.getTransaction()), balance);
//
//                return status;
//            }


            // we post from the client after knowing that the last transaction is receiving
            Address address = model.getAddress();

            Coin balance = model.getBalance();

            // save the receiving transaction and return it
            Status status = saveTransaction(user, walletId, address.toString(),
                    model.addTransactionHistory(model.getTransaction()), balance);

            return status;
        }

        return null;
    }


    /**
     * take the amount as Stirng and parse it as Satoshi coin
     *
     * @param amountStr wallet money amount as String
     * @return
     */
    protected Coin parseCoin(final String amountStr) {

        try {

            Coin amount = Coin.parseCoin(amountStr);
            if (amount.getValue() <= 0) {
                throw new IllegalArgumentException("Invalid amount: " + amountStr);
            }
            return amount;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid amount: " + amountStr);
        }
    }

    /**
     * Execute the sending from the user to the external wallet
     *
     * @param user      wallet user
     * @param walletId  wallet ID for the user
     * @param wallet    wallet belongs to the user
     * @param address   external address to send to BTC
     * @param amountStr amount to send to the external user
     */
    protected void send(final User user, final Long walletId, final Wallet wallet,
                        final String address, final String amountStr) {

        // Address exception cannot happen as we validated it beforehand.
        Coin balance = wallet.getBalance();

        try {
            Coin amount = parseCoin(amountStr);
            Address destination = Address.fromBase58(networkParameters, address);

            SendRequest req = null;
            if (amount.isGreaterThan(balance)) {

            } else if (amount.equals(balance)) {
                req = SendRequest.emptyWallet(destination);

            } else {
                req = SendRequest.to(destination, amount);
            }

            Wallet.SendResult sendResult = wallet.sendCoins(req);

            Futures.addCallback(sendResult.broadcastComplete, new FutureCallback<Transaction>() {
                @Override
                public void onSuccess(@Nullable Transaction result) {
                    String message = result.toString();
                    saveTransaction(user, walletId, address, message, balance);
                }

                @Override
                public void onFailure(Throwable t) {
                    String error = "Could send money. " + t.getMessage();
                    saveTransaction(user, walletId, address, error, balance);
                }
            });
            sendResult.tx.getConfidence().addEventListener((tx, reason) -> {
                if (reason == TransactionConfidence.Listener.ChangeReason.SEEN_PEERS) {
                    //todo
                }
            });
        } catch (InsufficientMoneyException e) {
            String error = "Could not empty the wallet. " +
                    "You may have too little money left in the wallet to make a transaction.";
            saveTransaction(user, walletId, address, error, balance);

        } catch (ECKey.KeyIsEncryptedException e) {
            String error = "Could send money. " + "Remove the wallet password protection.";
            saveTransaction(user, walletId, address, error, balance);
        } catch (AddressFormatException e) {
            String error = "Could send money. Invalid address: " + e.getMessage();
            saveTransaction(user, walletId, address, error, balance);
        } catch (Exception e) {
            String error = "Could send money. " + e.getMessage();
            saveTransaction(user, walletId, address, error, balance);
        }
    }

    /**
     * find the wallet manager with provided ID
     *
     * @param id
     * @return
     */
    public synchronized WalletManager getWalletManager(final Long id) {

        WalletManager walletManager = walletMangersMap.get(id);

        if (walletManager == null) {

            WalletInfo walletInfo = iWalletInfoData.getById(id);

            if (walletInfo != null) {

                String code = walletInfo.getCode();
                walletManager = WalletManager.setupWallet(code);
                walletMangersMap.put(walletInfo.getId(), walletManager);
            }
        }
        return walletManager;
    }

    /**
     * @return return the user of concern
     */
    protected User getCurrentUser() {
        User user = iUserData.getById(1); //TODO
        return user;
    }

    /**
     * saveOrUpdate instances in the wallet_info table with the wallet name and the address
     *
     * @param code
     * @param address
     * @return
     */
    protected WalletInfo createWalletInfo(final String code, final String currency, final String address) {
        return iWalletInfoData.create(code, currency, address);
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
    protected Status saveTransaction(final User user, final Long walletId, final String address,
                                     final String message, final Coin balance) {
        Status status = new Status();
        status.setAddress(address.length() > 90 ? address.substring(0, 89) : address);

        status.setUser_id(user.getId());
        status.setWallet_id(walletId);
        status.setTransaction(message.length() > 90 ? message.substring(0, 89) : message);
        status.setBalance(balance.getValue());

        return iStatusData.saveStatus(status);
    }

    /*
    * check if the model is synchronized to the blockchain
    * */
    public boolean isModelSynchronized(Long walletId) {

        WalletModel walletModel = getWalletModel(walletId);

        if (walletModel == null) {
            return false;
        }
        return walletModel.isSyncFinished();
    }

    public void deleteWalletInfoById(Long id) {
        iWalletInfoData.deleteWalletInfoByWalletId(id);
    }

    public String getWalletBalanceById(final long id) {
        WalletModel walletModel = getWalletModel(id);
        String balance = String.valueOf(walletModel.getBalance());

        return balance;
    }

    public WalletInfo getWalletInfoByCurrencyAndAddress(String currencyName, String address) {
        return iWalletInfoData.getWalletInfoByCurrencyAndAddress(currencyName, address);
    }


    /*
    * check if the last wallet transaction is receiving
    * */
    public boolean isReceivingTransaction(final Long walletId) {

        WalletModel model = null;
        WalletManager walletManager = getWalletManager(walletId);

        if (Objects.isNull(walletManager)) {
            model = walletManager.getModel();
        }

        // the model is not synchronized till now,
        if (Objects.isNull(model) || !model.isSyncFinished()) {
            return false;
        }

        Wallet wallet = walletManager.getBitcoin().wallet();

        if (Objects.isNull(wallet)) {
            return false;
        }

        // get the wallet, now check if the last transaction is receiving
        if (model.isLastTransactionReceiving()) {
            return true;
        }

        return false;
    }

    public String getWalletsCount() {
        List<WalletInfo> walletInfos = iWalletInfoData.getAllWallets();
        return String.valueOf(walletInfos.size());
    }

    public String getWalletTransactionsCount(final Long id) {

        WalletModel walletModel = getWalletModel(id);

        if (walletModel != null) {

            // this transaction list come from the blockchain
            List<Transaction> history = walletModel.getTransactions();
            return String.valueOf(history.size());
        }

        return "0";
    }
}
