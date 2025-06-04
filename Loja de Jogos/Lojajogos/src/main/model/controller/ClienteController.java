package main.model.controller;

import main.model.Cliente;
import main.model.util.Log;
import main.model.util.Serializador;

import java.util.ArrayList;
import java.util.List;

public class ClienteController {

    private static final String ARQUIVO_CLIENTES = "clientes.dat";
    private List<Cliente> clientes = carregarClientes();
    private int proximoId = !clientes.isEmpty() ? clientes.get(clientes.size() - 1).getId() + 1 : 1;

    private List<Cliente> carregarClientes() {
        List<Cliente> lista = Serializador.desserializar(ARQUIVO_CLIENTES);
        return lista != null ? lista : new ArrayList<>();
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    private void salvarClientes() {
        Serializador.serializar(ARQUIVO_CLIENTES, clientes);
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public void cadastrarCliente(String nome, String email, String endereco, String telefone) {
        try {
            Cliente cliente = new Cliente(proximoId++, nome, email, endereco, telefone);
            clientes.add(cliente);
            salvarClientes();
            Log.registrar("Cliente " + nome + " cadastrado com sucesso!");
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao cadastrar cliente " + nome, e);
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public Cliente buscarClientePorId(int id) {
        try {
            for (Cliente cliente : clientes) {
                if (cliente.getId() == id) {
                    return cliente;
                
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            Log.registrar("Cliente com ID " + id + " não encontrado.");
            return null;
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar cliente por ID " + id, e);
            return null;
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public List<Cliente> buscarClientePorNome(String nome) {
        try {
            List<Cliente> resultados = new ArrayList<>();
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equalsIgnoreCase(nome)) {
                    resultados.add(cliente);
                
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            Log.registrar("Busca por cliente com nome " + nome + ". Resultados: " + resultados.size());
            return resultados;
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar clientes por nome " + nome, e);
            return null;
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public Cliente buscarClientePorEmail(String email) {
        try {
            for (Cliente cliente : clientes) {
                if (cliente.getEmail().equalsIgnoreCase(email)) {
                    return cliente;
                
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            Log.registrar("Cliente com email " + email + " não encontrado.");
            return null;
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar cliente por email " + email, e);
            return null;
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public void listarClientes() {
        try {
            if (clientes.isEmpty()) {
                Log.registrar("Não há clientes cadastrados.");
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} else {
                Log.registrar("Listando todos os clientes. Total: " + clientes.size());
                for (Cliente cliente : clientes) {
                    cliente.exibirDetalhes();
                
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao listar clientes", e);
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public void atualizarCliente(int id, String nome, String email, String endereco, String telefone) {
        try {
            Cliente cliente = buscarClientePorId(id);
            if (cliente != null) {
                cliente.setNome(nome);
                cliente.setEmail(email);
                cliente.setEndereco(endereco);
                cliente.setTelefone(telefone);
                salvarClientes();
                Log.registrar("Cliente " + nome + " atualizado com sucesso!");
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} else {
                Log.registrar("Cliente com ID " + id + " não encontrado.");
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao atualizar cliente " + nome, e);
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public void deletarCliente(int id) {
        try {
            Cliente cliente = buscarClientePorId(id);
            if (cliente != null) {
                clientes.remove(cliente);
                salvarClientes();
                Log.registrar("Cliente " + cliente.getNome() + " deletado com sucesso!");
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} else {
                Log.registrar("Cliente com ID " + id + " não encontrado.");
            
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao deletar cliente com ID " + id, e);
        
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}
    
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}

    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }
    
}