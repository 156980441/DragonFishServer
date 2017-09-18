package com.liang.web.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class TcpSocketService implements Runnable {

	public Socket connectedsocket;

	public TcpSocketService(Socket connectedsocket) {
		this.connectedsocket = connectedsocket;
	}

	@Override
	public void run() {
		InputStream readStream = null;
		String deviceID = null;
		String temp = null;
		int index = 0;
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			readStream = connectedsocket.getInputStream();
			DataInputStream device2Server = new DataInputStream(readStream);
			while (true) {
				try {
					temp = inputStream2String(device2Server, deviceID);
					if (deviceID == null) {
						deviceID = temp;
						// device id as key
						if (!SocketThread.socketMap.containsKey(deviceID)) {
							SocketThread.socketMap.put(deviceID, this);
						} else {
							SocketThread.socketMap.remove(deviceID);
							SocketThread.socketMap.put(deviceID, this);
						}
					} else {
						// 如果没有数据就等待
						if (temp == null)
							continue;

						Connection conn = pool.getConnection();
						PreparedStatement ps = null;
						String[] comm = temp.split(",");
						try {
							String sql = " UPDATE tb_machine " + "    SET TEMPERATURE = ?, " + "  	   TDS = ?, "
									+ " 	   	   PH = ?, " + " 	   	   STATE = ?, "
									+ " 	   	   UPDATE_DATE = NOW() " + " 	 WHERE ID = ?";
							ps = conn.prepareStatement(sql);
							ps.setString(1, comm[1]);
							ps.setString(2, comm[2]);
							ps.setString(3, comm[3]);
							ps.setString(4, comm[4]);
							ps.setString(5, deviceID);
							ps.executeUpdate();
						} catch (Exception e) {

							e.printStackTrace();

							readStream.close();
							if (conn != null)
								conn.close();
							System.out.println("database errer when update data from device.");
							break;
						}
					}
					// 强制关闭该进程，回收资源，以免内存溢出
					if (index++ == 2) {
						System.out.println("2 release resource");
						break;
					}
				} catch (Exception e) {
					System.out.println("other exception");
					break;
				}
			}
			
		} catch (Exception e) {
			
			System.out.println("socket get input stream exception");
			e.printStackTrace();
			
		} finally {
			try {
				if (readStream != null)
					readStream.close();
				connectedsocket.close();
				if (SocketThread.socketMap.containsKey(deviceID))
					SocketThread.socketMap.remove(deviceID);
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("thead exit");
		}
	}

	public String inputStream2String(DataInputStream device2Server, String deviceID) throws Exception {

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[128];
		String result = null;

		for (int n; (n = device2Server.read(b)) != -1;) {

			System.out.println("read " + n + " byte into string buffer");
			out.append(new String(b, 0, n));
			String inputStr = out.toString();
			System.out.println("origin string is " + inputStr);

			// 第一次传还没有设备 ID
			if (deviceID == null) {
				// 防止网络不好传过来的数据多余 ID，还带有数据
				int idIndex = inputStr.indexOf("#");
				if (idIndex > 0) {
					System.out.println(inputStr.substring(0, idIndex) + " connect server but has #.");
					return inputStr.substring(0, idIndex);
				} else {
					// device id at first time
					System.out.println(inputStr + " connect server.");
					return inputStr;
				}
			} else {
				// 每次取字节流最后的 # 和 ！ 保证是最近一次的发送数据,可以减少数据库的存储
				int lastStartIndex = inputStr.lastIndexOf("#");
				int lastEndIndex = inputStr.lastIndexOf("!");
				if (lastEndIndex > lastStartIndex) {
					if (lastEndIndex > -1 && lastStartIndex > -1) {
						System.out.println(deviceID + " send " + inputStr.substring(lastStartIndex + 1, lastEndIndex));
						return inputStr.substring(lastStartIndex + 1, lastEndIndex);
					} else {
						System.out.println(deviceID + " send " + inputStr + ", can't process");
						return null;
					}
				} else {
					inputStr = inputStr.substring(0, lastEndIndex + 1);
					lastStartIndex = inputStr.lastIndexOf("#");
					if (lastEndIndex > -1 && lastStartIndex > -1) {
						System.out.println(deviceID + " send " + inputStr.substring(lastStartIndex + 1, lastEndIndex));
						return inputStr.substring(lastStartIndex + 1, lastEndIndex);
					} else {
						System.out.println(deviceID + " send " + inputStr + ", can't process");
						return null;
					}
				}
			}
		}
		return result;
	}
}