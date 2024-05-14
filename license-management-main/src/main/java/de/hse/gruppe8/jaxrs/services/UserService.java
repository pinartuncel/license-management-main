package de.hse.gruppe8.jaxrs.services;

import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.UserEntity;
import de.hse.gruppe8.util.mapper.UserMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.SecurityContext;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserDao userDao;

    @Inject
    UserMapper userMapper;

    public User getUserFromContext(SecurityContext securityContext) {
        Long user_id = Long.valueOf(securityContext.getUserPrincipal().getName());
        return userMapper.toUser(userDao.getUser(user_id));
    }

    public List<User> getUsers(User currentUser) {
        List<User> users = new ArrayList<>();
        if (currentUser.getIsAdmin()) {
            List<UserEntity> userEntities = userDao.getUsers();
            for (UserEntity userEntity : userEntities) {
                users.add(userMapper.toUser(userEntity));
            }
        } else {
            users.add(currentUser);
        }
        return users;
    }

    public User getUser(User currentUser, Long id) {
        if (currentUser.getIsAdmin() || id.equals(currentUser.getId())) {
            UserEntity userEntity = userDao.getUser(id);
            return userMapper.toUser(userEntity);
        }
        return null;
    }

    public User createUser(User currentUser, User user) {
        if (currentUser.getIsAdmin()) {
            UserEntity userEntity = userDao.save(userMapper.toUserEntity(user));
            return userMapper.toUser(userEntity);
        }
        return null;
    }

    public boolean deleteUser(User currentUser, Long id) {
        if (currentUser.getIsAdmin()) {
            userDao.delete(userDao.getUser(id));
            return true;
        }
        return false;
    }

    public User updateUser(User currentUser, User user) {
        if (currentUser.getIsAdmin()) {
            UserEntity userEntity = userDao.getUser(user.getId());
            userEntity = userMapper.mapUserEntityWithUser(userEntity, user);
            userEntity = userDao.save(userEntity);
            return userMapper.toUser(userEntity);
        } else {
            return null;
        }
    }

}
