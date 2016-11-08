package GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
class PwChange extends JFrame{
	JLabel pw = new JLabel("비밀번호");
	JLabel pwchk = new JLabel("비빌번호 확인");
	JTextField pwtf = new JTextField();
	JTextField pwchktf = new JTextField();
	JButton chk = new JButton("확인");
	public PwChange() {
		setTitle("비밀번호 변경");
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

class PwQnA extends JFrame{
	JLabel quiz = new JLabel("질문");
	JLabel answer = new JLabel("답");
	JTextField quiztf = new JTextField();
	JTextField answertf = new JTextField();
	JButton chk = new JButton("확인");
	public PwQnA() {
		setTitle("본인확인용QnA");
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

public class PwFindMain extends JFrame {

	JLabel name = new JLabel("ID");
	JLabel email = new JLabel("e-mail");
	JTextField nametf = new JTextField();
	JTextField emailtf = new JTextField();
	JButton chk = new JButton("확인");
	public PwFindMain() {
		setTitle("비밀번호찾기");
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
