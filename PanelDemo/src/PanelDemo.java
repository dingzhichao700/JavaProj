
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
		frame.setLayout(null); // ���ô��岼��Ϊ�ղ���

		JPanel panel = new JPanel(); // ʵ����һ�����
		panel.setBackground(Color.pink);
//		frame.getContentPane().add(panel); // �������ӵ�������
		// ���ʹ������������ķ�������彫�����������ڣ��������Կ�
		frame.setContentPane(panel);
	}

}