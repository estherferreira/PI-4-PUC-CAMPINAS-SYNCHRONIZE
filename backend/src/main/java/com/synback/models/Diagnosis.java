package com.synback.models;

public class Diagnosis implements Cloneable {
    private String disease;
    private double probability;

    public Diagnosis(String disease, double probability) {
        this.disease = disease;
        this.probability = probability;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    @Override
    public String toString() {
        return "[disease = " + disease + ", probability = " + probability + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        Diagnosis diagnosis = (Diagnosis) obj;

        if (diagnosis.disease != this.disease ||
                diagnosis.probability != this.probability)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + disease.hashCode();
        result = 7 * result + Double.valueOf(this.probability).hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private Diagnosis(Diagnosis modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.disease = modelo.disease;
        this.probability = modelo.probability;
    }

    public Object clone() {
        Diagnosis ret = null;

        try {
            ret = new Diagnosis(this);
        } catch (Exception erro) {
        }
        
        return ret;
    }
}
