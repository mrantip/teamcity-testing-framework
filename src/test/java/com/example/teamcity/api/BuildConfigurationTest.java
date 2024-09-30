package com.example.teamcity.api;

import com.example.teamcity.api.models.User;
import com.example.teamcity.api.spec.Specifications;
import io.restassured.RestAssured;
import org.testng.annotations.Test;
import org.apache.http.HttpStatus;


public class BuildConfigurationTest extends BaseApiTest{
    @Test
    public void buildConfiguration() {
        var user = User.builder()
                .username("mrantip")
                .password("4554447")
                .build();

        var token = RestAssured
                .given()
                .spec(Specifications.getSpec().authSpec(user))
                .get("/authenticationTest.html?crsf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
        System.out.println(token);

    }
}
