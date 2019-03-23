package channel;

import utils.Input;
import utils.Output;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
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

    public String getDestUrl(){
        return destUrl;
    }
    public int getDestPort(){
        return destPort;
    }

    public  void listen(){
        try {
            int n = socketChannel.read(byteBuffer);
            if(n>0){
                System.out.println("qq chose est arrive dans le buffer "+n);
                this.input.newMsg(socketChannel);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void sendMessage (String s){
        output.sendMsg(socketChannel,s);
    }
    public void sendPort(int port){
        output.sendPort(socketChannel,port);
    }
    public void sendPeersList(Collection<Client> peers){
        this.output.sendPeersList(socketChannel, peers);
    }
    public void askPeerList(){
        output.askPeerList(socketChannel);
    }
    public void askFileList(){
        output.askFileList(socketChannel);
    }
    public void getFilesList(){
        input.getFilesList(this.socketChannel);
    }

    /*public void run(){
        while (isConnected){
            try {
                int n = socketChannel.read(byteBuffer);
                byteBuffer.flip();
                if(n>=0)
                input.newMsg(socketChannel);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    } */

    public SocketChannel getSocketChannel(){
        return socketChannel;
    }


}
