package com.liang.web.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class SocketThread implements Runnable {  
	
	private ServerSocket serverSocket;
	public static Map<String, TcpSocketService> socketMap = new HashMap<String, TcpSocketService>();
    @Override
	public void run() {  
    	try {  
            ServerSocket serverSocket = new ServerSocket(8647); 
            while(true){
                Socket socket = serverSocket.accept();  
                TcpSocketService tcpSocketService = new TcpSocketService(socket);
                Thread thread = new Thread(tcpSocketService);
                thread.start();
            }
        } catch (IOException e) {  
            e.printStackTrace();
        }
    }
    
	public ServerSocket getServerSocket() {
		return serverSocket;
	}
	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	public Map getSocketMap() {
		return socketMap;
	}
	public void setSocketMap(Map socketMap) {
		SocketThread.socketMap = socketMap;
	}  
    
}  