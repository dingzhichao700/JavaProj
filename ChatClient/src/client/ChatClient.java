package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame {
	private boolean connected = false;
	private DataOutputStream dos = null;
	private DataInputStream dis = null;
	private static final long serialVersionUID = 1L;
	private Socket socket = null;
	private TextField tfWords = null;
	private TextArea taShow = null;

	public static void main(String args[]) {
		ChatClient cc = new ChatClient();
		cc.launchFrame();
		cc.Listen();
	}

	public void launchFrame() {
		try {
			socket = new Socket(InetAddress.getByName("localhost"), 8888);
			connected = true;
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException ioe) {
			System.out.println("����������ʧ�ܣ�");
			ioe.printStackTrace();
		}
		this.tfWords = new TextField();
		this.taShow = new TextArea();
		this.setTitle("Chatroom Client");
		this.setLocation(700, 50);
		this.add(tfWords, BorderLayout.SOUTH);
		this.add(taShow, BorderLayout.NORTH);
		this.setSize(400, 350);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					connected = false;
					dos.writeUTF("###Exit###");
					dos.flush();
					close();
					System.exit(0);
				} catch (IOException ioe) {
					System.out.println("��Ϣ���ͳ���");
					ioe.printStackTrace();
				}
			}
		});
		this.setVisible(true);
		this.tfWords.addActionListener(new TFWordsListener());
	}

	public void Listen() {
		new Thread(new ListenThread()).start();
	}

	public void close() {
		try {
			if (dos != null)
				dos.close();
			if (socket != null)
				socket.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			System.out.println("�ͻ��˶Ͽ�ʱ����");
			System.exit(-1);
		}
	}

	public class ListenThread implements Runnable {
		public void run() {
			while (connected) {
				try {
					String back = dis.readUTF(); // ����IO
					taShow.append(back);
				} catch (SocketException se) {
					System.out.println("ллʹ�ã�");
				} catch (IOException e) {
					System.out.println("���ݶ�ʧ��");
					e.printStackTrace();
				}
			}
		}
	}

	private class TFWordsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String words = tfWords.getText().trim();
			taShow.append("ME: " + words + "/n");
			tfWords.setText("");
			try {
				dos.writeUTF(words);
			} catch (IOException ioe) {
				System.out.println("���ͳ���");
				ioe.printStackTrace();
			}
		}
	}

}