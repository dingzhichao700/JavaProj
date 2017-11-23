package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	private Timer timer; // ��ʱ��

	public static void main(String[] args) {
		TimerTest test = new TimerTest();
		test.start(0, 3); // 3������һ��
	}
	
	public TimerTest() {
		timer = new Timer();
	}
	
	public String getNameIndex() {
		return "123";
	}

	private TimerTask task = new TimerTask() {
		public void run() {
			try {
				BufferedReader read = new BufferedReader(new FileReader("C://123.txt"));
				String text = null;
				while ((text = read.readLine()) != null) {
					System.out.println(text);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	};

	public void start(int delay, int internal) {
		timer.schedule(task, delay * 1000, internal * 1000); // ��ʱ
																// ��ʼ������TimerTask��run()
		// taskΪ�� TimerTask �࣬�ڰ���import java.util.TimerTask .ʹ����Ҫ�̳и��࣬��ʵ�� public
		// void run() ��������Ϊ TimerTask ��ʵ���� Runnable �ӿڡ�
		// delayΪ�ӳ١�0��ʾ���ӳ� 1000Ϊ1���ӳ�
		// internalΪ���� ����
	}
}