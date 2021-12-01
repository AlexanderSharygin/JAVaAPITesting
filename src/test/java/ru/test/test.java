package ru.test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.logging.FileHandler;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class test {

    @BeforeEach
    public void setup()
    {
        RestAssured.baseURI="https://qa-mesto.praktikum-services.ru";
    }

    //GET
    @Test
    public void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given().auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTZkYTY5YzMyOTg5YzAwN2Q2M2EyOGUiLCJpYXQiOjE2MzgzNTc2NzIsImV4cCI6MTYzODk2MjQ3Mn0.hIpkpOizi90dIvhnJLQlWTHz9yCTsvEhWOqccRRBKfE").
                get("/api/users/me").then().statusCode(200);

    }
    @Test
    public void checkUserName() {
        given().auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTZkYTY5YzMyOTg5YzAwN2Q2M2EyOGUiLCJpYXQiOjE2MzgzNTc2NzIsImV4cCI6MTYzODk2MjQ3Mn0.hIpkpOizi90dIvhnJLQlWTHz9yCTsvEhWOqccRRBKfE").
                get("/api/users/me").then().assertThat().body("data.name", equalTo("Аристарх Сократович"));
    }

    //POST
    @Test
    public void createNewPlaceAndCheckResponse(){
        File json = new File("src/test/resources/newCard.json");
        Response response = given().header("Content-type", "application/json").
                auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTZkYTY5YzMyOTg5YzAwN2Q2M2EyOGUiLCJpYXQiOjE2MzgzNTc2NzIsImV4cCI6MTYzODk2MjQ3Mn0.hIpkpOizi90dIvhnJLQlWTHz9yCTsvEhWOqccRRBKfE").
                and().body(json).when().post("/api/cards");
        response.then().assertThat().body("data._id", notNullValue()).
                and().statusCode(201);
    }

//PATCH
    @Test
    public void updatePlaceAndCheckResponse(){
        File json = new File("src/test/resources/editProfile.json");
        Response response = given().header("Content-type", "application/json; charset=utf-8").
                auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MTZkYTY5YzMyOTg5YzAwN2Q2M2EyOGUiLCJpYXQiOjE2MzgzNTc2NzIsImV4cCI6MTYzODk2MjQ3Mn0.hIpkpOizi90dIvhnJLQlWTHz9yCTsvEhWOqccRRBKfE").
                and().body(json).when().patch("/api/users/me");
        response.then().assertThat().body("data.name", notNullValue()).statusCode(200);
    }
}
