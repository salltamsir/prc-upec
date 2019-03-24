package channel;

import utils.Input;
import utils.Output;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Collection;

public class Client {


    private SocketChannel socketChannel;
    boolean isConnected = false;
    private ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
    private  int destPort;
    private String destUrl;
    private Input input = new Input(byteBuffer);
    Output output = new Output();

    public boolean getConnected() {
        return isConnected;
    }

    public void setConnected(boolean is) {
        isConnected=is;
    }

    public Client(String url, int port) throws IOException {
        this.destUrl=url;
        this.destPort=port;
        SocketAddress sa = new InetSocketAddress(url, port);
        socketChannel = SocketChannel.open();
        socketChannel.connect(sa);
        System.out.println("Connected to "+url);
        isConnected = true;
        byteBuffer.clear();
        this.listen();
        this.listen();

    }


    public  void listen(){
        try {
            int n = socketChannel.read(byteBuffer);
            if(n>0){
                System.out.println("qq chose est arrive dans le buffer "+n);
                this.input.readInfo();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* Sending information */
    public void sendMessage (String s){
        output.sendMessage(socketChannel,s);
    }

    public void sendPort(int port){
        output.sendPort(socketChannel,port);
    }



    /* Requesting */

    public void requestPeers(){
        output.requestPeers(socketChannel);
    }

    public void requestFiles(){
        output.requestFiles(socketChannel);
    }

    public void requestFragment(String name, long totalSize, long pos, int size){
        this.output.requestFragment(socketChannel,"smallfile.png",6148,10,600);
    }





    /* Getters and Setters */

    public int getDestPort() {
        return destPort;
    }

    public void setDestPort(int destPort) {
        this.destPort = destPort;
    }

    public String getDestUrl() {
        return destUrl;
    }

    public void setDestUrl(String destUrl) {
        this.destUrl = destUrl;
    }


}
