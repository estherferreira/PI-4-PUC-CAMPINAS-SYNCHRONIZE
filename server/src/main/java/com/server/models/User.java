package com.server.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.server.utils.Email;

public class User implements Cloneable {

     private final String id;
     private Email email;
     private String password;
     private final String role;

     public User(String id, Email email, String password, String role) {
          this.id = id;
          this.email = email;
          if (isPasswordStrong(password)) {
               // Realize o hashing da senha antes de armazená-la
               this.password = hashPassword(password);
          } else {
               throw new IllegalArgumentException(
                         "Para atender aos critérios de segurança, sua senha de conter pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.");
          }
          this.role = role;
     }

     public String getId() {
          return id;
     }

     public String getEmail() {
          return email.getEmail();
     }

     public String getPassword() {
          return password;
     }

     public boolean setPassword(String password) {
          if (password == null || password.isEmpty()) {
               throw new IllegalArgumentException("Campo obrigatório.");
          }
          if (isPasswordStrong(password)) {
               this.password = hashPassword(password);
               return true;
          } else {
               throw new IllegalArgumentException("A senha não atende aos critérios de segurança.");
          }
     }

     /* Métodos auxiliares */

     // Verifica se a senha é forte
     private boolean isPasswordStrong(String password) {
          String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(password);
          return matcher.matches();
     }

     // Gera é uma representação segura do hash da senha com o (cost) de 10. O valor
     // do custo é usado para controlar a velocidade de geração do hash e, portanto,
     // o tempo necessário para um ataque de força bruta.
     private String hashPassword(String password) {
          return BCrypt.hashpw(password, BCrypt.gensalt());
     }

     @Override
     public String toString() {
          return "User:" + '\n' + '\n' +
                    "Id: " + id + '\n' +
                    "Email: " + email.toString() + '\n' +
                    "Password Hash: " + password + '\n' +
                    "Role: " + role;
     }

     @Override
     public boolean equals(Object obj) {
          if (obj == this)
               return true;
          if (obj == null)
               return false;
          if (obj.getClass() != this.getClass())
               return false;

          User user = (User) obj;

          if (user.id != this.id ||
                    user.email != this.email ||
                    user.password != this.password ||
                    user.role != this.role)
               return false;

          return true;
     }

     @Override
     public int hashCode() {
          int result = 13;

          result = 7 * result + id.hashCode();
          result = 7 * result + email.hashCode();
          result = 7 * result + password.hashCode();
          result = 7 * result + role.hashCode();

          if (result < 0)
               result = -result;

          return result;
     }

     private User(User modelo) throws Exception {
          if (modelo == null)
               throw new Exception("modelo ausente");

          this.id = modelo.id;
          this.email = modelo.email;
          this.password = modelo.password;
          this.role = modelo.role;
     }

     public Object clone() {
          User ret = null;

          try {
               ret = new User(this);
          } catch (Exception erro) {
          }

          return ret;
     }
}