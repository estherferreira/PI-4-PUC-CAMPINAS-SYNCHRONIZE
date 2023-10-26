package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.server.models.Diagnosis;
import com.server.models.SubscriptionPlan;
import com.server.models.User;
import com.server.utils.Data;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        try {
            // Crie o objeto User usando o construtor
            
            User user = new User("Luana Pionvani", new Data((byte) 1, (byte) 1, (short) 2000), 50.0,
                    (short) 160, "Feminino", (byte) 30, "Nenhum", "A+", new SubscriptionPlan("Premium"));
            System.out.print(user + "\n");

            System.out.print("\n");
            System.out.println("---------------------------------------------------------");
            System.out.print("\n");

            // Crie o objeto Diagnosis usando o construtor
            Diagnosis diagnosis = new Diagnosis("Dor de cabeça", 0.5);
            System.out.print("Diagnosis: " + diagnosis + "\n");
            System.out.print("\n");
            System.out.println("---------------------------------------------------------");
            System.out.print("\n");

            // Crie o objeto SubscriptionPlan usando o construtor
            SubscriptionPlan subscriptionPlan = new SubscriptionPlan("Premium");
            System.out.print("Subscription Plan: " + subscriptionPlan);
             System.out.print("\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
