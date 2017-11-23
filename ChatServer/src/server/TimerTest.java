package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {
	private Timer timer; // 计时器

	public static void main(String[] args) {
		TimerTest test = new TimerTest();
		test.start(0, 3); // 3秒运行一次
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
		timer.schedule(task, delay * 1000, internal * 1000); // 计时
																// 开始。调用TimerTask的run()
		// task为是 TimerTask 类，在包：import java.util.TimerTask .使用者要继承该类，并实现 public
		// void run() 方法，因为 TimerTask 类实现了 Runnable 接口。
		// delay为延迟。0表示无延迟 1000为1秒延迟
		// internal为多少 毫秒
	}
}