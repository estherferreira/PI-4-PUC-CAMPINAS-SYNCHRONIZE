package com.synback.models;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "Diagnosis")
public class UserDiagnosis implements Cloneable {

    private String diagnosisId;
    private List<ReportItem> report;
    private String symptoms;
    private String userName;
    private String email;
    private String userId;

    public UserDiagnosis() {
    }

    public UserDiagnosis(String diagnosisId, List<ReportItem> report, String symptoms, String userName, String email,
            String userId) {
        this.diagnosisId = diagnosisId;
        this.report = report;
        this.symptoms = symptoms;
        this.userName = userName;
        this.email = email;
        this.userId = userId;
    }

    public String getId() {
        return diagnosisId;
    }

    public void setId(String id) {
        this.diagnosisId = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.email = userId;
    }

    public static class ReportItem {
        private String problem;
        private String chanceOfOccurrence;
        private String description;

        public ReportItem(String problem, String chanceOfOccurrence, String description) {
            this.problem = problem;
            this.chanceOfOccurrence = chanceOfOccurrence;
            this.description = description;
        }

        public String getProblem() {
            return problem;
        }

        public void setProblem(String problem) {
            this.problem = problem;
        }

        public String getChanceOfOccurrence() {
            return chanceOfOccurrence;
        }

        public void setChanceOfOccurrence(String chanceOfOccurrence) {
            this.chanceOfOccurrence = chanceOfOccurrence;
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
                "diagnosisId: " + diagnosisId + '\'' +
                ", report: " + report +
                ", symptoms: " + symptoms + '\'' +
                ", userName: " + userName + '\'' +
                ", email: " + email + '\'' +
                ", userId: " + userId + '\'' +
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

        UserDiagnosis diagnosis = (UserDiagnosis) obj;

        if (diagnosis.diagnosisId != this.diagnosisId ||
                diagnosis.report != this.report ||
                diagnosis.symptoms != this.symptoms ||
                diagnosis.userName != this.userName ||
                diagnosis.email != this.email ||
                diagnosis.userId != this.userId)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 13;

        result = 7 * result + diagnosisId.hashCode();
        result = 7 * result + report.hashCode();
        result = 7 * result + symptoms.hashCode();
        result = 7 * result + userName.hashCode();
        result = 7 * result + email.hashCode();
        result = 7 * result + userId.hashCode();

        if (result < 0)
            result = -result;

        return result;
    }

    private UserDiagnosis(UserDiagnosis modelo) throws Exception {
        if (modelo == null)
            throw new Exception("modelo ausente");

        this.diagnosisId = modelo.diagnosisId;
        this.report = modelo.report != null ? new ArrayList<>(modelo.report) : null;
        this.symptoms = modelo.symptoms;
        this.userName = modelo.userName;
        this.email = modelo.email;
        this.userId = modelo.userId;
    }

    public Object clone() {
        UserDiagnosis ret = null;

        try {
            ret = new UserDiagnosis(this);
        } catch (Exception erro) {
        }

        return ret;
    }
}
