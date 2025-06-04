package com.lojadejogos.model;

import java.math.BigDecimal;

public class Funcionario extends Pessoa implements Autenticavel {

    private static final long serialVersionUID = 1L;

    private int idFuncionario;
    private String cargo;
    private BigDecimal salario;
    private String senha;
    private static int proximoId = 1;

    public Funcionario(String nome, String cpf, String email, String cargo, BigDecimal salario, String senha) {
        super(nome, cpf, email);
        this.idFuncionario = proximoId++;
        this.cargo = cargo;
        this.salario = salario;
        this.senha = senha;
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    @Override
    public boolean autenticar(String senhaFornecida) {
        return this.senha.equals(senhaFornecida);
    }

    @Override
    public String exibirDetalhes() {
        return "Funcionário ID: " + idFuncionario + "\n" +
               "Nome: " + getNome() + "\n" +
               "CPF: " + getCpf() + "\n" +
               "Email: " + getEmail() + "\n" +
               "Cargo: " + cargo + "\n" +
               "Salário: R$ " + String.format("%.2f", salario);
    }

    @Override
    public String toString() {
        return "Funcionário: " + getNome() + " (Cargo: " + cargo + ")";
    }
}