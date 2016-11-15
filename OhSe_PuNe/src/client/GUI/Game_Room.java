package client.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



class Game_Room extends JFrame 

{
	JPanel GameRoom = new JPanel();
	PuyoPanel myPanel; 
	// �гβ������
	//Text textBox;

	NextPanel nextPanel;

	JLabel Point ;
	JLabel textp;
	JLabel time;
	

	public Game_Room()

	{ 
		super("���ӷ�");

		GameRoom.setBounds(20, 20, 920, 690);
		GameRoom.setLayout(null);


		JPanel NextPoint = new JPanel();
		NextPoint.setBounds(305,170,200,60);
		NextPoint.setBackground(Color.gray);
		GameRoom.add(NextPoint);

		textp = new JLabel(" < ���� > ");
		Point= new JLabel("Score + 0" );
		Point.setBounds(305, 170, 200, 60);

		time = new JLabel("time + " );
		time.setBounds(305, 220, 200, 60);
        
		NextPoint.add(textp);
		NextPoint.add(Point);
		//NextPoint.add(time);

		GameRoom.add(time);
	

	} 
	
    void game(){
    	makeGUI();
		mm();
    }
	void makeGUI()
	{
		Container c = getContentPane();    

		myPanel = new PuyoPanel();
		myPanel.setBounds(5,20,255,600);

		//   textBox = new Text();

		//   add(textBox);

		c.add(myPanel, "Center");
		GameRoom.add(c);
		GameRoom.add(myPanel);
	} 

	void mm()
	{
		Container d = getContentPane();
		nextPanel = new NextPanel();
		nextPanel.setBounds(305,10,130,150);
		//         add(nextPanel);
		d.add(nextPanel,"Center");//�̸� ���� �г��� ��������ϴ�.
		GameRoom.add(d);
		GameRoom.add(nextPanel);
	}



	class Block{

		int X,Y;
		int r;

		Color color;
		int pipe;    //���� 
		int pipe2;   //���� 

		boolean fourboom; //������ ����

		Block(int X, int Y, int r, Color color){
			this.X=X;   
			this.Y=Y;
			this.r=r;
			this.color=color;     

			pipe =-1;
			pipe2=-1;


			fourboom = false;
		}




		public void paint(Graphics g){
			g.setColor(color);
			g.fillOval(X-r,Y-r, 2*r,2*r);
		}

		public boolean CollideDown(Block b){
			//System.out.println(Y+r+"y+r");
			/*   System.out.println(b.Y-r+"b.y-r");
			 * 
         System.out.println(b.Y+r+"b.y+r");*/
			return Y+(2*r) >= b.Y;
		}   

	}

	//�������� ����
	//������ �̵�

	class BlockPipe{


		LinkedList blocks;

		public BlockPipe(){
			blocks = new LinkedList();
		}

		public void paint(Graphics g){

			Iterator itr= blocks.iterator();

			while(itr.hasNext())
			{
				Object element= itr.next();
				Block b = (Block) element;
				b.paint(g);
			}
		}

		public int getSize()
		{
			return blocks.size();
		}

	}

	//����� ¦ a,b
	class BlockAB
	{
		Block blockA; 
		Block blockB;
		int blockB_OR;  //A�� �������� B�� ������ ��

