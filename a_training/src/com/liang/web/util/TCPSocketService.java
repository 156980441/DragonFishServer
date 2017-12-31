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

	public Socket m_connectedsocket;
	private boolean m_stop = false;

	Logger logger = Logger.getLogger(TCPSocketService.class);

	public TCPSocketService(Socket connectedsocket) {
		this.m_connectedsocket = connectedsocket;
	}

	public void stop(boolean stop) {
		logger.info("线程： " + Thread.currentThread().getName() + " 线程是否要被关闭  " + stop);
		this.m_stop = stop;
	}

	@Override
	public void run() {

		logger.info("线程： " + Thread.currentThread().getName() + " 开始接受数据");

		InputStream readStream = null;
		DataInputStream device2Server = null;
		ConnectionPool pool = null;
		Connection conn = null;
		String deviceID = null;
		String temp = null;
		PreparedStatement ps = null;

		try {
			pool = ConnectionPool.getInstance();
			conn = pool.getConnection();
			if (conn == null) {
				logger.info("线程： " + Thread.currentThread().getName() + " 获取数据库连接失败。");
				return;
			}
			readStream = m_connectedsocket.getInputStream();
			device2Server = new DataInputStream(readStream);

			// 让线程一直读取
			while (!this.m_stop) {

				temp = inputStream2String(device2Server, deviceID);

				if (temp.equalsIgnoreCase("Internet worm")) {
					logger.info("线程： " + Thread.currentThread().getName() + "网络爬虫。 开始退出线程!");
					temp = null;
					break;
				}

				if (deviceID == null) {
					deviceID = temp;
					// device id as key
					if (!TCPSocketThread.socketMap.containsKey(deviceID)) {
						TCPSocketThread.socketMap.put(deviceID, this);
						logger.info("线程： " + Thread.currentThread().getName() + "将设备ID: " + deviceID + " 存储到 Map");
					} else {
						TCPSocketService existService = TCPSocketThread.socketMap.get(deviceID);
						existService.stop(true);
						TCPSocketThread.socketMap.remove(deviceID);
						logger.info("线程： " + Thread.currentThread().getName() + "删除设备ID: " + deviceID);
						TCPSocketThread.socketMap.put(deviceID, this);
						logger.info("线程： " + Thread.currentThread().getName() + "又一次将设备ID: " + deviceID + " 存储到 Map");
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
						logger.error("线程：" + Thread.currentThread().getName() + " " + deviceID + " " + e.getLocalizedMessage());
						break;
					}
				}
			}
		} catch (IOException e) {
			logger.error("线程：" + Thread.currentThread().getName() + " " + e.getLocalizedMessage());
		} finally {

			logger.info("线程：" + Thread.currentThread().getName() + " 开始释放线程资源 ");

			try {
				if (device2Server != null) {
					device2Server.close();
					logger.info("线程：" + Thread.currentThread().getName() + " 关闭 device2Server ");
				}
				if (conn != null) {
					conn.close();
					logger.info("线程：" + Thread.currentThread().getName() + " 关闭 conn ");
				}
			} catch (IOException e) {
				logger.error("线程：" + Thread.currentThread().getName() + " " + e.getLocalizedMessage());
			} catch (SQLException e) {
				logger.error("线程：" + Thread.currentThread().getName() + " " + e.getLocalizedMessage());
			} finally {
				if (TCPSocketThread.socketMap.containsKey(deviceID))
				{
					TCPSocketThread.socketMap.remove(deviceID);
					logger.info("线程：" + Thread.currentThread().getName() + "删除设备ID: " + deviceID);
				}
				TCPSocketThread.connectDeviceNum = TCPSocketThread.connectDeviceNum - 1;
				logger.info("完成释放线程资源 " + Thread.currentThread().getName() + " 剩余线程个数 ：" + Thread.activeCount());
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
			logger.info("线程：" + Thread.currentThread().getName() + " " + deviceID + " 原始字符串 " + inputStr);

			// 去除网络爬虫
			if (inputStr.indexOf("HTTP") != -1) {
				out = null;
				b = null;
				inputStr = null;
				logger.info("线程：" + Thread.currentThread().getName() + " " + deviceID + " 发现网络爬虫");
				return "Internet worm";
			}

			// 第一次传还没有设备 ID
			if (deviceID == null) {
				// 防止网络不好传过来的数据多余 ID，还带有数据
				int idIndex = inputStr.indexOf("#");
				if (idIndex > 0) {

					out = null;
					b = null;
					logger.info("线程：" + Thread.currentThread().getName() + " " + inputStr.substring(0, idIndex) + " connect server but has #.");
					return inputStr.substring(0, idIndex);
				} else {
					// device id at first time
					out = null;
					b = null;
					logger.info("线程：" + Thread.currentThread().getName() + " " + inputStr + " connect server.");
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
						logger.info("线程：" + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr.substring(lastStartIndex + 1, lastEndIndex));
						return inputStr.substring(lastStartIndex + 1, lastEndIndex);
					} else {
						logger.info("线程：" + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr + ", can't process");
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
						logger.info("线程：" + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr.substring(lastStartIndex + 1, lastEndIndex));
						return inputStr.substring(lastStartIndex + 1, lastEndIndex);
					} else {
						logger.info("线程：" + Thread.currentThread().getName() + " " + deviceID + " send " + inputStr + ", 无法处理");
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