package com.liang.web.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
public class TCPSocketThread implements Runnable {

	public static int connectDeviceNum = 0;
	public static Map<String, TCPSocketService> socketMap = new HashMap<String, TCPSocketService>();

	@Override
	public void run() {
		
		Thread current = Thread.currentThread();
		System.out.println("1.Socket start thread: " + current.getName());
		
		int i = 0;
		while (true) {
			ServerSocket serverSocket = null;
			Socket clientSocket = null;
			try {
				serverSocket = new ServerSocket(8647);
				serverSocket.setSoTimeout(10000);
				System.out.println("2.Socket create times is " + ++i);

				while (true) {
					System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

					clientSocket = serverSocket.accept();

					TCPSocketThread.connectDeviceNum = TCPSocketThread.connectDeviceNum + 1;
					System.out.println("3.Socket " + clientSocket.getRemoteSocketAddress().toString()
							+ " connect to server, total num is " + TCPSocketThread.connectDeviceNum);

					// 一旦有连接进入，在开启一个线程负责处理数据
					TCPSocketService tcpSocketService = new TCPSocketService(clientSocket);
					Thread thread = new Thread(tcpSocketService, clientSocket.getRemoteSocketAddress().toString());
					thread.start();
				}
			} catch (SocketTimeoutException s) {
				System.out.println("Socket timed out!");
				s.printStackTrace();
				Iterator<Entry<String, TCPSocketService>> it = socketMap.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<String, TCPSocketService> entry = it.next();
					String key = entry.getKey();
					TCPSocketService value = entry.getValue();
					value.stop(true);
					System.out
							.println("\nThread " + current.getName() + " key = " + key + "; value = " + value + " stop " + s.getLocalizedMessage());
				}
			} catch (SocketException e) {
				System.out.println("error may be set time out exception");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("error may be new socket exception");
				e.printStackTrace();
			} finally {
				try {
					if (serverSocket != null) {
						serverSocket.close();
					}
					if (clientSocket != null) {
						clientSocket.close();
					}
				} catch (IOException e) {
					System.out.println("Socket close exception!");
					e.printStackTrace();
				}
			}
		}
	}

	public Map<String, TCPSocketService> getSocketMap() {
		return socketMap;
	}

	public void setSocketMap(Map<String, TCPSocketService> socketMap) {
		TCPSocketThread.socketMap = socketMap;
	}
}