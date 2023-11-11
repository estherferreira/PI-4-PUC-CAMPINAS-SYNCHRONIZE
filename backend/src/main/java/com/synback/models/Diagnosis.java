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
    private String name;

    public Diagnosis(String id, List<ReportItem> report, String symptoms, String name) {
        this.id = id;
        this.report = report;
        this.symptoms = symptoms;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                ", userName='" + name + '\'' +
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
                diagnosis.name != this.name)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + id.hashCode();
        result = 7 * result + report.hashCode();
        result = 7 * result + symptoms.hashCode();
        result = 7 * result + name.hashCode();

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
    this.name = modelo.name;
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
