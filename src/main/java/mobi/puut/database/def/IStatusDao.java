package mobi.puut.database.def;

import mobi.puut.entities.Status;

import java.util.List;

public interface IStatusDao {

    Status saveStatus(final Status status);

    List<Status> getByWalletId(final Long walletId);

    boolean getStatusRetentionInfoByWalletId(Long id);
}
