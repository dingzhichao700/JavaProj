package server;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;

import sun.print.resources.serviceui;

public class ChatServer extends JFrame {
	private Boolean runnning = false;
	private ServerSocket serverSocket = null;
	private Client client = null;
	private List<Client> clients = new ArrayList<Client>();
	private JButton btn;

	public static void main(String args[]) {
		ChatServer sever = new ChatServer();
		while (sever.runnning) {
			sever.connect();
		}
	}

	public ChatServer() {
		super.setTitle("���Բ���");
		super.setSize(420, 300);
		super.setVisible(true);
		this.setLayout(null);

		btn = new JButton("����");
		btn.setBounds(new Rectangle(93, 220, 180, 30));
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			}
		});
		this.add(btn);
	}

	private void start() {
		try {
			if (serverSocket == null) {
				serverSocket = new ServerSocket(8888);
				btn.setLabel("������");
				this.runnning = true;
			}
		} catch (IOException ioe) {
			System.out.println("�Բ��𣬷��������������� ԭ��");
			ioe.printStackTrace();
			System.exit(-1);
		}
	}

	public void connect() {
		try {
			Socket socket = this.serverSocket.accept();
			System.out.println("Here comes a client: " + socket.getInetAddress() + ":" + socket.getPort());
			if (socket != null) {
				client = this.new Client(socket, true);
				this.clients.add(client);
				new Thread(client).start(); // �߳�ʼ��û��ֹͣ������?
				client = null;
			}
		} catch (IOException ioe) {
			System.out.println("�Բ��𣬷��������������� ԭ��");
		}
	}

	public List<Client> getClients() {
		return clients;
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
						+ getClients().get(i).socket.getPort() + " ����");
			}
		}
	}

	public Boolean isServerClose(Socket socket) {
		try {
			socket.sendUrgentData(0);// ����1���ֽڵĽ������ݣ�Ĭ������£���������û�п����������ݴ�����Ӱ������ͨ��
			return false;
		} catch (Exception se) {
			return true;
		}
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
			} catch (IOException e) {
				System.out.println("�ͻ������ӳ���");
				// e.printStackTrace();
			}
		}
	}
}