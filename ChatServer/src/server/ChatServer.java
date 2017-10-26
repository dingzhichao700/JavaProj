package server;

/** 
 * 这个聊天室程序是基于TCP Socket的，其中，为了消除IO阻塞，采用了多线程的方式。 
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
	private Client c = null;
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
			System.out.println("对不起，服务器不能启动！");
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	public void start() {
		try {
			Socket socket = this.getServerSocket().accept();
			System.out.println("Here comes a client! ");
			if (socket != null) {
				c = this.new Client(socket, true);
				this.clients.add(c);
				new Thread(c).start(); // 线程始终没能停止！！！?
				c = null;
			}
		} catch (IOException e) {
			System.out.println("连接错误！");
			e.printStackTrace();
		}
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
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
					String line = dis.readUTF();
					if (line.equals("###Exit###")) {
						getClients().remove(this); // 当接收到推出的消息，移除客户端记录
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
				System.out.println("客户端连接出错！");
				ioe.printStackTrace();
			}
		}
	}

}