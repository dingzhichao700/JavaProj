
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;

public class JavaClient1 {

	public static void main(String[] args) {
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
}