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

	public static int connectDeviceNum = 0;
	public static Map<String, TcpSocketService> socketMap = new HashMap<String, TcpSocketService>();

	@Override
	public void run() {
		try {
			ServerSocket server = new ServerSocket(8647);
			while (true) {
				Socket socket = server.accept();
				
				SocketThread.connectDeviceNum = SocketThread.connectDeviceNum + 1;
				System.out.println("one device connect to server 8647, current " + SocketThread.connectDeviceNum + " devices connect server");
				
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

	public Map<String, TcpSocketService> getSocketMap() {
		return socketMap;
	}

	public void setSocketMap(Map<String, TcpSocketService> socketMap) {
		SocketThread.socketMap = socketMap;
	}
}