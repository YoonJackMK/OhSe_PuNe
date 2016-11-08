package client.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class IdfindButton implements ActionListener 
{
   int kind = 0;
   public IdfindButton(int num) {
      this.kind = num;
   }
   @Override
   public void actionPerformed(ActionEvent e) {
      if(kind==0)new Find_ID();
      if(kind==1)new Find_PW();
      if(kind==2)new Join();
   }
   
}

public class Login extends JFrame {

   JLabel id = new JLabel("I  D:");
   JLabel pw = new JLabel("PW:");
   JTextField idtf = new JTextField();
   JPasswordField pwtf = new JPasswordField();
   JButton chk = new JButton("로그인");
   JButton idfind = new JButton("아이디찾기");
   JButton pwfind = new JButton("비밀번호찾기");
   JButton join = new JButton("회원가입");
   public Login() 
   {
      setTitle("세영이뿌네");
      setBounds(10,20,920,690);
      setLayout(null);
      id.setBounds(300, 400, 70, 30);
      id.setFont(new Font("Serif", Font.BOLD, 25));
      add(id);
      pw.setBounds(300, 450, 70, 30);
      pw.setFont(new Font("Serif", Font.BOLD, 25));
      add(pw);
      idtf.setBounds(360, 400, 240, 30);
      add(idtf);
      pwtf.setBounds(360, 450, 240, 30);
      add(pwtf);
      chk.setBounds(500, 500, 100, 40);
      add(chk);
      idfind.setBounds(300, 550, 100, 40);
      add(idfind);
      idfind.addActionListener(new IdfindButton(0));
      pwfind.setBounds(400, 550, 100, 40);
      pwfind.addActionListener(new IdfindButton(1));
      add(pwfind);
      join.setBounds(500, 550, 100, 40);
      join.addActionListener(new IdfindButton(2));
      add(join);
      
      
      setVisible(true);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   }
   public static void main(String[] args) {
      new Login();
   }

}