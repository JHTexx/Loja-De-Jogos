package main.model.controller;

import main.model.Jogo;
import main.model.util.Log;
import main.model.util.Serializador;

import java.util.ArrayList;
import java.util.List;

public class JogoController {

    private static final String ARQUIVO_JOGOS = "jogos.dat";
    private List<Jogo> jogos = carregarJogos();
    private int proximoId = !jogos.isEmpty() ? jogos.get(jogos.size() - 1).getId() + 1 : 1;

    private List<Jogo> carregarJogos() {
        List<Jogo> lista = Serializador.desserializar(ARQUIVO_JOGOS);
        return lista != null ? lista : new ArrayList<>();
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    private void salvarJogos() {
        Serializador.serializar(ARQUIVO_JOGOS, jogos);
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public void cadastrarJogo(String nome, String genero, String plataforma, float preco, String descricao, java.time.LocalDate dataLancamento, int estoque) {
        Jogo jogo = new Jogo(proximoId++, nome, genero, plataforma, preco, descricao, dataLancamento, estoque);
        jogos.add(jogo);
        salvarJogos();
        Log.registrar("Jogo " + nome + " cadastrado com sucesso!");
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public Jogo buscarJogoPorId(int id) {
        try {
            for (Jogo jogo : jogos) {
                if (jogo.getId() == id) {
                    return jogo;
                
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            Log.registrar("Jogo com ID " + id + " não encontrado.");
            return null;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar jogo por ID " + id, e);
            return null;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public List<Jogo> buscarJogoPorNome(String nome) {
        try {
            List<Jogo> resultados = new ArrayList<>();
            for (Jogo jogo : jogos) {
                if (jogo.getNome().equalsIgnoreCase(nome)) {
                    resultados.add(jogo);
                
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            Log.registrar("Busca por jogo com nome " + nome + ". Resultados: " + resultados.size());
            return resultados;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar jogos por nome " + nome, e);
            return null;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public List<Jogo> buscarJogoPorGenero(String genero) {
        try {
            List<Jogo> resultados = new ArrayList<>();
            for (Jogo jogo : jogos) {
                if (jogo.getGenero().equalsIgnoreCase(genero)) {
                    resultados.add(jogo);
                
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            Log.registrar("Busca por jogo com gênero " + genero + ". Resultados: " + resultados.size());
            return resultados;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar jogos por gênero " + genero, e);
            return null;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public List<Jogo> buscarJogoPorPlataforma(String plataforma) {
        try {
            List<Jogo> resultados = new ArrayList<>();
            for (Jogo jogo : jogos) {
                if (jogo.getPlataforma().equalsIgnoreCase(plataforma)) {
                    resultados.add(jogo);
                
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            Log.registrar("Busca por jogo com plataforma " + plataforma + ". Resultados: " + resultados.size());
            return resultados;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao buscar jogos por plataforma " + plataforma, e);
            return null;
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public void listarJogos() {
        try {
            if (jogos.isEmpty()) {
                Log.registrar("Não há jogos cadastrados.");
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} else {
                Log.registrar("Listando todos os jogos. Total: " + jogos.size());
                for (Jogo jogo : jogos) {
                    jogo.exibirDetalhes();
                
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao listar jogos", e);
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public void atualizarJogo(int id, String nome, String genero, String plataforma, float preco, String descricao, java.time.LocalDate dataLancamento, int estoque) {
        try {
            Jogo jogo = buscarJogoPorId(id);
            if (jogo != null) {
                jogo.setNome(nome);
                jogo.setGenero(genero);
                jogo.setPlataforma(plataforma);
                jogo.setPreco(preco);
                jogo.setDescricao(descricao);
                jogo.setDataLancamento(dataLancamento);
                jogo.setEstoque(estoque);
                salvarJogos();
                Log.registrar("Jogo " + nome + " atualizado com sucesso!");
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} else {
                Log.registrar("Jogo com ID " + id + " não encontrado.");
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao atualizar jogo " + nome, e);
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public void deletarJogo(int id) {
        try {
            Jogo jogo = buscarJogoPorId(id);
            if (jogo != null) {
                jogos.remove(jogo);
                salvarJogos();
                Log.registrar("Jogo " + jogo.getNome() + " deletado com sucesso!");
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} else {
                Log.registrar("Jogo com ID " + id + " não encontrado.");
            
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
} catch (Exception e) {
            Log.registrarErro("Erro ao deletar jogo com ID " + id, e);
        
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}
    
    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}

    public List<Jogo> listarTodos() {
        return jogos;
    }
    
}