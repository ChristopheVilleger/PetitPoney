package fr.esgi.demo.domain;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class PhoneControllerTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.port = 9091;
    }

    @Test
    public void should_getAllPhones() {
        given()
                .log().all()
                .when()
                .get("/phone")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .body("[0].stolen", is(true));
    }

    @Test
    public void should_getOnePhone() {
        given()
                .log().all()
                .when()
                .get("/phone/{phoneId}", 1L)
                .then()
                .log().all()
                .statusCode(HttpStatus.FOUND.value());
    }

    @Test
    public void shouldNot_getOnePhoneWhenIdNotFound() {
        given()
                .log().all()
                .when()
                .get("/phone/{phoneId}", 25L)
                .then()
                .log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void should_createPhone() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(" \"serialNumber\":\"5F551B22G312Z3A01B3\", \"number\":\"555-4356\", \"firstname\":\"Edward\" \"lastname\":\"Bloom\" ")
                .when()
                .post("/phone")
                .then()
                .log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void should_updatePhone() {
        given()
                .log().all()
                .when()
                .put("/phone/{serialNumber}", "5F55A01B22G312Z31B")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void shouldNot_updatePhoneWhenSerialNumberNotFound() {
        given()
                .log().all()
                .when()
                .put("/phone/{serialNumber}", "5F55A01B22G312Z31Z")
                .then()
                .log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
