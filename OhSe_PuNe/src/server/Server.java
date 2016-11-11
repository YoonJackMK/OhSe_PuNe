package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Server {
	HashMap<String, DataOutputStream>clients ;

	public Server() {
		//  이거로 바껴랏
		clients =new HashMap();
		Collections.synchronizedMap(clients);
		SimpleDateFormat sf = new SimpleDateFormat();

		try {
			ServerSocket server = new ServerSocket(7777);
			System.out.println("["+sf.format(new Date())+"]"+"서버 시작");

			while(true)
			{
				Socket client = server.accept();
				System.out.println("["+sf.format(new Date())+"]"+
						"["+"로그인"+":"+client.getPort()+"] 에서 접속");

				new Chat_Receiver(client).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void send_all_chat(String msg)
	{
		Iterator it =clients.keySet().iterator();
		while(it.hasNext())
		{
			try {
				DataOutputStream out = (DataOutputStream)clients.get(it.next());
				System.out.println(msg);

				out.writeUTF(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	class Chat_Receiver extends Thread
	{
		String name;
		DataOutputStream dos;
		DataInputStream dis;
		public Chat_Receiver(Socket client) {
			try {
				name = "["+client.getInetAddress()+"]";
				dos = new DataOutputStream(client.getOutputStream());
				dis = new DataInputStream(client.getInputStream());
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			try {
				name = dis.readUTF();
				clients.put(name, dos);

				while(dis!=null) {
					send_all_chat(dis.readUTF());
					System.out.println(dis.readUTF());
//					dos.writeUTF(dis.readUTF());
					
				}
			} 
			catch (IOException e) {
				clients.remove(name);
			}
		}
	}

	public static void main(String[] args) {
		new Server();
	}
}