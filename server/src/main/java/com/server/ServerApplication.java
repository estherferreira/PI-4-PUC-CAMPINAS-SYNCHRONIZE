package com.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.server.models.Diagnosis;
import com.server.models.SubscriptionPlan;
import com.server.models.UserDetails;
import com.server.models.verifications.Email;
import com.server.utils.Data;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);

		try {
            // Crie o objeto User usando o construtor
            UserDetails user = new UserDetails(new Email("luanapiovani@gmail.com"), "LuPiovani#12", "Luana Piovani",
                    new Data((byte) 29, (byte) 8, (short) 1996), "O-", new SubscriptionPlan("Básico"));
            System.out.print(user + "\n");
            
            UserDetails samUser = new UserDetails(new Email("laurabarros@gmail"), "LauraBarros&14", "Laura Barros",
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