		public BlockAB() {

			blockB_OR =0;

		}
		public void paint(Graphics g){
			blockA.paint(g);
			blockB.paint(g);
		}

	}
	class BlockCD////???
	{
		Block blockC;
		Block blockD;
		public void paint(Graphics g){// ���� ������༭ �׷��ִ� ��Ȱ�� ���ش�.
			blockC.paint(g);
			blockD.paint(g);

		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	interface init
	{

		int width=250;
		int height=600;
		int numW = 6; //  6�� �� ���� ���α���
		int inBlok = 12;  //�ִ� ���� �ִ� ���� ���α���
		int boom = 4; //4�� �̻��϶� �����
		int radius = 20;


		int nextWH= 130;//�ؽ�Ʈ â ũ��.
		int nestHE= 150;
		Color colors[]={Color.RED,Color.BLUE,Color.GREEN, Color.YELLOW};
		//���� �����̴� ��

		int RIGHT=1,LEFT=2,DOWN=3, UP=4;

		int RIGHT_OR = 0, TOP_OR=1, LEFT_OR=2, BOTTOM_OR=3;
		//B����  B��������     B�� ����      B������         B�� �Ʒ��� 

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////


	public class PuyoGame2  implements init 
	{
		BlockCD CD;
		public void StartGame2()
		{
			CD = new BlockCD();
			nextBB();
		}
		////////////////////////////////////////////////////////////////////////
		public void nextBB()//������ ���� ���� ���� ���ش�.
		{
			Random r = new Random();
			int colorindex = r.nextInt(4);//  �÷��� ����� �������� 4���� ���� �ְ� �������.

			//NextView.setBounds(305,10,130,150);
			Block blockC= new Block(50, 50, 20, colors[colorindex]);// ���� ����.

			colorindex= r.nextInt(4);// ���� �ٸ��� �ؾ��ؼ� �ٽ� �÷��� �Z��..

			Block blockD= new Block(90, 50, 20,  colors[colorindex]);
			CD.blockC= blockC;//A���̶�� ���� ����.
			CD.blockD =blockD;//B ���̶�� ���� ����.
		}
		public void Render2(Graphics g)// �� �׸�? ��Ȳ��?  ��� �׷��ִ� ��Ȱ../
		{ 
			CD.paint(g);
		}
	}

	public class PuyoGame  implements init {

		BlockAB  AB; // ����ڰ� ���� �����ϼ� �ִ� ¦ ��Ʈ
		BlockPipe [] pipes;
		LinkedList mBlocks; // �����鼭 �����̰� �ִ� ���̵�

		int score =0;


		public void StartGame()
		{
			AB = new BlockAB();
			mBlocks = new LinkedList();

			pipes = new BlockPipe[numW];

			for (int i = 0; i < numW ; i++) 
				pipes[i] =new BlockPipe();

			firstAB();



		}

		public void  firstAB()
		{

			Random r = new Random();
			int left =2;
			int right = left+1;
			int colorindex = r.nextInt(4);


			Block blockA= new Block(100, 150, 20, colors[colorindex]);

			colorindex= r.nextInt(4);

			Block blockB= new Block(140, 150, 20, colors[colorindex]);


			blockA.pipe =left;
			blockB.pipe = right;

			//AB ����
			AB.blockA= blockA;
			AB.blockB =blockB;
			AB.blockB_OR= RIGHT_OR;

		}

		public boolean Update()
		{
			moveMBlocks();

			Movepair();

			boolean gameOver = false;

			for (int i = 0; (i<numW) && !gameOver ; i++) 
				if(gameOver = (pipes[i].getSize() > inBlok || score >= 100))
					gameOver=true;


			return gameOver;

		}

		public int GetScore(){
			return score;
		}


		public void Render(Graphics g)
		{
			g.setColor(Color.black);

			AB.paint(g);

			for (int i = 0; i < numW; i++) 
				pipes[i].paint(g);


			for (int i = 0; i < mBlocks.size(); i++) 
			{
				Block bk = (Block) mBlocks.get(i);
				bk.paint(g);
			}


		}


		boolean MoveBlock(Block bk){

			if (! pipes[bk.pipe].blocks.isEmpty()) //�ؿ� ���� ������ 
			{
				if(!bk.CollideDown((Block) pipes[bk.pipe].blocks.getLast()))
				{
					bk.Y+=1;
					//System.out.println(bk.pipe +" �عٴ� ");
					//   System.out.println(bk.pipe2+" ���� ");
					return true;

				}

				else
				{   
					bk.pipe2 = pipes[bk.pipe].blocks.size(); 
					pipes[bk.pipe].blocks.add(bk);
					//   System.out.println(bk.pipe +" �عٴ� ");
					//   System.out.println(bk.pipe2+" ���� ");

					return false;
				}


			}

			if(!Bound(bk, DOWN)){
				bk.Y+=1;
				//   System.out.println(bk.pipe +" pipe --3");

				return true;
			}



			else 
			{
				//�������� �浹 �ؿ� �浹
				bk.pipe2 = pipes[bk.pipe].blocks.size();
				pipes[bk.pipe].blocks.addLast(bk);

				//   System.out.println(bk.pipe +" pipe --4");

				return false;
			}

		}


		void Movepair(){

			boolean ba = MoveBlock(AB.blockA);
			boolean bb = MoveBlock(AB.blockB);

			//A���� �������� �ʰ� b�� ���� ������ 
			if((!ba) && (AB.blockB_OR==TOP_OR))
			{
				serch(AB.blockA);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockB);

				firstAB();
			}

			//B���� �������� �ʰ�  B�� �Ʒ�������
			else if ((!bb) && (AB.blockB_OR==BOTTOM_OR))
			{
				pipes[AB.blockB.pipe].blocks.add(AB.blockA);
				AB.blockA.pipe2= AB.blockB.pipe2+1;

				serch(AB.blockB);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockA);


				firstAB();

			}
			//a�� �����̰� b�� �������� ������
			else if (ba && (!bb) )
			{
				// a���� mBlock�� �־��
				mBlocks.addLast(AB.blockA);

				serch(AB.blockB);

				firstAB();
			}

			else if( bb && (!ba) )
			{
				mBlocks.addLast(AB.blockB);

				serch(AB.blockA);
				firstAB();

			}

			//�Ѵ� �����϶� �������� ������
			else if (!(ba && bb))
			{
				if(AB.blockA.color!=AB.blockB.color)
					serch(AB.blockA);
				serch(AB.blockB);
				firstAB();
			}
		}
		//��Ʈ�� �Ҽ����� �����̴� ���̵�

		void moveMBlocks ()
		{

			ListIterator itr =  mBlocks.listIterator();

			while(itr.hasNext())
			{
				Block bk = (Block) itr.next();

				if(!MoveBlock(bk))

				{
					ListIterator litr = itr;

					itr.previous();

					serch((Block) itr.next());


					itr.previous();

					litr.remove();
				}
			}
		}


		public void ProcessKey(KeyEvent e){

			int key = e.getKeyCode();

			switch(key){
			case KeyEvent.VK_LEFT:
				Control(LEFT);

				break;

			case KeyEvent.VK_RIGHT:
				Control(RIGHT);

				break;


			case KeyEvent.VK_DOWN:
				Control(DOWN);

				break;

			case KeyEvent.VK_UP:
				Rotate();
				break;

			case KeyEvent.VK_SPACE:
				Space();
				break;
			}


		}

		boolean Bound(Block bk, int dir)
		{
			switch(dir)
			{
			case LEFT :

				return bk.pipe ==0;

			case RIGHT :

				return bk.pipe ==5;

			case DOWN :
			{
				return  bk.Y+20 > height;
			}
			default :
				return false;
			}
		}

		boolean moveRight(Block bk){
			bk.pipe++;
			bk.X+= 40;
			return true;
		}


		boolean moveLeft(Block bk){
			bk.pipe--;
			bk.X-=40;
			return true;

		}

		boolean MoveBlock2(Block bk){

			if (! pipes[bk.pipe].blocks.isEmpty()) //�ؿ� ���� ������ 
			{
				if(!bk.CollideDown((Block) pipes[bk.pipe].blocks.getLast()))
				{
					bk.Y+=1;
					//   System.out.println(bk.pipe +" �عٴ� ");
					//   System.out.println(bk.pipe2+" ���� ");
					return true;

				}

				else
				{   
					bk.pipe2 = pipes[bk.pipe].blocks.size(); 
					pipes[bk.pipe].blocks.add(bk);
					//      System.out.println(bk.pipe +" �عٴ� ");
					//      System.out.println(bk.pipe2+" ���� ");
					return false;
				}
			}

			if(!Bound(bk, DOWN)){
				bk.Y+=1;
				//   System.out.println(bk.pipe +" pipe --3");

				return true;
			}



			else 
			{
				//�������� �浹 �ؿ� �浹
				bk.pipe2 = pipes[bk.pipe].blocks.size();
				pipes[bk.pipe].blocks.addLast(bk);

				//   System.out.println(bk.pipe +" pipe --4");

				return false;
			}

		}

		public void Space()
		{

			boolean ba = MoveBlock2(AB.blockA);
			boolean bb = MoveBlock2(AB.blockB);

			int limit =  height-20;

			if((!ba) && (AB.blockB_OR==TOP_OR))
			{
				serch(AB.blockA);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockB);

				firstAB();
			}

			//B���� �������� �ʰ�  B�� �Ʒ�������
			else if ((!bb) && (AB.blockB_OR==BOTTOM_OR))
			{
				pipes[AB.blockB.pipe].blocks.add(AB.blockA);
				AB.blockA.pipe2= AB.blockB.pipe2+1;

				serch(AB.blockB);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockA);


				firstAB();

			}
			//a�� �����̰� b�� �������� ������
			else if (ba && (!bb) )
			{
				// a���� mBlock�� �־��
				mBlocks.addLast(AB.blockA);

				serch(AB.blockB);

				firstAB();
			}

