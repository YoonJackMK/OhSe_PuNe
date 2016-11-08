package GUI;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

class CreateRoom extends JFrame
{
	JLabel title = new JLabel("방 제 목");
	JTextField tiTF = new JTextField("방제목을 입력하세요");
	JCheckBox hide = new JCheckBox("비공개방");
	JTextField hiTF= new JTextField();
	JButton create = new JButton("만들기");
	JButton cancel = new JButton("취소");

	public CreateRoom() {
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
class FindRoom extends JFrame
{
	JLabel roomNum = new JLabel("방번호 ");
	JTextField rnTF = new JTextField("방번호를 입력하세요");
	JButton chk = new JButton("입장");
	public FindRoom() {
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
public class LobbyMain extends JFrame
{
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


	public LobbyMain() {
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
	
}
