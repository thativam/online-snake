package com.snake.communication.servClient;

public class Redirect {
    int port;
    String addr;
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getAddr() {
        return addr;
    }
    public void setAddr(String addr) {
        this.addr = addr;
    }
    public Redirect(int port, String addr) {
        this.port = port;
        this.addr = addr;
    }
}
