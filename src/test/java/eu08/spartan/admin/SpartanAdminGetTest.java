package eu08.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@SerenityTest

public class SpartanAdminGetTest {

    @BeforeAll
    public static void init() { //MUST BE STATIC!!!!!!!!!!!!!!!!
        //save baseurl inside this variable so that we don't need to type each http method.
        baseURI = "http://3.83.123.243:7000";

    }
    @Test
    public void getAllSpartan(){

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
        .when()
                .get("/api/spartans")
        .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void getOneSpartan(){

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}")
                .then()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);

    }
}
