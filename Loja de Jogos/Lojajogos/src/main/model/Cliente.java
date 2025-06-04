package main.model;

import main.model.interfaces.CRUD;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.List;

public class Cliente extends ModeloBase implements CRUD<Cliente>, Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private String email;
    private String endereco;
    private String telefone;
    private LocalDate dataCadastro;

    public Cliente(int id, String nome, String email, String endereco, String telefone) {
        super(id);
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.telefone = telefone;
        this.dataCadastro = LocalDate.now();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Detalhes do Cliente:");
        System.out.println("ID: " + getId());
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("Data de Cadastro: " + dataCadastro);
    }

    //Equals and HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(nome, cliente.nome) && Objects.equals(email, cliente.email) && Objects.equals(endereco, cliente.endereco) && Objects.equals(telefone, cliente.telefone) && Objects.equals(dataCadastro, cliente.dataCadastro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, email, endereco, telefone, dataCadastro);
    }

    @Override
    public void cadastrar(Cliente cliente) {
        // Lógica para cadastrar o cliente
        System.out.println("Cliente " + cliente.getNome() + " cadastrado.");
    }

    @Override
    public Cliente buscarPorId(int id) {
        // Lógica para buscar cliente por ID
        System.out.println("Buscando cliente por ID: " + id);
        return null; // Retorna null se não encontrar
    }

    @Override
    public List<Cliente> listarTodos() {
        // Lógica para listar todos os clientes
        System.out.println("Listando clientes.");
        return null;
    }

    @Override
    public void atualizar(Cliente cliente) {
        // Lógica para atualizar o cliente
        System.out.println("Cliente " + cliente.getNome() + " atualizado.");
    }

    @Override
    public void deletar(int id) {
        // Lógica para deletar o cliente
        System.out.println("Cliente com ID " + id + " deletado.");
    }
}