package order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class OrderAssertions {
    @Step("Successful get order list with authorization")
    public void successfulGetOrdersListAuthorizedUser(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("orders", notNullValue());
    }

    @Step("Unsuccessful get order list without authorization")
    public void unsuccessfulGetOrdersListWithoutAuthorization(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false));
    }

    @Step("Successful order creation")
    public void successfulOrderCreation(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true));
    }

    @Step("Unsuccessful order creation with authorization and non exist ingredients")
    public void unsuccessfulOrderCreationAuthorizedUserAndNonExistIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_INTERNAL_ERROR);
    }

    @Step("Unsuccessful order creation with authorization and without ingredients")
    public void unsuccessfulOrderCreationAuthorizedUserWithoutIngredients(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .body("success", is(false));
    }
}
