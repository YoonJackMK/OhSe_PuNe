package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.GUI.Find_Chk;
import server.model.UserDto;

public class Client_before extends JFrame {



	public Socket socket;

	UserDto dto = new UserDto();
	///////////////////////////////////////////  Login_Frame
	JLabel id = new JLabel("I  D:");
	JTextField id_txt = new JTextField();
	
	JLabel pw = new JLabel("P  W:");
	JPasswordField pw_txt = new JPasswordField();
	
	JButton login_chk = new JButton("�α���");
	
	JButton find_ID = new JButton("���̵�ã��");
	JButton find_PW = new JButton("��й�ȣã��");
	JButton join = new JButton("ȸ������");
	
	///////////////////////////////////////////  Lobby_Frame
	JTextArea txt_area= new JTextArea(); 
	JScrollPane txt_scrol = new JScrollPane(txt_area);
	JTextField chat_area= new JTextField();
	JButton chat_chk = new JButton("����");  
	
	
	
	
	public Client_before() {
		socket();
		
		setTitle("�����̻ѳ�");
		setBounds(10,20,920,690);
		setLayout(null);
		id.setBounds(300, 400, 70, 30);
		id.setFont(new Font("Serif", Font.BOLD, 25));
		add(id);
		pw.setBounds(300, 450, 70, 30);
		pw.setFont(new Font("Serif", Font.BOLD, 25));
		add(pw);
		id_txt.setBounds(360, 400, 240, 30);
		add(id_txt);
		pw_txt.setBounds(360, 450, 240, 30);
		add(pw_txt);
		login_chk.setBounds(500, 500, 100, 40);
		add(login_chk);
		login_chk.addActionListener(new Login_Chk());
		find_ID.setBounds(300, 550, 100, 40);
		add(find_ID);
		find_ID.addActionListener(new Find_Chk(0));
		find_PW.setBounds(400, 550, 100, 40);
		find_PW.addActionListener(new Find_Chk(1));
		add(find_PW);
		join.setBounds(500, 550, 100, 40);
		join.addActionListener(new Find_Chk(2));
		add(join);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	class Login_Chk implements ActionListener { // �α��� Btn_Chk
		@Override
		public void actionPerformed(ActionEvent e) {

			String id_chk ="admin";
			String pw_chk ="admin";
			
			char[] pw_arr1 = new char [pw_chk.length()];
			
			for (int i = 0; i < pw_arr1.length; i++) {
				pw_arr1[i] = pw_chk.charAt(i);
			}
			

			if((id_txt.getText().equals(id_chk))) {
				System.out.println("�̰� ��?"); // ok
				
				// PW �� �ϰ� �ϱ�.
				if((pw_txt.getText().equals(pw_chk))) {
					System.out.println("�α��� ����");
				}
			}
			else
				System.out.println("�α��� ����");
			
			
		}
	}

	class TCPSender {
		DataOutputStream output;
		String name;
		String chat;
		public TCPSender(Socket socket, String str) {
			this.chat = str;
			try {
				output = new DataOutputStream(socket.getOutputStream());
				name = "["+socket.getLocalAddress()+"]";
				output.writeUTF(name+chat);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	class Receiver extends Thread {
		DataInputStream input;
		public Receiver(Socket socket) {
			try {
				input = new DataInputStream(socket.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			while(input!=null) {
				try {
					txt_area.append(input.readUTF()+"\n");
					txt_area.setCaretPosition(txt_area.getDocument().getLength());
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void socket() {
		try {
			Socket socket = new Socket("192.168.30.46",7777);
			this.socket = socket;

			new Receiver(socket).start();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Client_before();
	}
}
