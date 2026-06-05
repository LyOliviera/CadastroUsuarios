package main.java.util;
import main.java.dao.*;

import main.java.model.Usuario;


public class CpfValidador {
    public static boolean isValido(String cpf) {
        if (cpf == null) {
            return false;
        }

        String cpfLimpo = cpf.replaceAll("\\D", "");
        return cpfLimpo.length() == 11;
    }
