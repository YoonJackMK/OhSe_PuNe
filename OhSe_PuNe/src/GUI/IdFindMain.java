package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
class IdFindResult extends JFrame {
	
	JLabel name = new JLabel("�̸�");
	JTextField nametf = new JTextField();
	JButton chk = new JButton("Ȯ��");
	JButton pwfind = new JButton("PWã��");
	public IdFindResult() {
		setTitle("���̵�ã����");
		setBounds(20,20,300,250);
		setLayout(null);
		name.setBounds(50,50,50,30);
		add(name);
		nametf.setBounds(100,50,150,30);
		nametf.setEditable(false);
		add(nametf);
		chk.setBounds(170, 150, 70, 30);
		add(chk);
		pwfind.setBounds(50, 150, 100, 30);
		add(pwfind);
		setVisible(true);
	}
}


public class IdFindMain extends JFrame {
	
	JLabel name = new JLabel("�̸�");
	JLabel email = new JLabel("e-mail");
	JTextField nametf = new JTextField();
	JTextField emailtf = new JTextField();
	JButton chk = new JButton("Ȯ��");
	public IdFindMain() {
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
		setVisible(true);
	}

}
