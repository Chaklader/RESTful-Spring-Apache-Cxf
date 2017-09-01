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


@Path("rest/wallet")
public interface IWalletService {


    // curl -X GET http://localhost:8080/rest/wallet/wallets | json
    @GET
    @Path("wallets")
    @Produces(MediaType.APPLICATION_JSON)
    List<WalletInfo> getAllWallets();


    //  curl -X GET http://localhost:8080/rest/wallet/1 | json
    @GET
    @Path("{walletId:[\\d]+}")
    @Produces(MediaType.APPLICATION_JSON)
    WalletInfo getWalletInfo(@PathParam("walletId") Long walletId);


    // curl -H "Content-Type: application/json" -X POST -d '{"currencyName":"Bitcoin"}' http://localhost:8080/rest/wallet/generateAddress
    @POST
    @Path("generateAddress")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    WalletInfo generateAddress(GenerateWallet generateWallet);


    // curl -X DELETE http://localhost:8080/rest/wallet/delete/42
    @DELETE
    @Path("delete/{walletId:[\\d]+}")
    void deleteWalletInfoById(@PathParam("walletId") Long id);


    // curl -H "Content-Type: application/json" -X POST -d '{"amount":"0.25","address":"mv4rnyY3Su5gjcDNzbMLKBQkBicCtHUtFB"}' http://localhost:8080/rest/wallet/sendMoney/1
    @POST
    @Path("sendMoney/{walletId:[\\d]+}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    WalletModel sendMoney(@PathParam("walletId") final Long walletId, final SendMoney sendMoney);


    // curl -X GET http://localhost:8080/rest/wallet/balance/1 | json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("balance/{walletId:[\\d]+}")
    String getWalletBalanceById(@PathParam("walletId") final long id);


    // curl -X GET http://localhost:8080/rest/wallet/count | json
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("count")
    String getWalletsCount();

    WalletModel getWalletModel(final Long walletId);

    // TODO
    // write a RESTful method for the receiving operations

    // TODO: 9/1/17
    // remove the annotation @RestService
}
