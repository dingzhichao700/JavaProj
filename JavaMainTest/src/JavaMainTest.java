public class JavaMainTest {

	public static String s = "我是静态变量";

	public static void printInfo() {
		System.out.println("啊哈哈哈哈哈");
	}

	public static void main(String[] args) {
		// 调用StaticDemo1中的printInfo方法时，不需要创建新的
		// StaticDemo1对象，直接访问即可
		JavaMainTest.printInfo();
		// 直接调用StaticDemo1中的s变量时，直接访问即可
		System.out.println(JavaMainTest.s);
	}
}