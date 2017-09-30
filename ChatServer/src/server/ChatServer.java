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
	private List<Client> clients = new ArrayList<Client>();
	private boolean started = false;
	private ServerSocket ss = null;
	private Client c = null;

	public ChatServer() {
		try {
			ss = new ServerSocket(8888);
			started = true;
		} catch (IOException ioe) {
			System.out.println("�Բ��𣬷���������������");
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	public void start() {
		try {
			Socket s = this.getSs().accept();
			System.out.println("Here comes a client! ");
			if (s != null) {
				c = this.new Client(s, true);
				this.clients.add(c);
				new Thread(c).start(); // �߳�ʼ��û��ֹͣ������?
				c = null;
			}
		} catch (IOException e) {
			System.out.println("���Ӵ���");
			e.printStackTrace();
		}
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public ServerSocket getSs() {
		return ss;
	}

	public void setSs(ServerSocket ss) {
		this.ss = ss;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	public static void main(String args[]) {
		ChatServer cs = new ChatServer();

		while (cs.isStarted()) {
			cs.start();
		}
	}

	private class Client implements Runnable {
		private boolean connected = false;
		private Socket s = null;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;

		public Client(Socket s, boolean connected) {
			this.s = s;
			this.connected = connected;
		}

		public void run() {
			InetAddress ip = null;
			int port = 9999;
			try {
				if (connected) {
					dis = new DataInputStream(s.getInputStream());
					dos = new DataOutputStream(s.getOutputStream());
					ip = s.getInetAddress();
					port = s.getPort();
				}
				while (connected) {
					String line = dis.readUTF();
					if (line.equals("###Exit###")) {
						getClients().remove(this); // �����յ��Ƴ�����Ϣ���Ƴ��ͻ��˼�¼
						line = "Bye-Bye!";
						connected = false;
					}
					System.out.println("From: (" + ip.toString() + ":" + port + "): " + line);
					for (int i = 0; i < getClients().size(); i++) {
						if (this.equals(getClients().get(i))) {
							continue;
						} else {
							getClients().get(i).dos
									.writeUTF("From: (" + ip.toString() + ":" + port + "): " + line + "/n");
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