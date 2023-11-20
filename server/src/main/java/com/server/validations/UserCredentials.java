package com.server.validations;

import org.springframework.security.crypto.bcrypt.BCrypt;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.server.errors.CustomException;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import lombok.extern.jackson.Jacksonized;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Jacksonized
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserCredentials implements Cloneable {

     private String email;
     private String password;

     @Tolerate
     public UserCredentials() {
          // Construtor padrão necessário para deserialização
     }

     // Construtor explicitamente definido com dois parâmetros
     public UserCredentials(String email, String password) {
          this.email = email;
          this.password = password;
     }

     public String getEmail() {
          return email;
     }

     public String getPassword() {
          return password;
     }

     public void setEmail(String email) {
          if (isValidEmail(email)) {
               this.email = email;
          } else {
               throw new CustomException("Endereço de e-mail inválido.", 1007);
          }
     }

     public boolean setPassword(String password) {
          if (password == null || password.isEmpty()) {
               throw new IllegalArgumentException("Campo obrigatório.");
          }
          if (!isPasswordStrong(password)) {
               throw new CustomException(
                         "Para atender aos critérios de segurança, sua senha de conter pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.",
                         1001);
          }
          this.password = hashPassword(password);
          return true;
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

     // Verifica se o email é válido (tem o formato de um email)
     public static boolean isValidEmail(String email) {
          String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
          Pattern pattern = Pattern.compile(regex);
          Matcher matcher = pattern.matcher(email);
          return matcher.matches() && !email.contains("@.");
     }

     @Override
     public String toString() {
          return "Email: " + email + '\n' +
                    "Password Hash: " + password + '\n';
     }

     @Override
     public boolean equals(Object obj) {
          if (obj == this)
               return true;
          if (obj == null)
               return false;
          if (obj.getClass() != this.getClass())
               return false;

          UserCredentials user = (UserCredentials) obj;

          if (!user.email.equals(this.email) || !user.password.equals(this.password))
               return false;

          return true;
     }

     @Override
     public int hashCode() {
          int result = 13;

          result = 7 * result + email.hashCode();
          result = 7 * result + password.hashCode();

          if (result < 0)
               result = -result;

          return result;
     }

     private UserCredentials(UserCredentials modelo) throws Exception {
          if (modelo == null)
               throw new Exception("modelo ausente");

          this.email = modelo.email;
          this.password = modelo.password;
     }

     public Object clone() {
          UserCredentials ret = null;

          try {
               ret = new UserCredentials(this);
          } catch (Exception erro) {
          }

          return ret;
     }
}