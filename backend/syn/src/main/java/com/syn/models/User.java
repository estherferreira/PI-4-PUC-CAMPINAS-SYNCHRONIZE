package com.syn.models;

import org.mindrot.jbcrypt.BCrypt;
import java.text.SimpleDateFormat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Calendar;
import java.util.Date;

public class User {
    private String email;
    private String password;
    private String name;
    private Date dateOfBirth;
    private byte day;
    private byte month;
    private short year;
    private String bloodType;

    private static final String[] VALID_BLOOD_TYPES = { "A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-" };

    public User(String email, String password, String name, byte day, byte month, short year, String bloodType) {
        this.email = email;
        if (isPasswordStrong(password)) {
            // Realize o hashing da senha antes de armazená-la
            this.password = hashPassword(password);
        } else {
            throw new IllegalArgumentException("A senha não atende aos critérios de segurança.");
        }
        this.name = name;
        setBirthDate(day, month, year);

        if (isValidBloodType(bloodType)) {
            this.bloodType = bloodType;
        } else {
            throw new IllegalArgumentException(
                    "Tipo sanguíneo inválido. Tipos válidos são: A+, B+, AB+, O+, A-, B-, AB-, O-");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public byte getDay() {
        return day;
    }

    public void setDay(byte day) {
        this.day = day;
    }

    public byte getMonth() {
        return month;
    }

    public void setMonth(byte month) {
        this.month = month;
    }

    public short getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public void setBirthDate(byte day, byte month, short year) {
        Calendar birthCalendar = Calendar.getInstance();
        birthCalendar.set(Calendar.YEAR, year);
        birthCalendar.set(Calendar.MONTH, month - 1); // O mês começa em 0 (janeiro) no Calendar
        birthCalendar.set(Calendar.DAY_OF_MONTH, day);

        if (isValidDate(day, month, year)) {
            if (isAdult(birthCalendar)) {
                this.dateOfBirth = birthCalendar.getTime();
            } else {
                throw new IllegalArgumentException("O usuário deve ter mais de 18 anos.");
            }
        } else {
            throw new IllegalArgumentException("Data de nascimento inválida.");
        }
    }

    public String getDateOfBirthFormatted() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        if (dateOfBirth != null) {
            return dateFormat.format(dateOfBirth);
        } else {
            return "";
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
    private boolean isAdult(Calendar birthCalendar) {
        Calendar currentCalendar = Calendar.getInstance();

        int age = currentCalendar.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR);

        if (currentCalendar.get(Calendar.MONTH) < birthCalendar.get(Calendar.MONTH) ||
                (currentCalendar.get(Calendar.MONTH) == birthCalendar.get(Calendar.MONTH) &&
                        currentCalendar.get(Calendar.DAY_OF_MONTH) < birthCalendar.get(Calendar.DAY_OF_MONTH))) {
            age--; // Ainda não fez aniversário neste ano
        }

        return age >= 18;
    }

    // Verifica se a data de nascimento é válida
    private boolean isValidDate(byte day, byte month, short year) {
        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);

        if (year < 1900 || year > currentYear || month < 1 || month > 12 || day < 1 || day > 31) {
            return false;
        }

        return year >= 1900 && year <= currentYear && month >= 1 && month <= 12 && day >= 1 && day <= 31;
    }

    @Override
    public String toString() {
        return "User:" + '\n' + '\n' +
                "Name: " + name + '\n' +
                "Email: " + email + '\n' +
                "Password Hash: " + password + '\n' +
                "DateOfBirth: " + getDateOfBirthFormatted() + '\n' +
                "BloodType: " + bloodType;
    }
}
