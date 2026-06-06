package main.java.util;

import java.util.Base64;


public class SenhaValidador {

        public static boolean senhaPadrao(String senha) {
        if (senha == null) {
            return false;
        }
        return true;
    }
    public static String criptografarSenha(String senhaLimpa) {
        if (senhaLimpa == null || senhaLimpa.trim().isEmpty()) {
            return null;
        }
        return Base64.getEncoder().encodeToString(senhaLimpa.getBytes());
    }
}

