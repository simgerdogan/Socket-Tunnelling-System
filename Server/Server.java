/*
CSE471 NETWORK PROJECT
Simge Pinar Erdogan
20180702092
 */

import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;


public class Server implements Runnable {


    private static void TCPServerTCPListener(ServerSocket SSLServerToTCPServer){

        try {

            Socket connectionSocket = SSLServerToTCPServer.accept();
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            String Sentence;
            Sentence = inFromServer.readLine();
            System.out.println('\n'+"Received in TCP Server : " +'\n'+ Sentence);
            String lowerizedSentence;
            lowerizedSentence = Sentence.toLowerCase() +'\n';
            outToClient.writeBytes(lowerizedSentence);
            System.out.println('\n'+"Final data in TCP Server : " +'\n'+ lowerizedSentence);
            //SSLServerToTCPServer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void TCPServerUDPListener(DatagramSocket SSLServerToUDPServer ){

        try {

            byte[] receiveData = new byte[100];
            for(int i =0; i<receiveData.length; i++) {
                receiveData[i] = '|';
            }
            byte[] sendData = null;
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            SSLServerToUDPServer.receive(receivePacket);
            String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            System.out.println('\n'+"Received in UDP Server : " +'\n'+ sentence);
            System.out.println('\n'+"Final data in UDP Server : " +'\n'+ sentence);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String args[]) throws Exception {

        ServerSocket SSLServerToTCPServer = new ServerSocket(6789);

        DatagramSocket SSLServerToUDPServer = null;
        SSLServerToUDPServer = new DatagramSocket(6789);



        Thread TCPListenerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                TCPServerTCPListener(SSLServerToTCPServer);

            }
        });

        DatagramSocket finalSSLServerToUDPServer = SSLServerToUDPServer;

        Thread UDPListenerThread = new Thread(new Runnable() {
            @Override
            public void run() {

                TCPServerUDPListener(finalSSLServerToUDPServer);

            }
        });

        TCPListenerThread.start();
        UDPListenerThread.start();



    }


    @Override
    public void run() {
    }


}