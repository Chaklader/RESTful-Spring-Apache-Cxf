package mobi.puut.services.def;

import mobi.puut.entities.Status;
import mobi.puut.entities.WalletInfo;
import mobi.puut.services.utils.WalletModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


// @produce for get and put
// @produce and @consume for post

@Path("/rest/wallet")
public interface IWalletService {

    // curl -X GET http://localhost:8080/api/rest/wallet/1/statuses | json
    @GET
    @Path("/{walletId}/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    List<Status> getWalletStatuses(@PathParam("walletId") final Long id);


    // curl -X GET http://localhost:8080/api/rest/wallet/1 | json
    @GET
    @Path("{walletId}")
    @Produces(MediaType.APPLICATION_JSON)
    WalletInfo getWalletInfo(@PathParam("walletId") Long walletId);

    // curl -X GET http://localhost:8080/api/rest/wallet/wallets | json
    @GET
    @Path("/wallets")
    @Produces(MediaType.APPLICATION_JSON)
    List<WalletInfo> getAllWallets();


    WalletInfo generateAddress(final String walletName, String currencyName);

    WalletInfo getWalletInfoWithCurrencyAndWalletName(String walletName, String currencyName);

    WalletModel getWalletModel(final Long walletId);

    WalletModel sendMoney(final Long walletId, final String amount, final String address);

    void deleteWalletInfoById(Long id);
}
