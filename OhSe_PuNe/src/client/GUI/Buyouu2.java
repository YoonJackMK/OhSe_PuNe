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
//      {random.x+40,random2.y},//������ ������ ���ػ�����.
//      {random.x,random2.y+40},// ������ ��
//      {random.x-40,random2.y},//�������ΰ�
//      {random.x,random2.y-40}   //����
//      };
   boolean chk =false;
   
   ArrayList<iconn_1>icrr_1 = new ArrayList<>();
   ArrayList<iconn_2>icrr_2 = new ArrayList<>();
   int  dx=20 , dy=5,dy1=5;
   int sp=40;
   class iconn_1 extends JLabel// ������,
   {
      Buyouu2 main;
      //ImageIcon chk1 = ;
      public iconn_1(Buyouu2 main) 
      {
         //random=new Point(Board.col/2,0);
         super(img[(int) (Math.random()*4)]);
//         super(img[0]);
         random=new Point(low/2,10);// ������ ��ǥ�� ���� ���ִ°�.
         //�ȸ��� ������ ��ǥ.
         
         
         setBounds(random.x, random.y, 40, 40);//random.x, random.y �̵��� ������ �� x,y��ǥ�� ������ �ȴ� ��ǥ.
         main.add(this);
      }

      /////����â ����1
      void move()
      {// ũ�� �߰� 20 //  �ؿ��� ���� �񼱱���  �뷫 30 // �뷫 ���� 40.
         random.y+=dy;//�����̰� ���ش�.
      
      //if(random.x>0 || random.x <300)
      //{
         if(random.y>580){
            random.y=580;
         }
         setLocation(random.x,random.y);
         System.out.println(random.x+"�ϳ�����");
         System.out.println(random.y+"�ϳ�����");
      //}
         //System.out.println(random.y+"ù����");
         //setLocation(random.x,random.y);

      }

   }
   ///////////////////////////////////////
   class iconn_2 extends JLabel//�ι�° ������.
   {
      Buyouu2 main;
   
      public iconn_2(Buyouu2 main)
      {
         super(img[(int) (Math.random()*4)]);
//         super(img[3]);
         random2=new Point(random.x+40,10);//�ι��� �������� ��ǥ�� ������x �� ��ǥ�� +40�� ���༭ ����
//         random2.x=random.x+40;
         setBounds(random2.x, random2.y, 40, 40);
         main.add(this);
      }
      
      void move2()
      {// ũ�� �߰� 20 //  �ؿ��� ���� �񼱱���  �뷫 30 // �뷫 ���� 40.
      
         random2.y+=dy1;//�����̰� ���ش�.

//         if(random2.y+40>620&&random2.x==random2.x){
//            random2.y=620;
//         }
         if(random2.y>580){
             random2.y=580;
          }
         setLocation(random2.x,random2.y);//������ ������ ���ػ�����.
//         setLocation(random2.x-40,random2.y+40);// ������ ��
//         setLocation(random2.x-80,random2.y);//�������ΰ�
//         setLocation(random2.x-40,random2.y-40);//����
         System.out.println(random2.x+"�ι���");

   
      }
      
   }
//////////////////////////////////////////////
   class   iconn_1MO extends KeyAdapter//�����̴� Ŭ����.
   {// ������ �� �����ϋ� ���Ǵ� Ű�Ʈ.
      
   
      public void keyPressed(KeyEvent e)// �����϶� ������
      {
         System.out.println("Ű������");
         
         switch (e.getKeyCode()) // Ű����
         {
            case KeyEvent.VK_LEFT:random.x-=sp;
                            random2.x-=sp;
               break;
            case KeyEvent.VK_RIGHT:random.x+=sp;
                              random2.x+=sp;
               break; 
            case KeyEvent.VK_UP:
               kind++;
            if(kind==1){ random2.x=random.x; random2.y+=40; break;}   //������ ����.      
            if(kind==2){ random2.x=random.x-40; random2.y=random2.y-40; break;}   //�������� ����
            if(kind==3){ random2.x=random.x; random2.y=random2.y-40; break;}   //���ΰ���.
            else if(kind>1) kind=0; 
            // ������ kind�� ��ī ��Ű�� 3 �̻����� ������  else if �� kind �� �ʱ�ȭ �����ش�.
            // �׸��� 0�϶��� ��ó�� �ʱⰪ���� �ٽ� ������ ���ش�.      
            if(kind==0){ random2.x=random.x+40; random2.y=random2.y+40; break;}   

            case KeyEvent.VK_DOWN://�ٿ�Ű�� ��������. +25 �� ���� �Ѵ�.
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
                  icon.move();//������  �̹���.
                  
               }
               for (iconn_2 icon_2 : icrr_2) {
                  icon_2.move2();//�ι��� �̹���.
                  
               }
            } catch (InterruptedException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
         }
      }
      
   }

   
   
   public Buyouu2()//��ü ũ���� �г�.
   {
      super("�������̻ѳע�");
      setBounds(20, 20, 920, 690);
      setLayout(null);

      /////����â ����1
      GameUser.setBounds(5,20,low,600);
      GameUser.setBackground(Color.white);

      ////////////////////////////////////
      
      
      icrr_1.add(new iconn_1(this));//ù��° �̹���
      icrr_2.add(new iconn_2(this));//�ι��� �̹���.
      ////////////////////////////////////
      add(GameUser);
   
      setVisible(true);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      addKeyListener(new iconn_1MO());//Ű���带 Ȱ���ϱ� ���ؼ�
      new Timer().start();
   }
   ////////////////////////////////////////////////////

   
   
   
   public static void main(String[] args) {
      // TODO Auto-generated method stub
      new Buyouu2(); 
//////////////////////////////////////
   }
}