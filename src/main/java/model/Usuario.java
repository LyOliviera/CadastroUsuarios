package main.java.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Usuario {

    private int id;
    private String username;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private LocalDate dtnascimento;
    private String telefone;
    private Boolean ativo;
    private LocalDateTime dtlimite;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDtnascimento() {
        return dtnascimento;
    }

    public void setDtnascimento(LocalDate dtnascimento) {
        this.dtnascimento = dtnascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDtlimite() {
        return dtlimite;
    }

    public void setDtlimite(LocalDateTime dtlimite) {
        this.dtlimite = dtlimite;
    }

    public LocalDateTime getDtalter() {
        return dtalter;
    }

    public void setDtalter(LocalDateTime dtalter) {
        this.dtalter = dtalter;
    }

    public LocalDateTime getDtcriado() {
        return dtcriado;
    }

    public void setDtcriado(LocalDateTime dtcriado) {
        this.dtcriado = dtcriado;
    }

    private LocalDateTime dtalter;
    private LocalDateTime dtcriado;
}