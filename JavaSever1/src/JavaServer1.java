import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaServer1 {

	public static void main(String[] arg) {
		int count = 0;
		try {
			ServerSocket server = new ServerSocket(9999);
			System.out.println("开始对端口9999进行监听");

			while (true) {
				Socket sc = server.accept();

				DataInputStream din=new DataInputStream(sc.getInputStream());
				DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
				
				System.out.println("客户端ip地址是："+sc.getInetAddress());
				System.out.println("客户端端口号是："+sc.getPort());
				System.out.println("本地端口号是："+sc.getLocalPort());
				System.out.println("客户端消息是："+din.readUTF());
				
				dout.writeUTF("已收到你发来的消息!!");
				
				din.close();
				dout.close();
				sc.close();
//				server.close();//可以注释掉
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}