			else if( bb && (!ba) )
			{
				mBlocks.addLast(AB.blockB);

				serch(AB.blockA);
				firstAB();

			}

			//�Ѵ� �����϶� �������� ������
			else if (!(ba && bb))
			{
				if(AB.blockA.color!=AB.blockB.color)
					serch(AB.blockA);
				serch(AB.blockB);

				firstAB();
			}


			/*   if(!Bound(AB.blockA, DOWN))
         {

            AB.blockA.Y= limit - pipes[AB.blockA.pipe].getSize()*40 ;
            AB.blockB.Y= limit - pipes[AB.blockB.pipe].getSize()*40 ; 

         }

         if(!Bound(AB.blockB, DOWN))
         {

            AB.blockA.Y= limit - pipes[AB.blockA.pipe].getSize()*40 ;
            AB.blockB.Y= limit - pipes[AB.blockB.pipe].getSize()*40 ; 

         }*/
			int i = AB.blockB_OR;


			switch(i)
			{

			case RIGHT_OR:
			{
				AB.blockA.Y= limit - pipes[AB.blockA.pipe].getSize()*40 ;
				AB.blockB.Y= limit - pipes[AB.blockB.pipe].getSize()*40 ; 


				break;

			}

			case TOP_OR:
			{
				if(AB.blockB.pipe>0)
				{
					AB.blockB.Y =AB.blockA.Y;
					AB.blockB.X= AB.blockA.X -40;
					AB.blockB_OR=LEFT_OR;
					AB.blockB.pipe--;

					AB.blockA.Y= limit - pipes[AB.blockA.pipe].getSize()*40 ;
					AB.blockB.Y= limit - pipes[AB.blockB.pipe].getSize()*40 ; 

				}


				break;
			}
			case LEFT_OR:
			{
				/*if(Bound(AB.blockB, DOWN))
               break;
				 */

				AB.blockA.Y= limit - pipes[AB.blockA.pipe].getSize()*40 ;
				AB.blockB.Y= limit - pipes[AB.blockB.pipe].getSize()*40 ; 

				break;

			}
			case BOTTOM_OR:
			{
				if(AB.blockB.pipe < 5){

					AB.blockB_OR =RIGHT_OR;
					AB.blockB.Y= AB.blockA.Y;
					AB.blockB.X = AB.blockA.X +40;
					AB.blockB.pipe++;
					AB.blockA.Y= limit - pipes[AB.blockA.pipe].getSize()*40 ;
					AB.blockB.Y= limit - pipes[AB.blockB.pipe].getSize()*40 ; 

				}

				break;
			}
			}   

		}

