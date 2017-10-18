public class JavaMainTest
{
    public static void main(String[] args) 
    {
        System.out.println("打印main方法的输入参数");
        for(int i=0;i<args.length;i++){
            System.out.println(args[i]);
        }
    }
}