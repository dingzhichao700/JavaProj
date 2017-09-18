package Chat;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
public class ServerHost extends WindowAdapter implements ActionListener,Runnable{
        public JFrame jf;
        public JTextField jt;
        public JScrollPane jsp;
        public static JTextArea  jta;
        public JButton jb;
        HashMap  hm=new HashMap();
        JLabel inof;
        JTextField name;
        JCheckBox check;
        OutputStream soc_out;
        Vector Thread_vector=new Vector();
public ServerHost(){
        jf=new JFrame("Server Chat Room");
        jta=new JTextArea(10,30);
        jta.setEditable(false);
        jsp=new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jt=new JTextField(30);
        jt.addActionListener(this);
        jb=new JButton("Send The Message");
        jb.addActionListener(this);
        check=new JCheckBox("Send to all");
        check.setSelected(true);
        inof=new JLabel("write the name you want to send");
        name=new JTextField(5);
        name.enable(false);
        JPanel jp=new JPanel();
        jp.setLayout(new FlowLayout());
        jp.add(inof);
        jp.add(name);
        jp.add(jt);
        jp.add(jb);
        jp.add(check);
        Container c=jf.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        c.add(jsp,gbc);
        gbc.gridy=1;
        c.add(jp,gbc);
        jf.pack();
        jf.setVisible(true);}
public void windowClosing(WindowEvent e){
        System.exit(0);  }

        public static void main(String[] args) {
        ServerHost sh=new ServerHost();
        new Thread(sh).start(); }
        public void run(){
                ServerSocket ssc=null;
                try{
                         ssc=new ServerSocket(55555);
                         }
                        catch(Exception e){System.out.print("1");}
                while(true){
                         try{
                           Socket soc=ssc.accept();
                          // System.out.println("come om!!!!!!!!!!!!!!!!!!!!");
                           BufferedReader Bsoc_in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
                                String s=Bsoc_in.readLine();
                                hm.put(soc,s);
                           ServerThread st=new ServerThread(soc);
                           Thread_vector.addElement(soc);
                           new Thread(st).start();
                         }catch(Exception e){System.out.print("2");}
                 }  }
        public void actionPerformed(ActionEvent e){
                boolean find=false;
                String temp="";
                try{
                        if(check.isSelected()){
                          for(int i=0;i<Thread_vector.size();i++){
                                Socket s=(Socket)(Thread_vector.elementAt(i));
                                PrintWriter Psoc_out=new PrintWriter( s.getOutputStream(),true);
                                temp="Server:"+jt.getText()+"\n";
                                Psoc_out.println("Server:"+jt.getText());
                                Psoc_out.flush();   }
                        jta.append(temp);
                        jt.setText("");  }
                        else{
                                System.out.print("aaaaaaaa");
                                Set shm=hm.keySet();
                                Iterator it=shm.iterator();
                                while(it.hasNext()){
                                        Map.Entry me=(Map.Entry)(it.next());
                                        String Sstr=(String)(me.getValue());
                                        if(Sstr.equals(name.getText())){
                                       Socket Smsoc=(Socket)(me.getKey());
                            PrintWriter Psoc_out=new PrintWriter( Smsoc.getOutputStream(),true);
                            Psoc_out.println(name.getText()+jt.getText());
                                find=true;  }  }
                                if(find==false){
                           new JOptionPane().showMessageDialog(null,"Can not find the Clientuser");
                           }  }
                }catch(Exception e3){e3.printStackTrace();}
        }
}
class ServerThread implements Runnable{
        Socket socket;
        InputStream soc_in;
    BufferedReader Bsoc_in;
        ServerThread(Socket socket){
                this.socket=socket;
                }
        public void run(){
                try{
                soc_in=socket.getInputStream();
                Bsoc_in=new BufferedReader(new InputStreamReader(soc_in));
                String s=Bsoc_in.readLine();
                while(true){
                        if(s!=null)
                        ServerHost.jta.append(s+"\n");
                         s=Bsoc_in.readLine();
                }
                }catch(Exception e){System.out.print("3");}
        }
}
