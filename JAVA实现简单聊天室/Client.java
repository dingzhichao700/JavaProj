package Chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import java.util.Vector;
public class Client implements ActionListener{
    ClientSocket cs;//接收信息线乘
    String name;
    Socket socket;
    JFrame frame;
    JTextArea area=new JTextArea(30,30);
    JTextField  text=new JTextField(30);
    JButton send=new JButton("发送消息");
    JButton exit=new JButton("离开");
    JPanel banner=new JPanel();
    JPanel panel=new JPanel();
    List list=new List(35);
    JPanel right=new JPanel();
    JLabel welcome=new JLabel();
    public Client(String name2) {
        this.name=name2;
        welcome.setText(name+"----欢迎来到聊天室");
        area.setEditable(false);
        //frame.setDefaultLookAndFeelDecorated(true);//
        frame=new JFrame("聊天室");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);
        banner.add(welcome);
        frame.add(banner,BorderLayout.NORTH);
        panel.add(text);
        panel.add(send);
        panel.add(exit);
        frame.add(panel,BorderLayout.SOUTH);
        frame.add(area,BorderLayout.CENTER);
        right.add(new JScrollPane(list));
        frame.add(right,BorderLayout.EAST);
        frame.pack();
        center();
        frame.setVisible(true);
        connect();//连接服务器
        send.addActionListener(this);
        exit.addActionListener(this);
        list.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String to=list.getSelectedItem();
                String content=JOptionPane.showInputDialog(frame,"输入向"+name+"发送的私聊信息","私聊",JOptionPane.INFORMATION_MESSAGE);
                 if(content==null){
                    return;
                }else{
                    
                 Objecting o=new Objecting(to,"---私聊信息--"+name+":"+content);//获得消息准备发送
                    try{
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        out.writeObject(o);
                        JOptionPane.showMessageDialog(frame,to+"收到消息");
                    }catch(Exception e2){e2.printStackTrace();}
                }
            }
        });
        text.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               if(text.getText().equals("")){
                   JOptionPane.showMessageDialog(frame,"消息为空");
                   return;
               }else{
                   Objecting o=new Objecting("all",name+":"+text.getText());//获得消息准备发送
               try{
                   ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                   out.writeObject(o);
                   text.setText("");
                }catch(Exception e2){e2.printStackTrace();}
            }  }   });   }
    public void connect(){
        try{
            socket = new Socket("localhost",55555);//连接服务器
            cs=new ClientSocket();//启动线乘
            cs.start();
        }catch(Exception e){e.printStackTrace();}  }
    public void center(){
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension e=toolkit.getScreenSize();
        int x=e.width/2-(int)(frame.getSize().getWidth()/2);
        int y=e.height/2-(int)(frame.getSize().getHeight()/2);
        frame.setLocation(x,y);
    }
    class ClientSocket extends Thread{
        ObjectInputStream in;
        ObjectOutputStream out;
        PrintWriter pw;
        public void run(){
            try{
                pw = new PrintWriter(socket.getOutputStream(),true);
                pw.println(name);//发送标记信息！名字
            }catch(Exception e){e.printStackTrace();}
            while(true){
               try{
                   in = new ObjectInputStream(socket.getInputStream());
                   Object o=in.readObject();
                   if(o instanceof  String){
                       area.append(o.toString()+"\n"); }
                   if(o instanceof Vector){
                       System.out.println("列表操作");
                       Vector v=(Vector)o;
                       list.removeAll();
                       Object []obj=v.toArray();
                       for(int i=0;i<obj.length;i++){
                           System.out.println((String)obj[i]);
                           list.add((String)obj[i]);
                    } }

               }catch(Exception e){
                   e.printStackTrace();
               }  }  }   }
public void actionPerformed(ActionEvent e){
   JButton btn=(JButton)e.getSource();
   if(btn==send){
       if(text.getText().equals("")){
           JOptionPane.showMessageDialog(frame,"消息为空");
           return;
       }else{
           Objecting o=new Objecting("all",name+":"+text.getText());//获得消息准备发送
           try{
               ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
               out.writeObject(o);
               text.setText("");
           }catch(Exception e2){e2.printStackTrace();}
       }
   }
   if(btn==exit){
     Objecting o=new Objecting("exit",name);
     try{
         ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
         out.writeObject(o);
         cs.stop();//结束线乘；
         socket.close();
     }catch(Exception x){x.printStackTrace();}
     System.exit(0);
   }  }  }