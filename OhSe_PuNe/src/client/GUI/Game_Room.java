package client.GUI;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Game_Room extends JFrame {

	JPanel GameUser = new JPanel();
	JPanel GameUser2 = new JPanel();
	
	JPanel NextView = new JPanel();
	JPanel NextView2 = new JPanel();
	
	JPanel NextPoint = new JPanel();
	JPanel NextPoint2 = new JPanel();
	
	JTextArea Conversation  = new JTextArea();
	JScrollPane ConScroll = new JScrollPane(Conversation);
	//
	JButton Out = new JButton();
	JButton Ready = new JButton();
	
	public Game_Room()//��ü ũ���� �г�.
	{
		super("�����̻ѳ�");
		setBounds(20, 20, 920, 690);
		setLayout(null);

		/////����â ����1
		GameUser.setBounds(5,20,280,600);
		GameUser.setBackground(Color.white);
		//����â ���� 2
		GameUser2.setBounds(620,20,280,600);
		GameUser2.setBackground(Color.white);
		//����1 ���� ���� ���� 
		NextView.setBounds(305,10,130,150);
		NextView.setBackground(Color.white);
		////����2 ���� ���� ���� 
		NextView2.setBounds(470,10,130,150);
		NextView2.setBackground(Color.white);
		//����1 ����
		NextPoint.setBounds(305,170,200,60);
		NextPoint.setBackground(Color.white);
		//����2 ����
		NextPoint2.setBounds(400,240,200,60);
		NextPoint2.setBackground(Color.white);
		//////////////////////////////////////
		//��ȭ
		ConScroll.setBounds(305,310,295,250);
		////////////////////////////////////
		//��ư
		Out.setBounds(305,570,130,50);
		Ready.setBounds(470,570,130,50);
		
		
		add(GameUser);
		add(GameUser2);
		add(NextView);
		add(NextView2);
		add(NextPoint);
		add(NextPoint2);
		add(ConScroll);
		add(Out);
		add(Ready);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	////////////////////////////////////////////////////

	
	
	
	public static void main(String[] args) {
		new Game_Room(); 
//////////////////////////////////////
	}
}