package br.com.spring_unit_testing.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import br.com.spring_unit_testing.config.TestConfig;
import br.com.spring_unit_testing.integrationtests.testconteiners.AbstractIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    @DisplayName("Junit test for should display swagger ui page")
    void testShouldDisplaySwaggerUiPage(){

        var content = given()
                .basePath("swagger-ui/index.html")
                .port(TestConfig.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();

        assertTrue(content.contains("Swagger UI"));
    }
}
