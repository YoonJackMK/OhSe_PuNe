package client;

import java.io.IOException;
import java.net.Socket;
import server.model.UserDto;


public class Client{
	public Socket socket;
	public Client() {
		socket();
	}
	void socket() {
		try {
			Socket socket = new Socket("127.0.0.1",7777);
			this.socket = socket;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new Client();
	}
}
