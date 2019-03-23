package model;

import java.nio.channels.SocketChannel;

public class Connection {

    private String url;
    private int port;
    private SocketChannel socketChannel;

    public String getUrl() {
        return url;
    }

    public int getPort() {
        return port;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public Connection(String url, int port, SocketChannel socketChannel) {
        this.url = url;
        this.port = port;
        this.socketChannel = socketChannel;
    }


}
