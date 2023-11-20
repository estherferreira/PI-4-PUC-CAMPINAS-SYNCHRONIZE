package com.server.validations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Calendar;

import com.server.errors.CustomException;
import com.server.utils.Data;

public class PersonalInfo {
    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @DecimalMin(value = "0.0", inclusive = false, message = "O peso deve ser maior que 0")
    @Digits(integer = 3, fraction = 2, message = "Formato de peso inválido")
    private double weight; // peso em kg

    @Min(value = 0, message = "A altura deve ser positiva")
    @Max(value = 300, message = "A altura deve ser menor que 300 cm")
    private short height; // altura em cm

    @Min(value = 1, message = "O tempo médio de exercícios deve ser maior que 0")
    @Max(value = 1440, message = "O tempo médio de exercícios deve ser menor que 1440 minutos")
    private short exerciseTime; // tempo em minutos
    private Data dateOfBirth;

    public PersonalInfo(String name, double weight, short height, short exerciseTime, Data dateOfBirth) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.exerciseTime = exerciseTime;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters e Setters
    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public short getHeight() {
        return height;
    }

    public short getExerciseTime() {
        return exerciseTime;
    }

    public Data getDateOfBirth() {
        return dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) throws CustomException {
        if (weight <= 0.0) {
            throw new CustomException("O peso deve ser maior que 0", 1006);
        }
        this.weight = weight;
    }

    public void setHeight(short height) throws CustomException {
        if (height < 0) {
            throw new CustomException("A altura deve ser positiva", 1005);
        }
        this.height = height;
    }

    public void setExerciseTime(short exerciseTime) throws CustomException {
        if (exerciseTime < 1 || exerciseTime > 1440) {
            throw new CustomException("O tempo médio de exercícios deve ser maior que 0 e menor igual a 1440 minutos", 1004);
        }
        this.exerciseTime = exerciseTime;
    }

    public void setBirthDate(byte day, byte month, short year) throws CustomException {
        try {
            Data date = new Data(day, month, year);

            if (!Data.isValida(day, month, year)) {
                throw new CustomException("Data de nascimento inválida.", 1003);
            }

            if (!isAdult(date)) {
                throw new CustomException("O usuário deve ter mais de 18 anos.", 1002);
            }

            this.dateOfBirth = date;
        } catch (Exception e) {
            System.err.println("Ocorreu um erro ao definir a data de nascimento: " + e.getMessage());
        }
    }

    /* Métodos auxiliares */

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
        return '\n' + "PersonalInfo" + '\n' + '\n' +
                "Nome: " + name + '\n' +
                "Peso: " + weight + '\n' +
                "Altura: " + height + '\n' +
                "Tempo médio de exercícios: " + exerciseTime + '\n' +
                "Data de aniversário: " + dateOfBirth + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        PersonalInfo personalInfo = (PersonalInfo) obj;

        if (!personalInfo.name.equals(this.name) || personalInfo.weight != this.weight
                || personalInfo.height != this.height || personalInfo.exerciseTime != this.exerciseTime
                || personalInfo.dateOfBirth != this.dateOfBirth)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + name.hashCode();
        result = 7 * result + Double.valueOf(this.weight).hashCode();
        result = 7 * result + Short.valueOf(this.height).hashCode();
        result = 7 * result + Short.valueOf(this.exerciseTime).hashCode();
        result = 7 * result + dateOfBirth.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private PersonalInfo(PersonalInfo modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.name = modelo.name;
        this.weight = modelo.weight;
        this.height = modelo.height;
        this.exerciseTime = modelo.exerciseTime;
        this.dateOfBirth = modelo.dateOfBirth;
    }

    public PersonalInfo clone() {
        PersonalInfo ret = null;

        try {
            ret = new PersonalInfo(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
