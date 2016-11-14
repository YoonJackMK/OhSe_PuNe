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
	public JList room = new JList<>();
	public JScrollPane roJS = new JScrollPane(room);
	public JList user = new JList();
	public JScrollPane usJS= new JScrollPane(user);
	public JTextArea chatview = new JTextArea();
	public JScrollPane chJS = new JScrollPane(chatview);
	public JTextField chat = new JTextField();

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
		
	}
	
}
