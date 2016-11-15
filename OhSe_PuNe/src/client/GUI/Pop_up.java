package client.GUI;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Pop_up extends JFrame{

	public Pop_up(String msg) {
		// TODO Auto-generated constructor stub
		 setBounds(100, 200, 300, 150);
		  setLayout(null);
		  JLabel notice = new JLabel(msg,SwingConstants.CENTER);
		  notice.setBounds(0,20,300,40);
		  add(notice);
		  JButton chk = new JButton("»Æ¿Œ");
		  chk.setBounds(110,70,70,30);
		  
		  add(chk);
		  chk.addActionListener(new Cancel(this));
		  setVisible(true); 
	}

}
