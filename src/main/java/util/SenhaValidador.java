package main.java.util;

import java.util.Base64;


public class SenhaValidador {

        public static boolean senhaPadrao(String senha) {
        if (senha == null) {
            return false;
        }
            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$";

            return senha.matches(regex);
        }

    public static String criptografarSenha(String senhaLimpa) {
        if (senhaLimpa == null || senhaLimpa.trim().isEmpty()) {
            return null;
        }
        return Base64.getEncoder().encodeToString(senhaLimpa.getBytes());
    }
}

