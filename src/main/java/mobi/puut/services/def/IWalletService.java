package mobi.puut.services.def;

import mobi.puut.entities.GenerateWallet;
import mobi.puut.entities.Status;
import mobi.puut.entities.WalletInfo;
import mobi.puut.services.utils.WalletModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


// @produce for get and put
// @produce and @consume for post

@Path("rest/wallet")
public interface IWalletService {

    // curl -X GET http://localhost:8080/rest/wallet/1/statuses | json
    @GET
    @Path("{walletId}/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    List<Status> getWalletStatuses(@PathParam("walletId") final Long id);


    // curl -X GET http://localhost:8080/rest/wallet/1 | json
    @GET
    @Path("{walletId}")
    @Produces(MediaType.APPLICATION_JSON)
    WalletInfo getWalletInfo(@PathParam("walletId") Long walletId);


    // curl -X GET http://localhost:8080/rest/wallet/wallets | json
    @GET
    @Path("wallets")
    @Produces(MediaType.APPLICATION_JSON)
    List<WalletInfo> getAllWallets();


    // curl -H "Content-Type: application/json" -X POST -d '{"walletName": "Arhaus","currencyName":"Bitcoin"}' http://localhost:8080/rest/wallet/generateAddress
    @POST
    @Path("generateAddress")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    WalletInfo generateAddress(GenerateWallet generateWallet);


    // curl -X GET http://localhost:8080/rest/wallet/currencyandname/puut45/Bitcoin | json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("currencyandname/{walletName}/{currencyName}")
    WalletInfo getWalletInfoWithCurrencyAndWalletName(@PathParam("walletName") String walletName,
                                                      @PathParam("currencyName") String currencyName);

    WalletModel sendMoney(final Long walletId, final String amount, final String address);

    WalletModel getWalletModel(final Long walletId);

    void deleteWalletInfoById(Long id);
}
