package model;

import channel.Client;
import channel.Server;

import java.io.IOException;
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


    public void addConnection(String url, int port ){
        try {
            Client client = new Client(url,port);
            //client.start();
            this.connections.put(url,client );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* Sending infos */

    public void sendMessage (String url, String message){
        this.connections.get(url).sendMessage(message);
    }



    public void sendPort(String url, int port){
        this.connections.get(url).sendPort(port);
    }

    /* Requesting  */

    public void requestPeers(String url){
        this.connections.get(url).requestPeers();
        this.connections.get(url).listen();
    }

    public void requestFiles(String url){
        this.connections.get(url).requestFiles();
        this.connections.get(url).listen();
    }
    public void requestFragment(String url,String name, long totalSize, long pos, int size){
        this.connections.get(url).requestFragment(name,totalSize,pos,size);
        this.connections.get(url).listen();
    }



}
