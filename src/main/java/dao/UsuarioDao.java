package main.java.dao;

import main.java.config.ConexaoDB;
import main.java.exception.UsuarioNotFound;
import main.java.listener.*;
import main.java.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UsuarioDao implements CrudDao {
    private final Connection connection;
    private final   CrudListener listener;

    public UsuarioDao(Connection connection, CrudListener listener) {
        this.connection = connection;
        this.listener = listener;
    }

    @Override
    public void insert(Object horcrux) {
        this.listener.prePersist(horcrux);
        Usuario usuario = (Usuario) horcrux;
        PreparedStatement preparedStatement = null;

        try {
            String sqlInsert = "Insert into usuario (username, nome, cpf, email, senha, dtnascimento, telefone, ativo, dtlimite, dtalter, dtcriado) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, usuario.getUsername());
            preparedStatement.setString(2, usuario.getNome());
            preparedStatement.setString(3, usuario.getCpf());
            preparedStatement.setString(4, usuario.getEmail());
            preparedStatement.setString(5, usuario.getSenha());
            preparedStatement.setDate(6, usuario.getDtnascimento() != null ? java.sql.Date.valueOf(usuario.getDtnascimento()) : null);
            preparedStatement.setLong(7, usuario.getTelefone());
            preparedStatement.setBoolean(8, usuario.getAtivo());
            preparedStatement.setTimestamp(9, usuario.getDtlimite() != null ? java.sql.Timestamp.valueOf(usuario.getDtlimite()) : null);
            preparedStatement.setTimestamp(10, usuario.getDtalter() != null ? java.sql.Timestamp.valueOf(usuario.getDtalter()) : null);
            preparedStatement.setTimestamp(11, java.sql.Timestamp.valueOf(usuario.getDtcriado()));

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConexaoDB.closeStatement(preparedStatement);
        }
    }

    @Override
    public int updateByID(Object horcrux) {
        this.listener.prePersist(horcrux);
        Usuario usuario = (Usuario) horcrux;
        PreparedStatement preparedStatement = null;
        findById(usuario.getId(), false);

        try{
            String sqlUpdateById = "Update usuario SET  nome = ?, cpf = ?, email = ?, senha = ?, dtnascimento = ?, telefone = ?, ativo = ?, dtlimite = ?, dtalter = ? where id = ?";
            preparedStatement = connection.prepareStatement(sqlUpdateById);

            preparedStatement.setString(1, usuario.getNome());
            preparedStatement.setString(2, usuario.getCpf());
            preparedStatement.setString(3, usuario.getEmail());
            preparedStatement.setString(4, usuario.getSenha());
            preparedStatement.setDate(5, usuario.getDtnascimento() != null ? java.sql.Date.valueOf(usuario.getDtnascimento()) : null);
            preparedStatement.setLong(6, usuario.getTelefone());
            preparedStatement.setBoolean(7, usuario.getAtivo());
            preparedStatement.setTimestamp(8, usuario.getDtlimite() != null ? java.sql.Timestamp.valueOf(usuario.getDtlimite()) : null);
            preparedStatement.setTimestamp(9, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            preparedStatement.setInt(10, usuario.getId());

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            ConexaoDB.closeStatement(preparedStatement);
        }
    }

    @Override
    public int deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        this.listener.preRemove(usuario);

        try{
            String sqlDeleteById = "DELETE from usuario where id = ?";

            preparedStatement = connection.prepareStatement(sqlDeleteById);
            preparedStatement.setInt(1,id);

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            ConexaoDB.closeStatement(preparedStatement);
        }
    }

    @Override
    public Object findById(Integer id, boolean ativos) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            String sqlConsultaById = "Select * from usuario where id = ?";
            if (ativos){
                sqlConsultaById = sqlConsultaById + " and ativo = true";
            }
            preparedStatement = connection.prepareStatement(sqlConsultaById);
            preparedStatement.setInt(1,id);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                return getUsuario(resultSet);
            } else {
                throw new UsuarioNotFound("Usuário Not Found");
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }finally {
            ConexaoDB.closeStatement(preparedStatement);
            ConexaoDB.closeResultSet(resultSet);
        }

    }

    @Override
    public List<Object> findAll() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Object> listUsuario = new ArrayList<>();

        try{
            String sqlConsultaAll = "Select * from usuario";
            preparedStatement = connection.prepareStatement(sqlConsultaAll);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                listUsuario.add(getUsuario(resultSet));
            }
            return listUsuario;
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        }finally {
            ConexaoDB.closeStatement(preparedStatement);
            ConexaoDB.closeResultSet(resultSet);
        }
    }


    private Usuario getUsuario(ResultSet resultSet) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(resultSet.getInt("id"));
        usuario.setUsername(resultSet.getString("username"));
        usuario.setNome(resultSet.getString("nome"));
        usuario.setCpf(resultSet.getString("cpf"));
        usuario.setEmail(resultSet.getString("email"));
        usuario.setSenha(resultSet.getString("senha"));
        usuario.setTelefone(resultSet.getLong("telefone"));
        usuario.setAtivo(resultSet.getBoolean("ativo"));

        // 1. Valida a Data de Nascimento
        java.sql.Date sqlDtNascimento = resultSet.getDate("dtnascimento");
        if (sqlDtNascimento != null) {
            usuario.setDtnascimento(sqlDtNascimento.toLocalDate());
        }

        // 2. Valida a Data Limite
        java.sql.Timestamp sqlDtLimite = resultSet.getTimestamp("dtlimite");
        if (sqlDtLimite != null) {
            usuario.setDtlimite(sqlDtLimite.toLocalDateTime());
        }

        // 3. Valida a Data de Alteração
        java.sql.Timestamp sqlDtAlter = resultSet.getTimestamp("dtalter");
        if (sqlDtAlter != null) {
            usuario.setDtalter(sqlDtAlter.toLocalDateTime());
        }

        // 4. Valida a Data de Criação
        java.sql.Timestamp sqlDtCriado = resultSet.getTimestamp("dtcriado");
        if (sqlDtCriado != null) {
            usuario.setDtcriado(sqlDtCriado.toLocalDateTime());
        }

        return usuario;
    }
    public Object findByUsername(String username, boolean ativos) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlConsultaByUsername = "Select * from usuario where username = ?";
            if (ativos){
                sqlConsultaByUsername = sqlConsultaByUsername + " and ativo = true";
            }

            preparedStatement = connection.prepareStatement(sqlConsultaByUsername);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getUsuario(resultSet);
            } else {
                return null;
            }
        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            ConexaoDB.closeResultSet(resultSet);
            ConexaoDB.closeStatement(preparedStatement);
        }
    }
    public Object findByCpf(String cpf,boolean ativos) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlConsultaByCpf = "Select * from usuario where cpf = ?";
            if (ativos){
                sqlConsultaByCpf = sqlConsultaByCpf + " and ativo = true";
            }
            preparedStatement = connection.prepareStatement(sqlConsultaByCpf);
            preparedStatement.setString(1, cpf);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getUsuario(resultSet);
            } else {
                return null;
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            ConexaoDB.closeStatement(preparedStatement);
            ConexaoDB.closeResultSet(resultSet);
        }
    }

    public Object findByEmail(String email,boolean ativos) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            String sqlConsultaByEmail = "Select * from usuario where email = ?";
            if (ativos){
                sqlConsultaByEmail = sqlConsultaByEmail + " and ativo = true";
            }

            preparedStatement = connection.prepareStatement(sqlConsultaByEmail);
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return getUsuario(resultSet);
            } else {
                return null;
            }

        } catch (SQLException sqlException) {
            throw new RuntimeException(sqlException);
        } finally {
            ConexaoDB.closeStatement(preparedStatement);
            ConexaoDB.closeResultSet(resultSet);
        }
    }
}

