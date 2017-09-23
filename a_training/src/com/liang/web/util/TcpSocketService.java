package com.liang.web.util;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.fanyl.c3p0.ConnectionPool;

public class TcpSocketService implements Runnable {

	public Socket connectedsocket;
	private int timeout;

	public TcpSocketService(Socket connectedsocket) {
		this.connectedsocket = connectedsocket;
		this.timeout = 1000 * 1 * 5;
	}

	@Override
	public void run() {

		System.out.println("start receive thread " + Thread.currentThread().getName());
		InputStream readStream = null;
		DataInputStream device2Server = null;
		String deviceID = null;
		String temp = null;
		ConnectionPool pool = null;
		Connection conn = null;
		PreparedStatement ps = null;
		int waitingTime = 0;

		try {
			pool = ConnectionPool.getInstance();
			readStream = connectedsocket.getInputStream();
			device2Server = new DataInputStream(readStream);
			conn = pool.getConnection();
			if (conn == null) {
				System.out.println("get db connection failed.");
			}
			
			while (true) {

				temp = inputStream2String(device2Server, deviceID);

				if (temp.equalsIgnoreCase("Internet worm")) {
					System.out.println("Internet worm.");
					break;
				}

				if (deviceID == null) {
					deviceID = temp;
					// device id as key
					if (!SocketThread.socketMap.containsKey(deviceID)) {
						SocketThread.socketMap.put(deviceID, this);
					}
				} else {

					// 如果没有数据就等待
					if (temp == null)
					{
						try  
				        {  
							System.out.println("no data, sleep 1000 ms");
				            Thread.sleep(1000);  
				            waitingTime = waitingTime + 1000;
				            if (waitingTime == this.timeout)
				            {
				            	break;
				            }
				            else
				            {
				            	continue;
				            }
				        }  
				        catch (InterruptedException e)  
				        {  
				        	System.out.println("thead sleep exception");
				        	e.printStackTrace();
				        	break;
				        } 
						
					}

					String[] comm = temp.split(",");
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
						System.out.println("SQLException.");
						e.printStackTrace();
						break;
					}
				}
			}

		} catch (IOException e) {

			System.out.println("socket IOException");
			e.printStackTrace();

		} finally {
			try {
				if (readStream != null) {
					device2Server.close();
					readStream.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (IOException e) {
				System.out.println("thead clear IOException");
				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("thead clear SQLException");
				e.printStackTrace();
			} finally {
				if (SocketThread.socketMap.containsKey(deviceID))
					SocketThread.socketMap.remove(deviceID);
				System.out.println("end receive thread " + Thread.currentThread().getName());
			}
		}
	}

	public String inputStream2String(DataInputStream device2Server, String deviceID) throws IOException {

		StringBuffer out = new StringBuffer();
		byte[] b = new byte[128];
		String result = null;

		for (int n; (n = device2Server.read(b)) != -1;) {

			System.out.println("read " + n + " byte into string buffer");
			out.append(new String(b, 0, n));
			String inputStr = out.toString();
			System.out.println("origin string is " + inputStr);

			// 去除网络爬虫
			if (inputStr.indexOf("HTTP") != -1) {
				System.out.println("Internet worm");
				return "Internet worm";
			}

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