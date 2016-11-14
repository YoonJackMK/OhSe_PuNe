package client.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


class Login extends JFrame implements ActionListener {

	JLabel id_la = new JLabel("I  D:");
	JTextField id_txt = new JTextField();

	JLabel pw_la = new JLabel("PW:");
	JPasswordField pw_txt = new JPasswordField();


	JButton find_ID_btn = new JButton("Find ID");
	JButton find_PW_btn = new JButton("Find PW");
	JButton join_btn = new JButton("Join");

	JPanel login = new JPanel();

	Login() {

		login.setBounds(0,0,920,690);
		login.setLayout(null);

		id_la.setBounds(300, 400, 70, 30);
		id_la.setFont(new Font("Serif", Font.BOLD, 25));
		login.add(id_la);

		pw_la.setBounds(300, 450, 70, 30);
		pw_la.setFont(new Font("Serif", Font.BOLD, 25));
		login.add(pw_la);

		id_txt.setBounds(360, 400, 240, 30);
		login.add(id_txt);

		pw_txt.setBounds(360, 450, 240, 30);
		login.add(pw_txt);



		find_ID_btn.setBounds(300, 550, 100, 40);
		find_ID_btn.setBackground(Color.GRAY);
		find_ID_btn.addActionListener(this);
		login.add(find_ID_btn);


		find_PW_btn.setBounds(400, 550, 100, 40);
		find_PW_btn.setBackground(Color.GRAY);
		find_PW_btn.addActionListener(this);
		login.add(find_PW_btn);


		join_btn.setBounds(500, 550, 100, 40);
		join_btn.setBackground(Color.GRAY);
		join_btn.addActionListener(this);
		login.add(join_btn);



	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==find_ID_btn) new Find_ID();
		if(e.getSource()==find_PW_btn) new Find_PW();
		if(e.getSource()==join_btn) new Join();

	}


}