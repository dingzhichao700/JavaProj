import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;

import javax.swing.JFrame;

/**�������*/
public class TestDrawLine {
	public static void main(String[] args) {
		new DrawSee();
	}
}

class DrawSee extends JFrame {

	private static final int sx = 50;// С������
	private static final int sy = 50;// С����߶�
	private static final int w = 40;
	private static final int rw = 400;

	public DrawSee() {
		Container p = getContentPane();
		setBounds(100, 100, 500, 500);
		setVisible(true);
		p.setBackground(new Color(0x000000));
		setLayout(null);
		setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		try {
			Thread.sleep(500);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// ��ȡר�������ڴ��ڽ����ϻ�ͼ�Ķ���
		Graphics g = this.getGraphics();

		// ������Ϸ����
		try {
			g.setColor(Color.WHITE);
			g.drawRect(sx, sy, rw, rw);

			for (int i = 1; i < 10; i++) {
				g.drawLine(sx + (i * w), sy, sx + (i * w), sy + rw);
				g.drawLine(sx, sy + (i * w), sx + rw, sy + (i * w));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}