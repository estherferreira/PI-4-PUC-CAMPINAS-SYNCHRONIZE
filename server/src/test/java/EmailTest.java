import com.server.utils.Email;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    public void testValidEmail() {
        assertTrue(Email.isValidEmail("test@example.com"));
    }

    @Test
    public void testInvalidEmail() {
        assertFalse(Email.isValidEmail("test.example.com"));
        assertFalse(Email.isValidEmail("test@.com"));
    }
}
