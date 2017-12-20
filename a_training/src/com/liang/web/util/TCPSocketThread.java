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

import org.apache.log4j.Logger;
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
	Logger logger = Logger.getLogger(TCPSocketThread.class);

	@Override
	public void run() {
		
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		Thread current = null;

		int i = 0;
		current = Thread.currentThread();
		logger.debug("1.Socket 线程启动 " + current.getName());
		try {
			serverSocket = new ServerSocket(8647);
			logger.debug("2.Socket 创建 " + ++i + " 次");

			while (true) {
				logger.debug("等待客户端连接端口： " + serverSocket.getLocalPort());

				clientSocket = serverSocket.accept();

				TCPSocketThread.connectDeviceNum = TCPSocketThread.connectDeviceNum + 1;
				logger.debug("3.客户端 Socket " + clientSocket.getRemoteSocketAddress().toString()
						+ " 连接 server, 当前连接服务端设备个数 " + TCPSocketThread.connectDeviceNum);

				// 一旦有连接进入，在开启一个线程负责处理数据
				TCPSocketService tcpSocketService = new TCPSocketService(clientSocket);
				Thread thread = new Thread(tcpSocketService, clientSocket.getRemoteSocketAddress().toString());
				thread.start();
			}
		} catch (SocketTimeoutException e) {
			logger.error("服务端连接超时 " + e.getLocalizedMessage());
			Iterator<Entry<String, TCPSocketService>> it = socketMap.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<String, TCPSocketService> entry = it.next();
				String key = entry.getKey();
				TCPSocketService value = entry.getValue();
				value.stop(true);
				logger.debug("\nThread " + current.getName() + " key = " + key + "; value = " + value
						+ " stop " + e.getLocalizedMessage());
			}
		} catch (SocketException e) {
			logger.debug(e.getLocalizedMessage());
		} catch (IOException e) {
			logger.debug(e.getLocalizedMessage());
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e) {
				logger.debug(e.getLocalizedMessage());
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