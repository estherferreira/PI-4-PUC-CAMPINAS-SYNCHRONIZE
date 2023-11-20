package com.synback.models;

public class Register implements Cloneable {

    private String email;
    private String password;

    public Register(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Email: " + email.toString() + '\n' +
                "Password Hash: " + password + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        Register user = (Register) obj;

        if (user.email != this.email ||
                user.password != this.password)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + email.hashCode();
        result = 7 * result + password.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private Register(Register modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.email = modelo.email;
        this.password = modelo.password;
    }

    public Object clone() {
        Register ret = null;

        try {
            ret = new Register(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
