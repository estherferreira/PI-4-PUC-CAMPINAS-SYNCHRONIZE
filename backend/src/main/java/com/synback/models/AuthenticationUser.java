package com.synback.models;

import java.util.Random;
import org.springframework.data.annotation.Id;

public class AuthenticationUser implements Cloneable {

    @Id
    private String id;
    private String email;
    private String password;
    private final String role = "customer";

    public AuthenticationUser(String email, String password) {
        this.id = generateUniqueId();
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Id: " + id + '\n' +
                "Email: " + email.toString() + '\n' +
                "Password Hash: " + password + '\n' +
                "Role: " + role;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        AuthenticationUser user = (AuthenticationUser) obj;

        if (user.id != this.id || user.email != this.email ||
                user.password != this.password ||
                user.role != this.role)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + id.hashCode();
        result = 7 * result + email.hashCode();
        result = 7 * result + password.hashCode();
        result = 7 * result + role.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private static String generateUniqueId() {
        Random random = new Random();
        // Gera um número aleatório com 10 dígitos
        int number = random.nextInt(1000000000, 2000000000);
        return "SYN-" + String.format("%010d", number);
    }

    private AuthenticationUser(AuthenticationUser modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.email = modelo.email;
        this.password = modelo.password;
    }

    public Object clone() {
        AuthenticationUser ret = null;

        try {
            ret = new AuthenticationUser(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
