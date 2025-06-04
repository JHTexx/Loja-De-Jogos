package main.model.controller;

import main.model.Venda;
import main.model.Cliente;
import main.model.Jogo;
import main.model.util.Log;
import main.model.util.Serializador;

import java.util.ArrayList;
import java.util.List;

public class VendaController {

    private static final String ARQUIVO_VENDAS = "vendas.dat";
    private List<Venda> vendas = carregarVendas();
    private int proximoId = !vendas.isEmpty() ? vendas.get(vendas.size() - 1).getId() + 1 : 1;

    private List<Venda> carregarVendas() {
        List<Venda> lista = Serializador.desserializar(ARQUIVO_VENDAS);
        return lista != null ? lista : new ArrayList<>();
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    private void salvarVendas() {
        Serializador.serializar(ARQUIVO_VENDAS, vendas);
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public void cadastrarVenda(Cliente cliente, String formaPagamento) {
        try {
            Venda venda = new Venda(proximoId++, cliente, formaPagamento);
            vendas.add(venda);
            salvarVendas();
            Log.registrar("Venda registrada para o cliente " + cliente.getNome() + "!");
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao cadastrar venda para o cliente " + cliente.getNome(), e);
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public Venda buscarVendaPorId(int id) {
        try {
            for (Venda venda : vendas) {
                if (venda.getId() == id) {
                    return venda;
                
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
            Log.registrar("Venda com ID " + id + " não encontrada.");
            return null;
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar venda por ID " + id, e);
            return null;
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public List<Venda> listarVendas() {
        try {
            if (vendas.isEmpty()) {
                Log.registrar("Não há vendas registradas.");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} else {
                Log.registrar("Listando todas as vendas. Total: " + vendas.size());
                for (Venda venda : vendas) {
                    venda.exibirDetalhes();
                
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
            return vendas;
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao listar vendas", e);
            return null;
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public void atualizarVenda(int id, Cliente cliente, String formaPagamento) {
        try {
            Venda venda = buscarVendaPorId(id);
            if (venda != null) {
                venda.setCliente(cliente);
                venda.setFormaPagamento(formaPagamento);
                salvarVendas();
                Log.registrar("Venda " + id + " atualizada!");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} else {
                Log.registrar("Venda com ID " + id + " não encontrada.");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao atualizar venda " + id, e);
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public void deletarVenda(int id) {
        try {
            Venda venda = buscarVendaPorId(id);
            if (venda != null) {
                vendas.remove(venda);
                salvarVendas();
                Log.registrar("Venda " + id + " cancelada.");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} else {
                Log.registrar("Venda com ID " + id + " não encontrada.");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao deletar venda " + id, e);
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public void adicionarJogoNaVenda(int idVenda, Jogo jogo) {
        try {
            Venda venda = buscarVendaPorId(idVenda);
            if (venda != null) {
                venda.adicionarJogo(jogo);
                salvarVendas();
                Log.registrar("Jogo " + jogo.getNome() + " adicionado à venda " + idVenda + ".");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} else {
                Log.registrar("Venda com ID " + idVenda + " não encontrada.");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao adicionar jogo à venda " + idVenda, e);
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public void removerJogoDaVenda(int idVenda, Jogo jogo) {
        try {
            Venda venda = buscarVendaPorId(idVenda);
            if (venda != null) {
                venda.removerJogo(jogo);
                salvarVendas();
                Log.registrar("Jogo " + jogo.getNome() + " removido da venda " + idVenda + ".");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} else {
                Log.registrar("Venda com ID " + idVenda + " não encontrada.");
            
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao remover jogo da venda " + idVenda, e);
        
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}
    
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}

    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }
    
}