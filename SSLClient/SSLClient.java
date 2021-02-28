/*
CSE471 NETWORK PROJECT
Simge Pinar Erdogan
20180702092
 */

import java.io.*;
import java.net.*;
import java.util.Properties;
import javax.net.SocketFactory;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


import java.security.KeyStore;

public class SSLClient implements Runnable {

    private static void  TCPClientListener(ServerSocket welcomeSocket){


            try {

                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("For TCP , Incoming from IP: " + connectionSocket.getInetAddress().getHostAddress());
                System.out.println("For TCP , Incoming from Port: " + connectionSocket.getLocalPort());
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                String clientSentence;
                clientSentence = inFromClient.readLine();
                System.out.println('\n'+"Received in SSLClient: "+'\n' + clientSentence);
                String capitalizedSentence;
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.writeBytes(capitalizedSentence);
                SSLClientTCPSender(capitalizedSentence);
                clientSentence = clientSentence + '\n';
                //welcomeSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }



    }

    private static void SSLClientTCPSender(String capitalizedSentence){

        try {

            Properties systemProps = System.getProperties();
            systemProps.put("javax.net.ssl.trustStore", "keystore.ImportKey");
            SSLSocketFactory factory = getSSLSocketFactory("TLS");
            SSLSocket SSLClientToServer = (SSLSocket) factory.createSocket("localhost", 443);
            SSLClientToServer.startHandshake();

            DataOutputStream outToClient = new DataOutputStream(SSLClientToServer.getOutputStream());
            outToClient.writeBytes(capitalizedSentence);
            System.out.println('\n'+"From SSLServer : " + '\n'+ capitalizedSentence);
            //SSLClientToServer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }



    private static void UDPClientListener(DatagramSocket serverSocket){

        try {

            byte[] receiveData = new byte[100];
            for(int i =0; i<receiveData.length; i++) {
                receiveData[i] = '|';
            }
            byte[] sendData = null;
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
            System.out.println('\n'+"Received in SSLClient: "+'\n' + sentence);
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
            SSLClientUDPSender(sendPacket);

        }
        catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static void SSLClientUDPSender(DatagramPacket sendPacket) throws IOException {


        try {

            Properties systemProps = System.getProperties();
            systemProps.put("javax.net.ssl.trustStore", "keystore.ImportKey");
            SSLSocketFactory factory = getSSLSocketFactory("TLS");
            SSLSocket SSLClientUDPServer = (SSLSocket) factory.createSocket("localhost", 443);
            SSLClientUDPServer.startHandshake();

            int packetSize = sendPacket.getLength();
            String packetLength = String.valueOf(packetSize);
            System.out.println('\n'+"Packet size : " + packetSize);
            byte[] packetInfo = sendPacket.getData();
            String packetContain = new String(packetInfo);
            System.out.println("Packet contain : " + packetInfo);
            String receivePacket = packetLength+":"+packetContain+'\n';
            System.out.println("Receive packet info : " + receivePacket);

            DataOutputStream outToClient = new DataOutputStream(SSLClientUDPServer .getOutputStream());
            outToClient.writeBytes(receivePacket);
            System.out.println("From SSLServer : " + '\n'+ receivePacket);
            //SSLClientToServer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }


    }



    public static void main(String[] args) throws Exception {


        ServerSocket welcomeSocket = new ServerSocket(8888);

        DatagramSocket serverSocket = new DatagramSocket(9999);



        Thread TCPThread = new Thread(new Runnable() {
            @Override
            public void run() {

                    TCPClientListener(welcomeSocket);
                    System.out.println("tcp thread runn");

            }
        });

        Thread UDPThread = new Thread(new Runnable() {
            @Override
            public void run() {
               UDPClientListener(serverSocket);
                System.out.println("udp thread runn");
            }
        });

            TCPThread.start();
            UDPThread.start();



    }

    private static SSLSocketFactory getSSLSocketFactory(String tls) {

        if (tls.equals("TLS")) {
            SocketFactory ssf = null;
            try {
                SSLContext ctx;
                KeyManagerFactory kmf;
                KeyStore ks;
                char[] passphrase = "importkey".toCharArray();

                ctx = SSLContext.getInstance("TLS");
                kmf = KeyManagerFactory.getInstance("SunX509");
                ks = KeyStore.getInstance("JKS");

                ks.load(new FileInputStream("keystore.ImportKey"), passphrase);
                kmf.init(ks, passphrase);

                ctx.init(kmf.getKeyManagers(), null, null);

                ssf = ctx.getSocketFactory();
                return (SSLSocketFactory) ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return (SSLSocketFactory) SSLSocketFactory.getDefault();
        }
        return null;
    }


    @Override
    public void run() {
        System.out.println("THREAD RUN");
    }


}
