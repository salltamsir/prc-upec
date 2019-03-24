package channel;

import utils.Input;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class Server extends Thread{ ;
    private Selector selector ;
    private ServerSocketChannel serverSocket ;
    private ByteBuffer buffer;
    private int port;
    private String url;
    Input input ;


    public Server (String url, int port){
        try {
            this.url=url;
            this.port=port;
            this.buffer =ByteBuffer.allocate(65536);
            this.input= new Input(buffer);
            selector = Selector.open();
            serverSocket = serverSocket.open();
            serverSocket.bind(new InetSocketAddress(url, port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void run ()  {
        while (true) {
            try {
                int test = selector.select();
                System.out.println(test + " clients");
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                int i = 1;
                while (iter.hasNext()) {
                    System.out.println("Numero " + (i++));
                    SelectionKey key = iter.next();

                    if (key.isAcceptable()) {
                        register(selector, serverSocket);
                    }

                    if (key.isReadable()) {
                        repeat(key);
                    }
                    iter.remove();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private  void repeat(SelectionKey key) throws IOException {

        SocketChannel client = (SocketChannel) key.channel();
        int n = client.read(buffer);
        if (n<0){
            key.cancel();
            client.close();
            System.out.println("Deconnexion");
            return;
        }
        buffer.flip();
        System.out.println("message recu");
        input.readInfo();

    }

    private  void register(Selector selector, ServerSocketChannel serverSocket)
            throws IOException {

        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }


}