package com.liang.web.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.fanyl.c3p0.ConnectionPool;

public class TCPSocketService implements Runnable {

	public Socket connectedsocket;
	private int timeout;
	private boolean m_stop = false;

	Logger logger = Logger.getLogger(TCPSocketService.class);

	public TCPSocketService(Socket connectedsocket) {
		this.connectedsocket = connectedsocket;
		this.timeout = 1000 * 1 * 5;
	}

	public void stop(boolean stop) {
		
		logger.debug("开始关闭线程 " + Thread.currentThread().getName());
		
		this.m_stop = stop;
		try {  
			connectedsocket.getInputStream().close();  
			logger.debug("开始关闭线程结束 " + Thread.currentThread().getName());
		} catch (IOException e) {
			logger.debug("开始关闭线程异常 " + Thread.currentThread().getName() + e.getLocalizedMessage());
			e.printStackTrace();  
		}  
	}

	@Override
	public void run() {

		logger.debug("线程 " + Thread.currentThread().getName()+ " 开始接受数据");

		InputStream readStream = null;
		DataInputStream device2Server = null;
		ConnectionPool pool = null;
		Connection conn = null;
		String deviceID = null;
		String temp = null;
		PreparedStatement ps = null;
		int waitingTime = 0;

		try {
			pool = ConnectionPool.getInstance();
			conn = pool.getConnection();
			if (conn == null) {
				logger.debug("线程 " + Thread.currentThread().getName()+ " 获取数据库连接失败. Exit!");
				return;
			}
			readStream = connectedsocket.getInputStream();
			device2Server = new DataInputStream(readStream);

			// 让线程一直读取
			while (!this.m_stop) {

				temp = inputStream2String(device2Server, deviceID);

				// handle error data
				if (temp == null) {
					try {
						logger.debug("线程 " + Thread.currentThread().getName()+ " error data, sleep 1000 ms");
						Thread.sleep(1000);
						waitingTime = waitingTime + 1000;
						if (waitingTime == this.timeout) {
							break;
						} else {
							continue;
						}
					} catch (InterruptedException e) {
						logger.debug("线程 " + Thread.currentThread().getName()+ " thead sleep exception");
						e.printStackTrace();
						break;
					}

				}

				if (temp.equalsIgnoreCase("Internet worm")) {
					logger.debug("Socket " + Thread.currentThread().getName()+ " 网络爬虫。 开始退出线程!");
					temp = null;
					break;
				}

				if (deviceID == null) {
					deviceID = temp;
					// device id as key
					if (!TCPSocketThread.socketMap.containsKey(deviceID)) {
						TCPSocketThread.socketMap.put(deviceID, this);
						logger.debug("put " + deviceID + " into map");
					}
				} else {
					String[] comm = temp.split(",");
					temp = null;
					try {
						String sql = " UPDATE tb_machine " + " SET TEMPERATURE = ?, " + " TDS = ?, " + " PH = ?, "
								+ " STATE = ?, " + " UPDATE_DATE = NOW() " + " WHERE ID = ?";
						ps = conn.prepareStatement(sql);
						ps.setString(1, comm[1]);
						ps.setString(2, comm[2]);
						ps.setString(3, comm[3]);
						ps.setString(4, comm[4]);
						ps.setString(5, deviceID);
						ps.executeUpdate();
					} catch (SQLException e) {
						logger.debug("Socket " + Thread.currentThread().getName()+ " SQLException. Exit!");
						break;
					}
				}
			}

		} catch (IOException e) {
			logger.debug("Socket " + Thread.currentThread().getName()+ " " + e.getLocalizedMessage());
			
			e.printStackTrace();

		} finally {
			try {
				if (readStream != null) {
					this.connectedsocket.shutdownInput();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (IOException e) {
				logger.debug("Socket " + Thread.currentThread().getName()+ " " + e.getLocalizedMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				logger.debug("Socket " + Thread.currentThread().getName()+ " " + e.getLocalizedMessage());
				e.printStackTrace();
			} finally {
				if (TCPSocketThread.socketMap.containsKey(deviceID))
					TCPSocketThread.socketMap.remove(deviceID);
				TCPSocketThread.connectDeviceNum = TCPSocketThread.connectDeviceNum - 1;
				logger.debug("设备连接线程 Socket " + Thread.currentThread().getName()+ " 退出！剩余 ：" + Thread.activeCount());
			}
		}
	}

	public String inputStream2String(DataInputStream device2Server, String deviceID) throws IOException {

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[64];
		String inputStr = null;

		for (int n; (n = device2Server.read(b)) != -1;) {
			out.append(new String(b, 0, n));
			inputStr = out.toString();
			logger.debug("线程 " + Thread.currentThread().getName()+ " 原始字符串 " + inputStr);

			// 去除网络爬虫
			if (inputStr.indexOf("HTTP") != -1) {
				out = null;
				b = null;
				inputStr = null;
				logger.debug("线程 " + Thread.currentThread().getName()+ " 发现网络爬虫");
				return "Internet worm";
			}

			// 第一次传还没有设备 ID
			if (deviceID == null) {
				// 防止网络不好传过来的数据多余 ID，还带有数据
				int idIndex = inputStr.indexOf("#");
				if (idIndex > 0) {
					
					out = null;
					b = null;
					logger.debug("线程 " + Thread.currentThread().getName() +" " + inputStr.substring(0, idIndex) + " connect server but has #.");
					return inputStr.substring(0, idIndex);
				} else {
					// device id at first time
					out = null;
					b = null;
					logger.debug("线程 " + Thread.currentThread().getName() + " " + inputStr + " connect server.");
					return inputStr;
				}
			} else {
				// 每次取字节流最后的 # 和 ！ 保证是最近一次的发送数据,可以减少数据库的存储
				int lastStartIndex = inputStr.lastIndexOf("#");
				int lastEndIndex = inputStr.lastIndexOf("!");
				if (lastEndIndex > lastStartIndex) {
					if (lastEndIndex > -1 && lastStartIndex > -1) {
						out = null;
						b = null;
						logger.debug("线程 " + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr.substring(lastStartIndex + 1, lastEndIndex));
						return inputStr.substring(lastStartIndex + 1, lastEndIndex);
					} else {
						logger.debug("线程 " + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr + ", can't process");
						out = null;
						b = null;
						return null;
					}
				} else {
					inputStr = inputStr.substring(0, lastEndIndex + 1);
					lastStartIndex = inputStr.lastIndexOf("#");
					if (lastEndIndex > -1 && lastStartIndex > -1) {
						out = null;
						b = null;
						logger.debug("线程 " + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr.substring(lastStartIndex + 1, lastEndIndex));
						return inputStr.substring(lastStartIndex + 1, lastEndIndex);
					} else {
						logger.debug("线程 " + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr + ", 无法处理");
						out = null;
						b = null;
						return null;
					}
				}
			}
		}
		out = null;
		b = null;
		return null;
	}
}