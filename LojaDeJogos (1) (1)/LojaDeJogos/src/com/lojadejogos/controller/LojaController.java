package com.lojadejogos.controller;

import com.lojadejogos.model.*; // Importa todas as classes do model
import com.lojadejogos.util.LogUtil;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LojaController {

    private List<Jogo> jogos;
    private List<Cliente> clientes;
    private List<Funcionario> funcionarios;
    private List<Venda> vendas;

    private Funcionario funcionarioLogado;

    private static final String ARQUIVO_JOGOS = "dados_jogos.dat";
    private static final String ARQUIVO_CLIENTES = "dados_clientes.dat";
    private static final String ARQUIVO_FUNCIONARIOS = "dados_funcionarios.dat";
    private static final String ARQUIVO_VENDAS = "dados_vendas.dat";

    public LojaController() {
        this.jogos = new ArrayList<>();
        this.clientes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.vendas = new ArrayList<>();
        carregarDados();
        LogUtil.registrar("Sistema iniciado. Dados carregados.");
    }

    public boolean autenticarFuncionario(int idFuncionario, String senha) {
        Optional<Funcionario> funcionarioOpt = buscarFuncionarioPorId(idFuncionario);
        if (funcionarioOpt.isPresent()) {
            Funcionario func = funcionarioOpt.get();
            if (func.autenticar(senha)) {
                this.funcionarioLogado = func;
                LogUtil.registrar("Funcionário logado: " + func.getNome() + " (ID: " + func.getIdFuncionario() + ")");
                return true;
            } else {
                LogUtil.registrar("Tentativa de login falhou para funcionário ID: " + idFuncionario + " (senha incorreta)");
            }
        } else {
            LogUtil.registrar("Tentativa de login falhou. Funcionário ID: " + idFuncionario + " não encontrado.");
        }
        return false;
    }

    public void logoutFuncionario() {
        if (this.funcionarioLogado != null) {
            LogUtil.registrar("Funcionário deslogado: " + this.funcionarioLogado.getNome());
            this.funcionarioLogado = null;
        }
    }

    public Funcionario getFuncionarioLogado() {
        return funcionarioLogado;
    }

    public boolean adicionarJogo(String nome, String genero, String plataforma, BigDecimal preco, int quantidadeEstoque) {
        if (funcionarioLogado == null) {
            System.out.println("Acesso negado. Nenhum funcionário logado.");
            LogUtil.registrar("Tentativa de adicionar jogo sem funcionário logado.");
            return false;
        }
        if (jogos.stream().anyMatch(j -> j.getNome().equalsIgnoreCase(nome) && j.getPlataforma().equalsIgnoreCase(plataforma))) {
            System.out.println("Erro: Jogo com este nome e plataforma já existe.");
            LogUtil.registrar("Falha ao adicionar jogo: " + nome + " - " + plataforma + " (já existe).");
            return false;
        }
        Jogo novoJogo = new Jogo(nome, genero, plataforma, preco, quantidadeEstoque);
        this.jogos.add(novoJogo);
        LogUtil.registrar("Jogo adicionado: " + novoJogo.getNome() + " (ID: " + novoJogo.getIdJogo() + ") por " + funcionarioLogado.getNome());
        salvarDadosJogos();
        return true;
    }

    public Optional<Jogo> buscarJogoPorId(int idJogo) {
        return this.jogos.stream().filter(j -> j.getIdJogo() == idJogo).findFirst();
    }

    public List<Jogo> buscarJogo(String termoBusca, String tipoBusca) {
        termoBusca = termoBusca.toLowerCase();
        switch (tipoBusca.toLowerCase()) {
            case "nome":
                return this.jogos.stream()
                        .filter(j -> j.getNome().toLowerCase().contains(termoBusca))
                        .collect(Collectors.toList());
            case "genero":
                return this.jogos.stream()
                        .filter(j -> j.getGenero().toLowerCase().contains(termoBusca))
                        .collect(Collectors.toList());
            case "plataforma":
                return this.jogos.stream()
                        .filter(j -> j.getPlataforma().toLowerCase().contains(termoBusca))
                        .collect(Collectors.toList());
            default:
                return new ArrayList<>();
        }
    }

    public List<Jogo> listarJogos() {
        return new ArrayList<>(this.jogos);
    }

    public boolean atualizarJogo(int idJogo, String novoNome, String novoGenero, String novaPlataforma, BigDecimal novoPreco, int novaQuantidadeEstoque) {
        if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de atualizar jogo sem funcionário logado."); return false;
        }
        Optional<Jogo> jogoOpt = buscarJogoPorId(idJogo);
        if (jogoOpt.isPresent()) {
            Jogo jogo = jogoOpt.get();
            if (!jogo.getNome().equalsIgnoreCase(novoNome) || !jogo.getPlataforma().equalsIgnoreCase(novaPlataforma)) {
                if (jogos.stream().anyMatch(j -> j.getIdJogo() != idJogo && j.getNome().equalsIgnoreCase(novoNome) && j.getPlataforma().equalsIgnoreCase(novaPlataforma))) {
                    System.out.println("Erro: Já existe outro jogo com o nome " + novoNome + " para a plataforma " + novaPlataforma + ".");
                    LogUtil.registrar("Falha ao atualizar jogo ID " + idJogo + ": nome/plataforma duplicado.");
                    return false;
                }
            }
            jogo.setNome(novoNome);
            jogo.setGenero(novoGenero);
            jogo.setPlataforma(novaPlataforma);
            jogo.setPreco(novoPreco);
            jogo.setQuantidadeEstoque(novaQuantidadeEstoque);
            LogUtil.registrar("Jogo ID " + idJogo + " atualizado por " + funcionarioLogado.getNome());
            salvarDadosJogos();
            return true;
        }
        LogUtil.registrar("Falha ao atualizar: Jogo ID " + idJogo + " não encontrado.");
        return false;
    }

    public boolean removerJogo(int idJogo) {
        if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de remover jogo sem funcionário logado."); return false;
        }
        Optional<Jogo> jogoOpt = buscarJogoPorId(idJogo);
        if (jogoOpt.isPresent()) {
            boolean jogoEmVenda = this.vendas.stream().anyMatch(v -> v.getJogosVendidos().stream().anyMatch(j -> j.getIdJogo() == idJogo));
            if (jogoEmVenda) {
                System.out.println("Não é possível remover o jogo ID " + idJogo + " pois ele existe em registros de vendas.");
                LogUtil.registrar("Tentativa falha de remover Jogo ID " + idJogo + " (em vendas) por " + funcionarioLogado.getNome());
                return false;
            }
            this.jogos.remove(jogoOpt.get());
            LogUtil.registrar("Jogo ID " + idJogo + " removido por " + funcionarioLogado.getNome());
            salvarDadosJogos();
            return true;
        }
        LogUtil.registrar("Falha ao remover: Jogo ID " + idJogo + " não encontrado.");
        return false;
    }

    public boolean cadastrarCliente(String nome, String cpf, String email) {
         if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de cadastrar cliente sem funcionário logado."); return false;
        }
        if (clientes.stream().anyMatch(c -> c.getCpf().equals(cpf))) {
            System.out.println("Erro: Cliente com este CPF já cadastrado.");
            LogUtil.registrar("Falha ao cadastrar cliente: CPF " + cpf + " (já existe).");
            return false;
        }
        Cliente novoCliente = new Cliente(nome, cpf, email);
        this.clientes.add(novoCliente);
        LogUtil.registrar("Cliente cadastrado: " + novoCliente.getNome() + " (ID: " + novoCliente.getIdCliente() + ") por " + funcionarioLogado.getNome());
        salvarDadosClientes();
        return true;
    }

    public Optional<Cliente> buscarClientePorId(int idCliente) {
        return this.clientes.stream().filter(c -> c.getIdCliente() == idCliente).findFirst();
    }

    public Optional<Cliente> buscarClientePorCpf(String cpf) {
        return this.clientes.stream().filter(c -> c.getCpf().equals(cpf)).findFirst();
    }

    public List<Cliente> listarClientes() {
        return new ArrayList<>(this.clientes);
    }

    public boolean atualizarCliente(int idCliente, String novoNome, String novoCpf, String novoEmail) {
        if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de atualizar cliente sem funcionário logado."); return false;
        }
        Optional<Cliente> clienteOpt = buscarClientePorId(idCliente);
        if (clienteOpt.isPresent()) {
            Cliente cliente = clienteOpt.get();
             if (!cliente.getCpf().equals(novoCpf)) {
                if (clientes.stream().anyMatch(c -> c.getIdCliente() != idCliente && c.getCpf().equals(novoCpf))) {
                    System.out.println("Erro: Já existe outro cliente com o CPF " + novoCpf + ".");
                    LogUtil.registrar("Falha ao atualizar cliente ID " + idCliente + ": CPF duplicado.");
                    return false;
                }
            }
            cliente.setNome(novoNome);
            cliente.setCpf(novoCpf);
            cliente.setEmail(novoEmail);
            LogUtil.registrar("Cliente ID " + idCliente + " atualizado por " + funcionarioLogado.getNome());
            salvarDadosClientes();
            return true;
        }
        LogUtil.registrar("Falha ao atualizar: Cliente ID " + idCliente + " não encontrado.");
        return false;
    }

    public boolean removerCliente(int idCliente) {
        if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de remover cliente sem funcionário logado."); return false;
        }
        Optional<Cliente> clienteOpt = buscarClientePorId(idCliente);
        if (clienteOpt.isPresent()) {
            boolean clienteEmVenda = this.vendas.stream().anyMatch(v -> v.getCliente().getIdCliente() == idCliente);
            if (clienteEmVenda) {
                System.out.println("Não é possível remover o cliente ID " + idCliente + " pois ele possui registros de vendas.");
                LogUtil.registrar("Tentativa falha de remover Cliente ID " + idCliente + " (possui vendas) por " + funcionarioLogado.getNome());
                return false;
            }
            this.clientes.remove(clienteOpt.get());
            LogUtil.registrar("Cliente ID " + idCliente + " removido por " + funcionarioLogado.getNome());
            salvarDadosClientes();
            return true;
        }
        LogUtil.registrar("Falha ao remover: Cliente ID " + idCliente + " não encontrado.");
        return false;
    }

    public boolean cadastrarFuncionario(String nome, String cpf, String email, String cargo, BigDecimal salario, String senha) {
        // A lógica de permitir cadastro de funcionário APENAS pelo primeiro admin ou algo assim
        // pode ser mais complexa. Aqui, qualquer funcionário logado pode cadastrar outro.
        // Em um cenário real, poderia haver uma verificação de cargo do funcionarioLogado.
        if (funcionarioLogado == null && !funcionarios.isEmpty()) { 
             LogUtil.registrar("Tentativa de cadastrar funcionário sem permissão/login (quando já existem funcionários)."); return false;
        }
         if (funcionarios.stream().anyMatch(f -> f.getCpf().equals(cpf))) {
            System.out.println("Erro: Funcionário com este CPF já cadastrado.");
            LogUtil.registrar("Falha ao cadastrar funcionário: CPF " + cpf + " (já existe).");
            return false;
        }
        Funcionario novoFuncionario = new Funcionario(nome, cpf, email, cargo, salario, senha);
        this.funcionarios.add(novoFuncionario);
        String logMessage = "Funcionário cadastrado: " + novoFuncionario.getNome() + " (ID: " + novoFuncionario.getIdFuncionario() + ")";
        if (funcionarioLogado != null) {
            logMessage += " por " + funcionarioLogado.getNome();
        } else {
            logMessage += " (primeiro cadastro)";
        }
        LogUtil.registrar(logMessage);
        salvarDadosFuncionarios();
        return true;
    }

    public Optional<Funcionario> buscarFuncionarioPorId(int idFuncionario) {
        return this.funcionarios.stream().filter(f -> f.getIdFuncionario() == idFuncionario).findFirst();
    }

    public Optional<Funcionario> buscarFuncionarioPorCpf(String cpf) { // Adicionado para consistência
        return this.funcionarios.stream().filter(f -> f.getCpf().equals(cpf)).findFirst();
    }

    public List<Funcionario> listarFuncionarios() {
        return new ArrayList<>(this.funcionarios);
    }

    public boolean atualizarFuncionario(int idFuncionario, String novoNome, String novoCpf, String novoEmail, String novoCargo, BigDecimal novoSalario, String novaSenha) {
        if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de atualizar funcionário sem permissão/login."); return false;
        }
        Optional<Funcionario> funcOpt = buscarFuncionarioPorId(idFuncionario);
        if (funcOpt.isPresent()) {
            Funcionario func = funcOpt.get();
            if (!func.getCpf().equals(novoCpf)) {
                if (funcionarios.stream().anyMatch(f -> f.getIdFuncionario() != idFuncionario && f.getCpf().equals(novoCpf))) {
                     System.out.println("Erro: Já existe outro funcionário com o CPF " + novoCpf + ".");
                    LogUtil.registrar("Falha ao atualizar funcionário ID " + idFuncionario + ": CPF duplicado.");
                    return false;
                }
            }
            func.setNome(novoNome);
            func.setCpf(novoCpf);
            func.setEmail(novoEmail);
            func.setCargo(novoCargo);
            func.setSalario(novoSalario);
            if (novaSenha != null && !novaSenha.isEmpty()) {
                func.setSenha(novaSenha);
            }
            LogUtil.registrar("Funcionário ID " + idFuncionario + " atualizado por " + funcionarioLogado.getNome());
            salvarDadosFuncionarios();
            return true;
        }
        LogUtil.registrar("Falha ao atualizar: Funcionário ID " + idFuncionario + " não encontrado.");
        return false;
    }

    public boolean removerFuncionario(int idFuncionario) {
        if (funcionarioLogado == null) {
            LogUtil.registrar("Tentativa de remover funcionário sem permissão/login."); return false;
        }
        if (funcionarioLogado.getIdFuncionario() == idFuncionario) {
            System.out.println("Não é possível remover o funcionário atualmente logado.");
            LogUtil.registrar("Tentativa falha de remover Funcionário ID " + idFuncionario + " (próprio usuário) por " + funcionarioLogado.getNome());
            return false;
        }
        Optional<Funcionario> funcOpt = buscarFuncionarioPorId(idFuncionario);
        if (funcOpt.isPresent()) {
            boolean funcEmVenda = this.vendas.stream().anyMatch(v -> v.getFuncionarioResponsavel() != null && v.getFuncionarioResponsavel().getIdFuncionario() == idFuncionario);
            if (funcEmVenda) {
                System.out.println("Não é possível remover o funcionário ID " + idFuncionario + " pois ele é responsável por registros de vendas.");
                LogUtil.registrar("Tentativa falha de remover Funcionário ID " + idFuncionario + " (em vendas) por " + funcionarioLogado.getNome());
                return false;
            }
            // Adicional: não permitir remover o último funcionário se for uma política
            if (this.funcionarios.size() == 1) {
                System.out.println("Não é possível remover o último funcionário do sistema.");
                LogUtil.registrar("Tentativa falha de remover o último funcionário ID " + idFuncionario + " por " + funcionarioLogado.getNome());
                return false;
            }
            this.funcionarios.remove(funcOpt.get());
            LogUtil.registrar("Funcionário ID " + idFuncionario + " removido por " + funcionarioLogado.getNome());
            salvarDadosFuncionarios();
            return true;
        }
        LogUtil.registrar("Falha ao remover: Funcionário ID " + idFuncionario + " não encontrado.");
        return false;
    }

    public boolean realizarVenda(int idCliente, List<Integer> idsJogos) {
        if (funcionarioLogado == null) {
            System.out.println("Operação de venda requer funcionário logado.");
            LogUtil.registrar("Tentativa de realizar venda sem funcionário logado.");
            return false;
        }

        Optional<Cliente> clienteOpt = buscarClientePorId(idCliente);
        if (!clienteOpt.isPresent()) {
            System.out.println("Cliente não encontrado.");
            LogUtil.registrar("Falha na venda: Cliente ID " + idCliente + " não encontrado.");
            return false;
        }
        Cliente cliente = clienteOpt.get();
        List<Jogo> jogosParaVenda = new ArrayList<>();

        for (int idJogo : idsJogos) {
            Optional<Jogo> jogoOpt = buscarJogoPorId(idJogo);
            if (!jogoOpt.isPresent()) {
                System.out.println("Jogo com ID " + idJogo + " não encontrado.");
                LogUtil.registrar("Falha na venda: Jogo ID " + idJogo + " não encontrado.");
                return false; 
            }
            Jogo jogo = jogoOpt.get();
            // Contabiliza quantos deste ID já estão na lista para venda atual
            long quantidadeNoCarrinho = jogosParaVenda.stream().filter(j -> j.getIdJogo() == idJogo).count();
            if (jogo.getQuantidadeEstoque() <= quantidadeNoCarrinho) { 
                System.out.println("Jogo " + jogo.getNome() + " com estoque insuficiente para a quantidade solicitada.");
                LogUtil.registrar("Falha na venda: Jogo " + jogo.getNome() + " (ID: " + idJogo + ") estoque insuficiente.");
                return false; 
            }
            jogosParaVenda.add(jogo);
        }

        // Se todos os jogos estão OK, processa a venda e debita estoque
        for (Jogo jogoNaVenda : jogosParaVenda) {
             Optional<Jogo> jogoOriginalOpt = buscarJogoPorId(jogoNaVenda.getIdJogo());
             if(jogoOriginalOpt.isPresent()){
                 jogoOriginalOpt.get().removerEstoque(1); 
             }
        }

        Venda novaVenda = new Venda(cliente, jogosParaVenda, funcionarioLogado);
        this.vendas.add(novaVenda);

        LogUtil.registrar("Venda realizada: ID " + novaVenda.getIdVenda() + " para Cliente " + cliente.getNome() +
                          " por Funcionário " + funcionarioLogado.getNome() +
                          ". Valor: R$ " + String.format("%.2f", novaVenda.getValorTotal()));
        salvarDadosJogos(); 
        salvarDadosVendas();
        return true;
    }

    public List<Venda> listarVendas() {
        return new ArrayList<>(this.vendas);
    }

    private void salvarDadosJogos() { salvarLista(ARQUIVO_JOGOS, this.jogos, "jogos"); }
    private void salvarDadosClientes() { salvarLista(ARQUIVO_CLIENTES, this.clientes, "clientes"); }
    private void salvarDadosFuncionarios() { salvarLista(ARQUIVO_FUNCIONARIOS, this.funcionarios, "funcionários"); }
    private void salvarDadosVendas() { salvarLista(ARQUIVO_VENDAS, this.vendas, "vendas"); }

    public void salvarTodosOsDados() {
        salvarDadosJogos();
        salvarDadosClientes();
        salvarDadosFuncionarios();
        salvarDadosVendas();
        LogUtil.registrar("Todos os dados foram salvos.");
    }

    private void carregarDados() {
        this.jogos = carregarLista(ARQUIVO_JOGOS, "jogos");
        if (!this.jogos.isEmpty()) {
            Jogo.setProximoId(this.jogos.stream().mapToInt(Jogo::getIdJogo).max().orElse(0) + 1);
        } else {
            Jogo.setProximoId(1);
        }

        this.clientes = carregarLista(ARQUIVO_CLIENTES, "clientes");
        if (!this.clientes.isEmpty()) {
            Cliente.setProximoId(this.clientes.stream().mapToInt(Cliente::getIdCliente).max().orElse(0) + 1);
        } else {
            Cliente.setProximoId(1);
        }

        this.funcionarios = carregarLista(ARQUIVO_FUNCIONARIOS, "funcionários");
        if(!this.funcionarios.isEmpty()) {
            Funcionario.setProximoId(this.funcionarios.stream().mapToInt(Funcionario::getIdFuncionario).max().orElse(0) + 1);
        } else {
            Funcionario.setProximoId(1);
        }

        this.vendas = carregarLista(ARQUIVO_VENDAS, "vendas");
        if(!this.vendas.isEmpty()) {
            Venda.setProximoId(this.vendas.stream().mapToInt(Venda::getIdVenda).max().orElse(0) + 1);
        } else {
            Venda.setProximoId(1);
        }
    }

    private <T> void salvarLista(String nomeArquivo, List<T> lista, String tipoDados) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            LogUtil.registrar("Erro ao salvar dados de " + tipoDados + " em " + nomeArquivo + ": " + e.getMessage());
            System.err.println("Erro ao salvar " + nomeArquivo + ": " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> carregarLista(String nomeArquivo, String tipoDados) {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists() && arquivo.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    // Verifica se a lista não é nula antes de retornar
                    List<T> loadedList = (List<T>) obj;
                    return loadedList != null ? loadedList : new ArrayList<>();
                } else {
                     LogUtil.registrar("Erro ao carregar dados de " + tipoDados + ": formato de arquivo inválido (" + nomeArquivo + ").");
                     System.err.println("Erro: Formato de arquivo " + nomeArquivo + " inválido.");
                }
            } catch (EOFException e) {
                LogUtil.registrar("Arquivo " + nomeArquivo + " para " + tipoDados + " está vazio ou corrompido (EOF). Iniciando com lista vazia.");
            } catch (IOException | ClassNotFoundException e) {
                LogUtil.registrar("Erro ao carregar dados de " + tipoDados + " de " + nomeArquivo + ": " + e.getMessage());
                System.err.println("Erro ao carregar " + nomeArquivo + ": " + e.getMessage() + ". Um novo arquivo será criado se necessário.");
            }
        } else if (!arquivo.exists()) {
            LogUtil.registrar("Arquivo " + nomeArquivo + " não encontrado. Iniciando com lista vazia para " + tipoDados + ".");
        } else { 
            LogUtil.registrar("Arquivo " + nomeArquivo + " para " + tipoDados + " está vazio. Iniciando com lista vazia.");
        }
        return new ArrayList<>();
    }
}