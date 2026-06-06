package main.java;

import main.java.controller.UsuarioController;
import main.java.model.Usuario;

public class RodarInsertOuUpdate {

    public static void main(String[] args) throws Exception {
        UsuarioController controller = new UsuarioController();

        Usuario usuario = new Usuario();

        usuario.setId(11); // Com id zero teremos um novo usuário (Regra dentro do controller)
        usuario.setUsername("novo_user_teste");
        usuario.setNome("Nome Teste teste");
        usuario.setCpf("10996943021");
        usuario.setEmail("fulaninho@email.com");
        usuario.setSenha("SenhaForte123!");
        usuario.setTelefone(351912345678L);
        usuario.setAtivo(true);

        Usuario resultado = controller.salvarUsuario(usuario);

        System.out.println("Operacao concluida! ID: " + resultado.getId() + " | Nome: " + resultado.getNome());
    }
}