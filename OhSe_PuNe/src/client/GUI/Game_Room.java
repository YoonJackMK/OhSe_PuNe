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
	// 패널끌고오는
	//Text textBox;

	NextPanel nextPanel;

	JLabel Point ;
	JLabel textp;
	JLabel time;
	

	public Game_Room()

	{ 
		super("게임룸");

		GameRoom.setBounds(20, 20, 920, 690);
		GameRoom.setLayout(null);


		JPanel NextPoint = new JPanel();
		NextPoint.setBounds(305,170,200,60);
		NextPoint.setBackground(Color.gray);
		GameRoom.add(NextPoint);

		textp = new JLabel(" < 점수 > ");
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
		d.add(nextPanel,"Center");//미리 보기 패널을 만들었습니다.
		GameRoom.add(d);
		GameRoom.add(nextPanel);
	}



	class Block{

		int X,Y;
		int r;

		Color color;
		int pipe;    //세로 
		int pipe2;   //가로 

		boolean fourboom; //터질때 쓰자

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

	//파이프로 구성
	//벽돌의 이동

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

	//블록의 짝 a,b
	class BlockAB
	{
		Block blockA; 
		Block blockB;
		int blockB_OR;  //A를 기준으로 B가 움직임 용

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
		public void paint(Graphics g){// 블럭을 만들어줘서 그려주는 역활을 해준다.
			blockC.paint(g);
			blockD.paint(g);

		}
	}
	///////////////////////////////////////////////////////////////////////////////////////////
	interface init
	{

		int width=250;
		int height=600;
		int numW = 6; //  6개 들어갈 공간 가로기준
		int inBlok = 12;  //최대 들어갈수 있는 갯수 세로기준
		int boom = 4; //4개 이상일때 사라짐
		int radius = 20;


		int nextWH= 130;//넥스트 창 크기.
		int nestHE= 150;
		Color colors[]={Color.RED,Color.BLUE,Color.GREEN, Color.YELLOW};
		//방향 움직이는 것

		int RIGHT=1,LEFT=2,DOWN=3, UP=4;

		int RIGHT_OR = 0, TOP_OR=1, LEFT_OR=2, BOTTOM_OR=3;
		//B기준  B가오른쪽     B가 위에      B가왼쪽         B가 아래로 

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
		public void nextBB()//다음에 나올 블럭을 생성 해준다.
		{
			Random r = new Random();
			int colorindex = r.nextInt(4);//  컬러를 만들고 렌덤으로 4까지 돌수 있게 만들엇따.

			//NextView.setBounds(305,10,130,150);
			Block blockC= new Block(50, 50, 20, colors[colorindex]);// 블럭을 생성.

			colorindex= r.nextInt(4);// 색을 다르게 해야해서 다시 컬러를 줫다..

			Block blockD= new Block(90, 50, 20,  colors[colorindex]);
			CD.blockC= blockC;//A블럭이라고 지정 해줌.
			CD.blockD =blockD;//B 블럭이라고 지정 해줌.
		}
		public void Render2(Graphics g)// 블럭 그림? 현황판?  계속 그려주는 역활../
		{ 
			CD.paint(g);
		}
	}

	public class PuyoGame  implements init {

		BlockAB  AB; // 사용자가 블럭을 움직일수 있는 짝 세트
		BlockPipe [] pipes;
		LinkedList mBlocks; // 다으면서 움직이고 있는 아이들

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

			//AB 보관
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

			if (! pipes[bk.pipe].blocks.isEmpty()) //밑에 뭔가 있을때 
			{
				if(!bk.CollideDown((Block) pipes[bk.pipe].blocks.getLast()))
				{
					bk.Y+=1;
					//System.out.println(bk.pipe +" 밑바닥 ");
					//   System.out.println(bk.pipe2+" 높이 ");
					return true;

				}

				else
				{   
					bk.pipe2 = pipes[bk.pipe].blocks.size(); 
					pipes[bk.pipe].blocks.add(bk);
					//   System.out.println(bk.pipe +" 밑바닥 ");
					//   System.out.println(bk.pipe2+" 높이 ");

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
				//파이프에 충돌 밑에 충돌
				bk.pipe2 = pipes[bk.pipe].blocks.size();
				pipes[bk.pipe].blocks.addLast(bk);

				//   System.out.println(bk.pipe +" pipe --4");

				return false;
			}

		}


		void Movepair(){

			boolean ba = MoveBlock(AB.blockA);
			boolean bb = MoveBlock(AB.blockB);

			//A블럭은 움직이지 않고 b가 위에 있을때 
			if((!ba) && (AB.blockB_OR==TOP_OR))
			{
				serch(AB.blockA);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockB);

				firstAB();
			}

			//B블럭은 움직이지 않고  B가 아래있을때
			else if ((!bb) && (AB.blockB_OR==BOTTOM_OR))
			{
				pipes[AB.blockB.pipe].blocks.add(AB.blockA);
				AB.blockA.pipe2= AB.blockB.pipe2+1;

				serch(AB.blockB);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockA);


				firstAB();

			}
			//a는 움직이고 b는 움직이지 않을때
			else if (ba && (!bb) )
			{
				// a블러은 mBlock에 넣어둠
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

			//둘다 수평일때 움직이지 않을때
			else if (!(ba && bb))
			{
				if(AB.blockA.color!=AB.blockB.color)
					serch(AB.blockA);
				serch(AB.blockB);
				firstAB();
			}
		}
		//컨트롤 할수없고 움직이는 아이들

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

			if (! pipes[bk.pipe].blocks.isEmpty()) //밑에 뭔가 있을때 
			{
				if(!bk.CollideDown((Block) pipes[bk.pipe].blocks.getLast()))
				{
					bk.Y+=1;
					//   System.out.println(bk.pipe +" 밑바닥 ");
					//   System.out.println(bk.pipe2+" 높이 ");
					return true;

				}

				else
				{   
					bk.pipe2 = pipes[bk.pipe].blocks.size(); 
					pipes[bk.pipe].blocks.add(bk);
					//      System.out.println(bk.pipe +" 밑바닥 ");
					//      System.out.println(bk.pipe2+" 높이 ");
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
				//파이프에 충돌 밑에 충돌
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

			//B블럭은 움직이지 않고  B가 아래있을때
			else if ((!bb) && (AB.blockB_OR==BOTTOM_OR))
			{
				pipes[AB.blockB.pipe].blocks.add(AB.blockA);
				AB.blockA.pipe2= AB.blockB.pipe2+1;

				serch(AB.blockB);

				if(AB.blockA.color != AB.blockB.color)
					serch(AB.blockA);


				firstAB();

			}
			//a는 움직이고 b는 움직이지 않을때
			else if (ba && (!bb) )
			{
				// a블러은 mBlock에 넣어둠
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

			//둘다 수평일때 움직이지 않을때
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
			Point.setText("Score : "+score+"점");


		}



	}
	class NextPanel extends JPanel implements Runnable, init
	{

		int PWIDTH2 = nextWH;
		int PHEIGHT2 = nextWH;

		Thread animator2;// 쓰레드를 돌리기 위해서.

		boolean running2 = false;   // 블린형태뤄 줫다

		Game_Room topFrame2;// 전체 패널에서 접근을 허용 하기 위해서.

		PuyoGame2 nextPuyo;//  불러서 사용. 하겟다/

		Graphics dbg2; 
		Image dbImage2 = null;

		public NextPanel() 
		{
			setBackground(Color.white);

			setPreferredSize( new Dimension(PWIDTH2, PHEIGHT2));// 페널 싸이즈 맞춰준다.
			//setBounds(20, 30, 150, 150);
			//             setFocusable(true);// 주석 풀면 안움직인다/

			nextPuyo= new PuyoGame2();// 새롭게 정의 해주고.     ㅁㄴㅁ    ㅁㄴㅁ
			nextPuyo.StartGame2();// myPuyo 안에 잇는 블럭 생성및 정보들을 스타트 하고

			startGame2();// 쓰레드를 시작한다.
		}

		void startGame2()
		{ 
			if (animator2 == null || !running2) {// 안돌고 있을떄... 말한다?
				animator2 = new Thread(this);
				animator2.start();//쓰레드를 시작한다. run  메소드 시작.
			}
		} 
		public void run()//게임의 시작 부분 스레드가 시작과동시
		{

			running2 = true;

			while(running2) {
				gameRender2();   // 버퍼에 게임.  게임판 그려주는에
				paintScreen2();  // 화면 버퍼를 그리


			}
		}
		void gameRender2()//  페널  그림  기려주는게  폭  넓이를 지정해줘서 그림 그려줌.
		{
			if (dbImage2 == null){
				dbImage2 = createImage(PWIDTH2, PHEIGHT2);// 가로 세로 그림// 그림이 나오는 페널.

				return;

			}

			else{
				dbg2 = dbImage2.getGraphics(); 
			}

			dbg2.setColor(Color.black);
			dbg2.fillRect (0,0, PWIDTH2, PHEIGHT2);//전체 페너얼의 위치(보이는 페널)

			nextPuyo.Render2(dbg2);// 그림 그림?
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

			//setPreferredSize()는  Dimention 객체를 인자로 받으며 컴포넌트의 크기를 지정할수 있다.

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
				gameRender();   // 버퍼에 게임.
				paintScreen();  // 화면 버퍼를 그리
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
			JOptionPane.showMessageDialog(null,"  GAME OVER   "+"\n"+" 획득 점수 "+ myPuyo.score+ "점"+ "   시 간 "+stime/100 +"sec",
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

			//배경
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