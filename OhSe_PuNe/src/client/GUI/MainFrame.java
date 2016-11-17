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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import javax.swing.JTextField;

import server.ServerJK;
import server.model.UserDao;
import server.model.UserDto;

public class MainFrame extends JFrame implements ActionListener{

	CardLayout card = new CardLayout();
	Lobby lb = new Lobby();
	Login lg = new Login();
	
	//Game_Room gb = new Game_Room();
	JPanel p1 = lb.lobby;
	JPanel p2 = lg.login;
	//JPanel p3 = gb.GameRoom;
	JButton login_btn = new JButton("Login");
	JButton send = new JButton("����");
	JButton whisper = new JButton("�Ӹ�");
	JButton crRom = new JButton("�游���");
	JButton fiRom = new JButton("��ã��");
	JButton joRom = new JButton("������");
	JButton Start = new JButton("����");
	JButton OutRom = new JButton("������");
	JButton join_btn = new JButton("Join");

	JButton find_ID_btn = new JButton("Find ID");
	JButton find_PW_btn = new JButton("Find PW");
	
	Find_PW pw;
	PW_QnA qna;
	PW_Change change;

	//net res
	Socket socket;
	InputStream is;
	OutputStream os;
	DataInputStream dis;
	DataOutputStream dos;
	boolean res = false;
	Vector userlist = new Vector<>();
	Vector roomlist = new Vector<>();
	StringTokenizer st;

	ArrayList userinfo;//���������� ������ �ִ� ����Ʈ
	String myrom;//�� ���� ��

