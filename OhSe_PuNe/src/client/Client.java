package client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;

import client.GUI.Lobby;
import client.GUI.Login;
import server.model.UserDto;

public class Client extends JFrame {



	public Socket socket;

	UserDto dto = new UserDto();

	CardLayout card = new CardLayout();
	Login lg = new Login();
	Lobby lb = new Lobby();
	JPanel p1 = lb.lobby;
	JPanel p2 = lg.login;


	public Client() {
		socket();

		setTitle("세영이뿌네");
		setBounds(10,20,920,690);
		setLayout(card);
		add(p1,"로비");
		add(p2,"로그인");
		card.show(getContentPane(), "로그인");

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	

	void socket() {
		try {
			Socket socket = new Socket("192.168.30.46",7777);
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
