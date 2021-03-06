package mobi.puut.services.utils;

// import mobi.puut.services.utils.annotationRestService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RestService
@Path("/exception")
public class ExceptionResource {

    public ExceptionResource() {
    }

    // jaxRsServer

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String generateException() throws Exception {
        throw new Exception("generateException from ExceptionResource");
    }
}
