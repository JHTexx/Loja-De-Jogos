package com.lojadejogos.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;

    private int idVenda;
    private Cliente cliente;
    private List<Jogo> jogosVendidos;
    private Funcionario funcionarioResponsavel;
    private LocalDateTime dataHoraVenda;
    private BigDecimal valorTotal;
    private static int proximoId = 1;

    public Venda(Cliente cliente, List<Jogo> jogosVendidos, Funcionario funcionarioResponsavel) {
        this.idVenda = proximoId++;
        this.cliente = cliente;
        this.jogosVendidos = new ArrayList<>(jogosVendidos); // Cria cópia defensiva
        this.funcionarioResponsavel = funcionarioResponsavel;
        this.dataHoraVenda = LocalDateTime.now();
        this.valorTotal = calcularValorTotal();
    }

    public static void setProximoId(int id) {
        proximoId = id;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Jogo> getJogosVendidos() {
        return new ArrayList<>(jogosVendidos); // Retorna cópia defensiva
    }

    public Funcionario getFuncionarioResponsavel() {
        return funcionarioResponsavel;
    }

    public LocalDateTime getDataHoraVenda() {
        return dataHoraVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    private BigDecimal calcularValorTotal() {
        BigDecimal total = BigDecimal.ZERO;
        if (this.jogosVendidos != null) {
            for (Jogo jogo : this.jogosVendidos) {
                total = total.add(jogo.getPreco());
            }
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venda ID: ").append(idVenda).append("\n");
        sb.append("Data/Hora: ").append(dataHoraVenda.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
        sb.append("Cliente: ").append(cliente.getNome()).append(" (ID: ").append(cliente.getIdCliente()).append(")\n");
        if (funcionarioResponsavel != null) {
            sb.append("Funcionário: ").append(funcionarioResponsavel.getNome()).append("\n");
        }
        sb.append("Jogos Vendidos:\n");
        for (Jogo jogo : jogosVendidos) {
            sb.append("  - ").append(jogo.getNome()).append(" (R$ ").append(String.format("%.2f", jogo.getPreco())).append(")\n");
        }
        sb.append("Valor Total: R$ ").append(String.format("%.2f", valorTotal));
        return sb.toString();
    }
}