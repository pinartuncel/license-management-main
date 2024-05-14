package de.hse.gruppe8;


import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.wildfly.common.Assert;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

@QuarkusTest
public class ExampleTest {

    @Test
    void true_is_true() {
        //given

        //when

        //then
        Assert.assertTrue(true);
    }

}