package de.hse.gruppe8.jaxrs.services;


import de.hse.gruppe8.jaxrs.model.Authentication;
import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.UserEntity;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class LoginServiceTest {

    @Inject
    LoginService loginService;

    @Inject
    UserDao userDao;

    @BeforeEach
    void InitDatabase() {
        userDao.removeAll();
    }


    @Test
    void checkLoginService() {
        //given
        final String username = "username";
        final String password = "password";
        final String firstname = "Firstname";
        final String lastName = "LastName";
        Authentication authentication = new Authentication(username, password);
        UserEntity userEntity = new UserEntity(null, username, password, false, firstname, lastName, "email@admin.de", null, null, true, null);
        userDao.save(userEntity);

        //when
        User user = loginService.login(authentication);

        //then
        assertNotNull(user);
        assertEquals(firstname, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertNotNull(user.getJwt());
        assertTrue(user.getJwt().length() > 0);
    }

}