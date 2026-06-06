package main.java;

import main.java.controller.UsuarioController;

public class RodarDelete {
    public static void main(String[] args) throws Exception {
        UsuarioController controller = new UsuarioController();
        System.out.println("Verificando e deletando usuário");

        boolean deletado = controller.excluirUsuario(2);
        if (deletado == true){
            System.out.println("Usuário excluido com sucesso." );
        } else {
            System.out.println("Falha ao tentar excluir o usuário");
        }
    }
}