package com.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.server.validations.UserCredentials;

class UserCredentialsTest {

    @Test
    void testEmailSetterGetter() {
        String email = "test@example.com";
        UserCredentials user = new UserCredentials();
        user.setEmail(email);
        assertEquals(email, user.getEmail());
    }

    @Test
    void testPasswordSetterGetter() {
        String password = "password123";
        UserCredentials user = new UserCredentials();
        user.setPassword(password);
        assertEquals(password, user.getPassword());
    }

    @Test
    void testIsValidEmail() {
        assertTrue(UserCredentials.isValidEmail("valid.email@example.com"));
        assertFalse(UserCredentials.isValidEmail("invalidemail"));
        assertFalse(UserCredentials.isValidEmail("invalid@.com"));
    }

    @Test
    void testEqualsAndHashCode() {
        UserCredentials user1 = new UserCredentials("user@example.com", "password123");
        UserCredentials user2 = new UserCredentials("user@example.com", "password123");
        UserCredentials user3 = new UserCredentials("different@example.com", "password123");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);

        assertEquals(user1.hashCode(), user2.hashCode());
        assertNotEquals(user1.hashCode(), user3.hashCode());
    }

    @Test
    void testClone() throws CloneNotSupportedException {
        UserCredentials original = new UserCredentials("clone@example.com", "password123");
        UserCredentials cloned = (UserCredentials) original.clone();

        assertEquals(original, cloned);
        assertNotSame(original, cloned);
    }

    @Test
    void testToString() {
        UserCredentials user = new UserCredentials("user@example.com", "password123");
        String expected = "Email: user@example.com\nPassword Hash: password123\n";
        assertEquals(expected, user.toString());
    }

    // Adicione mais testes conforme necessário para cobrir todos os aspectos da classe
}
