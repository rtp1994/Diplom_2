package order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserAssertions;
import user.UserClient;
import user.UserGenerator;

public class CreateOrderTests {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private final OrderClient orderClient = new OrderClient();
    private final OrderAssertions checks = new OrderAssertions();
    private String accessToken;

    @Before
    public void createUser() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);
    }
    @Test
    @Description("Successful order creation")
    @DisplayName("Creating an order with authorization and good ingredients")
    public void successfulCreatingOrderAuthorizedUser() {
        Order order = Order.getDefaultOrder();
        ValidatableResponse createResponse = orderClient.creatingOrder(order, accessToken);
        checks.successfulOrderCreation(createResponse);
    }
    @Test
    @Description("Unsuccessful order creation")
    @DisplayName("Creating an order with authorization and non-exist ingredients")
    public void unsuccessfulCreatingOrderAuthorizedUserAndNonExistIngredients() {
        Order order = Order.getIncorrectOrder();
        ValidatableResponse createResponse = orderClient.creatingOrder(order, accessToken);
        checks.unsuccessfulOrderCreationAuthorizedUserAndNonExistIngredients(createResponse);
    }
    @Test
    @Description("Unsuccessful order creation")
    @DisplayName("Creating an order with authorization and without ingredients")
    public void unsuccessfulCreatingOrderAuthorizedUserWithoutIngredients() {
        Order order = new Order(null);
        ValidatableResponse createResponse = orderClient.creatingOrder(order, accessToken);
        checks.unsuccessfulOrderCreationAuthorizedUserWithoutIngredients(createResponse);
    }
    @Test
    @Description("Successful order creation")
    @DisplayName("Creating an order without authorization")
    public void successfulCreatingOrderWithoutAuthorizedUser() {
        Order order = Order.getDefaultOrder();
        ValidatableResponse createResponse = orderClient.creatingOrderWithoutAuthorizedUser(order);
        checks.successfulOrderCreation(createResponse);
    }
    @After
    @DisplayName("Remove test users")
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = client.delete(accessToken);
            check.deletedSuccessfully(response);
        }
    }
}
