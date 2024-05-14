package de.hse.gruppe8.jaxrs.services;

import de.hse.gruppe8.jaxrs.model.Authentication;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.UserEntity;
import de.hse.gruppe8.util.JwtToken;
import de.hse.gruppe8.util.mapper.UserMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class LoginService {
    @Inject
    UserDao userDao;

    @Inject
    JwtToken jwtToken;

    @Inject
    UserMapper userMapper;

    public User login(Authentication authentication){
        UserEntity userEntity = userDao.login(authentication.getUsername(), authentication.getPassword());
        User user = userMapper.toUser(userEntity);
        user.setJwt(jwtToken.createUserToken(user.getId()));
        return user;
    }
}
