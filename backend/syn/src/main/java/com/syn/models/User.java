package com.syn.models;

import org.mindrot.jbcrypt.BCrypt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Calendar;
import com.syn.utils.Data;

import com.syn.models.verifications.Email;

public class User implements Cloneable {
    private Email email;
    private String password;
    private String name;
    private Data dateOfBirth;
    private String bloodType;
    private SubscriptionPlan subscriptionPlan;

    private static final String[] VALID_BLOOD_TYPES = { "A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-" };

    public User(Email email, String password, String name, Data dateOfBirth, String bloodType,
            SubscriptionPlan subscriptionPlan) {
        this.name = name;
        this.email = email;
        if (isPasswordStrong(password)) {
            // Realize o hashing da senha antes de armazená-la
            this.password = hashPassword(password);
        } else {
            throw new IllegalArgumentException("A senha não atende aos critérios de segurança.");
        }
        this.dateOfBirth = dateOfBirth;

        if (isValidBloodType(bloodType)) {
            this.bloodType = bloodType;
        } else {
            throw new IllegalArgumentException(
                    "Tipo sanguíneo inválido. Tipos válidos são: A+, B+, AB+, O+, A-, B-, AB-, O-");
        }

        this.subscriptionPlan = subscriptionPlan;
    }

    public String getPassword() {
        return password;
    }

    public boolean setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Campo obrigatório.");
        }
        if (isPasswordStrong(password)) {
            this.password = hashPassword(password);
            return true;
        } else {
            throw new IllegalArgumentException("A senha não atende aos critérios de segurança.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(byte day, byte month, short year) {
        try {
            Data date = new Data(day, month, year);

            if (!Data.isValida(day, month, year)) {
                throw new IllegalArgumentException("Data de nascimento inválida.");
            }

            if (!isAdult(date)) {
                throw new IllegalArgumentException("O usuário deve ter mais de 18 anos.");
            }

            this.dateOfBirth = date;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao definir a data de nascimento: " + e.getMessage());
        }
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        if (isValidBloodType(bloodType)) {
            this.bloodType = bloodType;
        } else {
            throw new IllegalArgumentException(
                    "Tipo sanguíneo inválido. Tipos válidos são: A+, B+, AB+, O+, A-, B-, AB-, O-");
        }
    }

    /* Métodos auxiliares */

    // Verifica se a senha é forte
    private boolean isPasswordStrong(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    // Gera é uma representação segura do hash da senha com o (cost) de 10. O valor
    // do custo é usado para controlar a velocidade de geração do hash e, portanto,
    // o tempo necessário para um ataque de força bruta.
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verifica se o tipo sanguineo informado é válido de acordo com a lista de
    // tipos sanguineos válidos
    private boolean isValidBloodType(String bloodType) {
        for (String validType : VALID_BLOOD_TYPES) {
            if (validType.equals(bloodType)) {
                return true;
            }
        }
        return false;
    }

    // Verifica se é a idade do usuário é maior ou igual a 18 anos
    private boolean isAdult(Data birthDate) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.set(birthDate.getAno(), birthDate.getMes() - 1, birthDate.getDia());

        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--; // Ainda não fez aniversário neste ano
        }

        return age >= 18;
    }

    @Override
    public String toString() {
        return "User:" + '\n' + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email.toString() + '\n' +
                "Password Hash: " + password + '\n' +
                "DateOfBirth: " + dateOfBirth.toString() + '\n' +
                "BloodType: " + bloodType + '\n' +
                "SubscriptionPlan: " + subscriptionPlan.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        User user = (User) obj;

        if (user.email != this.email ||
                user.password != this.password ||
                user.name != this.name ||
                user.dateOfBirth != this.dateOfBirth ||
                user.bloodType != this.bloodType ||
                user.subscriptionPlan != this.subscriptionPlan)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + email.hashCode();
        result = 7 * result + password.hashCode();
        result = 7 * result + name.hashCode();
        result = 7 * result + dateOfBirth.hashCode();
        result = 7 * result + bloodType.hashCode();
        result = 7 * result + subscriptionPlan.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private User(User modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.email = modelo.email;
        this.password = modelo.password;
        this.name = modelo.name;
        this.dateOfBirth = modelo.dateOfBirth;
        this.bloodType = modelo.bloodType;
        this.subscriptionPlan = modelo.subscriptionPlan;
    }

    public Object clone() {
        User ret = null;

        try {
            ret = new User(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
