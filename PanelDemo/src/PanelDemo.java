
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
		super.setTitle("���Բ���");
		super.setSize(420, 300);
		super.setVisible(true);
		this.setLayout(null);// ���ò��ֹ�����Ϊ��

		JButton btn = new JButton("����");
		btn.setBounds(new Rectangle(93, 220, 180, 30));
		this.add(btn); // �������ӵ�������

		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("����");
			}
		});

		JPanel panel = new JPanel(); // ʵ����һ�����
		panel.setBackground(Color.pink);
		panel.setBounds(new Rectangle(0, 0, 420, 300));
		super.getContentPane().add(panel); // �������ӵ�������
		// ���ʹ������������ķ�������彫�����������ڣ��������Կ�
		// super.setContentPane(panel);
	}

}