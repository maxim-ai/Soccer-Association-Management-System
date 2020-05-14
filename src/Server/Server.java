package Server;

import Server.BusinessLayer.Controllers.*;
import Server.BusinessLayer.OtherCrudOperations.*;
import Server.BusinessLayer.RoleCrudOperations.*;
import Server.DataLayer.DBAdapter;
import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Server {

    private int port=5400;
    private int listeningInterval=1000;
    private volatile boolean stop;
    private HashMap<Socket,Pair<ObjectOutputStream,ObjectInputStream>> openConnections;




    public Server() {
//        this.port = port;
//        this.listeningInterval = listeningInterval;
        openConnections = new HashMap<>();
    }

    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        try {
            int threadPoolSize = 5; //Configurations
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);//newCachedThreadPool();
            threadPoolExecutor.setCorePoolSize(threadPoolSize);

            ServerSocket serverSocket = new ServerSocket(port);
            //serverSocket.setSoTimeout(listeningInterval);

            while (!stop) {
                try { //opens new thread per request!!!!
                    Socket clientSocket = serverSocket.accept(); // waiting for new request and blocking other requests
//                    ObjectOutputStream OOS=new ObjectOutputStream(clientSocket.getOutputStream());
//                    ObjectInputStream OIS=new ObjectInputStream(clientSocket.getInputStream());
//                    openConnections.put(clientSocket,new Pair<>(new ObjectOutputStream(clientSocket.getOutputStream()),new ObjectInputStream(clientSocket.getInputStream())));
                    System.out.println("Client excepted");
                    threadPoolExecutor.execute(() -> {
                        handleClient(clientSocket);
//                        openConnections.put(clientSocket,new Pair<>(OOS,OIS));
                        //LOG.info(String.format("Finished handle client: %s", clientSocket));
                    });
                } catch (SocketTimeoutException e) {
//                    System.out.println("Trying existing connections");
//                    for(Socket socket: openConnections.keySet()){
//                        threadPoolExecutor.execute(() -> {
//                            handleClient(socket,openConnections.get(socket).getKey(),openConnections.get(socket).getValue());
//                        });
//                    }
                    //LOG.debug("Socket Timeout - No clients pending!");
                }
            }
            serverSocket.close();
            threadPoolExecutor.shutdown();
//            try {
//
//                threadPoolExecutor.awaitTermination(1, TimeUnit.HOURS); //wait maximum one hour for all tasks to complete. After one hour, exit.
//
//            } catch (InterruptedException e) {
//
//                System.out.println("Error await termination for ThreadPool "+ e);
//
//            }
        } catch (IOException e) {
            //LOG.error("IOException "+ e);
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            //LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            objectOutputStream.flush();
            Object objectFromClient = objectInputStream.readObject();
            if(objectFromClient!=null) {
                objectOutputStream.writeObject(receiveFromClient((Pair<String, Pair<String, List<String>>>) objectFromClient));
            }

            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Object receiveFromClient(Pair<String, Pair<String, List<String>>> fromClient) {
        String controllerName = fromClient.getKey().split("@")[0];
        String userName = fromClient.getKey().split("@")[1];
        Object controller=null;
        Object answer = null;
        if (controllerName.equals("Guest"))
            controller = new GuestBusinessController();
        else if(controllerName.equals("Owner"))
            controller = new OwnerBusinessController(new Owner(userName));
        else if(controllerName.equals("TeamManager"))
            controller = new TeamManagerBusinessController(new TeamManager(userName));
        else if(controllerName.equals("AssociationRepresentative"))
            controller = new AssociationRepresentativeBusinessController(new AssociationRepresentative(userName));
        else if(controllerName.equals("SystemManager"))
            controller= new SystemManagerBusinessController(new SystemManager(userName));
        else if(controllerName.equals("Player"))
            controller = new PlayerBusinessController(new Player(userName));
        else if(controllerName.equals("Referee"))
            controller =new RefereeBusinessController(new Referee(userName));
        else if(controllerName.equals("Coach"))
            controller = new CoachBusinessController(new Coach(userName));
        else if(controllerName.equals("Fan"))
            controller = new FanBusinessController(new Fan(userName));

        try {
            if(controller!=null) {
                String declareMethod = fromClient.getValue().getKey();
                List<String> parameters = fromClient.getValue().getValue();
//              Method method = controller.getClass().getDeclaredMethod(declareMethod,);
                Method method=getRightMethod(controller.getClass().getDeclaredMethods(),declareMethod);
                answer = method.invoke(controller, parameters.toArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;

    }


    public Method getRightMethod(Method[] methods,String name){
        for(Method method:methods){
            if(method.getName().equals(name))return method;
        }
        return null;
    }

    public void stop() {
        System.out.println("Stopping server..");
        stop = true;
    }

}
