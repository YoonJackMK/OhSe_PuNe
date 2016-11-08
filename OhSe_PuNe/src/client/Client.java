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


	// ī�巹�̾ƿ�  �迭  ->   �α���, �κ�    (room ���� �ÿ��� ���ο� â�������� ��Ÿ����? or ���� �����ϰ� �Ǹ� ��â���� �����ϴ� �� ó��?

	String [] panel_name_arr = {"Login", "Lobby"}; // ,"Game_Room"};



	public Client() {
		socket();

		setTitle("�����̻ѳ�");
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
