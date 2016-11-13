package client.GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import server.model.UserDao;
import server.model.UserDto;

class Pop_up extends JFrame {
	
	public Pop_up(String msg) {
		setBounds(100, 200, 300, 150);
		setLayout(null);
		JLabel notice = new JLabel(msg,SwingConstants.CENTER);
		notice.setBounds(0,20,300,40);
		add(notice);
		JButton chk = new JButton("Ȯ��");
		chk.setBounds(110,70,70,30);
		
		add(chk);
		chk.addActionListener(new Cancel(this));
		setVisible(true);
	}
}
public class Join extends JFrame {
	
	Calendar today = Calendar.getInstance();
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();
	JPasswordField pwchk = new JPasswordField();
	JTextField name = new JTextField();
	JLabel num2 = new JLabel("-");
	JTextField number2 = new JTextField();
	JLabel num3 = new JLabel("-");
	JTextField number3 = new JTextField();
	JTextField emailAddress = new JTextField();
	JTextField emailAddress2 = new JTextField();
	JComboBox number;
	JComboBox email;
	JComboBox yy;
	JComboBox mm; 
	JComboBox dd; 
	JComboBox quiz;
	JTextField Answer = new JTextField();
	String str = "�ȵƾ�̤�";
	public Join(){
		setTitle("Join SeyoungPuNE");
		setBounds(101, 200, 530, 380);
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
		number = new JComboBox<>(numberArr);
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
			yyArr.add(""+i);
		}
		yy = new JComboBox<>(yyArr);
		yy.setBounds(101, 161, 60, 30);
		add(yy);
		JLabel year = new JLabel("��");
		year.setBounds(166, 161, 20, 30);
		add(year);
		Vector<String> mmArr = new Vector<>();
		for (int i = 1; i <= today.getActualMaximum(Calendar.MONTH)+1; i++) {
			if(i<10) mmArr.add("0"+i);
			else mmArr.add(""+i);
		}
		mm = new JComboBox<>(mmArr);
		mm.setBounds(181, 161, 50, 30);
		add(mm);
		JLabel month = new JLabel("��");
		month.setBounds(236, 161, 20, 30);
		add(month);
		Vector<String> ddArr = new Vector<>();
		for (int i = 1; i <= today.getActualMaximum(Calendar.DATE) ; i++) {
			if(i<10) ddArr.add("0"+i);
			else ddArr.add(""+i);
		}
		dd = new JComboBox<>(ddArr);
		dd.setBounds(251, 161, 50, 30);
		add(dd);
		JLabel date = new JLabel("��");
		date.setBounds(306, 161, 20, 30);
		add(date);
		emailAddress.setBounds(101, 191, 70, 30);
		add(emailAddress);
		JLabel mail = new JLabel("@");
		mail.setBounds(176, 191, 20, 30);
		add(mail);
		Vector<String> emailArr = new Vector<>();
		emailArr.add("daum.net");
		emailArr.add("naver.com");
		emailArr.add("google.com");
		emailArr.add("�����Է�");
		email = new JComboBox<>(emailArr);
		email.setBounds(196, 191, 70, 30);
		add(email);
		emailAddress2.setBounds(276, 191, 70, 30);
		add(emailAddress2);
		Vector<String> quizArr = new Vector<>();
		quizArr.add("�� ������?");
		quizArr.add("�� ���� 1ȣ��?");
		quizArr.add("�� ��� �б���?");
		quizArr.add("�� ��� ������?");
		quiz = new JComboBox(quizArr);
		quiz.setBounds(101,221,400,30);
		add(quiz);
		Answer.setBounds(101, 251, 400, 30);
		add(Answer);
		JButton b1 = new JButton("���̵�");
		b1.setBackground(Color.gray);
		b1.setEnabled(false);
		b1.setBounds(0, 10, 100, 30);
		add(b1);
		JButton b1_1 = new JButton("�ߺ�üũ");
		b1_1.setBackground(Color.gray);
		b1_1.setBounds(202, 10, 100, 30);
		add(b1_1);
		b1_1.addActionListener(new IdChkButton());
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
		b10.setBounds(170, 300, 100, 30);
		add(b10);
		b10.addActionListener(new ChkButton());
		JButton b11 = new JButton("���");
		b11.setBackground(Color.gray);
		b11.setBounds(270, 300, 100, 30);
		add(b11);
		
		b11.addActionListener(new Cancel(this));
		setVisible(true);
	}
	class ChkButton  implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			UserDto dto = new UserDto();
			ArrayList list = new UserDao().id_chk();
			ArrayList list2 = new UserDao().mail_chk();
			if(list.contains(id.getText()))
				new Pop_up("������� ���̵��Դϴ�.");
			else if(list2.contains(emailAddress.getText()+"@"+email.getSelectedItem())||
					list2.contains(emailAddress.getText()+"@"+emailAddress2.getText()))
				new Pop_up("�̹� ������� �̸����Դϴ�.");
			else {
			if(id.getText().equals("")) new Pop_up("ID�� Ȯ���ϼ���.");
			else if(pw.getText().equals("")) new Pop_up("PW�� �Է��ϼ���.");
			else if(pwchk.getText().equals("")) new Pop_up("PWȮ���� �Է��ϼ���.");
			else if(name.getText().equals("")) new Pop_up("�̸��� Ȯ���ϼ���.");
		    else if(emailAddress.getText().equals("")) new Pop_up("�̸����� Ȯ�����ּ���.");
			else if(Answer.getText().equals("")) new Pop_up("������ �亯�� Ȯ���ϼ���.");
			else if(pw.getText().equals(pwchk.getText()))
			{
			dto.setId(id.getText());
			dto.setPw(pw.getText());
			dto.setName(name.getText());
			dto.setTel(number.getSelectedItem()+"-"+number2.getText()+"-"+number3.getText());
			dto.setBirthStr(yy.getSelectedItem()+"-"+mm.getSelectedItem()+"-"+dd.getSelectedItem());
			dto.setPw_q((String)quiz.getSelectedItem());
			dto.setPw_a(Answer.getText());
			if(email.getSelectedItem().equals("�����Է�"))
				dto.setEmail(emailAddress.getText()+"@"+emailAddress2.getText());
			else dto.setEmail(emailAddress.getText()+"@"+email.getSelectedItem());
			new UserDao().insert(dto);
			dispose();
			}
			else new Pop_up("PW�� PWȮ�� ��ġ���� �ʽ��ϴ�.");
			}
		}
	}
	class IdChkButton  implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			ArrayList list = new UserDao().id_chk();
			if(list.contains(id.getText()))
				new Pop_up("������� ���̵��Դϴ�.");
			else if(id.getText().equals("")) new Pop_up("���̵� �Է��ϼ���");
			else new Pop_up("����ϼŵ� �˴ϴ�.");
		}
	}
	
}

