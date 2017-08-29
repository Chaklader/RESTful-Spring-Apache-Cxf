package mobi.puut.services.impl;

import mobi.puut.database.def.IStatusDao;
import mobi.puut.entities.Status;
import mobi.puut.services.def.IStatusService;
import mobi.puut.services.utils.wrappers.StatusWrapper;
import mobi.puut.util.annotation.RestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@RestService
@Service("statusService")
public class StatusServiceImpl implements IStatusService {

    @Autowired
    private IStatusDao iStatusDao;

    @Override
    public List<StatusWrapper> getWalletStatuses(Long id) {

        List<Status> statuses = iStatusDao.getByWalletId(id);

        List<StatusWrapper> statusWrappers = new ArrayList<>();

        statuses.forEach(status -> statusWrappers.add(
                new StatusWrapper(status.getAddress().toString(),
                        String.valueOf(status.getBalance()),
                        status.getTransaction().toString())));

        return statusWrappers;
    }


    @Override
    public List<StatusWrapper> getAllStatuses() {
        return iStatusDao.getAllStatuses();
    }
}
