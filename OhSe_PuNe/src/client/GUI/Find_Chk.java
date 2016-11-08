package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Find_Chk implements ActionListener 
{
	int kind = 0;
	public Find_Chk(int num) {
		this.kind = num;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(kind==0)new Find_ID();
		if(kind==1)new Find_PW();
		if(kind==2)new Join();
	}
}

