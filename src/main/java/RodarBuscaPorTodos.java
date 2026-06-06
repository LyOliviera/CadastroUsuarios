package main.java;

import main.java.controller.UsuarioController;
import main.java.model.Usuario;
import java.util.List;

public class RodarBuscaPorTodos {
    public static void main(String[] args) throws Exception {
        UsuarioController controller = new UsuarioController();
        System.out.println("Buscando todos os usuários");

        List<Usuario> retorno = controller.listarTodosUsuarios();

        for (int i = 0; i < retorno.size(); i++) {
            Usuario u = retorno.get(i);
            System.out.println(u.toString());
        }
    }
}