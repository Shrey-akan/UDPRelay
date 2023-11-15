package com.demo;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class JavaUdpConnectionService {
    private static final int LOCAL_PORT = 8085;
    private static final int SENDER_PORT = 1234; // Replace with the actual sender port
    private static final String RELAY_IP = "0.0.0.0"; // Replace with the actual relay IP
    private static DatagramSocket udpClient;
    private static InetSocketAddress relayEndPoint;

    public static void main(String[] args) {
        try {
            udpClient = new DatagramSocket();
            relayEndPoint = new InetSocketAddress(RELAY_IP, SENDER_PORT);

            // Replace "Hello, UDP Server!" with the actual control data
            sendUdpData("Hello, UDP Server!");

            while (true) {
                byte[] receiveData = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                udpClient.receive(receivePacket);

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Received message from " + receivePacket.getAddress() + ":" + receivePacket.getPort() + ": " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendUdpData(String data) {
        try {
            // Sends a message to the relay server to establish a connection
            byte[] message = data.getBytes();
            DatagramPacket packet = new DatagramPacket(message, message.length, relayEndPoint);
            udpClient.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
