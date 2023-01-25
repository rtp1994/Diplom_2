package user;

import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {
    public User generick() {
        return new User("tasya.morozova@gmail.com", "123456", "Таисия");
    }

    public User random() {
        Faker faker = new Faker();
        return new User(faker.internet().emailAddress(), faker.internet().password(), RandomStringUtils.randomAlphanumeric(9));
    }

    public User nonExiting() {
        return new User("user@ya.ru", "user", "user");
    }
}
