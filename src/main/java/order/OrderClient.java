package order;

import client.Client;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient {

    protected final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    protected final String API_PREFIX = "/api";
    protected final String ROOT = "/orders";

    @Step("Get order list")
    public ValidatableResponse getOrderList(String accessToken) {
        OrderClient orderClient = new OrderClient();
        return given().log().all()
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(API_PREFIX)
                .body(orderClient)
                .when()
                .get(ROOT)
                .then().log().all();
    }

    @Step("Get order list without authorization")
    public ValidatableResponse getOrderListWithoutAuthorization() {
        OrderClient orderClient = new OrderClient();
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(API_PREFIX)
                .body(orderClient)
                .when()
                .get(ROOT)
                .then().log().all();
    }

    @Step("Order creation")
    public ValidatableResponse creatingOrder(Order order, String accessToken) {
        return given().log().all()
                .auth().oauth2(accessToken.replace("Bearer ", ""))
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(API_PREFIX)
                .body(order)
                .when()
                .post(ROOT)
                .then()
                .log().all();
    }

    @Step("Order creation without authorization")
    public ValidatableResponse creatingOrderWithoutAuthorizedUser(Order order) {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URI)
                .basePath(API_PREFIX)
                .body(order)
                .when()
                .post(ROOT)
                .then()
                .log().all();
    }

}
