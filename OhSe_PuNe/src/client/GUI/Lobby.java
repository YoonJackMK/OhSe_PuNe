package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;


class Room_Create extends JFrame
{
	JLabel title = new JLabel("�� �� ��");
	JTextField tiTF = new JTextField("�������� �Է��ϼ���");
	JCheckBox hide = new JCheckBox("�������");
	JTextField hiTF= new JTextField();
	JButton create = new JButton("�����");
	JButton cancel = new JButton("���");

	public Room_Create() {
		setTitle("�游���");
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
		cancel.addActionListener(new Cancel(this));
		setVisible(true);
		
	}
	

}

class Room_Find extends JFrame
{
	JLabel roomNum = new JLabel("���ȣ ");
	JTextField rnTF = new JTextField("���ȣ�� �Է��ϼ���");
	JButton chk = new JButton("����");
	public Room_Find() {
		setTitle("��ã��");
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
	JLabel pw = new JLabel("��й�ȣ");
	JPasswordField pwTF = new JPasswordField();
	JButton chk = new JButton("����");
	public Hide() {
		setTitle("��й�ȣ�Է�");
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
class ServerAccess
{
	Lobby lb = new Lobby();
	client.Client ct = new client.Client();
	public ServerAccess() {
		// TODO Auto-generated constructor stub
		new Receiver(ct.socket).start();
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
					
					lb.chatview.append(input.readUTF()+"\n");
					lb.chatview.setCaretPosition(lb.chatview.getDocument().getLength());
					// �����̰� ���� ��ũ�ѹ� ���� �ؼ� �����Ұ�!!!!!!!!!!!!!!!!!!!!!
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			}	
		}
	}
}

class Lobby extends JFrame {
	JTable room = new JTable();
	JScrollPane roJS = new JScrollPane(room);
	JTable user = new JTable();
	JScrollPane usJS= new JScrollPane(user);
	JTextArea chatview = new JTextArea();
	JScrollPane chJS = new JScrollPane(chatview);
	JTextField chat = new JTextField();
	JButton crRom = new JButton("�游���");
	JButton fiRom = new JButton("��ã��");
	JButton send = new JButton("����");
	
	public JPanel lobby = new JPanel();
	
	
	
	
	public Lobby() {
		
		lobby.setBounds(0,0,920,690);
		lobby.setLayout(null);
		room.setBounds(50,50,400,500);
		lobby.add(room);
		user.setBounds(550,50,300,200);
		lobby.add(user);
		chatview.setEditable(false);
		chJS.setBounds(550,280,300,230);
		lobby.add(chJS);
		chat.setBounds(550,520,300,30);
		lobby.add(chat);
		send.setBounds(780,560, 70, 30);
		lobby.add(send);
		crRom.setBounds(50, 560, 100, 30);
		lobby.add(crRom);
		crRom.addActionListener(new Room_Chk(0));
		fiRom.setBounds(150, 560, 100, 30);
		fiRom.addActionListener(new Room_Chk(1));
		lobby.add(fiRom);

	}
	
	
	
	
	
}
