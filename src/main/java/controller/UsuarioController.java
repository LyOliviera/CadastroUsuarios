package main.java.controller;

import main.java.config.ConexaoDB;
import main.java.model.Usuario;
import main.java.dao.*;
import main.java.listener.*;

import java.sql.Connection;
import java.util.List;

public class UsuarioController {

    public UsuarioController() {
    }

    public Usuario salvarUsuario(Usuario usuarioNovo) throws Exception {
        try (Connection conexao = ConexaoDB.getConections()) {
            CrudListener listener = new UsuarioListener();
            UsuarioDao usuarioDao = new UsuarioDao(conexao, listener);

            if (usuarioNovo.getId() == 0) {
                usuarioNovo.setDtcriado(java.time.LocalDateTime.now());
                usuarioNovo.setDtalter(java.time.LocalDateTime.now());
                usuarioNovo.setAtivo(true);

                usuarioDao.insert(usuarioNovo);

                Usuario usuarioAposInsert = (Usuario) usuarioDao.findByCpf(usuarioNovo.getCpf(), true);
                System.out.println("Novo usuário inserido no banco de dados com sucesso.");
                return usuarioAposInsert;

            } else {
                usuarioNovo.setDtalter(java.time.LocalDateTime.now());
                usuarioDao.updateByID(usuarioNovo);

                Usuario usuarioAposUpdate = (Usuario) usuarioDao.findByCpf(usuarioNovo.getCpf(), true);
                if (usuarioAposUpdate != null) {
                    System.out.println("Usuário atulizado com sucesso.");
                    return usuarioAposUpdate;
                }
                throw new IllegalArgumentException("Usuário não encontrado para atualização");
            }
        }
    }

    public boolean excluirUsuario(int id) throws Exception {
        try (Connection conexao = ConexaoDB.getConections()) {
            CrudListener listener = new UsuarioListener();
            UsuarioDao usuarioDao = new UsuarioDao(conexao, listener);

            int usuarioDeletado = usuarioDao.deleteById(id);
            return usuarioDeletado > 0;
        }
    }

    public Usuario buscarUsuarioPorId(int id) throws Exception {
        try (Connection conexao = ConexaoDB.getConections()) {
            CrudListener listener = new UsuarioListener();
            UsuarioDao usuarioDao = new UsuarioDao(conexao, listener);

            return (Usuario) usuarioDao.findById(id, false);
        }
    }

    public List<Usuario> listarTodosUsuarios() throws Exception {
        try (Connection conexao = ConexaoDB.getConections()) {
            CrudListener listener = new UsuarioListener();
            UsuarioDao usuarioDao = new UsuarioDao(conexao, listener);

            return (List<Usuario>) (List<?>) usuarioDao.findAll();
        }
    }
}