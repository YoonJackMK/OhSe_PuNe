package client.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;






public class Buyouu2 extends JFrame {

   JPanel GameUser = new JPanel();

   
   Point random;
   Point random2;
   int low =240;
   int kind=0;
   int down = 20;

   ImageIcon [] img={
         new ImageIcon("img/red.JPG"),
         new ImageIcon("img/blue.JPG"),
         new ImageIcon("img/green.JPG"),
         new ImageIcon("img/yellow.JPG")
         
         };
//   int [][]arr={
//      {random.x+40,random2.y},//기준점 오른쪽 기준빨간색.
//      {random.x,random2.y+40},// 밑으로 감
//      {random.x-40,random2.y},//왼쪽으로감
//      {random.x,random2.y-40}   //위로
//      };
   boolean chk =false;
   
   ArrayList<iconn_1>icrr_1 = new ArrayList<>();
   ArrayList<iconn_2>icrr_2 = new ArrayList<>();
   int  dx=20 , dy=5,dy1=5;
   int sp=40;
   class iconn_1 extends JLabel// 기준점,
   {
      Buyouu2 main;
      //ImageIcon chk1 = ;
      public iconn_1(Buyouu2 main) 
      {
         //random=new Point(Board.col/2,0);
         super(img[(int) (Math.random()*4)]);
//         super(img[0]);
         random=new Point(low/2,10);// 나오는 좌표를 지정 해주는것.
         //픽맨이 나오는 좌표.
         
         
         setBounds(random.x, random.y, 40, 40);//random.x, random.y 이둘은 기준점 의 x,y좌표들 변하지 안는 좌표.
         main.add(this);
      }

      /////게임창 유저1
      void move()
      {// 크기 중간 20 //  밑에서 부터 희선까지  대략 30 // 대략 위에 40.
         random.y+=dy;//움직이게 해준다.
      
      //if(random.x>0 || random.x <300)
      //{
         if(random.y>580){
            random.y=580;
         }
         setLocation(random.x,random.y);
         System.out.println(random.x+"하나번쨰");
         System.out.println(random.y+"하나번쨰");
      //}
         //System.out.println(random.y+"첫번쨰");
         //setLocation(random.x,random.y);

      }

   }
   ///////////////////////////////////////
   class iconn_2 extends JLabel//두번째 아이콘.
   {
      Buyouu2 main;
   
      public iconn_2(Buyouu2 main)
      {
         super(img[(int) (Math.random()*4)]);
//         super(img[3]);
         random2=new Point(random.x+40,10);//두번쨰 아이콘의 좌표는 기준점x 의 좌표에 +40을 해줘서 생성
//         random2.x=random.x+40;
         setBounds(random2.x, random2.y, 40, 40);
         main.add(this);
      }
      
      void move2()
      {// 크기 중간 20 //  밑에서 부터 희선까지  대략 30 // 대략 위에 40.
      
         random2.y+=dy1;//움직이게 해준다.

//         if(random2.y+40>620&&random2.x==random2.x){
//            random2.y=620;
//         }
         if(random2.y>580){
             random2.y=580;
          }
         setLocation(random2.x,random2.y);//기준점 오른쪽 기준빨간색.
//         setLocation(random2.x-40,random2.y+40);// 밑으로 감
//         setLocation(random2.x-80,random2.y);//왼쪽으로감
//         setLocation(random2.x-40,random2.y-40);//위로
         System.out.println(random2.x+"두번쨰");

   
      }
      
   }
//////////////////////////////////////////////
   class   iconn_1MO extends KeyAdapter//움직이는 클레스.
   {// 기준점 이 움직일떄 사용되는 키어뎁트.
      
   
      public void keyPressed(KeyEvent e)// 움직일때 기준점
      {
         System.out.println("키눌럿음");
         
         switch (e.getKeyCode()) // 키누름
         {
            case KeyEvent.VK_LEFT:random.x-=sp;
                            random2.x-=sp;
               break;
            case KeyEvent.VK_RIGHT:random.x+=sp;
                              random2.x+=sp;
               break; 
            case KeyEvent.VK_UP:
               kind++;
            if(kind==1){ random2.x=random.x; random2.y+=40; break;}   //밑으로 갈떄.      
            if(kind==2){ random2.x=random.x-40; random2.y=random2.y-40; break;}   //왼쪽으로 갈떄
            if(kind==3){ random2.x=random.x; random2.y=random2.y-40; break;}   //위로갈떄.
            else if(kind>1) kind=0; 
            // 위에서 kind를 추카 시키다 3 이상으로 갓을떄  else if 로 kind 를 초기화 시켜준다.
            // 그리고 0일때는 맨처음 초기값으로 다시 지정을 해준다.      
            if(kind==0){ random2.x=random.x+40; random2.y=random2.y+40; break;}   

            case KeyEvent.VK_DOWN://다운키를 눌럿을떄. +25 씩 증가 한다.
               random.y+=down;
               random2.y+=down;
                  break;   
         }
      
      }
      
   
   }
/////////////////////////////////////////////   
   
   class Timer extends Thread
   {
   
      int a;
      public void run() {
         // TODO Auto-generated method stub
         ww:while(true)
         {
            try {
               sleep(100);
               for (iconn_1 icon : icrr_1) {
                  icon.move();//기준점  이미지.
                  
               }
               for (iconn_2 icon_2 : icrr_2) {
                  icon_2.move2();//두번쨰 이미지.
                  
               }
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
      
   }

   
   
   public Buyouu2()//전체 크기의 패널.
   {
      super("♥세영이뿌네♥");
      setBounds(20, 20, 920, 690);
      setLayout(null);

      /////게임창 유저1
      GameUser.setBounds(5,20,low,600);
      GameUser.setBackground(Color.white);

      ////////////////////////////////////
      
      
      icrr_1.add(new iconn_1(this));//첫번째 이미지
      icrr_2.add(new iconn_2(this));//두번쨰 이미지.
      ////////////////////////////////////
      add(GameUser);
   
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      addKeyListener(new iconn_1MO());//키보드를 활용하기 위해서
      new Timer().start();
   }
   ////////////////////////////////////////////////////

   
   
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new Buyouu2(); 
//////////////////////////////////////
   }
}