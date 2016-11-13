package client.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import client.Client;
import server.model.UserDao;

public class MainFrame extends JFrame implements ActionListener{

	CardLayout card = new CardLayout();
	Lobby lb = new Lobby();
	Login lg = new Login();
	JPanel p1 = lb.lobby;
	JPanel p2 = lg.login;
	JButton login_btn = new JButton("Login");
	JButton send = new JButton("전송");
	JButton whisper = new JButton("귓말");
	//net res
	Socket socket;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;
	
	Vector userlist = new Vector<>();
	Vector roomlist = new Vector<>();
	StringTokenizer st;
	
	public MainFrame() {
		setTitle("세영이뿌네:그대에게 바치는 세레나데");
		setLayout(card);
		setBounds(10,20, 920, 690);
		add(p1,"로비");
		add(p2,"로그인");
		login_btn.setBounds(500, 500, 100, 40);
		login_btn.setBackground(Color.GRAY);
		p2.add(login_btn);
		login_btn.addActionListener(this);
		send.setBounds(780,560, 70, 30);
		p1.add(send);
		whisper.setBounds(780,240, 70, 30);
		p1.add(whisper);
		whisper.addActionListener(this);
		send.addActionListener(this);
		card.show(getContentPane(), "로그인");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void connect(){
		try 
		{
			socket = new Socket("127.0.0.1", 7777);
			is=socket.getInputStream();
			dis=new DataInputStream(is);
			os=socket.getOutputStream();
			dos=new DataOutputStream(os);
		} 
		catch (IOException e) {e.printStackTrace();}
		send_msg(lg.id_txt.getText().trim());
		userlist.add(lg.id_txt.getText().trim());
		lb.user.setListData(userlist);
		Thread th = new Thread(new Runnable() {
			public void run() {
				while(true)
				{
					try {
						String readmsg=dis.readUTF();
						inmsg(readmsg);
					} catch (IOException e) {e.printStackTrace();}	
				}
 
			}
		});
		th.start();
	}
	void inmsg(String str)
	{
	  st= new StringTokenizer(str, "/");
	  String protocol = st.nextToken();
	  String msg = st.nextToken();
	if(protocol.equals("NewUser"))
	{
		userlist.add(msg);
		lb.user.setListData(userlist);
	}
	else if(protocol.equals("OldUser"))
	{
		userlist.add(msg);
		lb.user.setListData(userlist);
	}
	else if(protocol.equals("Note"))
	{
	  st = new StringTokenizer(msg,"@");
	  String user = st.nextToken();
	  String MMsg = st.nextToken();
	}
	
	
	}
	void send_msg(String str)
	{
		try {
			dos.writeUTF(str);
		} 
		catch (IOException e) {e.printStackTrace();}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login_btn)
		{
			HashMap login_info= new UserDao().login_chk();	
			if(!login_info.containsKey(lg.id_txt.getText()))
				new Pop_up("존재하지 않는 아이디입니다.");
			else
			{
				if(login_info.get(lg.id_txt.getText()).equals(lg.pw_txt.getText()))
				{
					connect();
					card.show(getContentPane(), "로비");
					
				}
				else new Pop_up("비밀번호가 맞지 않습니다.");
			}
		}
		else if(e.getSource()==send)
		{
			send_msg(lb.chat.getText());
		}
		else if(e.getSource()==whisper)
		{
			String user = (String)lb.user.getSelectedValue();
			String note = JOptionPane.showInputDialog("보낼메세지");
			
			if(note!=null)
			{
				send_msg("Note/"+user+"@"+note);
			}
			else new Pop_up("내용을 입력하세요");
		}
	}
	public static void main(String[] args) {
		new MainFrame();
	}
}
