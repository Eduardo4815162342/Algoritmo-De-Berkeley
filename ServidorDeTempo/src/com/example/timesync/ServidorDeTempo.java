package com.example.timesync;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorDeTempo {
    private ServerSocket servidorSocket;
    public Map<Integer, Socket> socketsClientes = new HashMap<>();
    private Map<Integer, Long> diferencaDeTempos = new HashMap<>();

    public ServidorDeTempo(int porta) throws IOException {
        servidorSocket = new ServerSocket(porta);
    }

    public void iniciarServidor() {
        System.out.println("Servidor de Tempo iniciado e esperando conexoes...");
        try {
            while (socketsClientes.size() < 4) {
                Socket socketCliente = servidorSocket.accept();
                new GerenciadorDeCliente(this, socketCliente).start();
            }
            calcularAjustesDeTempo();
            ajustarTemposDosClientes();
            fecharTodasConexoes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void adicionarTempoCliente(int idCliente, long diferencaTempo) {
        diferencaDeTempos.put(idCliente, diferencaTempo);
    }

    private void calcularAjustesDeTempo() {
        long soma = diferencaDeTempos.values().stream().mapToLong(Long::longValue).sum();
        long media = soma / diferencaDeTempos.size();
        diferencaDeTempos.replaceAll((id, tempo) -> tempo - media);
    }

    private void ajustarTemposDosClientes() throws IOException {
        for (Integer idCliente : socketsClientes.keySet()) {
            DataOutputStream out = new DataOutputStream(socketsClientes.get(idCliente).getOutputStream());
            out.writeLong(diferencaDeTempos.get(idCliente));
        }
    }

    private void fecharTodasConexoes() throws IOException {
        for (Socket socket : socketsClientes.values()) {
            socket.close();
        }
        servidorSocket.close();
    }

    public static void main(String[] args) throws IOException {
        int porta = 9876; 
        ServidorDeTempo servidor = new ServidorDeTempo(porta);
        servidor.iniciarServidor();
    }
}
