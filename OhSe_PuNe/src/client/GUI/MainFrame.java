package client.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class MainFrame extends JFrame implements ActionListener
{

	CardLayout card = new CardLayout();
	Lobby lb = new Lobby();
	Login lg = new Login();
	Room ro = new Room();
	JPanel p1 = lb.lobby;
	JPanel p2 = lg.login;
	JPanel p3 = ro.room;
	JButton login_btn = new JButton("Login");
	JButton send = new JButton("전송");
	JButton whisper = new JButton("귓말");
	JButton crRom = new JButton("방만들기");
	JButton fiRom = new JButton("방찾기");
	JButton joRom = new JButton("방참여");
	JButton join_btn = new JButton("Join");
	JButton find_ID_btn = new JButton("Find ID");
	JButton find_PW_btn = new JButton("Find PW");
	JButton start = new JButton("준비");
	JButton cancel = new JButton("준비해제");
	JButton out = new JButton("나가기");
	JButton logout = new JButton("로그아웃");
	Find_PW pw;
	PW_QnA qna;
	PW_Change change;

	//net res
	Socket socket;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;
	StringTokenizer st;
	Sender sd;
	Game_Room ga;
	boolean res = false;
	Vector userlist = new Vector<>();
	Vector roomlist = new Vector<>();


	ArrayList userinfo;//유저정보를 가지고 있는 리스트
	String myrom;//내 현재 방
	String myid;//현재 내 아이디

	public MainFrame() {

		connect();
		setTitle("세영이뿌네:그대에게 바치는 세레나데");
		setLayout(card);
		setBounds(10,20, 920, 690);
		add(p1,"로비");
		add(p2,"로그인");
		add(p3,"게임방");
		login_btn.setBounds(500, 500, 100, 40);
		login_btn.setBackground(Color.GRAY);
		p2.add(login_btn);
		join_btn.setBounds(500, 550, 100, 40);
		join_btn.setBackground(Color.GRAY);
		join_btn.addActionListener(this);
		p2.add(join_btn);
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
		find_ID_btn.setBounds(300, 550, 100, 40);
		find_ID_btn.setBackground(Color.GRAY);
		find_ID_btn.addActionListener(this);
		p2.add(find_ID_btn);
		find_PW_btn.setBounds(400, 550, 100, 40);
		find_PW_btn.setBackground(Color.GRAY);
		find_PW_btn.addActionListener(this);
		p2.add(find_PW_btn);
		whisper.addActionListener(this);
		send.addActionListener(this);
		lb.chat.addActionListener(this);
		lg.pw_txt.addActionListener(this);
		card.show(getContentPane(), "로그인");
		lb.room.setListData(roomlist);

		start.setBounds(410, 500, 100, 60);
		p3.add(start);
		out.setBounds(510, 500, 100, 60);
		p3.add(out);
		cancel.setBounds(310, 500, 100, 60);
		cancel.setEnabled(false);
		p3.add(cancel);
		logout.setBounds(750, 10, 100, 30);
		p1.add(logout);
		logout.addActionListener(this);
		cancel.addActionListener(this);
		start.addActionListener(this);
		out.addActionListener(this);
		ro.chat.addActionListener(this);
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
			sd = new Sender(socket);
		}
		catch (UnknownHostException e)
		{
			new Pop_up("연결실패");
		}
		catch (IOException e) 
		{
			try {
				os.close();
				is.close();
				dos.close();
				dis.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				new Pop_up("연결실패");
				e1.printStackTrace();
			} finally{
				new Pop_up("연결실패");
			}


		}
		Thread th = new Thread(new Runnable() {
			public void run() {
				while(true)
				{
					try 
					{
						String readmsg=dis.readUTF();
						inmsg(readmsg);
					} 
					catch (IOException e) 
					{
						try {
							os.close();
							is.close();
							dos.close();
							dis.close();
							socket.close();
							new Pop_up("연결끊김");
						} catch (IOException e1){}
						break;
					}	
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
			lb.chatview.append(msg+"로부터 귓속말:"+MMsg+"\n");
		}
		else if(protocol.equals("userlistupdate"))
		{
			lb.user.setListData(userlist);
		}
		else if(protocol.equals("roomupdate"))
		{
			lb.room.setListData(roomlist);
		}
		else if(protocol.equals("CreateRoom"))
		{
			myrom = msg;
			card.show(getContentPane(),"게임방");
		}
		else if(protocol.equals("CreateRoomFail"))
		{
			new Pop_up("이미 방이 존재");
		}
		else if(protocol.equals("FindRoomFail"))
		{
			new Pop_up("방이 없어!");
		}
		else if(protocol.equals("NewRoom"))
		{
			roomlist.add(msg);
		}
		else if(protocol.equals("OldRoom"))
		{
			roomlist.add(msg);
		}
		else if(protocol.equals("Lobby"))
		{
			String note = st.nextToken();
			lb.chatview.append(msg+":"+note+"\n");
			lb.chJS.getVerticalScrollBar().setValue(lb.chJS.getVerticalScrollBar().getMaximum());
		}
		else if(protocol.equals("romChat"))
		{
			ro.roomChat.append(msg+"\n");
			ro.rcJs.getVerticalScrollBar().setValue(ro.rcJs.getVerticalScrollBar().getMaximum());
		}
		else if(protocol.equals("JoinRoom"))
		{
			myrom = msg;
			card.show(getContentPane(),"게임방");
		}
		else if(protocol.equals("HidenRoom"))
		{
			String pw = JOptionPane.showInputDialog("비밀번호");
			String name = st.nextToken();
			if(pw.equals(msg))
				sd.send_msg("HidenRoom/"+name);
			else new Pop_up("비밀번호가 맞지 않습니다.");
		}
		else if(protocol.equals("UserOut"))
		{
			userlist.remove(msg);
		}
		else if(protocol.equals("OutRoom"))
		{
			ro.roomChat.setText("");
			card.show(getContentPane(),"로비");
		}
		else if(protocol.equals("RemoveRoom"))
		{
			roomlist.remove(msg);
		}
		else if(protocol.equals("full"))
		{
			new Pop_up("방인원 초과");
		}
		else if(protocol.equals("login"))
		{
			card.show(getContentPane(), "로비");
		}
		else if(protocol.equals("Logout"))
		{
			card.show(getContentPane(), "로그인");
		}
		else if(protocol.equals("Findid"))
		{
			new Pop_up(msg);
		}
		else if(protocol.equals("FindPW"))
		{
			new PW_QnA();
		}
		else if(protocol.equals("PWQnA"))
		{
			new PW_Change();
		}
		else if(protocol.equals("PWchange"))
		{
			new Pop_up("비밀번호 변경");
		}
		else if(protocol.equals("print"))
		{
			st = new StringTokenizer(msg,"&,");

			while(st.hasMoreElements())
			{
				ga.myPanel_2.test(st.nextToken(),st.nextToken(),st.nextToken());
			}
		}
		else if(protocol.equals("Gamestart"))
		{
			start.setEnabled(true);
			cancel.setEnabled(false);
			ga = new Game_Room(socket);
		}
		else if(protocol.equals("Gameset"))
		{
			String res = "record_v";
			new Pop_up(msg);
			if(msg.equals("승리"))
				sd.send_msg("GameResult/"+res);
			else
				sd.send_msg("GameResult/"+"record_d");
		}
		else if(protocol.equals("Fail"))
		{
			if(msg.equals("idexist")) new Pop_up("접속중인 아이디");
			else if(msg.equals("idwrong")) new Pop_up("ID가 틀렸습니다");
			else if(msg.equals("pwwrong")) new Pop_up("비밀번호가 틀렸습니다");
			else if(msg.equals("mail")) new Pop_up("존재하지 않는 메일");
			else if(msg.equals("noname")) new Pop_up("존재하지 않는 이름");
			else if(msg.equals("qna")) new Pop_up("질문/답변을 확인하세요");
			else if(msg.equals("chk")) new Pop_up("비밀번호를 확인하세요");
			else if(msg.equals("ok")) new Pop_up("아이디 사용가능");
			else if(msg.equals("notok")) new Pop_up("사용중인 아이디");
			else if(msg.equals("complete")) new Pop_up("회원가입 완료");
			else if(msg.equals("mailnotok")) new Pop_up("사용중인 이메일");
		}

	}

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==login_btn)
		{
			if(lg.id_txt.getText().equals("")||lg.id_txt.getText().equals(null))
				new Pop_up("아이디를 입력하세요");
			else
			{
				if(lg.pw_txt.getText().equals("")||lg.pw_txt.getText().equals(null))
					new Pop_up("비밀번호를 입력하세요");
				else{
					sd.send_msg("Login/"+lg.id_txt.getText()+"/"+lg.pw_txt.getText());
					myid = lg.id_txt.getText();
				}
			}
		}
		else if(e.getSource()==send)
		{
			if(lb.chat.getText().equals(null)||lb.chat.getText().equals("")||lb.chat.getText().equals("/")){
				new Pop_up("내용을 입력하세요");
			}
			else sd.send_msg("LobbyChat/"+lg.id_txt.getText().trim()+"/"+lb.chat.getText().trim());
			lb.chat.setText("");
			lb.chJS.getVerticalScrollBar().setValue(lb.chJS.getVerticalScrollBar().getMaximum());
		}
		else if(e.getSource()==lb.chat)
		{
			if(lb.chat.getText().equals(null)||lb.chat.getText().equals("")||lb.chat.getText().equals("/")){
				new Pop_up("내용을 입력하세요");
			}
			else sd.send_msg("LobbyChat/"+lg.id_txt.getText().trim()+"/"+lb.chat.getText().trim());
			lb.chat.setText("");

		}
		else if(e.getSource()==whisper)
		{
			String user = (String)lb.user.getSelectedValue();
			String note = JOptionPane.showInputDialog("보낼메세지");

			if(note.equals("")||note.equals(null))
				new Pop_up("메세지를 입력하세요");

			else 
			{
				sd.send_msg("Note/"+user+"/"+note);
				lb.chatview.append(user+"에게 보낸 귓속말:"+note+"\n");
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
			sd.send_msg("JoinRoom/"+JoinRoom);

		}
		else if(e.getSource()==join_btn)
		{
			new Join();
		}
		else if(e.getSource()==find_ID_btn)
		{
			new Find_ID();
		}
		else if(e.getSource()==find_PW_btn)
		{
			new Find_PW();
		}
		else if(e.getSource()==start)
		{	
			start.setEnabled(false);
			cancel.setEnabled(true);

			sd.send_msg("Ready/"+myrom+"/"+myid);
		}
		else if(e.getSource()==cancel)
		{
			cancel.setEnabled(false);
			start.setEnabled(true);
			sd.send_msg("Readycancel/"+myrom+"/"+myid);
		}
		else if(e.getSource()==out)
		{
			sd.send_msg("OutRoom/"+myrom);
		}
		else if(e.getSource()==ro.chat)
		{
			if(ro.chat.getText().equals(null)||ro.chat.getText().equals("")||ro.chat.getText().equals("/")){
				new Pop_up("내용을 입력하세요");
			}
			else 
			{
				sd.send_msg("romChat/"+myrom+"/"+myid+":"+ro.chat.getText());
				ro.chat.setText("");
				ro.rcJs.getVerticalScrollBar().setValue(ro.rcJs.getVerticalScrollBar().getMaximum());
			}
		}
		else if(e.getSource()==logout)
		{
			lb.chatview.setText("");
			card.show(getContentPane(), "로그인");
			sd.send_msg("Logout/"+myid);

		}



	}
	public class Join extends JFrame implements ActionListener {

		Calendar today = Calendar.getInstance();
		JTextField id = new JTextField();
		JPasswordField pw = new JPasswordField();
		JPasswordField pwchk = new JPasswordField();
		JTextField name = new JTextField();
		JLabel num2 = new JLabel("-");
		JTextField number2 = new JTextField();
		JLabel num3 = new JLabel("-");
		JTextField number3 = new JTextField();
		JTextField emailAddress = new JTextField();
		JTextField emailAddress2 = new JTextField();
		JButton b1_1;
		JButton b10;
		JComboBox number,email, yy, mm, dd, quiz;
		JTextField Answer = new JTextField();
		public Join(){
			setTitle("Join SeyoungPuNE");
			setBounds(101, 200, 530, 380);
			setLayout(null);
			id.setBounds(101,  10, 100, 30);
			add(id);
			pw.setBounds(101,  40, 100, 30);
			add(pw);
			pwchk.setBounds(101,  70, 100, 30);
			add(pwchk);
			name.setBounds(101,  100, 100, 30);
			add(name);
			Vector<String> numberArr = new Vector<>();
			numberArr.add("010");
			numberArr.add("011");
			numberArr.add("016");
			numberArr.add("017");
			numberArr.add("019");
			number = new JComboBox<>(numberArr);
			number.setBounds(101,  131, 60, 30);
			add(number);
			num2.setBounds(166, 131, 10, 30);
			add(num2);
			number2.setBounds(176,  131, 60, 30);
			add(number2);
			num3.setBounds(241, 131, 10, 30);
			add(num3);
			number3.setBounds(251,  131, 60, 30);
			add(number3);
			Vector<String> yyArr = new Vector<>();
			for (int i = 1970; i <= today.get(Calendar.YEAR) ; i++) {
				yyArr.add(""+i);
			}
			yy = new JComboBox<>(yyArr);
			yy.setBounds(101, 161, 60, 30);
			add(yy);
			JLabel year = new JLabel("년");
			year.setBounds(166, 161, 20, 30);
			add(year);
			Vector<String> mmArr = new Vector<>();
			for (int i = 1; i <= today.getActualMaximum(Calendar.MONTH)+1; i++) {
				if(i<10) mmArr.add("0"+i);
				else mmArr.add(""+i);
			}
			mm = new JComboBox<>(mmArr);
			mm.setBounds(181, 161, 50, 30);
			add(mm);
			JLabel month = new JLabel("월");
			month.setBounds(236, 161, 20, 30);
			add(month);
			Vector<String> ddArr = new Vector<>();
			for (int i = 1; i <= today.getActualMaximum(Calendar.DATE) ; i++) {
				if(i<10) ddArr.add("0"+i);
				else ddArr.add(""+i);
			}
			dd = new JComboBox<>(ddArr);
			dd.setBounds(251, 161, 50, 30);
			add(dd);
			JLabel date = new JLabel("일");
			date.setBounds(306, 161, 20, 30);
			add(date);
			emailAddress.setBounds(101, 191, 70, 30);
			add(emailAddress);
			JLabel mail = new JLabel("@");
			mail.setBounds(176, 191, 20, 30);
			add(mail);
			Vector<String> emailArr = new Vector<>();
			emailArr.add("daum.net");
			emailArr.add("naver.com");
			emailArr.add("google.com");
			emailArr.add("직접입력");
			email = new JComboBox<>(emailArr);
			email.setBounds(196, 191, 70, 30);
			add(email);
			emailAddress2.setBounds(276, 191, 70, 30);
			add(emailAddress2);
			Vector<String> quizArr = new Vector<>();
			quizArr.add("내 고향은?");
			quizArr.add("내 보물 1호는?");
			quizArr.add("내 출신 학교는?");
			quizArr.add("내 어릴적 별명은?");
			quiz = new JComboBox(quizArr);
			quiz.setBounds(101,221,400,30);
			add(quiz);
			Answer.setBounds(101, 251, 400, 30);
			add(Answer);
			JButton b1 = new JButton("아이디");
			b1.setBackground(Color.gray);
			b1.setEnabled(false);
			b1.setBounds(0, 10, 100, 30);
			add(b1);
			b1_1 = new JButton("중복체크");
			b1_1.setBackground(Color.gray);
			b1_1.setBounds(202, 10, 100, 30);
			add(b1_1);
			b1_1.addActionListener(this);
			JButton b2 = new JButton("비밀번호");
			b2.setBackground(Color.gray);
			b2.setEnabled(false);
			b2.setBounds(0, 40, 100, 30);
			add(b2);
			JButton b3 = new JButton("비번확인");
			b3.setBackground(Color.gray);
			b3.setEnabled(false);
			b3.setBounds(0, 70, 100, 30);
			add(b3);
			JButton b4 = new JButton("이름");
			b4.setBackground(Color.gray);
			b4.setEnabled(false);
			b4.setBounds(0, 100, 100, 30);
			add(b4);
			JButton b5 = new JButton("전화번호");
			b5.setBackground(Color.gray);
			b5.setEnabled(false);
			b5.setBounds(0, 130, 100, 30);
			add(b5);
			JButton b6 = new JButton("생년월일");
			b6.setBackground(Color.gray);
			b6.setEnabled(false);
			b6.setBounds(0, 160, 100, 30);
			add(b6);
			JButton b7 = new JButton("이메일");
			b7.setBackground(Color.gray);
			b7.setEnabled(false);
			b7.setBounds(0, 190, 100, 30);
			add(b7);
			JButton b8 = new JButton("질문");
			b8.setBackground(Color.gray);
			b8.setEnabled(false);
			b8.setBounds(0, 220, 100, 30);
			add(b8);
			JButton b9 = new JButton("답변");
			b9.setBackground(Color.gray);
			b9.setEnabled(false);
			b9.setBounds(0, 250, 100, 30);
			add(b9);
			b10 = new JButton("가입");
			b10.setBackground(Color.gray);
			b10.setBounds(170, 300, 100, 30);
			add(b10);
			b10.addActionListener(this);
			JButton b11 = new JButton("취소");
			b11.setBackground(Color.gray);
			b11.setBounds(270, 300, 100, 30);
			add(b11);

			b11.addActionListener(new Cancel(this));
			setVisible(true);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==b10)
			{
				boolean mailchk = false;
				String mail = null;
				if(email.getSelectedItem().equals("직접입력")){
					if(emailAddress2.getText().equals("")||emailAddress2.getText().equals(null))
						new Pop_up("메일을 입력하세요");
					else{
						mail=emailAddress.getText()+"@"+emailAddress2.getText();
						mailchk=true;
					}

				}
				else {
					mail=emailAddress.getText()+"@"+email.getSelectedItem();
					mailchk=true;
				}
				if(mailchk)
				{
					if(id.getText().equals("")) new Pop_up("ID를 확인하세요.");
					else if(pw.getText().equals("")) new Pop_up("PW를 입력하세요.");
					else if(pwchk.getText().equals("")) new Pop_up("PW확인을 입력하세요.");
					else if(name.getText().equals("")) new Pop_up("이름을 확인하세요.");
					else if(emailAddress.getText().equals("")) new Pop_up("이메일을 확인해주세요.");
					else if(Answer.getText().equals("")) new Pop_up("질문의 답변을 확인하세요.");
					else if(pw.getText().equals(pwchk.getText()))
					{						
						sd.send_msg("Join/"+id.getText()+
								"/"+pw.getText()+"/"+
								name.getText()+"/"+
								number.getSelectedItem()+"-"+number2.getText()+"-"+number3.getText()+"/"+
								yy.getSelectedItem()+"-"+mm.getSelectedItem()+"-"+dd.getSelectedItem()+"/"+
								quiz.getSelectedItem()+"/"+
								Answer.getText()+"/"+
								mail);
					}
					else new Pop_up("PW와 PW확인 일치하지 않습니다.");
				}

			}
			if(e.getSource()==b1_1) 
			{
				if(id.getText().equals("")) new Pop_up("아이디를 입력하세요");
				else
				{
					sd.send_msg("IDchk/"+id.getText());
				}

			}

		}
	}
	class Room_Create extends JFrame implements ActionListener
	{
		JLabel title = new JLabel("방 제 목");
		JTextField tiTF = new JTextField();
		JCheckBox hide = new JCheckBox("비공개방");
		JPasswordField hiTF= new JPasswordField();
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
			hide.addActionListener(this);
			hiTF.setEditable(false);
			create.addActionListener(this);
			cancel.setBounds(140, 105, 80, 30);
			add(cancel);
			cancel.addActionListener(new Cancel(this));
			setVisible(true);	
		}
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==create)
			{

				if(tiTF.getText().equals(null)||tiTF.getText().equals(""))
					new Pop_up("방이름을 입력하세요");
				else{
					if(hide.isSelected())
					{
						if(hiTF.getText().equals(""))
							new Pop_up("비밀번호를 입력하세요");
						else
						{ 	
							sd.send_msg("CreateHideRoom/"+tiTF.getText()+"/"+hiTF.getText());
							dispose();
						}
					}
					else 
					{
						sd.send_msg("CreateRoom/"+tiTF.getText());
						dispose();
					}
				}
			}
			else if(e.getSource()==hide)
			{
				if(hide.isSelected())
				{
					hiTF.setEditable(true);
					hiTF.setText("");
				}
				else 
				{
					hiTF.setEditable(false);
					hiTF.setText("");
				}
			}

		}
	}
	class Room_Find extends JFrame implements ActionListener
	{
		JLabel roomNum = new JLabel("방이름 ");
		JTextField rnTF = new JTextField();
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
			chk.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==chk){
				sd.send_msg("FindRoom/"+rnTF.getText());
				dispose();
			}
		}

	}
	class Find_ID extends JFrame implements ActionListener {


		JLabel name = new JLabel("이름");
		JLabel email = new JLabel("e-mail");
		JTextField nametf = new JTextField();
		JTextField emailtf = new JTextField();
		JButton chk = new JButton("확인");

		public Find_ID() {
			setTitle("아이디찾기");
			setBounds(20,20,300,250);
			setLayout(null);
			name.setBounds(50,50,50,30);
			add(name);
			email.setBounds(50,100,50,30);
			add(email);
			nametf.setBounds(100,50,150,30);
			add(nametf);
			emailtf.setBounds(100,100,150,30);
			add(emailtf);
			chk.setBounds(170, 150, 70, 30);
			add(chk);
			chk.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {

			sd.send_msg("Findid/"+nametf.getText()+"/"+emailtf.getText());
		}
	}
	class PW_QnA extends JFrame implements ActionListener{
		JLabel quiz = new JLabel("질문");
		JLabel answer = new JLabel("답");
		JComboBox quiztf;
		JTextField answertf = new JTextField();
		JButton chk = new JButton("확인");

		public PW_QnA() {
			setTitle("본인확인용QnA");
			setBounds(20,20,300,250);
			setLayout(null);
			quiz.setBounds(40,50,50,30);
			add(quiz);
			answer.setBounds(40,100,50,30);
			add(answer);
			Vector<String> quizArr = new Vector<>();
			quizArr.add("내 고향은?");
			quizArr.add("내 보물 1호는?");
			quizArr.add("내 출신 학교는?");
			quizArr.add("내 어릴적 별명은?");
			quiztf = new JComboBox(quizArr);
			quiztf.setBounds(90,50,150,30);
			add(quiztf);
			answertf.setBounds(90,100,150,30);
			add(answertf);
			chk.setBounds(170, 150, 70, 30);
			add(chk);
			chk.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {

			sd.send_msg("PWQnA/"+((String)quiztf.getSelectedItem())+"/"+answertf.getText());
		}
	}
	class PW_Change extends JFrame implements ActionListener {
		JLabel id = new JLabel("아이디");
		JLabel pw = new JLabel("비밀번호");
		JLabel pwchk = new JLabel("비빌번호 확인");
		JTextField idtf = new JTextField();
		JPasswordField pwtf = new JPasswordField();
		JPasswordField pwchktf = new JPasswordField();
		JButton chk = new JButton("확인");
		public PW_Change() {
			setTitle("비밀번호 변경");
			setBounds(20,20,300,250);
			setLayout(null);
			pw.setBounds(30,60,100,30);
			add(pw);
			pwchk.setBounds(30,100,100,30);
			add(pwchk);
			pwtf.setBounds(120,60,150,30);
			add(pwtf);
			pwchktf.setBounds(120,100,150,30);
			add(pwchktf);
			chk.setBounds(170, 150, 70, 30);
			add(chk);
			chk.addActionListener(this);
			setVisible(true);
		}

		public void actionPerformed(ActionEvent e) {

			if(pwtf.getText().equals(pwchktf.getText()))
				sd.send_msg("PWchange/"+pwtf.getText()+"/"+pwchktf.getText());
			else new Pop_up("비밀번호 불일치");


		}
	}
	class Find_PW extends JFrame implements ActionListener {

		JLabel name = new JLabel("ID");
		JLabel email = new JLabel("e-mail");
		JTextField nametf = new JTextField();
		JTextField emailtf = new JTextField();
		JButton chk = new JButton("확인");
		public Find_PW() {
			setTitle("비밀번호찾기");
			setBounds(20,20,300,250);
			setLayout(null);
			name.setBounds(50,50,50,30);
			add(name);
			email.setBounds(50,100,50,30);
			add(email);
			nametf.setBounds(100,50,150,30);
			add(nametf);
			emailtf.setBounds(100,100,150,30);
			add(emailtf);
			chk.setBounds(170, 150, 70, 30);
			add(chk);
			chk.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {
			sd.send_msg("FindPW/"+nametf.getText()+"/"+emailtf.getText());
		}
	}
	class Room extends JFrame {
		JPanel room = new JPanel();
		public JTextArea roomChat = new JTextArea();
		public JScrollPane rcJs = new JScrollPane(roomChat);
		JTextField chat = new JTextField();
		public Room() {
			room.setBounds(0, 0, 920, 690);
			room.setLayout(null);
			rcJs.setBounds(310, 100, 300, 300);
			roomChat.setEditable(false);
			room.add(rcJs);
			chat.setBounds(310, 410, 300, 30);
			room.add(chat);
		}

	}
	interface GameParameters{

		int width=250;
		int height=600;
		int numW = 6; //6개 들어갈 공간 가로기준
		int inBlok = 12;  //최대 들어갈수 있는 갯수 세로기준
		int boom = 4; //4개 이상일때 사라짐
		int radius = 20;//NextView.setBounds(305,10,130,150);
		///////////////////////////////////////////////////
		int nextWH= 130;//넥스트 창 크기.
		int nestHE= 150;

		Color colors[]={Color.RED,Color.BLUE,Color.GREEN, Color.YELLOW};
		//방향 움직이는 것

		int right=1,left=2,down=3, up=4;

		int right_or = 0, top_or=1,left_or=2, bottom_or=3;
		//B기준  B가오른쪽     B가 위에      B가왼쪽         B가 아래로 

	}
	class Game_Room extends JFrame //전체패널 입니다.
	{

		int color_index_blockC_2;
		int color_index_blockD_2;
		int color_index_blockC;// 내가 게임할떄 미리보기를 보여주기 위해서  int형을로 값을 지정.
		int color_index_blockD;//위와 같음.
		Graphics gg = null;
		Container  c = getContentPane();

		PuyoPanel_1_1 myPanel_1; 	// 패널끌고오는거
		NextPanel_1 nextPanel_1;	//미리보기 패널.
		blockCD_1 CD_1=new blockCD_1();
		/////////////////////////////////////////////////
		String str;
		JLabel Point ;
		JLabel textp;
		JLabel time;
		Sender sd;
		/////////////////////////////////////////
		PuyoPanel_2 myPanel_2 ;			// 게임 페널을 끌고 오기위해서.
		//	blockCD_2 CD_2=new blockCD_2();		// 미리 보기 불럭을 사용 하기위해서 뉴로 새롭게 지정 해줌.
		/////////////////////////////////////////////////
		public Game_Room(Socket socket)//  전체 패널의 크기 를 설정.
		{ 
			super(" 세영이기무찡 ");


			setBounds(20, 20, 920, 690);
			setLayout(null);
			makeGui();
			imageNext();
			makeGui_2();
			addWindowListener(new WinCC());
			sd = new Sender(socket);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		}


		///////////////////////////////////////////////////////////////////////////////////////////
		void makeGui()// 페널 클레스를  컨테이터 를 사용해서  그릴수 있는 그림판  을 만들어 주는 메소드.
		{
			// 컨테이너로  페널을 붙여 주었다 프레임에.
			myPanel_1 = new PuyoPanel_1_1();
			myPanel_1.setBounds(5,20,255,600); // 만든 페널의 크기와 위피를 조정 해주었다.

			c.add(myPanel_1, "Center");

		} 
		void imageNext()// 내 미리보기 페널 위치를 지정해준다,
		{
			//Container  c = getContentPane();
			nextPanel_1 = new NextPanel_1();
			nextPanel_1.setBounds(305,10,130,150);
			//	   add(nextPanel);
			c.add(nextPanel_1,"Center");//미리 보기 패널을 만들었습니다.
		}
		////////////////////////////////////////////////
		void makeGui_2()//상대팀 페널과 위치를 지정 해줫다.
		{
			//Container  c = getContentPane();
			myPanel_2 = new PuyoPanel_2();// 뿌유 페널을 만든걸 새롭게 지정 을 해줫다.
			myPanel_2.setBounds(600,20,255,600); // 만든 페널의 크기와 위피를 조정 해주었다.

			c.add(myPanel_2, "Center");// 컨테이너로  페널을 붙여 주었다 프레임에.


		}
		class WinCC extends WindowAdapter{

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				dispose();
				myPanel_1.showMessage();
				myPanel_1.animator1_2.stop();
				nextPanel_1.animator1_1.stop();
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		class Block{// 블럭을 생성 해주는 클레스를 만들었다.,

			int  X,Y, lastX, lastY;//  x , y  축 인수..
			int radius;//  반지름./
			Color color;// 색을 가지고 있는 클레스.
			int pipe;    // 파이프를 (인트형.)
			int pipe2;// 파이프 인트형.
			boolean four;
			///////////////////////////////////////////////////////////////////////////////////////////     
			Block(int X, int Y, int radius, Color color)// 블럭의 생성자를 만들고 // 원을 그릴떄의 좌표밑. 크기를 설정해준다.
			{
				this.X= X;   
				this.Y= Y;
				lastX = X;
				lastY = Y;
				this.radius = radius;
				this.color  = color;     

				pipe =-1;// 파이프를 기본값 -1 줫다.
				pipe2=-1;//파이프2  기본값 -1 을 줫다.
				four = false;//블럭들을 없에기 위해서 만든 거.
			}
			String moveData()/// 바뀐부분.
			{// 스트링 형테로 데이터를 받습니다.
				// 리턴값을 x,y  받고 색을 가지고 온다.
				return X+","+Y+","+color.getRGB();
			}
			///////////////////////////////////////////////////////////////////////////////////////////
			public void paint(Graphics g){// 원을 그려준다.
				g.setColor(color);
				g.fillOval(X-radius,Y-radius, 2*radius,2*radius);//좌표와@ 크기를 해줘서 원을 그려준다.

			}
			///////////////////////////////////////////////////////////////////////////////////////////
			public boolean collideDown_1(Block b)// 충돌 됫을떄를 가정하에 블린형태로 생성 했다.
			{	// 
				// 기존에 멈춰 있는 블럭과  현재 생성된 블럭(충돌 됫을떄를 블린형태로)의 좌표보다 크다면  을 블린형태로  돌려준다.
				return Y+radius >= b.Y-radius;
				//현재 멈춰있는 블럭을 Y+반지름  이      현재 움직이고있는 블럭의 Y+반지름 보다 크다면 .
			}   
			//////////////////////////////////////////////////////////////////////////
			//정지되어 있는 블럭을  내가 움직일수있는 블럭으로 오른쪽 으로 들어가지 못하게 한기 위해서.
			public boolean chkLeft(Block b)
			{// 뭠춰 있는 블럭이  움직이는 블럭의 좌표보다  작을경우
				return b.X+radius <= Y-radius;
			}
			//////////////////////////////////////////////////////
			//정지 되어 있는 블럭을  움직일수 블럭으로 왼쪽을 눌럿을떄 들어가지 못하게 하기위해서.
			public boolean chkRight(Block b){
				return  b.chkLeft(this);
			}  
		}
		class blockPipe{// 블럭의 파이프를 만들어 줫다.
			LinkedList blocks;// 링크드 리스트 형태의 블럭스를 만든다.
			public blockPipe(){
				blocks= new LinkedList();// 세롭게  링크드 리스트를 정의 해준다..
			}
			public void paint(Graphics g)//  그레픽을 사용해서 페인트를 한다.
			{
				Iterator itr= blocks.iterator();
				//이터레이터  형식 itr 을 만들고  = 그안에 블럭 형태의./이터레이터 형식을 넣는다.
				while(itr.hasNext())// 데이터를 바꿔논걸 사용한다.
				{
					Object element= itr.next();// 오버잭트 형식으로   변환 시켜준다.
					Block b = (Block) element;// (블럭 형태) 테이터를   블럭 b 에 넣는다.
					b.paint(g);//  프린트 한다.
				}
			}
			public int getSize()// 인트형 메소드 사이즈를 만들고.
			{
				return blocks.size();// 리턴값  블럭의 . 크기를 돌려준다./
			}
		}
		class BlockAB_1{  // 블럭을 그리는 걸 A  B 로 나누기 위해서 생성한 클레스.

			Block blockA; // 블럭 클레스 정보를 A로 만든다
			Block blockB;//  블럭 클레스 정보를 B로 만든다.

			int blockB_OR;  //A를 기준으로 B가 움직임 용


			public void paint(Graphics g){// 블럭을 만들어줘서 그려주는 역활을 해준다.
				blockA.paint(g);
				blockB.paint(g);

			}
		}
		///////////////////////////////////////////////////////////////\
		class blockCD_1////???
		{
			Block blockC;
			Block blockD;
			public void paint(Graphics g){// 블럭을 만들어줘서 그려주는 역활을 해준다.
				blockC.paint(g);
				blockD.paint(g);
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class PuyoGame1_1  implements GameParameters 
		{
			public void startGame1_1_1()
			{
				CD_1=new blockCD_1();
				nextBB_1();
			}
			public void nextBB_1()//다음에 나올 블럭을 생성 해준다.
			{
				Random r = new Random();
				int colorindex = r.nextInt(4);//  컬러를 만들고 렌덤으로 4까지 돌수 있게 만들엇따.
				int width=125;
				//NextView.setBounds(305,10,130,150);
				Block blockC= new Block(width/2-25, 20, 20, colors[colorindex]);// 블럭을 생성.
				color_index_blockC=colorindex;
				colorindex= r.nextInt(4);// 색을 다르게 해야해서 다시 컬러를 줫다..
				Block blockD= new Block(width/2+15, 20, 20,  colors[colorindex]);
				color_index_blockD=colorindex;
				CD_1.blockC= blockC;//A블럭이라고 지정 해줌.
				CD_1.blockD =blockD;//B 블럭이라고 지정 해줌.
			}
			public void Render1(Graphics g)// 블럭 그림? 현황판?  계속 그려주는 역활../
			{ 
				CD_1.paint(g);
			}
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		public class PuyoGame1   implements GameParameters {//인터 페이스 받은 푸유 게임..

			PuyoGame1_1 pg2;
			BlockAB_1  AB_1; // 사용자가 블럭을 움직일수 있는 짝 세트//  블럭을 A   B  로 나눠서 그려주는 역활을 한다.
			blockPipe [] pipes_1;//  파이프를 배열로 만들어서. 공간을 할당 한다.
			LinkedList mBlocks_1;// 린크드 리스트 형식 데이터를 받을 공간을 만들어준다.
			int score =0;//  
			public void StartGame_1()//  게임을 스타트  메소드
			{
				AB_1 = new BlockAB_1();//블럭을 만들어주는 클레스  새롭게 지정 해준다.
				mBlocks_1 = new LinkedList();// 링크드 리스트를 새롭게 정의 했다.  움직이지 않는 블럭.! 통재권 없는 블럭.
				pipes_1 = new blockPipe[numW];// 파이프 배열에  최대 몇개의 파이프를 만들지를 새롭게 지정해줫다.
				// 클레스를 새롭게 정의를 해줌.
				for (int i = 0; i < numW ; i++) //  6  번을 돌면서.
					pipes_1[i] =new blockPipe();// 파이프 베열에. 6개의 파이프를 만들어 주고.(생)
				firstAB_1();// 첫번째 매소드를 돌릴 것이다.
			}
			public void  firstAB_1()// 블럭의 크기 및 좌표를 설정 해주는 메소드.
			{
				Random r = new Random();// 렌덤으로 만들고.
				int left =2;// 인트형 값 2개.
				int right = left+1;// 레프트 +1    =  라이트..
				int colorindex = r.nextInt(4);//  컬러를 만들고 렌덤으로 4까지 돌수 있게 만들엇따.
				Block blockA_1= new Block(width/2-25,20, 20, colors[color_index_blockC]);// 블럭을 생성.
				colorindex= r.nextInt(4);// 색을 다르게 해야해서 다시 컬러를 줫다..
				Block blockB_1= new Block(width/2+15, 20, 20, colors[color_index_blockD]);
				blockA_1.pipe =left;// A블럭의 파이프는 =2
				blockB_1.pipe = right;// B블럭 파이프는 3 으로 지정.
				//AB 보관
				AB_1.blockA= blockA_1;//A블럭이라고 지정 해줌.
				AB_1.blockB =blockB_1;//B 블럭이라고 지정 해줌.
				AB_1.blockB_OR= right_or;//맨처음 생성이 됫을때  B블럭은 항상 A블럭의 오른쪽으로 하기위해서.
				new PuyoGame1_1().nextBB_1();
			}
			public boolean Update_1()//블린 형테 메소드./
			{
				moveMBlocks_1();// 무브MB블럭을 블러온ㄷㅏ
				movepair_1();
				boolean gameOver = false; // 투른지 풜스인지..  그냥 변수값을 준거.
				for (int i = 0; (i<numW) && !gameOver ; i++) // 
					gameOver = (pipes_1[i].getSize() > inBlok);
				return gameOver;
			}
			////////////////////////////////////////////////////////////////////////
			public int getScore_1(){
				return score;
			}
			////////////////////////////////////////////////////////////////////////
			public void render_1(Graphics g)// 블럭 그림? 현황판?  계속 그려주는 역활../쓰레드를 시작하는부분.
			{
				AB_1.paint(g);//블럭 그려준다  A,B 블럭//움직이는 아이들의 색을 그려준다.
				for (int i = 0; i < numW; i++) //블럭을그려줌.
					pipes_1[i].paint(g);
				for (int i = 0; i < mBlocks_1.size(); i++) // 남아있는 블럭 을 그려줌.
				{
					Block bk = (Block) mBlocks_1.get(i);
					bk.paint(g);// 프린트해준다고함
				}
			}
			////////////////////////////////////////////////////////////////////////
			boolean moveBlock_1(Block bk)//현재 돌아가는 블럭을 가지고 온다.
			{
				if (! pipes_1[bk.pipe].blocks.isEmpty())//만약 파이프S [현재블럭 파이프].
				{
					if(!bk.collideDown_1((Block) pipes_1[bk.pipe].blocks.getLast()))
					{// 내가 현재 움직이는 블럭의 
						bk.Y+=1;
						//               System.out.println(bk.pipe +" pipe --1");
						//					if(bk.moving())
						//						System.out.println("다았다");
						return true;

					}
					else
					{   
						bk.pipe2 = pipes_1[bk.pipe].blocks.size();
						pipes_1[bk.pipe].blocks.add(bk);
						//   System.out.println(bk.pipe +" pipe--2");
						return false;
					}
				}
				if(!bound_1(bk, down)){
					bk.Y+=1;
					return true;
				}
				else 
				{
					bk.pipe2 = pipes_1[bk.pipe].blocks.size();
					pipes_1[bk.pipe].blocks.addLast(bk);
					return false;
				}

			}
			void movepair_1(){//  A,B가 움직일때 상황을 말해준다.//
				boolean ba = moveBlock_1(AB_1.blockA);
				boolean bb = moveBlock_1(AB_1.blockB);
				if((!ba) && (AB_1.blockB_OR==top_or))
				{
					chkColor_1(AB_1.blockA);
					if(AB_1.blockA.color!=AB_1.blockB.color)
						chkColor_1(AB_1.blockB);
					firstAB_1();//블럭 생성 하는 메소드/

				}

				//B블럭은 움직이지 않고  A가 아래있을때
				else if ((!bb) && (AB_1.blockB_OR==bottom_or))
				{
					pipes_1[AB_1.blockB.pipe].blocks.add(AB_1.blockA);
					AB_1.blockA.pipe2= AB_1.blockB.pipe2+1;
					chkColor_1(AB_1.blockB);
					if(AB_1.blockA.color!=AB_1.blockB.color)
						chkColor_1(AB_1.blockA);
					firstAB_1();

				}
				//a는 움직이고 b는 움직이지 않을때
				else if (ba && (!bb) )
				{  
					// a블러은 mBlock에 넣어둠
					mBlocks_1.addLast(AB_1.blockA);
					chkColor_1(AB_1.blockB);
					firstAB_1();
				}

				else if( bb && (!ba) )
				{ 
					mBlocks_1.addLast(AB_1.blockB);
					chkColor_1(AB_1.blockA);
					firstAB_1();

				}
				//둘다 수평일때 움직이지 않을때
				else if (!(ba && bb))
				{ 
					if(AB_1.blockA.color!=AB_1.blockB.color)
						chkColor_1(AB_1.blockA);
					chkColor_1(AB_1.blockB);
					firstAB_1();
				}
			}
			//컨트롤 할수없고 움직이는 아이들
			////////////////////////////////////////////////////////////////////////      
			void moveMBlocks_1 ()// 남아있는 에들  싸여있는 아이들.
			{
				//LinkedList mBlocks
				ListIterator itr =  mBlocks_1.listIterator();
				//린크드 이터레이터 형식 itr = 린크드리스트형식의 테이터를 린크드이터레이터 형식으로 바꿔주고.
				while(itr.hasNext())// 변환 시킨 자료를 가지고 while 형식으로 가지고 와서 돌린다.
				{
					Block bk = (Block) itr.next();// 가지고온 정보를 블럭형식으로 바꾼다.
					//            System.out.println(AB.blockA.color+"    색갈");
					if(!moveBlock_1(bk))//움직이는 블럭(bk )가 아니라면 
					{
						ListIterator litr = itr;

						itr.previous();
						chkColor_1((Block)itr.next());
						itr.previous();
						litr.remove();

					}
				}
			}
			////////////////////////////////////////////////////////////////////////     
			public void ProcessKey(KeyEvent e){

				int key = e.getKeyCode();
				switch(key){
				case KeyEvent.VK_LEFT:
					Control(left);
					break;
				case KeyEvent.VK_RIGHT:
					Control(right);
					break;
				case KeyEvent.VK_DOWN:
					Control(down);
					break;
				case KeyEvent.VK_UP:
					Rotate();
					break;
				case KeyEvent.VK_SPACE:
					space();///////////
					break;
				}
			}
			////////////////////////////////////////////////////////////////////////
			boolean bound_1(Block bk,int dir)  //  가드(범위) 라 불린다.. 이유는 찾아라.
			{
				switch(dir)
				{
				case left :
					return bk.pipe ==0;
				case right : 
					return bk.pipe ==5;
				case down :
				{
					return  bk.Y+20 > height;
				}

				}
				return false;
			}
			////////////////////////////////////////////////////////////////////////
			boolean moveRight(Block bk){
				if(	!pipes_1[bk.pipe+1].blocks.isEmpty())
					if(	bk.collideDown_1((Block)pipes_1[bk.pipe+1].blocks.getLast()))
						if(bk.chkLeft((Block)pipes_1[bk.pipe+1].blocks.getLast()))	  
							return false;
				bk.pipe++;
				bk.X+=2*radius;

				return true;
			}
			boolean moveLeft(Block bk){
				if(	!pipes_1[bk.pipe-1].blocks.isEmpty())
					if(	bk.collideDown_1((Block)pipes_1[bk.pipe-1].blocks.getLast()))
						if(bk.chkLeft((Block)pipes_1[bk.pipe-1].blocks.getLast()))	  
							return false;
				bk.pipe--;
				bk.X-=2*radius;
				return true;
			}
			boolean MoveBlock2(Block bk){//역기서 부터시작

				if (! pipes_1[bk.pipe].blocks.isEmpty()) //밑에 뭔가 있을때 
				{
					if(!bk.collideDown_1((Block) pipes_1[bk.pipe].blocks.getLast()))
					{
						bk.Y+=1;
						return true;
					}
					else
					{	
						bk.pipe2 = pipes_1[bk.pipe].blocks.size(); 
						pipes_1[bk.pipe].blocks.add(bk);
						return false;
					}
				}
				if(!bound_1(bk, down)){
					bk.Y+=1;
					return true;
				}
				else 
				{
					bk.pipe2 = pipes_1[bk.pipe].blocks.size();
					pipes_1[bk.pipe].blocks.addLast(bk);
					return false;
				}
			}
			public void space()
			{
				boolean ba = MoveBlock2(AB_1.blockA);
				boolean bb = MoveBlock2(AB_1.blockB);
				int limit =  height-20;
				if((!ba) && (AB_1.blockB_OR==top_or))
				{
					chkColor_1(AB_1.blockA);

					if(AB_1.blockA.color != AB_1.blockB.color)
						chkColor_1(AB_1.blockB);
					System.out.println(AB_1.blockA.X+"   2번x 좌표");
					System.out.println(AB_1.blockB.Y+"    2번 y좌표");

					firstAB_1();
				}
				else if ((!bb) && (AB_1.blockB_OR==bottom_or))
				{
					pipes_1[AB_1.blockB.pipe].blocks.add(AB_1.blockA);
					AB_1.blockA.pipe2= AB_1.blockB.pipe2+1;

					chkColor_1(AB_1.blockB);

					if(AB_1.blockA.color != AB_1.blockB.color)
						chkColor_1(AB_1.blockA);

					System.out.println(AB_1.blockA.X+"   2번x 좌표");
					System.out.println(AB_1.blockB.Y+"    2번 y좌표");
					firstAB_1();

				}
				//a는 움직이고 b는 움직이지 않을때
				else if (ba && (!bb) )
				{
					// a블러은 mBlock에 넣어둠
					mBlocks_1.addLast(AB_1.blockA);

					chkColor_1(AB_1.blockB);
					System.out.println(AB_1.blockA.X+"   2번x 좌표");
					System.out.println(AB_1.blockB.Y+"    2번 y좌표");
					firstAB_1();
				}

				else if( bb && (!ba) )
				{
					mBlocks_1.addLast(AB_1.blockB);
					chkColor_1(AB_1.blockA);
					firstAB_1();

				}
				//둘다 수평일때 움직이지 않을때
				else if (!(ba && bb))
				{
					if(AB_1.blockA.color!=AB_1.blockB.color)
						chkColor_1(AB_1.blockA);
					chkColor_1(AB_1.blockB);
					System.out.println(AB_1.blockA.X+"   2번x 좌표");
					System.out.println(AB_1.blockB.Y+"    2번 y좌표");
					firstAB_1();
				}

				if(!bound_1(AB_1.blockA, down))
				{
					AB_1.blockA.Y= limit- pipes_1[AB_1.blockA.pipe].getSize()*40 ;
					AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 
				}
				if(!bound_1(AB_1.blockB, down))
				{
					AB_1.blockA.Y= limit- pipes_1[AB_1.blockA.pipe].getSize()*40 ;
					AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 
				}
				int i = AB_1.blockB_OR;
				switch(i)
				{
				case right_or:
				{
					AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
					AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 
					break;
				}
				case top_or:
				{
					if(AB_1.blockA.pipe>=0 && AB_1.blockB.pipe >= 0)
					{
						AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
						AB_1.blockB.Y= limit - (pipes_1[AB_1.blockB.pipe].getSize()+1)*40 ; 

					}
					break;
				}
				case left_or:
				{
					AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
					AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 
					break;
				}
				case bottom_or:
				{
					if(AB_1.blockA.pipe>=0 && AB_1.blockB.pipe >= 0){

						AB_1.blockA.Y= limit - (pipes_1[AB_1.blockA.pipe].getSize()+1)*40 ;
						AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()+1*40 ; 
					}
					break;
				}
				}   
			}
			public void Control(int dir){//가드(범위)
				switch(dir)
				{
				case  down :
				{
					switch(AB_1.blockB_OR){
					case top_or:
						if(!bound_1(AB_1.blockA,down))
						{
							AB_1.blockA.Y+=20;
							AB_1.blockB.Y+=20;
						}
						break;
					default:
						if(!bound_1(AB_1.blockB,down))
						{
							AB_1.blockA.Y+=20;
							AB_1.blockB.Y+=20;
						}
						break;
					}
					break;
				}
				////////////////////////////////////////////////////////////////
				case left :
				{

					switch(AB_1.blockB_OR)
					{
					case right_or:
					{
						if(!bound_1(AB_1.blockA,left))
							if(moveLeft(AB_1.blockA))
								moveLeft(AB_1.blockB);
						break;
					}
					case top_or :
					{
						if(!bound_1(AB_1.blockA, left))
							if(moveLeft(AB_1.blockA))
								moveLeft(AB_1.blockB);
						break;
					}
					case left_or :
					{
						if(!bound_1(AB_1.blockB, left))
							if(moveLeft(AB_1.blockB))
								moveLeft(AB_1.blockA);
						break;
					}
					case bottom_or:
					{
						if(!bound_1(AB_1.blockA, left))
							if(moveLeft(AB_1.blockB))
								moveLeft(AB_1.blockA);
						break;
					}
					}
					break;

				}
				case right :
				{
					switch(AB_1.blockB_OR)
					{
					case right_or:
					{
						if(!bound_1(AB_1.blockB, right))
							if(moveRight(AB_1.blockB))
								moveRight(AB_1.blockA);
						break;
					}
					case top_or:
					{
						if(!bound_1(AB_1.blockB, right))
							if(moveRight(AB_1.blockA))
								moveRight(AB_1.blockB);
						break;
					}
					case left_or:
					{
						if(!bound_1(AB_1.blockA, right))
							if(moveRight(AB_1.blockA))
								moveRight(AB_1.blockB);
						break;

					}

					case bottom_or:
					{
						if(!bound_1(AB_1.blockB,right ))
							if(moveRight(AB_1.blockB))
								moveRight(AB_1.blockA);
						break;
					}

					}
					break;
				}
				}
			}
			public void Rotate(){//회전
				int i = AB_1.blockB_OR;
				switch(i)
				{
				case right_or:
				{
					AB_1.blockB_OR=top_or;
					AB_1.blockB.X=AB_1.blockA.X;
					AB_1.blockB.Y=AB_1.blockA.Y -40;
					AB_1.blockB.pipe = AB_1.blockA.pipe;

					break;

				}
				case top_or:
				{
					if(AB_1.blockB.pipe>0)
					{
						boolean downchk = true;//초기값 투루.
						if(!pipes_1[AB_1.blockA.pipe-1].blocks.isEmpty())
						{//회전을 했을 때 정지된 블럭을 채크해서 들어가지 못하게할것이다.
							if(AB_1.blockA.collideDown_1((Block)pipes_1[AB_1.blockA.pipe-1].blocks.getLast()))
							{
								if(AB_1.blockA.chkLeft((Block)pipes_1[AB_1.blockA.pipe-1].blocks.getLast())){
									downchk=false;
								}
							}
						}
						if(downchk){// 투루기 떄문에 회전은 가능하다.
							AB_1.blockB.Y =AB_1.blockA.Y;
							AB_1.blockB.X= AB_1.blockA.X -40;
							AB_1.blockB_OR=left_or;
							AB_1.blockB.pipe--;
						}
					}
					break;
				}
				case left_or:
				{	 //만약 우리가를 회전시켜 땅에 부딪칠 것이다.
					if(bound_1(AB_1.blockB, down))
						break;
					AB_1.blockB_OR =bottom_or;
					AB_1.blockB.Y = AB_1.blockA.Y +40;
					AB_1.blockB.X = AB_1.blockA.X;
					AB_1.blockB.pipe = AB_1.blockA.pipe;
					break;

				}
				case bottom_or:// 움직이는 물체가 밑에 있고  up을 눌럿을떄 오른쪽 위로 올라가는 상황.
				{//물체가 오른쪽에 있을떄  합쳐지지 못하게 막아주기위해서
					if(AB_1.blockB.pipe < 5)
					{
						boolean downchk = true;//초기값 투루.
						if(!pipes_1[AB_1.blockB.pipe+1].blocks.isEmpty())
						{//회전을 했을 때 정지된 블럭을 채크해서 들어가지 못하게할것이다.
							if(AB_1.blockB.collideDown_1((Block)pipes_1[AB_1.blockB.pipe+1].blocks.getLast()))
							{
								if(AB_1.blockB.chkLeft((Block)pipes_1[AB_1.blockB.pipe+1].blocks.getLast())){
									downchk=false;
								}
							}
						}
						if(downchk)//true 면 그냥 진행,
						{
							AB_1.blockB_OR =right_or;
							AB_1.blockB.Y= AB_1.blockA.Y;
							AB_1.blockB.X =AB_1.blockA.X +40;
							AB_1.blockB.pipe++;
						}
					}

					break;
				}
				}
			}
			/////////////////////////////////////////////////////////////////////
			//블록이 동일한 색이 있는 지확인 후 터트리기 위해서  밑으로
			void chkColor_1(Block bk)//블럭형식임. 1번.
			{
				LinkedList finshbk = new LinkedList<>();
				LinkedList list = new LinkedList<>();

				finshbk.add(bk);//블럭을 의 리스트의 마지막으로, 지정된 요소를 추가합니다
				bk.four= true;//  블린형태로 연쇄반응을 확인하려고. 형태로 받는다

				Iterator itr = finshbk.iterator();//이터레이터 형식 마지막의 블럭을 가지고 올것이다

				while(itr.hasNext())// 와일 문을 돌리면서  확일을 할것이다.
				{
					Object itrOB = itr.next(); //오버잭트 형식으로 변환 해서받을것이다.
					Block block =(Block) itrOB;// 블럭형식으로 다시 바꾼다. 
					LinkedList friend= friends_1(block);//밑에서  지정해준 값을 린크드 리스트로 받는다.

					for (int i = 0; i < friend.size(); i++) // 그갯수만큼  FOR문을 돌린다.
					{
						Block friend_1= (Block)friend.get(i);
						//블럭 형식인 을받는다 =     위의 정보들을 받아서 바꿔준다.
						if((!friend_1.four)&&(friend_1.color==block.color))
						{// 만약. 그형태의값이  투르가 아니고  &&  색도 같다면.
							finshbk.add(friend_1);// 그브럭의 마지막을 넣어준다.
							friend_1.four=true;// 그값은 투르 이다.
						}
					}
					list.add(finshbk.remove());//이 리스트로부터 최초의 요소를 삭제해 돌려줍니다.
					itr=finshbk.iterator();
					//	iterator ()
					//이 리스트내의 요소를(적절한 순서로) 반복 처리하는 이테레이타를 돌려줍니다.
				}
				/////////////////////////////////////
				if(list.size() >= boom)// 위에서 받은 값이   4보다 크다면. 
					for (int i = 0; i < list.size(); i++) 
					{
						Object OBJ =list.get(i);//그것에 데이터를 오브잭트로 바꾸고.
						Block block =(Block)OBJ;// 블럭형태로 형변환.
						eraseBB_1(block);// 그블럭들을 지우는 메소드로 가라.
					}
				else
					for (int i = 0; i < list.size(); i++) 
					{
						Object OB = list.get(i);
						Block block=(Block)OB;
						block.four=false;
					}

			}
			//2번
			LinkedList friends_1(Block block)//친구 주변에 뭐가 있는지 확인.
			{//린크드 리스트형식으로 값을 다시 리턴 할것이다.
				LinkedList friend = new LinkedList();
				//    	  pipe =-1;// 파이프를 기본값 -1 줫다.
				//          pipe2=-1;/
				int frpipe = block.pipe;
				int frpipe2 = block.pipe2;

				if(block.pipe>0)//2_1번//현 블럭의 가로의 길이가 0보다 클떄.
					if(pipes_1[frpipe-1].blocks.size()>frpipe2)
						//파이프 배열[현블럭의 가로-1]의 블럭들의 싸이즈가 >  현시점 세로의 파이프 길이보다 클떄.
						friend.add(pipes_1[frpipe-1].blocks.get(frpipe2));
				//        넣을것이다  .  ->  파이프 배열의[현시점가로파이프-1]들를.(현재 블럭의 세로길이로 )이 리스트내의 지정된 위치에 있는 요소를 돌려줍니다.

				///////////////////////////////////////////////////////
				if(block.pipe<numW-1 )//2_1번// 현블럭 파로의 길이가.  < 6-1 (5)
					if(pipes_1[frpipe+1].blocks.size() > frpipe2)
						//만약 파이프들의[현시점가로길이+1].블럭들의 싸이즈가  >  현시점 세로 파이프 길이보다
						friend.add(pipes_1[frpipe+1].blocks.get(frpipe2));
				////////////////////////////////////////////////////
				if(pipes_1[frpipe].blocks.size()>frpipe2+1)//2_3번.
					//파이프배열[현시점파이프 가로 길이의]블럭들의 싸이즈가 > 가로+1 보다 작을떄.
					friend.add(pipes_1[frpipe].blocks.get(frpipe2+1));
				//////////////////////////////////////////////
				if(frpipe2-1>-1)//이 리스트의 마지막으로, 지정된 요소를 추가합니다.  addLast
					friend.add(pipes_1[frpipe].blocks.get(frpipe2-1));

				return friend;
			}
			//3번
			void eraseBB_1(Block block)
			{
				for (int i = block.pipe2+1; i <pipes_1[block.pipe].blocks.size(); i++) 
				{
					Object OB = pipes_1[block.pipe].blocks.get(i);
					Block block_2 = (Block) OB;
					block_2.pipe2--;
					block_2.Y+= 2*radius;
				}
				pipes_1[block.pipe].blocks.remove(block.pipe2);
				score++;
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		class NextPanel_1 extends JPanel implements Runnable,GameParameters
		{
			int PWIDTH1 = nextWH;
			int PHEIGHT1 = nextWH;
			Thread animator1_1;// 쓰레드를 돌리기 위해서.
			boolean running1_1 = false;   // 블린형태뤄 줫다
			Game_Room topFrame1_1;// 전체 패널에서 접근을 허용 하기 위해서.
			PuyoGame1_1 nextPuyo1_1;//  불러서 사용. 하겟다/
			Graphics dbg1_1; 
			Image dbImage1_1 = null;
			public NextPanel_1() 
			{
				setBackground(Color.white);

				setPreferredSize( new Dimension(PWIDTH1, PHEIGHT1));// 페널 싸이즈 맞춰준다.
				//setBounds(20, 30, 150, 150);
				//	       setFocusable(true);// 주석 풀면 안움직인다/

				nextPuyo1_1= new PuyoGame1_1();// 새롭게 정의 해주고.     ㅁㄴㅁ    ㅁㄴㅁ
				nextPuyo1_1.startGame1_1_1();// myPuyo 안에 잇는 블럭 생성및 정보들을 스타트 하고
				startGame1_1();// 쓰레드를 시작한다.
			}
			void startGame1_1()
			{ 
				if (animator1_1 == null || !running1_1) {// 안돌고 있을떄... 말한다?
					animator1_1 = new Thread(this);
					animator1_1.start();//쓰레드를 시작한다. run  메소드 시작.
				}
			} 
			public void run()//게임의 시작 부분 스레드가 시작과동시
			{

				running1_1 = true;

				while(running1_1) {
					gameRender1_1();   // 버퍼에 게임.  게임판 그려주는에
					paintScreen1_1();  // 화면 버퍼를 그리
				}
			}
			void gameRender1_1()//  페널  그림  기려주는게  폭  넓이를 지정해줘서 그림 그려줌.
			{
				if (dbImage1_1 == null){
					dbImage1_1 = createImage(PWIDTH1, PHEIGHT1);// 가로 세로 그림// 그림이 나오는 페널.

					return;

				}

				else{
					dbg1_1 = dbImage1_1.getGraphics(); 
				}

				dbg1_1.setColor(Color.black);
				dbg1_1.fillRect (0,0, PWIDTH1, PHEIGHT1);//전체 페너얼의 위치(보이는 페널)

				nextPuyo1_1.Render1(dbg1_1);// 그림 그림?
			}  
			void paintScreen1_1() 
			{ 
				Graphics g;
				try {
					g = this.getGraphics();
					if ((g != null) && (dbImage1_1 != null))
						g.drawImage(dbImage1_1, 0, 0, null);
					g.dispose();
				}
				catch (Exception e)
				{ 

				}
			} 
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		class PuyoPanel_1_1 extends JPanel implements Runnable, GameParameters
		{	// 패널을 받고   패널에  러너블 @  그래픽 사용..
			////////////////////////////////////////////////////////////////////////
			int PWIDTH_1 = width; // 폭이 
			int PHEIGHT_1 = height; //높이
			Thread animator1_2;// 쓰레드를 돌리기 위해서.
			int stime=0;
			boolean running = false;   // 블린형태뤄 줫다
			boolean gameOver = false;//////////////////
			Game_Room topFrame1_2;// 전체 패널에서 접근을 허용 하기 위해서.
			PuyoGame1 myPuyo1_2;//  불러서 사용. 하겟다/
			int score = 0;
			Graphics dbg1_2; 
			Image dbImage1_2 = null;
			StringTokenizer st;

			///////////////////////////////////////////////////////////////////////
			public PuyoPanel_1_1()
			{
				setBackground(Color.red);// 겉에 페널색

				setPreferredSize( new Dimension(PWIDTH_1, PHEIGHT_1));// 페널 싸이즈 맞춰준다.
				//setBounds(20, 30, 150, 150);
				setFocusable(true);


				myPuyo1_2= new PuyoGame1();// 새롭게 정의 해주고.
				myPuyo1_2.StartGame_1();// myPuyo 안에 잇는 블럭 생성및 정보들을 스타트 하고//
				startGame();// 쓰레드를 시작한다.

				addKeyListener( new KeyAdapter() {

					public void keyPressed(KeyEvent e)
					{ 
						myKeyPressed(e); }
				});
			}  
			////////////////////////////////////////////////////////////////////////
			void myKeyPressed(KeyEvent e)
			{ 
				myPuyo1_2.ProcessKey(e);
			}
			////////////////////////////////////////////////////////////////////////
			void startGame()
			{ 
				if (animator1_2 == null || !running) {// 안돌고 있을떄... 말한다?
					animator1_2 = new Thread(this);
					animator1_2.start();//쓰레드를 시작한다. run  메소드 시작.
				}
			}
			////////////////////////////////////////////////////////////////////////
			public void run()//게임의 시작 부분 스레드가 시작과동시
			{
				running = true;
				int maxtime=8000;///////////
				int cnt =0;
				while(running) {
					gameUpdate(); //블럭 유동에 관한? 메소드.
					gameRender();   // 버퍼에 게임.  게임판 그려주는에
					paintScreen();  // 화면 버퍼를 그리
					cnt++;///// 쓰레드를 돌리면서 
					if(cnt%100==0)
					{
						String str ="";
						for (blockPipe bp : myPuyo1_2.pipes_1) {

							for (Object oo :  bp.blocks) {
								Block bb = (Block)oo;
								str +=bb.moveData()+"&";
							}
						}
						if(!str.equals(""))
							sd.send_msg("Coord/"+myrom+"/"+myid+"/"+str);
					}// 바뀐부분.

					if(myPuyo1_2.Update_1()){
						sd.send_msg("Gameover/"+myrom+"/"+score);
						new Pop_up("결과 기다리는중!");
						break;
					}

					if(stime == maxtime){
						sd.send_msg("Gameover/"+myrom+"/"+score);
						new Pop_up("결과 기다리는중!");
						break;
					}
					try {
						Thread.sleep(20);
						time.setText("TIME : "+ stime/100+"sec");
					} catch (Exception e) {
					}
				}
			}
			//////////////////////////////////////////////////////////
			void showMessage() {
				JOptionPane.showMessageDialog(null,"  GAME OVER   "+"\n"+" 획득 점수 "+ myPuyo1_2.score+ "점"+ "   시 간 "+stime/100 +"sec",
						"M",JOptionPane.WARNING_MESSAGE);
			}
			////////////////////////////////////////////////////////////////////////
			void gameUpdate() 
			{ 
				myPuyo1_2.Update_1();// 이거 주석 하면 움직이는 역활.
			}  
			///////////////////////////////////////////////////////////////////////
			void gameRender()//  페널  그림  기려주는게  폭  넓이를 지정해줘서 그림 그려줌.
			{
				if (dbImage1_2 == null){
					dbImage1_2 = createImage(PWIDTH_1, PHEIGHT_1);// 가로 세로 그림// 그림이 나오는 페널.
					return;
				}
				else{
					dbg1_2 = dbImage1_2.getGraphics(); 
				}
				//배경
				dbg1_2.setColor(Color.darkGray);
				dbg1_2.fillRect (0,0, PWIDTH_1, PHEIGHT_1);//전체 페너얼의 위치(보이는 페널)
				myPuyo1_2.render_1(dbg1_2);// 그림 그림?
			}  
			//    gameRender()     paintScreen()  두개가 쎄트 라한다 이유는,,,뭐라,  
			void paintScreen()  // 상황 판을  스크린에 그려주는 역활을 하는메소드.
			{ 
				Graphics g;
				try {
					g = this.getGraphics();
					if ((g != null) && (dbImage1_2 != null))
						g.drawImage(dbImage1_2, 0, 0, null);
					g.dispose();
				}
				catch (Exception e)
				{ 
				}
			} 

		}
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		class PuyoPanel_2 extends JPanel implements GameParameters
		{	// 패널을 받고   패널에  러너블 @  그래픽 사용..
			////////////////////////////////////////////////////////////////////////
			int PWIDTH = width; // 폭이 
			int PHEIGHT = height; //높이
			Image img = null;
			////////////////////////////////////////////////////////////////////////
			public void paint(Graphics g)
			{
				if(img==null)
				{
					img = createImage(500,600);
					gg = img.getGraphics();
					gg.setColor(Color.white);
					gg.fillRect(0, 0, 500, 600);
				}
				g.drawImage(img, 0, 0, this);

			}
			void test(String aa, String bb, String cc)
			{

				int a=Integer.parseInt(aa);
				int b=Integer.parseInt(bb);
				int c=Integer.parseInt(cc);
				gg.setColor(new Color(c));
				gg.fillOval(a, b, 40, 40);
				repaint();
			}
		}

	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
