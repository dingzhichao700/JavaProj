import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowTest1 extends JFrame {
	private MyPanel mp = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WindowTest1 qwe = new WindowTest1();

	}

	public WindowTest1() {
		mp = new MyPanel();

		this.add(mp);
		this.setSize(400, 300);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

class MyPanel extends JFrame // 我自己的面板，用于绘图和实现绘图区域
{
	// 覆盖JPanel的paint方法
	// Graphics是绘图的重要类，可以理解成一支画笔
	public void paint(Graphics g) {
		// 1.调用父类函数完成初始化
		super.paint(g); // 这句话不能少

		g.drawOval(10, 10, 30, 30); // 画圆
		g.drawLine(20, 30, 20, 80); // 画直线
		g.drawRect(50, 50, 100, 50); // 画出矩形边框
		g.setColor(Color.RED); // 设置颜色
		g.fillRect(80, 60, 40, 60);
	}
}