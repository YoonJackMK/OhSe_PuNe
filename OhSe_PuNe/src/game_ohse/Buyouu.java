package game_ohse;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Buyouu extends JFrame {

	JPanel GameUser = new JPanel();
	JPanel GameUser2 = new JPanel();
	JPanel GameUserb = new JPanel();
	JPanel NextView = new JPanel();
	JPanel NextView2 = new JPanel();
	
	JPanel NextPoint = new JPanel();
	JPanel NextPoint2 = new JPanel();
	
	JTextArea Conversation  = new JTextArea();
	JScrollPane ConScroll = new JScrollPane(Conversation);
	//
	JButton Out = new JButton();
	JButton Ready = new JButton();
	
	
	JLabel icon = new JLabel();
	
	int x=10, y=10, sp =10;

	public Buyouu()//��ü ũ���� �г�.
	{
		super("�������̻ѳע�");
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

	
	class MyKey extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			
			//System.out.println(e.getKeyCode());
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_S:
					sp+=4;
					if(sp>50) sp =50;
					break;
				case KeyEvent.VK_D:
					sp-=4;
					if(sp<4) sp =4;
				break;
				case KeyEvent.VK_LEFT:x-=sp;break;
				case KeyEvent.VK_RIGHT:x+=sp;break;
				case KeyEvent.VK_UP:y-=sp;break;
				case KeyEvent.VK_DOWN:y+=sp;break;
			}
			
			System.out.println(icon.getX()+","+icon.getY());
			
			
			icon.setLocation(x, y);
		}
	}
	
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Buyouu(); 
//////////////////////////////////////
	}
}
/*
��ü - 20,20,920,690

A-5,20,280,600
B-620,20,280,600

�̸�����
A-305,10,130,150
B-470,10,130,150


����
A-305,170,200,60
B-400,240,200,60

��ȭ-305,310,295,250

1-305,570,130,50
2-470,570,130,50  */