package client.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import server.ServerJK;

public class Room extends JFrame{
	
	JButton start = new JButton("시작");
	JButton out = new JButton("나가기");
	JTextArea roomChat = new JTextArea();
	JScrollPane rcJs = new JScrollPane(roomChat);
	JTextField chat = new JTextField();
	
	
	public Room() {
		setBounds(0, 0, 920, 690);
		setLayout(null);
		start.setBounds(360, 500, 100, 60);
		add(start);
		out.setBounds(460, 500, 100, 60);
		add(out);
		rcJs.setBounds(310, 100, 300, 300);
		add(rcJs);
		chat.setBounds(310, 410, 300, 70);
		add(chat);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	public static void main(String[] args) {
		new Room();
	}
}
