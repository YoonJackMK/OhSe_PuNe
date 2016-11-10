package client.GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import client.Client;
import server.model.UserDao;



public class MainFrame extends JFrame{

	CardLayout card = new CardLayout();
	Lobby lb = new Lobby();
	Login lg = new Login();
	JPanel p1 = lb.lobby;
	JPanel p2 = lg.login;
	JButton login_btn = new JButton("Login");
	public MainFrame() {
		// TODO Auto-generated constructor stub
		setTitle("�����̻ѳ�:�״뿡�� ��ġ�� ��������");
		setLayout(card);
		setBounds(10,20, 920, 690);
		add(p1,"�κ�");
		add(p2,"�α���");
		login_btn.setBounds(500, 500, 100, 40);
		login_btn.setBackground(Color.GRAY);
		p2.add(login_btn);
		login_btn.addActionListener(new Login_Chk());
		

		card.show(getContentPane(), "�α���");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	class Login_Chk implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

            HashMap login_info= new UserDao().login_chk();	
			
            if(login_info.get(lg.id_txt.getText()).equals(lg.pw_txt.getText()))
            {
            	card.show(getContentPane(), "�κ�");
    		    new ServerAccess();
            }
            else System.out.println("�ٽôٽ�");
			
		}
	}
	public static void main(String[] args) {
		new MainFrame();
	}
}
