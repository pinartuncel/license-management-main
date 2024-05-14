package de.hse.gruppe8.orm;

import de.hse.gruppe8.exception.NoUserFoundException;
import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.UserEntity;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class UserDaoTest {

    @Inject
    UserDao userDao;


    @BeforeEach
    void clearAllFromDatabase() {
        userDao.removeAll();
    }

    @Test
    void checkAddUser() {
        //Given
        UserEntity userEntity = new UserEntity(null, "username", "passord", false, "firstname", "lastName", "email@admin.de", null, null, true, null);

        //When
        userDao.save(userEntity);

        //Then
        assertEquals(1, userDao.getUsers().size());
    }

    @Test
    void checkUpdateUser() {
        //Given
        UserEntity userEntity = new UserEntity(null, "username", "passord", false, "firstname", "lastName", "email@admin.de", null, null, true, null);
        userDao.save(userEntity);
        UserEntity user = userDao.getUsers().get(0);
        final String newName = "andrenNamen";
        //When

        user.setFirstName(newName);
        userDao.save(user);

        //Then
        assertEquals(1, userDao.getUsers().size());
        assertEquals(newName, userDao.getUser(user.getId()).getFirstName());
    }

    @Test
    void checkDeleteUser() {
        //Given
        UserEntity userEntity = new UserEntity(null, "username", "passord", false, "firstname", "lastName", "email@admin.de", null, null, true, null);
        userDao.save(userEntity);
        UserEntity user = userDao.getUsers().get(0);
        //When
        userDao.delete(user);

        //Then
        assertEquals(0, userDao.getUsers().size());
    }


    @Test
    void checkDeleteUserWithNoneUser() {
        //Given
        UserEntity userEntity = new UserEntity(null, "username", "passord", false, "firstname", "lastName", "email@admin.de", null, null, true, null);
        userDao.save(userEntity);
        //When
        userDao.delete(null);

        //Then
        assertEquals(1, userDao.getUsers().size());
    }

    @Test
    void checkLoginWithUser() {
        //Given
        final String username = "username";
        final String password = "password";
        final String firstname = "Firstname";
        final String lastName = "LastName";
        UserEntity userEntity = new UserEntity(null, username, password, false, firstname, lastName, "email@admin.de", null, null, true, null);
        userDao.save(userEntity);

        //When
        UserEntity user = userDao.login(username, password);

        //Then
        assertEquals(username, user.getUsername());
        assertEquals(firstname, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }

    @Test
    void checkLoginWithoutUser() {
        //Given
        final String username = "username";
        final String password = "password";

        //When && Then
        NoUserFoundException exception = assertThrows(NoUserFoundException.class, () -> {
            userDao.login(username, password);
        });

        assertEquals("Login incorrect. No user with username \"username\" found or password incorrect.", exception.getMessage());
    }
}