		public void Control(int dir){

			switch(dir)
			{

			case  DOWN :

			{
				switch(AB.blockB_OR){

				case TOP_OR:

					if(!Bound(AB.blockA,DOWN))
					{
						AB.blockA.Y+=15;
						AB.blockB.Y+=15;
					}
					break;

				default:
					if(!Bound(AB.blockB,DOWN))
					{
						AB.blockA.Y+=15;
						AB.blockB.Y+=15;
					}
					break;

				}
				break;

			}

			case LEFT :
			{

				switch(AB.blockB_OR)
				{
				case RIGHT_OR:
				{
					if(!Bound(AB.blockA,LEFT))
						if(moveLeft(AB.blockA))
							moveLeft(AB.blockB);
					break;
				}

				case TOP_OR :
				{
					if(!Bound(AB.blockA, LEFT))
						if(moveLeft(AB.blockA))
							moveLeft(AB.blockB);
					break;
				}

				case LEFT_OR :
				{
					if(!Bound(AB.blockB, LEFT))
						if(moveLeft(AB.blockB))
							moveLeft(AB.blockA);
					break;
				}

				case BOTTOM_OR:
				{
					if(!Bound(AB.blockA, LEFT))
						if(moveLeft(AB.blockB))
							moveLeft(AB.blockA);
					break;
				}
				}
				break;

			}
			case RIGHT :
			{
				switch(AB.blockB_OR)
				{
				case RIGHT_OR:
				{
					if(!Bound(AB.blockB, RIGHT))
						if(moveRight(AB.blockB))
							moveRight(AB.blockA);
					break;
				}
				case TOP_OR:
				{
					if(!Bound(AB.blockB, RIGHT))
						if(moveRight(AB.blockA))
							moveRight(AB.blockB);
					break;
				}


				case LEFT_OR:
				{
					if(!Bound(AB.blockA, RIGHT))
						if(moveRight(AB.blockA))
							moveRight(AB.blockB);
					break;

				}

				case BOTTOM_OR:
				{
					if(!Bound(AB.blockB,RIGHT ))
						if(moveRight(AB.blockB))
							moveRight(AB.blockA);
					break;
				}

				}
				break;
			}
			}
		}


