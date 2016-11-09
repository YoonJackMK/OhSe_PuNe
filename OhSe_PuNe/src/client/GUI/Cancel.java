package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Cancel implements ActionListener {
	JFrame jf;
	public Cancel(JFrame jj) {
		// TODO Auto-generated constructor stub
		this.jf=jj;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jf.dispose();
	}

}

