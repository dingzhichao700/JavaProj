package Chat;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
public class Server implements ActionListener{
    int count=0;//��¼����رհ�ť����2�ιر�
    Vector soconly=new Vector();//ֻ��SOCKET,����Ⱥ��
    HashMap sockets=new HashMap();//���пͻ���SOCKET
    HashMap socket_thread=new HashMap();//Socket���ڵ��߳ˣ������˳���
    ServerSocket serversocket;
    JFrame frame;
    JTextArea area=new JTextArea();
    JPanel panel=new JPanel();
    JButton start=new JButton("����");
    JButton stop=new JButton("ֹͣ");
    public Server() {
        //������ʽ
        //frame.setDefaultLookAndFeelDecorated(true);//
        frame=new JFrame("������");
        //���
        panel.add(start);
        panel.add(stop);
        frame.add(panel,BorderLayout.NORTH);
        frame.add(area,BorderLayout.CENTER);
        frame.pack();
        center();
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setVisible(true);
        //��Ӽ���
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
                    Socket s = (Socket) i.next(); //�������SOCKET��ͨ�ſ�
                    ObjectOutputStream out = new ObjectOutputStream(s.
                            getOutputStream());
                  Objecting bj=new Objecting();
                    out.writeObject("ϵͳ��Ϣ-----���������Ϲرգ����������������Ϲرտͻ���");
                }
                count++;
               if(count==2){
                   System.exit(0);
               }
               JOptionPane.showMessageDialog(frame,"��֪ͨ�ͻ��ˣ����ٴε���رհ�ť�ر�");
            }catch(Exception ex){ex.printStackTrace();}
        }
    }
class serverRun extends Thread{
    public void run(){
        try{
            System.out.println("��ʼSERVERRUN");
            serversocket=new ServerSocket(55555);
            area.append("����������\n");
            start.setEnabled(false);
            while(true){
                Socket socket=serversocket.accept();//����1���ͻ�����
                System.out.println("���ӳɹ�");
                BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));//��ÿͻ�����
                String name=br.readLine();
                area.append("�����������û�"+name+"������\n");
                soconly.add(socket);//�ռ�����SOCKET
                //�������û���Ϣ
                Iterator i=soconly.iterator();
                        while(i.hasNext()){
                            Socket s=(Socket)i.next();//�������SOCKET��ͨ�ſ�
                            ObjectOutputStream out=new ObjectOutputStream(s.getOutputStream());
                            out.writeObject("ϵͳ��Ϣ-----"+name+"�û�����"+"------");  }
                sockets.put(name,socket);//�ռ������б��е�SOCKET
                Vector v=getList();
                Iterator j=soconly.iterator();
                    while(j.hasNext()){
                        Socket s = (Socket) j.next(); //�������SOCKET��ͨ�ſ�
                        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                        out.writeObject(v);
                        System.out.println("�����������û��б����"); }

                Details d=new Details(socket);
                socket_thread.put(name,d);//�ռ�SOCKET�߳�
                d.start();
            }
        }catch(Exception e){e.printStackTrace();}
    }   }
    class Details extends Thread{
        boolean alive=true;
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;//�ͻ�SOCKET
        Details(Socket socket){
           this.socket=socket;
        }              
        public void run(){
            while(alive){
                try{
                    in = new ObjectInputStream(socket.getInputStream());
                    Objecting objecting=(Objecting)in.readObject();//��ö���
                    String name=objecting.getKey();//�������
                    String content=objecting.getValue();//�������
                     if(name.equalsIgnoreCase("all")){
                        Iterator i=soconly.iterator();
                        while(i.hasNext()){
                            Socket s=(Socket)i.next();//�������SOCKET��ͨ�ſ�
                            out=new ObjectOutputStream(s.getOutputStream());
                            out.writeObject(content);
                            System.out.println("Send");
                        }      }
                    if(name.equalsIgnoreCase("exit")){
                        Details thread=(Details)socket_thread.get(content);
                        thread.alive=false;
                        area.append(content+"�뿪\n");
                        socket.close();
                        soconly.remove(socket);//Ⱥ��SOCKET��ʧ
                        sockets.remove(content);//����SOCKET��ʧ
                        Iterator i=soconly.iterator();
                        while(i.hasNext()){
                            Socket s=(Socket)i.next();//�������SOCKET��ͨ�ſ�
                            out=new ObjectOutputStream(s.getOutputStream());
                            out.writeObject("ϵͳ��Ϣ-----"+content+"�û��뿪"+"------"); }
                        Vector v=getList();
                        Iterator j=soconly.iterator();
                        while(j.hasNext()){
                            Socket s = (Socket) j.next(); //�������SOCKET��ͨ�ſ�
                            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                            out.writeObject(v);
                            System.out.println("�����������û��б��뿪");
                        }
                    }
                      if(!name.equalsIgnoreCase("all")&!name.equalsIgnoreCase("exit")){
                        area.append("����˽����Ϣ");
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
