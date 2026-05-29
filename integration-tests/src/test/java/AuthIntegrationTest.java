import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsNull.notNullValue;

public class AuthIntegrationTest
{

    @BeforeAll
    static void setup()
    {
        RestAssured.baseURI="http://localhost:4004";
    }

    @Test
    public void shouldReturnOKWithValidToken()
    {
//Inside our test method there are typically 3 steps to create a good test
//        1.Arrange: creating the setup that the test needs to work 100% of the tym, like setting up data that the test needs,etc.
//        2.Act: This is to act on the test, code that we write that actually triggers the thing that we are testing
//        3.Assert

        String loginPayLoad= """
                {
                    "email":"testuser@test.com"
                    "password":"password123"
                }
                """;

        Response response=given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .body("token",notNullValue())
                .extract()
                .response();

        System.out.println("Generated Toke: "+response.jsonPath().getString("token"));
    }

    @Test
    public void shouldReturnUnAuthorizedOnValidToken()
    {
        String loginPayLoad= """
                {
                    "email":"invalid_user@test.com"
                    "password":"wrongPassword"
                }
                """;
                 given()
                .contentType("application/json")
                .body(loginPayLoad)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(401);
    }
}
