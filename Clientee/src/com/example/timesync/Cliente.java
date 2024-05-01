package com.example.timesync;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cliente {
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private long deslocamentoTempo;

    public Cliente(String endereco, int porta, long deslocamentoTempo) throws IOException {
        this.socket = new Socket(endereco, porta);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.in = new DataInputStream(socket.getInputStream());
        this.deslocamentoTempo = deslocamentoTempo;
    }

    public void enviarTempo() throws IOException {
        long tempoAtual = System.currentTimeMillis() + deslocamentoTempo;
        System.out.println("Enviando tempo ajustado: " + new Date(tempoAtual));
        out.writeLong(tempoAtual);
    }

    public void ajustarTempo() throws IOException {
        long ajusteDeTempo = in.readLong();
        long tempoAjustado = System.currentTimeMillis() + deslocamentoTempo + ajusteDeTempo;
        System.out.println("Tempo ajustado recebido: " + new Date(tempoAjustado));
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite o horario inicial (dd/MM/yyyy HH:mm:ss): ");
        String dataEntrada = scanner.nextLine();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        try {
            Date dataUsuario = sdf.parse(dataEntrada);
            long deslocamento = dataUsuario.getTime() - System.currentTimeMillis();

            String endereco = "localhost";
            int porta = 9876;
            Cliente cliente = new Cliente(endereco, porta, deslocamento);
            cliente.enviarTempo();
            cliente.ajustarTempo();
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use o formato dd/MM/yyyy HH:mm:ss");
        }
    }
}
