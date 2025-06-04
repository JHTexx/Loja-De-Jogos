package main.model.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {

    private static final String LOG_FILE = "loja_jogos.log";

    public static void registrar(String mensagem) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            LocalDateTime agora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = agora.format(formatter);

            writer.println(timestamp + " - " + mensagem);
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }

    public static void registrarErro(String mensagem, Exception e) {
        registrar("ERRO: " + mensagem);
        if (e != null) {
            registrar("Detalhes do Erro: " + e.getMessage());
            e.printStackTrace(System.err); // Imprime o stack trace no console de erro
        }
    }
}