	public MainFrame() {

		connect();
		setTitle("�����̻ѳ�:�״뿡�� ��ġ�� ��������");
		setLayout(card);
		setBounds(10,20, 920, 690);
		add(p1,"�κ�");
		add(p2,"�α���");
		//add(p3,"���ӹ�");
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
		OutRom.setBounds(305,570,130,50);
		OutRom.addActionListener(this);
		Start.setBounds(445,570,130,50);
		Start.addActionListener(this);
		//p3.add(OutRom);
		//p3.add(Start);
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
		card.show(getContentPane(), "�α���");
		lb.room.setListData(roomlist);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	void connect(){
		try 
		{
			socket = new Socket("192.168.30.135", 7777);
			is=socket.getInputStream();
			dis=new DataInputStream(is);
			os=socket.getOutputStream();
			dos=new DataOutputStream(os);
		}
		catch (UnknownHostException e)
		{
			new Pop_up("�������");
		}
		catch (IOException e) 
		{
			new Pop_up("�������");
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
							new Pop_up("�������");
							dispose();
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
			lb.chatview.append(msg+"�κ��� �ӼӸ�:"+MMsg+"\n");
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
			card.show(getContentPane(), "���ӹ�");
		}
		else if(protocol.equals("CreateRoomFail"))
		{
			new Pop_up("�̹� ���� ����");
		}
		else if(protocol.equals("FindRoomFail"))
		{
			new Pop_up("���� ����!");
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
		}
		else if(protocol.equals("JoinRoom"))
		{
			myrom = msg;
			card.show(getContentPane(), "���ӹ�");
		}
		else if(protocol.equals("HidenRoom"))
		{
			String pw = JOptionPane.showInputDialog("��й�ȣ");
			String name = st.nextToken();
			if(pw.equals(msg))
				send_msg("HidenRoom/"+name);
			else new Pop_up("��й�ȣ�� ���� �ʽ��ϴ�.");
		}
		else if(protocol.equals("UserOut"))
		{
			userlist.remove(msg);
		}
		else if(protocol.equals("OutRoom"))
		{
			card.show(getContentPane(),"�κ�");
		}
		else if(protocol.equals("RemoveRoom"))
		{
			roomlist.remove(msg);
		}
		else if(protocol.equals("full"))
		{
			new Pop_up("���ο� �ʰ�");
		}
		else if(protocol.equals("login"))
		{
			card.show(getContentPane(), "�κ�");
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
			new Pop_up("��й�ȣ ����");
		}
		else if(protocol.equals("print"))
		{
			st = new StringTokenizer(msg,"&,");
			
			while(st.hasMoreElements())
			{
				System.out.println(st.nextToken()+","+st.nextToken()+","+st.nextToken());
			}
		}
		else if(protocol.equals("Fail"))
		{
			if(msg.equals("idexist")) new Pop_up("�������� ���̵�");
			else if(msg.equals("idwrong")) new Pop_up("ID�� Ʋ�Ƚ��ϴ�");
			else if(msg.equals("pwwrong")) new Pop_up("��й�ȣ�� Ʋ�Ƚ��ϴ�");
			else if(msg.equals("mail")) new Pop_up("�������� �ʴ� ����");
			else if(msg.equals("noname")) new Pop_up("�������� �ʴ� �̸�");
			else if(msg.equals("qna")) new Pop_up("����/�亯�� Ȯ���ϼ���");
			else if(msg.equals("chk")) new Pop_up("��й�ȣ�� Ȯ���ϼ���");
			else if(msg.equals("ok")) new Pop_up("���̵� ��밡��");
			else if(msg.equals("notok")) new Pop_up("������� ���̵�");
			else if(msg.equals("complete")) new Pop_up("ȸ������ �Ϸ�");
			else if(msg.equals("mailnotok")) new Pop_up("������� �̸���");
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
			send_msg("Login/"+lg.id_txt.getText()+"/"+lg.pw_txt.getText());
		}
		else if(e.getSource()==send)
		{
			if(lb.chat.getText().equals(null)||lb.chat.getText().equals("")||lb.chat.getText().equals("/")){
				new Pop_up("������ �Է��ϼ���");
			}
			else send_msg("LobbyChat/"+lg.id_txt.getText().trim()+"/"+lb.chat.getText().trim());
			lb.chat.setText("");
			lb.chJS.getVerticalScrollBar().setValue(lb.chJS.getVerticalScrollBar().getMaximum());
		}
		else if(e.getSource()==lb.chat)
		{
			if(lb.chat.getText().equals(null)||lb.chat.getText().equals("")||lb.chat.getText().equals("/")){
				new Pop_up("������ �Է��ϼ���");
			}
			else send_msg("LobbyChat/"+lg.id_txt.getText().trim()+"/"+lb.chat.getText().trim());
			lb.chat.setText("");
			lb.chJS.getVerticalScrollBar().setValue(lb.chJS.getVerticalScrollBar().getMaximum());
		}
		else if(e.getSource()==whisper)
		{
			String user = (String)lb.user.getSelectedValue();
			String note = JOptionPane.showInputDialog("�����޼���");

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
	/*	else if(e.getSource()==Start){
			gb.game();
		}*/
		else if(e.getSource()==OutRom)
		{
			send_msg("OutRoom/"+myrom);
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
			JLabel year = new JLabel("��");
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
			JLabel month = new JLabel("��");
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
			JLabel date = new JLabel("��");
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
			emailArr.add("�����Է�");
			email = new JComboBox<>(emailArr);
			email.setBounds(196, 191, 70, 30);
			add(email);
			emailAddress2.setBounds(276, 191, 70, 30);
			add(emailAddress2);
			Vector<String> quizArr = new Vector<>();
			quizArr.add("�� ������?");
			quizArr.add("�� ���� 1ȣ��?");
			quizArr.add("�� ��� �б���?");
			quizArr.add("�� ��� ������?");
			quiz = new JComboBox(quizArr);
			quiz.setBounds(101,221,400,30);
			add(quiz);
			Answer.setBounds(101, 251, 400, 30);
			add(Answer);
			JButton b1 = new JButton("���̵�");
			b1.setBackground(Color.gray);
			b1.setEnabled(false);
			b1.setBounds(0, 10, 100, 30);
			add(b1);
			b1_1 = new JButton("�ߺ�üũ");
			b1_1.setBackground(Color.gray);
			b1_1.setBounds(202, 10, 100, 30);
			add(b1_1);
			b1_1.addActionListener(this);
			JButton b2 = new JButton("��й�ȣ");
			b2.setBackground(Color.gray);
			b2.setEnabled(false);
			b2.setBounds(0, 40, 100, 30);
			add(b2);
			JButton b3 = new JButton("���Ȯ��");
			b3.setBackground(Color.gray);
			b3.setEnabled(false);
			b3.setBounds(0, 70, 100, 30);
			add(b3);
			JButton b4 = new JButton("�̸�");
			b4.setBackground(Color.gray);
			b4.setEnabled(false);
			b4.setBounds(0, 100, 100, 30);
			add(b4);
			JButton b5 = new JButton("��ȭ��ȣ");
			b5.setBackground(Color.gray);
			b5.setEnabled(false);
			b5.setBounds(0, 130, 100, 30);
			add(b5);
			JButton b6 = new JButton("�������");
			b6.setBackground(Color.gray);
			b6.setEnabled(false);
			b6.setBounds(0, 160, 100, 30);
			add(b6);
			JButton b7 = new JButton("�̸���");
			b7.setBackground(Color.gray);
			b7.setEnabled(false);
			b7.setBounds(0, 190, 100, 30);
			add(b7);
			JButton b8 = new JButton("����");
			b8.setBackground(Color.gray);
			b8.setEnabled(false);
			b8.setBounds(0, 220, 100, 30);
			add(b8);
			JButton b9 = new JButton("�亯");
			b9.setBackground(Color.gray);
			b9.setEnabled(false);
			b9.setBounds(0, 250, 100, 30);
			add(b9);
			b10 = new JButton("����");
			b10.setBackground(Color.gray);
			b10.setBounds(170, 300, 100, 30);
			add(b10);
			b10.addActionListener(this);
			JButton b11 = new JButton("���");
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
				String mail = null;
				if(email.getSelectedItem().equals("�����Է�"))
					mail=emailAddress.getText()+"@"+emailAddress2.getText();
				else
					mail=emailAddress.getText()+"@"+email.getSelectedItem();

				if(id.getText().equals("")) new Pop_up("ID�� Ȯ���ϼ���.");
				else if(pw.getText().equals("")) new Pop_up("PW�� �Է��ϼ���.");
				else if(pwchk.getText().equals("")) new Pop_up("PWȮ���� �Է��ϼ���.");
				else if(name.getText().equals("")) new Pop_up("�̸��� Ȯ���ϼ���.");
				else if(emailAddress.getText().equals("")) new Pop_up("�̸����� Ȯ�����ּ���.");
				else if(Answer.getText().equals("")) new Pop_up("������ �亯�� Ȯ���ϼ���.");
				else if(pw.getText().equals(pwchk.getText()))
				{						
					send_msg("Join/"+id.getText()+
							"/"+pw.getText()+"/"+
							name.getText()+"/"+
							number.getSelectedItem()+"-"+number2.getText()+"-"+number3.getText()+"/"+
							yy.getSelectedItem()+"-"+mm.getSelectedItem()+"-"+dd.getSelectedItem()+"/"+
							quiz.getSelectedItem()+"/"+
							Answer.getText()+"/"+
							mail);
				}
				else new Pop_up("PW�� PWȮ�� ��ġ���� �ʽ��ϴ�.");


			}
			if(e.getSource()==b1_1) 
			{
				if(id.getText().equals("")) new Pop_up("���̵� �Է��ϼ���");
				else
				{
					send_msg("IDchk/"+id.getText());
				}

			}
		}
	}
	class Room_Create extends JFrame implements ActionListener
	{
		JLabel title = new JLabel("�� �� ��");
		JTextField tiTF = new JTextField();
		JCheckBox hide = new JCheckBox("�������");
		JPasswordField hiTF= new JPasswordField();
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
					new Pop_up("���̸��� �Է��ϼ���");
				else{
					if(hide.isSelected())
					{
						if(hiTF.getText().equals(""))
							new Pop_up("��й�ȣ�� �Է��ϼ���");
						else
						{ 	
							send_msg("CreateHideRoom/"+tiTF.getText()+"/"+hiTF.getText());
							dispose();
						}
					}
					else 
					{
						send_msg("CreateRoom/"+tiTF.getText());
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
		JLabel roomNum = new JLabel("���̸� ");
		JTextField rnTF = new JTextField();
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
			chk.addActionListener(this);
			setVisible(true);
		}
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==chk){
				send_msg("FindRoom/"+rnTF.getText());
				dispose();
			}
		}
		
	}
	class Find_ID extends JFrame implements ActionListener {


