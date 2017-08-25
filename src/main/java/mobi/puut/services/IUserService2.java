package mobi.puut.services;

import mobi.puut.entities.User2;

import javax.ws.rs.core.Response;
import java.util.Collection;

public interface IUserService2 {

    Collection<User2> getUsers();

    User2 getUser(Integer id);

    Response add(User2 user);
}