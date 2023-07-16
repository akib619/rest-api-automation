package utils;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ApiUtils {

    public static Response sendGetRequest(String baseUrl, String endpoint) {
        RestAssured.baseURI = baseUrl;
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint);
    }

    public static RequestSpecification getPostRequest(String baseURI) {
        RestAssured.baseURI = baseURI;
        return RestAssured.given();
    }

    public static Response sendPostRequest(RequestSpecification requestSpecification, String endpoint, Object body) {
        return requestSpecification.contentType(ContentType.JSON)
                                     .body(body)
                                     .post(endpoint);
    }
}

