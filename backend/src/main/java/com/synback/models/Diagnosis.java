package com.synback.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "diagnosis")
public class Diagnosis implements Cloneable {

    @Id
    private String id;
    private List<ReportItem> report;
    private String symptoms;
    private String userName;

    public Diagnosis(String id, List<ReportItem> report, String symptoms, String userName) {
        this.id = id;
        this.report = report;
        this.symptoms = symptoms;
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ReportItem> getReport() {
        return report;
    }

    public void setReport(List<ReportItem> report) {
        this.report = report;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static class ReportItem {
        private String problem;
        private int percentage;
        private String description;

        public ReportItem(String problem, int percentage, String description) {
            this.problem = problem;
            this.percentage = percentage;
            this.description = description;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public int getPercentage() {
            return percentage;
        }

        public void setPercentage(int percentage) {
            this.percentage = percentage;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "id='" + id + '\'' +
                ", report=" + report +
                ", symptoms='" + symptoms + '\'' +
                ", userName='" + userName + '\'' +
                '}';
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

        if (diagnosis.id != this.id ||
                diagnosis.report != this.report ||
                diagnosis.symptoms != this.symptoms ||
                diagnosis.userName != this.userName)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + id.hashCode();
        result = 7 * result + report.hashCode();
        result = 7 * result + symptoms.hashCode();
        result = 7 * result + userName.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private Diagnosis(Diagnosis modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

    this.id = modelo.id;
    this.report = modelo.report != null ? new ArrayList<>(modelo.report) : null;
    this.symptoms = modelo.symptoms;
    this.userName = modelo.userName;
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
