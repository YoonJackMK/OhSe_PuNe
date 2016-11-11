package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import server.model.UserDao;

public class Find_ID extends JFrame {
	
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
		chk.addActionListener(new ChkButton());
		setVisible(true);
	}
	class ChkButton implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			HashMap Id_Info = new UserDao().find_idchk();
			if(!Id_Info.containsKey(nametf.getText()))
				new Pop_up("�������� �ʴ� �̸��Դϴ�.");
			else{
			if(Id_Info.get(nametf.getText()).equals(emailtf.getText())){
			   new Pop_up(new UserDao().Result_findid(emailtf.getText()));
			   dispose();
			}
			else new Pop_up("�������� �ʴ� �̸����Դϴ�.");
			}
		}
	}

}
