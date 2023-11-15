package com.demo;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.HashMap;

public class JavaAsyncUDPServer {
    private static final int PORT = 8083;
    private static DatagramSocket serverSocket;
    private static Map<InetSocketAddress, Integer> clients = new HashMap<>();

    public static void main(String[] args) {
        try {
            serverSocket = new DatagramSocket(PORT, InetAddress.getByName("0.0.0.0"));
            System.out.println("Server is running on port " + PORT);

            while (true) {
                byte[] receiveData = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message from " + receivePacket.getAddress() + ":" + receivePacket.getPort() + ": " + message);

                // Broadcast the message to all connected clients
                broadcastMessage(receivePacket.getData(), receivePacket.getLength(), receivePacket.getAddress(), receivePacket.getPort());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(byte[] data, int length, InetAddress senderAddress, int senderPort) {
        // Iterate over your clients and send the UDP packet to each one
        for (Map.Entry<InetSocketAddress, Integer> entry : clients.entrySet()) {
            InetSocketAddress clientAddress = entry.getKey();
            if (!clientAddress.getAddress().equals(senderAddress) || clientAddress.getPort() != senderPort) {
                int clientPort = entry.getValue();
                try {
                    DatagramPacket packet = new DatagramPacket(data, length, clientAddress.getAddress(), clientPort);
                    serverSocket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
