package server;

/** 
 * 这个聊天室程序是基于TCP Socket的，其中，为了消除IO阻塞，采用了多线程的方式�? 
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

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import java.util.ArrayList;

public class ChatServerProto extends JFrame {
	public boolean started = false;
	private ServerSocket serverSocket = null;
	private Client client = null;
	private List<Client> clients = new ArrayList<Client>();

	public static void main(String args[]) {
		ChatServerProto sever = new ChatServerProto();

		while (sever.started) {
			sever.start();
		}
	}

	public ChatServerProto() {
		try {
			serverSocket = new ServerSocket(8888);
			started = true;
		} catch (IOException ioe) {
			System.out.println("对不起，服务器不能启动！");
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	public void timeVoid() {
		final Timer timer = new Timer();
		TimerTask tt = new TimerTask() {
			public void run() {
				ClientsCheck();
			}
		};
		timer.schedule(tt, 0, 2000);
	}

	private void ClientsCheck() {
		for (int i = 0; i < getClients().size(); i++) {
			if (!isServerClose(getClients().get(i).socket)) {
				System.out.println(getClients().get(i).socket.getInetAddress() + ":"
						+ getClients().get(i).socket.getPort() + " 连接");
			}
		}
	}

	public Boolean isServerClose(Socket socket) {
		try {
			socket.sendUrgentData(0);// 发�??1个字节的紧�?�数据，默认情况下，服务器端没有�?启紧急数据处理，不影响正常�?�信
			return false;
		} catch (Exception se) {
			return true;
		}
	}

	public void start() {
		try {
			Socket socket = this.serverSocket.accept();
			System.out.println("Here comes a client: " + socket.getInetAddress() + ":" + socket.getPort());
			if (socket != null) {
				client = this.new Client(socket, true);
				this.clients.add(client);
				new Thread(client).start(); // 线程始终没能停止！！�??
				client = null;
			}
		} catch (IOException e) {
			System.out.println("连接错误�?");
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
			try {
				InetAddress ip = null;
				int port = 0;
				if (connected) {
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
					ip = socket.getInetAddress();
					port = socket.getPort();
				}
				while (connected) {
					String content = dis.readUTF();
					if (content.equals("###Exit###")) {
						getClients().remove(this); // 当接收到推出的消息，移除客户端记�?
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
			} catch (IOException e) {
				System.out.println("客户端连接出错！");
				// e.printStackTrace();
			}
		}
	}
}