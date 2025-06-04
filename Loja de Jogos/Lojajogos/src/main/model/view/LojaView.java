package main.model.view;

import main.model.controller.ClienteController;
import main.model.controller.JogoController;
import main.model.controller.VendaController;
import main.model.Cliente;
import main.model.Jogo;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LojaView {

    private JogoController jogoController = new JogoController();
    private ClienteController clienteController = new ClienteController();
    private VendaController vendaController = new VendaController();
    private Scanner scanner = new Scanner(System.in);

    public void exibirMenu() {
        while (true) {
            System.out.println("\n--- Loja de Jogos ---");
            System.out.println("1. Cadastrar Jogo");
            System.out.println("2. Listar Jogos");
            System.out.println("3. Atualizar Jogo");
            System.out.println("4. Deletar Jogo");
            System.out.println("5. Cadastrar Cliente");
            System.out.println("6. Listar Clientes");
            System.out.println("7. Atualizar Cliente");
            System.out.println("8. Deletar Cliente");
            System.out.println("9. Cadastrar Venda");
            System.out.println("10. Listar Vendas");
            System.out.println("11. Atualizar Venda");
            System.out.println("12. Deletar Venda");
            System.out.println("13. Adicionar Jogo à Venda");
            System.out.println("14. Remover Jogo da Venda");
            System.out.println("15. Buscar Jogo por Nome");
            System.out.println("16. Buscar Jogo por Gênero");
            System.out.println("17. Buscar Cliente por Nome");
            System.out.println("18. Buscar Cliente por Email");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            processarOpcao(opcao);
        }
    }

    private void processarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                cadastrarJogo();
                break;
            case 2:
                listarJogos();
                break;
            case 3:
                atualizarJogo();
                break;
            case 4:
                deletarJogo();
                break;
            case 5:
                cadastrarCliente();
                break;
            case 6:
                listarClientes();
                break;
            case 7:
                atualizarCliente();
                break;
            case 8:
                deletarCliente();
                break;
            case 9:
                cadastrarVenda();
                break;
            case 10:
                listarVendas();
                break;
            case 11:
                atualizarVenda();
                break;
            case 12:
                deletarVenda();
                break;
            case 13:
                adicionarJogoAVenda();
                break;
            case 14:
                removerJogoDaVenda();
                break;
            case 15:
                buscarJogoPorNome();
                break;
            case 16:
                buscarJogoPorGenero();
                break;
            case 17:
                buscarClientePorNome();
                break;
            case 18:
                buscarClientePorEmail();
                break;
            case 0:
                sair();
                break;
            default:
                opcaoInvalida();
        }
    }

    private void cadastrarJogo() {
        Jogo jogo = obterDadosJogo();
        jogoController.cadastrarJogo(jogo.getNome(), jogo.getGenero(), jogo.getPlataforma(), jogo.getPreco(), jogo.getDescricao(), jogo.getDataLancamento(), jogo.getEstoque());
    }

    private Jogo obterDadosJogo() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Gênero: ");
        String genero = scanner.nextLine();
        System.out.print("Plataforma: ");
        String plataforma = scanner.nextLine();
        System.out.print("Preço: ");
        float preco = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Data de Lançamento (AAAA-MM-DD): ");
        LocalDate dataLancamento = LocalDate.parse(scanner.nextLine());
        System.out.print("Estoque: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();
        return new Jogo(0, nome, genero, plataforma, preco, descricao, dataLancamento, estoque);
    }

    private void listarJogos() {
        jogoController.listarJogos();
    }

    private void atualizarJogo() {
        int id = obterIdJogo();
        Jogo jogo = obterDadosJogo();
        jogoController.atualizarJogo(id, jogo.getNome(), jogo.getGenero(), jogo.getPlataforma(), jogo.getPreco(), jogo.getDescricao(), jogo.getDataLancamento(), jogo.getEstoque());
    }

    private int obterIdJogo() {
        System.out.print("ID do Jogo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    private void deletarJogo() {
        int id = obterIdJogo();
        jogoController.deletarJogo(id);
    }

    private void cadastrarCliente() {
        Cliente cliente = obterDadosCliente();
        clienteController.cadastrarCliente(cliente.getNome(), cliente.getEmail(), cliente.getEndereco(), cliente.getTelefone());
    }

    private Cliente obterDadosCliente() {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Endereço: ");
        String endereco = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        return new Cliente(0, nome, email, endereco, telefone);
    }

    private void listarClientes() {
        clienteController.listarClientes();
    }

    private void atualizarCliente() {
        int id = obterIdCliente();
        Cliente cliente = obterDadosCliente();
        clienteController.atualizarCliente(id, cliente.getNome(), cliente.getEmail(), cliente.getEndereco(), cliente.getTelefone());
    }

    private int obterIdCliente() {
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    private void deletarCliente() {
        int id = obterIdCliente();
        clienteController.deletarCliente(id);
    }

    private void cadastrarVenda() {
        int idCliente = obterIdCliente();
        Cliente cliente = clienteController.buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        System.out.print("Forma de Pagamento: ");
        String formaPagamento = scanner.nextLine();
        vendaController.cadastrarVenda(cliente, formaPagamento);
    }

    private void listarVendas() {
        vendaController.listarVendas();
    }

    private void atualizarVenda() {
        int idVenda = obterIdVenda();
        int idCliente = obterIdCliente();
        Cliente cliente = clienteController.buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }
        System.out.print("Nova Forma de Pagamento: ");
        String formaPagamento = scanner.nextLine();
        vendaController.atualizarVenda(idVenda, cliente, formaPagamento);
    }

    private int obterIdVenda() {
        System.out.print("ID da Venda: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        return id;
    }

    private void deletarVenda() {
        int id = obterIdVenda();
        vendaController.deletarVenda(id);
    }

    private void adicionarJogoAVenda() {
        int idVenda = obterIdVenda();
        int idJogo = obterIdJogo();
        Jogo jogo = jogoController.buscarJogoPorId(idJogo);
        if (jogo == null) {
            System.out.println("Jogo não encontrado.");
            return;
        }
        vendaController.adicionarJogoNaVenda(idVenda, jogo);
    }

    private void removerJogoDaVenda() {
        int idVenda = obterIdVenda();
        int idJogo = obterIdJogo();
        Jogo jogo = jogoController.buscarJogoPorId(idJogo);
        if (jogo == null) {
            System.out.println("Jogo não encontrado.");
            return;
        }
        vendaController.removerJogoDaVenda(idVenda, jogo);
    }

    private void buscarJogoPorNome() {
        System.out.print("Nome do Jogo a Buscar: ");
        String nome = scanner.nextLine();
        List<Jogo> resultados = jogoController.buscarJogoPorNome(nome);
        exibirResultadosJogo(resultados, nome);
    }

    private void buscarJogoPorGenero() {
        System.out.print("Gênero do Jogo a Buscar: ");
        String genero = scanner.nextLine();
        List<Jogo> resultados = jogoController.buscarJogoPorGenero(genero);
        exibirResultadosJogo(resultados, genero);
    }

    private void exibirResultadosJogo(List<Jogo> resultados, String criterio) {
        if (resultados.isEmpty()) {
            System.out.println("Nenhum jogo encontrado com o critério " + criterio);
        } else {
            System.out.println("Resultados da Busca:");
            for (Jogo jogo : resultados) {
                jogo.exibirDetalhes();
                System.out.println("---");
            }
        }
    }

    private void buscarClientePorNome() {
        System.out.print("Nome do Cliente a Buscar: ");
        String nome = scanner.nextLine();
        List<Cliente> resultados = clienteController.buscarClientePorNome(nome);
        exibirResultadosCliente(resultados, nome);
    }

    private void buscarClientePorEmail() {
        System.out.print("Email do Cliente a Buscar: ");
        String email = scanner.nextLine();
        Cliente cliente = clienteController.buscarClientePorEmail(email);
        if (cliente == null) {
            System.out.println("Nenhum cliente encontrado com o email " + email);
        } else {
            System.out.println("Resultado da Busca:");
            cliente.exibirDetalhes();
        }
    }

    private void exibirResultadosCliente(List<Cliente> resultados, String criterio) {
        if (resultados.isEmpty()) {
            System.out.println("Nenhum cliente encontrado com o critério " + criterio);
        } else {
            System.out.println("Resultados da Busca:");
            for (Cliente cliente : resultados) {
                cliente.exibirDetalhes();
                System.out.println("---");
            }
        }
    }

    private void sair() {
        System.out.println("Saindo...");
        System.exit(0);
    }

    private void opcaoInvalida() {
        System.out.println("Opção inválida.");
    }
}