public class JavaMainTest {

	public static String s = "���Ǿ�̬����";

	public static void printInfo() {
		System.out.println("������������");
	}

	public static void main(String[] args) {
		// ����StaticDemo1�е�printInfo����ʱ������Ҫ�����µ�
		// StaticDemo1����ֱ�ӷ��ʼ���
		JavaMainTest.printInfo();
		// ֱ�ӵ���StaticDemo1�е�s����ʱ��ֱ�ӷ��ʼ���
		System.out.println(JavaMainTest.s);
	}
}