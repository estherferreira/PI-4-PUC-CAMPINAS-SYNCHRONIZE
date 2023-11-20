package com.synback.models;

import java.util.Calendar;
import com.synback.utils.Data;

public class User {
    private String id;
    private String name;
    private Data dateOfBirth;
    private double weight;
    private short height;
    private String gender;
    private byte dailyExerciseTime;
    private String diseasesInTheFamily;
    private String bloodType;
    private SubscriptionPlan subscriptionPlan;

    private static final String[] VALID_BLOOD_TYPES = { "A+", "B+", "AB+", "O+", "A-", "B-", "AB-", "O-" };

    public User(String id, String name, Data dateOfBirth, double weight, short height, String gender,
            byte dailyExerciseTime, String diseasesInTheFamily, String bloodType,
            SubscriptionPlan subscriptionPlan) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.dailyExerciseTime = dailyExerciseTime;
        this.diseasesInTheFamily = diseasesInTheFamily;

        if (isValidBloodType(bloodType)) {
            this.bloodType = bloodType;
        } else {
            throw new IllegalArgumentException(
                    "Tipo sanguíneo inválido. Tipos válidos são: A+, B+, AB+, O+, A-, B-, AB-, O-");
        }

        this.subscriptionPlan = subscriptionPlan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Data getDateOfBirth() {
        return dateOfBirth;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException("Peso inválido.");
        }
    }

    public short getHeight() {
        return height;
    }

    public void setHeight(short height) {
        if (height > 0) {
            this.height = height;
        } else {
            throw new IllegalArgumentException("Altura inválida.");
        }
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public byte getDailyExerciseTime() {
        return dailyExerciseTime;
    }

    public void setDailyExerciseTime(byte dailyExerciseTime) {
        if (dailyExerciseTime >= 0 && dailyExerciseTime <= 1440) {
            this.dailyExerciseTime = dailyExerciseTime;
        } else {
            throw new IllegalArgumentException("Tempo de exercício diário inválido.");
        }
    }

    public String getDiseasesInTheFamily() {
        return diseasesInTheFamily;
    }

    public void setDiseasesInTheFamily(String diseasesInTheFamily) {
        this.diseasesInTheFamily = diseasesInTheFamily;
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
        return "Id: " + id + '\n' +
                "Name: " + name + '\n' +
                "DateOfBirth: " + dateOfBirth.toString() + '\n' +
                "Weight: " + weight + '\n' +
                "Height: " + height + '\n' +
                "Gender: " + gender + '\n' +
                "DailyExerciseTime: " + dailyExerciseTime + '\n' +
                "DiseasesInTheFamily: " + diseasesInTheFamily + '\n' +
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

        if (user.id != this.id ||
                user.name != this.name ||
                user.dateOfBirth != this.dateOfBirth ||
                user.weight != this.weight ||
                user.height != this.height ||
                user.gender != this.gender ||
                user.dailyExerciseTime != this.dailyExerciseTime ||
                user.diseasesInTheFamily != this.diseasesInTheFamily ||
                user.bloodType != this.bloodType ||
                user.subscriptionPlan != this.subscriptionPlan)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + id.hashCode();
        result = 7 * result + name.hashCode();
        result = 7 * result + dateOfBirth.hashCode();
        result = 7 * result + Double.hashCode(weight);
        result = 7 * result + Short.hashCode(height);
        result = 7 * result + gender.hashCode();
        result = 7 * result + Byte.hashCode(dailyExerciseTime);
        result = 7 * result + diseasesInTheFamily.hashCode();
        result = 7 * result + bloodType.hashCode();
        result = 7 * result + subscriptionPlan.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private User(User modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.id = modelo.id;
        this.name = modelo.name;
        this.dateOfBirth = modelo.dateOfBirth;
        this.weight = modelo.weight;
        this.height = modelo.height;
        this.gender = modelo.gender;
        this.dailyExerciseTime = modelo.dailyExerciseTime;
        this.diseasesInTheFamily = modelo.diseasesInTheFamily;
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
