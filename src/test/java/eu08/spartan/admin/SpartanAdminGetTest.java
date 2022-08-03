package eu08.spartan.admin;

import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import net.serenitybdd.rest.SerenityRest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.*;
import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@Disabled
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
                .get("/api/spartans/{id}");
//                .then()
//                .statusCode(200)
//                .and()
//                .contentType(ContentType.JSON);

        //if you send a request using SerenityRest, the response object
        //can be obtained from the method called lastResponse() without being saved separately!!!!!!!!!!!!!!
        //same with Response response object
        System.out.println("statusCode = " + lastResponse().statusCode());

        //print id
        //instead of response.path(), we use lastResponse().path()
        System.out.println("lastResponse().path(\"id\") = " + lastResponse().path("id"));

        //use jsonpath with lastResponse and get the name
        String name = lastResponse().jsonPath().getString("name");
        System.out.println("name = " + name);


    }
    @DisplayName("GET request with Serenity Assertion way")
    @Test
    public void getOneSpartanAssertion(){

        given().accept(ContentType.JSON)
                .and()
                .auth().basic("admin", "admin")
                .pathParam("id", 15)
                .when()
                .get("/api/spartans/{id}");

        //Serenity way of assertion

        Ensure.that("Status code is 200", validatableResponse -> validatableResponse.statusCode(200));

        Ensure.that("Content-type is JSON", vRes ->  vRes.contentType(ContentType.JSON));

        Ensure.that("Id is 15", vRes->vRes.body("id", is(15)));
    }
}
