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
	Vector room_vc = new Vector<>();
	StringTokenizer st;
	boolean Roomchk = true;

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
				while(true)
				{	
					try 
					{
						socket = server_socket.accept();//접속자 대기
						UserInfo user = new UserInfo(socket);
						user.start();	
					} 
					catch (IOException e) 
					{
						System.out.println("에러연결발생");
						break;
					}
				}
			}
		});
		th.start();
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
				if(!user_vc.isEmpty())
				{
					for (int i = 0; i <user_vc.size(); i++) 
					{
						UserInfo u  = (UserInfo)user_vc.elementAt(i);
						Send_msg("userlist/"+u.Nickname);
					}
				}

			} catch (IOException e) {
				System.out.println("스트림 에러");
			}
		}
		public void run(){
			while(true)
			{
				try {
					String msg = dis.readUTF();
					Inmsg(msg);
				} catch (IOException e) {
					System.out.println(Nickname+":나감");
					try {
						dos.close();
						dis.close();
						user_socket.close();
						user_vc.remove(this);
						Send_msg_all("UserOut/"+Nickname);
						Send_msg_all("userlistupdate/*");

					} catch (IOException e1) {}
					break;
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
				String Msg = st.nextToken();
				if(Msg!=null)
				{
					for (int i = 0; i < user_vc.size(); i++) {
						UserInfo u  = (UserInfo)user_vc.elementAt(i);
						if(u.Nickname.equals(msg))
						{
							u.Send_msg("Note/"+Nickname+"/"+Msg);
						}
					}
				}
			}
			else if(protocol.equals("CreateRoom"))
			{

				for (int i = 0; i < room_vc.size(); i++) 
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{
						Send_msg("CreateRoomFail/ok");
						Roomchk = false;
						break;
					}

				}
				if(Roomchk)//방 x 새로 생성
				{
					RoomInfo new_room = new RoomInfo(msg, this);
					room_vc.add(new_room);
					Send_msg("CreateRoom/"+msg);
					Send_msg_all("NewRoom/"+msg);
					Send_msg_all("roomupdate/*");
				}
				Roomchk=true;

			}
			else if(protocol.equals("CreateHideRoom"))
			{
				String pw = st.nextToken();
				for (int i = 0; i < room_vc.size(); i++) 
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{
						Send_msg("CreateRoomFail/ok");
						Roomchk = false;
						break;
					}

				}
				if(Roomchk)//방 x 새로 생성
				{
					RoomInfo new_room = new RoomInfo(msg, this);
					new_room.pw=pw;
					room_vc.add(new_room);
					Send_msg("CreateRoom/"+msg);
					Send_msg_all("NewRoom/"+msg);
					Send_msg_all("roomupdate/*");
				}
				Roomchk=true;

			}
			else if(protocol.equals("LobbyChat"))
			{
				String chat = st.nextToken();
				Send_msg_all("Lobby/"+msg+"/"+chat);


			}
			else if(protocol.equals("romChat"))
			{
				String chat = st.nextToken();
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{
						r.BrodaCast_Room(chat);
					}
				}
			}
			else if(protocol.equals("JoinRoom"))
			{
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{	
						if(r.roomUser_vc.size()<2)
						{
							if(r.pw.equals(""))
							{
								r.Add_user(this);
								Send_msg("JoinRoom/"+msg);
							}
							else
								Send_msg("HidenRoom/"+r.pw+"/"+msg);
						}
						else
							Send_msg("full/*");
					}
				}
			}
			else if(protocol.equals("HidenRoom"))
			{
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{
						if(r.roomUser_vc.size()<2)
						{
							r.Add_user(this);
							Send_msg("JoinRoom/"+msg);
						}
						else
							Send_msg("full/*");
					}
				}
			}
			else if(protocol.equals("FindRoom"))
			{
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{
						if(r.roomUser_vc.size()<2)
						{
							if(r.pw.equals(""))
							{
								Send_msg("JoinRoom/"+msg);
								r.Add_user(this);
							}
							else
								Send_msg("HidenRoom/"+r.pw+"/"+msg);
						}
						else
							Send_msg("full/*");
					}
					else if(Roomchk) Send_msg("FindRoomFail/*");
				}

				Roomchk=true;
			}
			else if(protocol.equals("OutRoom"))
			{
				for (int i = 0; i < room_vc.size(); i++) {
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					if(r.roomname.equals(msg))
					{
						Send_msg("OutRoom/ok");
						r.remove_user(this);

						if(r.roomUser_vc.size()==0)
						{

							Send_msg_all("RemoveRoom/"+r.roomname);
							Send_msg_all("roomupdate/*");
							r.remove();
						}
					}
				}
			}
			else if(protocol.equals("userchk"))
			{
				for (int i = 0; i < user_vc.size(); i++) {
					UserInfo u  = (UserInfo)user_vc.elementAt(i);
					if(u.Nickname.equals(msg))
					{
						Send_msg("userexist/*");
					}
				}
			}
			else if(protocol.equals("Nickname"))
			{
				Nickname = msg;
				System.out.println(Nickname+":접속");
				Send_msg_all("NewUser/"+Nickname);
				for (int i = 0; i < user_vc.size(); i++) 
				{
					UserInfo u = (UserInfo)user_vc.elementAt(i);
					Send_msg("OldUser/"+u.Nickname);
				}

				for (int i = 0; i < room_vc.size(); i++) 
				{
					RoomInfo r = (RoomInfo)room_vc.elementAt(i);
					Send_msg("OldRoom/"+r.roomname);
				}

				Send_msg("roomupdate/*");
				user_vc.add(this);

				Send_msg_all("userlistupdate/*");
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
	class RoomInfo 
	{
		String roomname;
		String pw="";
		Vector roomUser_vc = new Vector<>();
		public RoomInfo(String str,UserInfo u) 
		{
			this.roomname = str;
			this.roomUser_vc.add(u);

		}
		public void BrodaCast_Room(String str)
		{
			for (int j = 0; j < roomUser_vc.size(); j++) 
			{
				UserInfo u = (UserInfo)roomUser_vc.elementAt(j);
				u.Send_msg(str);
			}
		}

		void Add_user(UserInfo u)
		{
			this.roomUser_vc.add(u);
		}
		void remove_user(UserInfo u)
		{
			this.roomUser_vc.remove(u);
		}
		void remove()
		{
			room_vc.remove(this);
		}
	}
	public static void main(String[] args) {
		new ServerJK();
	}
}
