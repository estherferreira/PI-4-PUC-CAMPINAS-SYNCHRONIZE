package com.synback.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Credentials")
public class AuthenticationUser implements Cloneable {

    private String credentialId;
    private String email;
    private String password;
    private String role;

    // Construtor padrão necessário para a desserialização
    public AuthenticationUser() {
    }

    public AuthenticationUser(String credentialId, String email, String password) {
        this.credentialId = credentialId;
        this.email = email;
        this.password = password;
        this.role = "customer";
    }

    public String getId() {
        return credentialId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.credentialId = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Id: " + credentialId + '\n' +
                "Email: " + email.toString() + '\n' +
                "Password: " + password + '\n' +
                "Role: " + role;
    }

    public String getRole() {
        return role;
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

        if (user.credentialId != this.credentialId || user.email != this.email ||
                user.password != this.password ||
                user.role != this.role)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + credentialId.hashCode();
        result = 7 * result + email.hashCode();
        result = 7 * result + password.hashCode();
        result = 7 * result + role.hashCode();

        if (result < 0)
            result = -result;

        return result;
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
