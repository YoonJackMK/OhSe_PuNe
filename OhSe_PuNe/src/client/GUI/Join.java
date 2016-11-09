package client.GUI;

import java.awt.Color;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Join extends JFrame {
	
	Calendar today = Calendar.getInstance();
	JTextField id = new JTextField("���̵� �Է�");
	JPasswordField pw = new JPasswordField();
	JPasswordField pwchk = new JPasswordField();
	JTextField name = new JTextField();
	ButtonGroup genbg = new ButtonGroup();
	JLabel num2 = new JLabel("-");
	JTextField number2 = new JTextField();
	JLabel num3 = new JLabel("-");
	JTextField number3 = new JTextField();
	JTextField emailaddress = new JTextField();
	
	public Join() {
		setBounds(101, 200, 530, 400);
		setLayout(null);
		id.setBounds(101,  10, 100, 30);
		add(id);
		pw.setBounds(101,  40, 100, 30);
		add(pw);
		pwchk.setBounds(101,  70, 100, 30);
		add(pwchk);
		name.setBounds(101,  100, 100, 30);
		add(name);
		Vector<String> numberArr = new Vector<>();
		numberArr.add("010");
		numberArr.add("011");
		numberArr.add("016");
		numberArr.add("017");
		numberArr.add("019");
		JComboBox number = new JComboBox<>(numberArr);
		number.setBounds(101,  131, 60, 30);
		add(number);
		num2.setBounds(166, 131, 10, 30);
		add(num2);
		number2.setBounds(176,  131, 60, 30);
	    add(number2);
		num3.setBounds(241, 131, 10, 30);
		add(num3);
		number3.setBounds(251,  131, 60, 30);
		add(number3);
		Vector<String> yyArr = new Vector<>();
		for (int i = 1970; i <= today.get(Calendar.YEAR) ; i++) {
			yyArr.add(i+"��");
		}
		JComboBox yy = new JComboBox<>(yyArr);
		yy.setBounds(101, 161, 70, 30);
		add(yy);
		Vector<String> mmArr = new Vector<>();
		for (int i = 1; i <= today.getActualMaximum(Calendar.MONTH)+1; i++) {
			mmArr.add(i+"��");
		}
		JComboBox mm = new JComboBox<>(mmArr);
		mm.setBounds(181, 161, 60, 30);
		add(mm);
		Vector<String> ddArr = new Vector<>();
		for (int i = 1; i <= today.getActualMaximum(Calendar.DATE) ; i++) {
			ddArr.add(i+"��");
		}
		JComboBox dd = new JComboBox<>(ddArr);
		dd.setBounds(251, 161, 60, 30);
		add(dd);
		emailaddress.setBounds(101, 191, 70, 30);
		add(emailaddress);
		JLabel mail = new JLabel("@");
		mail.setBounds(176, 191, 20, 30);
		add(mail);
		Vector<String> emailArr = new Vector<>();
		emailArr.add("daum.net");
		emailArr.add("naver.com");
		emailArr.add("google.com");
		emailArr.add("�����Է�");
		JComboBox email = new JComboBox<>(emailArr);
		email.setBounds(196, 191, 70, 30);
		add(email);
		Vector<String> quizArr = new Vector<>();
		quizArr.add("�� ������?");
		quizArr.add("�� ���� 1ȣ��?");
		quizArr.add("�� ��� �б���?");
		quizArr.add("�� ��� ������?");
		JComboBox quiz = new JComboBox(quizArr);
		quiz.setBounds(101,221,400,30);
		add(quiz);
		JTextField answer = new JTextField();
		answer.setBounds(101, 251, 400, 30);
		add(answer);
		JButton b1 = new JButton("���̵�");
		b1.setBackground(Color.gray);
		b1.setEnabled(false);
		b1.setBounds(0, 10, 100, 30);
		add(b1);
		JButton b1_1 = new JButton("�ߺ�üũ");
		b1_1.setBackground(Color.gray);
		b1_1.setBounds(202, 10, 100, 30);
		add(b1_1);
		JButton b2 = new JButton("��й�ȣ");
		b2.setBackground(Color.gray);
		b2.setEnabled(false);
		b2.setBounds(0, 40, 100, 30);
		add(b2);
		JButton b3 = new JButton("���Ȯ��");
		b3.setBackground(Color.gray);
		b3.setEnabled(false);
		b3.setBounds(0, 70, 100, 30);
		add(b3);
		JButton b4 = new JButton("�̸�");
		b4.setBackground(Color.gray);
		b4.setEnabled(false);
		b4.setBounds(0, 100, 100, 30);
		add(b4);
		JButton b5 = new JButton("��ȭ��ȣ");
		b5.setBackground(Color.gray);
		b5.setEnabled(false);
		b5.setBounds(0, 130, 100, 30);
		add(b5);
		JButton b6 = new JButton("�������");
		b6.setBackground(Color.gray);
		b6.setEnabled(false);
		b6.setBounds(0, 160, 100, 30);
		add(b6);
		JButton b7 = new JButton("�̸���");
		b7.setBackground(Color.gray);
		b7.setEnabled(false);
		b7.setBounds(0, 190, 100, 30);
		add(b7);
		JButton b8 = new JButton("����");
		b8.setBackground(Color.gray);
		b8.setEnabled(false);
		b8.setBounds(0, 220, 100, 30);
		add(b8);
		JButton b9 = new JButton("�亯");
		b9.setBackground(Color.gray);
		b9.setEnabled(false);
		b9.setBounds(0, 250, 100, 30);
		add(b9);
		
		JButton b10 = new JButton("����");
		b10.setBackground(Color.gray);
		//b11.setEnabled(false);
		b10.setBounds(170, 330, 100, 30);
		add(b10);
		JButton b11 = new JButton("���");
		b11.setBackground(Color.gray);
		//b12.setEnabled(false);
		b11.setBounds(270, 330, 100, 30);
		add(b11);
		b11.addActionListener(new Cancel(this));
		setVisible(true);
	}
}
