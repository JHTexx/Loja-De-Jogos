package main.model;

import main.model.interfaces.CRUD;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Venda extends ModeloBase implements CRUD<Venda>, Serializable {

      private static final long serialVersionUID = 1L;

    private Cliente cliente;
    private LocalDate dataVenda;
    private List<Jogo> jogosVendidos;
    private float valorTotal;
    private String formaPagamento;

    public Venda(int id, Cliente cliente, String formaPagamento) {
        super(id);
        this.cliente = cliente;
        this.dataVenda = LocalDate.now();
        this.jogosVendidos = new ArrayList<>();
        this.valorTotal = 0.0f;
        this.formaPagamento = formaPagamento;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public List<Jogo> getJogosVendidos() {
        return jogosVendidos;
    }

    public void setJogosVendidos(List<Jogo> jogosVendidos) {
        this.jogosVendidos = jogosVendidos;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void adicionarJogo(Jogo jogo) {
        this.jogosVendidos.add(jogo);
        this.valorTotal += jogo.getPreco();
    }

    public void removerJogo(Jogo jogo) {
        this.jogosVendidos.remove(jogo);
        this.valorTotal -= jogo.getPreco();
    }

    public void calcularTotalVenda() {
        this.valorTotal = 0.0f;
        for (Jogo jogo : jogosVendidos) {
            this.valorTotal += jogo.getPreco();
        }
    }

    public void registrarVenda() {
        // Lógica para registrar a venda
        System.out.println("Venda " + getId() + " registrada para o cliente " + cliente.getNome());
    }

    public void cancelarVenda() {
        // Lógica para cancelar a venda
        System.out.println("Venda " + getId() + " cancelada.");
    }

    public void listarVendas() {
        // Lógica para listar as vendas
        System.out.println("Listando vendas.");
    }

    public Venda buscarVendaPorId(int idVenda) {
        // Lógica para buscar venda por ID
        System.out.println("Buscando venda por ID: " + idVenda);
        return null; // Retorna null se não encontrar
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Detalhes da Venda:");
        System.out.println("ID: " + getId());
        System.out.println("Cliente: " + cliente.getNome());
        System.out.println("Data da Venda: " + dataVenda);
        System.out.println("Jogos Vendidos:");
        for (Jogo jogo : jogosVendidos) {
            System.out.println("- " + jogo.getNome());
        }
        System.out.println("Valor Total: " + valorTotal);
        System.out.println("Forma de Pagamento: " + formaPagamento);
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venda venda = (Venda) o;
        return Float.compare(venda.valorTotal, valorTotal) == 0 && Objects.equals(cliente, venda.cliente) && Objects.equals(dataVenda, venda.dataVenda) && Objects.equals(jogosVendidos, venda.jogosVendidos) && Objects.equals(formaPagamento, venda.formaPagamento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cliente, dataVenda, jogosVendidos, valorTotal, formaPagamento);
    }

    @Override
    public void cadastrar(Venda venda) {
        // Lógica para cadastrar a venda
        System.out.println("Venda cadastrada.");
    }

    @Override
    public Venda buscarPorId(int id) {
        // Lógica para buscar venda por ID
        System.out.println("Buscando venda por ID: " + id);
        return null;
    }

    @Override
    public List<Venda> listarTodos() {
        // Lógica para listar todas as vendas
        System.out.println("Listando todas as vendas.");
        return null;
    }

    @Override
    public void atualizar(Venda venda) {
        // Lógica para atualizar a venda
        System.out.println("Venda atualizada.");
    }

    @Override
    public void deletar(int id) {
        // Lógica para deletar a venda
        System.out.println("Venda com ID " + id + " deletada.");
    }
}