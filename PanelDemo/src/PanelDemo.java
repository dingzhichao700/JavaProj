
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PanelDemo extends JFrame {

	public static void main(String[] args) {
		PanelDemo demo = new PanelDemo();
	}

	public PanelDemo() {
		super.setTitle("测试布局");
		super.setSize(420, 300);
		super.setVisible(true);
		this.setLayout(null);// 设置布局管理器为空

		JButton btn = new JButton("按我");
		btn.setBounds(new Rectangle(93, 220, 180, 30));
		this.add(btn); // 将面板添加到窗体中

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("按了");
			}
		});

		JPanel panel = new JPanel(); // 实例化一个面板
		panel.setBackground(Color.pink);
		panel.setBounds(new Rectangle(0, 0, 420, 300));
		super.getContentPane().add(panel); // 将面板添加到窗体中
		// 如果使用下面添加面板的方法，面板将布满整个窗口，可以试试看
		// super.setContentPane(panel);
	}

}