package main.java.util;

public class CpfValidador {

    public static boolean isValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");
        return cpfLimpo.length() == 11;
    }
}