import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class JavaServer1 {

	public static void main(String[] arg) {
		int count = 0;
		try {
			ServerSocket server = new ServerSocket(9999);
			System.out.println("��ʼ�Զ˿�9999���м���");

			while (true) {
				Socket sc = server.accept();

				DataInputStream din=new DataInputStream(sc.getInputStream());
				DataOutputStream dout=new DataOutputStream(sc.getOutputStream());
				
				System.out.println("�ͻ���ip��ַ�ǣ�"+sc.getInetAddress());
				System.out.println("�ͻ��˶˿ں��ǣ�"+sc.getPort());
				System.out.println("���ض˿ں��ǣ�"+sc.getLocalPort());
				System.out.println("�ͻ�����Ϣ�ǣ�"+din.readUTF());
				
				dout.writeUTF("���յ��㷢������Ϣ!!");
				
				din.close();
				dout.close();
				sc.close();
//				server.close();//����ע�͵�
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}