		public void Rotate(){

			int i = AB.blockB_OR;


			switch(i)
			{

			case RIGHT_OR:
			{
				AB.blockB_OR=TOP_OR;
				AB.blockB.X=AB.blockA.X;
				AB.blockB.Y=AB.blockA.Y -40;
				AB.blockB.pipe = AB.blockA.pipe;

				break;

			}

			case TOP_OR:
			{
				if(AB.blockB.pipe>0)
				{

					AB.blockB.Y =AB.blockA.Y;
					AB.blockB.X= AB.blockA.X -40;
					AB.blockB_OR=LEFT_OR;
					AB.blockB.pipe--;
				}


				break;
			}
			case LEFT_OR:
			{
				if(Bound(AB.blockB, DOWN))
					break;


				AB.blockB_OR =BOTTOM_OR;
				AB.blockB.Y = AB.blockA.Y +40;
				AB.blockB.X = AB.blockA.X;
				AB.blockB.pipe = AB.blockA.pipe;
				break;

			}
			case BOTTOM_OR:
			{
				if(AB.blockB.pipe < 5){

					AB.blockB_OR =RIGHT_OR;
					AB.blockB.Y= AB.blockA.Y;
					AB.blockB.X = AB.blockA.X +40;
					AB.blockB.pipe++;
				}

				break;
			}
			}   
		}



