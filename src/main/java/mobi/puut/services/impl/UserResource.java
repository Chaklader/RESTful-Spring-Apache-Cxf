//package mobi.puut.rest;
//
//import mobi.puut.entities.User2;
//import mobi.puut.services.def.IUserService2;
//import mobi.puut.util.annotation.RestService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.NotFoundException;
//import javax.ws.rs.POST;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//import java.util.Collection;
//
//
//@RestService
//@Path("/users")
//@Produces({MediaType.APPLICATION_JSON})
//@Consumes({MediaType.APPLICATION_JSON})
//public class UserResource {
//
//    private static Logger log = LoggerFactory.getLogger(UserResource.class);
//
//    @Autowired
//    IUserService2 service;
//
//    public UserResource() {
//    }
//
//    @GET
//    public Collection<User2> getUsers() {
//        return service.getUsers();
//    }
//
//    @GET
//    @Path("/{id}")
//    public User2 getUser(@PathParam("id") Integer id) {
//        User2 user = service.getUser(id);
//        if (user == null) {
//            throw new NotFoundException();
//        } else {
//            return user;
//        }
//    }
//
//    @POST
//    public Response add(User2 user) {
//        log.info("Adding user {}", user.getName());
//        service.add(user);
//        return Response.status(Response.Status.OK).build();
//    }
//}
