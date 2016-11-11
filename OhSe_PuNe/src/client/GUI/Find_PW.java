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
		chk.addActionListener(new ChkButton());
		setVisible(true);
	}
	class ChkButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			HashMap Id_Info = new UserDao().find_pwchk();
			if(!Id_Info.containsKey(nametf.getText()))
				new Pop_up("�������� �ʴ� ID�Դϴ�.");
			else{
				if(Id_Info.get(nametf.getText()).equals(emailtf.getText())){
					new PW_QnA();
					dispose();
				}

				else new Pop_up("�������� �ʴ� �̸����Դϴ�.");
			}
		}
	}
	class PW_QnA extends JFrame{
		JLabel quiz = new JLabel("����");
		JLabel answer = new JLabel("��");
		JComboBox quiztf;
		JTextField answertf = new JTextField();
		JButton chk = new JButton("Ȯ��");
		public PW_QnA() {
			setTitle("����Ȯ�ο�QnA");
			setBounds(20,20,300,250);
			setLayout(null);
			quiz.setBounds(40,50,50,30);
			add(quiz);
			answer.setBounds(40,100,50,30);
			add(answer);
			Vector<String> quizArr = new Vector<>();
			quizArr.add("�� ������?");
			quizArr.add("�� ���� 1ȣ��?");
			quizArr.add("�� ��� �б���?");
			quizArr.add("�� ��� ������?");
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
					new Pop_up("������ �ùٸ��� �ʽ��ϴ�.");
				else{
					if(Quiz_Info.get(quiztf.getSelectedItem()).equals(answertf.getText())){
						new PW_Change();
						dispose();
					}
				  else new Pop_up("�亯�� �ùٸ��� �ʽ��ϴ�.");
				}
			}
		}
	}
	class PW_Change extends JFrame {
		JLabel pw = new JLabel("��й�ȣ");
		JLabel pwchk = new JLabel("�����ȣ Ȯ��");
		JPasswordField pwtf = new JPasswordField();
		JPasswordField pwchktf = new JPasswordField();
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
			chk.addActionListener(new ChkButton_3());
			setVisible(true);
		}
		class ChkButton_3 implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				if(pwtf.getText().equals(pwchktf.getText())){
				new UserDao().Change_pw(nametf.getText(), pwtf.getText());
				new Pop_up("��й�ȣ ����Ϸ�");
				dispose();
				}
				else new Pop_up("PW�� PWȮ���� ��ġ���� �ʽ��ϴ�.");
			}
		}
	}
}
