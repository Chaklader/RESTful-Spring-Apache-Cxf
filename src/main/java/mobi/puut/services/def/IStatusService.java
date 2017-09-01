package mobi.puut.services.def;

import mobi.puut.entities.Status;
import mobi.puut.services.utils.wrappers.StatusWrapper;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("rest/service")
public interface IStatusService {

    // curl -X GET http://localhost:8080/rest/service/1/statuses | json
    @GET
    @Path("{walletId:[\\d]+}/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    List<StatusWrapper> getWalletStatusesByWalletId(@PathParam("walletId") final Long id);


    // curl -X GET http://localhost:8080/rest/service/1/blockchain/statuses | json
    @GET
    @Path("{walletId:[\\d]+}/blockchain/statuses")
    @Produces(MediaType.APPLICATION_JSON)
    List<String> getWalletStatusesFromBlockchainById(@PathParam("walletId") final Long id);


    // curl -X GET http://localhost:8080/rest/service/statuses | json
    @GET
    @Path("statuses")
    @Produces(MediaType.APPLICATION_JSON)
    List<StatusWrapper> getAllStatuses();
}
