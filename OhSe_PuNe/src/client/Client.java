package client;

import java.awt.CardLayout;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

import server.model.UserDto;

public class Client extends JFrame {



	public Socket socket;

	UserDto dto = new UserDto();

	CardLayout card = new CardLayout();


	// 카드레이아웃  배열  ->   로그인, 로비    (room 입장 시에는 새로운 창형식으로 나타나게? or 게임 시작하게 되면 새창에서 시작하는 것 처럼?

	String [] panel_name_arr = {"Login", "Lobby"}; // ,"Game_Room"};



	public Client() {
		socket();

		setTitle("세영이뿌네");
		setBounds(10,20,920,690);
		setLayout(null);




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
