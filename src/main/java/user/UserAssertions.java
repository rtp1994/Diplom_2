package user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserAssertions {
    @Step("Successful user creation")
    public String createdSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
                .extract().path("accessToken");
    }

    @Step("Successful user login")
    public String loggedInSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .body("accessToken", notNullValue())
                .extract().path("accessToken");
    }

    @Step("Unsuccessful user creation")
    public void creationFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_FORBIDDEN)
                .body("success", is(false));
    }

    @Step("Unsuccessful user login")
    public void loginFailed(ValidatableResponse response) {
        response.assertThat().statusCode(HTTP_UNAUTHORIZED);
    }

    @Step("Successful user deleting")
    public void deletedSuccessfully(ValidatableResponse response) {
        response.assertThat().statusCode(HTTP_ACCEPTED);
    }

    @Step("Successful changing email")
    public String changingEmailSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
                .extract().path("user.email");
    }

    @Step("Successful changing name")
    public String changingNameSuccessfully(ValidatableResponse response) {
        return response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true))
                .extract().path("user.name");
    }

    @Step("Successful changing password")
    public void changingPasswordSuccessfully(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_OK)
                .body("success", is(true));
    }

    @Step("Unsuccessful changing user")
    public void changingFailed(ValidatableResponse response) {
        response.assertThat()
                .statusCode(HTTP_UNAUTHORIZED)
                .body("success", is(false));
    }
}
