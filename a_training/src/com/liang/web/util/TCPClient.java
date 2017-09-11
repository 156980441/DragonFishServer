package com.liang.web.util;

import java.io.*;
import java.net.Socket;  

public class TCPClient {  
    public static void main(String[] args) {  
    	DataOutputStream bos = null;
        try {  
            Socket socket = new Socket("localhost", 8647);  
            // 向服务器端发送数据  

            bos = new DataOutputStream(socket.getOutputStream());  
            bos.writeUTF("asd");

            while(true){ 
	
            	bos.writeUTF("#27.5,28.4,7.5,301,1!");  
				Thread.sleep(10000);
            } 
            // 接收服务器端数据  
            /*InputStream is = socket.getInputStream();  
            DataInputStream dis = new DataInputStream(is);  
            System.out.println(dis.readUTF());  */
        } catch (Exception e) {  
            e.printStackTrace();  
        }
    }  
}  