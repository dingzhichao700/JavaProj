package Chat;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
public class Server implements ActionListener{
    int count=0;//记录点机关闭按钮次数2次关闭
    Vector soconly=new Vector();//只有SOCKET,用于群发
    HashMap sockets=new HashMap();//所有客户的SOCKET
    HashMap socket_thread=new HashMap();//Socket所在的线乘，用于退出；
    ServerSocket serversocket;
    JFrame frame;
    JTextArea area=new JTextArea();
    JPanel panel=new JPanel();
    JButton start=new JButton("启动");
    JButton stop=new JButton("停止");
    public Server() {
        //设置样式
        //frame.setDefaultLookAndFeelDecorated(true);//
        frame=new JFrame("服务器");
        //添加
        panel.add(start);
        panel.add(stop);
        frame.add(panel,BorderLayout.NORTH);
        frame.add(area,BorderLayout.CENTER);
        frame.pack();
        center();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        //添加监听
        start.addActionListener(this);
        stop.addActionListener(this);
        System.out.println("new");
    }
    public void center(){
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension e=toolkit.getScreenSize();
        int x=e.width/2-(int)(frame.getSize().getWidth()/2);
        int y=e.height/2-(int)(frame.getSize().getHeight()/2);
        frame.setLocation(x,y);
    }
    public void actionPerformed(ActionEvent e){
        JButton btn=(JButton)e.getSource();
        if(btn==start){
            serverRun serverrun=new serverRun();
            System.out.println("gogo");
            serverrun.start();
        }
        if(btn==stop){
            try{
                Iterator i = soconly.iterator();
                while (i.hasNext()) {
                    Socket s = (Socket) i.next(); //获得其他SOCKET的通信口
                    ObjectOutputStream out = new ObjectOutputStream(s.
                            getOutputStream());
                  Objecting bj=new Objecting();
                    out.writeObject("系统消息-----服务器马上关闭！！！！！！请马上关闭客户端");
                }
                count++;
               if(count==2){
                   System.exit(0);
               }
               JOptionPane.showMessageDialog(frame,"已通知客户端，请再次点击关闭按钮关闭");
            }catch(Exception ex){ex.printStackTrace();}
        }
    }
class serverRun extends Thread{
    public void run(){
        try{
            System.out.println("开始SERVERRUN");
            serversocket=new ServerSocket(55555);
            area.append("服务器启动\n");
            start.setEnabled(false);
            while(true){
                Socket socket=serversocket.accept();//接受1个客户连接
                System.out.println("连接成功");
                BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));//获得客户名称
                String name=br.readLine();
                area.append("服务器接收用户"+name+"的连接\n");
                soconly.add(socket);//收集所有SOCKET
                //发送新用户消息
                Iterator i=soconly.iterator();
                        while(i.hasNext()){
                            Socket s=(Socket)i.next();//获得其他SOCKET的通信口
                            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                            out.writeObject("系统消息-----"+name+"用户进入"+"------");  }
                sockets.put(name,socket);//收集所有有标市的SOCKET
                Vector v=getList();
                Iterator j=soconly.iterator();
                    while(j.hasNext()){
                        Socket s = (Socket) j.next(); //获得其他SOCKET的通信口
                        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                        out.writeObject(v);
                        System.out.println("服务器发送用户列表进入"); }

                Details d=new Details(socket);
                socket_thread.put(name,d);//收集SOCKET线乘
                d.start();
            }
        }catch(Exception e){e.printStackTrace();}
    }   }
    class Details extends Thread{
        boolean alive=true;
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;//客户SOCKET
        Details(Socket socket){
           this.socket=socket;
        }              
        public void run(){
            while(alive){
                try{
                    in = new ObjectInputStream(socket.getInputStream());
                    Objecting objecting=(Objecting)in.readObject();//获得对象
                    String name=objecting.getKey();//获得名字
                    String content=objecting.getValue();//获得内容
                     if(name.equalsIgnoreCase("all")){
                        Iterator i=soconly.iterator();
                        while(i.hasNext()){
                            Socket s=(Socket)i.next();//获得其他SOCKET的通信口
                            out=new ObjectOutputStream(s.getOutputStream());
                            out.writeObject(content);
                            System.out.println("Send");
                        }      }
                    if(name.equalsIgnoreCase("exit")){
                        Details thread=(Details)socket_thread.get(content);
                        thread.alive=false;
                        area.append(content+"离开\n");
                        socket.close();
                        soconly.remove(socket);//群发SOCKET消失
                        sockets.remove(content);//标市SOCKET消失
                        Iterator i=soconly.iterator();
                        while(i.hasNext()){
                            Socket s=(Socket)i.next();//获得其他SOCKET的通信口
                            out=new ObjectOutputStream(s.getOutputStream());
                            out.writeObject("系统消息-----"+content+"用户离开"+"------"); }
                        Vector v=getList();
                        Iterator j=soconly.iterator();
                        while(j.hasNext()){
                            Socket s = (Socket) j.next(); //获得其他SOCKET的通信口
                            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                            out.writeObject(v);
                            System.out.println("服务器发送用户列表离开");
                        }
                    }
                      if(!name.equalsIgnoreCase("all")&!name.equalsIgnoreCase("exit")){
                        area.append("处理私聊信息");
                        Socket s=(Socket)sockets.get(name);
                        out=new ObjectOutputStream(s.getOutputStream());
                        out.writeObject(content);
                    }
                }catch(Exception e){e.printStackTrace();}
            }   }   }
    public Vector getList(){
       Vector v=new Vector();
       Set set=sockets.keySet();
       Iterator i=set.iterator();
       while(i.hasNext()){
          v.add(i.next());
       }
        return v;
    }
    public static void main(String args[]){
        new Server(); }    }
