//package mobi.puut.services.def;
//
//import mobi.puut.entities.Status;
//import mobi.puut.entities.User;
//import mobi.puut.services.utils.wrappers.UserWrapper;
//
//import javax.ws.rs.*;
//import javax.ws.rs.core.MediaType;
//import java.util.List;
//
//@Path("rest/user")
//public interface IUserService {
//
//
//    // curl -H "Content-Type: application/json" -X POST -d '{"name": "Rio2017"}' http://localhost:8080/rest/user/createUser
//    @POST
//    @Path("createUser")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces({MediaType.APPLICATION_JSON})
//    void saveOrUpdate(User user);
//
//
//    // curl -X GET http://localhost:8080/rest/user/all | json
//    @GET
//    @Path("all")
//    @Produces(MediaType.APPLICATION_JSON)
//    List<UserWrapper> getAllUsers();
//}
