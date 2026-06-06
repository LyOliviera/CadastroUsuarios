package main.java.listener;

import main.java.config.ConexaoDB;
import main.java.dao.UsuarioDao;
import main.java.model.Usuario;
import main.java.util.CpfValidador;
import main.java.util.EmailValidador;
import main.java.util.SenhaValidador;
import main.java.util.TelefoneValidador;

import java.time.LocalDate;

public class UsuarioListener implements CrudListener {

    UsuarioDao usuarioDao = new UsuarioDao(ConexaoDB.getConections(), this);

    @Override
    public void prePersist(Object horcrux) {

        Usuario usuario = (Usuario) horcrux;

        /*Valida Username*/
        if (usuario.getUsername() == null || usuario.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("O Username é um campo obrigatório e não pode estar vazio. Tente outra vez!");
        }

        Usuario userEncontrado = (Usuario) usuarioDao.findByUsername(usuario.getUsername(), false);

        if (userEncontrado != null) {
            if (usuario.getId() != userEncontrado.getId()) {
                throw new IllegalArgumentException("Esse username já está sendo utilizado em outro usuário (ativo ou inativo).");
            }
        }

        /*Valida Nome*/

        if (usuario.getNome() != null) {
            String[] arrayNomeSobrenome = usuario.getNome().trim().split(" ");
            if (arrayNomeSobrenome.length < 2) {
                throw new IllegalArgumentException("O usuário deve conter no mínimo um nome e um sobrenome separados por espaço");
            } else {
                System.out.println("O nome do usuário foi devidamente preenchido");
            }

        }

        //Valida CPF//

        if (usuario.getCpf() == null) {
            throw new IllegalArgumentException("O CPF é um campo obrigatório e não foi preenchido");
        }

        Usuario cpfEncontrado = (Usuario) usuarioDao.findByCpf(usuario.getCpf(), true);
        boolean existeCpf = false;
        if (cpfEncontrado != null && usuario.getId() != cpfEncontrado.getId()) {
            existeCpf = true;
        }

        if (existeCpf == true) {
            throw new IllegalArgumentException("Cada CPF deve estar ligado a apenas um usuário ativo e o número inserido já se encontra em uso.");
        }

        boolean cpfValido = CpfValidador.isValido(usuario.getCpf());
        if (cpfValido == false) {
            throw new IllegalArgumentException("Este CPF não é válido. Verifique e digite corretamente o número para salvar");
        }


        /*Valida o email*/

        if (usuario.getEmail() == null) {
            throw new IllegalArgumentException("O campo email deve ser preenchido");
        }

        Usuario emailEncontrado = (Usuario) usuarioDao.findByEmail(usuario.getEmail(), true);
        boolean existeEmail = false;


        if (emailEncontrado != null && usuario.getId() != emailEncontrado.getId()) {
            existeEmail = true;
        }

        if (existeEmail == true) {
            throw new IllegalArgumentException("Cada Email deve estar ligado a apenas um usuário ativo e este inserido já se encontra em um usuário ativo.");
        }

        boolean emailValido = EmailValidador.isValido(usuario.getEmail());
        if (emailValido == false) {
            throw new IllegalArgumentException("Para ser um email válido ele deve conter @ e domínio");
        }

        /*Valida Senha*/

        if (usuario.getSenha() == null || usuario.getSenha().length() < 8) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 8 dígitos");
        }

        boolean senhaValida = SenhaValidador.senhaPadrao(usuario.getSenha());
        if (senhaValida == false) {
            throw new IllegalArgumentException("Essa senha não atende aos requisitos. Ela deve conter no mínimo 8 caractere, uma letra maiúscula e uma minúscula, um numeral, e um símbolo.");
        }

        try{
            String senhaCodificada = SenhaValidador.criptografarSenha(usuario.getSenha());
            usuario.setSenha(senhaCodificada);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível criptografar a senha do usuário", e);
        }
        /*Valida Data de Nascimento*/

        if (usuario.getDtnascimento() != null) {
            if (usuario.getDtnascimento().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Essa não é uma data válida de nascimento.");
            }
        }

        //Valida Telefone//

        if (usuario.getTelefone() == null) {
            throw new IllegalArgumentException("O telefone não pode estar vazio");
        }

        boolean telefoneValido = TelefoneValidador.isvalido(usuario.getTelefone());
        if (telefoneValido == false) {
            throw new IllegalArgumentException("Este número de telefone é inválido");
        }
    }

    public void preRemove(Object horcrux){
        Usuario usuario = (Usuario) horcrux;
        if (usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido para exclusão.");
        }
        usuarioDao.findById(usuario.getId(), false);
    }

    @Override
    public void postLoad(Object horcrux){
        Usuario usuario = (Usuario) horcrux;

    }
}

