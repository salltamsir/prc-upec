package model;

import channel.Client;
import channel.Server;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.TreeMap;

public class Peer {

    private TreeMap <String, Client> connections;
    private Server server;

    public Peer (String url, int port){
        connections = new TreeMap<>();
        server = new Server(url,port);
        //server.start();
    }

    public TreeMap<String,Client> getConnections (){
        return connections;
    }

    public Server getServer(){
        return server;
    }

    public void addConnection(String url, int port ){
        try {
            Client client = new Client(url,port);
            //client.start();
            this.connections.put(url,client );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMess (String url, String message){
        //SocketChannel socketChannel = this.connections.get(url).getSocketChannel();
        this.connections.get(url).sendMessage(message);
    }

    public void sendPeerList(String url){
        this.connections.get(url).sendPeersList(this.connections.values());
    }

    public void sendPort(String url, int port){
        this.connections.get(url).sendPort(port);
    }

    public void askPeerList(String url){
        this.connections.get(url).askPeerList();

    }

    public void askFileList(String url){
        this.connections.get(url).askFileList();
        this.connections.get(url).listen();

    }

    public void getFilesList(String url){
        this.connections.get(url).getFilesList();

    }

}
