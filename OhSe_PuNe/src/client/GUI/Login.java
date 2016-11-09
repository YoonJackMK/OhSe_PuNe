package client.GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame {

   JLabel id_la = new JLabel("I  D:");
   JTextField id_txt = new JTextField();
   
   JLabel pw_la = new JLabel("PW:");
   JPasswordField pw_txt = new JPasswordField();

   JButton login_btn = new JButton("로그인");
   JButton find_ID_btn = new JButton("아이디찾기");
   JButton find_PW_btn = new JButton("비밀번호찾기");
   JButton join_btn = new JButton("회원가입");
   
   public JPanel login = new JPanel();
  
   public Login() {
	   
	  login.setBounds(10,20,920,690);
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
      
      login_btn.setBounds(500, 500, 100, 40);
      login.add(login_btn);
      login_btn.addActionListener(new Login_Chk());

      find_ID_btn.setBounds(300, 550, 100, 40);
      login.add(find_ID_btn);
      find_ID_btn.addActionListener(new Find_Chk(0));

      find_PW_btn.setBounds(400, 550, 100, 40);
      login.add(find_PW_btn);
      find_PW_btn.addActionListener(new Find_Chk(1));
      
      join_btn.setBounds(500, 550, 100, 40);
      login.add(join_btn);
      join_btn.addActionListener(new Find_Chk(2));
      
 
   }
   
   class Login_Chk implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			String id_chk ="admin";
			String pw_chk ="admin";

			if((id_txt.getText().equals(id_chk))) {
				System.out.println("이건 됨?"); // ok
				
				// PW 비교 하게 하기.
				if((pw_txt.getText().equals(pw_chk))) {
					System.out.println("로그인 성공");
					new Lobby();
				}
			}
			else
				System.out.println("로그인 실패");
			
			
		}
	}
   
   
   public static void main(String[] args) {
      new Login();
   }

}