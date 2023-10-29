package com.synback.models;

public class SubscriptionPlan implements Cloneable {

    private String type; // "Básico", "Premium"
    private int diagnosesPerMonth;

    // Construtor
    public SubscriptionPlan(String type) {
        this.type = type;

        if ("Básico".equals(type)) { //Compara strings
            diagnosesPerMonth = 2;
        } else {
            diagnosesPerMonth = 10;
        }
    }

    public String getType() {
        return type;
    }

    public int getDiagnosesPerMonth() {
        return diagnosesPerMonth;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDiagnosesPerMonth(int diagnosesPerMonth) {
        this.diagnosesPerMonth = diagnosesPerMonth;
    }

    @Override
    public String toString() {
        return "[type = " + type + ", diagnosesPerMonth = " + diagnosesPerMonth + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != this.getClass())
            return false;

        SubscriptionPlan subscriptionPlan = (SubscriptionPlan) obj;

        if (subscriptionPlan.type != this.type ||
                subscriptionPlan.diagnosesPerMonth != this.diagnosesPerMonth)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + type.hashCode();
        result = 7 * result + Integer.valueOf(this.diagnosesPerMonth).hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private SubscriptionPlan(SubscriptionPlan modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.type = modelo.type;
        this.diagnosesPerMonth = modelo.diagnosesPerMonth;
    }

    public Object clone() {
        SubscriptionPlan ret = null;

        try {
            ret = new SubscriptionPlan(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}

