import javax.swing.JFrame;

public class ShowItem extends JFrame{

	public ShowItem() {
		this.getGraphics().drawRect(0, 0, 50, 50);

	}

	public static void main(String[] args) {
		new ShowItem();
	}
}
