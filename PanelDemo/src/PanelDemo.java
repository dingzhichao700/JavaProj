
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

class PanelDemo {

	public static void main(String[] args) throws Exception {
		JFrame frame = new JFrame("Excel_2_Json");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setLayout(null); // 设置窗体布局为空布局

		JPanel panel = new JPanel(); // 实例化一个面板
		panel.setBackground(Color.pink);
//		frame.getContentPane().add(panel); // 将面板添加到窗体中
		// 如果使用下面添加面板的方法，面板将布满整个窗口，可以试试看
		frame.setContentPane(panel);
	}

}