package client.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class Find_ID_Result extends JFrame {
	
	JLabel name = new JLabel("이름");
	JTextField nametf = new JTextField();
	JButton chk = new JButton("확인");
	
	
	public Find_ID_Result() {
		setTitle("아이디찾기결과");
		setBounds(20,20,300,250);
		setLayout(null);
		name.setBounds(50,50,50,30);
		add(name);
		nametf.setBounds(100,50,150,30);
		nametf.setEditable(false);
		add(nametf);
		chk.setBounds(170, 150, 70, 30);
		add(chk);
		chk.addActionListener(new Cancel(this));
		setVisible(true);
	}
}


public class Find_ID extends JFrame {
	
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
		setVisible(true);
	}

}
