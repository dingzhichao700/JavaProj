import java.awt.Container;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicArrowButton;

public class ButtonsTest extends JFrame {
	JButton jb = new JButton("按钮");
	BasicArrowButton up = new BasicArrowButton(BasicArrowButton.NORTH);
	BasicArrowButton right = new BasicArrowButton(BasicArrowButton.EAST);

	public ButtonsTest() {
		super("各种类型Button示例！！！");
		super.setSize(500, 300);
		super.setVisible(true);
		
		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		jb.setBounds(100, 50, 200, 20);
		cp.add(jb);
		cp.add(new JCheckBox("JCheckBox"));
		cp.add(new JToggleButton("JToggleButton"));
		cp.add(new JRadioButton("JRadioButton"));

		JPanel jp = new JPanel();
		jp.setBorder(new TitledBorder("方向按钮"));
		jp.add(up);
		jp.add(right);
		cp.add(jp);
	}

	public static void main(String[] args) {
		ButtonsTest bt = new ButtonsTest();
	}
}
