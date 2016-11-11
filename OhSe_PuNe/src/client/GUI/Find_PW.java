package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import server.model.UserDao;

public class Find_PW extends JFrame {

	JLabel name = new JLabel("ID");
	JLabel email = new JLabel("e-mail");
	JTextField nametf = new JTextField();
	JTextField emailtf = new JTextField();
	JButton chk = new JButton("확인");
	public Find_PW() {
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
		chk.addActionListener(new ChkButton());
		setVisible(true);
	}
	class ChkButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			HashMap Id_Info = new UserDao().find_pwchk();
			if(!Id_Info.containsKey(nametf.getText()))
				new Pop_up("존재하지 않는 ID입니다.");
			else{
				if(Id_Info.get(nametf.getText()).equals(emailtf.getText())){
					new PW_QnA();
					dispose();
				}

				else new Pop_up("존재하지 않는 이메일입니다.");
			}
		}
	}
	class PW_QnA extends JFrame{
		JLabel quiz = new JLabel("질문");
		JLabel answer = new JLabel("답");
		JComboBox quiztf;
		JTextField answertf = new JTextField();
		JButton chk = new JButton("확인");
		public PW_QnA() {
			setTitle("본인확인용QnA");
			setBounds(20,20,300,250);
			setLayout(null);
			quiz.setBounds(40,50,50,30);
			add(quiz);
			answer.setBounds(40,100,50,30);
			add(answer);
			Vector<String> quizArr = new Vector<>();
			quizArr.add("내 고향은?");
			quizArr.add("내 보물 1호는?");
			quizArr.add("내 출신 학교는?");
			quizArr.add("내 어릴적 별명은?");
			quiztf = new JComboBox(quizArr);
			quiztf.setBounds(90,50,150,30);
			add(quiztf);
			answertf.setBounds(90,100,150,30);
			add(answertf);
			chk.setBounds(170, 150, 70, 30);
			add(chk);
			chk.addActionListener(new ChkButton_2());
			setVisible(true);
		}
		class ChkButton_2 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				HashMap Quiz_Info = new UserDao().Pw_QnA(nametf.getText());
				if(!Quiz_Info.containsKey(quiztf.getSelectedItem()))
					new Pop_up("질문이 올바르지 않습니다.");
				else{
					if(Quiz_Info.get(quiztf.getSelectedItem()).equals(answertf.getText())){
						new PW_Change();
						dispose();
					}
				  else new Pop_up("답변이 올바르지 않습니다.");
				}
			}
		}
	}
	class PW_Change extends JFrame {
		JLabel pw = new JLabel("비밀번호");
		JLabel pwchk = new JLabel("비빌번호 확인");
		JPasswordField pwtf = new JPasswordField();
		JPasswordField pwchktf = new JPasswordField();
		JButton chk = new JButton("확인");
		public PW_Change() {
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
			chk.addActionListener(new ChkButton_3());
			setVisible(true);
		}
		class ChkButton_3 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(pwtf.getText().equals(pwchktf.getText())){
				new UserDao().Change_pw(nametf.getText(), pwtf.getText());
				new Pop_up("비밀번호 변경완료");
				dispose();
				}
				else new Pop_up("PW와 PW확인이 일치하지 않습니다.");
			}
		}
	}
}
