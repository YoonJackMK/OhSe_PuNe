package client.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Game_Room extends JFrame {

	public JPanel GameUser = new JPanel();
	public JPanel GameUser2 = new JPanel();
	
	public JPanel NextView = new JPanel();
	public JPanel NextView2 = new JPanel();
	
	public JPanel NextPoint = new JPanel();
	public JPanel NextPoint2 = new JPanel();
	
	public JTextArea Conversation  = new JTextArea();
	public JScrollPane ConScroll = new JScrollPane(Conversation);
	//
	public JButton Out = new JButton("나가기");
	public JButton Ready = new JButton("준비");
	public JPanel GameRoom = new JPanel();
	
	public Game_Room()//전체 크기의 패널.
	{
		GameRoom.setBounds(20, 20, 920, 690);
		GameRoom.setLayout(null);
		/////게임창 유저1
		GameUser.setBounds(5,20,280,600);
		GameUser.setBackground(Color.white);
		//게임창 유저 2
		GameUser2.setBounds(620,20,280,600);
		GameUser2.setBackground(Color.white);
		//유저1 다음 나올 도형 
		NextView.setBounds(305,10,130,150);
		NextView.setBackground(Color.white);
		////유저2 다음 나올 도형 
		NextView2.setBounds(470,10,130,150);
		NextView2.setBackground(Color.white);
		//유저1 점수
		NextPoint.setBounds(305,170,200,60);
		NextPoint.setBackground(Color.white);
		//유저2 점수
		NextPoint2.setBounds(400,240,200,60);
		NextPoint2.setBackground(Color.white);
		//////////////////////////////////////
		//대화
		ConScroll.setBounds(305,310,295,250);
		////////////////////////////////////
		//버튼
		Ready.setBounds(470,570,130,50);
		
		GameRoom.add(GameUser);
		GameRoom.add(GameUser2);
		GameRoom.add(NextView);
		GameRoom.add(NextView2);
		GameRoom.add(NextPoint);
		GameRoom.add(NextPoint2);
		GameRoom.add(ConScroll);
		
		GameRoom.add(Ready);
		
	}
	
}