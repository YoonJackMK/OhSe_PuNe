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


class Login extends JFrame  {

	JLabel id_la = new JLabel("I  D:");
	JTextField id_txt = new JTextField();

	JLabel pw_la = new JLabel("PW:");
	JPasswordField pw_txt = new JPasswordField();


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


	}

	

}