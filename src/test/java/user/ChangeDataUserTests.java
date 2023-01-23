package user;
import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ChangeDataUserTests {
    private final UserGenerator generator = new UserGenerator();
    private final UserClient client = new UserClient();
    private final UserAssertions check = new UserAssertions();
    Faker faker = new Faker();
    private String accessToken;

    @Test
    @Description("Successfully change of user Email")
    @DisplayName("Successfully change of user Email with authorization")
    public void changeEmailOfUserWithAuthorization() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loggedInSuccessfully(loginResponse);

        user.setEmail(faker.internet().emailAddress());
        ValidatableResponse changingResponse = client.change(user, accessToken);
        String newEmailResponse = check.changingEmailSuccessfully(changingResponse);
        String newEmailRequest = user.getEmail();

        assertEquals(newEmailRequest, newEmailResponse);
    }
    @Test
    @Description("Successfully change of user Name")
    @DisplayName("Successfully change of user Name with authorization")
    public void changeNameOfUserWithAuthorization() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loggedInSuccessfully(loginResponse);

        user.setName(faker.name().firstName());
        ValidatableResponse changingResponse = client.change(user, accessToken);
        String newNameResponse = check.changingNameSuccessfully(changingResponse);
        String newNameRequest = user.getName();

        assertEquals(newNameRequest, newNameResponse);
    }
    @Test
    @Description("Successfully change of user Password")
    @DisplayName("Successfully change of user Password with authorization (without password-reset)")
    public void changePasswordOfUserWithAuthorization() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);

        Credentials creds = Credentials.from(user);
        ValidatableResponse loginResponse = client.login(creds);
        check.loggedInSuccessfully(loginResponse);

        user.setPassword(faker.internet().password(6, 10));
        ValidatableResponse changingResponse = client.change(user, accessToken);
        check.changingPasswordSuccessfully(changingResponse);

        Credentials credsNew = Credentials.from(user);
        ValidatableResponse loginResponseNew = client.login(credsNew);
        check.loggedInSuccessfully(loginResponseNew);
    }
    @Test
    @Description("Unsuccessfully change of user data")
    @DisplayName("Unsuccessfully change of user data without authorization and usage wrong accessToken)")
    public void changeOfUserDataWithoutAuthorization() {
        var user = generator.random();
        ValidatableResponse creationsResponse = client.create(user);
        accessToken = check.createdSuccessfully(creationsResponse);

        var newUserData = generator.random();
        String wrongAccessToken = "";
        ValidatableResponse changingResponse = client.change(newUserData, wrongAccessToken);
        check.changingFailed(changingResponse);
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