		void serch(Block bk)
		{
			LinkedList four = new LinkedList();
			LinkedList list = new LinkedList();

			four.add(bk);
			bk.fourboom= true;

			Iterator itr = four.iterator();

			while(itr.hasNext())
			{
				Object element = itr.next();
				Block block = (Block) element;

				LinkedList around = bobo(block);

				for (int i = 0; i < around.size() ; i++) {

					Block ar= (Block) around.get(i);

					if ((!ar.fourboom) && (ar.color==block.color))
					{
						four.add(ar);
						ar.fourboom =true;
					}
				}

				list.add(four.remove());
				itr = four.iterator();


			}

			if(list.size() >= boom )

				for (int i = 0; i < list.size(); i++) 
				{
					Object oj = list.get(i);
					Block kb = (Block) oj;

					removeBlock(kb);
				}

			else 
				for (int i = 0; i < list.size(); i++) {

					Object oj =list.get(i);
					Block kb = (Block) oj;
					kb.fourboom = false;
				}

		} 



		LinkedList bobo(Block bk)
		{
			LinkedList around = new LinkedList();

			int ppipe = bk.pipe;
			int ppipe2 =bk.pipe2;

			if(bk.pipe > 0 )
				if(pipes[ppipe-1].blocks.size() > ppipe2)
					around.add(pipes[ppipe-1].blocks.get(ppipe2));

			if(bk.pipe < 5)

				if(pipes[ppipe+1].blocks.size() > ppipe2)
					around.add(pipes[ppipe+1].blocks.get(ppipe2));

			if(pipes[ppipe].blocks.size() > ppipe2+1)
				around.add( pipes[ppipe].blocks.get(ppipe2+1));


			if(ppipe2-1 > -1)
				around.add( pipes[ppipe].blocks.get(ppipe2-1));

			return around;


		}

