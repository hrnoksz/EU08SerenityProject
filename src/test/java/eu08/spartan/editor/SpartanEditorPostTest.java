package eu08.spartan.editor;
import io.restassured.http.ContentType;
import net.serenitybdd.junit5.SerenityTest;
import net.serenitybdd.rest.Ensure;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import utilities.SpartanNewBase;
import utilities.SpartanUtil;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static net.serenitybdd.rest.SerenityRest.given;

@Disabled
@SerenityTest
public class SpartanEditorPostTest extends SpartanNewBase {

    @DisplayName("Editor should be able to POST")
    @Test
    public void postSpartanAsEditor(){

        //When you need deserialize or serialize, you don't need to add
        //separate dependency, it comes with serenity!!!!!!!!!!!!!!!!!!!!!!!!!
        //create one spartan using util
        Map<String, Object> bodyMap = SpartanUtil.getRandomSpartanMap();

        System.out.println("bodyMap = " + bodyMap);

        //send a post request as editor
        given()
                .auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
        .when()
                .post("/spartans")
        .then()
                .log().all();

         /*
                status code is 201
                content type is Json
                success message is A Spartan is Born!
                id is not null
                name is correct
                gender is correct
                phone is correct
                check location header ends with newly generated id
         */

        // status code is 201
        Ensure.that("status code is 201", vRes -> vRes.statusCode(201));

        //content type is Json
        Ensure.that("Content type is JSON", vRes -> vRes.contentType(ContentType.JSON));

        // success message is A Spartan is Born!
        Ensure.that("A Spartan is correct", vR->vR.body("success", is("A Spartan is Born!")));

        // id is not null
        Ensure.that("id is nut null", vR -> vR.body("data.id", notNullValue()));

        // name is correct
        Ensure.that("name is correct", vR -> vR.body("data.name", is(bodyMap.get("name"))));

        // gender is correct
        Ensure.that("Gender is correct", vR -> vR.body("data.gender", is(bodyMap.get("gender"))) );

        // phone is correct
        Ensure.that("Phone is correct", vR -> vR.body("data.phone", is(bodyMap.get("phone"))) );

        //check location header ends with newly generated id
        // get id and save
        String id = lastResponse().jsonPath().getString("data.id");

        Ensure.that("check location header ends with newly generated id",
                vR->vR.header("Location", endsWith(id)));


    }
    /*
           !!!!!!!!!!!!!!!!!!!!!!!
           we can give name to each execution using name = ""
           and if you want to get index of iteration we can use {index}
           and also if you to include parameter in your test name
           {0},{1},{2} --> based on the order you provide as argument.
        */
    @ParameterizedTest(name = "New Spartan {index} - name: {0}")
    @CsvFileSource(resources = "/SpartanDataPOST.csv", numLinesToSkip = 1)
    //numLinesToSkip = 1 is used to skip first row which consists of name, gender and phone
    public void postSpartanWithCsv(String name, String gender, long phone){

        System.out.println("name = " + name);
        System.out.println("gender = " + gender);
        System.out.println("phone = " + phone);

        Map<String, Object> bodyMap = new LinkedHashMap<>();
        bodyMap.put("name", name);
        bodyMap.put("gender", gender);
        bodyMap.put("phone", phone);

        System.out.println("bodyMap = " + bodyMap);

        //send a post request as editor
        given()
                .auth().basic("editor", "editor")
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(bodyMap)
                .log().body()
                .when()
                .post("/spartans")
                .then()
                .log().all();

        // status code is 201
        Ensure.that("status code is 201", vRes -> vRes.statusCode(201));

        //content type is Json
        Ensure.that("Content type is JSON", vRes -> vRes.contentType(ContentType.JSON));

        // success message is A Spartan is Born!
        Ensure.that("A Spartan is correct", vR->vR.body("success", is("A Spartan is Born!")));

        // id is not null
        Ensure.that("id is nut null", vR -> vR.body("data.id", notNullValue()));

        // name is correct
        Ensure.that("name is correct", vR -> vR.body("data.name", is(bodyMap.get("name"))));

        // gender is correct
        Ensure.that("Gender is correct", vR -> vR.body("data.gender", is(bodyMap.get("gender"))) );

        // phone is correct
        Ensure.that("Phone is correct", vR -> vR.body("data.phone", is(bodyMap.get("phone"))) );

        //check location header ends with newly generated id
        // get id and save
        String id = lastResponse().jsonPath().getString("data.id");

        Ensure.that("check location header ends with newly generated id",
                vR->vR.header("Location", endsWith(id)));


    }


}
