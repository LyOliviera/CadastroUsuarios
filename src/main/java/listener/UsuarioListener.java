package main.java.listener;

import main.java.model.Usuario;
import main.java.util.CpfValidador;
import main.java.util.EmailValidador;
import main.java.util.SenhaValidador;
import main.java.util.TelefoneValidador;

import java.time.LocalDate;

public class UsuarioListener implements CrudListener {


    @Override
    public void prePersist(Object horcrux) {

        Usuario usuario = (Usuario) horcrux;

        /*Valida Username*/

        if (usuario.getUsername() != null) {
            boolean existeUsername = true/*Consulta o Username no banco*/;
            if (existeUsername == true) {
                throw new IllegalArgumentException("Este nome de usuário já está sendo utilizado. Tente outro!");
            } else {
                System.out.println("O nome de usuário  " + usuario.getUsername() + " foi aceito.");
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

        /*Valida CPF*/
        boolean cpfValido = CpfValidador.isValido(usuario.getCpf());
        boolean existeCpf = true; /* Consulta o CPF no banco Select 1 from usuario where username = ? and ativo = 'S'*/
        if (existeCpf == true) {
            throw new IllegalArgumentException("Cada CPF deve estar ligado a apenas um usuário ativo e o número inserido já se encontra em uso.");
        }
        if (usuario.getCpf() == null) {
            throw new IllegalArgumentException("O CPF é um campo obrigatório e não foi preenchido");
        }
        if (cpfValido == false) {
            throw new IllegalArgumentException("Este CPF não é válido. Verifique e digite corretamente o número para salvar");
        }

        /*Valida o email*/
        boolean emailValido = EmailValidador.isValido(usuario.getEmail());
        boolean existeEmail = true; //Construir consulta where email = ? and ativo = 'S'

        if (usuario.getEmail() == null) {
            throw new IllegalArgumentException("O campo email deve ser preenchido");
        }
        if (emailValido == false) {
            throw new IllegalArgumentException("Para ser um email válido ele deve conter @ e domínio");
        }

        /*Valida Senha*/
        boolean senhaValida = SenhaValidador.senhaPadrao(usuario.getSenha());

        if (usuario.getSenha() == null || usuario.getSenha().length() < 8) {
            throw new IllegalArgumentException("A senha deve conter no mínimo 8 dígitos");
        }
        if (senhaValida == false) {
            throw new IllegalArgumentException("Essa senha não atende aos requisitos. Ela deve conter no mínimo 8 caractere, uma letra maiúscula e uma minúscula, um numeral, e um símbolo.");
        }
        /*Valida Data de Nascimento*/
        if (usuario.getDtnascimento() != null) {
            if (usuario.getDtnascimento().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Essa não é uma data válida de nascimento.");
            }
        }
        boolean telefoneValido = TelefoneValidador.isvalido(usuario.getTelefone());

        if (usuario.getTelefone() != null) {
            throw new IllegalArgumentException("O telefone não pode estar vazio");
        }
        if (telefoneValido == false) {
            throw new IllegalArgumentException("Este número de telefone é inválido");
        }
    }

    public void preRemove(Object horcrux){
        Usuario usuario = (Usuario) horcrux;

    }

    @Override
    public void postLoad(Object horcrux){
        Usuario usuario = (Usuario) horcrux;

    }
}

