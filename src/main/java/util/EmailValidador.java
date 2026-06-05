package main.java.util;

public class EmailValidador {
   public static existeEmail{};

   public static boolean isValido(String email) {

      if(!email.contains("@")) {
         return false;
      } else {
         String arrayEmail[] = email.trim().split("@");
         String nomeEmail = arrayEmail[0];
         String dominioEmail = arrayEmail[1];

         if (nomeEmail.isEmpty() || dominioEmail.isEmpty()) {
            return false;
         }
         if (!dominioEmail.contains(".")) {
            return false;
         }

         if (dominioEmail.startsWith(".") || dominioEmail.endsWith(".")) {
            return false;
         }
      }
      return true;
   }
}