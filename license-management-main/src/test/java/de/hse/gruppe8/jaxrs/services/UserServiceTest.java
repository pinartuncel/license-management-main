package de.hse.gruppe8.jaxrs.services;


import de.hse.gruppe8.jaxrs.model.User;
import de.hse.gruppe8.orm.dao.CompanyDao;
import de.hse.gruppe8.orm.dao.ContractDao;
import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.CompanyEntity;
import de.hse.gruppe8.orm.model.UserEntity;
import de.hse.gruppe8.util.mapper.CompanyMapper;
import de.hse.gruppe8.util.mapper.ContractMapper;
import de.hse.gruppe8.util.mapper.UserMapper;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class UserServiceTest {

    @Inject
    UserService userService;

    @Inject
    UserDao userDao;

    @Inject
    ContractDao contractDao;

    @Inject
    CompanyDao companyDao;

    @Inject
    CompanyMapper companyMapper;

    @Inject
    UserMapper userMapper;

    @Inject
    ContractMapper contractMapper;

    @BeforeEach
    @AfterEach
    void InitDatabase() {
        contractDao.removeAll();
        userDao.removeAll();
        companyDao.removeAll();
    }

    @Test
    void checkGetUsersAsUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        //When
        List<User> users = userService.getUsers(user);

        //Then
        assertEquals(1, users.size());
        assertEquals(userEntity_1.getId(), users.get(0).getId());
    }

    @Test
    void checkGetUsersAsAdmin() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        //When
        List<User> users = userService.getUsers(user);

        //Then
        assertEquals(3, users.size());
    }

    @Test
    void checkGetUserAsUserOwnUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        //When
        User new_users = userService.getUser(user, userEntity_1.getId());

        //Then
        assertEquals(userEntity_1.getId(), new_users.getId());
    }

    @Test
    void checkGetUserAsUserOtherUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        //When
        User new_users = userService.getUser(user, userEntity_2.getId());

        //Then
        assertNull(new_users);
    }

    @Test
    void checkGetUserAsAdminOtherUser() {
        //Given
        CompanyEntity companyEntity1 = companyDao.save(new CompanyEntity(null, "name 1", "department 1", "street 1", "73732", "esslingen", "Germany", true));

        UserEntity userEntity_1 = userDao.save(new UserEntity(null, "username_01", "password", true, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_2 = userDao.save(new UserEntity(null, "username_02", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));
        UserEntity userEntity_3 = userDao.save(new UserEntity(null, "username_03", "password", false, "Hans", "Peter", "test@dd.de", "+49 123", "+49 123", true, companyEntity1));


        User user = userMapper.toUser(userEntity_1);

        //When
        User new_users = userService.getUser(user, userEntity_2.getId());

        //Then
        assertEquals(userEntity_2.getId(), new_users.getId());
    }
}