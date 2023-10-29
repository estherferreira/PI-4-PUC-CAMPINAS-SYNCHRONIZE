

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.server.models.User;
import com.server.utils.Email;

public class UserTest {

    private Email validEmail;

    @BeforeEach
    public void setUp() {
        validEmail = new Email("example@test.com");
    }

    @Test
    public void testCreateUserWithStrongPassword() {
        String strongPassword = "Abc@1234";
        assertDoesNotThrow(() -> {
            new User("001", validEmail, strongPassword, "Client");
        });
    }

    @Test
    public void testCreateUserWithWeakPassword() {
        String weakPassword = "abc";
        assertThrows(IllegalArgumentException.class, () -> {
            new User("001", validEmail, weakPassword, "Client");
        }, "A senha não atende aos critérios de segurança.");
    }

    @Test
    public void testUserPasswordHashing() {
        String password = "Abc@1234";
        User user = new User("001", validEmail, password, "Client");
        assertFalse(password.equals(user.getPassword()), "Password should be hashed");
    }
}
