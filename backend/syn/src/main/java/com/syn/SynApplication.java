package com.syn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.syn.models.User;
import com.syn.models.verifications.Email;
import com.syn.utils.Data;

@SpringBootApplication
public class SynApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynApplication.class, args);

        try {
            // Crie o objeto User usando o construtor
            User user = new User(new Email("luanapiovani@gmail.com"), "LuPiovani#12", "Luana Piovani",
                    new Data((byte) 29, (byte) 8, (short) 1996), "O-");
            System.out.print(user);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
