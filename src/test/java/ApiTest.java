import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @BeforeEach
    void setup() {
        // Make base URL configurable via system property, with a default
        RestAssured.baseURI = System.getProperty(
                "baseUrl",
                "https://jsonplaceholder.typicode.com"
        );
    }

    @Test
    void testGetRequest() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("userId", equalTo(1));
    }

    @Test
    void testPostRequest() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "Jonas test");
        body.put("body", "Practicing a POST request");
        body.put("userId", 7);

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("Jonas test"))
                .body("userId", equalTo(7));
    }

    @Test
    void testPutRequest() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 1);
        payload.put("title", "Updated Title");
        payload.put("body", "Updated body");
        payload.put("userId", 1);

        given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("body", equalTo("Updated body"));
    }

    @Test
    void testDeleteRequest() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }

    @Test
    void testUnknownEndpointReturns404() {
        given()
                .when()
                .get("/postssss/1")
                .then()
                .statusCode(404);
    }

    @ParameterizedTest
    @CsvSource({"1,1", "2,1", "3,1"})
    void posts_have_expected_user(int postId, int expectedUserId) {
        given()
                .when()
                .get("/posts/{id}", postId)
                .then()
                .statusCode(200)
                .body("userId", equalTo(expectedUserId));
    }
}
