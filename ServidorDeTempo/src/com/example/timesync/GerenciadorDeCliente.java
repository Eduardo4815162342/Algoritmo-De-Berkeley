package com.example.timesync;


import com.example.timesync.ServidorDeTempo;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

class GerenciadorDeCliente extends Thread {
    private Socket socketCliente;
    private ServidorDeTempo servidor;

    public GerenciadorDeCliente(ServidorDeTempo servidor, Socket socket) {
        this.servidor = servidor;
        this.socketCliente = socket;
        synchronized (servidor) {
            int idCliente = servidor.socketsClientes.size() + 1;
            servidor.socketsClientes.put(idCliente, socket);
            System.out.println("Cliente " + idCliente + " conectado.");
        }
    }

    @Override
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socketCliente.getInputStream());
            long tempoCliente = in.readLong();
            long tempoServidor = System.currentTimeMillis();
            long diferencaTempo = tempoCliente - tempoServidor;
            servidor.adicionarTempoCliente(servidor.socketsClientes.size(), diferencaTempo);

            System.out.println("Tempo recebido do cliente: " + tempoCliente + " | Diferenca: " + diferencaTempo);

            DataOutputStream out = new DataOutputStream(socketCliente.getOutputStream());
            out.writeLong(-diferencaTempo);  // Enviar o ajuste
            out.flush();

            System.out.println("Ajuste enviado para o cliente: " + (-diferencaTempo));
        } catch (IOException e) {
            System.out.println("Erro ao processar dados do cliente: " + e.getMessage());
        } finally {
            try {
                socketCliente.close();  
            } catch (IOException e) {
                System.out.println("Erro ao fechar o socket do cliente: " + e.getMessage());
            }
        }
    }
}
