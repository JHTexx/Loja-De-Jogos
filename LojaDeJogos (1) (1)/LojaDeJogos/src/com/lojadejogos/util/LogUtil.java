package com.lojadejogos.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogUtil {
    private static final String LOG_FILE = "log_atividades.txt"; // Será criado na raiz do projeto
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void registrar(String mensagem) {
        try (FileWriter fw = new FileWriter(LOG_FILE, true); // true para append
             PrintWriter pw = new PrintWriter(fw)) {

            String timestamp = LocalDateTime.now().format(formatter);
            pw.println("[" + timestamp + "] " + mensagem);

        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de log: " + e.getMessage());
        }
    }
}