package utils;


import model.Peer;

import java.nio.ByteBuffer;

public class Input {

    int port;
    private ByteBuffer byteBuffer ;
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

    public void readInfo() {
        byteBuffer.flip();
        byte id;
        id = byteBuffer.get();
        switch (id){
            case 1 :
                getMessage();
                System.out.println("id = 1");
                break;
            case 2 :
                getPort();
                System.out.println("id = 2");
                break;
            case 3 :
                System.out.println("id = 3");
            case 4 :
                System.out.println("id = 4");
                getPeers();
                break;
            case 6 :
                System.out.println("id=6");
                getFiles();
                break;
            case 8 :
                System.out.println("id=8");
                getFragment();


        }

        byteBuffer.clear();
    }


    public void getMessage() {
        String sto = readString();
        System.out.println("le message issu du serveur: " + sto);

    }

    public void getPort(){
        int port= readInt();
        System.out.println("Port: "+port);
    }



    public void getFiles(){
        System.out.println("Files : ");
        int n= readInt();
        for(int i=0;i<n;++i){
            String file= readString();
            long size= readLong();
            System.out.println("Received File: "+file+" Size: "+size);
        }
    }

    public void getPeers(){
        System.out.println("Peers");
        int n= readInt();
        for(int i=0;i<n;++i){
            int port= readInt();
            String pair= readString();
            System.out.println("Port: "+port+" Peer: "+pair);
        }

    }

    public void getFragment(){
        String fileName= readString();
        long totalSize= readLong();
        long position= readLong();
        int size= readInt();
        System.out.println("Nalme  : "+fileName+"Total size : "+totalSize+"position : "+position+" size :"+size);

    }


    /* String long int */

    public String readString(){
        try {
            int size= readInt();
            byte[] bytes=new byte[size];
            for(int i=0;i<size;++i){
                bytes[i]=byteBuffer.get();
            }

            return new String(bytes);
        } catch(java.nio.BufferUnderflowException e){
            System.out.println("BufferUnderflowException");
        }
        return "Eroor";
    }

    public int readInt(){
        byte[] bytes=new byte[4];
        for(int i=0;i<4;++i){
            bytes[i]=byteBuffer.get();
        }
        return ByteBuffer.wrap(bytes).getInt();
    }

    public long readLong(){
        byte[] bytes=new byte[8];
        try {
            for(int i=0;i<8;++i){
                bytes[i]=byteBuffer.get();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ByteBuffer.wrap(bytes).getLong();
    }



}