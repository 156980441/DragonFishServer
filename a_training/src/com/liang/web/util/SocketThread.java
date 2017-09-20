package com.liang.web.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/*
 * 通过实现 Runnable 接口；
 * 通过继承 Thread 类本身；
 * 通过 Callable 和 Future 创建线程。
 * run()新线程的入口点。
 * 必须调用 start() 方法才能执行。
*/

@Component
public class SocketThread implements Runnable {

	private ServerSocket serverSocket;

	public static Map<String, TcpSocketService> socketMap = new HashMap<String, TcpSocketService>();

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(8647);
			int connectDeviceNum = 0;
			while (true) {
				Socket socket = serverSocket.accept();
				
				connectDeviceNum = connectDeviceNum + 1;
				System.out.println("one device connect to server 8647, current " + connectDeviceNum + " devices connect server");
				
				// 一旦有连接进入，在开启一个线程负责处理数据
				TcpSocketService tcpSocketService = new TcpSocketService(socket);
				Thread thread = new Thread(tcpSocketService, "device num " + connectDeviceNum);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("new socket failed or accept failed");
			e.printStackTrace();
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public Map<String, TcpSocketService> getSocketMap() {
		return socketMap;
	}

	public void setSocketMap(Map<String, TcpSocketService> socketMap) {
		SocketThread.socketMap = socketMap;
	}
}