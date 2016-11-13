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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import client.Client;
import server.model.UserDao;

public class MainFrame extends JFrame implements ActionListener{

	CardLayout card = new CardLayout();
	Lobby lb = new Lobby();
	Login lg = new Login();
	Game_Room gb = new Game_Room();
	JPanel p1 = lb.lobby;
	JPanel p2 = lg.login;
	JPanel p3 = gb.GameRoom;
	JButton login_btn = new JButton("Login");
	JButton send = new JButton("전송");
	JButton whisper = new JButton("귓말");
	JButton crRom = new JButton("방만들기");
	JButton fiRom = new JButton("방찾기");
	JButton joRom = new JButton("방참여");
	
	//net res
	Socket socket;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;

	Vector userlist = new Vector<>();
	Vector roomlist = new Vector<>();
	StringTokenizer st;
	
	String myrom;//내 현재 방

	public MainFrame() {
		setTitle("세영이뿌네:그대에게 바치는 세레나데");
		setLayout(card);
		setBounds(10,20, 920, 690);
		add(p1,"로비");
		add(p2,"로그인");
		add(p3,"게임방");
		login_btn.setBounds(500, 500, 100, 40);
		login_btn.setBackground(Color.GRAY);
		p2.add(login_btn);
		login_btn.addActionListener(this);
		send.setBounds(780,560, 70, 30);
		p1.add(send);
		whisper.setBounds(780,240, 70, 30);
		p1.add(whisper);
		crRom.setBounds(50, 560, 100, 30);
		p1.add(crRom);
		crRom.addActionListener(this);
		fiRom.setBounds(150, 560, 100, 30);
		fiRom.addActionListener(this);
		p1.add(fiRom);
		joRom.setBounds(250, 560, 100, 30);
		joRom.addActionListener(this);
		p1.add(joRom);
		whisper.addActionListener(this);
		send.addActionListener(this);
		card.show(getContentPane(), "로그인");
		//lb.user.setListData(userlist);
		lb.room.setListData(roomlist);
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
		}
		else if(protocol.equals("OldUser"))
		{
			userlist.add(msg);
		}
		
		else if(protocol.equals("Note"))
		{
			String MMsg = st.nextToken();
			System.out.println(msg+"/"+MMsg);
		}
		else if(protocol.equals("userlistupdate"))
		{
			lb.user.setListData(userlist);
		}
		else if(protocol.equals("CreateRoom"))
		{
			myrom = msg;
		}
		else if(protocol.equals("CreateRoomFail"))
		{
			new Pop_up("이미 방이 존재");
		}
		else if(protocol.equals("NewRoom"))
		{
			roomlist.add(msg);
			lb.room.setListData(roomlist);
		}
		else if(protocol.equals("OldRoom"))
		{
			roomlist.add(msg);
		}
		else if(protocol.equals("roomlistupdate"))
		{
			lb.room.setListData(roomlist);
		}
		else if(protocol.equals("Lobby"))
		{
			String note = st.nextToken();
			lb.chatview.append(msg+":"+note+"\n");
		}
		else if(protocol.equals("JoinRoom"))
		{
			card.show(getContentPane(), "게임방");
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
			send_msg("LobbyChat/"+lg.id_txt.getText().trim()+"/"+lb.chat.getText().trim());
		}
		else if(e.getSource()==whisper)
		{
			String user = (String)lb.user.getSelectedValue();
			String note = JOptionPane.showInputDialog("보낼메세지");

			if(note!=null)
			{
				send_msg("Note/"+user+"/"+note);
			}
		}
		else if(e.getSource()==crRom)
		{
			new Room_Create();
			
		}
		else if(e.getSource()==fiRom)
		{
			new Room_Find();
		}
		else if(e.getSource()==joRom)
		{
			String JoinRoom =(String)lb.room.getSelectedValue();
			send_msg("JoinRoom/"+JoinRoom);
		}
	}
	class Room_Create extends JFrame implements ActionListener
	{
		JLabel title = new JLabel("방 제 목");
		JTextField tiTF = new JTextField();
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
			create.addActionListener(this);
			cancel.setBounds(140, 105, 80, 30);
			add(cancel);
			cancel.addActionListener(new Cancel(this));
			setVisible(true);	
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==create)
			{
			  send_msg("CreateRoom/"+tiTF.getText());
			  dispose();
			}
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
	public static void main(String[] args) {
		new MainFrame();
	}
}
