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

class MyPanel extends JFrame // ���Լ�����壬���ڻ�ͼ��ʵ�ֻ�ͼ����
{
	// ����JPanel��paint����
	// Graphics�ǻ�ͼ����Ҫ�࣬��������һ֧����
	public void paint(Graphics g) {
		// 1.���ø��ຯ����ɳ�ʼ��
		super.paint(g); // ��仰������

		g.drawOval(10, 10, 30, 30); // ��Բ
		g.drawLine(20, 30, 20, 80); // ��ֱ��
		g.drawRect(50, 50, 100, 50); // �������α߿�
		g.setColor(Color.RED); // ������ɫ
		g.fillRect(80, 60, 40, 60);
	}
}