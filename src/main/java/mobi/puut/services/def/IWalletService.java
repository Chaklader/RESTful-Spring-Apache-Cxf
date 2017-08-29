package mobi.puut.services.def;

import mobi.puut.entities.GenerateWallet;
import mobi.puut.entities.SendMoney;
import mobi.puut.entities.Status;
import mobi.puut.entities.WalletInfo;
import mobi.puut.services.utils.WalletModel;
import mobi.puut.services.utils.wrappers.WalletInfoWrapper;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;


// @produce for get and put
// @produce and @consume for post

@Path("rest/wallet")
public interface IWalletService {


    // curl -X GET http://localhost:8080/rest/wallet/1 | json
    @GET
    @Path("{walletId:[\\d]+}")
    @Produces(MediaType.APPLICATION_JSON)
    WalletInfoWrapper getWalletInfo(@PathParam("walletId") Long walletId);


    // curl -X GET http://localhost:8080/rest/wallet/wallets | json
    @GET
    @Path("wallets")
    @Produces(MediaType.APPLICATION_JSON)
    List<WalletInfoWrapper> getAllWallets();


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


    // curl -H "Content-Type: application/json" -X POST -d '{"amount":"0","address":"mwCwTceJvYV27KXBc3NJZys6CjsgsoeHmf"}' http://localhost:8080/rest/wallet/sendMoney/9
    @POST
    @Path("sendMoney/{walletId:[\\d]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    WalletModel sendMoney(@PathParam("walletId") final Long walletId, final SendMoney sendMoney);


    // curl -X DELETE http://localhost:8080/rest/wallet/delete/9
    @DELETE
    @Path("delete/{walletId:[\\d]+}")
    void deleteWalletInfoById(@PathParam("walletId") Long id);

    WalletModel getWalletModel(final Long walletId);
}
