package com.server.validations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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

    public PersonalInfo(String name, double weight, short height, short exerciseTime) throws Exception {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.exerciseTime = exerciseTime;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(double weight) throws Exception {
        if (weight < 0.0) {
            throw new Exception("O peso deve ser maior que 0");
        }
        this.weight = weight;
    }

    public void setHeight(short height) throws Exception {
        if (height < 0) {
            throw new Exception("A altura deve ser positiva");
        }
        this.height = height;
    }

    public void setExerciseTime(short exerciseTime) throws Exception {
        if (exerciseTime < 1) {
            throw new Exception("O tempo médio de exercícios deve ser maior que 0");
        }
        this.exerciseTime = exerciseTime;
    }

    @Override
    public String toString() {
        return '\n' + "PersonalInfo" + '\n' + '\n' +
                "Nome: " + name + '\n' +
                "Peso: " + weight + '\n' +
                "Altura: " + height + '\n' +
                "Tempo médio de exercícios: " + exerciseTime + '\n';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        PersonalInfo personalInfo = (PersonalInfo) obj;

        if (personalInfo.name != this.name || personalInfo.weight != this.weight
                || personalInfo.height != this.height || personalInfo.exerciseTime != this.exerciseTime)
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
