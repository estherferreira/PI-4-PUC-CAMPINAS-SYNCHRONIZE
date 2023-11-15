package com.synback;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import com.synback.models.User;
// import com.synback.models.Diagnosis;
// import com.synback.models.SubscriptionPlan;
// import com.synback.utils.Data;
// import java.util.Arrays;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        try {
            // Crie o objeto User usando o construtor
            
            // User user = new User("0001", "Luana", new Data((byte) 1, (byte) 1, (short) 2000), 50.0,
            //         (short) 160, "Feminino", (byte) 30, "Nenhum", "A+", new SubscriptionPlan("Premium"));
            // System.out.print(user + "\n");

            // System.out.print("\n");
            // System.out.println("---------------------------------------------------------");
            // System.out.print("\n");

            // // Crie o objeto Diagnosis usando o construtor
            // Diagnosis.ReportItem reportItem = new Diagnosis.ReportItem("Dor de cabeça", 50, "Descrição detalhada da dor de cabeça.");
            // Diagnosis diagnosis = new Diagnosis("0001", Arrays.asList(reportItem), "Dor de cabeça, tontura, visão embaçada", "Luana");

            // System.out.print("Diagnosis: " + diagnosis + "\n");
            // System.out.print("\n");
            // System.out.println("---------------------------------------------------------");
            // System.out.print("\n");

            // // Crie o objeto SubscriptionPlan usando o construtor
            // SubscriptionPlan subscriptionPlan = new SubscriptionPlan("Premium");
            // System.out.print("Subscription Plan: " + subscriptionPlan);
            // System.out.print("\n");

            // // Crie o objeto Data usando o construtor
            // Data date = new Data((byte) 19, (byte) 1, (short) 2011);
            // System.out.println(date);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
