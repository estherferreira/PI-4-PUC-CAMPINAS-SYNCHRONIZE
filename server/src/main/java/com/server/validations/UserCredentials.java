package com.server.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.server.errors.CustomException;
import com.server.utils.Email;

public class UserCredentials implements Cloneable {

     private Email email;
     private String password;

     public UserCredentials(Email email, String password) {
          this.email = email;

          if (!isPasswordStrong(password)) {
               throw new CustomException("Para atender aos critérios de segurança, sua senha de conter pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.", 1001);
          }
          this.password = hashPassword(password);

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
          if (!isPasswordStrong(password)) {
               throw new CustomException("Para atender aos critérios de segurança, sua senha de conter pelo menos uma letra maiúscula, uma letra minúscula, um dígito e um caractere especial.", 1001);
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

     @Override
     public String toString() {
          return "User:" + '\n' + '\n' +
                    "Email: " + email.toString() + '\n' +
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

          if (user.email != this.email ||
                    user.password != this.password)
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