package com.lojadejogos.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Jogo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idJogo;
    private String nome;
    private String genero;
    private String plataforma;
    private BigDecimal preco;
    private int quantidadeEstoque;
    private static int proximoId = 1;

    public Jogo(String nome, String genero, String plataforma, BigDecimal preco, int quantidadeEstoque) {
        this.idJogo = proximoId++;
        this.nome = nome;
        this.genero = genero;
        this.plataforma = plataforma;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public int getIdJogo() {
        return idJogo;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }
    public BigDecimal getPreco() { return preco; }
    public void setPreco(BigDecimal preco) { this.preco = preco; }
    public int getQuantidadeEstoque() { return quantidadeEstoque; }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque >= 0) {
            this.quantidadeEstoque = quantidadeEstoque;
        } else {
            System.err.println("Quantidade em estoque não pode ser negativa.");
        }
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.quantidadeEstoque += quantidade;
        }
    }

    public boolean removerEstoque(int quantidade) {
        if (quantidade > 0 && this.quantidadeEstoque >= quantidade) {
            this.quantidadeEstoque -= quantidade;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Jogo ID: " + idJogo + "\n" +
               "Nome: " + nome + "\n" +
               "Gênero: " + genero + "\n" +
               "Plataforma: " + plataforma + "\n" +
               "Preço: R$ " + String.format("%.2f", preco) + "\n" +
               "Estoque: " + quantidadeEstoque + " unidades";
    }
}