package client.GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;





class Game_Room extends JFrame //��ü�г� �Դϴ�.

{
	int color_index_blockC_2;
	int color_index_blockD_2;
	int color_index_blockC;
	int color_index_blockD;
	
	interface GameParameters{

		int width=250;
		int height=600;
		int numW = 6; //6�� �� ���� ���α���
		int inBlok = 12;  //�ִ� ���� �ִ� ���� ���α���
		int boom = 4; //4�� �̻��϶� �����
		int radius = 20;//NextView.setBounds(305,10,130,150);
		///////////////////////////////////////////////////
		int nextWH= 130;//�ؽ�Ʈ â ũ��.
		int nestHE= 150;

		Color colors[]={Color.RED,Color.BLUE,Color.GREEN, Color.YELLOW};
		//���� �����̴� ��

		int right=1,left=2,down=3, up=4;

		int right_or = 0, top_or=1,left_or=2, bottom_or=3;
		//B����  B��������     B�� ����      B������         B�� �Ʒ��� 

	}
	
	PuyoPanel_1_1 myPanel_1;        // �гβ�����°�
	NextPanel_1 nextPanel_1;//�̸����� �г�.
	blockCD_1 CD_1=new blockCD_1();
/////////////////////////////////////////////////
	
    
//////////////////////////////////////////////////
	JLabel Point ;
	JLabel textp;
	JLabel time;
	/////////////////////////////////////////
	PuyoPanel_2 myPanel_2;        // �гβ�����°�
	NextPanel_2 nextPanel_2;//�̸����� �г�.
	blockCD_2 CD_2=new blockCD_2();
	JPanel GameRoom=new JPanel();
	/////////////////////////////////////////////////
	void game(){
		makeGui();
		imageNext();
		makeGui_2();
		imageNext_2();
	}
	public Game_Room()//  ��ü �г��� ũ�� �� ����.
	{ 
		super(" �����̱⹫�� ");

		GameRoom.setBounds(20, 20, 920, 690);
		GameRoom.setLayout(null);
		


	}  
	///////////////////////////////////////////////////////////////////////////////////////////
	void makeGui()// ��� Ŭ������  �������� �� ����ؼ�  �׸��� �ִ� �׸���  �� ����� �ִ� �޼ҵ�.
	{
		Container c = getContentPane(); // c  �� �����̳ʶ�  ������ ����� �Z��.   
		//      Container d = getContentPane();
		myPanel_1 = new PuyoPanel_1_1();// ���� ����� ����� ���Ӱ� ���� �� �آZ��.
		myPanel_1.setBounds(5,20,255,600); // ���� ����� ũ��� ���Ǹ� ���� ���־���.

		c.add(myPanel_1, "Center");// �����̳ʷ�  ����� �ٿ� �־��� �����ӿ�.
		//c.add(nextPanel);//�̸� ���� �г��� ��������ϴ�.
		GameRoom.add(myPanel_1);
		GameRoom.add(c);

	} 
	void imageNext()
	{
		Container c = getContentPane();
		nextPanel_1 = new NextPanel_1();
		nextPanel_1.setBounds(305,10,130,150);
		//	   add(nextPanel);
		c.add(nextPanel_1,"Center");//�̸� ���� �г��� ��������ϴ�.
		GameRoom.add(nextPanel_1);
		GameRoom.add(c);
	}
	////////////////////////////////////////////////
	void makeGui_2()// ��� Ŭ������  �������� �� ����ؼ�  �׸��� �ִ� �׸���  �� ����� �ִ� �޼ҵ�.
	{
		Container c = getContentPane(); // c  �� �����̳ʶ�  ������ ����� �Z��.   
		//      Container d = getContentPane();
		myPanel_2 = new PuyoPanel_2();// ���� ����� ����� ���Ӱ� ���� �� �آZ��.
		myPanel_2.setBounds(620,20,255,600); // ���� ����� ũ��� ���Ǹ� ���� ���־���.

		c.add(myPanel_2, "Center");// �����̳ʷ�  ����� �ٿ� �־��� �����ӿ�.
		GameRoom.add(myPanel_2);
		GameRoom.add(c);
	} 
	void imageNext_2()
	{
		Container c = getContentPane();
		nextPanel_2 = new NextPanel_2();
		nextPanel_2.setBounds(470,10,130,150);
		//	   add(nextPanel);
		c.add(nextPanel_2,"Center");//�̸� ���� �г��� ��������ϴ�.
		GameRoom.add(nextPanel_2);
		GameRoom.add(c);
	}
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	class Block{// ���� ���� ���ִ� Ŭ������ �������.,

		int  X,Y;//  x , y  �� �μ�..
		int radius;//  ������./

		Color color;// ���� ������ �ִ� Ŭ����.
		int pipe;    // �������� (��Ʈ��.)
		int pipe2;// ������ ��Ʈ��.

		boolean four;
		///////////////////////////////////////////////////////////////////////////////////////////     
		Block(int X, int Y, int radius, Color color)// ���� �����ڸ� ����� // ���� �׸����� ��ǥ��. ũ�⸦ �������ش�.
		{
			this.X= X;   
			this.Y= Y;
			this.radius = radius;
			this.color  = color;     

			pipe =-1;// �������� �⺻�� -1 �Z��.
			pipe2=-1;//������2  �⺻�� -1 �� �Z��.
			four = false;//������ ������ ���ؼ� ���� ��.
		}


		///////////////////////////////////////////////////////////////////////////////////////////
		public void paint(Graphics g){// ���� �׷��ش�.
			g.setColor(color);
			g.fillOval(X-radius,Y-radius, 2*radius,2*radius);//��ǥ��@ ũ�⸦ ���༭ ���� �׷��ش�.

		}
		///////////////////////////////////////////////////////////////////////////////////////////
		public boolean collideDown_1(Block b)// �浹 �������� �����Ͽ� �����·� ���� �ߴ�.
		{	// 
			// ������ ���� �ִ� ����  ���� ������ ��(�浹 �������� �����·�)�� ��ǥ���� ũ�ٸ�  �� �����·�  �����ش�.

			return Y+radius >= b.Y-radius;
			//���� �����ִ� ���� Y+������  ��      ���� �����̰��ִ� ���� Y+������ ���� ũ�ٸ� .
		}   
		//////////////////////////////////////////////////////////////////////////
		//�����Ǿ� �ִ� ����  ���� �����ϼ��ִ� ������ ������ ���� ���� ���ϰ� �ѱ� ���ؼ�.
		public boolean chkLeft(Block b)
		{// �l�� �ִ� ����  �����̴� ���� ��ǥ����  �������
			return b.X+radius <= Y-radius;
		}
		//////////////////////////////////////////////////////
		//���� �Ǿ� �ִ� ����  �����ϼ� ������ ������ �������� ���� ���ϰ� �ϱ����ؼ�.
		public boolean chkRight(Block b){

			return  b.chkLeft(this);

		}  
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//�������� ����
	//������ �̵�
	class blockPipe{// ���� �������� ����� �Z��.

		//	   = new LinkedList();
		LinkedList blocks;// ��ũ�� ����Ʈ ������ ������ �����.


		public blockPipe(){
			blocks= new LinkedList();// ���Ӱ�  ��ũ�� ����Ʈ�� ���� ���ش�..
		}

		public void paint(Graphics g)//  �׷����� ����ؼ� ����Ʈ�� �Ѵ�.
		{
			Iterator itr= blocks.iterator();
			//���ͷ�����  ���� itr �� �����  = �׾ȿ� �� ������./���ͷ����� ������ �ִ´�.
			while(itr.hasNext())// �����͸� �ٲ��� ����Ѵ�.
			{
				Object element= itr.next();// ������Ʈ ��������   ��ȯ �����ش�.
				Block b = (Block) element;// (�� ����) �����͸�   �� b �� �ִ´�.
				b.paint(g);//  ����Ʈ �Ѵ�.
			}
		}

		public int getSize()// ��Ʈ�� �޼ҵ� ����� �����.
		{
			return blocks.size();// ���ϰ�  ���� . ũ�⸦ �����ش�./
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//����� ¦ a,b 
	class BlockAB_1{  // ���� �׸��� �� A  B �� ������ ���ؼ� ������ Ŭ����.

		Block blockA; // �� Ŭ���� ������ A�� �����
		Block blockB;//  �� Ŭ���� ������ B�� �����.

		int blockB_OR;  //A�� �������� B�� ������ ��


		public void paint(Graphics g){// ���� ������༭ �׷��ִ� ��Ȱ�� ���ش�.
			blockA.paint(g);
			blockB.paint(g);

		}
	}
	///////////////////////////////////////////////////////////////\
	class blockCD_1////???
	{
		Block blockC;
		Block blockD;
		public void paint(Graphics g){// ���� ������༭ �׷��ִ� ��Ȱ�� ���ش�.
			blockC.paint(g);
			blockD.paint(g);

		}

	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////



	public class PuyoGame1_1  implements GameParameters 
	{

		public void startGame1_1_1()
		{
			CD_1=new blockCD_1();
			nextBB_1();
		}
		////////////////////////////////////////////////////////////////////////
		public void nextBB_1()//������ ���� ���� ���� ���ش�.
		{
			Random r = new Random();
			int colorindex = r.nextInt(4);//  �÷��� ����� �������� 4���� ���� �ְ� �������.
			int width=125;
			//NextView.setBounds(305,10,130,150);
			Block blockC= new Block(width/2-25, 20, 20, colors[colorindex]);// ���� ����.
			color_index_blockC=colorindex;
			colorindex= r.nextInt(4);// ���� �ٸ��� �ؾ��ؼ� �ٽ� �÷��� �Z��..

			Block blockD= new Block(width/2+15, 20, 20,  colors[colorindex]);
			color_index_blockD=colorindex;
			CD_1.blockC= blockC;//A���̶�� ���� ����.
			CD_1.blockD =blockD;//B ���̶�� ���� ����.
		}
		public void Render1(Graphics g)// �� �׸�? ��Ȳ��?  ��� �׷��ִ� ��Ȱ../
		{ 
			CD_1.paint(g);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class PuyoGame1   implements GameParameters {//���� ���̽� ���� Ǫ�� ����..
		//nextPanel,PuyoFrametest

		PuyoGame1_1 pg2;
		BlockAB_1  AB_1; // ����ڰ� ���� �����ϼ� �ִ� ¦ ��Ʈ//  ���� A   B  �� ������ �׷��ִ� ��Ȱ�� �Ѵ�.
		//	   BlockMake qq ;
		blockPipe [] pipes_1;//  �������� �迭�� ����. ������ �Ҵ� �Ѵ�.
		LinkedList mBlocks_1;// ��ũ�� ����Ʈ ���� �����͸� ���� ������ ������ش�.
		int score =0;//  �ʱⰪ 0 


		//      boolean chk =true;
		////////////////////////////////////////////////////////////////////////
		public void StartGame_1()//  ������ ��ŸƮ  �޼ҵ�
		{
			AB_1 = new BlockAB_1();//���� ������ִ� Ŭ����  ���Ӱ� ���� ���ش�.

			mBlocks_1 = new LinkedList();// ��ũ�� ����Ʈ�� ���Ӱ� ���� �ߴ�.  �������� �ʴ� ��.! ����� ���� ��.
			pipes_1 = new blockPipe[numW];// ������ �迭��  �ִ� ��� �������� �������� ���Ӱ� �����آZ��.
			// Ŭ������ ���Ӱ� ���Ǹ� ����.
			for (int i = 0; i < numW ; i++) //  6  ���� ���鼭.
				pipes_1[i] =new blockPipe();// ������ ������. 6���� �������� ����� �ְ�.(��)

			firstAB_1();// ù��° �żҵ带 ���� ���̴�.



		}
		////////////////////////////////////////////////////////////////////////
		public void  firstAB_1()// ���� ũ�� �� ��ǥ�� ���� ���ִ� �޼ҵ�.
		{

			Random r = new Random();// �������� �����.
			int left =2;// ��Ʈ�� �� 2��.
			int right = left+1;// ����Ʈ +1    =  ����Ʈ..
			int colorindex = r.nextInt(4);//  �÷��� ����� �������� 4���� ���� �ְ� �������.


			Block blockA_1= new Block(width/2-25, 20, 20, colors[color_index_blockC]);// ���� ����.

			colorindex= r.nextInt(4);// ���� �ٸ��� �ؾ��ؼ� �ٽ� �÷��� �Z��..

			Block blockB_1= new Block(width/2+15, 20, 20, colors[color_index_blockD]);


			blockA_1.pipe =left;// A���� �������� =2
			blockB_1.pipe = right;// B�� �������� 3 ���� ����.

			//AB ����
			AB_1.blockA= blockA_1;//A���̶�� ���� ����.
			AB_1.blockB =blockB_1;//B ���̶�� ���� ����.
			AB_1.blockB_OR= right_or;//��ó�� ������ ������  B���� �׻� A���� ���������� �ϱ����ؼ�.

			new PuyoGame1_1().nextBB_1();
		}

		/////////////////////////////////////////////////////////////////////      
		public boolean Update_1()//�� ���� �޼ҵ�./
		{
			moveMBlocks_1();// ����MB���� ���¤���
			movepair_1();

			boolean gameOver = false; // ������ ��������..  �׳� �������� �ذ�.

			for (int i = 0; (i<numW) && !gameOver ; i++) // 
				gameOver = (pipes_1[i].getSize() > inBlok);
			//


			return gameOver;

		}
		////////////////////////////////////////////////////////////////////////
		public int getScore_1(){
			return score;
		}

		////////////////////////////////////////////////////////////////////////
		public void render_1(Graphics g)// �� �׸�? ��Ȳ��?  ��� �׷��ִ� ��Ȱ../�����带 �����ϴºκ�.
		{
			//         g.setColor(Color.black);

			AB_1.paint(g);//�� �׷��ش�  A,B ��//�����̴� ���̵��� ���� �׷��ش�.

			//         System.out.println(AB.blockA.color+"    ����");
			//         System.out.println(AB.blockB.color);
			for (int i = 0; i < numW; i++) //�����׷���.

				pipes_1[i].paint(g);



			for (int i = 0; i < mBlocks_1.size(); i++) // �����ִ� �� �� �׷���.
			{

				Block bk = (Block) mBlocks_1.get(i);
				bk.paint(g);// ����Ʈ���شٰ���
				//            System.out.println(bk.pipe+"???");

			}



		}

		////////////////////////////////////////////////////////////////////////
		boolean moveBlock_1(Block bk)//���� ���ư��� ���� ������ �´�.
		{

			if (! pipes_1[bk.pipe].blocks.isEmpty())//���� ������S [����� ������].
			{
				if(!bk.collideDown_1((Block) pipes_1[bk.pipe].blocks.getLast()))
				{// ���� ���� �����̴� ���� 
					bk.Y+=1;
					//               System.out.println(bk.pipe +" pipe --1");

					return true;

				}

				else
				{   
					bk.pipe2 = pipes_1[bk.pipe].blocks.size();
					pipes_1[bk.pipe].blocks.add(bk);
					//   System.out.println(bk.pipe +" pipe--2");

					return false;
				}
			}

			if(!bound_1(bk, down)){
				bk.Y+=1;
				//   System.out.println(bk.pipe +" pipe --3");

				return true;
			}



			else 
			{
				//�������� �浹 �ؿ� �浹
				bk.pipe2 = pipes_1[bk.pipe].blocks.size();
				pipes_1[bk.pipe].blocks.addLast(bk);

				//   System.out.println(bk.pipe +" pipe --4");

				return false;
			}

		}

		////////////////////////////////////////////////////////////////////////
		void movepair_1(){//  A,B�� �����϶� ��Ȳ�� �����ش�.//
			//���� ��Ȳ�� ��� ����ϋ�   �Ǵٽ� ���� ���Ӱ� ����� ���  ��� �Ѱ�..  Ű�̺�Ʈ���� �ٸ� �޼ҵ�.
			boolean ba = moveBlock_1(AB_1.blockA);
			boolean bb = moveBlock_1(AB_1.blockB);

			//A���� �������� �ʰ� b�� ���� ������ 
			if((!ba) && (AB_1.blockB_OR==top_or))
			{
				chkColor_1(AB_1.blockA);
				if(AB_1.blockA.color!=AB_1.blockB.color)
					chkColor_1(AB_1.blockB);
				//        	 next.nextBB();
				firstAB_1();//�� ���� �ϴ� �޼ҵ�/


			}

			//B���� �������� �ʰ�  A�� �Ʒ�������
			else if ((!bb) && (AB_1.blockB_OR==bottom_or))
			{

				pipes_1[AB_1.blockB.pipe].blocks.add(AB_1.blockA);
				AB_1.blockA.pipe2= AB_1.blockB.pipe2+1;
				chkColor_1(AB_1.blockB);
				if(AB_1.blockA.color!=AB_1.blockB.color)
					chkColor_1(AB_1.blockA);

				firstAB_1();

			}
			//a�� �����̰� b�� �������� ������
			else if (ba && (!bb) )
			{  
				// a���� mBlock�� �־��
				mBlocks_1.addLast(AB_1.blockA);
				chkColor_1(AB_1.blockB);

				firstAB_1();
			}

			else if( bb && (!ba) )
			{ 
				//        	 System.out.println(AB.blockA.color+"----4_1��");
				//    	 System.out.println(AB.blockB.color+"----4_2��");
				mBlocks_1.addLast(AB_1.blockB);
				chkColor_1(AB_1.blockA);
				firstAB_1();

			}

			//�Ѵ� �����϶� �������� ������
			else if (!(ba && bb))
			{ 
				if(AB_1.blockA.color!=AB_1.blockB.color)
					chkColor_1(AB_1.blockA);
				chkColor_1(AB_1.blockB);

				firstAB_1();
			}


		}
		//��Ʈ�� �Ҽ����� �����̴� ���̵�
		////////////////////////////////////////////////////////////////////////      
		void moveMBlocks_1 ()// �����ִ� ����  �ο��ִ� ���̵�.
		{
			//LinkedList mBlocks
			ListIterator itr =  mBlocks_1.listIterator();
			//��ũ�� ���ͷ����� ���� itr = ��ũ�帮��Ʈ������ �����͸� ��ũ�����ͷ����� �������� �ٲ��ְ�.
			while(itr.hasNext())// ��ȯ ��Ų �ڷḦ ������ while �������� ������ �ͼ� ������.
			{
				Block bk = (Block) itr.next();// ������� ������ ���������� �ٲ۴�.
				//            System.out.println(AB.blockA.color+"    ����");
				if(!moveBlock_1(bk))//�����̴� ��(bk )�� �ƴ϶�� 
				{
					ListIterator litr = itr;

					itr.previous();

					chkColor_1((Block)itr.next());
					itr.previous();
					litr.remove();

					//                  litr.remove();

				}
			}
		}

		////////////////////////////////////////////////////////////////////////     
		public void ProcessKey(KeyEvent e){

			int key = e.getKeyCode();

			switch(key){
			case KeyEvent.VK_LEFT:
				Control(left);

				break;

			case KeyEvent.VK_RIGHT:
				Control(right);

				break;


			case KeyEvent.VK_DOWN:
				Control(down);

				break;

			case KeyEvent.VK_UP:
				Rotate();
				break;

			case KeyEvent.VK_SPACE:
				space();///////////

				break;
			}
		}
		////////////////////////////////////////////////////////////////////////
		boolean bound_1(Block bk,int dir)  //  ����(����) �� �Ҹ���.. ������ ã�ƶ�.
		{
			switch(dir)
			{
			case left :

				return bk.pipe ==0;

			case right :

				return bk.pipe ==5;

			case down :
			{
				return  bk.Y+20 > height;
			}
			//         default :
			}
			return false;
		}
		////////////////////////////////////////////////////////////////////////
		boolean moveRight(Block bk){
			//        bk.pipe++;
			//        bk.X+= 40;chkLeft
			//    LinkedList blocks;// ��ũ�� ����Ʈ ������ ������ �����.
			if(	!pipes_1[bk.pipe+1].blocks.isEmpty())
				if(	bk.collideDown_1((Block)pipes_1[bk.pipe+1].blocks.getLast()))
					if(bk.chkLeft((Block)pipes_1[bk.pipe+1].blocks.getLast()))	  
						return false;
			bk.pipe++;
			bk.X+=2*radius;

			return true;
		}
		////////////////////////////////////////////////////////////////////////
		boolean moveLeft(Block bk){
			if(	!pipes_1[bk.pipe-1].blocks.isEmpty())
				if(	bk.collideDown_1((Block)pipes_1[bk.pipe-1].blocks.getLast()))
					if(bk.chkLeft((Block)pipes_1[bk.pipe-1].blocks.getLast()))	  
						return false;

			bk.pipe--;
			bk.X-=2*radius;

			return true;

		}
		////////////////////////////////////////////////////////////      
		//boolean MoveBlock2(Block bk){ 
		boolean MoveBlock2(Block bk){//���⼭ ���ͽ���

			if (! pipes_1[bk.pipe].blocks.isEmpty()) //�ؿ� ���� ������ 
			{
				if(!bk.collideDown_1((Block) pipes_1[bk.pipe].blocks.getLast()))
				{
					bk.Y+=1;
					//	System.out.println(bk.pipe +" �عٴ� ");
					//		System.out.println(bk.pipe2+" ���� ");
					return true;

				}

				else
				{	
					bk.pipe2 = pipes_1[bk.pipe].blocks.size(); 
					pipes_1[bk.pipe].blocks.add(bk);
					//		System.out.println(bk.pipe +" �عٴ� ");
					//		System.out.println(bk.pipe2+" ���� ");
					return false;
				}


			}

			if(!bound_1(bk, down)){
				bk.Y+=1;
				//	System.out.println(bk.pipe +" pipe --3");

				return true;
			}



			else 
			{
				//�������� �浹 �ؿ� �浹
				bk.pipe2 = pipes_1[bk.pipe].blocks.size();
				pipes_1[bk.pipe].blocks.addLast(bk);

				//	System.out.println(bk.pipe +" pipe --4");

				return false;
			}

		}

		public void space()
		{

			boolean ba = MoveBlock2(AB_1.blockA);
			boolean bb = MoveBlock2(AB_1.blockB);

			int limit =  height-20;

			if((!ba) && (AB_1.blockB_OR==top_or))
			{
				chkColor_1(AB_1.blockA);

				if(AB_1.blockA.color != AB_1.blockB.color)
					chkColor_1(AB_1.blockB);

				firstAB_1();
			}

			//B���� �������� �ʰ�  B�� �Ʒ�������
			else if ((!bb) && (AB_1.blockB_OR==bottom_or))
			{
				pipes_1[AB_1.blockB.pipe].blocks.add(AB_1.blockA);
				AB_1.blockA.pipe2= AB_1.blockB.pipe2+1;

				chkColor_1(AB_1.blockB);

				if(AB_1.blockA.color != AB_1.blockB.color)
					chkColor_1(AB_1.blockA);


				firstAB_1();

			}
			//a�� �����̰� b�� �������� ������
			else if (ba && (!bb) )
			{
				// a���� mBlock�� �־��
				mBlocks_1.addLast(AB_1.blockA);

				chkColor_1(AB_1.blockB);

				firstAB_1();
			}

			else if( bb && (!ba) )
			{
				mBlocks_1.addLast(AB_1.blockB);

				chkColor_1(AB_1.blockA);
				firstAB_1();

			}

			//�Ѵ� �����϶� �������� ������
			else if (!(ba && bb))
			{
				if(AB_1.blockA.color!=AB_1.blockB.color)
					chkColor_1(AB_1.blockA);
				chkColor_1(AB_1.blockB);
				firstAB_1();
			}


			if(!bound_1(AB_1.blockA, down))
			{

				AB_1.blockA.Y= limit- pipes_1[AB_1.blockA.pipe].getSize()*40 ;
				AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 
			}

			if(!bound_1(AB_1.blockB, down))
			{

				AB_1.blockA.Y= limit- pipes_1[AB_1.blockA.pipe].getSize()*40 ;
				AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 

			}
			int i = AB_1.blockB_OR;

			 switch(i)
	         {

	         case right_or:
	         {
	        	 AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
	        	 AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 


	        	 break;

	         }

	         case top_or:
	         {
	        	 if(AB_1.blockA.pipe>0 && AB_1.blockB.pipe > 0)
	        	 {
	        		 AB_1.blockB.Y =AB_1.blockA.Y;
	        		    AB_1.blockB.X= AB_1.blockA.X -40;
	        		 AB_1.blockB_OR=left_or; 
	        		 AB_1.blockB.pipe--;

	        		 AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
	        		 AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 

	        	 }


	        	 break;
	         }
	         case left_or:
	         {
	        	 /*if(Bound(AB.blockB, DOWN))
	               break;
	        	  */

	        	 AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
	        	 AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 

	        	 break;

	         }
	         case bottom_or:
	         {
	        	 if(AB_1.blockA.pipe>0 && AB_1.blockB.pipe > 0){

	        		    AB_1.blockB_OR =right_or;
	        		    AB_1.blockB.Y= AB_1.blockA.Y;
	        		       AB_1.blockB.X = AB_1.blockA.X +40;
	        		    AB_1.blockB.pipe2++;
	        		 AB_1.blockA.Y= limit - pipes_1[AB_1.blockA.pipe].getSize()*40 ;
	        		 AB_1.blockB.Y= limit - pipes_1[AB_1.blockB.pipe].getSize()*40 ; 

	        	 }

	        	 break;
	         }
	         }   
		}
		//�������.
		////////////////////////////////////////////////////////////////////////
		public void Control(int dir){//����(����)
			switch(dir)
			{
			case  down :

			{
				switch(AB_1.blockB_OR){

				case top_or:

					if(!bound_1(AB_1.blockA,down))
					{
						AB_1.blockA.Y+=20;
						AB_1.blockB.Y+=20;
					}
					break;

				default:
					if(!bound_1(AB_1.blockB,down))
					{
						AB_1.blockA.Y+=20;
						AB_1.blockB.Y+=20;
					}
					break;

				}
				break;

			}
			////////////////////////////////////////////////////////////////
			case left :
			{

				switch(AB_1.blockB_OR)
				{
				case right_or:
				{
					if(!bound_1(AB_1.blockA,left))
						if(moveLeft(AB_1.blockA))
							moveLeft(AB_1.blockB);
					break;
				}

				case top_or :
				{
					if(!bound_1(AB_1.blockA, left))
						if(moveLeft(AB_1.blockA))
							moveLeft(AB_1.blockB);
					break;
				}

				case left_or :
				{
					if(!bound_1(AB_1.blockB, left))
						if(moveLeft(AB_1.blockB))
							moveLeft(AB_1.blockA);
					break;
				}

				case bottom_or:
				{
					if(!bound_1(AB_1.blockA, left))
						if(moveLeft(AB_1.blockB))
							moveLeft(AB_1.blockA);
					break;
				}
				}
				break;

			}
			case right :
			{
				switch(AB_1.blockB_OR)
				{
				case right_or:
				{
					if(!bound_1(AB_1.blockB, right))
						if(moveRight(AB_1.blockB))
							moveRight(AB_1.blockA);
					break;
				}
				case top_or:
				{
					if(!bound_1(AB_1.blockB, right))
						if(moveRight(AB_1.blockA))
							moveRight(AB_1.blockB);
					break;
				}
				case left_or:
				{
					if(!bound_1(AB_1.blockA, right))
						if(moveRight(AB_1.blockA))
							moveRight(AB_1.blockB);
					break;

				}

				case bottom_or:
				{
					if(!bound_1(AB_1.blockB,right ))
						if(moveRight(AB_1.blockB))
							moveRight(AB_1.blockA);
					break;
				}

				}
				break;
			}
			}
		}
		////////////////////////////////////////////////////////////////////////
		public void Rotate(){//ȸ��

			int i = AB_1.blockB_OR;


			switch(i)
			{

			case right_or:
			{
				AB_1.blockB_OR=top_or;
				AB_1.blockB.X=AB_1.blockA.X;
				AB_1.blockB.Y=AB_1.blockA.Y -40;
				AB_1.blockB.pipe = AB_1.blockA.pipe;

				break;

			}

			case top_or:
			{// ȸ���� ���� �� �����Ǿ� �ִ� ������ ���� �� �������ؼ�.�����̴� ���� ������ �������� ����. 
				if(AB_1.blockB.pipe>0)
				{
					boolean downchk = true;//�ʱⰪ ����.
					if(!pipes_1[AB_1.blockA.pipe-1].blocks.isEmpty())
					{//ȸ���� ���� �� ������ ���� äũ�ؼ� ���� ���ϰ��Ұ��̴�.
						if(AB_1.blockA.collideDown_1((Block)pipes_1[AB_1.blockA.pipe-1].blocks.getLast()))
						{
							if(AB_1.blockA.chkLeft((Block)pipes_1[AB_1.blockA.pipe-1].blocks.getLast())){
								downchk=false;
							}
						}
					}


					if(downchk){// ����� ������ ȸ���� �����ϴ�.
						AB_1.blockB.Y =AB_1.blockA.Y;
						AB_1.blockB.X= AB_1.blockA.X -40;
						AB_1.blockB_OR=left_or;
						AB_1.blockB.pipe--;
					}
				}


				break;
			}
			case left_or:
			{	 //���� �츮���� ȸ������ ���� �ε�ĥ ���̴�.

				if(bound_1(AB_1.blockB, down))
					break;

				//            //�����̴� ���� ���ʿ� �ְ� ������ ������ �مf�� �� �ؿ� �����ִ� ���� �ִٸ� ��ġ�� ���ϰ���/
				//            if(!pipes[AB.blockA.pipe].blocks.isEmpty())
				//            	 break;

				AB_1.blockB_OR =bottom_or;
				AB_1.blockB.Y = AB_1.blockA.Y +40;
				AB_1.blockB.X = AB_1.blockA.X;
				AB_1.blockB.pipe = AB_1.blockA.pipe;
				break;

			}
			case bottom_or:// �����̴� ��ü�� �ؿ� �ְ�  up�� �������� ������ ���� �ö󰡴� ��Ȳ.
			{//��ü�� �����ʿ� ������  �������� ���ϰ� �����ֱ����ؼ�
				if(AB_1.blockB.pipe < 5)
				{
					boolean downchk = true;//�ʱⰪ ����.
					if(!pipes_1[AB_1.blockB.pipe+1].blocks.isEmpty())
					{//ȸ���� ���� �� ������ ���� äũ�ؼ� ���� ���ϰ��Ұ��̴�.
						if(AB_1.blockB.collideDown_1((Block)pipes_1[AB_1.blockB.pipe+1].blocks.getLast()))
						{
							if(AB_1.blockB.chkLeft((Block)pipes_1[AB_1.blockB.pipe+1].blocks.getLast())){
								downchk=false;
							}
						}
					}



					if(downchk)//true �� �׳� ����,
					{
						AB_1.blockB_OR =right_or;
						AB_1.blockB.Y= AB_1.blockA.Y;
						AB_1.blockB.X =AB_1.blockA.X +40;
						AB_1.blockB.pipe++;
					}
				}

				break;
			}
			}   
		}
		/////////////////////////////////////////////////////////////////////
		//����� ������ ���� �ִ� ��Ȯ�� �� ��Ʈ���� ���ؼ�  ������

		void chkColor_1(Block bk)//��������. 1��.
		{
			LinkedList finshbk = new LinkedList<>();
			LinkedList list = new LinkedList<>();

			finshbk.add(bk);//���� �� ����Ʈ�� ����������, ������ ��Ҹ� �߰��մϴ�
			bk.four= true;//  �����·� ��������� Ȯ���Ϸ���. ���·� �޴´�

			Iterator itr = finshbk.iterator();//���ͷ����� ���� �������� ���� ������ �ð��̴�

			while(itr.hasNext())// ���� ���� �����鼭  Ȯ���� �Ұ��̴�.
			{
				Object itrOB = itr.next(); //������Ʈ �������� ��ȯ �ؼ��������̴�.
				Block block =(Block) itrOB;// ���������� �ٽ� �ٲ۴�. 
				LinkedList friend= friends_1(block);//�ؿ���  �������� ���� ��ũ�� ����Ʈ�� �޴´�.

				for (int i = 0; i < friend.size(); i++) // �װ�����ŭ  FOR���� ������.
				{
					Block friend_1= (Block)friend.get(i);
					//�� ������ ���޴´� =     ���� �������� �޾Ƽ� �ٲ��ش�.
					if((!friend_1.four)&&(friend_1.color==block.color))
					{// ����. �������ǰ���  ������ �ƴϰ�  &&  ���� ���ٸ�.
						finshbk.add(friend_1);// �׺귰�� �������� �־��ش�.
						friend_1.four=true;// �װ��� ���� �̴�.
					}
				}
				list.add(finshbk.remove());//�� ����Ʈ�κ��� ������ ��Ҹ� ������ �����ݴϴ�.
				itr=finshbk.iterator();
				//	iterator ()
				//�� ����Ʈ���� ��Ҹ�(������ ������) �ݺ� ó���ϴ� ���׷���Ÿ�� �����ݴϴ�.
			}
			/////////////////////////////////////
			if(list.size() >= boom)// ������ ���� ����   4���� ũ�ٸ�. 
				for (int i = 0; i < list.size(); i++) 
				{
					Object OBJ =list.get(i);//�װͿ� �����͸� ������Ʈ�� �ٲٰ�.
					Block block =(Block)OBJ;// �����·� ����ȯ.
					eraseBB_1(block);// �׺����� ����� �޼ҵ�� ����.
				}
			else
				for (int i = 0; i < list.size(); i++) 
				{
					Object OB = list.get(i);
					Block block=(Block)OB;
					block.four=false;
				}

		}

		//2��
		LinkedList friends_1(Block block)//ģ�� �ֺ��� ���� �ִ��� Ȯ��.
		{//��ũ�� ����Ʈ�������� ���� �ٽ� ���� �Ұ��̴�.
			LinkedList friend = new LinkedList();
			//    	  pipe =-1;// �������� �⺻�� -1 �Z��.
			//          pipe2=-1;/
			int frpipe = block.pipe;
			int frpipe2 = block.pipe2;

			if(block.pipe>0)//2_1��//�� ���� ������ ���̰� 0���� Ŭ��.
				if(pipes_1[frpipe-1].blocks.size()>frpipe2)
					//������ �迭[������ ����-1]�� ������ ����� >  ������ ������ ������ ���̺��� Ŭ��.
					friend.add(pipes_1[frpipe-1].blocks.get(frpipe2));
			//        �������̴�  .  ->  ������ �迭��[����������������-1]�鸦.(���� ���� ���α��̷� )�� ����Ʈ���� ������ ��ġ�� �ִ� ��Ҹ� �����ݴϴ�.

			///////////////////////////////////////////////////////
			if(block.pipe<numW-1 )//2_1��// ���� �ķ��� ���̰�.  < 6-1 (5)
				if(pipes_1[frpipe+1].blocks.size() > frpipe2)
					//���� ����������[���������α���+1].������ �����  >  ������ ���� ������ ���̺���
					friend.add(pipes_1[frpipe+1].blocks.get(frpipe2));
			////////////////////////////////////////////////////
			if(pipes_1[frpipe].blocks.size()>frpipe2+1)//2_3��.
				//�������迭[������������ ���� ������]������ ����� > ����+1 ���� ������.
				friend.add(pipes_1[frpipe].blocks.get(frpipe2+1));
			//////////////////////////////////////////////
			if(frpipe2-1>-1)//�� ����Ʈ�� ����������, ������ ��Ҹ� �߰��մϴ�.  addLast
				friend.add(pipes_1[frpipe].blocks.get(frpipe2-1));

			return friend;
		}
		//3��
		void eraseBB_1(Block block)
		{
			for (int i = block.pipe2+1; i <pipes_1[block.pipe].blocks.size(); i++) 
			{
				Object OB = pipes_1[block.pipe].blocks.get(i);
				Block block_2 = (Block) OB;
				block_2.pipe2--;
				block_2.Y+= 2*radius;
			}
			pipes_1[block.pipe].blocks.remove(block.pipe2);
			score++;
		}


	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	class NextPanel_1 extends JPanel implements Runnable,GameParameters
	{

		int PWIDTH1 = nextWH;
		int PHEIGHT1 = nextWH;

		Thread animator1_1;// �����带 ������ ���ؼ�.

		boolean running1_1 = false;   // �����·� �Z��

		Game_Room topFrame1_1;// ��ü �гο��� ������ ��� �ϱ� ���ؼ�.

		PuyoGame1_1 nextPuyo1_1;//  �ҷ��� ���. �ϰٴ�/

		Graphics dbg1_1; 
		Image dbImage1_1 = null;

		public NextPanel_1() 
		{
			setBackground(Color.white);

			setPreferredSize( new Dimension(PWIDTH1, PHEIGHT1));// ��� ������ �����ش�.
			//setBounds(20, 30, 150, 150);
			//	       setFocusable(true);// �ּ� Ǯ�� �ȿ����δ�/

			nextPuyo1_1= new PuyoGame1_1();// ���Ӱ� ���� ���ְ�.     ������    ������
			nextPuyo1_1.startGame1_1_1();// myPuyo �ȿ� �մ� �� ������ �������� ��ŸƮ �ϰ�
			startGame1_1();// �����带 �����Ѵ�.
		}

		void startGame1_1()
		{ 
			if (animator1_1 == null || !running1_1) {// �ȵ��� ������... ���Ѵ�?
				animator1_1 = new Thread(this);
				animator1_1.start();//�����带 �����Ѵ�. run  �޼ҵ� ����.
			}
		} 
		public void run()//������ ���� �κ� �����尡 ���۰�����
		{

			running1_1 = true;

			while(running1_1) {
				gameRender1_1();   // ���ۿ� ����.  ������ �׷��ִ¿�
				paintScreen1_1();  // ȭ�� ���۸� �׸�


			}
		}
		void gameRender1_1()//  ���  �׸�  ����ִ°�  ��  ���̸� �������༭ �׸� �׷���.
		{
			if (dbImage1_1 == null){
				dbImage1_1 = createImage(PWIDTH1, PHEIGHT1);// ���� ���� �׸�// �׸��� ������ ���.

				return;

			}

			else{
				dbg1_1 = dbImage1_1.getGraphics(); 
			}

			dbg1_1.setColor(Color.black);
			dbg1_1.fillRect (0,0, PWIDTH1, PHEIGHT1);//��ü ��ʾ��� ��ġ(���̴� ���)

			nextPuyo1_1.Render1(dbg1_1);// �׸� �׸�?
		}  

		void paintScreen1_1() 
		{ 
			Graphics g;
			try {
				g = this.getGraphics();
				if ((g != null) && (dbImage1_1 != null))
					g.drawImage(dbImage1_1, 0, 0, null);
				g.dispose();
			}
			catch (Exception e)
			{ 

			}
		} 



	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	class PuyoPanel_1_1 extends JPanel implements Runnable, GameParameters
	{	// �г��� �ް�   �гο�  ���ʺ� @  �׷��� ���..
		////////////////////////////////////////////////////////////////////////
		int PWIDTH_1 = width; // ���� 
		int PHEIGHT_1 = height; //����

		Thread animator1_2;// �����带 ������ ���ؼ�.
		int stime=0;
		boolean running = false;   // �����·� �Z��
		boolean gameOver = false;//////////////////
		Game_Room topFrame1_2;// ��ü �гο��� ������ ��� �ϱ� ���ؼ�.

		PuyoGame1 myPuyo1_2;//  �ҷ��� ���. �ϰٴ�/

		int score = 0;

		Graphics dbg1_2; 
		Image dbImage1_2 = null;

		////////////////////////////////////////////////////////////////////////
		public PuyoPanel_1_1()
		{
			setBackground(Color.red);// �ѿ� ��λ�

			setPreferredSize( new Dimension(PWIDTH_1, PHEIGHT_1));// ��� ������ �����ش�.
			//setBounds(20, 30, 150, 150);
			setFocusable(true);


			myPuyo1_2= new PuyoGame1();// ���Ӱ� ���� ���ְ�.
			myPuyo1_2.StartGame_1();// myPuyo �ȿ� �մ� �� ������ �������� ��ŸƮ �ϰ�//
			startGame();// �����带 �����Ѵ�.

			addKeyListener( new KeyAdapter() {

				public void keyPressed(KeyEvent e)
				{ 
					myKeyPressed(e); }
			});
		}  
		////////////////////////////////////////////////////////////////////////
		void myKeyPressed(KeyEvent e)
		{ 

			myPuyo1_2.ProcessKey(e);
		}
		////////////////////////////////////////////////////////////////////////
		void startGame()
		{ 
			if (animator1_2 == null || !running) {// �ȵ��� ������... ���Ѵ�?
				animator1_2 = new Thread(this);
				animator1_2.start();//�����带 �����Ѵ�. run  �޼ҵ� ����.
			}
		} 
		////////////////////////////////////////////////////////////////////////
		public void run()//������ ���� �κ� �����尡 ���۰�����
		{

			running = true;
			int maxtime=8000;///////////



			while(running) {
				gameUpdate(); //�� ������ ����? �޼ҵ�.
				gameRender();   // ���ۿ� ����.  ������ �׷��ִ¿�
				paintScreen();  // ȭ�� ���۸� �׸�

				if(myPuyo1_2.Update_1()){
					showMessage();
					break;
				}

				if(stime == maxtime){
					showMessage();

					break;
				}


				try {
					Thread.sleep(20);
					time.setText("TIME : "+ stime/100+"sec");

				} catch (Exception e) {

				}
			}
		}
		//////////////////////////////////////////////////////////
		void showMessage() {


			JOptionPane.showMessageDialog(null,"  GAME OVER   "+"\n"+" ȹ�� ���� "+ myPuyo1_2.score+ "��"+ "   �� �� "+stime/100 +"sec",
					"M",JOptionPane.WARNING_MESSAGE);

		}
		////////////////////////////////////////////////////////////////////////
		void gameUpdate() 
		{ 
			myPuyo1_2.Update_1();// �̰� �ּ� �ϸ� �����̴� ��Ȱ.

		}  
		///////////////////////////////////////////////////////////////////////
		void gameRender()//  ���  �׸�  ����ִ°�  ��  ���̸� �������༭ �׸� �׷���.
		{
			if (dbImage1_2 == null){
				dbImage1_2 = createImage(PWIDTH_1, PHEIGHT_1);// ���� ���� �׸�// �׸��� ������ ���.

				return;

			}

			else{
				dbg1_2 = dbImage1_2.getGraphics(); 
			}

			//���
			dbg1_2.setColor(Color.darkGray);
			dbg1_2.fillRect (0,0, PWIDTH_1, PHEIGHT_1);//��ü ��ʾ��� ��ġ(���̴� ���)

			myPuyo1_2.render_1(dbg1_2);// �׸� �׸�?


		}  
		//    gameRender()     paintScreen()  �ΰ��� ��Ʈ ���Ѵ� ������,,,����,  
		void paintScreen()  // ��Ȳ ����  ��ũ���� �׷��ִ� ��Ȱ�� �ϴ¸޼ҵ�.
		{ 
			Graphics g;
			try {
				g = this.getGraphics();
				if ((g != null) && (dbImage1_2 != null))
					g.drawImage(dbImage1_2, 0, 0, null);
				//Image img, int x, int y,
				//ImageObserver observer);
				g.dispose();
			}
			catch (Exception e)
			{ 

			}
		} 

	}
	////////////////////////////////////////////////////////////////////////////
	// ������� Ȯ���� ������ Ŭ������ ������ �޿���~~~
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	class Block_2{// ���� ���� ���ִ� Ŭ������ �������.,

		int  X,Y;//  x , y  �� �μ�..
		int radius;//  ������./

		Color color;// ���� ������ �ִ� Ŭ����.
		int pipe;    // �������� (��Ʈ��.)
		int pipe2;// ������ ��Ʈ��.

		boolean four;
		///////////////////////////////////////////////////////////////////////////////////////////     
		Block_2(int X, int Y, int radius, Color color)// ���� �����ڸ� ����� // ���� �׸����� ��ǥ��. ũ�⸦ �������ش�.
		{
			this.X= X;   
			this.Y= Y;
			this.radius = radius;
			this.color  = color;     

			pipe =-1;// �������� �⺻�� -1 �Z��.
			pipe2=-1;//������2  �⺻�� -1 �� �Z��.
			four = false;//������ ������ ���ؼ� ���� ��.
		}


		///////////////////////////////////////////////////////////////////////////////////////////
		public void paint(Graphics g){// ���� �׷��ش�.
			g.setColor(color);
			g.fillOval(X-radius,Y-radius, 2*radius,2*radius);//��ǥ��@ ũ�⸦ ���༭ ���� �׷��ش�.

		}
		///////////////////////////////////////////////////////////////////////////////////////////
		public boolean collideDown_2(Block_2 b)// �浹 �������� �����Ͽ� �����·� ���� �ߴ�.
		{	// 
			// ������ ���� �ִ� ����  ���� ������ ��(�浹 �������� �����·�)�� ��ǥ���� ũ�ٸ�  �� �����·�  �����ش�.

			return Y+radius >= b.Y-radius;
			//���� �����ִ� ���� Y+������  ��      ���� �����̰��ִ� ���� Y+������ ���� ũ�ٸ� .
		}   
		//////////////////////////////////////////////////////////////////////////
		//�����Ǿ� �ִ� ����  ���� �����ϼ��ִ� ������ ������ ���� ���� ���ϰ� �ѱ� ���ؼ�.
		public boolean chkLeft_2(Block_2 b)
		{// �l�� �ִ� ����  �����̴� ���� ��ǥ����  �������
			return b.X+radius <= Y-radius;
		}
		//////////////////////////////////////////////////////
		//���� �Ǿ� �ִ� ����  �����ϼ� ������ ������ �������� ���� ���ϰ� �ϱ����ؼ�.
		public boolean chkRight(Block_2 b){

			return  b.chkLeft_2(this);

		}  
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//�������� ����
	//������ �̵�
	class blockPipe_2{// ���� �������� ����� �Z��.

		//	   = new LinkedList();
		LinkedList blocks;// ��ũ�� ����Ʈ ������ ������ �����.


		public blockPipe_2(){
			blocks= new LinkedList();// ���Ӱ�  ��ũ�� ����Ʈ�� ���� ���ش�..
		}

		public void paint(Graphics g)//  �׷����� ����ؼ� ����Ʈ�� �Ѵ�.
		{
			Iterator itr= blocks.iterator();
			//���ͷ�����  ���� itr �� �����  = �׾ȿ� �� ������./���ͷ����� ������ �ִ´�.
			while(itr.hasNext())// �����͸� �ٲ��� ����Ѵ�.
			{
				Object element= itr.next();// ������Ʈ ��������   ��ȯ �����ش�.
				Block_2 b = (Block_2) element;// (�� ����) �����͸�   �� b �� �ִ´�.
				b.paint(g);//  ����Ʈ �Ѵ�.
			}
		}

		public int getSize()// ��Ʈ�� �޼ҵ� ����� �����.
		{
			return blocks.size();// ���ϰ�  ���� . ũ�⸦ �����ش�./
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//����� ¦ a,b 
	class BlockAB_2{  // ���� �׸��� �� A  B �� ������ ���ؼ� ������ Ŭ����.

		Block_2 blockA; // �� Ŭ���� ������ A�� �����
		Block_2 blockB;//  �� Ŭ���� ������ B�� �����.

		int blockB_OR;  //A�� �������� B�� ������ ��


		public void paint(Graphics g){// ���� ������༭ �׷��ִ� ��Ȱ�� ���ش�.
			blockA.paint(g);
			blockB.paint(g);

		}
	}
	///////////////////////////////////////////////////////////////\
	class blockCD_2////???
	{
		Block_2 blockC;
		Block_2 blockD;
		public void paint(Graphics g){// ���� ������༭ �׷��ִ� ��Ȱ�� ���ش�.
			blockC.paint(g);
			blockD.paint(g);

		}

	}

	public class PuyoGame2_1  implements GameParameters 
	{

		public void startGame2_1_1()
		{
			CD_2=new blockCD_2();
			nextBB_2();
		}
		////////////////////////////////////////////////////////////////////////
		public void nextBB_2()//������ ���� ���� ���� ���ش�.
		{
			Random r = new Random();
			int colorindex = r.nextInt(4);//  �÷��� ����� �������� 4���� ���� �ְ� �������.
			int width=125;
			//NextView.setBounds(305,10,130,150);
			Block_2 blockC= new Block_2(width/2-25, 20, 20, colors[colorindex]);// ���� ����.
			color_index_blockC_2=colorindex;
			colorindex= r.nextInt(4);// ���� �ٸ��� �ؾ��ؼ� �ٽ� �÷��� �Z��..

			Block_2 blockD= new Block_2(width/2+15, 20, 20,  colors[colorindex]);
			color_index_blockD_2=colorindex;
			CD_2.blockC= blockC;//A���̶�� ���� ����.
			CD_2.blockD =blockD;//B ���̶�� ���� ����.
		}
		public void Render2(Graphics g)// �� �׸�? ��Ȳ��?  ��� �׷��ִ� ��Ȱ../
		{ 
			CD_2.paint(g);
		}
	}



	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public class PuyoGame2   implements GameParameters {//���� ���̽� ���� Ǫ�� ����..
		//nextPanel,PuyoFrametest

		PuyoGame2_1 pg2;
		BlockAB_2  AB_2; // ����ڰ� ���� �����ϼ� �ִ� ¦ ��Ʈ//  ���� A   B  �� ������ �׷��ִ� ��Ȱ�� �Ѵ�.
		//	   BlockMake qq ;
		blockPipe_2 [] pipes_2;//  �������� �迭�� ����. ������ �Ҵ� �Ѵ�.
		LinkedList mBlocks_2;// ��ũ�� ����Ʈ ���� �����͸� ���� ������ ������ش�.
		int score =0;//  �ʱⰪ 0 


		//      boolean chk =true;
		////////////////////////////////////////////////////////////////////////
		public void StartGame_2()//  ������ ��ŸƮ  �޼ҵ�
		{
			AB_2 = new BlockAB_2();//���� ������ִ� Ŭ����  ���Ӱ� ���� ���ش�.

			mBlocks_2 = new LinkedList();// ��ũ�� ����Ʈ�� ���Ӱ� ���� �ߴ�.  �������� �ʴ� ��.! ����� ���� ��.
			pipes_2 = new blockPipe_2[numW];// ������ �迭��  �ִ� ��� �������� �������� ���Ӱ� �����آZ��.
			// Ŭ������ ���Ӱ� ���Ǹ� ����.
			for (int i = 0; i < numW ; i++) //  6  ���� ���鼭.
				pipes_2[i] =new blockPipe_2();// ������ ������. 6���� �������� ����� �ְ�.(��)

			firstAB_2();// ù��° �żҵ带 ���� ���̴�.



		}
		////////////////////////////////////////////////////////////////////////
		public void  firstAB_2()// ���� ũ�� �� ��ǥ�� ���� ���ִ� �޼ҵ�.
		{

			Random r = new Random();// �������� �����.
			int left =2;// ��Ʈ�� �� 2��.
			int right = left+1;// ����Ʈ +1    =  ����Ʈ..
			int colorindex = r.nextInt(4);//  �÷��� ����� �������� 4���� ���� �ְ� �������.


			Block_2 blockA_2= new Block_2(width/2-25, 20, 20, colors[color_index_blockC_2]);// ���� ����.

			colorindex= r.nextInt(4);// ���� �ٸ��� �ؾ��ؼ� �ٽ� �÷��� �Z��..

			Block_2 blockB_2= new Block_2(width/2+15, 20, 20, colors[color_index_blockD_2]);


			blockA_2.pipe =left;// A���� �������� =2
			blockB_2.pipe = right;// B�� �������� 3 ���� ����.

			//AB ����
			AB_2.blockA= blockA_2;//A���̶�� ���� ����.
			AB_2.blockB =blockB_2;//B ���̶�� ���� ����.


			new PuyoGame2_1().nextBB_2();
		}

		/////////////////////////////////////////////////////////////////////      
		public boolean Update_2()//�� ���� �޼ҵ�./
		{
			moveMBlocks_2();// ����MB���� ���¤���
			movepair_2();

			boolean gameOver = false; // ������ ��������..  �׳� �������� �ذ�.

			for (int i = 0; (i<numW) && !gameOver ; i++) // 
				gameOver = (pipes_2[i].getSize() > inBlok);
			//


			return gameOver;

		}
		////////////////////////////////////////////////////////////////////////
		public int getScore_2(){
			return score;
		}

		////////////////////////////////////////////////////////////////////////
		public void render_2(Graphics g)// �� �׸�? ��Ȳ��?  ��� �׷��ִ� ��Ȱ../�����带 �����ϴºκ�.
		{
			//         g.setColor(Color.black);

			AB_2.paint(g);//�� �׷��ش�  A,B ��//�����̴� ���̵��� ���� �׷��ش�.

			//         System.out.println(AB.blockA.color+"    ����");
			//         System.out.println(AB.blockB.color);
			for (int i = 0; i < numW; i++) //�����׷���.

				pipes_2[i].paint(g);



			for (int i = 0; i < mBlocks_2.size(); i++) // �����ִ� �� �� �׷���.
			{

				Block_2 bk = (Block_2) mBlocks_2.get(i);
				bk.paint(g);// ����Ʈ���شٰ���
				//            System.out.println(bk.pipe+"???");

			}



		}

		////////////////////////////////////////////////////////////////////////
		boolean moveBlock_2(Block_2 bk)//���� ���ư��� ���� ������ �´�.
		{

			if (! pipes_2[bk.pipe].blocks.isEmpty())//���� ������S [����� ������].
			{
				if(!bk.collideDown_2((Block_2) pipes_2[bk.pipe].blocks.getLast()))
				{// ���� ���� �����̴� ���� 
					bk.Y+=1;
					//               System.out.println(bk.pipe +" pipe --1");

					return true;

				}

				else
				{   
					bk.pipe2 = pipes_2[bk.pipe].blocks.size();
					pipes_2[bk.pipe].blocks.add(bk);
					//   System.out.println(bk.pipe +" pipe--2");

					return false;
				}
			}

			if(!bound_2(bk, down)){
				bk.Y+=1;
				//   System.out.println(bk.pipe +" pipe --3");

				return true;
			}



			else 
			{
				//�������� �浹 �ؿ� �浹
				bk.pipe2 = pipes_2[bk.pipe].blocks.size();
				pipes_2[bk.pipe].blocks.addLast(bk);

				//   System.out.println(bk.pipe +" pipe --4");

				return false;
			}

		}

		////////////////////////////////////////////////////////////////////////
		void movepair_2(){//  A,B�� �����϶� ��Ȳ�� �����ش�.//
			//���� ��Ȳ�� ��� ����ϋ�   �Ǵٽ� ���� ���Ӱ� ����� ���  ��� �Ѱ�..  Ű�̺�Ʈ���� �ٸ� �޼ҵ�.
			boolean ba = moveBlock_2(AB_2.blockA);
			boolean bb = moveBlock_2(AB_2.blockB);

			//A���� �������� �ʰ� b�� ���� ������ 
			if((!ba) && (AB_2.blockB_OR==top_or))
			{
				chkColor_2(AB_2.blockA);
				if(AB_2.blockA.color!=AB_2.blockB.color)
					chkColor_2(AB_2.blockB);
				//        	 next.nextBB();
				firstAB_2();//�� ���� �ϴ� �޼ҵ�/


			}

			//B���� �������� �ʰ�  A�� �Ʒ�������
			else if ((!bb) && (AB_2.blockB_OR==bottom_or))
			{

				pipes_2[AB_2.blockB.pipe].blocks.add(AB_2.blockA);
				AB_2.blockA.pipe2= AB_2.blockB.pipe2+1;
				chkColor_2(AB_2.blockB);
				if(AB_2.blockA.color!=AB_2.blockB.color)
					chkColor_2(AB_2.blockA);

				firstAB_2();

			}
			//a�� �����̰� b�� �������� ������
			else if (ba && (!bb) )
			{  
				// a���� mBlock�� �־��
				mBlocks_2.addLast(AB_2.blockA);
				chkColor_2(AB_2.blockB);

				firstAB_2();
			}

			else if( bb && (!ba) )
			{ 
				//        	 System.out.println(AB.blockA.color+"----4_1��");
				//    	 System.out.println(AB.blockB.color+"----4_2��");
				mBlocks_2.addLast(AB_2.blockB);
				chkColor_2(AB_2.blockA);
				firstAB_2();

			}

			//�Ѵ� �����϶� �������� ������
			else if (!(ba && bb))
			{ 
				if(AB_2.blockA.color!=AB_2.blockB.color)
					chkColor_2(AB_2.blockA);
				chkColor_2(AB_2.blockB);

				firstAB_2();
			}


		}
		//��Ʈ�� �Ҽ����� �����̴� ���̵�
		////////////////////////////////////////////////////////////////////////      
		void moveMBlocks_2 ()// �����ִ� ����  �ο��ִ� ���̵�.
		{
			//LinkedList mBlocks
			ListIterator itr =  mBlocks_2.listIterator();
			//��ũ�� ���ͷ����� ���� itr = ��ũ�帮��Ʈ������ �����͸� ��ũ�����ͷ����� �������� �ٲ��ְ�.
			while(itr.hasNext())// ��ȯ ��Ų �ڷḦ ������ while �������� ������ �ͼ� ������.
			{
				Block_2 bk = (Block_2) itr.next();// ������� ������ ���������� �ٲ۴�.
				//            System.out.println(AB.blockA.color+"    ����");
				if(!moveBlock_2(bk))//�����̴� ��(bk )�� �ƴ϶�� 
				{
					ListIterator litr = itr;

					itr.previous();

					chkColor_2((Block_2)itr.next());
					itr.previous();
					litr.remove();

					//                  litr.remove();

				}
			}
		}


		////////////////////////////////////////////////////////////////////////
		boolean bound_2(Block_2 bk,int dir)  //  ����(����) �� �Ҹ���.. ������ ã�ƶ�.
		{
			switch(dir)
			{
			case left :

				return bk.pipe ==0;

			case right :

				return bk.pipe ==5;

			case down :
			{
				return  bk.Y+20 > height;
			}
			//         default :
			}
			return false;
		}
		////////////////////////////////////////////////////////////////////////
		boolean moveRight(Block_2 bk){
			//        bk.pipe++;
			//        bk.X+= 40;chkLeft
			//    LinkedList blocks;// ��ũ�� ����Ʈ ������ ������ �����.
			if(	!pipes_2[bk.pipe+1].blocks.isEmpty())
				if(	bk.collideDown_2((Block_2)pipes_2[bk.pipe+1].blocks.getLast()))
					if(bk.chkLeft_2((Block_2)pipes_2[bk.pipe+1].blocks.getLast()))	  
						return false;
			bk.pipe++;
			bk.X+=2*radius;

			return true;
		}
		////////////////////////////////////////////////////////////////////////
		boolean moveLeft(Block_2 bk){
			if(	!pipes_2[bk.pipe-1].blocks.isEmpty())
				if(	bk.collideDown_2((Block_2)pipes_2[bk.pipe-1].blocks.getLast()))
					if(bk.chkLeft_2((Block_2)pipes_2[bk.pipe-1].blocks.getLast()))	  
						return false;

			bk.pipe--;
			bk.X-=2*radius;

			return true;

		}


		/////////////////////////////////////////////////////////////////////
		//����� ������ ���� �ִ� ��Ȯ�� �� ��Ʈ���� ���ؼ�  ������

		void chkColor_2(Block_2 bk)//��������. 1��.
		{
			LinkedList finshbk = new LinkedList<>();
			LinkedList list = new LinkedList<>();

			finshbk.add(bk);//���� �� ����Ʈ�� ����������, ������ ��Ҹ� �߰��մϴ�
			bk.four= true;//  �����·� ��������� Ȯ���Ϸ���. ���·� �޴´�

			Iterator itr = finshbk.iterator();//���ͷ����� ���� �������� ���� ������ �ð��̴�

			while(itr.hasNext())// ���� ���� �����鼭  Ȯ���� �Ұ��̴�.
			{
				Object itrOB = itr.next(); //������Ʈ �������� ��ȯ �ؼ��������̴�.
				Block_2 block =(Block_2) itrOB;// ���������� �ٽ� �ٲ۴�. 
				LinkedList friend= friends_2(block);//�ؿ���  �������� ���� ��ũ�� ����Ʈ�� �޴´�.

				for (int i = 0; i < friend.size(); i++) // �װ�����ŭ  FOR���� ������.
				{
					Block_2 friend_1= (Block_2)friend.get(i);
					//�� ������ ���޴´� =     ���� �������� �޾Ƽ� �ٲ��ش�.
					if((!friend_1.four)&&(friend_1.color==block.color))
					{// ����. �������ǰ���  ������ �ƴϰ�  &&  ���� ���ٸ�.
						finshbk.add(friend_1);// �׺귰�� �������� �־��ش�.
						friend_1.four=true;// �װ��� ���� �̴�.
					}
				}
				list.add(finshbk.remove());//�� ����Ʈ�κ��� ������ ��Ҹ� ������ �����ݴϴ�.
				itr=finshbk.iterator();
				//	iterator ()
				//�� ����Ʈ���� ��Ҹ�(������ ������) �ݺ� ó���ϴ� ���׷���Ÿ�� �����ݴϴ�.
			}
			/////////////////////////////////////
			if(list.size() >= boom)// ������ ���� ����   4���� ũ�ٸ�. 
				for (int i = 0; i < list.size(); i++) 
				{
					Object OBJ =list.get(i);//�װͿ� �����͸� ������Ʈ�� �ٲٰ�.
					Block_2 block =(Block_2)OBJ;// �����·� ����ȯ.
					eraseBB_2(block);// �׺����� ����� �޼ҵ�� ����.
				}
			else
				for (int i = 0; i < list.size(); i++) 
				{
					Object OB = list.get(i);
					Block_2 block=(Block_2)OB;
					block.four=false;
				}

		}

		//2��
		LinkedList friends_2(Block_2 block)//ģ�� �ֺ��� ���� �ִ��� Ȯ��.
		{//��ũ�� ����Ʈ�������� ���� �ٽ� ���� �Ұ��̴�.
			LinkedList friend = new LinkedList();
			//    	  pipe =-1;// �������� �⺻�� -1 �Z��.
			//          pipe2=-1;/
			int frpipe = block.pipe;
			int frpipe2 = block.pipe2;

			if(block.pipe>0)//2_1��//�� ���� ������ ���̰� 0���� Ŭ��.
				if(pipes_2[frpipe-1].blocks.size()>frpipe2)
					//������ �迭[������ ����-1]�� ������ ����� >  ������ ������ ������ ���̺��� Ŭ��.
					friend.add(pipes_2[frpipe-1].blocks.get(frpipe2));
			//        �������̴�  .  ->  ������ �迭��[����������������-1]�鸦.(���� ���� ���α��̷� )�� ����Ʈ���� ������ ��ġ�� �ִ� ��Ҹ� �����ݴϴ�.

			///////////////////////////////////////////////////////
			if(block.pipe<numW-1 )//2_1��// ���� �ķ��� ���̰�.  < 6-1 (5)
				if(pipes_2[frpipe+1].blocks.size() > frpipe2)
					//���� ����������[���������α���+1].������ �����  >  ������ ���� ������ ���̺���
					friend.add(pipes_2[frpipe+1].blocks.get(frpipe2));
			////////////////////////////////////////////////////
			if(pipes_2[frpipe].blocks.size()>frpipe2+1)//2_3��.
				//�������迭[������������ ���� ������]������ ����� > ����+1 ���� ������.
				friend.add(pipes_2[frpipe].blocks.get(frpipe2+1));
			//////////////////////////////////////////////
			if(frpipe2-1>-1)//�� ����Ʈ�� ����������, ������ ��Ҹ� �߰��մϴ�.  addLast
				friend.add(pipes_2[frpipe].blocks.get(frpipe2-1));

			return friend;
		}
		//3��
		void eraseBB_2(Block_2 block)
		{
			for (int i = block.pipe2+1; i <pipes_2[block.pipe].blocks.size(); i++) 
			{
				Object OB = pipes_2[block.pipe].blocks.get(i);
				Block_2 block_2 = (Block_2) OB;
				block_2.pipe2--;
				block_2.Y+= 2*radius;
			}
			pipes_2[block.pipe].blocks.remove(block.pipe2);
			score++;
		}


	}
	
	//�������.
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	class NextPanel_2 extends JPanel implements Runnable,GameParameters
	{

		int PWIDTH2 = nextWH;
		int PHEIGHT2 = nextWH;

		Thread animator2;// �����带 ������ ���ؼ�.

		boolean running2 = false;   // �����·� �Z��

		Game_Room topFrame2;// ��ü �гο��� ������ ��� �ϱ� ���ؼ�.

		PuyoGame2_1 nextPuyo;//  �ҷ��� ���. �ϰٴ�/

		Graphics dbg2; 
		Image dbImage2 = null;

		public NextPanel_2() 
		{
			setBackground(Color.white);

			setPreferredSize( new Dimension(PWIDTH2, PHEIGHT2));// ��� ������ �����ش�.
			//setBounds(20, 30, 150, 150);
			//	       setFocusable(true);// �ּ� Ǯ�� �ȿ����δ�/

			nextPuyo= new PuyoGame2_1();// ���Ӱ� ���� ���ְ�.     ������    ������
			nextPuyo.startGame2_1_1();// myPuyo �ȿ� �մ� �� ������ �������� ��ŸƮ �ϰ�
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
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////
	class PuyoPanel_2 extends JPanel implements Runnable, GameParameters
	{	// �г��� �ް�   �гο�  ���ʺ� @  �׷��� ���..
		////////////////////////////////////////////////////////////////////////
		int PWIDTH = width; // ���� 
		int PHEIGHT = height; //����

		Thread animator;// �����带 ������ ���ؼ�.
		int stime=0;
		boolean running = false;   // �����·� �Z��
		boolean gameOver = false;//////////////////
		Game_Room topFrame;// ��ü �гο��� ������ ��� �ϱ� ���ؼ�.

		PuyoGame2 myPuyo;//  �ҷ��� ���. �ϰٴ�/

		int score = 0;

		Graphics dbg; 
		Image dbImage = null;

		////////////////////////////////////////////////////////////////////////
		public PuyoPanel_2()
		{
			setBackground(Color.red);// �ѿ� ��λ�

			setPreferredSize( new Dimension(PWIDTH, PHEIGHT));// ��� ������ �����ش�.
			//setBounds(20, 30, 150, 150);
			setFocusable(true);


			myPuyo= new PuyoGame2();// ���Ӱ� ���� ���ְ�.
			myPuyo.StartGame_2();// myPuyo �ȿ� �մ� �� ������ �������� ��ŸƮ �ϰ�//
			startGame();// �����带 �����Ѵ�.


		}  
		////////////////////////////////////////////////////////////////////////

		////////////////////////////////////////////////////////////////////////
		void startGame()
		{ 
			if (animator == null || !running) {// �ȵ��� ������... ���Ѵ�?
				animator = new Thread(this);
				animator.start();//�����带 �����Ѵ�. run  �޼ҵ� ����.
			}
		} 
		////////////////////////////////////////////////////////////////////////
		public void run()//������ ���� �κ� �����尡 ���۰�����
		{

			running = true;
			int maxtime=8000;///////////



			while(running) {
				gameUpdate(); //�� ������ ����? �޼ҵ�.
				gameRender();   // ���ۿ� ����.  ������ �׷��ִ¿�
				paintScreen();  // ȭ�� ���۸� �׸�

				if(myPuyo.Update_2()){
					showMessage();
					break;
				}

				if(stime == maxtime){
					showMessage();

					break;
				}


				try {
					Thread.sleep(20);
					time.setText("TIME : "+ stime/100+"sec");

				} catch (Exception e) {

				}
			}
		}
		//////////////////////////////////////////////////////////
		void showMessage() {


			JOptionPane.showMessageDialog(null,"  GAME OVER   "+"\n"+" ȹ�� ���� "+ myPuyo.score+ "��"+ "   �� �� "+stime/100 +"sec",
					"M",JOptionPane.WARNING_MESSAGE);

		}
		////////////////////////////////////////////////////////////////////////
		void gameUpdate() 
		{ 
			myPuyo.Update_2();// �̰� �ּ� �ϸ� �����̴� ��Ȱ.

		}  
		///////////////////////////////////////////////////////////////////////
		void gameRender()//  ���  �׸�  ����ִ°�  ��  ���̸� �������༭ �׸� �׷���.
		{
			if (dbImage == null){
				dbImage = createImage(PWIDTH, PHEIGHT);// ���� ���� �׸�// �׸��� ������ ���.

				return;

			}

			else{
				dbg = dbImage.getGraphics(); 
			}

			//���
			dbg.setColor(Color.darkGray);
			dbg.fillRect (0,0, PWIDTH, PHEIGHT);//��ü ��ʾ��� ��ġ(���̴� ���)

			myPuyo.render_2(dbg);// �׸� �׸�?


		}  
		//    gameRender()     paintScreen()  �ΰ��� ��Ʈ ���Ѵ� ������,,,����,  
		void paintScreen()  // ��Ȳ ����  ��ũ���� �׷��ִ� ��Ȱ�� �ϴ¸޼ҵ�.
		{ 
			Graphics g;
			try {
				g = this.getGraphics();
				if ((g != null) && (dbImage != null))
					g.drawImage(dbImage, 0, 0, null);
				//Image img, int x, int y,
				//ImageObserver observer);
				g.dispose();
			}
			catch (Exception e)
			{ 

			}
		} 
		
	}
	
	
	
	public    static void main(String args[])
	{ 

		new Game_Room(); // ���� �մϴ�.   
	}

}
