package mobi.puut.database.impl;

import mobi.puut.database.def.IStatusData;
import mobi.puut.entities.Status;
import mobi.puut.services.utils.wrappers.StatusWrapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Chaklader on 6/24/17.
 */
@Repository
public class StatusDataImpl implements IStatusData {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * @param status takes transaction status save it in the status database table
     * @return return the status object
     */
    @Transactional(rollbackFor = Exception.class)
    public Status saveStatus(final Status status) {
        sessionFactory.getCurrentSession().persist(status);
        return status;
    }

    /**
     * @param walletId takes wallet ID as the input paramenter
     * @return return the list of the statuses
     */
    @Transactional(rollbackFor = Exception.class)
    public List<Status> getByWalletId(final Long walletId) {
        return sessionFactory.getCurrentSession()
                .createQuery("from Status where wallet_id = :walletId")
                .setParameter("walletId", walletId).getResultList();
    }

    // get the status with the wallet Id
    @Transactional(rollbackFor = Exception.class)
    public boolean getStatusRetentionInfoByWalletId(Long id) {

        List<Status> statuses = sessionFactory.getCurrentSession()
                .createQuery("from Status where wallet_id = :id")
                .setParameter("id", id)
                .getResultList();

        return Objects.isNull(statuses) || statuses.isEmpty() ? false : true;
    }

    // get all the current statuses from the all users and the wallets
    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = Exception.class)
    public List<StatusWrapper> getAllStatuses() {
        List<Status> statuses = sessionFactory.getCurrentSession()
                .createQuery("from Status").getResultList();

        List<StatusWrapper> statusWrappers = new ArrayList<>();

        statuses.forEach(status -> statusWrappers.add(new StatusWrapper(
                status.getAddress().toString(),
                String.valueOf(status.getTransaction()),
                status.getTransaction().toString())));

        return statusWrappers;
    }
}
