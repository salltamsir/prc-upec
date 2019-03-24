package utils;

import channel.Client;
import model.Peer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Collection;

public class Output {

    private Charset charset=Charset.forName("UTF-8");
    private ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
    Peer peer;

    public Output (){

    }

    public Output (Peer peer){
        this.peer = peer;
    }


    /* Send infos */
    public void sendMessage(SocketChannel socketChannel, String msg){
        byteBuffer.clear();
        byteBuffer.put((byte)1);
        ByteBuffer b=charset.encode(msg);
        byteBuffer.putInt(b.remaining());
        byteBuffer.put(b);
        byteBuffer.clear();
        write(socketChannel);
    }

    public void sendPort(SocketChannel socketChannel, int port){
        byteBuffer.clear();
        byteBuffer.put((byte)2);
        byteBuffer.putInt(port);
        byteBuffer.flip();
        write(socketChannel);
    }



    /* Send request */
    public void requestPeers(SocketChannel socketChannel){
        byteBuffer.clear();
        byteBuffer.put((byte)3);
        byteBuffer.flip();
        write(socketChannel);
    }

    public void requestFiles(SocketChannel socketChannel){
        byteBuffer.clear();
        byteBuffer.put((byte)5);
        byteBuffer.flip();
        write(socketChannel);
    }

    public void requestFragment(SocketChannel socketChannel, String fileName, long totalSize, long start, int fragmentSize){
        System.out.println(socketChannel.socket().getInetAddress().getHostName()+socketChannel.socket().getPort()+" "+start);
        byteBuffer.clear();
        byteBuffer.put((byte)7);
        ByteBuffer b=charset.encode(fileName);
        byteBuffer.putInt(b.remaining());
        byteBuffer.put(b);
        byteBuffer.putLong(totalSize);
        byteBuffer.putLong(start);
        byteBuffer.putInt(fragmentSize);
        byteBuffer.flip();
        write(socketChannel);
    }



    /* Others */

    void write(SocketChannel socketChannel){
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
