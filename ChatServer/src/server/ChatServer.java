package server;

/** 
 * ��������ҳ����ǻ���TCP Socket�ģ����У�Ϊ������IO�����������˶��̵߳ķ�ʽ�� 
 *  
 * @author Quasar20063501 
 * @since Jan 29, 2009 
 *  
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.ArrayList;

public class ChatServer {
	public boolean started = false;
	private ServerSocket serverSocket = null;
	private Client client = null;
	private List<Client> clients = new ArrayList<Client>();

	public static void main(String args[]) {
		ChatServer sever = new ChatServer();

		while (sever.started) {
			sever.start();
		}
	}

	public ChatServer() {
		try {
			serverSocket = new ServerSocket(8888);
			started = true;
		} catch (IOException ioe) {
			System.out.println("�Բ��𣬷���������������");
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	public void start() {
		try {
			Socket socket = this.serverSocket.accept();
			System.out.println("Here comes a client! ");
			if (socket != null) {
				client = this.new Client(socket, true);
				this.clients.add(client);
				new Thread(client).start(); // �߳�ʼ��û��ֹͣ������?
				client = null;
			}
		} catch (IOException e) {
			System.out.println("���Ӵ���");
			e.printStackTrace();
		}
	}

	public List<Client> getClients() {
		return clients;
	}

	private class Client implements Runnable {
		private boolean connected = false;
		private Socket socket = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;

		public Client(Socket s, boolean connected) {
			this.socket = s;
			this.connected = connected;
		}

		public void run() {
			InetAddress ip = null;
			int port = 0;
			try {
				if (connected) {
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					ip = socket.getInetAddress();
					port = socket.getPort();
				}
				while (connected) {
					String content = dis.readUTF();
					if (content.equals("###Exit###")) {
						getClients().remove(this); // �����յ��Ƴ�����Ϣ���Ƴ��ͻ��˼�¼
						content = "Bye-Bye!";
						connected = false;
					}
					System.out.println("From: (" + ip.toString() + ":" + port + "): " + content);
					for (int i = 0; i < getClients().size(); i++) {
						if (this.equals(getClients().get(i))) {
							continue;
						} else {
							getClients().get(i).dos.writeUTF("From: (" + ip.toString() + ":" + port + "): " + content);
						}
					}
				}

			} catch (IOException ioe) {
				System.out.println("�ͻ������ӳ���");
				ioe.printStackTrace();
			}
		}
	}
}