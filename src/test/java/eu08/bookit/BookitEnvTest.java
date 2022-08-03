package eu08.bookit;

import net.serenitybdd.junit5.SerenityTest;
import org.junit.jupiter.api.Test;
import utilities.ConfigReader;

@SerenityTest
public class BookitEnvTest {

    @Test
    public void test1(){
        /*
        !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        In order to change environment which we define inside serenity.conf file
        Go to console in intelliJ
        And write mvn clean verify -Denvironment=bookit3 and then hit Ctrl + enter!!!!!!!!!!!
        Now without changing anything in test cases, it runs in bookit3 environment
         */
        System.out.println(ConfigReader.getProperty("base.url"));
        System.out.println(ConfigReader.getProperty("teacher_email"));
        System.out.println(ConfigReader.getProperty("teacher_password"));
    }
}
