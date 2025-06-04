package com.lojadejogos;

import com.lojadejogos.controller.LojaController;
import com.lojadejogos.model.Cliente;
import com.lojadejogos.model.Funcionario;
import com.lojadejogos.model.Jogo;
import com.lojadejogos.model.Venda;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AppMain {

    private static LojaController controller = new LojaController();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Sistema de Gerenciamento da Loja de Jogos!");

        // Lógica para o primeiro funcionário
        if (controller.listarFuncionarios().isEmpty()) {
            System.out.println("\n*** CONFIGURAÇÃO INICIAL NECESSÁRIA ***");
            System.out.println("Nenhum funcionário cadastrado. Vamos cadastrar o primeiro administrador.");
            cadastrarPrimeiroFuncionarioSemLogin();
        }

        if (controller.getFuncionarioLogado() == null && !loginFuncionario()) {
            System.out.println("Falha no login. Encerrando o sistema.");
            controller.salvarTodosOsDados(); 
            return;
        }

        exibirMenuPrincipal();

        System.out.println("Encerrando o sistema. Obrigado!");
        controller.salvarTodosOsDados(); 
        scanner.close();
    }

    private static void cadastrarPrimeiroFuncionarioSemLogin() {
        System.out.println("\n--- Cadastro do Primeiro Funcionário (Admin) ---");
        // Este método não requer que um funcionário esteja logado
        // É uma exceção para o setup inicial.
        try {
            System.out.print("Nome do administrador: ");
            String nome = scanner.nextLine();
            System.out.print("CPF (apenas números): ");
            String cpf = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Cargo (sugestão: Administrador): ");
            String cargo = scanner.nextLine();
            System.out.print("Salário (ex: 5000.00): ");
            BigDecimal salario = scanner.nextBigDecimal();
            scanner.nextLine(); 
            System.out.print("Senha para login: ");
            String senha = scanner.nextLine();

            // Chamada direta ao controller, que precisa permitir este primeiro cadastro
            // sem verificar 'funcionarioLogado' se a lista de funcionários estiver vazia.
            // A lógica no LojaController.cadastrarFuncionario foi ajustada para isso.
            if (controller.cadastrarFuncionario(nome, cpf, email, cargo, salario, senha)) {
                System.out.println("Primeiro funcionário administrador cadastrado com sucesso!");
                System.out.println("Por favor, faça login com as credenciais cadastradas.");
            } else {
                System.out.println("Falha ao cadastrar o primeiro funcionário. O sistema pode não funcionar corretamente.");
                System.out.println("Verifique os logs ou tente reiniciar. Se o problema persistir, apague os arquivos .dat e tente novamente.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada. Verifique os tipos de dados inseridos (ex: salário deve ser um número).");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado no cadastro inicial: " + e.getMessage());
        }
    }


    private static boolean loginFuncionario() {
        System.out.println("\n--- Login de Funcionário ---");

        if (controller.listarFuncionarios().isEmpty()){
             System.out.println("Nenhum funcionário cadastrado no sistema. Impossível logar.");
             // A lógica de cadastrarPrimeiroFuncionarioSemLogin deve ter sido chamada antes.
             // Se ainda assim cair aqui, é um estado inesperado.
             return false;
        }

        int tentativas = 3;
        while (tentativas > 0) {
            try {
                System.out.print("ID do Funcionário: ");
                int id = scanner.nextInt();
                scanner.nextLine(); 
                System.out.print("Senha: ");
                String senha = scanner.nextLine();

                if (controller.autenticarFuncionario(id, senha)) {
                    System.out.println("Login bem-sucedido! Bem-vindo, " + controller.getFuncionarioLogado().getNome() + ".");
                    return true;
                } else {
                    System.out.println("ID ou senha incorretos.");
                    tentativas--;
                    System.out.println("Tentativas restantes: " + tentativas);
                }
            } catch (InputMismatchException e) {
                System.out.println("ID inválido. Por favor, insira um número.");
                scanner.nextLine(); 
                tentativas--;
                System.out.println("Tentativas restantes: " + tentativas);
            }
        }
        return false;
    }

    private static void exibirMenuPrincipal() {
        boolean executando = true;
        while (executando) {
            System.out.println("\n--- Menu Principal ---");
            System.out.println("Funcionário Logado: " + (controller.getFuncionarioLogado() != null ? controller.getFuncionarioLogado().getNome() : "Nenhum"));
            System.out.println("1. Gerenciar Jogos");
            System.out.println("2. Gerenciar Clientes");
            System.out.println("3. Gerenciar Funcionários");
            System.out.println("4. Realizar Venda");
            System.out.println("5. Listar Vendas");
            System.out.println("6. Logout");
            System.out.println("0. Sair e Salvar");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcao) {
                    case 1:
                        menuGerenciarJogos();
                        break;
                    case 2:
                        menuGerenciarClientes();
                        break;
                    case 3:
                        menuGerenciarFuncionarios();
                        break;
                    case 4:
                        realizarVenda();
                        break;
                    case 5:
                        listarVendas();
                        break;
                    case 6:
                        controller.logoutFuncionario();
                        System.out.println("Logout realizado. Faça login novamente para continuar.");
                        if (!loginFuncionario()) {
                            System.out.println("Falha no login. Encerrando o sistema.");
                            executando = false; 
                        }
                        break;
                    case 0:
                        executando = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scanner.nextLine(); 
            }
        }
    }

    private static void menuGerenciarJogos() {
        System.out.println("\n--- Gerenciar Jogos ---");
        System.out.println("1. Adicionar Jogo");
        System.out.println("2. Listar Jogos");
        System.out.println("3. Atualizar Jogo");
        System.out.println("4. Remover Jogo");
        System.out.println("5. Buscar Jogo");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    adicionarNovoJogo();
                    break;
                case 2:
                    listarTodosJogos();
                    break;
                case 3:
                    atualizarJogoExistente();
                    break;
                case 4:
                    removerJogoExistente();
                    break;
                case 5:
                    buscarJogoEspecifico();
                    break;
                case 0:
                    return; 
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); 
        }
    }

    private static void adicionarNovoJogo() {
        System.out.println("\n--- Adicionar Novo Jogo ---");
        try {
            System.out.print("Nome do jogo: ");
            String nome = scanner.nextLine();
            System.out.print("Gênero: ");
            String genero = scanner.nextLine();
            System.out.print("Plataforma: ");
            String plataforma = scanner.nextLine();
            System.out.print("Preço (ex: 199.99): ");
            BigDecimal preco = scanner.nextBigDecimal();
            System.out.print("Quantidade em estoque: ");
            int estoque = scanner.nextInt();
            scanner.nextLine(); 

            if (controller.adicionarJogo(nome, genero, plataforma, preco, estoque)) {
                System.out.println("Jogo adicionado com sucesso!");
            } else {
                System.out.println("Falha ao adicionar jogo. Verifique os dados ou o log.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada. Verifique os tipos de dados inseridos.");
            scanner.nextLine(); 
        }
    }

    private static void listarTodosJogos() {
        System.out.println("\n--- Lista de Jogos ---");
        List<Jogo> jogos = controller.listarJogos();
        if (jogos.isEmpty()) {
            System.out.println("Nenhum jogo cadastrado.");
            return;
        }
        for (Jogo jogo : jogos) {
            System.out.println("-------------------------");
            System.out.println(jogo.toString());
        }
        System.out.println("-------------------------");
    }

    private static void atualizarJogoExistente() {
        System.out.println("\n--- Atualizar Jogo ---");
        try {
            System.out.print("ID do jogo a ser atualizado: ");
            int idJogo = scanner.nextInt();
            scanner.nextLine();

            Optional<Jogo> jogoOpt = controller.buscarJogoPorId(idJogo);
            if (!jogoOpt.isPresent()) {
                System.out.println("Jogo não encontrado com o ID: " + idJogo);
                return;
            }
            Jogo jogoAtual = jogoOpt.get();
            System.out.println("Dados atuais: \n" + jogoAtual.toString());

            System.out.print("Novo nome (ou enter para manter '" + jogoAtual.getNome() + "'): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) nome = jogoAtual.getNome();

            System.out.print("Novo gênero (ou enter para manter '" + jogoAtual.getGenero() + "'): ");
            String genero = scanner.nextLine();
            if (genero.isEmpty()) genero = jogoAtual.getGenero();

            System.out.print("Nova plataforma (ou enter para manter '" + jogoAtual.getPlataforma() + "'): ");
            String plataforma = scanner.nextLine();
            if (plataforma.isEmpty()) plataforma = jogoAtual.getPlataforma();

            System.out.print("Novo preço (ou enter para manter '" + jogoAtual.getPreco() + "'): ");
            String precoStr = scanner.nextLine();
            BigDecimal preco = precoStr.isEmpty() ? jogoAtual.getPreco() : new BigDecimal(precoStr);

            System.out.print("Nova quantidade em estoque (ou enter para manter '" + jogoAtual.getQuantidadeEstoque() + "'): ");
            String estoqueStr = scanner.nextLine();
            int estoque = estoqueStr.isEmpty() ? jogoAtual.getQuantidadeEstoque() : Integer.parseInt(estoqueStr);


            if (controller.atualizarJogo(idJogo, nome, genero, plataforma, preco, estoque)) {
                System.out.println("Jogo atualizado com sucesso!");
            } else {
                System.out.println("Falha ao atualizar jogo.");
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Erro de entrada. Verifique os tipos de dados inseridos.");
            scanner.nextLine(); 
        }
    }

    private static void removerJogoExistente() {
        System.out.println("\n--- Remover Jogo ---");
        try {
            System.out.print("ID do jogo a ser removido: ");
            int idJogo = scanner.nextInt();
            scanner.nextLine();

            if (controller.removerJogo(idJogo)) {
                System.out.println("Jogo removido com sucesso!");
            } else {
                System.out.println("Falha ao remover jogo. Verifique se o ID existe ou se o jogo está em alguma venda.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido.");
            scanner.nextLine();
        }
    }

    private static void buscarJogoEspecifico() {
        System.out.println("\n--- Buscar Jogo ---");
        System.out.println("Buscar por: 1. ID | 2. Nome | 3. Gênero | 4. Plataforma");
        System.out.print("Opção: ");
        try {
            int tipo = scanner.nextInt();
            scanner.nextLine();
            List<Jogo> jogosEncontrados = new ArrayList<>();

            if (tipo == 1) {
                System.out.print("Digite o ID do jogo: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                controller.buscarJogoPorId(id).ifPresent(jogosEncontrados::add);
            } else {
                System.out.print("Digite o termo de busca: ");
                String termo = scanner.nextLine();
                if (tipo == 2) jogosEncontrados = controller.buscarJogo(termo, "nome");
                else if (tipo == 3) jogosEncontrados = controller.buscarJogo(termo, "genero");
                else if (tipo == 4) jogosEncontrados = controller.buscarJogo(termo, "plataforma");
                else { System.out.println("Tipo de busca inválido."); return; }
            }

            if (jogosEncontrados.isEmpty()) {
                System.out.println("Nenhum jogo encontrado com os critérios fornecidos.");
            } else {
                System.out.println("Jogos encontrados:");
                for (Jogo jogo : jogosEncontrados) {
                    System.out.println("-------------------------");
                    System.out.println(jogo.toString());
                }
                System.out.println("-------------------------");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine();
        }
    }

    private static void menuGerenciarClientes() {
        System.out.println("\n--- Gerenciar Clientes ---");
        System.out.println("1. Cadastrar Novo Cliente");
        System.out.println("2. Listar Todos os Clientes");
        System.out.println("3. Atualizar Dados de Cliente");
        System.out.println("4. Remover Cliente");
        System.out.println("5. Buscar Cliente");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarNovoCliente();
                    break;
                case 2:
                    listarTodosClientes();
                    break;
                case 3:
                    atualizarClienteExistente();
                    break;
                case 4:
                    removerClienteExistente();
                    break;
                case 5:
                    buscarClienteEspecifico();
                    break;
                case 0:
                    return; 
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); 
        }
    }

    private static void cadastrarNovoCliente() {
        System.out.println("\n--- Cadastrar Novo Cliente ---");
        try {
            System.out.print("Nome do cliente: ");
            String nome = scanner.nextLine();
            System.out.print("CPF do cliente (apenas números): ");
            String cpf = scanner.nextLine();
            System.out.print("Email do cliente: ");
            String email = scanner.nextLine();

            if (controller.cadastrarCliente(nome, cpf, email)) {
                System.out.println("Cliente cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar cliente. Verifique se o CPF já existe ou o log para mais detalhes.");
            }
        } catch (Exception e) { 
            System.out.println("Erro de entrada ao cadastrar cliente: " + e.getMessage());
        }
    }

    private static void listarTodosClientes() {
        System.out.println("\n--- Lista de Clientes ---");
        List<Cliente> clientes = controller.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }
        for (Cliente cliente : clientes) {
            System.out.println("-------------------------");
            System.out.println(cliente.exibirDetalhes());
        }
        System.out.println("-------------------------");
    }

    private static void atualizarClienteExistente() {
        System.out.println("\n--- Atualizar Dados de Cliente ---");
        try {
            System.out.print("ID do cliente a ser atualizado: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine(); 

            Optional<Cliente> clienteOpt = controller.buscarClientePorId(idCliente);
            if (!clienteOpt.isPresent()) {
                System.out.println("Cliente não encontrado com o ID: " + idCliente);
                return;
            }
            Cliente clienteAtual = clienteOpt.get();
            System.out.println("Dados atuais: \n" + clienteAtual.exibirDetalhes());

            System.out.print("Novo nome (ou enter para manter '" + clienteAtual.getNome() + "'): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) nome = clienteAtual.getNome();

            System.out.print("Novo CPF (ou enter para manter '" + clienteAtual.getCpf() + "'): ");
            String cpf = scanner.nextLine();
            if (cpf.isEmpty()) cpf = clienteAtual.getCpf();

            System.out.print("Novo email (ou enter para manter '" + clienteAtual.getEmail() + "'): ");
            String email = scanner.nextLine();
            if (email.isEmpty()) email = clienteAtual.getEmail();

            if (controller.atualizarCliente(idCliente, nome, cpf, email)) {
                System.out.println("Dados do cliente atualizados com sucesso!");
            } else {
                System.out.println("Falha ao atualizar dados do cliente. Verifique se o novo CPF já existe.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("Erro de entrada ao atualizar cliente: " + e.getMessage());
        }
    }

    private static void removerClienteExistente() {
        System.out.println("\n--- Remover Cliente ---");
        try {
            System.out.print("ID do cliente a ser removido: ");
            int idCliente = scanner.nextInt();
            scanner.nextLine(); 

            if (controller.removerCliente(idCliente)) {
                System.out.println("Cliente removido com sucesso!");
            } else {
                System.out.println("Falha ao remover cliente. Verifique se o ID existe ou se o cliente possui vendas registradas.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
            scanner.nextLine(); 
        }
    }

    private static void buscarClienteEspecifico() {
        System.out.println("\n--- Buscar Cliente ---");
        System.out.println("Buscar por: 1. ID | 2. CPF");
        System.out.print("Opção: ");
        try {
            int tipoBusca = scanner.nextInt();
            scanner.nextLine(); 
            Optional<Cliente> clienteEncontrado = Optional.empty();

            if (tipoBusca == 1) {
                System.out.print("Digite o ID do cliente: ");
                int id = scanner.nextInt();
                scanner.nextLine(); 
                clienteEncontrado = controller.buscarClientePorId(id);
            } else if (tipoBusca == 2) {
                System.out.print("Digite o CPF do cliente: ");
                String cpf = scanner.nextLine();
                clienteEncontrado = controller.buscarClientePorCpf(cpf);
            } else {
                System.out.println("Tipo de busca inválido.");
                return;
            }

            if (clienteEncontrado.isPresent()) {
                System.out.println("Cliente encontrado:");
                System.out.println("-------------------------");
                System.out.println(clienteEncontrado.get().exibirDetalhes());
                System.out.println("-------------------------");
            } else {
                System.out.println("Nenhum cliente encontrado com os critérios fornecidos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número para ID ou tipo de busca.");
            scanner.nextLine(); 
        }
    }

    private static void menuGerenciarFuncionarios() {
        System.out.println("\n--- Gerenciar Funcionários ---");
        System.out.println("1. Cadastrar Novo Funcionário");
        System.out.println("2. Listar Todos os Funcionários");
        System.out.println("3. Atualizar Dados de Funcionário");
        System.out.println("4. Remover Funcionário");
        System.out.println("5. Buscar Funcionário");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");

        try {
            int opcao = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcao) {
                case 1:
                    cadastrarNovoFuncionario();
                    break;
                case 2:
                    listarTodosFuncionarios();
                    break;
                case 3:
                    atualizarFuncionarioExistente();
                    break;
                case 4:
                    removerFuncionarioExistente();
                    break;
                case 5:
                    buscarFuncionarioEspecifico();
                    break;
                case 0:
                    return; 
                default:
                    System.out.println("Opção inválida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            scanner.nextLine(); 
        }
    }

    private static void cadastrarNovoFuncionario() {
        System.out.println("\n--- Cadastrar Novo Funcionário ---");
         if (controller.getFuncionarioLogado() == null && !controller.listarFuncionarios().isEmpty()) {
            System.out.println("Acesso negado. Apenas o setup inicial ou um funcionário logado pode cadastrar novos funcionários.");
            return;
        }
        try {
            System.out.print("Nome do funcionário: ");
            String nome = scanner.nextLine();
            System.out.print("CPF (apenas números): ");
            String cpf = scanner.nextLine();
            System.out.print("Email: ");
            String email = scanner.nextLine();
            System.out.print("Cargo: ");
            String cargo = scanner.nextLine();
            System.out.print("Salário (ex: 2500.00): ");
            BigDecimal salario = scanner.nextBigDecimal();
            scanner.nextLine(); 
            System.out.print("Senha para login: ");
            String senha = scanner.nextLine();

            if (controller.cadastrarFuncionario(nome, cpf, email, cargo, salario, senha)) {
                System.out.println("Funcionário cadastrado com sucesso!");
            } else {
                System.out.println("Falha ao cadastrar funcionário. Verifique se o CPF já existe ou o log para detalhes.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Erro de entrada. Verifique os tipos de dados inseridos (ex: salário deve ser um número).");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void listarTodosFuncionarios() {
        System.out.println("\n--- Lista de Funcionários ---");
        List<Funcionario> funcionarios = controller.listarFuncionarios();
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
            return;
        }
        for (Funcionario funcionario : funcionarios) {
            System.out.println("-------------------------");
            System.out.println(funcionario.exibirDetalhes());
        }
        System.out.println("-------------------------");
    }

    private static void atualizarFuncionarioExistente() {
        System.out.println("\n--- Atualizar Dados de Funcionário ---");
        try {
            System.out.print("ID do funcionário a ser atualizado: ");
            int idFuncionario = scanner.nextInt();
            scanner.nextLine(); 

            Optional<Funcionario> funcOpt = controller.buscarFuncionarioPorId(idFuncionario);
            if (!funcOpt.isPresent()) {
                System.out.println("Funcionário não encontrado com o ID: " + idFuncionario);
                return;
            }
            Funcionario funcAtual = funcOpt.get();
            System.out.println("Dados atuais: \n" + funcAtual.exibirDetalhes());

            System.out.print("Novo nome (ou enter para manter '" + funcAtual.getNome() + "'): ");
            String nome = scanner.nextLine();
            if (nome.isEmpty()) nome = funcAtual.getNome();

            System.out.print("Novo CPF (ou enter para manter '" + funcAtual.getCpf() + "'): ");
            String cpf = scanner.nextLine();
            if (cpf.isEmpty()) cpf = funcAtual.getCpf();

            System.out.print("Novo email (ou enter para manter '" + funcAtual.getEmail() + "'): ");
            String email = scanner.nextLine();
            if (email.isEmpty()) email = funcAtual.getEmail();

            System.out.print("Novo cargo (ou enter para manter '" + funcAtual.getCargo() + "'): ");
            String cargo = scanner.nextLine();
            if (cargo.isEmpty()) cargo = funcAtual.getCargo();

            System.out.print("Novo salário (ou enter para manter '" + funcAtual.getSalario() + "'): ");
            String salarioStr = scanner.nextLine();
            BigDecimal salario = salarioStr.isEmpty() ? funcAtual.getSalario() : new BigDecimal(salarioStr);

            System.out.print("Nova senha (ou enter para não alterar): ");
            String senha = scanner.nextLine();

            if (controller.atualizarFuncionario(idFuncionario, nome, cpf, email, cargo, salario, senha)) {
                System.out.println("Dados do funcionário atualizados com sucesso!");
            } else {
                System.out.println("Falha ao atualizar dados do funcionário. Verifique se o novo CPF já existe.");
            }
        } catch (InputMismatchException | NumberFormatException e) {
            System.out.println("Erro de entrada. Verifique os tipos de dados inseridos.");
            scanner.nextLine(); 
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    private static void removerFuncionarioExistente() {
        System.out.println("\n--- Remover Funcionário ---");
        try {
            System.out.print("ID do funcionário a ser removido: ");
            int idFuncionario = scanner.nextInt();
            scanner.nextLine(); 

            if (controller.getFuncionarioLogado() != null && controller.getFuncionarioLogado().getIdFuncionario() == idFuncionario) {
                System.out.println("Você não pode remover o seu próprio usuário enquanto estiver logado.");
                return;
            }

            if (controller.removerFuncionario(idFuncionario)) {
                System.out.println("Funcionário removido com sucesso!");
            } else {
                System.out.println("Falha ao remover funcionário. Verifique se o ID existe, se é o último funcionário ou se é responsável por vendas.");
            }
        } catch (InputMismatchException e) {
            System.out.println("ID inválido. Por favor, insira um número.");
            scanner.nextLine(); 
        }
    }

    private static void buscarFuncionarioEspecifico() {
        System.out.println("\n--- Buscar Funcionário ---");
        System.out.println("Buscar por: 1. ID | 2. CPF"); 
        System.out.print("Opção: ");
        try {
            int tipoBusca = scanner.nextInt();
            scanner.nextLine(); 
            Optional<Funcionario> funcionarioEncontrado = Optional.empty();

            if (tipoBusca == 1) {
                System.out.print("Digite o ID do funcionário: ");
                int id = scanner.nextInt();
                scanner.nextLine(); 
                funcionarioEncontrado = controller.buscarFuncionarioPorId(id);
            } else if (tipoBusca == 2) {
                 System.out.print("Digite o CPF do funcionário: ");
                 String cpf = scanner.nextLine();
                 funcionarioEncontrado = controller.buscarFuncionarioPorCpf(cpf); // Assumindo que este método existe no controller
            }else {
                System.out.println("Tipo de busca inválido.");
                return;
            }

            if (funcionarioEncontrado.isPresent()) {
                System.out.println("Funcionário encontrado:");
                System.out.println("-------------------------");
                System.out.println(funcionarioEncontrado.get().exibirDetalhes());
                System.out.println("-------------------------");
            } else {
                System.out.println("Nenhum funcionário encontrado com os critérios fornecidos.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida.");
            scanner.nextLine(); 
        }
    }

    private static void realizarVenda() {
        System.out.println("\n--- Realizar Venda ---");

        if (controller.getFuncionarioLogado() == null) {
            System.out.println("Nenhum funcionário logado. Faça o login para realizar vendas.");
            return;
        }

        System.out.println("\nSelecione o Cliente:");
        listarTodosClientes(); 
        if(controller.listarClientes().isEmpty()){
            System.out.println("Não há clientes cadastrados para realizar uma venda.");
            return;
        }
        System.out.print("Digite o ID do Cliente para a venda: ");
        int idCliente;
        Optional<Cliente> clienteOpt;
        try {
            idCliente = scanner.nextInt();
            scanner.nextLine(); 
            clienteOpt = controller.buscarClientePorId(idCliente);
            if (!clienteOpt.isPresent()) {
                System.out.println("Cliente com ID " + idCliente + " não encontrado.");
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("ID do cliente inválido.");
            scanner.nextLine(); 
            return;
        }
        Cliente clienteSelecionado = clienteOpt.get();
        System.out.println("Cliente selecionado: " + clienteSelecionado.getNome());

        List<Integer> idsJogosSelecionados = new ArrayList<>();
        List<Jogo> jogosNoCarrinho = new ArrayList<>(); // Para exibir resumo
        BigDecimal valorTotalEstimado = BigDecimal.ZERO;

        boolean adicionandoJogos = true;
        if(controller.listarJogos().isEmpty()){
            System.out.println("Não há jogos cadastrados para vender.");
            return;
        }

        while (adicionandoJogos) {
            System.out.println("\nSelecione os Jogos (digite 0 para finalizar seleção):");
            listarTodosJogos(); 
            System.out.print("Digite o ID do Jogo para adicionar ao carrinho (ou 0 para finalizar): ");
            try {
                int idJogo = scanner.nextInt();
                scanner.nextLine(); 

                if (idJogo == 0) {
                    if (idsJogosSelecionados.isEmpty()) {
                        System.out.println("Nenhum jogo selecionado. A venda não pode prosseguir.");
                        return; // Sai da função realizarVenda
                    }
                    adicionandoJogos = false; // Finaliza a seleção de jogos
                } else {
                    Optional<Jogo> jogoOpt = controller.buscarJogoPorId(idJogo);
                    if (jogoOpt.isPresent()) {
                        Jogo jogoParaAdicionar = jogoOpt.get();

                        // Contar quantos deste jogo já estão no carrinho para verificar contra o estoque
                        long quantidadeJaNoCarrinho = jogosNoCarrinho.stream().filter(j -> j.getIdJogo() == jogoParaAdicionar.getIdJogo()).count();

                        if (jogoParaAdicionar.getQuantidadeEstoque() > quantidadeJaNoCarrinho) {
                            idsJogosSelecionados.add(idJogo);
                            jogosNoCarrinho.add(jogoParaAdicionar); 
                            valorTotalEstimado = valorTotalEstimado.add(jogoParaAdicionar.getPreco());
                            System.out.println("Jogo '" + jogoParaAdicionar.getNome() + "' adicionado ao carrinho.");
                            System.out.println("Estoque atual do jogo: " + jogoParaAdicionar.getQuantidadeEstoque() + ", No carrinho: " + (quantidadeJaNoCarrinho + 1));
                        } else {
                            System.out.println("Jogo '" + jogoParaAdicionar.getNome() + "' com estoque insuficiente para a quantidade solicitada.");
                        }
                    } else {
                        System.out.println("Jogo com ID " + idJogo + " não encontrado.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("ID do jogo inválido.");
                scanner.nextLine(); 
            }
        }

        System.out.println("\n--- Resumo da Venda ---");
        System.out.println("Cliente: " + clienteSelecionado.getNome());
        System.out.println("Funcionário: " + controller.getFuncionarioLogado().getNome());
        System.out.println("Jogos no Carrinho:");
        for (Jogo jogo : jogosNoCarrinho) {
            System.out.println("  - " + jogo.getNome() + " (R$ " + String.format("%.2f", jogo.getPreco()) + ")");
        }
        System.out.println("Valor Total Estimado: R$ " + String.format("%.2f", valorTotalEstimado));
        System.out.print("\nConfirmar venda? (S/N): ");
        String confirmacao = scanner.nextLine().trim().toUpperCase();

        if (confirmacao.equals("S")) {
            if (controller.realizarVenda(clienteSelecionado.getIdCliente(), idsJogosSelecionados)) {
                System.out.println("Venda realizada com sucesso!");
            } else {
                System.out.println("Falha ao processar a venda. Verifique o estoque dos jogos ou o log para mais detalhes.");
            }
        } else {
            System.out.println("Venda cancelada pelo usuário.");
        }
    }

    private static void listarVendas() {
        System.out.println("\n--- Lista de Vendas ---");
        List<Venda> vendas = controller.listarVendas();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        for (Venda venda : vendas) {
            System.out.println("-------------------------");
            System.out.println(venda.toString()); 
        }
        System.out.println("-------------------------");
    }
}