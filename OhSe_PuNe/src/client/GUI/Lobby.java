package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import client.Client;

public class Lobby extends JFrame implements ActionListener {
	public JList room = new JList<>();
	public JScrollPane roJS = new JScrollPane(room);
	public JList user = new JList();
	public JScrollPane usJS= new JScrollPane(user);
	public JTextArea chatview = new JTextArea();
	public JScrollPane chJS = new JScrollPane(chatview);
	public JTextField chat = new JTextField();
	public JButton crRom = new JButton("�游���");
	public JButton fiRom = new JButton("��ã��");
	
	
	MainFrame mf;
	public JPanel lobby = new JPanel();
  
	public Lobby() {
		lobby.setBounds(0,0,920,690);
		lobby.setLayout(null);
		room.setBounds(50,50,400,500);
		lobby.add(room);
		user.setBounds(550,50,300,180);
		lobby.add(user);
		chatview.setEditable(false);
		chJS.setBounds(550,280,300,230);
		lobby.add(chJS);
		chat.setBounds(550,520,300,30);
		lobby.add(chat);
		crRom.setBounds(50, 560, 100, 30);
		lobby.add(crRom);
		crRom.addActionListener(this);
		fiRom.setBounds(150, 560, 100, 30);
		fiRom.addActionListener(this);
		lobby.add(fiRom);
	}
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
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==crRom)
			new Room_Create();
		if(e.getSource()==fiRom)
			new Room_Find();
	}



}
