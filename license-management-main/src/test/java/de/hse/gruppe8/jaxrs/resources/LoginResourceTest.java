package de.hse.gruppe8.jaxrs.resources;


import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.UserEntity;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class LoginResourceTest {

    @Inject
    UserDao userDao;

    @BeforeEach
    void clearAllFromDatabase() {
        userDao.removeAll();
    }

    @Test
    void requestLoginTokenWithValidUser() {

        //Given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("admin");
        userEntity.setFirstName("Hans Peter");
        userEntity.setLastName("Yeeeeee");
        userDao.save(userEntity);

        //When
        given().contentType(ContentType.JSON)
                .body("{\"username\":\"admin\",\"password\":\"admin\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .body("firstName", is("Hans Peter"))
                .body("lastName", is("Yeeeeee"));
    }

    @Test
    void requestLoginTokenWithNoneValidUserPassword() {

        //Given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("password");
        userDao.save(userEntity);

        //When
        given().contentType(ContentType.JSON)
                .body("{\"username\":\"admin\",\"password\":\"admin\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(403)
                .body(is("{\"errorMessage\":\"Login incorrect. No user with username \\\"admin\\\" found or password incorrect.\"}"));
    }

    @Test
    void requestLoginTokenWithNoneValidUser() {

        given().contentType(ContentType.JSON)
                .body("{\"username\":\"admin\",\"password\":\"admin\"}")
                .when()
                .post("/login")
                .then()
                .statusCode(403)
                .body(is("{\"errorMessage\":\"Login incorrect. No user with username \\\"admin\\\" found or password incorrect.\"}"));
    }
}