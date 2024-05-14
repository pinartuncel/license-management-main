package de.hse.gruppe8.jaxrs.resources.security;


import de.hse.gruppe8.orm.dao.UserDao;
import de.hse.gruppe8.orm.model.UserEntity;
import de.hse.gruppe8.util.JwtToken;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
public class SecurityInterceptorTest {


    @Inject
    UserDao userDao;

    @Inject
    JwtToken jwtToken;

    @BeforeEach
    void clearAllFromDatabase() {
        userDao.removeAll();
    }

    @Test
    void requestWithNoJWTToken() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/companies")
                .then()
                .statusCode(401);
    }

    @Test
    void requestWithJWTToken() {
        UserEntity userEntity = new UserEntity(1L, "username", "password", false, "firstname", "lastName", "email@admin.de", null, null, true, null);
        userEntity = userDao.save(userEntity);
        final String token = jwtToken.createUserToken(userEntity.getId());
        
        given().contentType(ContentType.JSON)
                .when()
                .header("Authorization", "Bearer " + token)
                .get("/companies")
                .then()
                .statusCode(200);
    }

    @Test
    void testUserSecurityContext() {
        //Given
        final Long userId = 1337L;

        //When
        UserSecurityContext userSecurityContext = new UserSecurityContext(userId);

        //Then
        assertEquals(userId, Long.valueOf(userSecurityContext.getUserPrincipal().getName()));
        assertFalse(userSecurityContext.isSecure());
        assertFalse(userSecurityContext.isUserInRole("role"));
        assertEquals("Bearer", userSecurityContext.getAuthenticationScheme());

    }

}