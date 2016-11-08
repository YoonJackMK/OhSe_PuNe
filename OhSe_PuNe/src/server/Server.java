package server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Server {
   HashMap<String, DataOutputStream>clients ;
   
   public Server() {
      //  이거로 바껴랏
      clients =new HashMap();
      Collections.synchronizedMap(clients);
      SimpleDateFormat sf = new SimpleDateFormat();
      
      try {
         ServerSocket server = new ServerSocket(7777);
         System.out.println("["+sf.format(new Date())+"]"+"서버 시작");
         
         while(true)
         {
            Socket client = server.accept();
            System.out.println("["+sf.format(new Date())+"]"+
                  "["+"로그인"+":"+client.getPort()+"] 에서 접속");
            
            new Receiver(client).start();
         }
         
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   void sendToAll(String msg)
   {
      Iterator it =clients.keySet().iterator();
      while(it.hasNext())
      {
         try {
            DataOutputStream out = 
                  (DataOutputStream)clients.get(it.next());
            System.out.println(msg);

            out.writeUTF(msg);
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
   class Receiver extends Thread
   {
      String name;
      DataOutputStream output;
      DataInputStream input;
      public Receiver(Socket client) {
         try {
            name = "["+client.getInetAddress()+"]";
            output = new DataOutputStream(client.getOutputStream());
            input = new DataInputStream(client.getInputStream());
               
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      
      @Override
      public void run() {
         
         try {
            name = input.readUTF();
            sendToAll("#"+name+"님이 입장하였습니다.");
            clients.put(name, output);
            
            System.out.println("현재접속자수:"+clients.size());
            
            while(input!=null)
            {
               sendToAll(input.readUTF());
               System.out.println(input.readUTF());
            }
         } catch (IOException e) {
            clients.remove(name);
            sendToAll("#"+name+"님이 퇴장하셨습니다.");
            System.out.println("현재접속자수:"+clients.size());
         }
      
      }// run 종료
   }
  
   public static void main(String[] args) {
      new Server();
   }
}