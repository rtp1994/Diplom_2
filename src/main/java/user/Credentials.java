package user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    public String email;
    public String password;
    public String name;

    public static Credentials from(User user) {
        return new Credentials(user.getEmail(), user.getPassword(), user.getName());
    }
}
