/*
CSE471 NETWORK PROJECT
Simge Pinar Erdogan
20180702092
 */

import java.io.*;
import java.net.*;
import java.security.*;
import javax.net.*;
import javax.net.ssl.*;

public class SSLServer implements Runnable{


    private static  void  Listener(Socket serverSocket){

            try {

                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                DataOutputStream outToClient = new DataOutputStream(serverSocket.getOutputStream());;
                String clientSentence = null;
                clientSentence = inFromServer.readLine();
                System.out.println('\n'+"Received in SSLClient: "+'\n' + clientSentence);

                String[] parts = clientSentence.split(":");

                boolean isTCP = true;

                if (parts.length == 2) {
                    isTCP = false;
                }

                if (isTCP) {
                    clientSentence = clientSentence + '\n';
                    outToClient.writeBytes(clientSentence);
                    SSLClientTCPSender(clientSentence);
                    //serverSocket.close();
                } else {
                    int size = Integer.parseInt(parts[0]);
                    String data = parts[1] + '\n';
                    System.out.println('\n'+"Data:" +'\n'+ data);
                    outToClient.writeBytes(data);
                    SSLClientUDPSender(data);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


    }


    private static void SSLClientTCPSender(String lowerizedSentence){

            try {

                Socket SSLServertoTCPServer = new Socket("localhost", 6789);
                DataOutputStream outToClient = new DataOutputStream(SSLServertoTCPServer.getOutputStream());
                lowerizedSentence = lowerizedSentence + '\n';
                outToClient.writeBytes(lowerizedSentence);
                System.out.println('\n'+"From TCP Server: " +'\n'+ lowerizedSentence);

            } catch (IOException e) {
                e.printStackTrace();
            }


    }



    private static void SSLClientUDPSender(String data){

        try {

            DatagramSocket UDPSenderSocket = null;
            UDPSenderSocket= new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = null;
            byte[] receiveData = new byte[100];
            sendData = data.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 6789);
            UDPSenderSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            UDPSenderSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println('\n'+"From UDP Server:" +'\n'+ modifiedSentence);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }







    public static void main(String args[]) throws Exception {

        ServerSocketFactory ssf = SSLServer.getServerSocketFactory("TLS");
        assert ssf != null;
        ServerSocket ss = ssf.createServerSocket(443);



        Thread listenerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket serverSocket = null;
                try {

                    serverSocket = ss.accept();
                    Listener(serverSocket);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        listenerThread.start();



    }



    private static ServerSocketFactory getServerSocketFactory(String tls) {
        if (tls.equals("TLS")) {
            SSLServerSocketFactory ssf;
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

                ssf = ctx.getServerSocketFactory();
                return ssf;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return ServerSocketFactory.getDefault();
        }
        return null;

    }


    @Override
    public void run() {

    }


}