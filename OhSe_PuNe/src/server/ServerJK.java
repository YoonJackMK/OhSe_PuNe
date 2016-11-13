package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;
import java.util.Vector;
import client.GUI.Lobby;
public class ServerJK {

	//newwork 자원
	
	ServerSocket server_socket;
	Socket socket;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;
	Vector user_vc = new Vector<>();
	
	StringTokenizer st;
	
	public ServerJK() {
		try {
			server_socket = new ServerSocket(7777);
		} 
		catch (IOException e) {e.printStackTrace();}
		
		if(server_socket!=null)
		{
			Connection();
		}
		
	}
	void Connection()
	{
	
		Thread th = new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("서버 실행한다");	
				while(true){
					socket = server_socket.accept();//접속자 대기
				    UserInfo user = new UserInfo(socket);
				    user.start();	
				}
					
				} 
				catch (IOException e) {e.printStackTrace();}
			}
		});
		th.start();
	}
	public void Send_To_All(String str)
	{
	 	try 
	 	{
			dos.writeUTF(str);
		} 
	 	catch (IOException e) {e.printStackTrace();}
	}
	
	class UserInfo extends Thread 
	{
		Lobby lb = new Lobby();
		InputStream is;
		OutputStream os;
		DataInputStream dis;
		DataOutputStream dos;
		Socket user_socket;
		String Nickname;
		public UserInfo(Socket soc) {
		
			this.user_socket = soc;
			UserNetwork();
		}
		void UserNetwork()
		{
			try {
				is=user_socket.getInputStream();
				dis=new DataInputStream(is);
				os=user_socket.getOutputStream();
				dos=new DataOutputStream(os);
				Nickname = dis.readUTF();
				System.out.println(Nickname+":접속");
			} catch (IOException e) {e.printStackTrace();}
			
			Send_msg_all("NewUser/"+Nickname);
			for (int i = 0; i < user_vc.size(); i++) 
			{
				UserInfo u = (UserInfo)user_vc.elementAt(i);
				Send_msg("OldUser/"+u.Nickname);
			}
			
			user_vc.add(this);
			
		}
		
	    
		
		public void run(){
			while(true)
			{
				try {
					String msg = dis.readUTF();
					System.out.println(Nickname+"가 보낸 메세지:"+msg);
					Inmsg(msg);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		void Inmsg(String str)//클라이언트로부터 오는 메세지
		{
			st = new StringTokenizer(str, "/");
			String protocol = st.nextToken();
			String msg = st.nextToken();
			
			if(protocol.equals("Note"))
			{
				st = new StringTokenizer(msg, "@");
				String user = st.nextToken();
				String Msg = st.nextToken();
				if(Msg!=null){
				for (int i = 0; i < user_vc.size(); i++) {
					UserInfo u  = (UserInfo)user_vc.elementAt(i);
					if(u.Nickname.equals(user))
					{
						u.Send_msg("Note/"+Nickname+"@"+msg);
					}
				}
				}
				
			}
			
		}
		void Send_msg(String str)
		{
		 	try 
		 	{
				dos.writeUTF(str);
			} 
		 	catch (IOException e) {e.printStackTrace();}
		}
		
		void Send_msg_all(String str)//전체용
		{
			for (int i = 0; i < user_vc.size(); i++) 
			{
				UserInfo u = (UserInfo)user_vc.elementAt(i);
				u.Send_msg(str);
				
			}
		}
		
	}
	public static void main(String[] args) {
		new ServerJK();
	}

}
