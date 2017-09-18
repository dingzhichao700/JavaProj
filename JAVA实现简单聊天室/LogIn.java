package Chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class LogIn implements ActionListener{
    JFrame frame;
    JLabel title=new JLabel("免注册版");
    JPanel banner=new JPanel();
    JTextField text=new JTextField(10);
    JPanel body=new JPanel();
    JPanel buttom=new JPanel();
    JLabel name=new JLabel("输入姓名");
    JButton send=new JButton("进入");
    public LogIn() {                
        frame=new JFrame("注册");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        banner.add(title);
        body.add(name);
        body.add(text);
        buttom.add(send);
        frame.add(banner,BorderLayout.NORTH);
        frame.add(body,BorderLayout.CENTER);
        frame.add(buttom,BorderLayout.SOUTH);
        frame.pack();
        center();
        frame.setVisible(true);
        send.addActionListener(this);  }
    public void center(){
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Dimension e=toolkit.getScreenSize();
        int x=e.width/2-(int)(frame.getSize().getWidth()/2);
        int y=e.height/2-(int)(frame.getSize().getHeight()/2);
        frame.setLocation(x,y);   }
    public void actionPerformed(ActionEvent e){
        if(text.getText().equals("")){
        JOptionPane.showMessageDialog(frame,"姓名不能为空");
            return;
        }else{
            new Client(text.getText());
            frame.dispose();  }   }
    public static void main(String args[]){
        new LogIn(); }  }
