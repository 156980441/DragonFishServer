package com.liang.web.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
		System.out.println("start socket thread...");
		int i = 0;
		while(true) {
			ServerSocket server = null;
			try {
				server = new ServerSocket(8647);
				server.setSoTimeout(10000);
				System.out.println("new socket "+ ++i);
				while (true) {
					System.out.println("Waiting for client on port " + server.getLocalPort() + "...");
					Socket socket = server.accept();

					SocketThread.connectDeviceNum = SocketThread.connectDeviceNum + 1;
					System.out.println(socket.getRemoteSocketAddress() + " connect to server, total num is "
							+ SocketThread.connectDeviceNum);

					// 一旦有连接进入，在开启一个线程负责处理数据
					TcpSocketService tcpSocketService = new TcpSocketService(socket);
					Thread thread = new Thread(tcpSocketService, "device num " + connectDeviceNum);
					thread.start();
				}
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				Iterator<Entry<String, TcpSocketService>> it = socketMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, TcpSocketService> entry = it.next();
					String key = entry.getKey();
					TcpSocketService value = entry.getValue();
					value.stop(true);
					System.out.println("key = " + key + "; value = " + value + "stop");
				}
				
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} catch (IOException e) {
				System.out.println("new socket failed or accept failed");
				e.printStackTrace();
			}
			
		}
	}
	
	public Map<String, TcpSocketService> getSocketMap() {
		return socketMap;
	}

	public void setSocketMap(Map<String, TcpSocketService> socketMap) {
		SocketThread.socketMap = socketMap;
	}
}