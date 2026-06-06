package main.java;

import main.java.controller.UsuarioController;
import main.java.model.Usuario;

public class RodarBuscaPorId {
    public static void main(String[] args) throws Exception {
        UsuarioController controller = new UsuarioController();
        System.out.println("Buscando usuário por id");

        Usuario retorno = controller.buscarUsuarioPorId(6);
        if (retorno != null) {
            System.out.println("Usuario encontrado: " + retorno.toString());
        }
    }
}