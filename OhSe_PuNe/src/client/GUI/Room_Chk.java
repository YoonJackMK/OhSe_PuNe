package client.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Room_Chk implements ActionListener 
{
	int kind = 0;
	public Room_Chk(int num) {
		// TODO Auto-generated constructor stub
		this.kind=num;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(kind==0)new Room_Create();
		if(kind==1)new Room_Find();
	}
}
