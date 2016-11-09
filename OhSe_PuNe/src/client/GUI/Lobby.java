package client.GUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;


class Room_Create extends JFrame
{
	JLabel title = new JLabel("방 제 목");
	JTextField tiTF = new JTextField("방제목을 입력하세요");
	JCheckBox hide = new JCheckBox("비공개방");
	JTextField hiTF= new JTextField();
	JButton create = new JButton("만들기");
	JButton cancel = new JButton("취소");

	public Room_Create() {
		setTitle("방만들기");
		setBounds(10,20,300,200);
		setLayout(null);
		title.setBounds(30, 25, 50, 30);
		add(title);
		tiTF.setBounds(110, 25, 120, 30);
		add(tiTF);
		hide.setBounds(30, 65, 80, 30);
		add(hide);
		hiTF.setBounds(110, 65, 120, 30);
		add(hiTF);
		create.setBounds(50, 105, 80, 30);
		add(create);
		cancel.setBounds(140, 105, 80, 30);
		add(cancel);
		
		setVisible(true);

	}
	
}
class Room_Find extends JFrame
{
	JLabel roomNum = new JLabel("방번호 ");
	JTextField rnTF = new JTextField("방번호를 입력하세요");
	JButton chk = new JButton("입장");
	public Room_Find() {
		setTitle("방찾기");
		setBounds(10,20,300,200);
		setLayout(null);
		roomNum.setBounds(30, 50, 70, 30);
		add(roomNum);
		rnTF.setBounds(100, 50, 120, 30);
		add(rnTF);
		chk.setBounds(110, 90, 70, 30);
		add(chk);
		setVisible(true);

	}

}
class Hide extends JFrame{

	JLabel pw = new JLabel("비밀번호");
	JPasswordField pwTF = new JPasswordField();
	JButton chk = new JButton("입장");
	public Hide() {
		setTitle("비밀번호입력");
		setBounds(10,20,300,200);
		setLayout(null);
		pw.setBounds(30, 50, 70, 30);
		add(pw);
		pwTF.setBounds(100, 50, 120, 30);
		add(pwTF);
		chk.setBounds(110, 90, 70, 30);
		add(chk);
		setVisible(true);

	}
}

public class Lobby extends JFrame {
	JTable room = new JTable();
	JScrollPane roJS = new JScrollPane(room);
	
	JTable user = new JTable();
	JScrollPane usJS= new JScrollPane(user);
	
	JTextArea chatview = new JTextArea();
	JScrollPane chJS = new JScrollPane(chatview);
	
	JTextField chat = new JTextField();

	JButton crRom = new JButton("방만들기");
	JButton fiRom = new JButton("방찾기");
	JButton send = new JButton("전송");
	
	Socket socket;
	
	client.Client ct = new client.Client();
	

	public Lobby() {
		
		new Receiver(ct.socket).start();
		
		
		setTitle("대기실");
		setBounds(10,20,920,690);
		setLayout(null);
		room.setBounds(50,50,400,500);
		add(room);
		user.setBounds(550,50,300,200);
		add(user);
		chJS.setBounds(550,280,300,230);
		add(chJS);
		chat.setBounds(550,520,300,30);
		add(chat);
		send.setBounds(780,560, 70, 30);
		add(send);
		crRom.setBounds(50, 560, 100, 30);
		add(crRom);
		fiRom.setBounds(150, 560, 100, 30);
		add(fiRom);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

	public class Receiver extends Thread {
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
					chatview.append(input.readUTF()+"\n");
					chatview.setCaretPosition(chatview.getDocument().getLength());
					// 성훈이가 만든 스크롤바 참고 해서 변경할것!!!!!!!!!!!!!!!!!!!!!
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
	}
	
	
}
