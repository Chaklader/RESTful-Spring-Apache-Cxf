package mobi.puut.services.impl;

import java.util.List;
import java.util.Objects;

import mobi.puut.database.def.IUserDao;
import mobi.puut.services.def.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mobi.puut.entities.User;


/**
 * Created by Chaklader on 6/19/17.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

    @Autowired
    public IUserDao userDao;

    public List<User> getCurrentStatuses() {
        return userDao.getAllUsers();
    }

    public void create(User user) {
        userDao.saveOrUpdate(user);
    }

    public List<User> getAllUsers() {
        List<User> users = userDao.getAllUsers();

        if (Objects.isNull(users)) {
            return null;
        }
        return users;
    }
}

