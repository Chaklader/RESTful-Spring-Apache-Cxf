package mobi.puut.services.impl;

import mobi.puut.database.def.IStatusData;
import mobi.puut.entities.Status;
import mobi.puut.services.def.IStatusService;
import mobi.puut.services.def.IWalletService;
import mobi.puut.services.utils.WalletModel;
import mobi.puut.services.utils.wrappers.StatusWrapper;
import mobi.puut.services.utils.RestService;
import org.bitcoinj.core.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestService
@Service("statusService")
public class StatusServiceImpl implements IStatusService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IStatusData iStatusData;

    @Autowired
    private IWalletService iWalletService;

    /**
     * get all the transactions history for particular wallet from the blockchain
     *
     * @param id the wallet Id of concern
     * @return
     */
    public List<String> getWalletStatusesFromBlockchainById(Long id) {

        WalletModel walletModel = iWalletService.getWalletModel(id);

        List<String> strings = new ArrayList<>();

        List<Transaction> transactions = walletModel.getTransactions();

        if (Objects.isNull(transactions)) {
            logger.info("\n\nNo transactions history found for the wallet with Id = {}\n\n", id);
            return strings;
        }

        transactions.forEach(transaction -> strings.add(walletModel.addTransactionHistory(transaction)));
        return strings;
    }

    /**
     * get all the transactions history for particular wallet from the database
     *
     * @param id the wallet Id of concern
     * @return
     */
    public List<StatusWrapper> getWalletStatusesByWalletId(Long id) {

        List<Status> statuses = iStatusData.getByWalletId(id);

        List<StatusWrapper> statusWrappers = new ArrayList<>();

        statuses.forEach(status -> statusWrappers.add(
                new StatusWrapper(status.getAddress().toString(),
                        String.valueOf(status.getBalance()),
                        status.getTransaction().toString())));

        return statusWrappers;
    }

    /**
     * get all the statuses (irrespective of user or the wallet Id) from the database
     *
     * @return
     */
    public List<StatusWrapper> getAllStatuses() {
        return iStatusData.getAllStatuses();
    }
}
