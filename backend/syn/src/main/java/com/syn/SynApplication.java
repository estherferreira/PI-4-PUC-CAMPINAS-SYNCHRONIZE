package com.syn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.syn.models.User;
import com.syn.models.verifications.Email;
import com.syn.utils.Data;
import com.syn.models.Diagnosis;
import com.syn.models.SubscriptionPlan;

@SpringBootApplication
public class SynApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynApplication.class, args);

        try {
            // Crie o objeto User usando o construtor
            User user = new User(new Email("luanapiovani@gmail.com"), "LuPiovani#12", "Luana Piovani",
                    new Data((byte) 29, (byte) 8, (short) 1996), "O-", new SubscriptionPlan("Básico"));
            System.out.print(user + "\n");
            
            User samUser = new User(new Email("laurabarros@gmail"), "LauraBarros&14", "Laura Barros",
                    new Data((byte) 25, (byte) 8, (short) 2003), "O-", new SubscriptionPlan("Premium"));

            // System.out.println(user.getPassword());
            // user.setPassword("LauraBarros&14");
            // System.out.println(user.getPassword());
            // System.out.println(user.equals(samUser));

            // System.out.println(user.clone());
            // System.out.println(user.hashCode());
            // System.out.println(user.toString());

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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
