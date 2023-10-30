import com.server.models.User;
import com.server.utils.Email;
import com.server.services.AuthenticationService;
import com.server.services.UserCommunicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authService;

    @Mock
    private UserCommunicationService userCommunicationService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        authService.setSecretKey("estherferreira");
    }

    @Test
    public void testSuccessfulLogin() {
        User user = new User("123", new Email("test@example.com"), "Password123!", "ADMIN");
        when(userCommunicationService.findByEmail(anyString())).thenReturn(user);

        String token = authService.login("test@example.com", "password123");

        assertNotNull(token);
    }

    @Test
    public void testLoginWithNonExistentEmail() {
        when(userCommunicationService.findByEmail(anyString())).thenReturn(null);

        assertThrows(RuntimeException.class, () -> {
            authService.login("nonexistent@example.com", "password123");
        });
    }

    @Test
    public void testSuccessfulRegistration() {
        User user = new User("123", new Email("test@example.com"), "Password123!", "ADMIN");
        when(userCommunicationService.findByEmail(anyString())).thenReturn(null);

        authService.register(user);

        verify(userCommunicationService, times(1)).save(user);
    }

    @Test
    public void testRegistrationWithExistingEmail() {
        User user = new User("123", new Email("test@example.com"), "Password123!", "ADMIN");
        when(userCommunicationService.findByEmail(anyString())).thenReturn(user);

        assertThrows(RuntimeException.class, () -> {
            authService.register(user);
        });
    }
}