		JLabel name = new JLabel("�̸�");
		JLabel email = new JLabel("e-mail");
		JTextField nametf = new JTextField();
		JTextField emailtf = new JTextField();
		JButton chk = new JButton("Ȯ��");
		
		public Find_ID() {
			setTitle("���̵�ã��");
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
			
			send_msg("Findid/"+nametf.getText()+"/"+emailtf.getText());
		}
	}
	class PW_QnA extends JFrame implements ActionListener{
		JLabel quiz = new JLabel("����");
		JLabel answer = new JLabel("��");
		JComboBox quiztf;
		JTextField answertf = new JTextField();
		JButton chk = new JButton("Ȯ��");
		
		public PW_QnA() {
			setTitle("����Ȯ�ο�QnA");
			setBounds(20,20,300,250);
			setLayout(null);
			quiz.setBounds(40,50,50,30);
			add(quiz);
			answer.setBounds(40,100,50,30);
			add(answer);
			Vector<String> quizArr = new Vector<>();
			quizArr.add("�� ������?");
			quizArr.add("�� ���� 1ȣ��?");
			quizArr.add("�� ��� �б���?");
			quizArr.add("�� ��� ������?");
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
			
			send_msg("PWQnA/"+((String)quiztf.getSelectedItem())+"/"+answertf.getText());
		}
	}
	class PW_Change extends JFrame implements ActionListener {
		JLabel id = new JLabel("���̵�");
		JLabel pw = new JLabel("��й�ȣ");
		JLabel pwchk = new JLabel("�����ȣ Ȯ��");
		JTextField idtf = new JTextField();
		JPasswordField pwtf = new JPasswordField();
		JPasswordField pwchktf = new JPasswordField();
		JButton chk = new JButton("Ȯ��");
		public PW_Change() {
			setTitle("��й�ȣ ����");
			setBounds(20,20,300,250);
			setLayout(null);
			id.setBounds(30,20,100,30);
			add(id);
			pw.setBounds(30,60,100,30);
			add(pw);
			pwchk.setBounds(30,100,100,30);
			add(pwchk);
			idtf.setBounds(120,20,150,30);
			add(idtf);
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
			send_msg("PWchange/"+idtf.getText()+"/"+pwtf.getText()+"/"+pwchktf.getText());
		
		}
	}
	class Find_PW extends JFrame implements ActionListener {

		JLabel name = new JLabel("ID");
		JLabel email = new JLabel("e-mail");
		JTextField nametf = new JTextField();
		JTextField emailtf = new JTextField();
		JButton chk = new JButton("Ȯ��");
		public Find_PW() {
			setTitle("��й�ȣã��");
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
			send_msg("FindPW/"+nametf.getText()+"/"+emailtf.getText());
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