		void removeBlock(Block block)
		{
			for (int i = block.pipe2 + 1 ; i < pipes[block.pipe].blocks.size(); i++) 
			{

				Object oj = pipes[block.pipe].blocks.get(i);
				Block bb = (Block) oj;
				bb.pipe2--;
				bb.Y+= 40;
			}

			pipes[block.pipe].blocks.remove(block.pipe2);
			score++;
			Point.setText("Score : "+score+"��");


		}



	}
	class NextPanel extends JPanel implements Runnable, init
	{

		int PWIDTH2 = nextWH;
		int PHEIGHT2 = nextWH;

		Thread animator2;// �����带 ������ ���ؼ�.

		boolean running2 = false;   // �����·� �Z��

		Game_Room topFrame2;// ��ü �гο��� ������ ��� �ϱ� ���ؼ�.

		PuyoGame2 nextPuyo;//  �ҷ��� ���. �ϰٴ�/

		Graphics dbg2; 
		Image dbImage2 = null;

		public NextPanel() 
		{
			setBackground(Color.white);

			setPreferredSize( new Dimension(PWIDTH2, PHEIGHT2));// ��� ������ �����ش�.
			//setBounds(20, 30, 150, 150);
			//             setFocusable(true);// �ּ� Ǯ�� �ȿ����δ�/

			nextPuyo= new PuyoGame2();// ���Ӱ� ���� ���ְ�.     ������    ������
			nextPuyo.StartGame2();// myPuyo �ȿ� �մ� �� ������ �������� ��ŸƮ �ϰ�

			startGame2();// �����带 �����Ѵ�.
		}

		void startGame2()
		{ 
			if (animator2 == null || !running2) {// �ȵ��� ������... ���Ѵ�?
				animator2 = new Thread(this);
				animator2.start();//�����带 �����Ѵ�. run  �޼ҵ� ����.
			}
		} 
		public void run()//������ ���� �κ� �����尡 ���۰�����
		{

			running2 = true;

			while(running2) {
				gameRender2();   // ���ۿ� ����.  ������ �׷��ִ¿�
				paintScreen2();  // ȭ�� ���۸� �׸�


			}
		}
		void gameRender2()//  ���  �׸�  ����ִ°�  ��  ���̸� �������༭ �׸� �׷���.
		{
			if (dbImage2 == null){
				dbImage2 = createImage(PWIDTH2, PHEIGHT2);// ���� ���� �׸�// �׸��� ������ ���.

				return;

			}

			else{
				dbg2 = dbImage2.getGraphics(); 
			}

			dbg2.setColor(Color.black);
			dbg2.fillRect (0,0, PWIDTH2, PHEIGHT2);//��ü ��ʾ��� ��ġ(���̴� ���)

			nextPuyo.Render2(dbg2);// �׸� �׸�?
		}  

		void paintScreen2() 
		{ 
			Graphics g;
			try {
				g = this.getGraphics();
				if ((g != null) && (dbImage2 != null))
					g.drawImage(dbImage2, 0, 0, null);
				g.dispose();
			}
			catch (Exception e)
			{ 

			}
		} 



	}

	class PuyoPanel extends JPanel implements Runnable, init
	{

		int PWIDTH = width; 
		int PHEIGHT = height; 


		Thread gogo;
		int stime=0;

		boolean running = false;   
		boolean gameOver = false;
	
		Game_Room topFrame;
		PuyoGame myPuyo;


		int scroe = 0;


		Graphics dbg; 

		Image dbImage = null;

		public PuyoPanel()
		{
			setBackground(Color.white);

			//setPreferredSize()��  Dimention ��ü�� ���ڷ� ������ ������Ʈ�� ũ�⸦ �����Ҽ� �ִ�.

			setPreferredSize( new Dimension(PWIDTH, PHEIGHT));

			//setBounds(20, 30, 150, 150);
			setFocusable(true);

			myPuyo= new PuyoGame();
			myPuyo.StartGame();
			startGame();
			addKeyListener( new KeyAdapter() {

				public void keyPressed(KeyEvent e)
				{ 
					myKeyPressed(e); }
			});
		}   
		void myKeyPressed(KeyEvent e)
		{ 

			if(!gameOver)

				myPuyo.ProcessKey(e);

		}


		void startGame()

		{ 
			if (gogo == null || !running) {
				gogo = new Thread(this);
				gogo.start();

			}

		} 

		public void run()

		{

			running = true;


			int maxtime=4000;

			while(running) {
				gameUpdate(); 
				gameRender();   // ���ۿ� ����.
				paintScreen();  // ȭ�� ���۸� �׸�
				if(myPuyo.Update()){
					showMessage();
					break;

				}
				if(stime == maxtime){
					showMessage();

					break;
				}
				try {
					Thread.sleep(20);
					stime++;
					time.setText("TIME : "+ stime/100+"sec");
				} catch (Exception e) {

				}
			}
		}
		void showMessage() {
			JOptionPane.showMessageDialog(null,"  GAME OVER   "+"\n"+" ȹ�� ���� "+ myPuyo.score+ "��"+ "   �� �� "+stime/100 +"sec",
					"M",JOptionPane.WARNING_MESSAGE);

		}

		void gameUpdate() 
		{ 
			if (!gameOver)    
				myPuyo.Update();

			//this.topFrame.setScoreNumber(myPuyo.GetScore());

		}  
		void gameRender()
		{
			if (dbImage == null){
				dbImage = createImage(PWIDTH, PHEIGHT);

				return;

			}

			else{
				dbg = dbImage.getGraphics();
			}

			//���
			dbg.setColor(Color.darkGray);
			dbg.fillRect (0, 0, PWIDTH, PHEIGHT);

			myPuyo.Render(dbg);

		}  
		void paintScreen()

		{ 
			Graphics g;
			try {
				g = this.getGraphics();
				if ((g != null) && (dbImage != null))
					g.drawImage(dbImage, 0, 0, null);

				g.dispose();
			}
			catch (Exception e)
			{ 

			}
		} 
	}

	


}