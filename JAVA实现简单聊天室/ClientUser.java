package Chat;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;
public class ClientUser extends WindowAdapter implements ActionListener,Runnable{
        public JFrame jf;
        public JTextField jt;
        public JTextField name;
        public JButton connect;
        public JScrollPane jsp;
        public JTextArea  jta;
        public JButton jb;
        public JLabel l;
        Socket soc;
        InputStream soc_in;
        OutputStream soc_out;
        BufferedReader Bsoc_in;
        PrintWriter Psoc_out;
public ClientUser(){
        jf=new JFrame("Client Chat Room");
        jta=new JTextArea(10,30);
        jta.setEditable(false);
        jsp=new JScrollPane(jta,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jt=new JTextField(30);
        name=new JTextField(5);
        l=new JLabel("Please in put your name");
        connect=new JButton("Connect");
        connect.addActionListener(this);
        jt.addActionListener(this);
        jb=new JButton("Send The Message");
        jb.addActionListener(this);
        JPanel jp=new JPanel();
        JPanel jp2=new JPanel();
        jp.setLayout(new FlowLayout());
        jp.add(jt);
        jp.add(jb);
        jp2.add(l);
        jp2.add(name);
        jp2.add(connect);
        Container c=jf.getContentPane();
        c.setLayout(new GridBagLayout());
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        c.add(jp2,gbc);
        gbc.gridx=0;
        gbc.gridy=1;
        c.add(jsp,gbc);
        gbc.gridy=2;
        c.add(jp,gbc);
        jf.pack();
        jf.setVisible(true);  }
        public static void main(String[] args) {
                 ClientUser cuser=new ClientUser(); }
public void windowClosing(WindowEvent e){
        System.exit(0);}
public void actionPerformed(ActionEvent e){
        if (e.getSource()==connect){
                if(name.getText().equals("")){
                        new JOptionPane().showMessageDialog(null,"Please in Put your name First!");
                }
                else{
                        try{
                        soc=new Socket("localhost",55555);
                    Psoc_out=new PrintWriter(soc.getOutputStream(),true);
                    Bsoc_in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
                    Psoc_out.println(name.getText());
                        }
                        catch(Exception e1){e1.printStackTrace();}
                        name.setEditable(false);
                          new Thread (this).start();
                }  }
        if(e.getSource()==jt||e.getSource()==jb){
                jta.append(name.getText()+":"+jt.getText()+"\n");
                Psoc_out.println(name.getText()+":"+jt.getText());
                jt.setText("");
        }   }
public void run(){
        String	text;
        while(true){
                try{
                text=Bsoc_in.readLine();
                jta.append(text+"\n");
                }
    catch(Exception e){e.printStackTrace();}
  }  }}
