package client.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

class PW_Change extends JFrame {
	JLabel pw = new JLabel("��й�ȣ");
	JLabel pwchk = new JLabel("�����ȣ Ȯ��");
	JTextField pwtf = new JTextField();
	JTextField pwchktf = new JTextField();
	JButton chk = new JButton("Ȯ��");
	public PW_Change() {
		setTitle("��й�ȣ ����");
		setBounds(20,20,300,250);
		setLayout(null);
		pw.setBounds(30,50,100,30);
		add(pw);
		pwchk.setBounds(30,100,100,30);
		add(pwchk);
		pwtf.setBounds(120,50,150,30);
		add(pwtf);
		pwchktf.setBounds(120,100,150,30);
		add(pwchktf);
		chk.setBounds(170, 150, 70, 30);
		add(chk);
		setVisible(true);
	}
	
}

class PW_QnA extends JFrame{
	JLabel quiz = new JLabel("����");
	JLabel answer = new JLabel("��");
	JTextField quiztf = new JTextField();
	JTextField answertf = new JTextField();
	JButton chk = new JButton("Ȯ��");
	public PW_QnA() {
		setTitle("����Ȯ�ο�QnA");
		setBounds(20,20,300,250);
		setLayout(null);
		quiz.setBounds(50,50,50,30);
		add(quiz);
		answer.setBounds(50,100,50,30);
		add(answer);
		quiztf.setBounds(100,50,150,30);
		add(quiztf);
		answertf.setBounds(100,100,150,30);
		add(answertf);
		chk.setBounds(170, 150, 70, 30);
		add(chk);
		setVisible(true);
	}
	
}

public class Find_PW extends JFrame {

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
		setVisible(true);
	}
}
