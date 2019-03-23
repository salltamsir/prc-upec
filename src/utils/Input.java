package utils;


import model.Peer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Input {

    int port;
    private int capacity = 65536;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
    private Output out;
    private Peer peer;

    public Input(ByteBuffer byteBuffer) {
        this.byteBuffer=byteBuffer;
        this.port = port;
        out = new Output();
    }
    public Input(ByteBuffer byteBuffer, Peer peer) {
        this.peer = peer;
        this.byteBuffer=byteBuffer;
        this.port = port;
        out = new Output();
    }

    public void newMsg(SocketChannel socket) {
        byteBuffer.flip();
        byte id;
        id = byteBuffer.get();
        System.out.println(id+" retour "+id);
        switch (id){
            case 1 :
                getMsg(socket);
                System.out.println("id = 1");
                break;
            case 2 :
                getPort(socket);
                System.out.println("id = 2");
                break;
            case 3 :
                System.out.println("id = 3");
                //out.sendPeersList(socket,peer.getConnections().values());
            case 4 :
                System.out.println("id = 4");
                break;
            case 6 :
                System.out.println("id=6");
                getFilesList(socket);


        }

        byteBuffer.clear();
    }

    void getMsg(SocketChannel socket) {
        String sto = getString(socket);
        System.out.println("le message issu du serveur: " + sto);

    }

    void getPort(SocketChannel socket){
        System.out.println("Réception du numéro de port");
        int port=getInt();
        System.out.println("Port: "+port);
    }

    void getPeersList(SocketChannel socket){
        System.out.println("Réception de la liste des pairs");
        int n=getInt();
        System.out.println("Nombre de pairs :"+n);
        while(n>0){
            int port=getInt();
            String pair=getString(socket);
            System.out.println("Port: "+port+" Pair: "+pair);
            n--;
        }
        System.out.println("End");
    }

    public void getFilesList(SocketChannel socketChannel){
        System.out.println("Réception de la liste des fichiers");
        int n=getInt();
        System.out.println("nom du fichier : "+n+" lettres");
        while(n>0){
            String file=getString(socketChannel);
            long size=getLong();
            System.out.println("Fichier: "+file+" Taille: "+size);
            n--;
        }
        System.out.println("End");
    }

    String getString(SocketChannel socket){
        try {
            int i=0;
            int taille=getInt();
            byte[] bytes=new byte[taille];
            while(i<taille ){
                bytes[i]=byteBuffer.get();
                i++;
            }
            return new String(bytes);
        } catch(java.nio.BufferUnderflowException e){
            System.out.println("Buffer under flow exception");
        }
        return "Erreur lors de la récupération de la chaine!!!";
    }

    int getInt(){
        byte[] val=new byte[4];
        int i=0;
        while(i<4){
            val[i]=byteBuffer.get();
            i++;
        }
        return ByteBuffer.wrap(val).getInt();
    }

    long getLong(){
        byte[] val=new byte[8];
        try {
            int i=0;
            while(i<8 ){
                val[i]=byteBuffer.get();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(val).getLong();
    }



}