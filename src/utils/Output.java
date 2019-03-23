package utils;

import channel.Client;
import model.Peer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Output {

    private Charset charset=Charset.forName("UTF-8");
    private ByteBuffer buff= ByteBuffer.allocate(65536);
    Peer peer;

    public Output (){

    }

    public Output (Peer peer){
        this.peer = peer;
    }


    public void sendMsg(SocketChannel socket, String msg){
        buff.clear();
        buff.put((byte)1);
        ByteBuffer b=charset.encode(msg);
        buff.putInt(b.remaining());
        buff.put(b);
        buff.clear();
        send(socket);
    }

    public void sendPort(SocketChannel socketChannel, int port){
        buff.clear();
        buff.put((byte)2);
        buff.putInt(port);
        buff.flip();
        send(socketChannel);
    }

    public void askPeerList(SocketChannel socketChannel){
        System.out.println("Demande de la liste des pairs");
        buff.clear();
        buff.put((byte)3);
        buff.flip();
        send(socketChannel);
    }

    public void sendPeersList(SocketChannel socket, Collection<Client> peers){
        buff.clear();
        buff.clear();
        buff.put((byte)4);
        buff.putInt(peers.size());
        peers.stream().forEach(peer->{
            buff.putInt(peer.getDestPort());
            ByteBuffer b=charset.encode(peer.getDestUrl());
            buff.putInt(b.remaining());
            buff.put(b);

        });
        buff.flip();
        send(socket);
    }

    public void askFileList(SocketChannel socket){
        buff.clear();
        buff.put((byte)5);
        buff.flip();
        send(socket);
    }



    void send(SocketChannel s){
        try {
            s.write(buff);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
