package com.lojadejogos.model;

import java.time.LocalDate;

public class Cliente extends Pessoa {

    private static final long serialVersionUID = 1L;

    private int idCliente;
    private LocalDate dataCadastro;
    private static int proximoId = 1;

    public Cliente(String nome, String cpf, String email) {
        super(nome, cpf, email);
        this.idCliente = proximoId++;
        this.dataCadastro = LocalDate.now();
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public String exibirDetalhes() {
        return "Cliente ID: " + idCliente + "\n" +
               "Nome: " + getNome() + "\n" +
               "CPF: " + getCpf() + "\n" +
               "Email: " + getEmail() + "\n" +
               "Data de Cadastro: " + dataCadastro.toString();
    }

    @Override
    public String toString() {
        return "Cliente: " + getNome() + " (ID: " + idCliente + ")";
    }
}