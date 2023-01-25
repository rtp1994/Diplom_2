package user;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;

public class UserTests {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    private String accessToken;

    @Test
    @DisplayName("Creation user")
    public void creationUser() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);
    }

    @Test
    @DisplayName("Login user")
    public void loginUser() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);
        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loggedInSuccessfully(loginResponse);
    }

    @Test
    @DisplayName("Creation fails with data existing user")
    public void creationFailsWithDataExistingUser() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);
        ValidatableResponse creationsResponseTwice = client.create(user);
        check.creationFailed(creationsResponseTwice);
    }

    @Test
    @DisplayName("Login fails without password")
    public void loginFailsWithoutPassword() {
        var user = generator.generick();
        user.setPassword(null);

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loginFailed(loginResponse);
    }

    @Test
    @DisplayName("Login fails with wrong email and password")
    public void loginFailsWithWrongEmailAndPassword() {
        var user = generator.generick();
        user.setEmail(null);
        user.setPassword(null);

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loginFailed(loginResponse);
    }

    @After
    @DisplayName("Remove test users")
    public void deleteUser() {
        if (accessToken != null) {
            ValidatableResponse response = client.delete(accessToken);
            check.deletedSuccessfully(response);
            accessToken = null;
        }
    }
}