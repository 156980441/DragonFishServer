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
		InputStream is = null;
		String id = null;
		ConnectionPool pool = ConnectionPool.getInstance();
		try {
			is = connectedsocket.getInputStream();
			String temp = null;
			int index = 0;
			while (true) {
				DataInputStream dis = new DataInputStream(is);
				try {
					temp = inputStream2String(dis, id);
					// System.out.print(temp);
					if (id == null) {
						id = temp;
						if (!SocketThread.socketMap.containsKey(id)) {
							SocketThread.socketMap.put(id, this);
						} else {
							SocketThread.socketMap.remove(id);
							SocketThread.socketMap.put(id, this);
						}
					} else {
						if (temp == null)
							continue;
						Connection conn = pool.getConnection();
						PreparedStatement ps = null;
						String[] comm = temp.split(",");
						try {
							String sql = " UPDATE tb_machine " + "    SET TEMPERATURE = ?, " + "  	   TDS = ?, "
									+ " 	   	   PH = ?, " + " 	   	   STATE = ?, "
									+ " 	   	   UPDATE_DATE = NOW() " + " 	 WHERE ID = ? and state = 1";
							ps = conn.prepareStatement(sql);
							ps.setString(1, comm[1]);
							ps.setString(2, comm[2]);
							ps.setString(3, comm[3]);
							String state = comm[4].substring(0, comm[4].length() - 1);
							ps.setString(4, state);
							ps.setString(5, id);
							ps.executeUpdate();
						} catch (Exception e) {
							e.printStackTrace();
							is.close();
							if (conn != null)
								conn.close();
							break;
						}
					}
					// 强制关闭该进程，回收资源，以免内存溢出
					if (index++ == 20) {
						break;
					}
				} catch (Exception e) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
				connectedsocket.close();
				if (SocketThread.socketMap.containsKey(id))
					SocketThread.socketMap.remove(id);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String inputStream2String(DataInputStream dis, String id) throws Exception {
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[128];
		String result = null;
		for (int n; (n = dis.read(b)) != -1;) {
			out.append(new String(b, 0, n));
			String inputStr = out.toString();
			if (id == null) {
				int idIndex = inputStr.indexOf("#");
				if (idIndex > 0) {
					return inputStr.substring(0, idIndex);
				} else {
					return inputStr;
				}
			} else {
				int lastStartIndex = inputStr.lastIndexOf("#");
				int lastEndIndex = inputStr.lastIndexOf("!");
				if (lastEndIndex > lastStartIndex) {
					if (lastEndIndex > -1 && lastStartIndex > -1) {
						return inputStr.substring(lastStartIndex, lastEndIndex + 1);
					} else {
						return null;
					}
				} else {
					inputStr = inputStr.substring(0, lastEndIndex + 1);
					lastStartIndex = inputStr.lastIndexOf("#");
					if (lastEndIndex > -1 && lastStartIndex > -1) {
						return inputStr.substring(lastStartIndex, lastEndIndex + 1);
					} else {
						return null;
					}
				}
			}
		}
		return result;
	}
}