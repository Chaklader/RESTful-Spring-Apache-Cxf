//package mobi.puut.services.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import mobi.puut.database.def.IUserDao;
//import mobi.puut.services.def.IUserService;
//import mobi.puut.services.utils.wrappers.UserWrapper;
//import mobi.puut.services.utils.RestService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import mobi.puut.entities.User;
//
//
///**
// * Created by Chaklader on 6/19/17.
// */
//@RestService
//@Service("userService")
//public class UserServiceImpl implements IUserService {
//
//    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    public IUserDao userDao;
//
//    public void saveOrUpdate(User user) {
//        userDao.saveOrUpdate(user);
//    }
//
//    public List<UserWrapper> getAllUsers() {
//        List<User> users = userDao.getAllUsers();
//
//        if (Objects.isNull(users)) {
//            return null;
//        }
//
//        List<UserWrapper> userWrappers = new ArrayList<>();
//
//        users.forEach(user -> userWrappers.add(new UserWrapper(user.getName())));
//
//        return userWrappers;
//    }
//}
//
