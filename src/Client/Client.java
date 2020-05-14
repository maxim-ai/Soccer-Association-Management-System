package Client;

import javafx.util.Pair;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Client {

    private static String userName;
    private static InetAddress serverIP;

    static {
        try {
            serverIP = serverIP = InetAddress.getByName("132.72.65.41");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private static int serverPort=5400;
    private static Socket theServer;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

//    public Client(InetAddress serverIP, int serverPort) {
//        this.serverIP = serverIP;
//        this.serverPort = serverPort;
//    }


    public static void setUserName(String userName) {
        Client.userName = userName;
    }

    public static String getUserName() {
        return userName;
    }

    public static Object connectToServer(Pair<String, Pair<String, List<String>>> objectToServer){
        return start(objectToServer);
    }

    public static Object start(Pair<String, Pair<String, List<String>>> objectToServer) {
        try {
            //if (theServer==null) {
            theServer = new Socket(serverIP, serverPort);
            theServer.setKeepAlive(true);
            objectOutputStream = new ObjectOutputStream(theServer.getOutputStream());//sends to server
            objectInputStream = new ObjectInputStream(theServer.getInputStream());//receive from server
         //   }
            objectOutputStream.flush();
            objectOutputStream.writeObject(objectToServer);
            return objectInputStream.readObject();

                //LOG.info(String.format("Client is connected to server (IP: %s, port: %s)", serverIP, serverPort));
            //theServer.close();!!!

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static Object receivedFromServer(){
//
//    }
}
