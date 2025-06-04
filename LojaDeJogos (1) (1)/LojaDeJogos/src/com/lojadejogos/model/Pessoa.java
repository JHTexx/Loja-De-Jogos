package com.lojadejogos.model;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String cpf;
    private String email;

    public Pessoa(String nome, String cpf, String email) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
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

    public abstract String exibirDetalhes();

    @Override
    public String toString() {
        return "Nome: " + nome + ", CPF: " + cpf + ", Email: " + email;
    }
}