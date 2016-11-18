package client.GUI;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Sender{
	DataOutputStream dos;
	public Sender(Socket socket) {
		// TODO Auto-generated constructor stub
		try {
			dos = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void send_msg(String str)

	{
		try {
			dos.writeUTF(str);
		} 
		catch (IOException e) {e.printStackTrace();}
	}
	
}
