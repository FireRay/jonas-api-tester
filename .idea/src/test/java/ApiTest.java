import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {

    @Test
    public void testGetRequest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("userId", equalTo(1));
    }

    @Test
    public void testPostRequest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .header("Content-type", "application/json")
                .and()
                .body("{\"title\":\"Jonas test\",\"body\":\"Practicing a POST request\",\"userId\":7}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("Jonas test"))
                .body("userId", equalTo(7));
    }

    @Test
    public void testPutRequest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        given()
                .header("Content-type", "application/json")
                .and()
                .body("{\"id\":1,\"title\":\"Updated Title\",\"body\":\"Updated body\",\"userId\":1}")
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("body", equalTo("Updated body"));
    }

    @Test
    public void testDeleteRequest() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }


}
