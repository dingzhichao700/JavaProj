
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class JavaClient1 extends Frame {

	private TextField tfWords = null;

	private void launchFrame() {
		 this.setTitle("Chatroom Client");
		 this.setLocation(700, 50);
		 this.tfWords = new TextField();
		 this.add(tfWords, BorderLayout.SOUTH);
		this.setSize(400, 350);
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		 try {
		 Socket sc = new Socket(InetAddress.getLocalHost(), 9999);
		 DataInputStream din = new DataInputStream(sc.getInputStream());
		 DataOutputStream dout = new DataOutputStream(sc.getOutputStream());
		
		 dout.writeUTF("helloworld222");
		 System.out.println(din.readUTF());
		
		 din.close();
		 dout.close();
		 sc.close();
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	}
	
	public static void main(String[] args) {
		JavaClient1 cc = new JavaClient1();
		cc.launchFrame();
	}
}