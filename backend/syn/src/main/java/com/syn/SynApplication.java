package com.syn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.syn.models.User;

@SpringBootApplication
public class SynApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynApplication.class, args);

        try {
            // Crie o objeto User usando o construtor
            User user = new User("luanapiovani@gmail.com", "LuPiovani#12", "Luana Piovani", (byte) 1, (byte) 1, (short) 2001, "A-");
            System.out.print(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
