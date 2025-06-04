package main.model.util;

import java.io.*;
import java.util.List;

public class Serializador {

    public static <T> void serializar(String nomeArquivo, List<T> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
            Log.registrar("Lista serializada para " + nomeArquivo);
        } catch (IOException e) {
            Log.registrarErro("Erro ao serializar para " + nomeArquivo, e);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> desserializar(String nomeArquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            List<T> lista = (List<T>) ois.readObject();
            Log.registrar("Lista desserializada de " + nomeArquivo);
            return lista;
        } catch (IOException | ClassNotFoundException e) {
            Log.registrarErro("Erro ao desserializar de " + nomeArquivo, e);
            return null; // Retorna null em caso de erro
        }
    }
}