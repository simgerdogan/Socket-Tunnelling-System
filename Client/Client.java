/*
CSE471 NETWORK PROJECT
Simge Pinar Erdogan
20180702092
 */
import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Client implements Runnable {

    public static void TCPConnection(){

        try {

            System.out.println("TCP CONNECTION IN TCP CLIENT");
            Socket TCPClientSocket = new Socket("localhost", 8888);
            String sentence;
            String modifiedSentence;
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DataOutputStream outToServer = new DataOutputStream(TCPClientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(TCPClientSocket.getInputStream()));
            System.out.println('\n'+"Please,enter your message :");
            sentence = inFromUser.readLine();
            outToServer.writeBytes(sentence + '\n');
            modifiedSentence = inFromServer.readLine();
            System.out.println('\n'+"Received in TCPClient : "+'\n' + modifiedSentence+'\n');
            //TCPClientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void UDPConnection(){


        try {
            System.out.println("UDP CONNECTION IN UDP CLIENT");
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket UDPclientSocket = null;
            UDPclientSocket = new DatagramSocket();
            InetAddress IPAddress = InetAddress.getByName("localhost");
            byte[] sendData = null;
            byte[] receiveData = new byte[100];
            System.out.println('\n'+"Please,enter your message :");
            String sentence = inFromUser.readLine();
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9999);
            UDPclientSocket.send(sendPacket);
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            UDPclientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println('\n'+"Received in UDPClient : " +'\n'+ modifiedSentence+'\n');


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static void main(String args[]) throws Exception {




        Thread TCPConnectionThread = new Thread(new Runnable() {
            @Override
            public void run() {

                TCPConnection();
            }
        });
        Thread UDPConnectionThread = new Thread(new Runnable() {
            @Override
            public void run() {

                UDPConnection();
            }
        });

        Scanner userInput= new Scanner(System.in);  // Create a Scanner object
        System.out.println("Please press 1 for TCP or press 2 for UDP ");

        int userChoice = userInput.nextInt();  // Read user input

        if(userChoice==1){
            TCPConnectionThread.start();
        }
        else if(userChoice==2){
            UDPConnectionThread.start();
        }
        else{
            System.out.println("Invalid input");
        }




    }

    @Override
    public void run() {
    }


}